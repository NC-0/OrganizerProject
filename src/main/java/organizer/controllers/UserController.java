package organizer.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.util.comparator.BooleanComparator;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import org.springframework.web.servlet.ModelAndView;
import organizer.dao.api.UserDao;
import organizer.logic.impl.MessageContent;
import organizer.logic.impl.security.CustomUserDetails;
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

	@RequestMapping(value="/updateprofile",method=RequestMethod.GET)
	public ModelAndView editUserForm(Authentication authentication){
		ModelAndView modelAndView = new ModelAndView("edituser");
		CustomUserDetails authorizedUser = (CustomUserDetails)authentication.getPrincipal();
		modelAndView.addObject("email",authorizedUser.getEmail());
		modelAndView.addObject("surname",authorizedUser.getSurname());
		modelAndView.addObject("name",authorizedUser.getName());
		return modelAndView;
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

	@RequestMapping(value="/edituser",method=RequestMethod.POST)
	public @ResponseBody String editUser(HttpServletRequest request,Authentication authentication){
		String email =	request.getParameter("email");
		String password =  request.getParameter("password");
		String retryPassword =  request.getParameter("retrypassword");
		CustomUserDetails authorizedUser = (CustomUserDetails)authentication.getPrincipal();
		if(!password.equals(retryPassword)){
			return MessageContent.PASSWORD_ERROR;
		}
		String name =  request.getParameter("name");
		String surname = request.getParameter("surname");
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		String hashedPass = bCryptPasswordEncoder.encode(password);
		User user = new User(email,hashedPass,name,surname);
		user.setId(authorizedUser.getId());
		authorizedUser.setName(name);
		authorizedUser.setSurname(surname);
		userDaoImpl.edit(user);
		if(!password.equals("")){
			userDaoImpl.editPassword(user);
		}
		return MessageContent.USER_UPDATED;
	}

	@RequestMapping(value = "/deleteuser",method = RequestMethod.POST)
	public String deleteUser(HttpServletRequest request,Authentication authentication,@RequestParam(value = "deletecheckbox", required = false) boolean checkDelete){
		CustomUserDetails authorizedUser = (CustomUserDetails)authentication.getPrincipal();
		if(!checkDelete)
			return "redirect:/updateprofile";
		else {
			userDaoImpl.delete(authorizedUser.getId());
			return "redirect:/j_spring_security_logout";
		}
	}
}
