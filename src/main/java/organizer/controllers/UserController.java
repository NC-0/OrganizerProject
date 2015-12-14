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
	
	@RequestMapping(value="/createuserform",method=RequestMethod.GET)
	public String createUserForm(){
		Locale.setDefault(Locale.ENGLISH);
		return "createuser";
	}
	
	@RequestMapping(value="/createuser",method=RequestMethod.POST)
	public @ResponseBody String createUser(HttpServletRequest request){
		String email =	request.getParameter("email");
		String password =  request.getParameter("password");
		
		
		//******Md5 Test********
		MessageDigest messageDigest = null;
		try {
			messageDigest = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		messageDigest.update(password.getBytes(),0, password.length());  
		String hashedPass = new BigInteger(1,messageDigest.digest()).toString(16);  
		if (hashedPass.length() < 32) {
		   hashedPass = "0" + hashedPass; 
		}
		//******Md5 Test********
		
		
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
