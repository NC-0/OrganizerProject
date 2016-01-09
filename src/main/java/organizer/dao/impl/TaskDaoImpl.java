package organizer.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import organizer.dao.api.TaskDao;
import organizer.dao.cache.SubtaskRowMapper;
import organizer.dao.cache.TaskRowMapper;
import organizer.models.Task;
import organizer.models.User;
import java.util.List;

@Component
@Scope("prototype")
public class TaskDaoImpl implements TaskDao {
	@Autowired
	@Qualifier("jdbcTemplate")
	private JdbcTemplate jdbcTemplate;

	public void create(Task task) {
		jdbcTemplate.update(INSERT, task.getName());
		int taskId = jdbcTemplate.queryForObject(SELECT_ID, Integer.class);
		task.setId(taskId);

		jdbcTemplate.update(INSERT_DATE, taskId, task.getDate());
		jdbcTemplate.update(INSERT_PRIORITY, taskId, task.getPriority());
		jdbcTemplate.update(INSERT_CATEGORY, taskId, task.getCategory().getId());
		jdbcTemplate.update(INSERT_STATUS, taskId, task.isCompleted());
		jdbcTemplate.update(INSERT_REF_USER, taskId, task.getUser().getId());
	}

	public void delete(Task task) {
		jdbcTemplate.update(DELETE_REF_TO_USER, task.getId());
		jdbcTemplate.update(DELETE_OBJECT, task.getId());
	}

	public void update(Task task) {
		jdbcTemplate.update(UPDATE_NAME, task.getName(), task.getId());
		jdbcTemplate.update(UPDATE_DATE, task.getDate(), task.getId());
		jdbcTemplate.update(UPDATE_PRIORITY, task.getPriority(), task.getId());
		jdbcTemplate.update(UPDATE_CATEGORY, task.getCategory().getId(), task.getId());
		jdbcTemplate.update(UPDATE_STATUS, task.isCompleted(), task.getId());
		if (task.isCompleted())
			jdbcTemplate.update(UPDATE_SUBTASK_STATUS, task.getId());
	}
	public List<Task> get(User user) {
		List<Task> tasks =  jdbcTemplate.query(SELECT_LIST_OF_USER_TASKS,new TaskRowMapper(user),user.getId());
		for (Task task: tasks ) {
			task.setSubtasks(jdbcTemplate.query(SELECT_SUBTASKS_BY_TASK_ID,new SubtaskRowMapper(task),task.getId()));
		}
		return tasks;
	}


}