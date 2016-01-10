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
import organizer.models.User;


import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/organizer-web-servlet-test.xml" )
@Rollback(true)
@Transactional
public class CategoryDaoImplTest {

    @Autowired
    @Qualifier("userDaoImpl")
    UserDaoImpl userDao;
    @Autowired
    @Qualifier("taskDaoImpl")
    TaskDaoImpl taskDao;
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
        assertTrue(categoryDao.get(user).contains(category));
       }

    @Test
    public void testDelete(){
        User user = new User("MyUserEmailNewTest@mail.ru","MyUserPassword","MyUserName","MyUserSurname");
        userDao.create(user);
        Category category = new Category("Category_name_test");
        category.setUser(user);
        categoryDao.create(category);
        assertTrue(categoryDao.get(user).contains(category));
        category.setUser(user);
        categoryDao.delete(category);
        assertTrue(categoryDao.get(user).isEmpty());
    }
    @Test
    public void testUpdate(){
        User user = new User("MyUserEmailNewTest@mail.ru","MyUserPassword","MyUserName","MyUserSurname");
        userDao.create(user);
        Category category = new Category("Category_name_test");
        category.setUser(user);
        categoryDao.create(category);
        category.setName("after Update");
        categoryDao.update(category);
        assertTrue(categoryDao.get(user).contains(category));

    }
}