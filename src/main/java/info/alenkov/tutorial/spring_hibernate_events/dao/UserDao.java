package info.alenkov.tutorial.spring_hibernate_events.dao;

import info.alenkov.tutorial.spring_hibernate_events.model.User;
import org.hibernate.Criteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("userDao")
public class UserDao extends AbstractDao {
	private transient static final Logger LOG = LoggerFactory.getLogger(UserDao.class.getName());

	private Criteria getCriteriaUser() {
		return getSession().createCriteria(User.class, "user");
	}

	@Override
	public <T> List<T> getAll() {
		Criteria cUsers = getCriteriaUser();

		@SuppressWarnings("unchecked") List<User> list = cUsers.list();
		LOG.trace(LOG_MSG_FOUND_ITEMS, list.size());
		@SuppressWarnings("unchecked") List<T> list1 = (List<T>) list;
		return list1;
	}
}
