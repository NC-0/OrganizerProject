package organizer.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import organizer.dao.api.UserDao;
import organizer.dao.cache.CategoryRowMapper;
import organizer.dao.cache.SubtaskRowMapper;
import organizer.dao.cache.UserRowMapper;
import organizer.logic.impl.MessageContent;
import organizer.models.Task;
import organizer.models.User;

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
				"FALSE");//TRUE-enabled
			return String.format(MessageContent.USER_CREATED, user.getEmail());
		}
		throw new UnsupportedOperationException();
	}

	public void delete(User user) {
		jdbcTemplate.update(
				DELETE_OBJECTS_REF_T0_USER,
				user.getId()
		);
		jdbcTemplate.update(
				DELETE_OBJECT,
				user.getId()
		);

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
		jdbcTemplate.update(
			UPDATE_ENABLED,
			String.valueOf(user.isEnabled()),
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
			return get(SELECT_USER_BY_EMAIL,email);
		}
		return null;
	}

	public User get(int id) {
		if (exist(id)) {
			return get(SELECT_USER_BY_ID,String.valueOf(id));
		}
		return null;
	}

	private User get(String query,String value){
		User user = jdbcTemplate.queryForObject(
				query,
				new Object[]{value},
				new UserRowMapper());
		user.setCategories(jdbcTemplate.query(SELECT_CATEGORIES_BY_USER_ID,new CategoryRowMapper(),user.getId()));
		user.setTasks(jdbcTemplate.query(SELECT_TASK_BY_USER_ID, new TaskRowMapper(user),user.getId()));
		for (Task task : user.getTasks()) {
			task.setSubtasks(jdbcTemplate.query(SELECT_SUBTASKS_BY_TASK_ID, new SubtaskRowMapper(task),task.getId()));
		}
		return user;
	}
}
