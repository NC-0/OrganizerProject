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
		Integer id = jdbcTemplate.queryForObject("select object_id.nextval from dual", Integer.class);
		jdbcTemplate.execute("insert into objects values ("+id+",null,4,'"+newCategory.getName()+"',null)");
		jdbcTemplate.execute("insert into attributes values (9,"+ id +","+ newCategory.getPosition()+",null)");

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
