package organizer.dao.api;

import java.util.ArrayList;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.support.TransactionTemplate;

import organizer.models.Task;
import organizer.models.User;

public interface TaskIDao {
	void setJdbcTemplate(JdbcTemplate jdbcTemplate);
	void setTransactionTemplate(TransactionTemplate transactionTemplate);
	void create(final User user, final Task task);
	void delete();
	void edit();
	Task get();
	ArrayList<Task> getSubTaskList();
}
