package organizer.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import organizer.dao.api.SubtaskDao;
import organizer.logic.impl.security.CustomUserDetails;
import organizer.models.Category;
import organizer.models.Subtask;
import organizer.models.Task;
import organizer.models.User;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping(value = "/subtask")
public class SubtaskController {

    @Autowired
    @Qualifier("subtaskDaoImpl")
    private SubtaskDao subtaskDaoImpl;

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String initPage(int id, Model model, Authentication authentication) {
        // Add subtask for binding attributes
        Subtask subtask = new Subtask();
        subtask.setCompleted(false);
        Task task = new Task();
        task.setId(id);
        subtask.setTask(task);
        subtask.setId(id);
        model.addAttribute("subtaskForm", subtask);

        // show view
        return "createsubtask";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String createTask(@Valid @ModelAttribute("subtaskForm") Subtask subtask,
                             BindingResult result,
                             Authentication authentication) {
        // get current user
        CustomUserDetails userDetails = (CustomUserDetails)authentication.getPrincipal();
        User user = userDetails.getUser();

        // checking errors
        if (result.hasErrors()) {
            return "createtask";
        }

        Task task = new Task();
        task.setId(subtask.getId());
        subtask.setTask(task);
        // it's ok lets do stuff
        subtaskDaoImpl.create(subtask);

        // show success view
        return "redirect:/";
    }

    @RequestMapping(value="/list",
            produces = { MediaType.APPLICATION_JSON_VALUE },
            method = RequestMethod.GET)
    public @ResponseBody List<Subtask> getSubtasks(int id) {
        Task task = new Task();
        task.setId(id);
        List<Subtask> subtasks = subtaskDaoImpl.get(task);
        for (Subtask sub : subtasks)
        System.out.println(sub.getName());
        return subtasks;
    }
}
