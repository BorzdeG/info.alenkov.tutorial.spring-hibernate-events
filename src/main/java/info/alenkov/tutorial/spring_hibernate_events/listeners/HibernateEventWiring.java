package info.alenkov.tutorial.spring_hibernate_events.listeners;

import org.hibernate.SessionFactory;
import org.hibernate.event.service.spi.EventListenerRegistry;
import org.hibernate.event.spi.EventType;
import org.hibernate.internal.SessionFactoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class HibernateEventWiring {
	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	private LastModifiedListener lastModifiedListener;

	@PostConstruct
	public void registerListeners() {
		EventListenerRegistry registry = ((SessionFactoryImpl) sessionFactory).getServiceRegistry().getService(
			EventListenerRegistry.class);

		registry.getEventListenerGroup(EventType.SAVE_UPDATE).prependListener(lastModifiedListener);
	}
}
