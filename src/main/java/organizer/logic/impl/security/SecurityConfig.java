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


@Configuration
@EnableWebMvcSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	UserAuthenticationService userAuthenticationService;

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
		auth
			.userDetailsService(userAuthenticationService)
			.passwordEncoder(new BCryptPasswordEncoder());
	}
}
