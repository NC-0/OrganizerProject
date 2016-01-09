package organizer.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import organizer.dao.api.SubtaskDao;
import organizer.dao.cache.SubtaskRowMapper;
import organizer.models.Subtask;
import organizer.models.Task;

import java.util.List;

@Component
@Scope("prototype")
public class SubtaskDaoImpl implements SubtaskDao {
	@Autowired
	@Qualifier("jdbcTemplate")
	private JdbcTemplate jdbcTemplate;
	
	public void create(Subtask subtask) {
		jdbcTemplate.update(
			INSERT,
			subtask.getTask().getId(),
			subtask.getName()
		);

		int subtaskId = jdbcTemplate.queryForObject(
			SELECT_ID,
			Integer.class
		);

		subtask.setId(subtaskId);

		jdbcTemplate.update(
			INSERT_COMPLETED,
			subtaskId
		);
	}

	public void delete(Subtask subtask) {
		jdbcTemplate.update(DELETE, subtask.getId());
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

	// Get a single subtask by id [NOT IMPLEMENTED]
//	public Subtask get(int subtaskId) {
//		return jdbcTemplate.queryForObject(
//			SubtaskDao.SELECT,
//			new SubtaskRowMapper(),
//			subtaskId
//		);
//	}
}
