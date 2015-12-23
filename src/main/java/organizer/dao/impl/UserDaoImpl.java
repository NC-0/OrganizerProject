package organizer.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import organizer.dao.api.UserDao;
import organizer.logic.impl.MessageContent;
import organizer.logic.impl.SqlContent;
import organizer.models.User;

import java.sql.ResultSet;
import java.sql.SQLException;

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
			User user = jdbcTemplate.queryForObject(UserDao.GET_USER_INFO, new Object[]{email}, new RowMapper<User>() {
				public User mapRow(ResultSet resultSet, int i) throws SQLException {
					User user = new User(resultSet.getString("email"),resultSet.getString("password"),resultSet.getString("username"),resultSet.getString("surname"));
					user.setRole("USER_ROLE");
					user.setId(Integer.valueOf(resultSet.getString("userid")));
					user.setEnabled(Boolean.valueOf(resultSet.getString("enabled")));
					return user;
				}
			});
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
