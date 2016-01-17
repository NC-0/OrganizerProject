package organizer.logic.impl.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import organizer.dao.api.UserDao;
import organizer.models.User;

import java.util.Arrays;
import java.util.Collection;

@Service
public class UserAuthenticationService implements UserDetailsService {
	@Autowired
	@Qualifier("userDaoImpl")
	private UserDao userDaoImpl;

	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = userDaoImpl.get(email);
		GrantedAuthority authority = new SimpleGrantedAuthority(user.getRole());
		UserDetails userDetails = (UserDetails)new CustomUserDetails(user.getEmail(),user.getPassword(),user.isEnabled(),true,true,true, Arrays.asList(authority),user);
		return userDetails;
	}
}
