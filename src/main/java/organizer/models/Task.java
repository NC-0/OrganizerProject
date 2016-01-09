package organizer.models;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Task {
	private int id;
	@Size(min=4, max=20)
	private String name;

	@NotNull
	@DateTimeFormat(pattern = "yyyy-mm-dd")
	private Date date;

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
	private User user;

	public Task() {}

	public Task(String name, Date date, int priority, Category category, boolean completed, List<Subtask> subtasks) {
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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj instanceof Task) {
			Task task = (Task) obj;
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd mm yyyy hh:mm:ss", Locale.ENGLISH);
			return
					(
							equals(id, task.getId()) &&
							equals(name, task.getName()) &&
							equals(simpleDateFormat.format(date), simpleDateFormat.format(task.getDate())) &&
							equals(priority, task.getPriority()) &&
							equals(category, task.getCategory()) &&
							equals(completed, task.isCompleted()) &&
							equals(subtasks, task.getSubtasks())
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
		hash = hash * 17 + hashCode(date);
		hash = hash * 17 + hashCode(priority);
		hash = hash * 17 + hashCode(category);
		hash = hash * 17 + hashCode(completed);
		hash = hash * 17 + hashCode(subtasks);
		return hash;
	}

	private int hashCode(Object object){
		return object !=null? object.hashCode():0;
	}
}
