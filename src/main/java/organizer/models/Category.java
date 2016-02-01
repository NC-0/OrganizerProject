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

	@Override
	public boolean equals(Object obj) {


		if (this == obj)
			return true;
		if (obj instanceof Category) {
			Category category = (Category) obj;

			return  (
					equals(id, category.getId()) &&
							equals(name, category.getName())
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
		return hash;
	}

	private int hashCode(Object object){
		return object !=null? object.hashCode():0;
	}
}