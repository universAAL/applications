package org.universAAL.agenda.gui.util;

public class TimeInstance {
	public final int hour, minute, second;

	public TimeInstance(final int hour, final int minute, final int second) {
		this.hour = hour;
		this.minute = minute;
		this.second = second;
	}

	public TimeInstance(final int hour, final int minute) {
		this.hour = hour;
		this.minute = minute;
		this.second = 0;
	}

	public String toString() {
		return this.hour + ":" + this.minute + ":" + this.hour;
	}

	public boolean equals(Object o) {
		if (!(o instanceof TimeInstance))
			return false;
		TimeInstance other = (TimeInstance) o;
		return this.hour == other.hour && this.minute == other.minute
				&& this.second == other.second;
	}
}
