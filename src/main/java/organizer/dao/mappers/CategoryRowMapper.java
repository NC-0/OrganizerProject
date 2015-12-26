package organizer.dao.mappers;

import organizer.models.Category;
import organizer.models.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CategoryRowMapper extends CachedRowMapper<Category> {
	private User user;

	public CategoryRowMapper(User user) {
		this.user = user;
	}

	@Override
	public Category createObject(int id, ResultSet resultSet, int i) throws SQLException {
		return new Category(
			id,
			resultSet.getString("name"),
			user
		);
	}
}
