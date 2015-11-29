package organizer.dao.api;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.support.TransactionTemplate;

import organizer.models.User;

public interface UserDao {
	int exist(User user);
	String create(User user);
	String delete(User user);
	String edit(User user);
	User get();
}
