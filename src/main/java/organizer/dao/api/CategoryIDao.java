package organizer.dao.api;

import org.springframework.jdbc.core.JdbcTemplate;

import organizer.models.Category;

public interface CategoryIDao {
	void setJdbcTemplate(JdbcTemplate jdbcTemplate);
	void createCategory();
	void deleteCategory();
	void editCategory();
	Category getCategory();
}
