package organizer.logic.impl.security;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import organizer.models.User;

import java.util.Collection;

public final class CustomUserDetails extends org.springframework.security.core.userdetails.User implements UserDetails{

	private User user;

	public CustomUserDetails(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities,User user) {
		super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
		this.user=user;
	}

	public int getId(){
		return user.getId();
	}

	public String getName(){
		return user.getName();
	}

	public String getEmail(){
		return user.getEmail();
	}

	public String getSurname(){
		return user.getSurname();
	}

	public void setRole(String role){
		user.setRole(role);
	}

	public boolean getEnabled() { return user.isEnabled(); }

	public void setName(String name){
		user.setName(name);
	}

	public void setSurname(String surname){
		user.setSurname(surname);
	}

	public User getUser(){
		return user;
	}
}
