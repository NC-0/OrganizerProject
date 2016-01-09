package organizer.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import organizer.dao.api.CategoryDao;
import organizer.dao.api.UserDao;
import organizer.dao.cache.CategoryRowMapper;
import organizer.models.Category;
import organizer.models.User;

import java.util.List;

public class CategoryDaoImpl implements CategoryDao {

	@Autowired
	@Qualifier("jdbcTemplate")
	private JdbcTemplate jdbcTemplate;

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
	}

	public void delete(Category category) {
		jdbcTemplate.update(
			CategoryDao.DELETE,
			category.getId()
		);
	}

	public void update(Category category) {
		jdbcTemplate.update(
			CategoryDao.UPDATE,
			category.getName(),
			category.getId()
		);
	}

	public List<Category> get(User user) {
		return jdbcTemplate.query(
			SELECT_USER_CATEGORIES,
			new CategoryRowMapper(),
			user.getId()
		);
	}
}