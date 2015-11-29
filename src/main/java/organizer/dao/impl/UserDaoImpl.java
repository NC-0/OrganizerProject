package organizer.dao.impl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import organizer.dao.api.UserDao;
import organizer.logic.impl.SqlContent;
import organizer.models.User;

public class UserDaoImpl implements UserDao {
	@Autowired
	@Qualifier("jdbcTemplate")
	private JdbcTemplate jdbcTemplate;
	@Autowired
	@Qualifier("transactionTemplate")
	private TransactionTemplate transactionTemplate;

	public int exist(User user) {
		Integer userCount = 0;
		userCount = jdbcTemplate.queryForObject(SqlContent.SELECT_USER_COUNT,new Object[]{user.getEmail()},Integer.class);
		return userCount;
	}
	
	public String create(final User user) {
		if(exist(user)==0){
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

	public User get() {
		// TODO Auto-generated method stub
		return null;
	}
	
	private void createUserTransaction(User user) throws Exception{
		Integer objectsCurrentValue = jdbcTemplate.queryForObject(SqlContent.SELECT_NEXT_OBJECT_ID_VALUE,Integer.class);
		jdbcTemplate.update(SqlContent.INSERT_USER_OBJECT,objectsCurrentValue,user.getName());
		jdbcTemplate.update(SqlContent.INSERT_USER_EMAIL_ATTRIBUTE,objectsCurrentValue,user.getEmail());
		jdbcTemplate.update(SqlContent.INSERT_USER_PASSWORD_ATTRIBUTE,objectsCurrentValue,user.getPassword());
		jdbcTemplate.update(SqlContent.INSERT_USER_SURNAME_ATTRIBUTE,objectsCurrentValue,user.getSurname());
	}

}
