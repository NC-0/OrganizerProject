package organizer.dao.rowmappers;

import org.springframework.jdbc.core.RowMapper;
import organizer.models.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRowMapper implements RowMapper<User> {
	@Override
	public User mapRow(ResultSet resultSet, int i) throws SQLException {
		User user = new User(
			resultSet.getString("email"),
			resultSet.getString("password"),
			resultSet.getString("username"),
			resultSet.getString("surname")
		);
		user.setVerify(resultSet.getString("verify"));
		user.setRegistrationDate(resultSet.getDate("regdate"));
		user.setRole("USER_ROLE");
		user.setId(Integer.valueOf(resultSet.getString("id")));
		user.setEnabled(Boolean.valueOf(resultSet.getString("enabled")));
		return user;
	}
}
