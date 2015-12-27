package organizer.dao.mappers;

import org.springframework.jdbc.core.RowMapper;
import organizer.models.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRowMapper implements RowMapper<User> {
	public User mapRow(ResultSet resultSet, int i) throws SQLException {
		User user = new User(resultSet.getString("email"),
			resultSet.getString("password"),
			resultSet.getString("username"),
			resultSet.getString("surname"));
		user.setRole("USER_ROLE");
		user.setId(Integer.valueOf(resultSet.getString("userid")));
		user.setEnabled(Boolean.valueOf(resultSet.getString("enabled")));
		return user;
	}
}