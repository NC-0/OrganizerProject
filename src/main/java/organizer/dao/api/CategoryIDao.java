package organizer.dao.api;

import org.springframework.jdbc.core.JdbcTemplate;

import organizer.models.Category;

import javax.sql.DataSource;

public interface CategoryIDao {
	void setDataSource(DataSource dataSource);
	void createCategory(Category newCategory);
	void deleteCategory();
	void editCategory();
	Category getCategory();
}
