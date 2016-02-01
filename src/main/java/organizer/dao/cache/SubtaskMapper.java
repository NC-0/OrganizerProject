package organizer.dao.cache;

import organizer.models.Subtask;
import organizer.models.Task;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SubtaskMapper extends CachedMapper<Subtask> {

	private Task task;

	public SubtaskMapper(CacheImpl cache,Task task) {
		setCacheImpl(cache);
		this.task = task;
	}

	public SubtaskMapper(CacheImpl cache) {
		setCacheImpl(cache);
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
