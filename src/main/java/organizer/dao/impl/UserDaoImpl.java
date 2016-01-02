package organizer.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import organizer.dao.api.UserDao;
import organizer.dao.cache.UserRowMapper;
import organizer.logic.impl.MessageContent;
import organizer.models.User;

@Component
@Scope("prototype")
public class UserDaoImpl implements UserDao {
	@Autowired
	@Qualifier("jdbcTemplate")
	private JdbcTemplate jdbcTemplate;

	public boolean exist(String email) {
		boolean exist = jdbcTemplate.queryForObject(
			SELECT_COUNT_EMAIL,
			new Object[]{email},
			Integer.class) != 0;
		return exist;
	}

	public boolean exist(int id) {
		boolean exist = jdbcTemplate.queryForObject(
			SELECT_COUNT_ID,
			new Object[]{id},
			Integer.class) != 0;
		return exist;
	}

	public String create(User user) {
		if (!exist(user.getEmail())) {
			jdbcTemplate.update(
				INSERT,
				user.getName());
			Integer objectsCurrentValue = jdbcTemplate.queryForObject(
				SELECT_ID, Integer.class);
			jdbcTemplate.update(
				INSERT_EMAIL,
				objectsCurrentValue,
				user.getEmail());
			jdbcTemplate.update(
				INSERT_PASSWORD,
				objectsCurrentValue,
				user.getPassword());
			jdbcTemplate.update(
				INSERT_SURNAME,
				objectsCurrentValue,
				user.getSurname());
			jdbcTemplate.update(
				INSERT_ENABLED,
				objectsCurrentValue,
				1);//1-enabled
			return String.format(MessageContent.USER_CREATED, user.getEmail());
		}
		return String.format(MessageContent.USER_ALREADY_EXIST, user.getEmail());
	}

	public void delete(int id) {
		jdbcTemplate.update(
			DELETE_OBJECTS_REF_T0_USER,
			id);
		jdbcTemplate.update(
			DELETE_OBJECT,
			id);
	}

	public void edit(User user) {
		jdbcTemplate.update(
			UPDATE_NAME,
			user.getName(),
			user.getId());
		jdbcTemplate.update(
			UPDATE_SURNAME,
			user.getSurname(),
			user.getId());
	}

	public void editPassword(User user) {
		jdbcTemplate.update(
			UPDATE_PASSWORD,
			user.getPassword(),
			user.getId());
	}

	public User get(String email) {
		if (exist(email)) {
			User user = jdbcTemplate.queryForObject(
				SELECT_USER_BY_EMAIL,
				new Object[]{email},
				new UserRowMapper());
			return user;
		}
		return null;
	}

	public User get(int id) {
		if (exist(id)) {
			User user = jdbcTemplate.queryForObject(
				SELECT_USER_BY_ID,
				new Object[]{id},
				new UserRowMapper());
			return user;
		}
		return null;
	}
}