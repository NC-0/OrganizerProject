package organizer.dao.impl;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import organizer.dao.api.CategoryDao;
import organizer.dao.api.TaskDao;
import organizer.models.Category;
import organizer.models.Task;
import organizer.models.User;

import java.util.List;
import java.util.Locale;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring-test.xml" })
public class CacheTest {

	@Autowired
	@Qualifier("categoryDaoImpl")
	private CategoryDao categoryDao;

	@Autowired
	@Qualifier("taskDaoImpl")
	private TaskDao taskDaoImpl;

	@Test
	public void testMultiSelect(){
		Locale.setDefault(Locale.ENGLISH);
		User user = new User();
		user.setId(100664);
		for(int i=0;i<4;i++) {
			long start = System.nanoTime();
			List<Task> list = taskDaoImpl.get(user,false);
			double elapsedTimeInSec = (System.nanoTime() - start) * 1.0e-9;
			System.out.println(elapsedTimeInSec);
			System.out.println(list.size());
		}
	}

	@Test
	public void testSingleSelect(){
		Locale.setDefault(Locale.ENGLISH);
		User user = new User();
		user.setId(100664);
		long start = System.nanoTime();
		List<Task> list = taskDaoImpl.get(user,false);
		double elapsedTimeInSec = (System.nanoTime() - start) * 1.0e-9;
		System.out.println(elapsedTimeInSec);
		start = System.nanoTime();
		Task task = taskDaoImpl.get(user,604491);
		elapsedTimeInSec = (System.nanoTime() - start) * 1.0e-9;
		System.out.println(elapsedTimeInSec);
		start = System.nanoTime();
		task = taskDaoImpl.get(user,605491);
		elapsedTimeInSec = (System.nanoTime() - start) * 1.0e-9;
		System.out.println(elapsedTimeInSec);
		start = System.nanoTime();
		task = taskDaoImpl.get(user,606491);
		elapsedTimeInSec = (System.nanoTime() - start) * 1.0e-9;
		System.out.println(elapsedTimeInSec);
	}
}
