
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
    @Autowired
    @Qualifier("transactionTemplate")
    private TransactionTemplate transactionTemplate;

    public boolean isExist(Task task) {
        boolean exist = jdbcTemplate.update(SqlContent.SELECT_TASK_BY_OBJECT_ID, task.getId()) > 0;
        return exist;
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
                            new Object[]{user.getName()}, Integer.class);

                    // Add Task object
                    jdbcTemplate.update("INSERT INTO OBJECTS (OBJECT_ID, PARENT_ID, OBJECT_TYPE_ID, NAME, DESCRIPTION) "
                            + "VALUES (?, ?, 1, ?, NULL)", currentId, userId, task.getName()); 	/* task obj_type_id=1 */

                    // Task Object - Fill all task attributes
                    jdbcTemplate.update("INSERT INTO ATTRIBUTES (ATTR_ID, OBJECT_ID, VALUE, DATE_VALUE) "
                            + "VALUES (1, ?, NULL, ?)", currentId, task.getDate()); 		/* add date */

                    jdbcTemplate.update("INSERT INTO ATTRIBUTES (ATTR_ID, OBJECT_ID, VALUE, DATE_VALUE) "
                            + "VALUES (2, ?, ?, NULL)", currentId, task.getPriority()); 	/* add priority */

                    jdbcTemplate.update("INSERT INTO ATTRIBUTES (ATTR_ID, OBJECT_ID, VALUE, DATE_VALUE)"
                            + "VALUES (3, ?, ?, NULL)", currentId, task.getCategory()); 	/* add category */

                    jdbcTemplate.update("INSERT INTO ATTRIBUTES (ATTR_ID, OBJECT_ID, VALUE, DATE_VALUE) "
                            + "VALUES (4, ?, ?, NULL)", currentId, task.isCompleted()); 	/* add status */


                } catch (DataAccessException ex) {
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

    public void edit(final Task task) {
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus status) {
                try {
                    updateTask(task);
                } catch (Exception e) {
                    status.setRollbackOnly();
                }
            }
        });
    }

    private void updateSubTask(Task task) {
        if (task.getSubTaskList() != null)
            for (Task subTask : task.getSubTaskList()) {
                jdbcTemplate.update(SqlContent.UPDATE_SUBTASK_NAME, subTask.getName(), subTask.getId());
            }
    }

    private void updateTask(Task task) {
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