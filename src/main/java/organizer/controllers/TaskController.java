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
@RequestMapping("/task")
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

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String initPage(Model model, Authentication authentication) {
		// Add task for binding attributes
		Task task = new Task();
		task.setCompleted(false);
		model.addAttribute("taskForm", task);

		// get current user
		CustomUserDetails userDetails = (CustomUserDetails)authentication.getPrincipal();
		User user = userDetails.getUser();

		// fill categories from db to categories and jsp attribute
		List<Category> categoryList = categoryDaoImpl.get(user);
		Set<String> categoryDisplaySet  = new LinkedHashSet<>();
		for (Category current : categoryList) {
			categories.put(current.getName(), current);
			categoryDisplaySet.add(current.getName());
		}

		// Init set of user categories loaded from db
		model.addAttribute("categories", categoryDisplaySet);

		// Init list of user priorities
		Set<String> priorityList = new LinkedHashSet<>();
		priorityList.addAll(priorities.keySet());
		model.addAttribute("priorities", priorityList);

		// show view
		return "createtask";
	}

	/**
	 * @param task - get information that user had input
	 * @param result - var that contains binding errors
	 * @return redirect to create-task page if was validation error otherwise to the success page
	 */
	@RequestMapping(value = "/create", method = RequestMethod.POST)
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

	@RequestMapping(value="/show",
			produces = { MediaType.TEXT_HTML_VALUE },
			method = RequestMethod.GET)
	public String viewTasks () {
		return "tasklist";
	}


	@RequestMapping(value="/list",
					produces = { MediaType.APPLICATION_JSON_VALUE },
					method = RequestMethod.GET)
	public  @ResponseBody
	List<Task> getTasks(/*@ModelAttribute(value="listForm") Task task*/Authentication authentication) {
		CustomUserDetails userDetails = (CustomUserDetails)authentication.getPrincipal();
		User user = userDetails.getUser();
		/*Task task = new Task("name", new Date(), 1, null, false, null);
		task.setCategory_str("Work");
		task.setPriority_str("High");
		Task task2 = new Task("name2", new Date(), 2, null, true, null);
		task2.setCategory_str("Work2");
		task2.setPriority_str("High2");*/
//		System.out.println("task list method");
		List<Task> tasks = taskDaoImpl.get(user);
//		tasks.add(task);
//		tasks.add(task2);
		return tasks;
	}

}


