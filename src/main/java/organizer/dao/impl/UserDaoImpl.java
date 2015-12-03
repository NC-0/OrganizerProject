package organizer.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import organizer.dao.api.UserDao;
import organizer.logic.impl.SqlContent;
import organizer.models.User;

public class UserDaoImpl implements UserDao {
	@Autowired
	@Qualifier("jdbcTemplate")
	private JdbcTemplate jdbcTemplate;

	public boolean exist(String email) {
		boolean exist = jdbcTemplate.queryForObject(SqlContent.SELECT_USER_COUNT,new Object[]{email},Integer.class)!=0;
		return exist;
	}
	
	public String create(User user) {
		if(!exist(user.getEmail())){
			Integer objectsCurrentValue = jdbcTemplate.queryForObject(SqlContent.SELECT_NEXT_OBJECT_ID_VALUE,Integer.class);
			jdbcTemplate.update(SqlContent.INSERT_USER_OBJECT,objectsCurrentValue,user.getName());
			jdbcTemplate.update(SqlContent.INSERT_USER_EMAIL_ATTRIBUTE,objectsCurrentValue,user.getEmail());
			jdbcTemplate.update(SqlContent.INSERT_USER_PASSWORD_ATTRIBUTE,objectsCurrentValue,user.getPassword());
			jdbcTemplate.update(SqlContent.INSERT_USER_SURNAME_ATTRIBUTE,objectsCurrentValue,user.getSurname());
			return String.format(MessageContent.USER_CREATED, user.getEmail());
		}
		return String.format(MessageContent.USER_ALREADY_EXIST, user.getEmail());
	}

	public String delete(final String email) {
		if(exist(email)){
			transactionTemplate.execute(new TransactionCallbackWithoutResult() {
				@Override
				protected void doInTransactionWithoutResult(TransactionStatus status) {
					try {
						deleteUser(email);
					} catch (Exception e) {
						status.setRollbackOnly();
					}
				}
			});
		}
		return null;
	}

	public String edit(User user) {
		// TODO Auto-generated method stub
		return null;
	}

	public User get(String email) {	
		// check if user exists 
		if (exist(email)) {
			// get user object_id from OBJECTS
			int userId = jdbcTemplate.queryForObject(SqlContent.SELECT_USER_OBJECT_ID_BY_EMAIL, new Object[] { email }, Integer.class); 	
			
			// get user name
			String userName = jdbcTemplate.queryForObject(SqlContent.SELECT_USER_NAME, new Object[] { userId }, String.class); 
			// get user password
			String password = jdbcTemplate.queryForObject(SqlContent.SELECT_USER_PASS, new Object[] { userId }, String.class); 		
			// get user surname
			String surname = jdbcTemplate.queryForObject(SqlContent.SELECT_USER_SURNAME, new Object[] { userId }, String.class);
			
			// create new user and fill attributes
			return new User(userName, surname, password, email);
		}
		// if user not exists
		return null;
	}

	private void deleteUser(String email){
		Integer id = jdbcTemplate.queryForObject(SqlContent.GET_OBJECT_ID_BY_EMAIL ,new Object[]{email},Integer.class);

// 		TODO uncommented after realization
//		taskDao.deleteByUserId(id);
//		categoryDao.deleteByUserId(id);
		jdbcTemplate.update(SqlContent.DELETE_USER_ATTRIBUTES,id);
		jdbcTemplate.update(SqlContent.DELETE_USER_OBJECT,id);
	}

}
