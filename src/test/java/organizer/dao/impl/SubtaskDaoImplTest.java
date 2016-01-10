package organizer.dao.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import organizer.models.Category;
import organizer.models.Subtask;
import organizer.models.Task;
import organizer.models.User;
import java.util.Date;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/organizer-web-servlet-test.xml" )
@Transactional
@Rollback(true)
public class SubtaskDaoImplTest {
    @Autowired
    @Qualifier("userDaoImpl")
    UserDaoImpl userDao;
    @Autowired
    @Qualifier("taskDaoImpl")
    TaskDaoImpl taskDao;
    @Autowired
    @Qualifier("categoryDaoImpl")
    CategoryDaoImpl categoryDao;
    @Autowired
    @Qualifier("subtaskDaoImpl")
    SubtaskDaoImpl subtaskDao;

    @Test
    public void testCreate() throws Exception {
        User user = new User("MyUserEmailNewTest@mail.ru","MyUserPassword","MyUserName","MyUserSurname");
        userDao.create(user);
        Category category = new Category("Category_name_test");
        category.setUser(user);
        categoryDao.create(category);
        user = userDao.get(user.getId());
        Task task = new Task("task_name",new Date(),4,category,false,null);
        task.setUser(user);
        taskDao.create(task);
        Subtask subtask = new Subtask(-1,"subtask_name",false,task);
        subtask.setTask(task);
        subtaskDao.create(subtask);
        assertTrue(subtaskDao.get(task).contains(subtask));
    }

    @Test
    public void testUpdate() throws Exception {
        User user = new User("MyUserEmailNewTest@mail.ru","MyUserPassword","MyUserName","MyUserSurname");
        userDao.create(user);
        Category category = new Category("Category_name_test");
        category.setUser(user);
        categoryDao.create(category);
        user = userDao.get(user.getId());
        Task task = new Task("task_name",new Date(),4,category,false,null);
        task.setUser(user);
        taskDao.create(task);
        Subtask subtask = new Subtask(-1,"subtask_name",false,task);
        subtask.setTask(task);
        subtaskDao.create(subtask);
        subtask.setName("subtask_name2");
        subtask.setCompleted(true);
        subtaskDao.update(subtask);
        assertTrue(subtaskDao.get(task).contains(subtask));
    }

    @Test
    public void testGetTaskWithAnySubtask() throws Exception {
        User user = new User("MyUserEmailNewTest@mail.ru","MyUserPassword","MyUserName","MyUserSurname");
        userDao.create(user);
        Category category = new Category("Category_name_test");
        category.setUser(user);
        categoryDao.create(category);
        user = userDao.get(user.getId());
        Task task = new Task("task_name",new Date(),4,category,false,new ArrayList<Subtask>());
        task.setUser(user);
        taskDao.create(task);
        Subtask subtask = new Subtask(-1,"subtask_name",false,task);
        subtask.setTask(task);
        subtaskDao.create(subtask);
        task.setSubtasks(Arrays.asList(subtask));
        Subtask subtask2 = new Subtask(-1,"subtask2_name",false,task);
        subtask2.setTask(task);
        subtaskDao.create(subtask2);
        task.setSubtasks(Arrays.asList(subtask,subtask2));
        assertTrue(taskDao.get(user).contains(task));

    }

    @Test
    public void testTaskInSubtask(){
        User user = new User("MyUserEmailNewTest@mail.ru","MyUserPassword","MyUserName","MyUserSurname");
        userDao.create(user);
        Category category = new Category("Category_name_test");
        category.setUser(user);
        categoryDao.create(category);
        user = userDao.get(user.getId());
        Task task = new Task("task_name",new Date(),4,category,false,new ArrayList<Subtask>());
        task.setUser(user);
        taskDao.create(task);
        Subtask subtask = new Subtask(-1,"subtask_name",false,task);
        subtask.setTask(task);
        subtaskDao.create(subtask);
        task.setSubtasks(Arrays.asList(subtask));
        assertTrue(subtaskDao.get(task).get(0).getTask().equals(task));
    }

    @Test
    public void testDelete() throws Exception {
        User user = new User("MyUserEmailNewTest@mail.ru","MyUserPassword","MyUserName","MyUserSurname");
        userDao.create(user);
        Category category = new Category("Category_name_test");
        category.setUser(user);
        categoryDao.create(category);
        user = userDao.get(user.getId());
        Task task = new Task("task_name",new Date(new java.util.Date().getTime()),4,category,false,null);
        task.setUser(user);
        taskDao.create(task);
        Subtask subtask = new Subtask(-1,"subtask_name",false,task);
        subtask.setTask(task);
        subtaskDao.create(subtask);
        assertTrue(subtaskDao.get(task).contains(subtask));
        subtaskDao.delete(subtask);
        assertFalse(subtaskDao.get(task).contains(subtask));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateWithIllegalTaskId(){
        User user = new User("MyUserEmailNewTest@mail.ru","MyUserPassword","MyUserName","MyUserSurname");
        userDao.create(user);
        Category category = new Category("Category_name_test");
        category.setUser(user);
        categoryDao.create(category);
        user = userDao.get(user.getId());
        Task task = new Task("task_name",new Date(),4,category,false,null);
        task.setUser(user);
        taskDao.create(task);
        Subtask subtask = new Subtask(-1,"subtask_name",false,task);
        task.setId(0);
        subtask.setTask(task);
        subtaskDao.create(subtask);
     }

}