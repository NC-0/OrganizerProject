package organizer.dao.impl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import organizer.dao.api.UserIDao;
import organizer.models.User;

public class UserDao implements UserIDao {
	private JdbcTemplate jdbcTemplate;
	private TransactionTemplate transactionTemplate;

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate=jdbcTemplate;
	}
	
	public void setTransactionTemplate(TransactionTemplate transactionTemplate) {
		this.transactionTemplate=transactionTemplate;
	}

	@SuppressWarnings("deprecation")
	public int existUser(User user) {
		int user_count = 0;
		user_count = jdbcTemplate.queryForInt("SELECT count(*) FROM ATTRIBUTES USR_ATTR WHERE USR_ATTR.ATTR_ID=6 AND USR_ATTR.VALUE=?",user.getEmail());
		return user_count;
	}
	
	
	public String create(final User user) {
		if(existUser(user)==0){
			transactionTemplate.execute(new TransactionCallbackWithoutResult() {
				@Override
				protected void doInTransactionWithoutResult(TransactionStatus status) {
					try {
						createUserTransaction(user);
					} catch (Exception e) {
						status.setRollbackOnly();
					}
				}
			});	
			return "User created";
		}
		return "User already exist";
	}

	public String delete(User user) {
		// TODO Auto-generated method stub
		return null;
	}

	public String edit(User user) {
		// TODO Auto-generated method stub
		return null;
	}

	public User get(String userName) {
		String email, password, surname;
		int userId;		

		// log - method start
		
		try {
			// get user object_id from OBJECTS
			userId = jdbcTemplate.queryForObject("select OBJECT_ID from OBJECTS "
					+ "where OBJECT_TYPE_ID = 3 " 	/* User = 3 */
					+ "and NAME = ?", new Object[] { userName }, Integer.class); 	
			
			// get user email
			email = jdbcTemplate.queryForObject("select VALUE from ATTRIBUTES "
					+ "where OBJECT_ID = ? "		/* email = 6 */
					+ "and ATTR_ID = 6", new Object[] { userId }, String.class); 
			// get user password
			password = jdbcTemplate.queryForObject("select VALUE from ATTRIBUTES "
					+ "where OBJECT_ID = ? "		/* password = 7 */
					+ "and ATTR_ID = 7", new Object[] { userId }, String.class); 		
			// get user surname
			surname = jdbcTemplate.queryForObject("select VALUE from ATTRIBUTES "
					+ "where OBJECT_ID = ? "		/* surname = 8 */
					+ "and ATTR_ID = 8", new Object[] { userId }, String.class);
			
			// create new user and fill attributes
			User user = new User(userName, surname, password, email);
			
			// logg it
			
			return user;
			
		}  catch (DataAccessException ex) {
	         // logg error ex
		}
		return null;
	}
	
	@SuppressWarnings("deprecation")
	private void createUserTransaction(User user) throws Exception{
		int objectsCurrentValue = jdbcTemplate.queryForInt("SELECT object_id.nextval FROM dual");
		System.out.println(objectsCurrentValue);
		jdbcTemplate.update("INSERT INTO objects(object_id,parent_id,object_type_id,name,description) VALUES(?,NULL,3,?,NULL)",new Object[]{objectsCurrentValue,user.getName()});
		jdbcTemplate.update("INSERT INTO attributes(attr_id,object_id,value,date_value) VALUES(6,?,?,null)",objectsCurrentValue,user.getEmail());
		jdbcTemplate.update("INSERT INTO attributes(attr_id,object_id,value,date_value) VALUES(7,?,?,null)",objectsCurrentValue,user.getPassword());
		jdbcTemplate.update("INSERT INTO attributes(attr_id,object_id,value,date_value) VALUES(8,?,?,null)",objectsCurrentValue,user.getSurname());
	}

}
