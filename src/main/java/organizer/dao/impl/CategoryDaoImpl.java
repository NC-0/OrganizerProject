package organizer.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import organizer.dao.api.CategoryDao;
import organizer.dao.api.SubtaskDao;
import organizer.dao.mappers.CategoryRowMapper;
import organizer.dao.mappers.SubtaskRowMapper;
import organizer.models.Category;
import organizer.models.Subtask;
import organizer.models.User;

import java.util.List;

public class CategoryDaoImpl implements CategoryDao {
	@Autowired
	@Qualifier("jdbcTemplate")
	private JdbcTemplate jdbcTemplate;

	public void create(Category category) {
		jdbcTemplate.update(
			CategoryDao.INSERT,
			category.getName()
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
			CategoryDao.SELECT_BY_USER_ID,
			new CategoryRowMapper(user),
			user.getId()
		);
	}
}
