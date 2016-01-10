package organizer.dao.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ExceptionHandler;
import organizer.dao.api.SubtaskDao;
import organizer.logic.impl.MessageContent;
import organizer.models.Category;
import organizer.models.Subtask;
import organizer.models.Task;
import organizer.models.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/organizer-web-servlet-test.xml" )
@Rollback(true)
@Transactional
public class UserDaoImplTest {


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
        assertEquals(user,userDao.get(user.getEmail()));
    }

    @Test(expected = DuplicateKeyException.class)
    public void testCreateExistUser() throws Exception {
        User user = new User("MyUserEmailNewTest@mail.ru","MyUserPassword","MyUserName","MyUserSurname");
        userDao.create(user);
        userDao.create(user);
    }

    @Test
    public void testExistById() throws Exception {
        User user = new User("MyUserEmailNewTest@mail.ru","MyUserPassword","MyUserName","MyUserSurname");
        userDao.create(user);
        assertTrue(userDao.exist(user.getId()));
    }

    @Test
    public void testNotExistById() throws Exception {
        User user = new User("MyNotExistUserEmailNewTest@mail.ru","MyUserPassword","MyUserName","MyUserSurname");
        assertFalse(userDao.exist(user.getEmail()));
    }

    @Test
    public void testExistByEmail() throws Exception {
        User user = new User("MyUserEmailNewTest@mail.ru","MyUserPassword","MyUserName","MyUserSurname");
        userDao.create(user);
        assertTrue(userDao.exist(user.getEmail()));
    }

    @Test
    public void testNotExistByEmail() throws Exception {
        User user = new User("MyNotUserEmailNewTest@mail.ru","MyUserPassword","MyUserName","MyUserSurname");
        assertFalse(userDao.exist(user.getEmail()));
    }

    @Test
    public void testDelete() throws Exception {
        User user = new User("MyUserEmailNewTest@mail.ru","MyUserPassword","MyUserName","MyUserSurname");
        userDao.create(user);
        assertTrue(userDao.exist(user.getId()));
        userDao.delete(user);
        assertFalse(userDao.exist(user.getId()));
    }

    @Test
    public void testEdit() throws Exception {
        User user = new User("MyUserEmailNewTest@mail.ru","MyUserPassword","MyUserName","MyUserSurname");
        userDao.create(user);
        User userUpdate = userDao.get("MyUserEmailNewTest@mail.ru");
        userUpdate.setName("name after update");
//        userUpdate.setEmail("email after update");
//        userUpdate.setEnabled(true);
//        userUpdate.setPassword("password after update");
//        userUpdate.setRole("role after update");
        userUpdate.setSurname("surname after update");
        userDao.edit(userUpdate);
        User userGet = userDao.get("MyUserEmailNewTest@mail.ru");
        assertTrue(userUpdate.equals(userGet));
    }

    @Test
    public void testGet() throws Exception {
        User user = new User("MyUserEmailNewTest@mail.ru","MyUserPassword","MyUserName","MyUserSurname");
        userDao.create(user);
        User userGet = userDao.get("MyUserEmailNewTest@mail.ru");
        assertEquals(userGet.getId(),user.getId());

    }

    @Test
    public void testGetWithCategory() throws Exception {
        User user = new User("MyUserEmailNewTest@mail.ru","MyUserPassword","MyUserName","MyUserSurname");
        userDao.create(user);
        Category category = new Category("Category_name_test");
        category.setUser(user);
        categoryDao.create(category);
        User userGet = userDao.get("MyUserEmailNewTest@mail.ru");
        assertEquals(userGet.getId(),user.getId());

    }

    @Test
    public void testGetWithTask() throws Exception {
        User user = new User("MyUserEmailNewTest@mail.ru","MyUserPassword","MyUserName","MyUserSurname");
        userDao.create(user);
        Category category = new Category("Category_name_test");
        category.setUser(user);
        categoryDao.create(category);
        user.setCategories(Arrays.asList(category));
        Task task = new Task("task_name",new Date(),4,category,false,new ArrayList<Subtask>());
        task.setUser(user);
        task.setCategory(category);
        taskDao.create(task);
        user.setTasks(Arrays.asList(task));
        User userGet = userDao.get("MyUserEmailNewTest@mail.ru");
        assertTrue(userGet.equals(user));

    }

    @Test
    public void testGetWithSubtask() throws Exception {
        User user = new User("MyUserEmailNewTest@mail.ru","MyUserPassword","MyUserName","MyUserSurname");
        userDao.create(user);
        Category category = new Category("Category_name_test");
        category.setUser(user);
        categoryDao.create(category);
        user.setCategories(Arrays.asList(category));
        Task task = new Task("task_name",new Date(),4,category,false,null);
        task.setUser(user);
        taskDao.create(task);
        user.setTasks(Arrays.asList(task));
        Subtask subtask = new Subtask(-1,"subtask_name",false,task);
        subtask.setTask(task);
        subtaskDao.create(subtask);
        task.setSubtasks(Arrays.asList(subtask));
        User userGet = userDao.get("MyUserEmailNewTest@mail.ru");
        assertTrue(userGet.equals(user));

    }

    @Test
    public void testEquals(){
        User user = new User("MyUserEmailNewTest@mail.ru","MyUserPassword","MyUserName","MyUserSurname");
        userDao.create(user);
        User userGet = userDao.get("MyUserEmailNewTest@mail.ru");
        assertTrue(userGet.equals(user));
    }
}