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

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Controller
public class CategoryController {

	@Autowired
	@Qualifier("categoryDaoImpl")
	private CategoryDao categoryDao;

	@RequestMapping(value = "/addcategory", method = RequestMethod.GET)
	public String addCategory(Map<String, Object> model){
		Category category = new Category();
		model.put("categoryForm",category);
		return "createcategory";
	}

	@RequestMapping(value = "/createcategory", method = RequestMethod.POST)
	public String createCategory(Authentication authentication,
										  @Valid @ModelAttribute("categoryForm") Category categoryForm,
										  BindingResult result,
										  Map<String, Object> model){
		if(result.hasErrors())
			return "createcategory";
		CustomUserDetails authorizedUser = (CustomUserDetails)authentication.getPrincipal();
		categoryForm.setUser(authorizedUser.getUser());
		categoryForm.setName(categoryForm.getName().trim());
		categoryDao.create(categoryForm);
		return "redirect:/protected";
	}

	@RequestMapping(value = "/categorylist", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody	List<Category> categoryList(Authentication authentication){
		CustomUserDetails authorizedUser = (CustomUserDetails)authentication.getPrincipal();
		ArrayList<Category> categories = (ArrayList<Category>) categoryDao.get(authorizedUser.getUser());
		Collections.sort(categories);
		return categories;
	}

	@RequestMapping(value = "/updatecategory", method = RequestMethod.GET)
	public String updateCategory(HttpServletRequest request){
		Category category = new Category();
		category.setId(Integer.parseInt(request.getParameter("categoryid")));
		category.setName(String.valueOf(request.getParameter("categoryname")));
		request.setAttribute("categoryForm",category);
		return "editcategory";
	}

	@RequestMapping(value = "/editcategory", method = RequestMethod.POST)
	public String editCategory(Authentication authentication,
										@Valid @ModelAttribute("categoryForm") Category categoryForm,
										BindingResult result,
										Map<String, Object> model){
		if(result.hasErrors())
			return "editcategory";
		categoryForm.setName(categoryForm.getName().trim());
		categoryDao.update(categoryForm);
		return "redirect:/protected";
	}

	@RequestMapping(value = "/deletecategory", method = RequestMethod.POST)
	public String deleteCategory(HttpServletRequest request){
		Category category = new Category();
		category.setId(Integer.parseInt(request.getParameter("categoryid")));
		categoryDao.delete(category);
		return "redirect:/protected";
	}
}
