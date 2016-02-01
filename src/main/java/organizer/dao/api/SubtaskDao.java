package organizer.dao.api;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import organizer.models.Subtask;
import organizer.models.Task;

import java.util.List;

public interface SubtaskDao {
	void create(Subtask subtask);
	void delete(Subtask subtask);
	void update(Subtask subtask);
	List<Subtask> get(Task task);
	Subtask get(int id);

	int OBJ_TYPE          = 2;
	int IS_COMPLETED_ATTR = 5;

	String SELECT_ID = "SELECT object_id.CURRVAL FROM dual";

	String INSERT           = "INSERT INTO objects (parent_id, object_type_id, name, description) VALUES (?, " + OBJ_TYPE + ", ?, NULL)";
	String INSERT_COMPLETED = "INSERT INTO attributes (attr_id, object_id, value, date_value) VALUES (" + IS_COMPLETED_ATTR + ", ?, ?, NULL)";

	String UPDATE_NAME = "UPDATE objects SET name = ? WHERE object_id = ?";
	String UPDATE_STATUS = "UPDATE attributes SET value = ? WHERE attr_id = " + IS_COMPLETED_ATTR + " and object_id = ?";

	String DELETE = "DELETE FROM objects WHERE object_id = ? AND object_type_id = " + OBJ_TYPE;

	String SELECT_BY_TASK_ID = (
			"SELECT obj.object_id AS id, " +
					"obj.name AS name, " +
					"attr.value AS completed " +
			"FROM objects obj " +
					"JOIN attributes attr ON obj.object_id = attr.object_id " +
			"WHERE obj.object_type_id = " + OBJ_TYPE +
					" AND obj.parent_id = ? " +
					" AND attr.attr_id = " + IS_COMPLETED_ATTR
	);

	String SELECT = (
			"SELECT obj.object_id AS id, " +
					"obj.name AS name, " +
					"attr.value AS completed " +
			"FROM objects obj "
				+ "JOIN attributes attr ON obj.object_id = attr.object_id " +
			"WHERE obj.object_type_id = " + OBJ_TYPE
				+ " AND obj.object_id = ? " +
					"AND attr.attr_id = " + IS_COMPLETED_ATTR
	);

}
