package organizer.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import organizer.dao.api.CategoryDao;
import organizer.logic.impl.security.CustomUserDetails;
import organizer.models.Category;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

@Controller
public class CategoryController {

	@Autowired
	@Qualifier("categoryDaoImpl")
	private CategoryDao categoryDao;

	@RequestMapping(value = "/createcategory", method = RequestMethod.POST)
	public String createCategory(Authentication authentication,
										  @Valid @ModelAttribute("categoryForm") Category categoryForm,
										  BindingResult result,
										  Map<String, Object> model){
		if(result.hasErrors())
			return "organizer";
		CustomUserDetails authorizedUser = (CustomUserDetails)authentication.getPrincipal();
		categoryForm.setUser(authorizedUser.getUser());
		categoryForm.setName(categoryForm.getName().trim());
		categoryDao.create(categoryForm);
		return "redirect:/protected";
	}

	@RequestMapping(value = "/categorylist", method = RequestMethod.GET)
	public String categoryList(Authentication authentication,
										Map<String, Object> model){
		CustomUserDetails authorizedUser = (CustomUserDetails)authentication.getPrincipal();
		ArrayList<Category> categories = (ArrayList<Category>) categoryDao.get(authorizedUser.getUser());
		Collections.sort(categories);
		model.put("catList", categories);
		return "categorylist";
	}
}
