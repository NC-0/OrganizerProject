package organizer.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import organizer.dao.api.CategoryDao;
import organizer.logic.impl.SqlContent;
import organizer.models.Category;
import organizer.models.User;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoryDaoImpl implements CategoryDao {
	@Autowired
	@Qualifier("jdbcTemplate")
	private JdbcTemplate jdbcTemplate;

	public void create(Category category) {
		throw new UnsupportedOperationException("Need realization session and then we may get user in session.");
	}

	public void create(User user, Category category) {
		jdbcTemplate.update(CategoryDao.INSERT_CATEGORY_OBJECT, category.getName());
		Integer categoryId = jdbcTemplate.queryForObject(CategoryDao.SELECT_ID, Integer.class);
		jdbcTemplate.update(CategoryDao.INSERT_REF_TO_USER, user.getId(), categoryId);
	}

	public void delete(int id) {
		jdbcTemplate.update(CategoryDao.DELETE, id);
	}

	public void edit(Category category) {
		// get category Id 
		int categoryId = jdbcTemplate.queryForObject(SqlContent.SELECT_CATEGORY_ID_BY_NAME,
			new Object[]{category.getName()}, Integer.class);

		// update category in db
		jdbcTemplate.update(SqlContent.UPDATE_CATEGORY_NAME, category.getName(), categoryId);
	}

	public List<Category> get(int userId) {
		List<Category> categories = (ArrayList<Category>) jdbcTemplate.query(CategoryDao.SELECT_USER_CATEGORIES, new Object[]{userId}, new RowMapper<Category>() {
			public Category mapRow(ResultSet rs, int rowNum) throws SQLException {
				Category category = new Category(rs.getString("NAME"));
				return category;
			}
		});
		return categories;
	}

}
