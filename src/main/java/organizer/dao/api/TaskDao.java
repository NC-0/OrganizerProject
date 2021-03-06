package organizer.dao.api;

import organizer.models.Category;
import organizer.models.Task;
import organizer.models.User;

import java.util.List;

public interface TaskDao {
	void create(int userId, Task task);
	void delete(Task task);
	void edit(Task task);
	void updateStatus(Task task);
	Task get(User user, int id);
	List<Task> get(User user, boolean completed);
	List<Task> getByCat(User user, Category category, boolean completed);
	boolean checkChilds(Task task);

	int OBJ_TYPE      = 1;
	int OBJE_TYPE_SUBTASK = 2;

	int DATE_ATTR     = 1;
	int PRIORITY_ATTR = 2;
	int CATEGORY_ATTR = 3;
	int STATUS_ATTR   = 4;
	int USER_REF_ATTR = 10;
	int TASK_REF_CATEGORY = 13;

	// Create Task requests
	String INSERT          = "INSERT INTO OBJECTS (PARENT_ID, OBJECT_TYPE_ID, NAME, DESCRIPTION) VALUES (NULL, " + OBJ_TYPE + ", ?, NULL)";
	String INSERT_DATE     = "INSERT INTO ATTRIBUTES (ATTR_ID, OBJECT_ID, VALUE, DATE_VALUE) VALUES (" + DATE_ATTR + ", ?, NULL, ?)";
	String INSERT_PRIORITY = "INSERT INTO ATTRIBUTES (ATTR_ID, OBJECT_ID, VALUE, DATE_VALUE) VALUES (" + PRIORITY_ATTR + ", ?, ?, NULL)";
	String INSERT_CATEGORY = "INSERT INTO ATTRIBUTES (ATTR_ID, OBJECT_ID, VALUE, DATE_VALUE) VALUES (" + CATEGORY_ATTR + ", ?, ?, NULL)";
	String INSERT_STATUS   = "INSERT INTO ATTRIBUTES (ATTR_ID, OBJECT_ID, VALUE, DATE_VALUE) VALUES (" + STATUS_ATTR + ", ?, ?, NULL)";
	String INSERT_REF_USER = "INSERT INTO OBJREFERENCE (ATTR_ID,  REFERENCE, OBJECT_ID) VALUES (" + USER_REF_ATTR + ", ?, ?)";
	String INSERT_REF_CATEGORY = "INSERT INTO OBJREFERENCE (ATTR_ID,  REFERENCE, OBJECT_ID) VALUES (" + TASK_REF_CATEGORY + ", ?, ?)";

	// Delete Task requests
	String DELETE_OBJECT = "DELETE FROM objects WHERE object_id = ? AND object_type_id = " + OBJ_TYPE;
	String DELETE_REF_TO_USER = "DELETE objreference WHERE attr_id = " + USER_REF_ATTR + " AND REFERENCE = ?";
	String DELETE_REF_TO_CATEGORY = "DELETE objreference WHERE attr_id = " + TASK_REF_CATEGORY + " AND REFERENCE = ?";

	// Update Task requests
	String UPDATE_NAME = "UPDATE objects SET name = ? WHERE object_id = ?";
	String UPDATE_DATE = "UPDATE attributes SET date_value = ? WHERE attr_id = " + DATE_ATTR +" and object_id = ?";
	String UPDATE_PRIORITY = "UPDATE attributes SET value = ? WHERE attr_id = " + PRIORITY_ATTR +" and object_id = ?";
	String UPDATE_CATEGORY = "UPDATE attributes SET value = ? WHERE attr_id = " + CATEGORY_ATTR +" and object_id = ?";
	String UPDATE_CATEGORY_REF = "UPDATE OBJREFERENCE SET reference = ? WHERE attr_id = " + TASK_REF_CATEGORY +" and object_id = ?";
	String UPDATE_STATUS = "UPDATE attributes SET value = ? WHERE attr_id = " + STATUS_ATTR +" and object_id = ?";
	String UPDATE_SUBTASK_STATUS = "UPDATE attributes SET value = 1 WHERE attr_id =" + SubtaskDao.IS_COMPLETED_ATTR +
		" AND object_id IN (SELECT object_id FROM objects WHERE parent_id = ?)";


	// Get Task requests
	String SELECT_LIST_OF_USER_TASKS = (
		"SELECT" +
				" obj.OBJECT_ID as id, " +
				" obj.NAME as taskname," +
				" date_attr.DATE_VALUE as dates ," +
				" priority_attr.VALUE as priority," +
				" category_attr.VALUE as category," +
				" status_attr.VALUE as status " +
		" FROM" +
				" attributes date_attr," +
				" attributes priority_attr," +
				" attributes category_attr," +
				" attributes status_attr," +
				" OBJREFERENCE ref," +
				" objects obj" +
		" WHERE" +
				" date_attr.attr_id = " + DATE_ATTR + " AND" +
				" priority_attr.attr_id = " + PRIORITY_ATTR + " AND" +
				" category_attr.attr_id = " + CATEGORY_ATTR + " AND" +
				" status_attr.attr_id = " + STATUS_ATTR + " AND" +
				" status_attr.VALUE = ? AND" +
				" ref.object_id = ? AND" +
				" ref.attr_id = " + USER_REF_ATTR + " AND" +
				" date_attr.object_id = obj.OBJECT_ID AND" +
				" priority_attr.OBJECT_ID = obj.OBJECT_ID AND" +
				" category_attr.object_id = obj.OBJECT_ID AND" +
				" status_attr.object_id = obj.OBJECT_ID AND" +
				" obj.OBJECT_ID=ref.reference " +
				" ORDER BY priority_attr.VALUE"
	);

	String SELECT_TASK_BY_ID = (
			"SELECT" +
					" obj.OBJECT_ID as id, " +
					" obj.NAME as taskname," +
					" date_attr.DATE_VALUE as dates ," +
					" priority_attr.VALUE as priority," +
					" category_attr.VALUE as category," +
					" status_attr.VALUE as status " +
					" FROM" +
					" attributes date_attr," +
					" attributes priority_attr," +
					" attributes category_attr," +
					" attributes status_attr," +
					" objects obj" +
					" WHERE" +
					" date_attr.attr_id = " + DATE_ATTR + " AND" +
					" priority_attr.attr_id = " + PRIORITY_ATTR + " AND" +
					" category_attr.attr_id = " + CATEGORY_ATTR + " AND" +
					" status_attr.attr_id = " + STATUS_ATTR + " AND" +
					" date_attr.object_id = obj.OBJECT_ID AND" +
					" priority_attr.OBJECT_ID = obj.OBJECT_ID AND" +
					" category_attr.object_id = obj.OBJECT_ID AND" +
					" status_attr.object_id = obj.OBJECT_ID AND" +
					" obj.OBJECT_ID = ? " +
					" ORDER BY priority_attr.VALUE"
	);

	String SELECT_LIST_OF_USER_TASKS_BY_CAT = " " +
		"SELECT" +
		" obj.OBJECT_ID as id, " +
		" obj.NAME as taskname," +
		" date_attr.DATE_VALUE as dates ," +
		" priority_attr.VALUE as priority," +
		" category_attr.VALUE as category," +
		" status_attr.VALUE as status " +
		" FROM" +
		" attributes date_attr," +
		" attributes priority_attr," +
		" attributes category_attr," +
		" attributes status_attr," +
		" OBJREFERENCE ref," +
		" objects obj" +
		" WHERE" +
		" date_attr.attr_id = " + DATE_ATTR + " AND" +
		" priority_attr.attr_id = " + PRIORITY_ATTR + " AND" +
		" category_attr.attr_id = " + CATEGORY_ATTR + " AND" +
		" status_attr.attr_id = " + STATUS_ATTR + " AND" +
		" status_attr.VALUE = ? AND" +
		" ref.object_id = ? AND" +
		" ref.attr_id = " + USER_REF_ATTR + " AND" +
		" date_attr.object_id = obj.OBJECT_ID AND" +
		" priority_attr.OBJECT_ID = obj.OBJECT_ID AND" +
		" category_attr.object_id = obj.OBJECT_ID AND" +
		" status_attr.object_id = obj.OBJECT_ID AND" +
		" obj.OBJECT_ID=ref.reference AND" +
		" category_attr.VALUE = ? " +
		" ORDER BY priority_attr.VALUE";

	String SELECT_CHECKED_CHILDS = (
		"select checkComplete(?) from dual"
	);
}
