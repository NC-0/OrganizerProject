package organizer.dao.api;

import organizer.models.Subtask;
import organizer.models.Task;

import java.util.List;

public interface SubtaskDao {
	void create(Subtask subtask, Task task);
	void delete(int id);
	void edit(Subtask subtask);
	List<Subtask> get(Task task);

	int OBJ_TYPE          = 2;
	int IS_COMPLETED_ATTR = 5;

	String SELECT_ID    = "SELECT object_id.CURRVAL FROM dual";

	String INSERT           = "INSERT INTO objects (parent_id, object_type_id, name, description) VALUES (?, " + OBJ_TYPE + ", ?, NULL)";
	String INSERT_COMPLETED = "INSERT INTO attributes (attr_id, object_id, value, date_value) VALUES (" + IS_COMPLETED_ATTR + ", ?, FALSE, NULL)";

	String DELETE = "DELETE FROM objects WHERE object_id = ? AND object_type_id = " + OBJ_TYPE;
}
