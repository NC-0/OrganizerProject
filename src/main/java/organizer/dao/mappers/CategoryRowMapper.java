package organizer.dao.mappers;

import org.springframework.jdbc.core.RowMapper;
import organizer.models.Category;
import organizer.models.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CategoryRowMapper implements RowMapper<Category> {
	private User user;

	public CategoryRowMapper(User user) {
		this.user = user;
	}

	@Override
	public Category mapRow(ResultSet resultSet, int i) throws SQLException {
		return new Category(
			resultSet.getInt("id"),
			resultSet.getString("name"),
			user
		);
	}
}
