package organizer.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class HelloController {
		
	@RequestMapping(value="/{msg}",method=RequestMethod.GET)
	public ModelAndView hello(@PathVariable("msg") String msg){
		ModelAndView model = new ModelAndView("hello");
		model.addObject("message",msg);
		return model;
	}
	
	@RequestMapping(value="/",method=RequestMethod.GET)
	public String helloStatic(){
		return "hello";
	}
	
}
