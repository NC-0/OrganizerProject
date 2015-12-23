package organizer.dao.api;

import organizer.models.User;

public interface UserDao {
	boolean exist(String email);
	String create(User user);
	String delete(String email);
	String edit(User user);
	User get(String email);

	int OBJ_TYPE      = 3;
	int EMAIL_ATTR    = 6;
	int ENABLED_ATTR  = 11;
	int PASSWORD_ATTR = 7;
	int SURNAME_ATTR  = 8;

	String SELECT_COUNT = "SELECT COUNT(*) FROM attributes usr_attr WHERE usr_attr.attr_id = 6 AND usr_attr.value = ?";
	String SELECT_ID    = "SELECT object_id.CURRVAL FROM dual";

	String INSERT          = "INSERT INTO objects (parent_id, object_type_id, name, description) VALUES (NULL, " + OBJ_TYPE + ", ?,NULL)";
	String INSERT_EMAIL    = "INSERT INTO attributes (attr_id, object_id, value, date_value) VALUES (" + EMAIL_ATTR + ", ?, ?, NULL)";
	String INSERT_ENABLED  = "INSERT INTO attributes (attr_id,object_id,value,date_value) VALUES (" + ENABLED_ATTR + ", ?, ?, NULL)";
	String INSERT_PASSWORD = "INSERT INTO attributes (attr_id, object_id, value, date_value) VALUES (" + PASSWORD_ATTR + ", ?, ?, NULL)";
	String INSERT_SURNAME  = "INSERT INTO attributes (attr_id, object_id, value, date_value) VALUES (" + SURNAME_ATTR + ", ?, ?, NULL)";
	
	String SELECT_ID_BY_EMAIL = "SELECT object_ID FROM attributes WHERE attr_id = " + EMAIL_ATTR + " AND value = ?";
	String SELECT_NAME        = "SELECT name FROM objects WHERE object_id = ?";
	String SELECT_PASSWORD    = "SELECT value FROM attributes WHERE object_id = ? AND attr_id = " + PASSWORD_ATTR;
	String SELECT_SURNAME     = "SELECT value FROM attributes WHERE object_id = ? AND attr_id = " + SURNAME_ATTR;
}
