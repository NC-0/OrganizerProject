package organizer.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;

import organizer.dao.api.SubtaskDao;
import organizer.logic.impl.SqlContent;
import organizer.models.Subtask;
import organizer.models.Task;

public class SubtaskDaoImpl implements SubtaskDao {
	@Autowired
	@Qualifier("jdbcTemplate")
	private JdbcTemplate jdbcTemplate;
	
	public void create(Subtask subtask, Task task) {
		// TODO Auto-generated method stub
		
	}

	public void delete(int id) {
		jdbcTemplate.update(SubtaskDao.DELETE_SUBTASK_OBJECT,id);
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
