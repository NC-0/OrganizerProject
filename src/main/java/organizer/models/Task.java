package organizer.models;

import java.util.ArrayList;

public class Task {
	private String name;
	private java.sql.Date date;
	private int priority;
	private String category;
	private boolean completed;
	private ArrayList<Task> subTaskList;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public java.sql.Date getDate() {
		return date;
	}
	public void setDate(java.sql.Date date) {
		this.date = date;
	}
	public int getPriority() {
		return priority;
	}
	public void setPriority(int priority) {
		this.priority = priority;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public boolean isCompleted() {
		return completed;
	}
	public void setCompleted(boolean completed) {
		this.completed = completed;
	}
	public ArrayList<Task> getSubTaskList() {
		return subTaskList;
	}
	public void setSubTaskList(ArrayList<Task> subTaskList) {
		this.subTaskList = subTaskList;
	}
	
}
