package organizer.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import organizer.controllers.validation.TaskValidator;
import organizer.dao.api.CategoryDao;
import organizer.dao.api.TaskDao;
import organizer.logic.impl.security.CustomUserDetails;
import organizer.models.Category;
import organizer.models.Task;
import organizer.models.User;

import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/createtask")
public class TaskController {

	@Autowired
	@Qualifier("taskDaoImpl")
	private TaskDao taskDaoImpl;

	@Autowired
	@Qualifier("categoryDaoImpl")
	private CategoryDao categoryDaoImpl;

//	@Autowired
//	private TaskValidator validator;

	/**
	 * map that contains user categories
	 */
	private Map<String, Category> categories = new LinkedHashMap<>();
	/**
	 * map that contains user priorities
	 */
	private static Map<String, Integer> priorities = new LinkedHashMap<>();
	static {
		priorities.put("Very High", 1);
		priorities.put("High", 2);
		priorities.put("Normal", 3);
		priorities.put("Low", 4);
		priorities.put("So Low", 5);
	}

	@RequestMapping(method = RequestMethod.GET)
	public String initPage(Model model, Authentication authentication) {
		// Add task for binding attributes
		Task task = new Task();
		task.setCompleted(false);
		model.addAttribute("taskForm", task);

		// get current user
		CustomUserDetails userDetails = (CustomUserDetails)authentication.getPrincipal();
		User user = userDetails.getUser();

		// fill categories from db
		List<Category> categoryList = categoryDaoImpl.get(user);
		for (Category current : categoryList) {
			categories.put(current.getName(), current);
		}

		// show view
		return "createtask";
	}

	/**
	 * Init list of user priorities
	 */
	@ModelAttribute("priorities")
	public Set<String> populatePriorities() {
		Set<String> priorityList = new LinkedHashSet<>();
		priorityList.addAll(priorities.keySet());
		return priorityList;
	}

	/**
	 * Init list of user categories loaded from db
	 */
	@ModelAttribute("categories")
	public Set<String> populateCategories() {
		Set<String> categoryList = new LinkedHashSet<>();
		categoryList.addAll(categories.keySet());
		return categoryList;
	}

	/**	 *
	 * @param task - get information that user had input
	 * @param result - var that contains binding errors
	 * @return redirect to create-task page if was validation error otherwise to the success page
	 */
	@RequestMapping(method = RequestMethod.POST)
	public String createTask(@Valid @ModelAttribute("taskForm") Task task,
							 BindingResult result,
							 Authentication authentication) {
		// checking errors
		if (result.hasErrors()) {
			return "createtask";
		}

		// it's ok lets do stuff
		CustomUserDetails user = (CustomUserDetails)authentication.getPrincipal();
		int priority = priorities.get(task.getPriority_str());
		Category category = categories.get(task.getCategory_str());
		task.setPriority(priority);
		task.setCategory(category);
		taskDaoImpl.create(user.getId(), task);

		// show success view
		return "task-success";
	}



}


