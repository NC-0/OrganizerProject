package organizer.dao.api;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import organizer.models.Category;
import organizer.models.User;

import java.util.List;

@Component
@Scope("prototype")
public interface CategoryDao {
	void create(Category category);
	void delete(Category category);
	void update(Category category);
	List<Category> get(User user);

	int OBJ_TYPE      = 4;

//	String SELECT_ID = UserDao.SELECT_ID;

	String SELECT_BY_USER_ID = (
		"SELECT object_id AS id, name FROM objects " +
		"WHERE parent_id = ? " +
		"AND object_type_id = " + OBJ_TYPE
	);

	String UPDATE   = (
		"UPDATE objects SET name = ? " +
		"WHERE object_id = ? " +
		"AND object_type_id = " + OBJ_TYPE
	);

	String INSERT  = (
		"INSERT INTO objects (" +
			"parent_id, " +
			"object_type_id, " +
			"name, " +
			"description" +
		") VALUES (NULL, " + OBJ_TYPE + ", ?, NULL)"
	);

	String DELETE = "DELETE FROM objects WHERE object_id = ? AND object_type_id = " + OBJ_TYPE;
}
