package organizer.dao.cache;


import org.springframework.jdbc.core.RowMapper;
import organizer.models.Task;
import organizer.models.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TaskRowMapper implements RowMapper<Task> {
    private User user;

    public TaskRowMapper(User user) {
        this.user = user;
    }

//    @Override
//    public Task createObject(int id, ResultSet resultSet) throws SQLException {
//
//        Task task =  new Task(
//            resultSet.getString("taskname"),
//            resultSet.getDate("dates"),
//            resultSet.getInt("priority"),
//            null,
//            resultSet.getBoolean("status"),
//            null
//        );
//
//        task.setId(id);
//        return task;
//    }

    @Override
    public Task mapRow(ResultSet resultSet, int id) throws SQLException {
        Task task =  new Task(
                resultSet.getString("taskname"),
                resultSet.getDate("dates"),
                resultSet.getInt("priority"),
                null,
                resultSet.getBoolean("status"),
                null
        );

        task.setId(resultSet.getInt("id"));
        return task;
    }
}