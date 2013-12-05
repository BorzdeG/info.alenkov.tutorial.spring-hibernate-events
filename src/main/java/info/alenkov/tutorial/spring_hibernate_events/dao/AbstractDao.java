package info.alenkov.tutorial.spring_hibernate_events.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

abstract public class AbstractDao {
	private transient static final Logger LOG = LoggerFactory.getLogger(AbstractDao.class.getName());

	protected static final String LOG_MSG_FOUND_ITEMS = "found items: {}";

	@Autowired
	private SessionFactory sessionFactory;

	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	abstract public <T> List<T> getAll();

	@Transactional(readOnly = false)
	public <T> void saveOrUpdate(T item) {
		LOG.trace("item before: {}", item);
		getSession().saveOrUpdate(item);
		LOG.debug("item after: {}", item);
	}

	public abstract <T> T get(long id);
}
