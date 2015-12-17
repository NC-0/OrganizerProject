package organizer.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import organizer.dao.api.SubtaskDao;
import organizer.dao.api.TaskDao;
import organizer.dao.api.UserDao;
import organizer.logic.impl.SqlContent;
import organizer.models.Subtask;
import organizer.models.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SubtaskDaoImpl implements SubtaskDao {
	@Autowired
	@Qualifier("jdbcTemplate")
	private JdbcTemplate jdbcTemplate;
	
	public void create(Subtask subtask, Task task) {
		jdbcTemplate.update(SubtaskDao.INSERT, task.getId(), subtask.getName());

		int subtaskId = jdbcTemplate.queryForObject(SubtaskDao.SELECT_ID, Integer.class);
		task.setId(subtaskId);

		jdbcTemplate.update(SubtaskDao.INSERT_COMPLETED, subtaskId, task.isCompleted());
		
	}

	public void delete(int id) {
		jdbcTemplate.update(SubtaskDao.DELETE, id);
	}

	public void edit(Subtask subtask) {
		jdbcTemplate.update(SqlContent.UPDATE_TASK_NAME, subtask.getName(), subtask.getId());
		jdbcTemplate.update(SqlContent.UPDATE_TASK_STATUS, subtask.isCompleted(), subtask.getId());
		
	}
	
	public ArrayList<Subtask> get(Task task) {
		ArrayList<Subtask> subtaskList = new ArrayList<>();
		
		List<Map<String, Object>> resultList = jdbcTemplate.queryForList(SqlContent.SELECT_SUBTASKS_BY_TASK_ID);
		for (Map<String, Object> curMap : resultList) {
			Subtask subtask = new Subtask(
				(String) curMap.get("SubtaskName"),
				(Boolean) curMap.get("Status")
			);
			subtaskList.add(subtask);
		}
		return subtaskList;
	}
	
}
