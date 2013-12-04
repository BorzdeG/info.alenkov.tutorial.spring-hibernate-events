package info.alenkov.tutorial.spring_hibernate_events.dao;

import info.alenkov.tutorial.spring_hibernate_events.AbstractTest;
import info.alenkov.tutorial.spring_hibernate_events.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

public class UserDaoTest extends AbstractTest {
	@Autowired
	private UserDao userDao;

	@BeforeMethod
	@Override
	public void beforeMethod() throws Exception {
		super.beforeMethod();
	}

	@Test
	public void testGetAll() throws Exception {
		List<User> users = userDao.getAll();
		Assert.assertEquals(users.size(), 2);
	}

}
