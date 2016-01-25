package organizer.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import organizer.dao.api.CategoryDao;
import organizer.logic.impl.security.CustomUserDetails;
import organizer.models.Category;
import organizer.models.Task;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;
import java.util.Map;

@Controller
public class OrganizerController {

	@Autowired
	@Qualifier("categoryDaoImpl")
	private CategoryDao categoryDao;

	@RequestMapping(value="/protected",method= RequestMethod.GET)
	public String protectedMethod(Authentication authentication,Map<String, Object> model){
		CustomUserDetails authorizedUser = (CustomUserDetails)authentication.getPrincipal();
		model.put("username",authorizedUser.getName());
		model.put("usersurname",authorizedUser.getSurname());
		return "organizer";
	}

	@RequestMapping(value = "/calendar",method = RequestMethod.GET)
	public String showCalendar(Integer cat,HttpServletRequest request,Authentication authentication){
		if(cat!=0) {
			CustomUserDetails authorizedUser = (CustomUserDetails)authentication.getPrincipal();
			Category category = categoryDao.get(cat,authorizedUser.getUser());
			request.setAttribute("cattitle", category.getName());
		}
		request.setAttribute("cat", cat);
		return "calendar";
	}
}
