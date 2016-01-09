package organizer.dao.api;

import organizer.models.Category;
import organizer.models.User;

import java.util.List;

public interface CategoryDao {
	void create(Category category);
	void delete(Category category);
	void update(Category category);
	List<Category> get(User user);

	int OBJ_TYPE      = 4;
	int REF_ATTR 		= 12;

	String SELECT_USER_CATEGORIES = (
		"SELECT cat_obj.object_id AS id, " +
			"cat_obj.NAME FROM objects cat_obj, " +
			"OBJECTS usr_obj," +
			"objreference usr_cat_ref " +
			"WHERE usr_cat_ref.ATTR_ID="+REF_ATTR+" AND " +
			"cat_obj.OBJECT_ID=usr_cat_ref.REFERENCE AND " +
			"usr_obj.OBJECT_ID=usr_cat_ref.OBJECT_ID AND " +
			"usr_obj.OBJECT_ID=?"
	);

	String INSERT_REF = (
		"INSERT INTO objreference (" +
		"attr_id," +
		"object_id," +
		"reference " +
		") VALUES ("+REF_ATTR+",?,?)"
	);

	String INSERT = (
		"INSERT INTO objects(" +
			"parent_id," +
			"object_type_id," +
			"name," +
			"description" +
			") VALUES (NULL,"+OBJ_TYPE+",?,NULL)"
	);

	String UPDATE   = (
		"UPDATE objects SET name = ? " +
			"WHERE object_id = ? " +
			"AND object_type_id = " + OBJ_TYPE
	);

	String DELETE = (
		"DELETE FROM objects " +
			"WHERE object_id = ? " +
			"AND object_type_id = " + OBJ_TYPE
	);
}