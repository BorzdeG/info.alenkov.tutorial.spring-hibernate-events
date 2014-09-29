package info.alenkov.tutorial.spring_hibernate_events.listeners;

import info.alenkov.tutorial.spring_hibernate_events.dao.UserDao;
import info.alenkov.tutorial.spring_hibernate_events.model.User;
import org.hibernate.event.internal.DefaultSaveOrUpdateEventListener;
import org.hibernate.event.spi.SaveOrUpdateEvent;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.AbstractAuditable;
import org.springframework.stereotype.Component;

@Component
public class LastModifiedListener extends DefaultSaveOrUpdateEventListener {
	private transient static final Logger LOG = LoggerFactory.getLogger(LastModifiedListener.class.getName());

	@Autowired
	private UserDao userDao;

	@Override
	@SuppressWarnings("unchecked")
	public void onSaveOrUpdate(SaveOrUpdateEvent event) {
		LOG.trace("object: {}", event.getObject());
		if (event.getObject() instanceof AbstractAuditable) {
			final User editor = userDao.get(2);
			final AbstractAuditable<User, Long> object = (AbstractAuditable<User, Long>) event.getObject();
			object.setLastModifiedBy(editor);
			object.setLastModifiedDate(DateTime.now());
		}
		LOG.trace("object: {}", event.getObject());
		super.onSaveOrUpdate(event);
	}
}
