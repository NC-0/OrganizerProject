package organizer.dao.api;

import organizer.models.User;

public interface UserDao {
	boolean exist(String email);
	String create(User user);
	void delete(int id);
	String edit(User user);
	User get(String email);
	User get(int id);

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

	String DELETE_OBJECT = "DELETE objects WHERE object_id = ? AND object_type_id = " + OBJ_TYPE;
	String DELETE_OBJECTS_REF_T0_USER = "DELETE objects WHERE object_id IN (SELECT reference FROM objreference WHERE object_id = ?)";

//	String SELECT_ID_BY_EMAIL = "SELECT object_ID FROM attributes WHERE attr_id = " + EMAIL_ATTR + " AND value = ?";
//	String SELECT_NAME        = "SELECT name FROM objects WHERE object_id = ?";
//	String SELECT_PASSWORD    = "SELECT value FROM attributes WHERE object_id = ? AND attr_id = " + PASSWORD_ATTR;
//	String SELECT_SURNAME     = "SELECT value FROM attributes WHERE object_id = ? AND attr_id = " + SURNAME_ATTR;
	
	String GET_USER_INFO = "SELECT " +
		"obj.OBJECT_ID as userid, " +		// user id
		"obj.NAME as username, " + 		// user name
		"pass_attr.VALUE as password, " +	// password
		"email_attr.VALUE as email, "+		// email
		"surname_attr.VALUE as surname, " +	// surname
		"enable_attr.VALUE as enabled " +	// enabled
		"FROM  attributes email_attr, " +
		"attributes pass_attr, " +
		"attributes surname_attr, " +
		"attributes enable_attr, " +
		"objects obj " +
		"WHERE " +
		"email_attr.attr_id = " + EMAIL_ATTR + " AND " +
		"pass_attr.attr_id = " + PASSWORD_ATTR + " AND " +
		"surname_attr.attr_id = " + SURNAME_ATTR + " AND " +
		"enable_attr.attr_id = " + ENABLED_ATTR + " AND " +
		"email_attr.value = ? AND " +
		"obj.object_id = email_attr.OBJECT_ID AND " +
		"pass_attr.OBJECT_ID = email_attr.OBJECT_ID AND " +
		"surname_attr.object_id = email_attr.OBJECT_ID AND " +
		"enable_attr.object_id = email_attr.OBJECT_ID";
}
