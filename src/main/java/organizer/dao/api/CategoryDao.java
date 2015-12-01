package organizer.dao.api;

import organizer.models.Category;

public interface CategoryDao {
	boolean create(Category category);
	void delete();
	void edit();
	Category get();
}
