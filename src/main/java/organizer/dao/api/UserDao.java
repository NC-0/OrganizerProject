package organizer.dao.api;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.support.TransactionTemplate;

import organizer.models.User;

public interface UserDao {
	boolean exist(String email);
	String create(User user);
	String delete(String email);
	String edit(User user);
	User get(String email);

	final static String SELECT_USER_COUNT = "SELECT count(*) FROM ATTRIBUTES USR_ATTR WHERE USR_ATTR.ATTR_ID=6 AND USR_ATTR.VALUE=?";
	final static String SELECT_CURR_OBJECT_ID_VALUE = "SELECT object_id.currval FROM dual";
	final static String INSERT_USER_OBJECT = "INSERT INTO objects(parent_id,object_type_id,name,description) VALUES(NULL,3,?,NULL)";
	final static String INSERT_USER_EMAIL_ATTRIBUTE = "INSERT INTO attributes(attr_id,object_id,value,date_value) VALUES(6,?,?,null)";
	final static String INSERT_USER_PASSWORD_ATTRIBUTE = "INSERT INTO attributes(attr_id,object_id,value,date_value) VALUES(7,?,?,null)";
	final static String INSERT_USER_SURNAME_ATTRIBUTE = "INSERT INTO attributes(attr_id,object_id,value,date_value) VALUES(8,?,?,null)";
}
