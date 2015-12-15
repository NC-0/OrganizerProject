package organizer.logic.impl.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.PortMapper;
import org.springframework.security.web.PortMapperImpl;
import organizer.dao.api.UserDao;

import javax.sql.DataSource;


@Configuration
@EnableWebMvcSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	@Qualifier("dataSource")
	private DataSource dataSource;

	private final static String GET_USER_INFO = "SELECT " +
		"email_attr.VALUE as username, " +
		"pass_attr.VALUE as password, " +
		"enable_attr.VALUE as enabled " +
		"FROM " +
		"attributes email_attr, " +
		"attributes pass_attr, " +
		"attributes enable_attr " +
		"WHERE " +
		"email_attr.attr_id=6 AND " +
		"pass_attr.attr_id=7 AND " +
		"enable_attr.attr_id=11 AND " +
		"email_attr.value=? AND " +
		"pass_attr.OBJECT_ID=email_attr.OBJECT_ID AND " +
		"enable_attr.object_id = email_attr.OBJECT_ID ";

	private final static String GET_USER_ROLE = "SELECT email_attr.VALUE as username,'USER_ROLE' FROM attributes email_attr WHERE email_attr.VALUE = ?";

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.formLogin()
				.defaultSuccessUrl("/",false)
//				.loginPage("login")
			.and()
			.authorizeRequests()
				.antMatchers("/protected").authenticated()
				.antMatchers(HttpMethod.POST,"/protected").authenticated()
				.anyRequest().permitAll()
			.and()
			.requiresChannel()//https
				.antMatchers("/createuserform").requiresSecure()
				.antMatchers("/createuser").requiresSecure()
				.antMatchers("/").requiresInsecure()
			.and()
			.portMapper()
				.http(8081).mapsTo(8443)
			.and()
			.rememberMe()
				.tokenValiditySeconds(1)//cookie lifetime in seconds
				.key("OrganizerApplicationPrivateKey")
			.and()
			.logout()
				.logoutSuccessUrl("/")
				.invalidateHttpSession(true)
			.and()
			.csrf()
				.disable();

	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.
			jdbcAuthentication().dataSource(dataSource)
				.usersByUsernameQuery(GET_USER_INFO)
				.authoritiesByUsernameQuery(GET_USER_ROLE)
				.passwordEncoder(new BCryptPasswordEncoder());
	}
}
