package organizer.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;

import organizer.dao.api.UserDao;
import organizer.logic.impl.MessageContent;
import organizer.logic.impl.SqlContent;
import organizer.models.User;

public class UserDaoImpl implements UserDao {
	@Autowired
	@Qualifier("jdbcTemplate")
	private JdbcTemplate jdbcTemplate;

	public boolean exist(String email) {
		boolean exist = jdbcTemplate.queryForObject(UserDao.SELECT_COUNT, new Object[]{email}, Integer.class) != 0;
		return exist;
	}
	
	public String create(User user) {
		if (!exist(user.getEmail())) {
			jdbcTemplate.update(UserDao.INSERT, user.getName());
			Integer objectsCurrentValue = jdbcTemplate.queryForObject(UserDao.SELECT_ID, Integer.class);
			jdbcTemplate.update(UserDao.INSERT_EMAIL, objectsCurrentValue, user.getEmail());
			jdbcTemplate.update(UserDao.INSERT_PASSWORD, objectsCurrentValue, user.getPassword());
			jdbcTemplate.update(UserDao.INSERT_SURNAME, objectsCurrentValue, user.getSurname());
			jdbcTemplate.update(UserDao.INSERT_ENABLED, objectsCurrentValue, 1);//1-enabled
			return String.format(MessageContent.USER_CREATED, user.getEmail());
		}
		return String.format(MessageContent.USER_ALREADY_EXIST, user.getEmail());
	}

	public String delete(final String email) {
		if (exist(email)) {
			transactionTemplate.execute(new TransactionCallbackWithoutResult() {
				@Override
				protected void doInTransactionWithoutResult(TransactionStatus status) {
					try {
						deleteUser(email);
					}
					catch (Exception e) {
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
			String userName, password, surname, enabled;
			List<Map<String, Object>> list = jdbcTemplate.queryForList(UserDao.GET_USER_INFO, new Object[]{email});
			
			int userId = (int) list.get(0).get("userid");
			userName = (String) list.get(1).get("username");
			password = (String) list.get(2).get("password");
			surname = (String) list.get(3).get("surname");
			enabled = (String) list.get(4).get("enabled");

			// create new user and fill attributes
			User user = new User(email, password, userName, surname);
			user.setId(userId);
			user.setRole("USER_ROLE");
			user.setEnabled(Boolean.valueOf(enabled));
			return user;
		}
		// if user not exists
		return null;
	}

	private void deleteUser(String email) {
		Integer id = jdbcTemplate.queryForObject(SqlContent.GET_OBJECT_ID_BY_EMAIL, new Object[]{email}, Integer.class);

// 		TODO uncommented after realization
//		taskDao.deleteByUserId(id);
//		categoryDao.deleteByUserId(id);
		jdbcTemplate.update(SqlContent.DELETE_USER_ATTRIBUTES, id);
		jdbcTemplate.update(SqlContent.DELETE_USER_OBJECT, id);
	}

}
