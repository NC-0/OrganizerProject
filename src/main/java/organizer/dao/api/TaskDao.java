package organizer.dao.api;

import java.util.ArrayList;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.support.TransactionTemplate;

import organizer.models.Task;

public interface TaskDao {
	void create(int userId, Task task);
	void delete();
	void edit(Task task);
	Task get();
	ArrayList<Task> getSubTaskList();
	
	// Create Task requests
	final static String INSERT_TASK = "INSERT INTO OBJECTS (OBJECT_ID, PARENT_ID, OBJECT_TYPE_ID, NAME, DESCRIPTION) "
									+ "VALUES (0, NULL, 1, ?, NULL)"; /* 0-trigger will changed it to nextval before insert operation, task obj_type_id = 1 */
	final static String INSERT_TASK_DATE = "INSERT INTO ATTRIBUTES (ATTR_ID, OBJECT_ID, VALUE, DATE_VALUE) VALUES (1, ?, NULL, ?)"; /* date = 1 */
	final static String INSERT_TASK_PRIORITY = "INSERT INTO ATTRIBUTES (ATTR_ID, OBJECT_ID, VALUE, DATE_VALUE) VALUES (2, ?, ?, NULL)"; /* priority = 2 */
	final static String INSERT_TASK_CATEGORY = "INSERT INTO ATTRIBUTES (ATTR_ID, OBJECT_ID, VALUE, DATE_VALUE) VALUES (3, ?, ?, NULL)"; /* category = 3 */
	final static String INSERT_TASK_STATUS = "INSERT INTO ATTRIBUTES (ATTR_ID, OBJECT_ID, VALUE, DATE_VALUE) VALUES (4, ?, ?, NULL)";	/* status = 4 */
	final static String INSERT_TASK_REF_USER = "INSERT INTO OBJREFERENCE (ATTR_ID,  REFERENCE, OBJECT_ID) VALUES (10, ?, ?);"; 	/* ref attribute=10, task ?, user ? */
	
	// Delete Task requests
	
	// Update Task requests
	
	// Get Task requests
}
