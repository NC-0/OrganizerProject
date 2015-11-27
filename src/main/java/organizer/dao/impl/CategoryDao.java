package organizer.dao.impl;

import org.springframework.jdbc.core.JdbcTemplate;

import organizer.dao.api.CategoryIDao;
import organizer.models.Category;

import javax.sql.DataSource;

public class CategoryDao implements CategoryIDao {

	private JdbcTemplate jdbcTemplate;

	public void setDataSource(DataSource dataSource)  {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public void createCategory(Category newCategory) {
		Integer id = jdbcTemplate.queryForObject("SELECT object_id.nextval FROM dual", Integer.class);
		jdbcTemplate.execute("INSERT INTO objects VALUES ("+id+",NULL,4,'"+newCategory.getName()+"',NULL)");
	}

	public void deleteCategory() {
		// TODO Auto-generated method stub
		
	}

	public void editCategory() {
		// TODO Auto-generated method stub
		
	}

	public Category getCategory() {
		// TODO Auto-generated method stub
		return null;
	}

}
