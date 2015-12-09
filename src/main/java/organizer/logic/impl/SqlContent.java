package organizer.logic.impl;

import org.springframework.jdbc.core.PreparedStatementCreator;

public class SqlContent {
	
	// User requests ----------------------------------------------------------------------------------------------------
	
	
	public final static String SELECT_USER_OBJECT_ID_BY_EMAIL = "select OBJECT_ID from ATTRIBUTES where ATTR_ID = 6 and NAME = ?"; 	
	
	public final static String SELECT_USER_ID = "SELECT object_id FROM objects WHERE name = ?";

	public final static String SELECT_USER_NAME = "select NAME from OBJECTS where OBJECT_ID = ?";			 

	public final static String SELECT_USER_PASS = "select VALUE from ATTRIBUTES where OBJECT_ID = ? and ATTR_ID = 7";		 		

	public final static String SELECT_USER_SURNAME = "select VALUE from ATTRIBUTES where OBJECT_ID = ? and ATTR_ID = 8";
	
	// Task requests -----------------------------------------------------------------------------------------------------
	
	public final static String INSERT_TASK = "INSERT INTO OBJECTS (OBJECT_ID, PARENT_ID, OBJECT_TYPE_ID, NAME, DESCRIPTION) VALUES (?, ?, 1, ?, NULL)"; /* task obj_type_id=1 */
	
	public final static String INSERT_TASK_DATE = "INSERT INTO ATTRIBUTES (ATTR_ID, OBJECT_ID, VALUE, DATE_VALUE) VALUES (1, ?, NULL, ?)";
			
	public final static String INSERT_TASK_PRIORITY = "INSERT INTO ATTRIBUTES (ATTR_ID, OBJECT_ID, VALUE, DATE_VALUE) VALUES (2, ?, ?, NULL)";
	
	public final static String INSERT_TASK_CATEGORY = "INSERT INTO ATTRIBUTES (ATTR_ID, OBJECT_ID, VALUE, DATE_VALUE) VALUES (3, ?, ?, NULL)";
	
	public final static String INSERT_TASK_STATUS = "INSERT INTO ATTRIBUTES (ATTR_ID, OBJECT_ID, VALUE, DATE_VALUE) VALUES (4, ?, ?, NULL)";
	
	public final static String GET_OBJECT_ID_BY_EMAIL ="SELECT object_id FROM attributes WHERE value = ?";

	public final static String DELETE_USER_OBJECT = "DELETE objects WHERE object_id = ? ";

	public final static String DELETE_USER_ATTRIBUTES = "DELETE attributes WHERE object_id = ? ";

	public final static String SELECT_TASK_BY_OBJECT_ID = "SELECT name FROM objects WHERE object_id = ? ";

	public final static String UPDATE_TASK_NAME ="UPDATE objects SET name = ? WHERE object_id = ?";

	public final static String UPDATE_TASK_DATE ="UPDATE attributes SET date_value = ? WHERE attr_id = 1 and object_id = ?";

	public final static String UPDATE_TASK_PRIORITY ="UPDATE attributes SET value = ? WHERE attr_id = 2 and object_id = ?";

	public final static String UPDATE_TASK_CATEGORY ="UPDATE attributes SET value = ? WHERE attr_id = 3 and object_id = ?";

	public final static String UPDATE_TASK_STATUS ="UPDATE attributes SET value = ? WHERE attr_id = 4 and object_id = ?";

	// Subtask requests --------------------------------------------------------------------------------------------------
		
	public static final String SELECT_SUBTASKS_BY_TASK_ID = "SELECT object_id, o.name as SubtaskName, a.value as Status FROM objects o "
														+ "LEFT JOIN attributes a ON (o.object_id=a.object_id) WHERE parent_id = ?";
	
	public static final String UPDATE_SUBTASK_NAME = "UPDATE objects SET name = ? WHERE object_id = ?";

	// Category requests -------------------------------------------------------------------------------------------------
	
	public final static String SELECT_CATEGORY_ID_BY_NAME = "SELECT object_id FROM objects WHERE name = ?";
	
	public final static String UPDATE_CATEGORY_NAME = "UPDATE objects set NAME = ? where object_id = ?";
	
	public final static String INSERT_CATEGORY_OBJECT = "INSERT INTO objects(object_id,parent_id,object_type_id,name,description) VALUES (?,NULL,4,?,NULL)";

	public final static String INSERT_USER_TO_CATEGORY_REFERENCE = "INSERT INTO objreference (attr_id,object_id,reference) VALUES (9,?,?)"; //INSERT INTO OBJREFERENCE(ATTR_ID,OBJECT_ID,REFERENCE) VALUES (9,65,67) 65-user object, 67-category object
	
	public final static String SELECT_USER_CATEGORIES = "SELECT cat_obj.NAME FROM OBJECTS cat_obj,OBJECTS usr_obj,OBJREFERENCE usr_cat_ref WHERE usr_cat_ref.ATTR_ID=9 AND cat_obj.OBJECT_ID=usr_cat_ref.REFERENCE AND usr_obj.OBJECT_ID=usr_cat_ref.OBJECT_ID AND usr_obj.OBJECT_ID=?";

}
