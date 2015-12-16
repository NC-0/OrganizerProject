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

	// Get Task requests
	String SELECT_LIST_OF_USER_TASKS = "Select objreference.reference, objects.NAME, a1.date_value, a2.value as priority, a3.value as category,a4.value as status from objreference"
			+ " join objects on objreference.REFERENCE=objects.object_id, attributes a1, ATTRIBUTES a2, "
			+ "attributes a3 , attributes a4 where  a1.date_value is not null "
			+ "and a1.attr_id=1 and a1.object_id=objects.object_id and a2.VALUE is not null "
			+ "and a2.attr_id=2 and a2.object_id = objects.object_id and a3.VALUE is not null "
			+ "and a3.attr_id =3 and a3.object_id=objects.object_id and a4.VALUE is not null "
			+ "and a4.attr_id =4 and a4.object_id=objects.object_id and objreference.attr_id = " + USER_REF_ATTR + " and objreference.object_id=? ";
}
