package organizer.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import organizer.dao.api.UserDao;
import organizer.logic.impl.MessageContent;
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

	public void delete(int id) {
		jdbcTemplate.update(DELETE_OBJECTS_REF_T0_USER, id);
		jdbcTemplate.update(DELETE_OBJECT, id);
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

	// TODO: Implement get user by id method
	@Override
	public User get(int id) {
		throw new UnsupportedOperationException();
	}
}
