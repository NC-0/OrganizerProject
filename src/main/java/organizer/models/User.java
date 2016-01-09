package organizer.models;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import organizer.logic.api.PasswordMatcher;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@PasswordMatcher(message = "Passwords don't match")
public class User {
	private int id;
	@Email
	@NotEmpty(message = "Enter email")
	@Size(min = 6, max = 40)
	private String email;
	@NotEmpty
	@Size(min = 5, max = 20, message = "Your password must between 5 and 20 characters")
	private String password;
	private String matchingPassword;
	@NotNull
	@Size(min = 6, max = 20)
	private String name;
	@NotNull
	@Size(min = 6, max = 20)
	private String surname;
	private String role;
	private boolean enabled;
	private List<Category> categories;
	private List<Task> tasks;

	public User() {
		this(null,null,null,null);
	}

	public User(String email, String password, String name, String surname) {
		this(0,email,password,name,surname,"VERIFY_ROLE",false,new ArrayList<Category>(),new ArrayList<Task>());
	}

	public User(int id, String email, String password, String name, String surname, String role, boolean enabled, List<Category> categories, List<Task> tasks) {
		this.id = id;
		this.email = email;
		this.password = password;
		this.name = name;
		this.surname = surname;
		this.role = role;
		this.enabled = enabled;
		this.categories = categories;
		this.tasks = tasks;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getMatchingPassword() {
		return matchingPassword;
	}

	public void setMatchingPassword(String matchingPassword) {
		this.matchingPassword = matchingPassword;
	}

	public List<Category> getCategories() {
		return categories;
	}

	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}

	public List<Task> getTasks() {
		return tasks;
	}

	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj instanceof User) {
			User user = (User) obj;
			return
					(
							equals(id, user.getId()) &&
									equals(email, user.getEmail()) &&
									equals(password, user.getPassword()) &&
									equals(name, user.getName()) &&
									equals(surname, user.getSurname()) &&
									equals(role, user.getRole()) &&
									equals(enabled, user.isEnabled()) &&
									equals(categories, user.getCategories()) &&
									equals(tasks, user.getTasks())

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
		hash = hash * 17 + hashCode(email);
		hash = hash * 17 + hashCode(password);
		hash = hash * 17 + hashCode(name);
		hash = hash * 17 + hashCode(surname);
		hash = hash * 17 + hashCode(role);
		hash = hash * 17 + hashCode(enabled);
		hash = hash * 17 + hashCode(categories);
		hash = hash * 17 + hashCode(tasks);
		return hash;
	}

	private int hashCode(Object object){
		return object !=null? object.hashCode():0;
	}
}
