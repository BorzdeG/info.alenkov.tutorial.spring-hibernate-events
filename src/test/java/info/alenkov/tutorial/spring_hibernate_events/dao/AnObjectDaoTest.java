package info.alenkov.tutorial.spring_hibernate_events.dao;

import info.alenkov.tutorial.spring_hibernate_events.AbstractTest;
import info.alenkov.tutorial.spring_hibernate_events.model.AnObject;
import info.alenkov.tutorial.spring_hibernate_events.model.User;
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
	@Autowired
	private UserDao     userDao;

	private User testEditor;

	@BeforeMethod
	@Override
	public void beforeMethod() throws Exception {
		super.beforeMethod();
		testEditor = userDao.get(1);
	}

	@Test
	public void testGetAll() throws Exception {
		List<AnObject> list = anObjectDao.getAll();
		Assert.assertEquals(list.size(), 3);
	}

	@Test(dependsOnMethods = {"testGetAll"}, groups = {"lastModified"})
	public void testLastModifiedNull() {
		AnObject anObject = anObjectDao.get(1);
		Assert.assertNull(anObject.getLastModified());
	}

	@Test(dependsOnMethods = {"testLastModifiedNull"}, groups = {"lastModified"})
	public void testLastModifiedNotNull() {
		AnObject anObject = anObjectDao.get(3);
		LastModified lastModified = anObject.getLastModified();
		Assert.assertNotNull(lastModified);
		Assert.assertEquals(lastModified.getLastEditor().getId(), 1);
	}

	@Test(dependsOnGroups = {"lastModified"}, groups = {"update"})
	public void testUpdateNull() {
		final int id = 1;
		final String newValue = "newValue";

		AnObject anObject = anObjectDao.get(id);
		Assert.assertNotEquals(anObject.getValue(), newValue);

		anObject.setValue(newValue);
		anObjectDao.saveOrUpdate(anObject);

		AnObject anObject1 = anObjectDao.get(id);
		Assert.assertEquals(anObject1.getValue(), newValue);

		LastModified lastModified = anObject1.getLastModified();
		Assert.assertNotNull(lastModified);
	}

	@Test(dependsOnMethods = {"testUpdateNull"}, groups = {"update"})
	public void testUpdateNotNull() {
		final int id = 3;
		final String newValue = "newValue";

		AnObject anObject = anObjectDao.get(id);
		LastModified lastModified = anObject.getLastModified();
		Assert.assertNotEquals(anObject.getValue(), newValue);

		anObject.setValue(newValue);
		anObjectDao.saveOrUpdate(anObject);

		AnObject anObject1 = anObjectDao.get(id);
		Assert.assertEquals(anObject1.getValue(), newValue);

		LastModified lastModified1 = anObject1.getLastModified();
		Assert.assertFalse(lastModified1.equals(lastModified));
		Assert.assertTrue(lastModified1.getLastUpdated().after(lastModified.getLastUpdated()));
	}

	@Test(dependsOnGroups = {"update"})
	public void testInsert() {
		AnObject anObject = new AnObject();
		anObject.setValue("value");

		Assert.assertNull(anObject.getLastModified());

		LOG.trace("anObject: {}", anObject);
		anObjectDao.saveOrUpdate(anObject);
		Assert.assertNotNull(anObject.getLastModified());
	}

	@Test(dependsOnMethods = {"testInsert"})
	public void testInsertOwerrideModified() {
		AnObject anObject = new AnObject();
		anObject.setValue("value");

		Assert.assertNull(anObject.getLastModified());

		LastModified lastModified = new LastModified(testEditor);
		anObject.setLastModified(lastModified);

		anObjectDao.saveOrUpdate(anObject);
		final LastModified lastModified1 = anObject.getLastModified();
		Assert.assertNotNull(lastModified1);
		Assert.assertFalse(lastModified.equals(lastModified1));
		Assert.assertTrue(lastModified1.getLastUpdated().after(lastModified.getLastUpdated()));
	}

	@Test(dependsOnGroups = {"lastModified"}, groups = {"onlySave"})
	public void testOnlySave() {
		AnObject anObject = new AnObject();
		anObject.setValue("value");

		Assert.assertNull(anObject.getLastModified());

		LOG.trace("anObject: {}", anObject);
		anObjectDao.save(anObject);
		Assert.assertNotNull(anObject.getLastModified());
	}

	@Test(dependsOnMethods = {"testOnlySave"}, groups = {"onlySave"})
	public void testOnlySaveOwerrideModified() {
		AnObject anObject = new AnObject();
		anObject.setValue("value");

		Assert.assertNull(anObject.getLastModified());

		LastModified lastModified = new LastModified(testEditor);
		anObject.setLastModified(lastModified);

		anObjectDao.save(anObject);
		final LastModified lastModified1 = anObject.getLastModified();
		Assert.assertNotNull(lastModified1);
		Assert.assertFalse(lastModified.equals(lastModified1));
		Assert.assertTrue(lastModified1.getLastUpdated().after(lastModified.getLastUpdated()));
	}

	@Test(dependsOnGroups = {"lastModified"}, groups = {"onlyUpdate"})
	public void testOnlyUpdateNull() {
		final int id = 1;
		final String newValue = "newValue";

		AnObject anObject = anObjectDao.get(id);
		Assert.assertNotEquals(anObject.getValue(), newValue);

		anObject.setValue(newValue);
		anObjectDao.saveOrUpdate(anObject);

		AnObject anObject1 = anObjectDao.get(id);
		Assert.assertEquals(anObject1.getValue(), newValue);

		LastModified lastModified = anObject1.getLastModified();
		Assert.assertNotNull(lastModified);
	}

	@Test(dependsOnMethods = {"testOnlyUpdateNull"}, groups = {"onlyUpdate"})
	public void testOnlyUpdateNotNull() {
		final int id = 3;
		final String newValue = "newValue";

		AnObject anObject = anObjectDao.get(id);
		LastModified lastModified = anObject.getLastModified();
		Assert.assertNotEquals(anObject.getValue(), newValue);

		anObject.setValue(newValue);
		anObjectDao.saveOrUpdate(anObject);

		AnObject anObject1 = anObjectDao.get(id);
		Assert.assertEquals(anObject1.getValue(), newValue);

		LastModified lastModified1 = anObject1.getLastModified();
		Assert.assertFalse(lastModified1.equals(lastModified));
		Assert.assertTrue(lastModified1.getLastUpdated().after(lastModified.getLastUpdated()));
	}
}
