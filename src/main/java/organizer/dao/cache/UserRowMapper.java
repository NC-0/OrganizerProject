package organizer.dao.cache;

import organizer.models.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRowMapper extends CachedRowMapper<User> {
	@Override
	public User createObject(int id, ResultSet resultSet) throws SQLException {
		User user = new User(
			resultSet.getString("email"),
			resultSet.getString("password"),
			resultSet.getString("username"),
			resultSet.getString("surname")
		);
		boolean enabled = Boolean.valueOf(resultSet.getString("enabled"));
		if(enabled)
			user.setRole("USER_ROLE");
		else
			user.setRole("VERIFY_ROLE");
		user.setId(Integer.valueOf(resultSet.getString("id")));
		user.setEnabled(enabled);
		return user;
	}
}