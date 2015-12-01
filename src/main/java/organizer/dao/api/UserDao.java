package organizer.dao.api;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.support.TransactionTemplate;

import organizer.models.User;

public interface UserDao {
	boolean exist(String email);
	String create(User user);
	String delete(String email);
	String edit(User user);
	User get(String email);
}
