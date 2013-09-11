/*
	Copyright 2008-2014 TSB, http://www.tsbtecnologias.es
	TSB - Tecnologías para la Salud y el Bienestar
	
	See the NOTICE file distributed with this work for additional 
	information regarding copyright ownership
	
	Licensed under the Apache License, Version 2.0 (the "License");
	you may not use this file except in compliance with the License.
	You may obtain a copy of the License at
	
	  http://www.apache.org/licenses/LICENSE-2.0
	
	Unless required by applicable law or agreed to in writing, software
	distributed under the License is distributed on an "AS IS" BASIS,
	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
	See the License for the specific language governing permissions and
	limitations under the License.
 */
package org.universAAL.ltba.activity;

/**
 * 
 * @author Miguel Llorente (mllorente)
 *
 */
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
