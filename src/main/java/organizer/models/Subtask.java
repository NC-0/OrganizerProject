package organizer.models;

import javax.validation.constraints.Size;

public class Subtask {
	private int id;
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
}
