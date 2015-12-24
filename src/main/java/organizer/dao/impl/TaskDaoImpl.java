package organizer.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import organizer.dao.api.TaskDao;
import organizer.dao.api.UserDao;
import organizer.models.Category;
import organizer.models.Task;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TaskDaoImpl implements TaskDao {
	@Autowired
	@Qualifier("jdbcTemplate")
	private JdbcTemplate jdbcTemplate;

	public void create(int userId, Task task) {
		// Add Task object 	
		jdbcTemplate.update(TaskDao.INSERT, userId, task.getName());

		// get current task object_id from OBJECTS and set it
		int taskId = jdbcTemplate.queryForObject(UserDao.SELECT_ID, Integer.class);
		task.setId(taskId);

		// Task Object - Fill all task attributes
		jdbcTemplate.update(TaskDao.INSERT_DATE, taskId, task.getDate());
		jdbcTemplate.update(TaskDao.INSERT_PRIORITY, taskId, task.getPriority());
		jdbcTemplate.update(TaskDao.INSERT_CATEGORY, taskId, task.getCategory());
		jdbcTemplate.update(TaskDao.INSERT_STATUS, taskId, task.isCompleted());

		// Add reference between Task and User
		jdbcTemplate.update(TaskDao.INSERT_REF_USER, taskId, userId);
	}

	public void delete(int id) {
		jdbcTemplate.update(TaskDao.DELETE, id);
	}

	public void edit(Task task) {
		jdbcTemplate.update(UPDATE_NAME, task.getName(), task.getId());
		jdbcTemplate.update(UPDATE_DATE, task.getDate(), task.getId());
		jdbcTemplate.update(UPDATE_PRIORITY, task.getPriority(), task.getId());
		jdbcTemplate.update(UPDATE_CATEGORY, task.getCategory().getId(), task.getId());
		jdbcTemplate.update(UPDATE_STATUS, task.isCompleted(), task.getId());
		if (task.isCompleted())
			jdbcTemplate.update(UPDATE_SUBTASK_STATUS, task.getId());
	}

	public List <Task> get(int userId) {
		List<Task>tasks = (ArrayList<Task>)jdbcTemplate.query(TaskDao.SELECT_LIST_OF_USER_TASKS,
				new Object[]{userId}, new RowMapper<Task>(){

			@Override
			public Task mapRow(ResultSet rs, int rowNum) throws SQLException {

				Task task = new Task(rs.getInt("REFERENCE"),rs.getString("Name"),rs.getDate("DATE_VALUE"),
						rs.getInt("PRIORITY"), new Category(), new Boolean(rs.getString("Status")),null);

			return task;
			}
		});
		return tasks;
	}

	public ArrayList<Task> getSubtaskList() {
		// TODO Auto-generated method stub
		return null;
	}
}