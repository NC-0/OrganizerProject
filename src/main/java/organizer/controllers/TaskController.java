package organizer.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import organizer.dao.api.CategoryDao;
import organizer.dao.api.TaskDao;
import organizer.logic.impl.security.CustomUserDetails;
import organizer.models.Category;
import organizer.models.Task;
import organizer.models.User;

import javax.validation.Valid;
import java.util.*;

@Controller
@RequestMapping(value = "/task")
public class TaskController {

	@Autowired
	@Qualifier("taskDaoImpl")
	private TaskDao taskDaoImpl;

	@Autowired
	@Qualifier("categoryDaoImpl")
	private CategoryDao categoryDaoImpl;

	/**
	 * map that contains user categories
	 */
	private Map<String, Category> categories = new LinkedHashMap<String, Category>();
	/**
	 * map that contains user priorities
	 */
	private static Map<String, Integer> priorities = new LinkedHashMap<String, Integer>();
	static {
		priorities.put("Very High", 1);
		priorities.put("High", 2);
		priorities.put("Normal", 3);
		priorities.put("Low", 4);
		priorities.put("So Low", 5);
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String initPage(Model model, Authentication authentication) {
		// Add task for binding attributes
		Task task = new Task();
		task.setCompleted(false);
		model.addAttribute("taskForm", task);
		model.addAttribute("title", "Create");

		// get current user
		CustomUserDetails userDetails = (CustomUserDetails)authentication.getPrincipal();
		User user = userDetails.getUser();
		initAttributes(user, model);

		// show view
		return "createtask";
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String editGetTask(Integer id, Model model, Authentication authentication) {
		// get current user
		CustomUserDetails userDetails = (CustomUserDetails)authentication.getPrincipal();
		User user = userDetails.getUser();

		// Get task for binding attributes
		Task task = taskDaoImpl.get(user, id);
		model.addAttribute("taskForm", task);
		initAttributes(user, model);
		// show view
		return "edittask";
	}

	private void initAttributes(User user, Model model) {
		// fill categories from db to categories and jsp attribute
		List<Category> categoryList = categoryDaoImpl.get(user);
		List<String> categoryDisplayList  = new ArrayList<String>();
		for (Category current : categoryList) {
			categories.put(current.getName(), current);
			categoryDisplayList.add(current.getName());
		}

		// add categories to user
		user.setCategories(categoryList);

		// Init set of user categories loaded from db
		model.addAttribute("categories", categoryDisplayList);

		// Init list of user priorities
		List<String> priorityList = new ArrayList<String>();
		priorityList.addAll(priorities.keySet());
		model.addAttribute("priorities", priorityList);
	}

	/**
	 * @param task - get information that user had input
	 * @param result - var that contains binding errors
	 * @return redirect to create-task page if was validation error otherwise to the success page
	 */
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public String createTask(@Valid @ModelAttribute("taskForm") Task task,
									 BindingResult result,
									 Authentication authentication,
									 Model model) {
		// get current user
		CustomUserDetails userDetails = (CustomUserDetails)authentication.getPrincipal();
		User user = userDetails.getUser();

		// checking errors
		if (result.hasErrors()) {
			initAttributes(user, model);
			return "createtask";
		}

		// it's ok lets do stuff
		int priority = priorities.get(task.getPriority_str());
		Category category = categories.get(task.getCategory_str());
		task.setPriority(priority);
		task.setCategory(category);
		taskDaoImpl.create(user.getId(), task);

		// show success view
		return "redirect:/";
	}

	/**
	 * @param task - get information that user had input
	 * @param result - var that contains binding errors
	 * @return redirect to create-task page if was validation error otherwise to the success page
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public String editTask(@Valid @ModelAttribute("taskForm") Task task,
							 BindingResult result,
							 Authentication authentication,
							 Model model) {
		// get current user
		CustomUserDetails userDetails = (CustomUserDetails)authentication.getPrincipal();
		User user = userDetails.getUser();

		// checking errors
		if (result.hasErrors()) {
			System.out.print("error edit task");
			initAttributes(user, model);
			return "edittask";
		}

		// it's ok lets do stuff
		int priority = priorities.get(task.getPriority_str());
		Category category = categories.get(task.getCategory_str());
		task.setPriority(priority);
		task.setCategory(category);
		taskDaoImpl.edit(task);

		// show success view
		return "redirect:/";
	}

	@RequestMapping(value="/list",
		produces = { MediaType.APPLICATION_JSON_VALUE },
		method = RequestMethod.GET)
	public  @ResponseBody
	List<Task> getTasks(Authentication authentication) {
		CustomUserDetails userDetails = (CustomUserDetails)authentication.getPrincipal();
		User user = userDetails.getUser();
		List<Task> tasks = taskDaoImpl.get(user);
		return tasks;
	}

	@RequestMapping(value="/listcat/{cat}",
		produces = { MediaType.APPLICATION_JSON_VALUE },
		method = RequestMethod.GET)
	public  @ResponseBody
	List<Task> getTasksCat(Authentication authentication,@PathVariable("cat") int cat) {
		CustomUserDetails userDetails = (CustomUserDetails)authentication.getPrincipal();
		User user = userDetails.getUser();
		Category category = new Category();
		category.setId(cat);
		List<Task> tasks = taskDaoImpl.getByCat(user,category);
		return tasks;
	}

}


