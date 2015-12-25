package organizer.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import org.springframework.web.servlet.ModelAndView;
import organizer.dao.api.UserDao;
import organizer.logic.impl.MessageContent;
import organizer.models.User;

@Controller
public class UserController {

	@Autowired
	@Qualifier("userDaoImpl")
	private UserDao userDaoImpl;

	@RequestMapping(value="/registration",method=RequestMethod.GET)
	public String createUserForm(Authentication authentication){
		if(authentication!=null)
			return "redirect:/protected";
		return "createuser";
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView login(Authentication authentication,@RequestParam(value = "error", required = false) String error) {
		ModelAndView model = new ModelAndView();
		if (error != null) {
			model.addObject("error", MessageContent.USER_INVALID_LOGIN);
		}
		model.setViewName("login");
		if(authentication!=null)
			model = new ModelAndView("redirect:/protected");
		return model;
	}

	@RequestMapping(value="/createuser",method=RequestMethod.POST)
	public @ResponseBody String createUser(HttpServletRequest request){
		String email =	request.getParameter("email");
		String password =  request.getParameter("password");
		String name =  request.getParameter("name");
		String surname = request.getParameter("surname");
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		String hashedPass = bCryptPasswordEncoder.encode(password);
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
