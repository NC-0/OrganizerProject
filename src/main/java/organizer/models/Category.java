package organizer.models;

import organizer.logic.api.ValidString;
import organizer.logic.impl.customvalidators.StringPerformer;

import javax.validation.constraints.Size;

public class Category implements Comparable<Category> {
	private int id;
	@ValidString
	@Size(min = 1, max = 50, message = "Name must between 1 and 50 characters")
	private String name;
	private User user;

	public Category() {
	}

	public Category(String name) {
		this.name = StringPerformer.perform(name);
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
		this.name = StringPerformer.perform(name);
	}

	public void setUser (User user) { this.user = user; }

	public User getUser() { return user; }

	public int compareTo(Category o) {
		if(!this.name.equalsIgnoreCase(o.getName()))
			return this.name.compareTo(o.getName());
		return 0;
	}
}