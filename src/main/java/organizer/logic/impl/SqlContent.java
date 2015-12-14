package organizer.logic.impl;

import org.springframework.jdbc.core.PreparedStatementCreator;

public class SqlContent {
	
	// User requests ----------------------------------------------------------------------------------------------------
	
	

	
	// Task requests -----------------------------------------------------------------------------------------------------
	

	
	public final static String GET_OBJECT_ID_BY_EMAIL ="SELECT object_id FROM attributes WHERE value = ?";

	public final static String DELETE_USER_OBJECT = "DELETE objects WHERE object_id = ? ";

	public final static String DELETE_USER_ATTRIBUTES = "DELETE attributes WHERE object_id = ? ";

	public final static String SELECT_TASK_BY_OBJECT_ID = "SELECT name FROM objects WHERE object_id = ? ";

	public final static String UPDATE_TASK_NAME ="UPDATE objects SET name = ? WHERE object_id = ?";

	public final static String UPDATE_TASK_DATE ="UPDATE attributes SET date_value = ? WHERE attr_id = 1 and object_id = ?";

	public final static String UPDATE_TASK_PRIORITY ="UPDATE attributes SET value = ? WHERE attr_id = 2 and object_id = ?";

	public final static String UPDATE_TASK_CATEGORY ="UPDATE attributes SET value = ? WHERE attr_id = 3 and object_id = ?";

	public final static String UPDATE_TASK_STATUS ="UPDATE attributes SET value = ? WHERE attr_id = 4 and object_id = ?";
	
	
	public final static String SELECT_TASK_OBJECT_BY_DATE = "SELECT object_id FROM ATTRIBUTES WHERE attr_id = 1  AND date_value = to_date(?, 'dd.mm.yyyy')";
	public final static String SELECT_TASK_NAME = "SELECT name FROM objects WHERE object_type_id = 1 AND object_id = ?";
	public final static String SELECT_TASK_PRIORITY = "SELECT value FROM attributes WHERE attr_id = 2 AND object_id = ?";
	public final static String SELECT_TASK_CATEGORY = "SELECT value FROM attributes WHERE attr_id = 3 AND object_id = ?";
	public final static String SELECT_TASK_STATUS = "SELECT value FROM attributes WHERE attr_id = 4 AND object_id = ?";
	

	// Subtask requests --------------------------------------------------------------------------------------------------
		
	public static final String SELECT_SUBTASKS_BY_TASK_ID = "SELECT object_id, o.name as SubtaskName, a.value as Status FROM objects o "
														+ "LEFT JOIN attributes a ON (o.object_id=a.object_id) WHERE parent_id = ?";
	
	public static final String UPDATE_SUBTASK_NAME = "UPDATE objects SET name = ? WHERE object_id = ?";

	// Category requests -------------------------------------------------------------------------------------------------
	
	public final static String SELECT_CATEGORY_ID_BY_NAME = "SELECT object_id FROM objects WHERE name = ?";
	
	public final static String UPDATE_CATEGORY_NAME = "UPDATE objects set NAME = ? where object_id = ?";
	
	public final static String INSERT_CATEGORY_OBJECT = "INSERT INTO objects(object_id,parent_id,object_type_id,name,description) VALUES (?,NULL,4,?,NULL)";

	public final static String INSERT_USER_TO_CATEGORY_REFERENCE = "INSERT INTO objreference (attr_id,object_id,reference) VALUES (9,?,?)"; //INSERT INTO OBJREFERENCE(ATTR_ID,OBJECT_ID,REFERENCE) VALUES (9,65,67) 65-user object, 67-category object
	
}
