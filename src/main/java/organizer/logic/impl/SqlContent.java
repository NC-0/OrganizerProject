package organizer.logic.impl;

import org.springframework.jdbc.core.PreparedStatementCreator;

public class SqlContent {

	public final static String SELECT_USER_COUNT = "SELECT count(*) FROM ATTRIBUTES USR_ATTR WHERE USR_ATTR.ATTR_ID=6 AND USR_ATTR.VALUE=?";

	public final static String SELECT_NEXT_OBJECT_ID_VALUE = "SELECT object_id.nextval FROM dual";

	public final static String INSERT_USER_OBJECT = "INSERT INTO objects(object_id,parent_id,object_type_id,name,description) VALUES(?,NULL,3,?,NULL)";

	public final static String INSERT_USER_EMAIL_ATTRIBUTE = "INSERT INTO attributes(attr_id,object_id,value,date_value) VALUES(6,?,?,null)";

	public final static String INSERT_USER_PASSWORD_ATTRIBUTE = "INSERT INTO attributes(attr_id,object_id,value,date_value) VALUES(7,?,?,null)";

	public final static String INSERT_USER_SURNAME_ATTRIBUTE = "INSERT INTO attributes(attr_id,object_id,value,date_value) VALUES(8,?,?,null)";


	public final static String GET_OBJECT_ID_BY_EMAIL ="SELECT object_id FROM attributes WHERE value = ?";

	public final static String DELETE_USER_OBJECT = "DELETE objects WHERE object_id = ? ";

	public final static String DELETE_USER_ATTRIBUTES = "DELETE attributes WHERE object_id = ? ";

	public final static String SELECT_TASK_BY_OBJECT_ID = "SELECT name FROM objects WHERE object_id = ? ";

	public final static String UPDATE_TASK_NAME ="UPDATE objects SET name = ? WHERE object_id = ?";

	public final static String UPDATE_TASK_DATE ="UPDATE attributes SET date_value = ? WHERE attr_id = 1 and object_id = ?";

	public final static String UPDATE_TASK_PRIORITY ="UPDATE attributes SET value = ? WHERE attr_id = 2 and object_id = ?";

	public final static String UPDATE_TASK_CATEGORY ="UPDATE attributes SET value = ? WHERE attr_id = 3 and object_id = ?";

	public final static String UPDATE_TASK_STATUS ="UPDATE attributes SET value = ? WHERE attr_id = 4 and object_id = ?";


	public static final String UPDATE_SUBTASK_NAME = "UPDATE objects SET name = ? WHERE object_id = ?";


	public final static String INSERT_CATEGORY_OBJECT = "INSERT INTO objects(object_id,parent_id,object_type_id,name,description) VALUES (?,NULL,4,?,NULL)";

	public final static String INSERT_USER_TO_CATEGORY_REFERENCE = "INSERT INTO objreference (attr_id,object_id,reference) VALUES (9,?,?)";

	;
}
