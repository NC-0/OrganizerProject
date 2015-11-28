package organizer.dao.api;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.support.TransactionTemplate;

import organizer.models.User;

public interface UserIDao {
	void setJdbcTemplate(JdbcTemplate jdbcTemplate);
	void setTransactionTemplate(TransactionTemplate transactionTemplate);
	int existUser(User user);
	String create(final User user);
	String delete(User user);
	String edit(User user);
	User get(String userName);
}
