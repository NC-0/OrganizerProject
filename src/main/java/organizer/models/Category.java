package organizer.models;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Size;

public class Category implements Comparable<Category> {
	private int id;
	@NotEmpty
	@Size(min = 1, max = 50, message = "Your category must between 1 and 50 characters")
	private String name;
	private User user;

	public Category() {
	}

	public Category(String name) {
		this.name = name;
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

	public void setUser (User user) { this.user = user; }

	public User getUser() { return user; }

	public int compareTo(Category o) {
		if(!this.name.equalsIgnoreCase(o.getName()))
			return this.name.compareTo(o.getName());
		return 0;
	}
}