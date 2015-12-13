package organizer.dao.api;

import organizer.models.Category;
import organizer.models.User;

import java.util.List;

public interface CategoryDao {
	void create(Category category);
	void create(User user, Category category);
	void delete();
	void edit(Category category);
	List<Category> get(int userId);

	final static String SELECT_USER_CATEGORIES = "SELECT cat_obj.NAME FROM objects cat_obj, OBJECTS usr_obj,objreference usr_cat_ref WHERE usr_cat_ref.ATTR_ID=9 AND cat_obj.OBJECT_ID=usr_cat_ref.REFERENCE AND usr_obj.OBJECT_ID=usr_cat_ref.OBJECT_ID AND usr_obj.OBJECT_ID=?";
}
