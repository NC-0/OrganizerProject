package organizer.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import organizer.dao.api.TaskDao;
import organizer.dao.api.UserDao;
import organizer.dao.cache.CacheImpl;
import organizer.dao.cache.TaskMapper;
import organizer.models.Category;
import organizer.models.Task;
import organizer.models.User;

import java.util.List;

public class TaskDaoImpl implements TaskDao {
	@Autowired
	@Qualifier("jdbcTemplate")
	private JdbcTemplate jdbcTemplate;

	@Autowired
	@Qualifier("daoCache")
	private CacheImpl cache;

	public void create(int userId, Task task) {
		task.getName();
		// Add Task object
		jdbcTemplate.update(TaskDao.INSERT, task.getName());

		// get current task object_id from OBJECTS and set it
		int taskId = jdbcTemplate.queryForObject(UserDao.SELECT_ID, Integer.class);
		task.setId(taskId);

		// Task Object - Fill all task attributes
		java.sql.Date sqlDate = new java.sql.Date(task.getDate().getTime());
		jdbcTemplate.update(TaskDao.INSERT_DATE, taskId, sqlDate);

		jdbcTemplate.update(TaskDao.INSERT_PRIORITY, taskId, task.getPriority());
		jdbcTemplate.update(TaskDao.INSERT_CATEGORY, taskId, task.getCategory().getId());
		jdbcTemplate.update(TaskDao.INSERT_STATUS, taskId, task.isCompleted());

		// Add reference between Task and User
		jdbcTemplate.update(TaskDao.INSERT_REF_USER, taskId, userId);

		// Add reference between Task and Category
		jdbcTemplate.update(TaskDao.INSERT_REF_CATEGORY, taskId, task.getCategory().getId());
	}

	public void delete(Task task) {
		// delete reference to parent user
		jdbcTemplate.update(TaskDao.DELETE_REF_TO_USER, task.getId());
		// delete reference to parent category
		jdbcTemplate.update(TaskDao.DELETE_REF_TO_CATEGORY, task.getId());
		// delete task
		jdbcTemplate.update(TaskDao.DELETE_OBJECT, task.getId());
	}

	public void edit(Task task) {
		jdbcTemplate.update(UPDATE_NAME, task.getName(), task.getId());
		jdbcTemplate.update(UPDATE_DATE, task.getDate(), task.getId());
		jdbcTemplate.update(UPDATE_PRIORITY, task.getPriority(), task.getId());
		jdbcTemplate.update(UPDATE_CATEGORY, task.getCategory().getId(), task.getId());
		jdbcTemplate.update(UPDATE_STATUS, task.isCompleted(), task.getId());

		// update category reference
		jdbcTemplate.update(UPDATE_CATEGORY_REF, task.getCategory().getId(), task.getId());

		// update all subtasks
		if (task.isCompleted())
			jdbcTemplate.update(UPDATE_SUBTASK_STATUS, task.getId());
	}

	public void updateStatus(Task task) {
		jdbcTemplate.update(UPDATE_STATUS, task.isCompleted(), task.getId());
	}

	public Task get(User user, int taskId) {
		Task task = jdbcTemplate.queryForObject(
			SELECT_TASK_BY_ID,
			new TaskMapper(cache),
			taskId
		);
		return task;
	}

	public List <Task> get(final User user, boolean completed) {
		List<Task> tasks =  jdbcTemplate.query(
				SELECT_LIST_OF_USER_TASKS,
				new TaskMapper(cache),
				completed,
				user.getId()
		);
		return tasks;
	}
	public List <Task> getByCat(final User user, Category category) {
		List<Task> tasks =  jdbcTemplate.query(
				SELECT_LIST_OF_USER_TASKS_BY_CAT,
				new TaskMapper(cache),
				user.getId(),
				String.valueOf(category.getId())
		);
		return tasks;
	}
}