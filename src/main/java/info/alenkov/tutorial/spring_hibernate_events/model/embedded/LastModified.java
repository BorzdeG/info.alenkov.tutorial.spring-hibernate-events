package info.alenkov.tutorial.spring_hibernate_events.model.embedded;

import info.alenkov.tutorial.spring_hibernate_events.model.User;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;
import java.util.Calendar;

@Embeddable
public class LastModified {
	@Column
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar lastUpdated;
	@OneToOne
	@JoinColumn(name = "lastEditor_id")
	private User     lastEditor;

	@Override
	public String toString() {
		final ToStringBuilder builder = new ToStringBuilder(this);
		builder.append("lastUpdated", lastUpdated != null ? lastUpdated.getTimeInMillis() : null);
		builder.append("lastEditor", lastEditor != null ? lastEditor.getId() : null);
		return builder.toString();
	}

	public Calendar getLastUpdated() {
		return lastUpdated;
	}

	public void setLastUpdated(Calendar updated) {
		this.lastUpdated = updated;
	}

	public User getLastEditor() {
		return lastEditor;
	}

	public void setLastEditor(User editor) {
		this.lastEditor = editor;
	}
}
