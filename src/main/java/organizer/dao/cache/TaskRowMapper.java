package organizer.dao.cache;


import organizer.models.Category;
import organizer.models.Task;
import organizer.models.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TaskRowMapper extends CachedRowMapper<Task> {
    private User user;

    public TaskRowMapper(User user) {
        this.user = user;
    }

    @Override
    public Task createObject(int id, ResultSet resultSet) throws SQLException {

        Task task =  new Task(
            resultSet.getString("taskname"),
            resultSet.getDate("dates"),
            resultSet.getInt("priority"),
            null,
            resultSet.getBoolean("status"),
            null
        );

        task.setId(id);
        return task;
    }
}