package organizer.controllers;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import organizer.dao.api.UserIDao;
import organizer.models.User;

@Controller
public class UserController {

	@Autowired
	private ApplicationContext appContext;
	
	@RequestMapping(value="/createuser",method=RequestMethod.GET)
	public @ResponseBody String createUser(@RequestParam("email") String email,@RequestParam("password") String password,@RequestParam("name") String name,@RequestParam("surname") String surname){
		Locale.setDefault(Locale.ENGLISH);
		UserIDao userIDao = (UserIDao) appContext.getBean("userDao");
		User user = new User(email,password,name,surname);
		return userIDao.createUser(user);
	}
}
