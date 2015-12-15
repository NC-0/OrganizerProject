package organizer.dao.api;

import organizer.models.Task;

import java.util.List;

public interface TaskDao {
	void create(int userId, Task task);
	void delete(int id);
	void edit(Task task);
	Task get();
	List<Task> getSubtaskList();

	int OBJ_TYPE      = 1;
	int OBJ_TYPE_SUBTASKS = 2;
	int DATE_ATTR     = 1;
	int PRIORITY_ATTR = 2;
	int CATEGORY_ATTR = 3;
	int STATUS_ATTR   = 4;
	int USER_REF_ATTR = 10;

	// Create Task requests
	String INSERT          = "INSERT INTO OBJECTS (PARENT_ID, OBJECT_TYPE_ID, NAME, DESCRIPTION) VALUES (NULL, " + OBJ_TYPE + ", ?, NULL)";
	String INSERT_DATE     = "INSERT INTO ATTRIBUTES (ATTR_ID, OBJECT_ID, VALUE, DATE_VALUE) VALUES (" + DATE_ATTR + ", ?, NULL, ?)";
	String INSERT_PRIORITY = "INSERT INTO ATTRIBUTES (ATTR_ID, OBJECT_ID, VALUE, DATE_VALUE) VALUES (" + PRIORITY_ATTR + ", ?, ?, NULL)";
	String INSERT_CATEGORY = "INSERT INTO ATTRIBUTES (ATTR_ID, OBJECT_ID, VALUE, DATE_VALUE) VALUES (" + CATEGORY_ATTR + ", ?, ?, NULL)";
	String INSERT_STATUS   = "INSERT INTO ATTRIBUTES (ATTR_ID, OBJECT_ID, VALUE, DATE_VALUE) VALUES (" + STATUS_ATTR + ", ?, ?, NULL)";
	String INSERT_REF_USER = "INSERT INTO OBJREFERENCE (ATTR_ID,  REFERENCE, OBJECT_ID) VALUES (" + USER_REF_ATTR + ", ?, ?)";
	
	// Delete Task requests
	String DELETE = "DELETE FROM objects WHERE object_id = ?";

	// Update Task requests

	// Get Task requests
	String SELECT_NAME = "SELECT name FROM objects WHERE object_type_id =" + OBJ_TYPE + " AND object_id=?";
	String SELECT_DATE 	= "SELECT date_value FROM ATTRIBUTES WHERE attr_id = " + DATE_ATTR + " AND object_id=?";
	String SELECT_PRIORITY 	= "SELECT value FROM ATTRIBUTES WHERE attr_id = " + PRIORITY_ATTR + " AND object_id=?";
	String SELECT_CATEGORY 	= "SELECT value FROM ATTRIBUTES WHERE attr_id = " + CATEGORY_ATTR + " AND object_id = ?";
	String SELECT_STATUS 	= "SELECT value FROM ATTRIBUTES WHERE attr_id = " + STATUS_ATTR +" AND object_id=?";
	String SELECT_SUBTASKS_NAMES = "SELECT name FROM objects WHERE object_type_id = "+ OBJ_TYPE_SUBTASKS +" AND parent_id=?";
}
