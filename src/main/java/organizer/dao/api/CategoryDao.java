package organizer.dao.api;

import organizer.models.Category;
import organizer.models.User;

import java.util.List;

public interface CategoryDao {
	void create(Category category);
	void delete(Category category);
	void update(Category category);
	List<Category> get(User user);
	Category get(int id,User user);

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

	String SELECT_CATEGORY_BY_ID =
		"SELECT "+
		"cat_obj.NAME as name,"+
		"cat_obj.OBJECT_ID as id "+
		"FROM "+
		"objects cat_obj, "+
		"objects usr_obj, "+
		"objreference reff "+
		"WHERE "+
		"cat_obj.OBJECT_ID=? AND "+
		"cat_obj.OBJECT_TYPE_ID=4 AND "+
		"usr_obj.OBJECT_ID = ? AND "+
		"usr_obj.OBJECT_TYPE_ID = 3 AND "+
		"reff.ATTR_ID=12 AND "+
		"reff.REFERENCE=cat_obj.OBJECT_ID AND "+
		"reff.OBJECT_ID=usr_obj.OBJECT_ID";

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

	String DELETE_REF =
		"DELETE FROM objects " +
			"WHERE object_id " +
			"IN (" +
			"SELECT reference " +
			"FROM objreference " +
			"WHERE object_id = ?)";

}