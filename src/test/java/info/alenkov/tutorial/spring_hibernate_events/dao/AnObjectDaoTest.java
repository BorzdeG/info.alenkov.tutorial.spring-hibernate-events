package info.alenkov.tutorial.spring_hibernate_events.dao;

import info.alenkov.tutorial.spring_hibernate_events.AbstractTest;
import info.alenkov.tutorial.spring_hibernate_events.model.AnObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

public class AnObjectDaoTest extends AbstractTest {
	@Autowired
	private AnObjectDao anObjectDao;

	@BeforeMethod
	@Override
	public void beforeMethod() throws Exception {
		super.beforeMethod();
	}

	@Test
	public void testGetAll() throws Exception {
		List<AnObject> list = anObjectDao.getAll();
		Assert.assertEquals(list.size(), 2);
	}
}
