package organizer.dao.cache;

import organizer.models.Category;
import organizer.models.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CategoryRowMapper extends CachedRowMapper<Category> {
	private User user;

	public CategoryRowMapper(User user) {
		this.user = user;
	}

	public CategoryRowMapper(){
	this(null);
	}

	@Override
	public Category createObject(int id, ResultSet resultSet) throws SQLException {
		Category category = new Category(
			resultSet.getString("name")
		);
		category.setUser(user);
		category.setId(Integer.valueOf(resultSet.getString("id")));
		return category;
	}
}
