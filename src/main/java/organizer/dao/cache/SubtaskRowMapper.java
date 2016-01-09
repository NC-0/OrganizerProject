package organizer.dao.cache;

import organizer.models.Subtask;
import organizer.models.Task;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SubtaskRowMapper extends CachedRowMapper<Subtask> {

	private Task task;

	public SubtaskRowMapper(Task task) {
		this.task = task;
	}

	@Override
	public Subtask createObject(int id, ResultSet resultSet) throws SQLException {
		return new Subtask(
			id,
			resultSet.getString("name"),
			"1".equals(resultSet.getString("completed")),
			task
		);
	}
}
