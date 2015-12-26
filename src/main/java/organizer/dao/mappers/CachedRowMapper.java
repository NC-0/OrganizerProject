package organizer.dao.mappers;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public abstract class CachedRowMapper<T> implements RowMapper<T> {

	private Map<Integer, T> data;

	public CachedRowMapper() {
		data = new HashMap<>();
	}

	public abstract T createObject(int id, ResultSet resultSet, int i) throws SQLException;

	public T addToCache(int id, ResultSet resultSet, int i) throws SQLException {
		T result = createObject(id, resultSet, i);
		data.put(id, createObject(id, resultSet, i));
		return result;
	}

	@Override
	public T mapRow(ResultSet resultSet, int i) throws SQLException {
		int id = resultSet.getInt("id");

		if (!data.containsKey(id)) {
			return addToCache(id, resultSet, i);
		}

		return data.get(id);
	}
}
