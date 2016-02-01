package organizer.dao.cache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

public class CacheImpl<T>{

	private final int CACHE_SIZE = 1000000;

	private Cache<Integer,T> cache;

	CacheImpl() {
		cache = CacheBuilder
			.newBuilder()
			.maximumSize(CACHE_SIZE)
			.build();
	}

	public T get(int id)  {
		return cache.getIfPresent(id);
	}

	public T add(int id, T object){
		cache.put(id,object);
		return object;
	}

}