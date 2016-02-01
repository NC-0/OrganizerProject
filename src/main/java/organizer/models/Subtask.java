package organizer.models;

import organizer.logic.api.ValidString;

import javax.validation.constraints.Size;

public class Subtask {
	private int id;
	@ValidString
	@Size(min=1, max=30)
	private String name;
	private boolean completed;
	private Task task;

	public Subtask() {}

	public Subtask(int id, String name, boolean completed, Task task) {
		this.name = name;
		this.completed = completed;
		this.id = id;
		this.task = task;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public boolean isCompleted() {
		return completed;
	}
	
	public void setCompleted(boolean completed) {
		this.completed = completed;
	}

	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj instanceof Subtask) {
			Subtask subtask = (Subtask) obj;
			return
					(
							equals(id, subtask.getId()) &&
									equals(name, subtask.getName()) &&
									equals(completed, subtask.isCompleted())
					);
		}
		return false;

	}

	private boolean equals(Object firstObject, Object secondObject) {
		return (firstObject == secondObject || (firstObject != null && firstObject.equals(secondObject)));
	}

	@Override
	public int hashCode() {
		int hash = 37;
		hash = hash * 17 + hashCode(id);
		hash = hash * 17 + hashCode(name);
		hash = hash * 17 + hashCode(completed);
		return hash;
	}

	private int hashCode(Object object){
		return object !=null? object.hashCode():0;
	}
}
