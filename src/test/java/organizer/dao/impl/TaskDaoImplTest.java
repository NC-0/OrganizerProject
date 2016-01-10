package organizer.dao.impl;

import org.junit.Ignore;
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
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/organizer-web-servlet-test.xml" )
@Transactional
@Rollback(true)
public class TaskDaoImplTest {
    @Autowired
    @Qualifier("taskDaoImpl")
    TaskDaoImpl taskDao;

    @Autowired
    @Qualifier("userDaoImpl")
    UserDaoImpl userDao;

    @Autowired
    @Qualifier("categoryDaoImpl")
    CategoryDaoImpl categoryDao;

    @Test
    public void testCreateAndGet() throws Exception {
        User user = new User("MyUserEmailNewTest@mail.ru","MyUserPassword","MyUserName","MyUserSurname");
        userDao.create(user);
        Category category = new Category("Category_name_test");
        category.setUser(user);
        categoryDao.create(category);
        user = userDao.get(user.getId());
        Task task = new Task("task_name",new Date(),4,category,false,new ArrayList<Subtask>());
        task.setUser(user);
        taskDao.create(task);
        assertTrue(taskDao.get(user).contains(task));
    }

    @Test
    public void testUpdateCategory() throws Exception {
        User user = new User("MyUserEmailNewTest@mail.ru","MyUserPassword","MyUserName","MyUserSurname");
        userDao.create(user);
        Category category = new Category("Category_name_test");
        category.setUser(user);
        categoryDao.create(category);
        Category category2 = new Category("Category2_name_test");
        category2.setUser(user);
        categoryDao.create(category2);
        Task task = new Task("task_name",new Date(),4,category,false,new ArrayList<Subtask>());
        task.setUser(user);
        taskDao.create(task);
        Date newDate = new Date();
        task.setDate(newDate);
        task.setName("name after update");
        task.setCompleted(true);
        task.setCategory(category2);
        taskDao.update(task);
        user = userDao.get(user.getId());
        assertTrue(taskDao.get(user).contains(task));

    }
    @Test

    public void testGetListTask(){
        User user = new User("MyUserEmailNewTest@mail.ru","MyUserPassword","MyUserName","MyUserSurname");
        userDao.create(user);
        Category category = new Category("Category_name_test");
        category.setUser(user);
        categoryDao.create(category);
        user = userDao.get(user.getId());
        Task task = new Task("task_name",new Date(),4,category,false,new ArrayList<Subtask>());
        task.setUser(user);
        taskDao.create(task);
        Task task2 = new Task("task2_name",new Date(),4,category,false,new ArrayList<Subtask>());
        task2.setUser(user);
        taskDao.create(task2);
        assertTrue(taskDao.get(user).contains(task2));
        assertTrue(taskDao.get(user).contains(task));
    }

    @Test
    public void testDelete() throws Exception {
        User user = new User("MyUserEmailNewTest@mail.ru","MyUserPassword","MyUserName","MyUserSurname");
        userDao.create(user);
        Category category = new Category("Category_name_test");
        category.setUser(user);
        categoryDao.create(category);
        user = userDao.get(user.getId());
        Task task = new Task("task_name",new Date(),4,category,false,new ArrayList<Subtask>());
        task.setUser(user);
        taskDao.create(task);
        assertTrue(taskDao.get(user).contains(task));
        taskDao.delete(task);
        assertFalse(taskDao.get(user).contains(task));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateWithIllegalUserId(){
        User user = new User("MyUserEmailNewTest@mail.ru","MyUserPassword","MyUserName","MyUserSurname");
        userDao.create(user);
        Category category = new Category("Category_name_test");
        category.setUser(user);
        categoryDao.create(category);
        user = userDao.get(user.getId());
        Task task = new Task("task_name",new Date(),4,category,false,new ArrayList<Subtask>());
        user.setId(0);
        task.setUser(user);
        taskDao.create(task);
    }

}