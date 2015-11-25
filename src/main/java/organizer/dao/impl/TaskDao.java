package organizer.dao.impl;

import java.util.ArrayList;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;

import organizer.dao.api.TaskIDao;
import organizer.models.Task;

public class TaskDao implements TaskIDao {
	private JdbcTemplate jdbcTemplateObject;
	private SimpleJdbcCall jdbcCall;
	/**
	 * current task id
	 */
	private int id = 0; 
	
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplateObject = jdbcTemplate;
		this.jdbcCall = new SimpleJdbcCall(jdbcTemplateObject).withProcedureName("createTask"); 
	}
	
	public void create(Task task) {
		SqlParameterSource in = new MapSqlParameterSource();
		((MapSqlParameterSource) in).addValue("v_task", "Task" + (id++));
		((MapSqlParameterSource) in).addValue("v_name", task.getName());
		((MapSqlParameterSource) in).addValue("v_date", task.getDate());
		((MapSqlParameterSource) in).addValue("v_priority", task.getPriority());
		((MapSqlParameterSource) in).addValue("v_category", task.getCategory());
		((MapSqlParameterSource) in).addValue("v_status", task.isCompleted());
		jdbcCall.execute(in); 
	}

	public void delete() {
		// TODO Auto-generated method stub
		
	}

	public void edit() {
		// TODO Auto-generated method stub
		
	}

	public Task get() {
		// TODO Auto-generated method stub
		return null;
	}



	public ArrayList<Task> getSubTaskList() {
		// TODO Auto-generated method stub
		return null;
	}

}
