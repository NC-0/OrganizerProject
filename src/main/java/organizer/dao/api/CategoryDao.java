package organizer.dao.api;

import organizer.models.Category;
import organizer.models.User;

import java.util.List;

public interface CategoryDao {
	void create(Category category);
	void delete(Category category);
	void update(Category category);
	List<Category> get(User user);

	int OBJ_TYPE = 4;
	int REF_ATTR = 12;

	String INSERT_REF = (
		"INSERT INTO objreference (" +
		"attr_id," +
		"object_id," +
		"reference " +
		") VALUES ("+REF_ATTR+",?,?)"
	);

	String INSERT_OBJECT = (
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


	String DELETE_OBJECT =
			"DELETE FROM " +
					"objects " +
			"WHERE " +
					"object_id = ? " +
					"AND object_type_id = " + OBJ_TYPE;
	String DELETE_REF =
			"DELETE FROM " +
					"OBJREFERENCE " +
			"WHERE " +
					"reference = ? " +
					"AND attr_id = " + REF_ATTR;


	String SELECT_ID = UserDao.SELECT_ID;
	String SELECT_BY_USER_ID =
			"SELECT " +
					" obj.object_id as id," +
					" obj.name as name" +
					" FROM " +
					" objects obj," +
					" OBJREFERENCE ref" +
					" WHERE " +
					" ref.object_id = ? " +
					" AND obj.object_id = ref.reference" +
					" AND ref.attr_id = " + REF_ATTR +
					" AND object_type_id = " + OBJ_TYPE;
}