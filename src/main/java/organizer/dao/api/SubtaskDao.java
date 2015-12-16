package organizer.dao.api;

import organizer.models.Subtask;
import organizer.models.Task;

import java.util.List;

public interface SubtaskDao {
	void create(Subtask subtask, Task task);
	void delete(int id);
	void edit(Subtask subtask);
	List<Subtask> get(Task task);

	int OBJ_TYPE = 2;

	String DELETE = "DELETE FROM objects WHERE object_id = ? AND object_type_id = " + OBJ_TYPE;
}
