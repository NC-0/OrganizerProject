package organizer.dao.api;

import java.util.ArrayList;

import organizer.models.Subtask;
import organizer.models.Task;

public interface SubtaskDao {
	void create(Subtask subtask, Task task);
	void delete(int id);
	void edit(Subtask subtask);
	ArrayList<Subtask> get(Task task);

	final static String DELETE_SUBTASK_OBJECT = "DELETE objects WHERE object_id = ?";
}
