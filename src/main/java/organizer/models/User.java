package organizer.models;

public class User {
	private String email;
	private String password;
	private String name;
	private String surname;
	public User(String email,String password,String name,String surname) {
		this.email=email;
		this.name=name;
		this.password=password;
		this.surname=surname;
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
	
}
