package info.alenkov.tutorial.spring_hibernate_events.dao;

import info.alenkov.tutorial.spring_hibernate_events.AbstractTest;
import info.alenkov.tutorial.spring_hibernate_events.model.AnObject;
import info.alenkov.tutorial.spring_hibernate_events.model.User;
import org.joda.time.DateTime;
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
		Assert.assertNull(anObject.getLastModifiedBy());
	}

	@Test(dependsOnMethods = {"testLastModifiedNull"}, groups = {"lastModified"})
	public void testLastModifiedNotNull() {
		AnObject anObject = anObjectDao.get(3);
		final User lastModifiedBy = anObject.getLastModifiedBy();
		Assert.assertNotNull(lastModifiedBy);
		Assert.assertEquals(lastModifiedBy.getId(), 1);
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

		final User lastModifiedBy = anObject.getLastModifiedBy();
		Assert.assertNotNull(lastModifiedBy);
	}

	@Test(dependsOnMethods = {"testUpdateNull"}, groups = {"update"})
	public void testUpdateNotNull() {
		final int id = 3;
		final String newValue = "newValue";

		AnObject anObject = anObjectDao.get(id);
		final User lastModifiedBy = anObject.getLastModifiedBy();
		final DateTime lastModifiedDate = anObject.getLastModifiedDate();
		Assert.assertNotEquals(anObject.getValue(), newValue);

		anObject.setValue(newValue);
		anObjectDao.saveOrUpdate(anObject);

		AnObject anObject1 = anObjectDao.get(id);
		Assert.assertEquals(anObject1.getValue(), newValue);

		final User lastModifiedBy1 = anObject1.getLastModifiedBy();
		final DateTime lastModifiedDate1 = anObject1.getLastModifiedDate();
		Assert.assertFalse(lastModifiedBy1.equals(lastModifiedBy));
		Assert.assertTrue(lastModifiedDate1.isAfter(lastModifiedDate));
	}

	@Test(dependsOnGroups = {"update"})
	public void testInsert() {
		AnObject anObject = new AnObject();
		anObject.setValue("value");

		Assert.assertNull(anObject.getLastModifiedBy());

		LOG.trace("anObject: {}", anObject);
		anObjectDao.saveOrUpdate(anObject);
		Assert.assertNotNull(anObject.getLastModifiedBy());
	}

	@Test(dependsOnMethods = {"testInsert"})
	public void testInsertOwerrideModified() {
		AnObject anObject = new AnObject();
		anObject.setValue("value");

		Assert.assertNull(anObject.getLastModifiedBy());

		final DateTime lastModifiedDate = DateTime.now();
		anObject.setLastModifiedBy(testEditor);
		anObject.setLastModifiedDate(lastModifiedDate);

		anObjectDao.saveOrUpdate(anObject);
		final DateTime lastModifiedDate1 = anObject.getLastModifiedDate();
		Assert.assertNotNull(lastModifiedDate1);
		Assert.assertFalse(lastModifiedDate.equals(lastModifiedDate1));
		Assert.assertTrue(lastModifiedDate1.isAfter(lastModifiedDate));
	}

	@Test(dependsOnGroups = {"lastModified"}, groups = {"onlySave"})
	public void testOnlySave() {
		AnObject anObject = new AnObject();
		anObject.setValue("value");

		Assert.assertNull(anObject.getLastModifiedBy());

		LOG.trace("anObject: {}", anObject);
		anObjectDao.save(anObject);
		Assert.assertNotNull(anObject.getLastModifiedBy());
	}

	@Test(dependsOnMethods = {"testOnlySave"}, groups = {"onlySave"})
	public void testOnlySaveOwerrideModified() {
		AnObject anObject = new AnObject();
		anObject.setValue("value");

		Assert.assertNull(anObject.getLastModifiedBy());

		final DateTime lastModifiedDate = DateTime.now();
		anObject.setLastModifiedBy(testEditor);
		anObject.setLastModifiedDate(lastModifiedDate);

		anObjectDao.save(anObject);
		final DateTime lastModifiedDate1 = anObject.getLastModifiedDate();
		Assert.assertNotNull(lastModifiedDate1);
		Assert.assertFalse(lastModifiedDate.equals(lastModifiedDate1));
		Assert.assertTrue(lastModifiedDate1.isAfter(lastModifiedDate));
	}

	@Test(dependsOnGroups = {"lastModified"}, groups = {"onlyUpdate"})
	public void testOnlyUpdateNull() {
		final int id = 1;
		final String newValue = "newValue";

		AnObject anObject = anObjectDao.get(id);
		Assert.assertNotEquals(anObject.getValue(), newValue);

		anObject.setValue(newValue);
		anObjectDao.update(anObject);

		AnObject anObject1 = anObjectDao.get(id);
		Assert.assertEquals(anObject1.getValue(), newValue);

		Assert.assertNotNull(anObject.getLastModifiedBy());
	}

	@Test(dependsOnMethods = {"testOnlyUpdateNull"}, groups = {"onlyUpdate"})
	public void testOnlyUpdateNotNull() {
		final int id = 3;
		final String newValue = "newValue";

		AnObject anObject = anObjectDao.get(id);
		final DateTime lastModifiedDate = anObject.getLastModifiedDate();
		Assert.assertNotEquals(anObject.getValue(), newValue);

		anObject.setValue(newValue);
		anObjectDao.update(anObject);

		AnObject anObject1 = anObjectDao.get(id);
		Assert.assertEquals(anObject1.getValue(), newValue);

		final DateTime lastModifiedDate1 = anObject.getLastModifiedDate();
		Assert.assertNotNull(lastModifiedDate1);
//		Assert.assertFalse(lastModifiedDate1.equals(lastModifiedDate));
		Assert.assertTrue(lastModifiedDate1.isAfter(lastModifiedDate));
	}
}
