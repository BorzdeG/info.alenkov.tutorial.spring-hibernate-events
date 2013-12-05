package info.alenkov.tutorial.spring_hibernate_events.dao;

import info.alenkov.tutorial.spring_hibernate_events.AbstractTest;
import info.alenkov.tutorial.spring_hibernate_events.model.AnObject;
import info.alenkov.tutorial.spring_hibernate_events.model.embedded.LastModified;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

public class AnObjectDaoTest extends AbstractTest {
	private transient static final Logger LOG = LoggerFactory.getLogger(AnObjectDaoTest.class.getName());

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
		Assert.assertEquals(list.size(), 3);
	}

	@Test(dependsOnMethods = {"testGetAll"}, groups = {"lastModified"})
	public void testLastModifiedNull() {
		AnObject anObject = anObjectDao.get(1);
		LOG.trace("anObject: {}", anObject);
		Assert.assertNull(anObject.getLastModified());
	}

	@Test(dependsOnMethods = {"testLastModifiedNull"}, groups = {"lastModified"})
	public void testLastModifiedNotNull() {
		AnObject anObject = anObjectDao.get(3);
		LOG.trace("anObject: {}", anObject);
		LastModified lastModified = anObject.getLastModified();
		Assert.assertNotNull(lastModified);
		Assert.assertEquals(lastModified.getLastEditor().getId(), 1);
	}
}
