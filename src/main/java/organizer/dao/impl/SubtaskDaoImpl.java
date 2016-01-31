package organizer.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import organizer.dao.api.SubtaskDao;
import organizer.dao.cache.SubtaskRowMapper;
import organizer.models.Subtask;
import organizer.models.Task;

import java.util.List;

public class SubtaskDaoImpl implements SubtaskDao {
	@Autowired
	@Qualifier("jdbcTemplate")
	private JdbcTemplate jdbcTemplate;
	
	public void create(Subtask subtask) {
		jdbcTemplate.update(
			SubtaskDao.INSERT,
			subtask.getTask().getId(),
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
			subtask.isCompleted()
		);
	}

	public void delete(Subtask subtask) {
		jdbcTemplate.update(SubtaskDao.DELETE, subtask.getId());
	}

	public void update(Subtask subtask) {
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
			new SubtaskRowMapper(task),
			task.getId()
		);
	}

	// Get a single subtask by id
	public Subtask get(int id) {
		return jdbcTemplate.queryForObject(
			SubtaskDao.SELECT,
			new SubtaskRowMapper(),
			id
		);
	}
}
