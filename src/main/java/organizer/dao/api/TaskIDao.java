package organizer.dao.api;

import java.util.ArrayList;

import org.springframework.jdbc.core.JdbcTemplate;

import organizer.models.Task;

public interface TaskIDao {
	void setJdbcTemplate(JdbcTemplate jdbcTemplate);
	void createTask();
	void deleteTask();
	void editTask();
	Task getTask();
	ArrayList<Task> getSubTaskList();
}
