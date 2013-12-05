package info.alenkov.tutorial.spring_hibernate_events.listeners;

import info.alenkov.tutorial.spring_hibernate_events.dao.UserDao;
import info.alenkov.tutorial.spring_hibernate_events.model.User;
import info.alenkov.tutorial.spring_hibernate_events.model.embedded.LastModifiable;
import info.alenkov.tutorial.spring_hibernate_events.model.embedded.LastModified;
import org.hibernate.event.internal.DefaultSaveOrUpdateEventListener;
import org.hibernate.event.spi.SaveOrUpdateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LastModifiedListener extends DefaultSaveOrUpdateEventListener {
	private transient static final Logger LOG = LoggerFactory.getLogger(LastModifiedListener.class.getName());

	@Autowired
	private UserDao userDao;

	@Override
	public void onSaveOrUpdate(SaveOrUpdateEvent event) {
		LOG.trace("object: {}", event.getObject());
		if (event.getObject() instanceof LastModifiable) {
			LastModified lastModified = new LastModified((User) userDao.get(2));
			((LastModifiable) event.getObject()).setLastModified(lastModified);
			LOG.trace("object: {}", event.getObject());
		}
		super.onSaveOrUpdate(event);
	}
}
