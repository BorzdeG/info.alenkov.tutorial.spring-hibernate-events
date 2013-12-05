package info.alenkov.tutorial.spring_hibernate_events.model;

import info.alenkov.tutorial.spring_hibernate_events.model.embedded.LastModifiable;
import info.alenkov.tutorial.spring_hibernate_events.model.embedded.LastModified;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "anObject")
public class AnObject implements LastModifiable {
	@Id
	@GeneratedValue
	private long         id;
	@Column
	private String       value;
	@Embedded
	private LastModified lastModified;

	@Override
	public String toString() {
		final ToStringBuilder builder = new ToStringBuilder(this);
		builder.append("id", id);
		builder.append("value", value);
		builder.append("lastModified", lastModified);
		return builder.toString();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public LastModified getLastModified() {
		return lastModified;
	}

	@Override
	public void setLastModified(LastModified lastModified) {
		this.lastModified = lastModified;
	}
}
