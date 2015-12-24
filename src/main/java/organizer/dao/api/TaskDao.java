package organizer.dao.api;

import organizer.models.Task;

import java.util.List;

public interface TaskDao {
	void create(int userId, Task task);
	void delete(int id);
	void edit(Task task);
	List<Task> get(int userId);
	List<Task> getSubtaskList();

	int OBJ_TYPE      = 1;
	int OBJ_TYPE_SUBTASKS = 2;
	int DATE_ATTR     = 1;
	int PRIORITY_ATTR = 2;
	int CATEGORY_ATTR = 3;
	int STATUS_ATTR   = 4;
	int USER_REF_ATTR = 10;
	int SUBTASK_ATTR_TYPE = 5;
	int TASK_REF_SUBTASK = 12;
	int OBJTYPE_CATEGORY = 4;

	// Create Task requests
	String INSERT          = "INSERT INTO OBJECTS (PARENT_ID, OBJECT_TYPE_ID, NAME, DESCRIPTION) VALUES (NULL, " + OBJ_TYPE + ", ?, NULL)";
	String INSERT_DATE     = "INSERT INTO ATTRIBUTES (ATTR_ID, OBJECT_ID, VALUE, DATE_VALUE) VALUES (" + DATE_ATTR + ", ?, NULL, ?)";
	String INSERT_PRIORITY = "INSERT INTO ATTRIBUTES (ATTR_ID, OBJECT_ID, VALUE, DATE_VALUE) VALUES (" + PRIORITY_ATTR + ", ?, ?, NULL)";
	String INSERT_CATEGORY = "INSERT INTO ATTRIBUTES (ATTR_ID, OBJECT_ID, VALUE, DATE_VALUE) VALUES (" + CATEGORY_ATTR + ", ?, ?, NULL)";
	String INSERT_STATUS   = "INSERT INTO ATTRIBUTES (ATTR_ID, OBJECT_ID, VALUE, DATE_VALUE) VALUES (" + STATUS_ATTR + ", ?, ?, NULL)";
	String INSERT_REF_USER = "INSERT INTO OBJREFERENCE (ATTR_ID,  REFERENCE, OBJECT_ID) VALUES (" + USER_REF_ATTR + ", ?, ?)";
	
	// Delete Task requests
	String DELETE = "DELETE FROM objects WHERE object_id = ? AND object_type_id = " + OBJ_TYPE;

	// Update Task requests
	String UPDATE_NAME = "UPDATE objects SET name = ? WHERE object_id = ?";
	String UPDATE_DATE = "UPDATE attributes SET date_value = ? WHERE attr_id = " + DATE_ATTR +" and object_id = ?";
	String UPDATE_PRIORITY = "UPDATE attributes SET value = ? WHERE attr_id = " + PRIORITY_ATTR +" and object_id = ?";
	String UPDATE_CATEGORY = "UPDATE attributes SET value = ? WHERE attr_id = " + CATEGORY_ATTR +" and object_id = ?";
	String UPDATE_STATUS = "UPDATE attributes SET value = ? WHERE attr_id = " + STATUS_ATTR +" and object_id = ?";
	String UPDATE_SUBTASK_STATUS = "UPDATE attributes SET value = 1 WHERE attr_id =" + SubtaskDao.IS_COMPLETED_ATTR +
			" AND object_id IN (SELECT object_id FROM objects WHERE parent_id = ?)";


	// Get Task requests
	String SELECT_LIST_OF_USER_TASKS = "SELECT oref1.REFERENCE, obj1.NAME, a1.date_value, a2.VALUE AS priority, "
			+ "a4.VALUE AS status , oref2.REFERENCE AS category_id, obj2.NAME category_name FROM objreference oref1 "
			+ "JOIN objects obj1 ON oref1.REFERENCE=obj1.object_id, "
			+ "ATTRIBUTES a1, ATTRIBUTES a2, objreference oref2, objects obj2, ATTRIBUTES a4 WHERE  a1.date_value IS NOT NULL AND "
			+ "a1.attr_id = "+ DATE_ATTR +" AND a1.object_id=obj1.object_id AND a2.VALUE IS NOT NULL AND "
			+ "a2.attr_id = "+ PRIORITY_ATTR +" AND a2.object_id = obj1.object_id AND "
			+ "oref2.attr_id = "+ TASK_REF_SUBTASK +" AND oref2.object_id=oref1.REFERENCE AND "
			+ "obj2.object_id = (SELECT oref3.REFERENCE FROM objreference oref3 "
			+ "WHERE oref3.attr_id = "+ TASK_REF_SUBTASK + " AND oref3.object_id=oref1.REFERENCE) "
			+ "AND obj2.object_type_id = " + OBJTYPE_CATEGORY + " AND a4.attr_id = "+ STATUS_ATTR
			+ " AND a4.object_id = obj1.object_id AND oref1.attr_id = " + USER_REF_ATTR + " AND oref1.object_id = ? ";
	
	String SELECT_SUBTASKS_BY_TASK_ID = "SELECT obj.object_id, obj.name, attr.value FROM objects obj "
			+ "JOIN  ATTRIBUTES attr ON obj.object_id=attr.object_id WHERE obj.object_type_id = "+ OBJ_TYPE_SUBTASKS 
			+ " AND obj.parent_id= ? AND attr.attr_id = "+ SUBTASK_ATTR_TYPE;
}
