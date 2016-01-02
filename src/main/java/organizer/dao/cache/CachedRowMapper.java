package organizer.dao.cache;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class CachedRowMapper<T> implements RowMapper<T> {

	private LoadingCache<Integer, T> data;

	public CachedRowMapper() {
		data = CacheBuilder.newBuilder().softValues().build(new RowMapperCacheLoader());
	}

	public abstract T createObject(int id, ResultSet resultSet) throws SQLException;

	public T addToCache(int id, ResultSet resultSet) throws SQLException {
		T result = createObject(id, resultSet);
		data.put(id, createObject(id, resultSet));
		return result;
	}

	@Override
	public T mapRow(ResultSet resultSet, int i) throws SQLException {
		int id = resultSet.getInt("id");
		T result = data.getIfPresent(id);

		if (result == null) {
			return addToCache(id, resultSet);
		}

		return result;
	}

	private class RowMapperCacheLoader extends CacheLoader<Integer, T> {
		private ResultSet resultSet;

		@Override
		public T load(Integer id) throws Exception {
			return createObject(id, resultSet);
		}
	}
}
