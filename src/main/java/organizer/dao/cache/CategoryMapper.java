//package organizer.dao.cache;
//
//import organizer.models.Category;
//
//import java.sql.ResultSet;
//import java.sql.SQLException;
//
//public class CategoryMapper extends CachedMapper<Category> {
//
//	public CategoryMapper(CacheImpl cache) {
//		setCacheImpl(cache);
//	}
//
//	@Override
//	public Category createObject(int id, ResultSet resultSet) throws SQLException {
//		Category category = new Category(
//			resultSet.getString("name")
//		);
//		category.setId(id);
//		return category;
//	}
//}

package organizer.dao.cache;

import organizer.models.Category;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CategoryMapper extends CachedMapper<Category> {

	public CategoryMapper(CacheImpl cache) {
		setCacheImpl(cache);
	}

	@Override
	public Category createObject(int id, ResultSet resultSet) throws SQLException {
		Category category = new Category(
			resultSet.getString("name")
		);
		category.setId(id);
		return category;
	}
}