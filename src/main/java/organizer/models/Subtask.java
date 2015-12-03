package organizer.models;

public class Subtask {
	private String name;
	private boolean completed;
	
	public Subtask() {}
	
	public Subtask(String name, boolean completed) {
		this.name = name;
		this.completed = completed;
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
}
