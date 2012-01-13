package org.universAAL.agenda.gui.util;

public class DateTimeInstance {
	public final DateInstance date;
	public final TimeInstance time;

	public DateTimeInstance(DateInstance date, TimeInstance time) {
		this.date = date;
		this.time = time;
	}

	public boolean equals(Object o) {
		if (!(o instanceof DateTimeInstance))
			return false;
		DateTimeInstance other = (DateTimeInstance) o;
		return ((this.date.equals(other.date)) && (this.time.equals(other.time)));
	}

	public String toString() {
		return this.date + " " + this.time;
	}

}
