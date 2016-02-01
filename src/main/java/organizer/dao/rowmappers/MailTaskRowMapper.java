package organizer.dao.rowmappers;


import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import org.springframework.jdbc.core.RowMapper;
import organizer.models.Task;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class MailTaskRowMapper implements RowMapper {
	private Multimap<String,Task> taskMultimap;

	public MailTaskRowMapper(Multimap<String, Task> taskMultimap) {
		this.taskMultimap = taskMultimap;
	}

	public Object mapRow(ResultSet resultSet, int i) throws SQLException {
		Task task = new Task();
		task.setName(resultSet.getString("task"));
		task.setPriority(resultSet.getInt("priorr"));
		taskMultimap.put(resultSet.getString("mail"),task);
		return task;
	}
}
