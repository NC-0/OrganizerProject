package organizer.dao.api;

import org.springframework.jdbc.core.JdbcTemplate;

import organizer.models.User;

public interface UserIDao {
	void setJdbcTemplate(JdbcTemplate jdbcTemplate);
	boolean isExistUser(User user);
	String createUser(User user);
	String deleteUser(User user);
	String editUser(User user);
	User getUser();
}
