package organizer.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import organizer.logic.impl.security.CustomUserDetails;

@Controller
public class OrganizerController {

	@RequestMapping(value="/protected",method= RequestMethod.GET)
	public @ResponseBody
	String protectedMethod(Authentication authentication){
		CustomUserDetails authorizedUser = (CustomUserDetails)authentication.getPrincipal();
		return "protected or not?" + authorizedUser.getId()+";"+authorizedUser.getUsername();
	}
}
