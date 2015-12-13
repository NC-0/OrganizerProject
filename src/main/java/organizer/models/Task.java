package organizer.models;

import java.util.Date;
import java.util.List;

public class Task {
	private int id;
	private String name;
	private Date date;
	private int priority;
	private Category category;
	private boolean completed;
	private List<Task> subtasks;

	public Task(int id, String name, Date date, int priority, Category category, boolean completed, List<Task> subtasks) {
		this.id = id;
		this.name = name;
		this.date = date;
		this.priority = priority;
		this.category = category;
		this.completed = completed;
		this.subtasks = subtasks;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public boolean isCompleted() {
		return completed;
	}

	public void setCompleted(boolean completed) {
		this.completed = completed;
	}

	public List<Task> getSubtasks() {
		return subtasks;
	}

	public void setSubtasks(List<Task> subtasks) {
		this.subtasks = subtasks;
	}
}
