package organizer.dao.api;

import java.util.ArrayList;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.support.TransactionTemplate;

import organizer.models.Task;
import organizer.models.User;

public interface TaskDao {
	void create(User user, Task task);
	void delete();
	void edit(Task task);
	Task get();
	ArrayList<Task> getSubTaskList();
}
