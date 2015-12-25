package organizer.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import organizer.dao.api.SubtaskDao;
import organizer.dao.mappers.SubtaskRowMapper;
import organizer.models.Subtask;
import organizer.models.Task;

import java.util.List;

@Component
@Scope("prototype")
public class SubtaskDaoImpl implements SubtaskDao {
	@Autowired
	@Qualifier("jdbcTemplate")
	private JdbcTemplate jdbcTemplate;
	
	public void create(Subtask subtask, Task task) {
		jdbcTemplate.update(
			SubtaskDao.INSERT,
			task.getId(),
			subtask.getName()
		);

		int subtaskId = jdbcTemplate.queryForObject(
			SubtaskDao.SELECT_ID,
			Integer.class
		);

		subtask.setId(subtaskId);

		jdbcTemplate.update(
			SubtaskDao.INSERT_COMPLETED,
			subtaskId,
			task.isCompleted()
		);
	}

	public void delete(int id) {
		jdbcTemplate.update(SubtaskDao.DELETE, id);
	}

	public void edit(Subtask subtask) {
		jdbcTemplate.update(
			UPDATE_NAME,
			subtask.getName(),
			subtask.getId()
		);

		jdbcTemplate.update(
			UPDATE_STATUS,
			subtask.isCompleted(),
			subtask.getId()
		);
	}

	// Get a list of subtasks of a specific task
	public List<Subtask> get(Task task) {
		return jdbcTemplate.query(
			SubtaskDao.SELECT_BY_TASK_ID,
			new SubtaskRowMapper(),
			task.getId()
		);
	}

	// Get a single subtask by id
	public Subtask get(int subtaskId) {
		return jdbcTemplate.queryForObject(
			SubtaskDao.SELECT,
			new SubtaskRowMapper(),
			subtaskId
		);
	}
}
