package organizer.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import organizer.logic.impl.security.CustomUserDetails;
import organizer.models.Category;

import java.util.Locale;
import java.util.Map;

@Controller
public class OrganizerController {

	@RequestMapping(value="/protected",method= RequestMethod.GET)
	public String protectedMethod(Authentication authentication,Map<String, Object> model){
		Category category = new Category();
		model.put("categoryForm",category);
		CustomUserDetails authorizedUser = (CustomUserDetails)authentication.getPrincipal();
		return "organizer";
	}
}
