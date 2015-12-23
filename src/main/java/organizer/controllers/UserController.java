package organizer.controllers;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import organizer.dao.api.TaskDao;
import organizer.dao.api.UserDao;
import organizer.logic.impl.MessageContent;
import organizer.models.Task;
import organizer.models.User;

@Controller
public class UserController {

	@Autowired
	@Qualifier("userDaoImpl")
	private UserDao userDaoImpl;

	public UserController(){
		Locale.setDefault(Locale.ENGLISH);
	}

	@RequestMapping(value="/createuserform",method=RequestMethod.GET)
	public String createUserForm(){
		return "createuser";
	}

	@RequestMapping(value="/protected",method=RequestMethod.GET)
	public @ResponseBody String protectedMethod(Authentication authentication){
		CustomUserDetails authorizedUser = (CustomUserDetails)authentication.getPrincipal();
		return "protected or not?" + authorizedUser.getId()+";"+authorizedUser.getUsername();
	}

	@RequestMapping(value="/createuser",method=RequestMethod.POST)
	public @ResponseBody String createUser(HttpServletRequest request){
		String email =	request.getParameter("email");
		String password =  request.getParameter("password");

		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		String hashedPass = bCryptPasswordEncoder.encode(password);

		String name =  request.getParameter("name");
		String surname = request.getParameter("surname");
		User user = new User(email,hashedPass,name,surname);
		String message = MessageContent.ERROR;
		try{
			message = userDaoImpl.create(user);
		}catch(Exception e){
			e.printStackTrace();
		}
		return message;
	}
}
