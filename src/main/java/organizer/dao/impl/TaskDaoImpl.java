package organizer.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import organizer.dao.api.TaskDao;
import organizer.dao.api.UserDao;
import organizer.logic.impl.SqlContent;
import organizer.models.Task;

import java.util.ArrayList;

public class TaskDaoImpl implements TaskDao {
	@Autowired
	@Qualifier("jdbcTemplate")
	private JdbcTemplate jdbcTemplate;

	public boolean isExist(Task task) {
		boolean exist = jdbcTemplate.update(SqlContent.SELECT_TASK_BY_OBJECT_ID, task.getId()) > 0;
		return exist;
	}

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
		if (isExist(task)) {
//			updateSubtask(task); // This is not implememted yet
			jdbcTemplate.update(SqlContent.UPDATE_TASK_NAME, task.getName(), task.getId());
			jdbcTemplate.update(SqlContent.UPDATE_TASK_DATE, task.getDate(), task.getId());
			jdbcTemplate.update(SqlContent.UPDATE_TASK_PRIORITY, task.getPriority(), task.getId());
			jdbcTemplate.update(SqlContent.UPDATE_TASK_CATEGORY, task.getCategory(), task.getId());
			jdbcTemplate.update(SqlContent.UPDATE_TASK_STATUS, task.isCompleted(), task.getId());
		}
	}

	public Task get() {
		// To be done very soon
	}

	public ArrayList<Task> getSubtaskList() {
		// TODO Auto-generated method stub
		return null;
	}
}