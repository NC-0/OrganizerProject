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
	
	@Override
	public void create(Subtask subtask, Task task) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Subtask subtask) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void edit(Subtask subtask) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public ArrayList<Subtask> get(Task task) {

	}
	
}
