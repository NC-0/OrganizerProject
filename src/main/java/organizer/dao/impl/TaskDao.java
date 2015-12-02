package organizer.dao.impl;

import java.util.ArrayList;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import organizer.dao.api.TaskIDao;
import organizer.models.Task;
import organizer.models.User;

public class TaskDao implements TaskIDao {
	private JdbcTemplate jdbcTemplate;
	private TransactionTemplate transactionTemplate;
	
	// constructor-injection to supply the PlatformTransactionManager and JDBC
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	public void setTransactionTemplate(TransactionTemplate transactionTemplate) {
		this.transactionTemplate = transactionTemplate;
	}
	
	public void create(final User user, final Task task) {
		// log - method start
		
		// transaction
		transactionTemplate.execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus status) {
				try {
					// get last row index from OBJECTS
					int currentId = jdbcTemplate.queryForObject("SELECT object_id.nextval FROM dual", Integer.class);
					
					// ! get user id from OBJECTS
					int userId = jdbcTemplate.queryForObject("SELECT object_id FROM objects WHERE name = ?", 
							new Object[] { user.getName() }, Integer.class);
					
					// Add Task object 	
					jdbcTemplate.update("INSERT INTO OBJECTS (OBJECT_ID, PARENT_ID, OBJECT_TYPE_ID, NAME, DESCRIPTION) "
							+ "VALUES (?, ?, 1, ?, NULL)", currentId, userId, task.getName() ); 	/* task obj_type_id=1 */	
					
					// Task Object - Fill all task attributes
					jdbcTemplate.update("INSERT INTO ATTRIBUTES (ATTR_ID, OBJECT_ID, VALUE, DATE_VALUE) "
							+ "VALUES (1, ?, NULL, ?)", userId, task.getDate()); 		/* add date */
					
					jdbcTemplate.update("INSERT INTO ATTRIBUTES (ATTR_ID, OBJECT_ID, VALUE, DATE_VALUE) "
							+ "VALUES (2, ?, ?, NULL)", userId, task.getPriority()); 	/* add priority */
					
					jdbcTemplate.update("INSERT INTO ATTRIBUTES (ATTR_ID, OBJECT_ID, VALUE, DATE_VALUE)"
							+ "VALUES (3, ?, ?, NULL)", userId, task.getCategory()); 	/* add category */
					
					jdbcTemplate.update("INSERT INTO ATTRIBUTES (ATTR_ID, OBJECT_ID, VALUE, DATE_VALUE) "
							+ "VALUES (4, ?, ?, NULL)", userId, task.isCompleted()); 	/* add status */
					
					// auto commit transaction
					
					// logg commit
					
				}  catch (DataAccessException ex) {
					// rollback transaction
					status.setRollbackOnly();
			         // logg error
				}
			}
		});
		return;		 
	}

	public void delete() {
		// TODO Auto-generated method stub
		
	}

	public void edit() {
		// TODO Auto-generated method stub
		
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
