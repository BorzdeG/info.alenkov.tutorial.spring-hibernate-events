package info.alenkov.tutorial.spring_hibernate_events.model.embedded;

import info.alenkov.tutorial.spring_hibernate_events.model.User;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.time.DateUtils;

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

	public LastModified() {
	}

	public LastModified(User lastEditor) {
		this();
		this.setLastEditor(lastEditor);
		this.setLastUpdated(Calendar.getInstance());
	}

	@Override
	public String toString() {
		final ToStringBuilder builder = new ToStringBuilder(this);
		builder.append("lastUpdated", lastUpdated != null ? lastUpdated.getTimeInMillis() : null);
		builder.append("lastEditor", lastEditor != null ? lastEditor.getId() : null);
		return builder.toString();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof LastModified)) {
			return false;
		}
		LastModified o = (LastModified) obj;
		if (null != this.getLastEditor() && null == o.getLastEditor()) {
			return false;
		}
		if (this.getLastEditor().getId() != o.getLastEditor().getId()) {
			// here it is necessary to make a comparison to the class User, but for example, and so come
			return false;
		}
		return DateUtils.truncatedEquals(this.getLastUpdated(), o.getLastUpdated(), Calendar.MILLISECOND);
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
