package org.universAAL.FitbitPublisher.FitbitAPI;

/* 
 * Author: Carsten Ringe
 * Project repository: https://github.com/MoriTanosuke/fitbitclient 
*/

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Range {
	final static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	private final int interval;
	private final String modifier;
	private final Date date;

	public Range(Date date, int interval, String modifier) {
		this.date = date;
		this.interval = interval;
		this.modifier = modifier;
	}

	public String build() {
		return dateFormat.format(date) + "/" + interval + modifier;
	}

	@Override
	public String toString() {
		return build();
	}
}