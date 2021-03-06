package organizer.models;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;
import organizer.logic.api.ValidString;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

public class Task {

	private int id;
	@ValidString
	@Size(min=1, max=255)
	private String name;

	@NotNull
	@Future
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date date;

//	@NotEmpty
//	private String date;

	private int priority;

	/** String interpretation of number for User */
	@NotEmpty
	private String priority_str;

	private Category category;

	/** String interpretation of Category name for User */
	@NotEmpty
	private String category_str;

	private boolean completed;
	private List<Subtask> subtasks;

	public Task() {}

	public Task(String name, Date date, int priority, Category category, boolean completed, List<Subtask> subtasks) {
		//this.id = id;
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

	public void setId(int id) {
		this.id = id;
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

	public List<Subtask> getSubtasks() {
		return subtasks;
	}

	public void setSubtasks(List<Subtask> subtasks) {
		this.subtasks = subtasks;
	}

	public String getCategory_str() {
		return category_str;
	}

	public void setCategory_str(String category_str) {
		this.category_str = category_str;
	}

	public String getPriority_str() {
		return priority_str;
	}

	public void setPriority_str(String priority_str) {
		this.priority_str = priority_str;
	}
}
