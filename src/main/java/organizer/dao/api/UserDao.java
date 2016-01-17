package organizer.dao.api;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import organizer.logic.impl.email.MailTasks;
import organizer.models.User;

import java.util.List;

public interface UserDao {
	boolean exist(String email);
	boolean exist(int id);
	String create(User user);
	void delete(int id);
	void edit(User user);
	void editPassword(User user);
	boolean existVerify(String verificationId);
	User verify(String verificationId);
	User get(String email);
	User get(int id);
	MailTasks getTommorowTasks();

	int OBJ_TYPE      = 3;
	int EMAIL_ATTR    = 6;
	int ENABLED_ATTR  = 11;
	int PASSWORD_ATTR = 7;
	int SURNAME_ATTR  = 8;
	int VERIFY_ATTR = 9;

	String SELECT_COUNT_EMAIL  = "SELECT COUNT(*) FROM attributes usr_attr WHERE usr_attr.attr_id = " + EMAIL_ATTR + " AND usr_attr.value = ?";
	String SELECT_COUNT_ID 	   = "SELECT COUNT(*) FROM objects usr_obj WHERE usr_obj.object_id = ? AND usr_obj.object_type_id=" + OBJ_TYPE;
	String SELECT_ID       		= "SELECT object_id.CURRVAL FROM dual";
	String SELECT_VERIFY			= "SELECT COUNT(*) FROM attributes usr_attr WHERE usr_attr.attr_id = " + VERIFY_ATTR + " AND usr_attr.value =?";
	String SELECT_USER_ID		= "SELECT usr_attr.OBJECT_ID FROM attributes usr_attr WHERE usr_attr.attr_id = " + VERIFY_ATTR + " AND usr_attr.value =?";

	String INSERT          	= "INSERT INTO objects (parent_id, object_type_id, name, description) VALUES (NULL, " + OBJ_TYPE + ", ?,NULL)";
	String INSERT_EMAIL    	= "INSERT INTO attributes (attr_id, object_id, value, date_value) VALUES (" + EMAIL_ATTR + ", ?, ?, NULL)";
	String INSERT_ENABLED  	= "INSERT INTO attributes (attr_id,object_id,value,date_value) VALUES (" + ENABLED_ATTR + ", ?, ?, NULL)";
	String INSERT_PASSWORD 	= "INSERT INTO attributes (attr_id, object_id, value, date_value) VALUES (" + PASSWORD_ATTR + ", ?, ?, NULL)";
	String INSERT_SURNAME  	= "INSERT INTO attributes (attr_id, object_id, value, date_value) VALUES (" + SURNAME_ATTR + ", ?, ?, NULL)";
	String INSERT_VERIFY  	= "INSERT INTO attributes (attr_id, object_id, value, date_value) VALUES (" + VERIFY_ATTR + ", ?, ?, ?)";

	String UPDATE_NAME		= "UPDATE objects usr_obj SET usr_obj.name = ? WHERE usr_obj.object_id = ?";
	String UPDATE_SURNAME 	= "UPDATE attributes usr_attr SET usr_attr.value = ? WHERE usr_attr.attr_id = " + SURNAME_ATTR + "  and usr_attr.object_id = ?";
	String UPDATE_PASSWORD 	= "UPDATE attributes usr_attr SET usr_attr.value = ? WHERE usr_attr.attr_id = " + PASSWORD_ATTR + "  and usr_attr.object_id = ?";
	String UPDATE_ENABLED	= "UPDATE attributes usr_attr SET usr_attr.value = ? WHERE usr_attr.attr_id = " + ENABLED_ATTR + "  and usr_attr.object_id = ?";

	String DELETE_OBJECT 					= "DELETE objects WHERE object_id = ? AND object_type_id = " + OBJ_TYPE;
	String DELETE_OBJECTS_REF_T0_USER	= "DELETE objects WHERE object_id IN (SELECT reference FROM objreference WHERE object_id = ?)";

	String SELECT_USER_BY_EMAIL = "SELECT " +
		"verify_attr.VALUE as verify," +
		"verify_attr.DATE_VALUE as regdate ," +
		"obj.OBJECT_ID as id, " +
		"obj.NAME as username, " +
		"pass_attr.VALUE as password, " +
		"email_attr.VALUE as email, " +
		"surname_attr.VALUE as surname, " +
		"enable_attr.VALUE as enabled " +
		"FROM  " +
		"attributes verify_attr," +
		"attributes email_attr, " +
		"attributes pass_attr, " +
		"attributes surname_attr, " +
		"attributes enable_attr, " +
		"objects obj " +
		"WHERE " +
		"verify_attr.attr_id=" + VERIFY_ATTR + " AND " +
		"email_attr.attr_id = " + EMAIL_ATTR + " AND " +
		"pass_attr.attr_id = " + PASSWORD_ATTR + " AND " +
		"surname_attr.attr_id = " + SURNAME_ATTR + " AND " +
		"enable_attr.attr_id = " + ENABLED_ATTR + " AND " +
		"email_attr.value = ? AND " +
		"obj.object_id = email_attr.OBJECT_ID AND " +
		"pass_attr.OBJECT_ID = email_attr.OBJECT_ID " +
		"AND surname_attr.object_id = email_attr.OBJECT_ID AND " +
		"enable_attr.object_id = email_attr.OBJECT_ID AND " +
		"verify_attr.OBJECT_ID=email_attr.OBJECT_ID";

	String SELECT_USER_BY_ID = "SELECT " +
		"verify_attr.VALUE as verify," +
		"verify_attr.DATE_VALUE as regdate ," +
		"obj.OBJECT_ID as id, " +
		"obj.NAME as username, " +
		"pass_attr.VALUE as password, " +
		"email_attr.VALUE as email, " +
		"surname_attr.VALUE as surname, " +
		"enable_attr.VALUE as enabled " +
		"FROM  " +
		"attributes verify_attr," +
		"attributes email_attr, " +
		"attributes pass_attr, " +
		"attributes surname_attr, " +
		"attributes enable_attr, " +
		"objects obj " +
		"WHERE " +
		"verify_attr.attr_id=" + VERIFY_ATTR + " AND " +
		"email_attr.attr_id = " + EMAIL_ATTR + " AND " +
		"pass_attr.attr_id = " + PASSWORD_ATTR + " AND " +
		"surname_attr.attr_id = " + SURNAME_ATTR + " AND " +
		"enable_attr.attr_id = " + ENABLED_ATTR + " AND " +
		"obj.object_id = ? AND " +
		"obj.object_id = email_attr.OBJECT_ID AND " +
		"pass_attr.OBJECT_ID = email_attr.OBJECT_ID " +
		"AND surname_attr.object_id = email_attr.OBJECT_ID AND " +
		"enable_attr.object_id = email_attr.OBJECT_ID AND " +
		"verify_attr.OBJECT_ID=email_attr.OBJECT_ID";

	String SELECT_USERS_AND_TASKS ="SELECT " +
		"task_obj.name as task, " +
		"usr_attr.value as mail " +
		"FROM " +
		"objects task_obj, " +
		"objects usr_obj, " +
		"ATTRIBUTES usr_attr, " +
		"objreference ref " +
		"WHERE " +
		"task_obj.OBJECT_TYPE_ID=1 AND " +
		"usr_obj.OBJECT_TYPE_ID=3 AND " +
		"usr_attr.ATTR_ID=6 AND " +
		"usr_attr.OBJECT_ID=usr_obj.OBJECT_ID AND " +
		"ref.ATTR_ID=10 AND " +
		"ref.OBJECT_ID=usr_obj.OBJECT_ID AND " +
		"ref.REFERENCE=task_obj.OBJECT_ID";
}