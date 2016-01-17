package organizer.controllers;

import javax.mail.MessagingException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.util.comparator.BooleanComparator;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.servlet.ModelAndView;
import organizer.dao.api.UserDao;
import organizer.logic.impl.MessageContent;
import organizer.logic.impl.email.MailSender;
import organizer.logic.impl.security.CustomUserDetails;
import organizer.logic.impl.security.UserAuthenticationService;
import organizer.models.User;

import java.sql.Date;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Map;

@Controller
public class UserController {

	@Autowired
	@Qualifier("userDaoImpl")
	private UserDao userDaoImpl;

	@Autowired
	@Qualifier("mailSender")
	private MailSender mailSender;

	@RequestMapping(value="/registration",method=RequestMethod.GET)
	public String createUserForm(Authentication authentication,Map<String, Object> model){
		if(authentication!=null)
			return "redirect:/protected";
		User user = new User();
		model.put("userForm",user);
		return "createuser";
	}

	@RequestMapping(value="/updateprofile",method=RequestMethod.GET)
	public String editUserForm(Authentication authentication,Map<String, Object> model){
		CustomUserDetails authorizedUser = (CustomUserDetails)authentication.getPrincipal();
		User user = authorizedUser.getUser();
		model.put("userForm",user);
		return "edituser";
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView login(Authentication authentication,
									  @RequestParam(value = "error", required = false) String error) {
		ModelAndView model = new ModelAndView();
		if (error != null) {
			model.addObject("error", MessageContent.USER_INVALID_LOGIN);
		}
		model.setViewName("login");
		if(authentication!=null) {
			model = new ModelAndView("redirect:/protected");
		}
		return model;
	}

	@RequestMapping(value = "/verification",method = RequestMethod.GET)
	public String validate(HttpServletRequest request){
		String message = MessageContent.VERIFY_ERROR;
		String verifyId = request.getParameter("verific");
		if(verifyId!=null) {
			User user = userDaoImpl.verify(verifyId);
			if (user != null) {
				if((System.currentTimeMillis()-user.getRegistrationDate().getTime())<7*24*60*60*1000){
					if(user.isEnabled()==false) {
						user.setEnabled(true);
						userDaoImpl.edit(user);
						message = MessageContent.VERIFY_SUCCESSFULL;
					}
				}
			}
		}
		request.setAttribute("message",message);
		return "hello";
	}

	@RequestMapping(value="/createuser",method=RequestMethod.POST)
	public String createUser(@Valid @ModelAttribute("userForm") User userForm,
									 BindingResult result,
									 Map<String, Object> model){
		if (result.hasErrors()) {
			return "createuser";
		}
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		String hashedPass = bCryptPasswordEncoder.encode(userForm.getPassword());
		String verificationId = bCryptPasswordEncoder.encode(userForm.getEmail()+MessageContent.MAIL+userForm.getName()+userForm.getSurname());
		java.sql.Date timeNow = new java.sql.Date(Calendar.getInstance().getTimeInMillis());
		userForm.setVerify(verificationId);
		userForm.setPassword(hashedPass);
		userForm.setRegistrationDate(timeNow);
		String message;
		try{
			message = userDaoImpl.create(userForm);
			try {
				mailSender.sendMail(userForm.getEmail(),
					verificationId,
					MessageContent.MAIL_SUBJECT_VERIFICATION,
					MessageContent.MAIL_TEXT_VERIFICATION);
			}catch (MessagingException e) {
				model.put("message",message);
				return "createuser";
			}
		}catch(Exception e){
			model.put("message",String.format(MessageContent.USER_ALREADY_EXIST, userForm.getEmail()));
			return "createuser";
		}
		model.put("message",String.format(MessageContent.USER_CREATED, userForm.getEmail()));
		return "hello";
	}

	@RequestMapping(value="/edituser",method=RequestMethod.POST)
	public String editUser(@Valid @ModelAttribute("userForm") User userForm,
								  BindingResult result,
								  Authentication authentication,
								  @RequestParam(value = "editecheckbox", required = false) boolean checkEdit){
		if (result.hasErrors()) {
			if(result.hasFieldErrors("password") && !result.hasFieldErrors("name") && !result.hasFieldErrors("surname")){
				CustomUserDetails authorizedUser = (CustomUserDetails)authentication.getPrincipal();
				userForm.setId(authorizedUser.getId());
				userForm.setEnabled(true);
				authorizedUser.setName(userForm.getName());
				authorizedUser.setSurname(userForm.getSurname());
				userDaoImpl.edit(userForm);
			}
			else
				return "edituser";
		}
		if(checkEdit){
			if (result.hasFieldErrors("password")) {
				return "edituser";
			}
			BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
			String hashedPass = bCryptPasswordEncoder.encode(userForm.getPassword());
			userForm.setPassword(hashedPass);
			userDaoImpl.editPassword(userForm);
		}
		return "redirect:/updateprofile";
	}

	@RequestMapping(value = "/deleteuser",method = RequestMethod.POST)
	public String deleteUser(HttpServletRequest request,
									 Authentication authentication,
									 @RequestParam(value = "deletecheckbox", required = false) boolean checkDelete){
		CustomUserDetails authorizedUser = (CustomUserDetails)authentication.getPrincipal();
		if(!checkDelete)
			return "redirect:/updateprofile";
		else {
			userDaoImpl.delete(authorizedUser.getId());
			return "redirect:/j_spring_security_logout";
		}
	}
}
