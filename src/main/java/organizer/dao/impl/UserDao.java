package organizer.dao.impl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.jdbc.core.JdbcTemplate;

import organizer.dao.api.UserIDao;
import organizer.models.User;

public class UserDao implements UserIDao {
	private JdbcTemplate jdbcTemplate;

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate=jdbcTemplate;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public boolean isExistUser(final User user) {
		String boolVal = (String) jdbcTemplate.execute(
			    new CallableStatementCreator() {
			        public CallableStatement createCallableStatement(Connection con) throws SQLException {
			            CallableStatement cs = con.prepareCall("{? = call ISEXISTUSER(?)}");
			            cs.registerOutParameter(1, Types.VARCHAR);
			            cs.setString(2,user.getEmail());
			            return cs;
			        }
			    },
			    new CallableStatementCallback() {
					public Object doInCallableStatement(CallableStatement cs) throws SQLException {
						cs.execute();
						String boolValue = cs.getString(1);
						return boolValue;
					}
				}
			);
		if(boolVal.equals("TRUE"))
			return true;
		return false;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String createUser(final User user) {
		if(isExistUser(user)==false){
			jdbcTemplate.execute(
				new CallableStatementCreator() {
				public CallableStatement createCallableStatement(Connection con)throws SQLException {
					CallableStatement callableStatement = con.prepareCall("{call CREATEUSER(?,?,?,?)}");
					callableStatement.setString(1, user.getEmail());
					callableStatement.setString(2, user.getPassword());
					callableStatement.setString(3, user.getName());
					callableStatement.setString(4, user.getSurname());
					return callableStatement;
				}
			},
			new CallableStatementCallback() {
				public Object doInCallableStatement(CallableStatement cs) throws SQLException {
					cs.execute();
					return null;
				}
			});
			return "User created";
		}
		return "User already exist";
	}

	public String deleteUser(User user) {
		// TODO Auto-generated method stub
		return null;
	}

	public String editUser(User user) {
		// TODO Auto-generated method stub
		return null;
	}

	public User getUser() {
		// TODO Auto-generated method stub
		return null;
	}


}
