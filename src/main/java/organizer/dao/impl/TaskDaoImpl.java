
package organizer.dao.impl;

import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;
import organizer.dao.api.TaskDao;
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


	public void create(User user, Task task) {
    	// get last row index from OBJECTS
		int currentId = jdbcTemplate.queryForObject(SqlContent.SELECT_NEXT_OBJECT_ID_VALUE, Integer.class);
		
		// ! get user id from OBJECTS
		int userId = jdbcTemplate.queryForObject(SqlContent.SELECT_USER_ID, new Object[] { user.getName() }, Integer.class);
		
		// Add Task object 	
		jdbcTemplate.update(SqlContent.INSERT_TASK, currentId, userId, task.getName() ); 		
		
		// Task Object - Fill all task attributes
		jdbcTemplate.update(SqlContent.INSERT_TASK_DATE, userId, task.getDate()); 
		
		jdbcTemplate.update(SqlContent.INSERT_TASK_PRIORITY, userId, task.getPriority()); 
		
		jdbcTemplate.update(SqlContent.INSERT_TASK_CATEGORY, userId, task.getCategory()); 
		
		jdbcTemplate.update(SqlContent.INSERT_TASK_STATUS, userId, task.isCompleted()); 

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

    private void updateSubTask(Task task) {
        if (task.getSubTaskList() != null)
            for (Task subTask : task.getSubTaskList()) {
                jdbcTemplate.update(SqlContent.UPDATE_SUBTASK_NAME, subTask.getName(), subTask.getId());
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