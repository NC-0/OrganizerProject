package organizer.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import organizer.dao.api.CategoryDao;
import organizer.dao.api.UserDao;
import organizer.dao.cache.CacheImpl;
import organizer.dao.cache.CategoryMapper;
import organizer.models.Category;
import organizer.models.User;

import java.util.List;

public class CategoryDaoImpl implements CategoryDao {

	@Autowired
	@Qualifier("jdbcTemplate")
	private JdbcTemplate jdbcTemplate;

	@Autowired
	@Qualifier("daoCache")
	private CacheImpl cache;

	public void create(Category category) {
		jdbcTemplate.update(
			INSERT,
			category.getName()
		);
		Integer objectsCurrentValue = jdbcTemplate.queryForObject(
			UserDao.SELECT_ID,
			Integer.class);
		jdbcTemplate.update(
			INSERT_REF,
			category.getUser().getId(),
			objectsCurrentValue
		);
		category.setId(objectsCurrentValue);
		cache.add(objectsCurrentValue,category);
	}

	public void delete(Category category) {
		jdbcTemplate.update(
			DELETE_REF,
			category.getId()
		);
		jdbcTemplate.update(
			DELETE,
			category.getId()
		);
		cache.delete(category.getId());
	}

	public void update(Category category) {
		jdbcTemplate.update(
			UPDATE,
			category.getName(),
			category.getId()
		);
		cache.add(category.getId(),category);
	}

	public List<Category> get(User user) {
		return jdbcTemplate.query(
			SELECT_USER_CATEGORIES,
			new CategoryMapper(cache),
			user.getId()
		);
	}

	public Category get(int id,User user) {
		Category category = jdbcTemplate.queryForObject(
			SELECT_CATEGORY_BY_ID,
			new CategoryMapper(cache),
			id,
			user.getId()
		);
		return category;
	}
}