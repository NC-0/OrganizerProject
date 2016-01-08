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

import java.util.Arrays;
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
		User user = new User(authorizedUser.getEmail(),
			authorizedUser.getPassword(),
			authorizedUser.getName(),
			authorizedUser.getSurname());
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
			System.out.println("a"+authentication.getName());
			model = new ModelAndView("redirect:/protected");
		}
		return model;
	}

	@RequestMapping(value = "/verification",method = RequestMethod.GET)
	public String validate(HttpServletRequest request,Authentication authentication,
								  @CookieValue(value = "verificationId",
									  defaultValue = "undefined") String cookieVerify	){
		String message=MessageContent.VERIFY_LOGIN;
		System.out.println(cookieVerify);
		if(authentication!=null){
			message = MessageContent.VERIFY_ERROR;
			if(!cookieVerify.equals("undefined")) {
				CustomUserDetails authorizedUser = (CustomUserDetails)authentication.getPrincipal();
				message = String.format(MessageContent.VERIFY_ENABLED, authorizedUser.getEmail());
				if(!authorizedUser.getEnabled()) {
					String verifyId = request.getParameter("verific");
					if(verifyId.equals(cookieVerify)) {
						message = MessageContent.VERIFY_SUCCESSFULL;
						User user = new User(authorizedUser.getEmail(),
							authorizedUser.getPassword(),
							authorizedUser.getName(),
							authorizedUser.getSurname());
						user.setEnabled(true);
						user.setRole("USER_ROLE");
						user.setId(authorizedUser.getId());
						GrantedAuthority authority = new SimpleGrantedAuthority(user.getRole());
						authentication = new UsernamePasswordAuthenticationToken(authentication.getPrincipal(),
							authentication.getCredentials(),
							Arrays.asList(authority));
						SecurityContextHolder.getContext().setAuthentication(authentication);
						userDaoImpl.edit(user);
					}
				}
			}
		}
		request.setAttribute("message",message);
		return "hello";
	}

	@RequestMapping(value="/createuser",method=RequestMethod.POST)
	public String createUser(HttpServletResponse response,
									 @Valid @ModelAttribute("userForm") User userForm,
									 BindingResult result,
									 Map<String, Object> model){
		if (result.hasErrors()) {
			return "createuser";
		}
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		String hashedPass = bCryptPasswordEncoder.encode(userForm.getPassword());
		userForm.setPassword(hashedPass);
		String message;
		try{
			message = userDaoImpl.create(userForm);
			String verificationId = bCryptPasswordEncoder.encode(userForm.getEmail()+MessageContent.MAIL+userForm.getName()+userForm.getSurname());
			Cookie cookieVerify = new Cookie("verificationId", verificationId);
			cookieVerify.setMaxAge(7*24*60*60);
			response.addCookie(cookieVerify);
			try {
				mailSender.sendMail(userForm,verificationId);
			}catch (MessagingException e) {
				model.put("message",message);
				return "createuser";
			}
		}catch(Exception e){
			model.put("message",String.format(MessageContent.USER_ALREADY_EXIST, userForm.getEmail()));
			return "createuser";
		}
		return "redirect:/";
	}

	@RequestMapping(value="/edituser",method=RequestMethod.POST)
	public String editUser(HttpServletResponse response,
								  @Valid @ModelAttribute("userForm") User userForm,
								  BindingResult result, Map<String, Object> model,
								  Authentication authentication){
		if (result.hasErrors()) {
			return "edituser";
		}
		CustomUserDetails authorizedUser = (CustomUserDetails)authentication.getPrincipal();
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		String hashedPass = bCryptPasswordEncoder.encode(userForm.getPassword());
		userForm.setPassword(hashedPass);
		userForm.setId(authorizedUser.getId());
		userForm.setEnabled(true);
		authorizedUser.setName(userForm.getName());
		authorizedUser.setSurname(userForm.getSurname());
		userDaoImpl.edit(userForm);
		userDaoImpl.editPassword(userForm);
		return "edituser";
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
