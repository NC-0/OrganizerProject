
package organizer.dao.impl;

import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import organizer.dao.api.TaskDao;
import organizer.dao.api.UserDao;
import organizer.logic.impl.SqlContent;
import organizer.models.Task;
import organizer.models.User;

class TaskDaoImpl implements TaskDao {
    @Autowired
    @Qualifier("jdbcTemplate")
    private JdbcTemplate jdbcTemplate;

    public boolean isExist(Task task) {
        boolean exist = jdbcTemplate.update(SqlContent.SELECT_TASK_BY_OBJECT_ID, task.getId()) > 0;
        return exist;
    }


	public void create(int userId, Task task) {
		// Add Task object 	
		jdbcTemplate.update(TaskDao.INSERT_TASK, userId, task.getName() ); 
		
		// get current task object_id from OBJECTS and set it
		int taskId = jdbcTemplate.queryForObject(UserDao.SELECT_CURR_OBJECT_ID_VALUE, Integer.class);
		task.setId(taskId);
		
		// Task Object - Fill all task attributes
		jdbcTemplate.update(TaskDao.INSERT_TASK_DATE, taskId, task.getDate()); 
		jdbcTemplate.update(TaskDao.INSERT_TASK_PRIORITY, taskId, task.getPriority()); 
		jdbcTemplate.update(TaskDao.INSERT_TASK_CATEGORY, taskId, task.getCategory()); 
		jdbcTemplate.update(TaskDao.INSERT_TASK_STATUS, taskId, task.isCompleted()); 
		
		// Add reference between Task and User
		jdbcTemplate.update(TaskDao.INSERT_TASK_REF_USER, taskId, userId);
    }

    public void delete() {
        // TODO Auto-generated method stub

    }

    public void edit(Task task) {
    	 if (isExist(task)) {
             updateSubTask(task);
             jdbcTemplate.update(SqlContent.UPDATE_TASK_NAME, task.getName(), task.getId());
             jdbcTemplate.update(SqlContent.UPDATE_TASK_DATE, task.getDate(), task.getId());
             jdbcTemplate.update(SqlContent.UPDATE_TASK_PRIORITY, task.getPriority(), task.getId());
             jdbcTemplate.update(SqlContent.UPDATE_TASK_CATEGORY, task.getCategory(), task.getId());
             jdbcTemplate.update(SqlContent.UPDATE_TASK_STATUS, task.isCompleted(), task.getId());
         }
    }


    public Task get() {
        // TODO Auto-generated method stub
        return null;
    }


    public ArrayList<Task> getSubTaskList() {
        // TODO Auto-generated method stub
        return null;
    }

}