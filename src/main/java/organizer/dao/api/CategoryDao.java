package organizer.dao.api;

import organizer.models.Category;
import organizer.models.User;

public interface CategoryDao {
	void create(Category category);
	void create(User user, Category category);
	void delete();
	void edit();
	Category get();
}
