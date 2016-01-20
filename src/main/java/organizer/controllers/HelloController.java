package organizer.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class HelloController {

	@RequestMapping(value="/",method=RequestMethod.GET)
	public String helloStatic(Authentication authentication){
		if(authentication!=null)
			return "redirect:/protected";
		return "hello";
	}
}
