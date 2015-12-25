package organizer.dao.mappers;

import org.springframework.jdbc.core.RowMapper;
import organizer.models.Subtask;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SubtaskRowMapper implements RowMapper<Subtask> {
	// FLYWEIGHT cache will be implemented
	@Override
	public Subtask mapRow(ResultSet resultSet, int i) throws SQLException {
		return new Subtask(
			resultSet.getInt("id"),
			resultSet.getString("name"),
			resultSet.getBoolean("completed")
		);
	}
}
