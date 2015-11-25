package organizer.dao.api;

import java.util.ArrayList;

import org.springframework.jdbc.core.JdbcTemplate;

import organizer.models.Task;

public interface TaskIDao {
	void setJdbcTemplate(JdbcTemplate jdbcTemplate);
	void create(Task task);
	void delete();
	void edit();
	Task get();
	ArrayList<Task> getSubTaskList();
}
