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
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


@Configuration
@EnableWebMvcSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	UserAuthenticationService userAuthenticationService;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.formLogin()
				.defaultSuccessUrl("/protected",false)
				.loginPage("/login")
				.loginProcessingUrl("/j_spring_security_check")
			.and()
			.authorizeRequests()
				.antMatchers("/protected").authenticated()
				.antMatchers("/updateprofile").authenticated()
				.antMatchers("/addcategory").authenticated()
				.antMatchers("/categorylist").authenticated()
				.antMatchers("/updatecategory").authenticated()
				.antMatchers("/calendar").authenticated()
				.antMatchers("/archive").authenticated()
				.antMatchers("/task/edit").authenticated()
				.antMatchers("/task/create").authenticated()
				.antMatchers("/task/list").authenticated()
				.antMatchers("/task/listcat").authenticated()
				.antMatchers("/subtask/edit").authenticated()
				.antMatchers("/subtask/create").authenticated()
				.antMatchers("/subtask/list").authenticated()
				.antMatchers(HttpMethod.POST,"/protected").authenticated()
				.antMatchers(HttpMethod.POST,"/edituser").authenticated()
				.antMatchers(HttpMethod.POST,"/deleteuser").authenticated()
				.antMatchers(HttpMethod.POST,"/createcategory").authenticated()
				.antMatchers(HttpMethod.POST,"/editcategory").authenticated()
				.antMatchers(HttpMethod.POST,"/deletecategory").authenticated()
				.antMatchers(HttpMethod.POST,"/subtask/create").authenticated()
				.antMatchers(HttpMethod.POST,"/subtask/delete").authenticated()
				.antMatchers(HttpMethod.POST,"/subtask/edit").authenticated()
				.antMatchers(HttpMethod.POST,"/subtask/complete").authenticated()
				.antMatchers(HttpMethod.POST,"/task/create").authenticated()
				.antMatchers(HttpMethod.POST,"/task/edit").authenticated()
				.antMatchers(HttpMethod.POST,"/task/delete").authenticated()
				.antMatchers(HttpMethod.POST,"/task/complete").authenticated()
				.antMatchers(HttpMethod.POST,"/task/complete/subtasks").authenticated()
				.anyRequest().permitAll()
			.and()
			.requiresChannel()//https
				.antMatchers("/registration").requiresSecure()
				.antMatchers("/createuser").requiresSecure()
				.antMatchers("/updateprofile").requiresSecure()
				.antMatchers("/edituser").requiresSecure()
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
				.logoutRequestMatcher(new AntPathRequestMatcher("/j_spring_security_logout"))
				.logoutSuccessUrl("/")
				.invalidateHttpSession(true)
			.and()
			.csrf();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth
			.userDetailsService(userAuthenticationService)
			.passwordEncoder(new BCryptPasswordEncoder());
	}
}
