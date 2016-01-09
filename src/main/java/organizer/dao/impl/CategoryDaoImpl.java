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
				INSERT_OBJECT,
			category.getName()
		);
		category.setId(
				jdbcTemplate.queryForObject(
						SELECT_ID, Integer.class
				)
		);
		jdbcTemplate.update(
				INSERT_REF,
				category.getId(),
				category.getUser().getId()
		);
	}

	public void delete(Category category) {
		jdbcTemplate.update(
				DELETE_OBJECT,
				category.getId()
		);
		jdbcTemplate.update(
				DELETE_REF,
				category.getId()
		);
	}

	public void update(Category category) {
		jdbcTemplate.update(
			UPDATE,
			category.getName(),
			category.getId()
		);
	}

	public List<Category> get(User user) {
		return jdbcTemplate.query(
			SELECT_BY_USER_ID,
			new CategoryRowMapper(),
			user.getId()
		);
	}
}