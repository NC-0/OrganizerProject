package organizer.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import org.springframework.stereotype.Component;
import organizer.dao.api.TaskDao;
import organizer.dao.api.UserDao;
import organizer.models.Category;
import organizer.models.Subtask;
import organizer.models.Task;
import organizer.models.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
@Scope("prototype")
public class TaskDaoImpl implements TaskDao {
	@Autowired
	@Qualifier("jdbcTemplate")
	private JdbcTemplate jdbcTemplate;

	public void create(int userId, Task task) {
		task.getName();
		// Add Task object 	
		jdbcTemplate.update(TaskDao.INSERT, task.getName());

		// get current task object_id from OBJECTS and set it
		int taskId = jdbcTemplate.queryForObject(UserDao.SELECT_ID, Integer.class);
		task.setId(taskId);

		// Task Object - Fill all task attributes
		jdbcTemplate.update(TaskDao.INSERT_DATE, taskId, task.getDate());
		jdbcTemplate.update(TaskDao.INSERT_PRIORITY, taskId, task.getPriority());
		jdbcTemplate.update(TaskDao.INSERT_CATEGORY, taskId, task.getCategory().getName());
		jdbcTemplate.update(TaskDao.INSERT_STATUS, taskId, task.isCompleted());

		// Add reference between Task and User
		jdbcTemplate.update(TaskDao.INSERT_REF_USER, taskId, userId);
	}

	public void delete(Task task) {
		System.out.println("deleting");
		// delete reference to parent user
		jdbcTemplate.update(TaskDao.DELETE_REF_TO_USER, task.getId());
		// delete task
		jdbcTemplate.update(TaskDao.DELETE_OBJECT, task.getId());
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

	public List <Task> get(final User user) {
//		List<Task>tasks = (ArrayList<Task>)jdbcTemplate.query(TaskDao.SELECT_LIST_OF_USER_TASKS,
//				new Object[]{user.getId()}, new RowMapper<Task>(){
//			@Override
//			public Task mapRow(ResultSet rs, int rowNum) throws SQLException {
//				Task task = new Task(rs.getInt("REFERENCE"),rs.getString("Name"),rs.getDate("DATE_VALUE"),
//						rs.getInt("PRIORITY"), new Category(rs.getInt("CATEGORY_ID"), rs.getString("CATEGORY_NAME"), user),
//						new Boolean(rs.getString("Status")), getSubtasks(rs.getInt("REFERENCE")));
//			return task;
//			}
//		});
//		return tasks;
		return null;
	}
	
	public List <Subtask> getSubtasks (final Task task){
//		List <Subtask> subtasks = (ArrayList<Subtask>)jdbcTemplate.query(TaskDao.SELECT_SUBTASKS_BY_TASK_ID, new Object[]{task.getId()},
//				new RowMapper <Subtask>() {
//					@Override
//					public Subtask mapRow(ResultSet rs, int rowNum)
//							throws SQLException {
//						Subtask subtask = new Subtask(rs.getInt("OBJECT_ID"), rs.getString("NAME"), new Boolean(rs.getString("VALUE")), task);
//						return subtask;
//					}
//		});
//		return (ArrayList<Subtask>) subtasks;
		return null;
	}


}