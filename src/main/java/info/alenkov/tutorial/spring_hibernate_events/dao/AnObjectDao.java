package info.alenkov.tutorial.spring_hibernate_events.dao;

import info.alenkov.tutorial.spring_hibernate_events.model.AnObject;
import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AnObjectDao extends AbstractDao {
	private transient static final Logger LOG = LoggerFactory.getLogger(AnObjectDao.class.getName());

	private Criteria getCriteriaAnObject() {
		return getSession().createCriteria(AnObject.class, "o");
	}

	@Override
	public <T> List<T> getAll() {
		Criteria criteria = getCriteriaAnObject();

		@SuppressWarnings("unchecked") List<AnObject> list = criteria.list();
		LOG.trace(LOG_MSG_FOUND_ITEMS, list.size());
		@SuppressWarnings("unchecked") List<T> list1 = (List<T>) list;
		return list1;
	}

	@Override
	public <T> T get(long id) {
		LOG.debug("id: {}", id);

		Criteria criteria = getCriteriaAnObject();
		Criterion eqId = Restrictions.eq("id", id);
		criteria.add(eqId);

		List list = criteria.list();
		LOG.trace(AbstractDao.LOG_MSG_FOUND_ITEMS, list.size());
		@SuppressWarnings("unchecked") final T item = (list.size() == 1) ? (T) list.get(0) : null;
		return item;
	}
}
