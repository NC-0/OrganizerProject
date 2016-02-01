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

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
	 * map of user's categories
	 */
	private Map<String, Category> categories = new LinkedHashMap<String, Category>();

	enum Priority {
		Very_High,
		High,
		Normal,
		Low,
		So_Low
	}

	private void initAttributes(User user, Model model) {
		// fill categories from db to categories and jsp attribute
		List<Category> categoryList = categoryDaoImpl.get(user);
		List<String> categoryDisplayList  = new ArrayList<String>();
		for (Category current : categoryList) {
			categories.put(current.getName(), current);
			categoryDisplayList.add(current.getName());
		}
		// Init user's categories set in jsp
		model.addAttribute("categories", categoryDisplayList);

		// Init list of user priorities
		List<String> priorityList = new ArrayList<String>();
		for (Priority prior : Priority.values()) {
			priorityList.add(prior.name());
		}
		model.addAttribute("priorities", priorityList);
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String initPage(Model model, Authentication authentication,HttpServletRequest request) {
		String requestUri =  request.getHeader("Referer");
		requestUri=requestUri.replace("http://localhost:8081","");
		request.getSession().setAttribute("redir",requestUri);
		request.setAttribute("redir",requestUri);
		// Add task for binding attributes
		Task task = new Task();
		task.setCompleted(false);
		model.addAttribute("taskForm", task);

		// get current user
		CustomUserDetails userDetails = (CustomUserDetails)authentication.getPrincipal();
		User user = userDetails.getUser();
		initAttributes(user, model);

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
							 Authentication authentication,
							 Model model,
							 HttpServletRequest request) {
		// get current user
		CustomUserDetails userDetails = (CustomUserDetails)authentication.getPrincipal();
		User user = userDetails.getUser();

		// checking errors
		if (result.hasErrors()) {
			initAttributes(user, model);
			return "createtask";
		}

		// it's ok lets do stuff
		int priority = Priority.valueOf(task.getPriority_str()).ordinal();
		Category category = categories.get(task.getCategory_str());
		task.setPriority(priority);
		task.setCategory(category);
		taskDaoImpl.create(user.getId(), task);

		// show success view
		return "redirect:"+request.getSession().getAttribute("redir");
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String editGetTask(Integer id, Model model, Authentication authentication,HttpServletRequest request) {
		String requestUri =  request.getHeader("Referer");
		requestUri=requestUri.replace("http://localhost:8081","");
		request.getSession().setAttribute("redir",requestUri);
		request.setAttribute("redir",requestUri);
		// get current user
		CustomUserDetails userDetails = (CustomUserDetails)authentication.getPrincipal();
		User user = userDetails.getUser();

		// Get task for binding attributes
		Task task = taskDaoImpl.get(user, id);
		model.addAttribute("taskForm", task);
		initAttributes(user, model);
		model.addAttribute("priority", Priority.values()[task.getPriority()]);
		// show view
		return "edittask";
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public String editTask(@Valid @ModelAttribute("taskForm") Task task,
							 BindingResult result,
							 Authentication authentication,
							 Model model,
							 HttpServletRequest request) {
		// get current user
		CustomUserDetails userDetails = (CustomUserDetails)authentication.getPrincipal();
		User user = userDetails.getUser();

		// checking errors
		if (result.hasErrors()) {
			initAttributes(user, model);
			return "edittask";
		}

		// it's ok lets do stuff
		int priority = Priority.valueOf(task.getPriority_str()).ordinal();
		Category category = categories.get(task.getCategory_str());
		task.setPriority(priority);
		task.setCategory(category);
		taskDaoImpl.edit(task);

		// show success view
		return "redirect:"+request.getSession().getAttribute("redir");
	}

	@RequestMapping(value="/list/{status}",
					produces = { MediaType.APPLICATION_JSON_VALUE },
					method = RequestMethod.GET)
	public @ResponseBody List<Task> getTasks(@PathVariable("status") boolean isCompleted, Authentication authentication) {
		CustomUserDetails userDetails = (CustomUserDetails)authentication.getPrincipal();
		User user = userDetails.getUser();
		List<Task> tasks = taskDaoImpl.get(user, isCompleted);
		for (Task task : tasks) {
			if (task.isCompleted() == isCompleted) {
				task.setPriority_str(Priority.values()[task.getPriority()].name());
			} else {
				tasks.remove(task);
			}
		}
		return tasks;
	}

	@RequestMapping(value="/listcat/{cat}",
					produces = { MediaType.APPLICATION_JSON_VALUE },
					method = RequestMethod.GET)
	public @ResponseBody List<Task> getTasksCat(boolean isCompleted,
												Authentication authentication,
												@PathVariable("cat") int cat) {
		CustomUserDetails userDetails = (CustomUserDetails)authentication.getPrincipal();
		User user = userDetails.getUser();
		Category category = new Category();
		category.setId(cat);
		List<Task> tasks = taskDaoImpl.getByCat(user,category);
		for (Task task : tasks) {
			task.setPriority_str(Priority.values()[task.getPriority()].name());
		}
		return tasks;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public String deleteTask(HttpServletRequest request, Authentication authentication) {
		Task task = new Task();
		int id = Integer.parseInt(request.getParameter("id"));
		task.setId(id);
		taskDaoImpl.delete(task);
		return "redirect:/";
	}

	@RequestMapping(value = "/complete",
		method = RequestMethod.POST)
	public String completeTask(HttpServletRequest request) {
		int id = Integer.parseInt(request.getParameter("id"));
		boolean completed = Boolean.parseBoolean(request.getParameter("completed"));

		Task task = new Task(); //name, date, prior, cat, status, null);
		task.setCompleted(!completed);
		task.setId(id);

		taskDaoImpl.updateStatus(task);
		return "redirect:/";
	}
}


