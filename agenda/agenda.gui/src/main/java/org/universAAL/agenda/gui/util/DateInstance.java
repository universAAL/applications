package org.universAAL.agenda.gui.util;

public class DateInstance {
	public final int year, month, day;

	public DateInstance(final int year, final int month, final int day) {
		this.year = year;
		this.month = month;
		this.day = day;
	}

	public String toString() {
		return this.day + "/" + this.month + "/" + this.year;
	}

	public boolean equals(Object o) {
		if (!(o instanceof DateInstance))
			return false;
		DateInstance other = (DateInstance) o;
		return this.year == other.year && this.month == other.month
				&& this.day == other.day;
	}
}
