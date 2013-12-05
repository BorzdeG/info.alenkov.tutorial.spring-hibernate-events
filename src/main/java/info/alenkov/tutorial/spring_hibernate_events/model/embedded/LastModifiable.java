package info.alenkov.tutorial.spring_hibernate_events.model.embedded;

public interface LastModifiable {
	LastModified getLastModified();

	void setLastModified(LastModified modified);
}
