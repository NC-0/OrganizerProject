package organizer.dao.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class CachedMapper<T> implements RowMapper<T> {

	protected CacheImpl<T> cache;

	public void setCacheImpl(CacheImpl cache){
		this.cache=cache;
	};

	public abstract T createObject(int id, ResultSet resultSet) throws SQLException;

	protected T addToCache(Integer id,ResultSet resultSet) throws SQLException{
		T object = createObject(id,resultSet);
		cache.add(id,object);
		return object;
	}

	@Override
	public T mapRow(ResultSet resultSet, int i) throws SQLException {
			Integer id = resultSet.getInt("id");
			T cachedObject = cache.get(id);
			if (cachedObject == null) {
				cachedObject = addToCache(id, resultSet);
			}
		return cachedObject;
	}

}
