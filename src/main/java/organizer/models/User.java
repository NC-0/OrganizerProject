package organizer.models;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import organizer.logic.api.PasswordMatcher;
import organizer.logic.api.ValidString;
import organizer.logic.impl.customvalidators.StringPerformer;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Date;

@PasswordMatcher(message = "Passwords don't match")
public class User {
	private int id;
	@ValidString
	@Email
	@NotEmpty(message = "Enter email")
	@Size(min = 6, max = 40)
	private String email;
	@NotEmpty
	@Size(min = 5, max = 20, message = "Your password must between 5 and 20 characters")
	private String password;
	private String matchingPassword;
	@ValidString
	@Size(min = 1, max = 20)
	private String name;
	@ValidString
	@Size(min = 1, max = 20)
	private String surname;
	private String role;
	private boolean enabled;
	private String verify;
	private java.sql.Date registrationDate;
	public User() {
	}

	public User(String email, String password, String name, String surname) {
		this.email = StringPerformer.perform(email);
		this.password = StringPerformer.perform(password);
		this.name = StringPerformer.perform(name);
		this.surname = StringPerformer.perform(surname);
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
		this.email = StringPerformer.perform(email);
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = StringPerformer.perform(password);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = StringPerformer.perform(name);
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = StringPerformer.perform(surname);
	}

	public String getMatchingPassword() {
		return matchingPassword;
	}

	public void setMatchingPassword(String matchingPassword) {
		this.matchingPassword = matchingPassword;
	}

	public String getVerify() {
		return verify;
	}

	public void setVerify(String verify) {
		this.verify = verify;
	}

	public Date getRegistrationDate() {
		return registrationDate;
	}

	public void setRegistrationDate(Date registrationDate) {
		this.registrationDate = registrationDate;
	}
}
