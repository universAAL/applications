package org.universAAL.ltba.activity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.universAAL.ltba.activity.ActivityIntensity;
import org.universAAL.ltba.activity.Room;

public class DailyActivity {

	private int day;
	private int month;
	private int year;
	private ArrayList<ActivityEntry> entryList;
	private long activityIndex;

	public DailyActivity(long timestamp) {

		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date(timestamp));

		this.year = cal.get(Calendar.YEAR);
		this.month = cal.get(Calendar.MONTH);
		this.day = cal.get(Calendar.DAY_OF_MONTH);
		
		entryList = new ArrayList<ActivityEntry>();

	}

	/**
	 * @return the day
	 */
	public int getDay() {
		return day;
	}

	/**
	 * @return the month
	 */
	public int getMonth() {
		return month;
	}

	/**
	 * @return the year
	 */
	public int getYear() {
		return year;
	}

	/**
	 * @return the entryList
	 */
	public ArrayList<ActivityEntry> getEntryList() {
		return entryList;
	}

	/**
	 * @return the activityIndex
	 */
	public long getActivityIndex() {
		return activityIndex;
	}

	public void addNewEntry(long timestamp, Room room,
			ActivityIntensity intensity) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date(timestamp));
		//System.out.println(cal.get(Calendar.HOUR_OF_DAY) + "--"
		//		+ cal.get(Calendar.MINUTE) + "--" + cal.get(Calendar.SECOND)
		//		+ "--" + intensity + "--" + room);
		entryList.add(new ActivityEntry(cal.get(Calendar.HOUR_OF_DAY), cal
				.get(Calendar.MINUTE), cal.get(Calendar.SECOND), intensity,
				room));
	}

	public void print() {
		for (ActivityEntry entry : entryList) {
			System.out.println("------");
			entry.print();
			System.out.println("------");
		}
	}
}
