package org.universAAL.ltba.activity;

import java.awt.Color;

public class ActivityEntry {
	
	private int hour;
	private int minute;
	private int second;
	private ActivityIntensity intensity;
	private Room room;

	public ActivityEntry(int h, int m, int s, ActivityIntensity actInt, Room r) {
		
		
		if (h >= 0 && h <= 23) {
			hour = h;
		} else {
			System.out.println("The hour is not valid!!");
		}

		if (m >= 0 && m <= 59) {
			minute = m;
		} else {
			System.out.println("The minutes value is not valid!!!");
		}

		if (s >= 0 && s <= 59) {
			second = s;
		} else {
			System.out.println("The seconds value is not valid!!!");
		}

		intensity = actInt;
		room = r;
		
		//System.out.println("ACTIVITY ENTRY:");		
		//print();
	}

	/**
	 * @return the hour
	 */
	public int getHour() {
		return hour;
	}

	/**
	 * @param hour
	 *            the hour to set
	 */
	public void setHour(int hour) {
		this.hour = hour;
	}

	/**
	 * @return the minute
	 */
	public int getMinute() {
		return minute;
	}

	/**
	 * @param minute
	 *            the minute to set
	 */
	public void setMinute(int minute) {
		this.minute = minute;
	}

	/**
	 * @return the second
	 */
	public int getSecond() {
		return second;
	}

	/**
	 * @param second
	 *            the second to set
	 */
	public void setSecond(int second) {
		this.second = second;
	}

	/**
	 * @return the intensity
	 */
	public ActivityIntensity getIntensity() {
		return intensity;
	}

	/**
	 * @param intensity
	 *            the intensity to set
	 */
	public void setIntensity(ActivityIntensity intensity) {
		this.intensity = intensity;
	}

	/**
	 * @return the room
	 */
	public Room getRoom() {
		return room;
	}

	/**
	 * @param room
	 *            the room to set
	 */
	public void setRoom(Room room) {
		this.room = room;
	}

	public void print() {

		System.out.println(intensity.getIntensityString());
		System.out.println(room.getRoomString());
		printHour();

	}

	private void printHour() {
	
		System.out.println(getHourAsString());
	}

	private String getHourAsString() {
		
		String h = "" + hour, m = "" + minute, s = "" + second;

		if (hour < 10) {
			h = "0" + hour;
		}
		if (minute < 10) {
			m = "0" + minute;
		}
		if (second < 10) {
			s = "0" + second;
		}
		
		return new String(h + ":" + m + ":" + s);
	}

}
