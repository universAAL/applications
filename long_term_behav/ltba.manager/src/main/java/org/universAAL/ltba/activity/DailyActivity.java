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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

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
