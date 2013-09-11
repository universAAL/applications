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
package org.universAAL.ltba.activity.representation;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import org.universAAL.ltba.activity.ActivityEntry;
import org.universAAL.ltba.activity.ActivityIntensity;
import org.universAAL.ltba.activity.DailyActivity;
import org.universAAL.ltba.activity.Room;

public class __WeekActivityMap {
	private AtomicEntry[][] weekTable;
	private int rows;
	private int cols;
	private int timeStepInSeconds;

	public __WeekActivityMap(int rows, int cols, ArrayList<DailyActivity> list) {
		this.rows = rows;
		this.cols = cols;
		timeStepInSeconds = (24 * 60 * 60) / rows;
		weekTable = new AtomicEntry[rows][cols];
		Calendar initCal = Calendar.getInstance();
		initCal.set(Calendar.YEAR, list.get(0).getYear());
		initCal.set(Calendar.MONTH, list.get(0).getMonth());
		initCal.set(Calendar.DAY_OF_MONTH, list.get(0).getDay());
		initCal.set(Calendar.HOUR_OF_DAY, 0);
		initCal.set(Calendar.MINUTE, 0);
		initCal.set(Calendar.SECOND, 0);

		initWeekActivityMap(initCal);
		setEvents(list);
		propagateEvents();		
	}

	private void initWeekActivityMap(Calendar initCal) {
		for (int j = 0; j < weekTable[0].length; j++) {
			for (int i = 0; i < weekTable.length; i++) {
				weekTable[i][j] = new AtomicEntry(
						initCal.getTimeInMillis()
								+ ((j * weekTable.length + i)
										* timeStepInSeconds * 1000),
						timeStepInSeconds);
			}
		}
	}

	private void setEvents(ArrayList<DailyActivity> dayList) {
		int colInTable = 0;
		// For each day
		for (DailyActivity day : dayList) {

			ArrayList<ActivityEntry> entryList = day.getEntryList();

			// For each entry
			for (ActivityEntry entry : entryList) {

				int h = entry.getHour();
				int m = entry.getMinute();
				int s = entry.getSecond();
				// Round off
				if (s >= 30)
					m++;
				int r = m % (60 / WeekCanvas.HOUR_STEPS);
				if (r >= 3)
					m = m + (60 / WeekCanvas.HOUR_STEPS) - r;
				else
					m = m - r;
				// get the index in the table which represents the graphical
				// points(of this entry)
				int rowInTable = h * WeekCanvas.HOUR_STEPS + m
						* WeekCanvas.HOUR_STEPS / 60;
				// System.out.println("RowInTable->" + rowInTable +
				// "ColInTable->"
				// + colInTable);
				weekTable[rowInTable][colInTable].putEntry(entry.getRoom(),
						entry.getIntensity());

				// if (rowInTable > 0) {
				// // weekTable[rowInTable][colInTable]
				// // .setAtomicMap(weekTable[rowInTable - 1][colInTable]
				// // .getAtomicMap());
				// weekTable[rowInTable][colInTable].putEntry(entry.getRoom(),
				// entry.getIntensity());
				// } else if (rowInTable == 0) {
				//
				// // weekTable[rowInTable][colInTable]
				// // .setAtomicMap(weekTable[weekTable.length - 1][colInTable -
				// 1]
				// // .getAtomicMap());
				//
				// weekTable[rowInTable][colInTable].putEntry(entry.getRoom(),
				// entry.getIntensity());
				// } else if ((rowInTable == 0) && (colInTable == 0)) {
				// // weekTable[rowInTable][colInTable]
				// // .setAtomicMap(new HashMap<Room, ActivityIntensity>());
				// weekTable[rowInTable][colInTable].putEntry(entry.getRoom(),
				// entry.getIntensity());
				// }

			}

			colInTable++;

		}
	}

	private void propagateEvents() {
		HashMap<Room, ActivityIntensity> propagatedState = new HashMap<Room, ActivityIntensity>();
		propagatedState.put(Room.BATHROOM, ActivityIntensity.NULL);
		propagatedState.put(Room.BEDROOM, ActivityIntensity.NULL);
		propagatedState.put(Room.LIVINGROOM, ActivityIntensity.NULL);
		propagatedState.put(Room.KITCHEN, ActivityIntensity.NULL);
		propagatedState.put(Room.GARDEN, ActivityIntensity.NULL);
		propagatedState.put(Room.HALL, ActivityIntensity.NULL);
		for (int j = 0; j < cols; j++) {
			for (int i = 0; i < rows; i++) {
				if (!weekTable[i][j].getAtomicMap().isEmpty()) {
					HashMap<Room, ActivityIntensity> hm = (HashMap<Room, ActivityIntensity>) weekTable[i][j]
							.getAtomicMap();
					weekTable[i][j].setAtomicMap(propagatedState);
					Set<Room> keys = hm.keySet();
					Iterator<Room> iterator = keys.iterator();
					for (Room room : keys) {
//						if (j == 0)
//							System.out.println("Putting in " + i + "," + j
//									+ " key->" + room + " value->"
//									+ hm.get(room));
						weekTable[i][j].putEntry(room, hm.get(room));
					}
					propagatedState = new HashMap<Room, ActivityIntensity>(
							weekTable[i][j].getAtomicMap());
//					if (j == 0)
//						System.out.println("NEW PROPAGATED STATE: "
//								+ propagatedState);
				} else {
					// System.out.println("Putting in " + i + "," + j
					// + " ThePropagatedState->" +propagatedState);
					weekTable[i][j].setAtomicMap(propagatedState);
				}
			}
		}
	}

	public AtomicEntry[][] getWeekArray() {
		return weekTable;
	}

	public void auxPrinter() {
		for (int j = 0; j < cols; j++) {
			for (int i = 0; i < rows; i++) {
				if (j == 0)
					System.out.println(weekTable[i][j].getAtomicMap());
			}
		}
	}

	public AtomicEntry getAtomicEntryFromCoordinates(int x, int y) {
		// System.out.println("Calculating Map Entry");
		int relX = x - WeekCanvas.X_OFFSET;
		int relY = y - WeekCanvas.Y_OFFSET;
		// System.out.println("Table_row->" + (relY / WeekCanvas.STEP_HEIGHT));
		// System.out.println("Table_col->"
		// + (relX / WeekCanvas.WEEK_COLUMN_WIDTH));
		return weekTable[(relY / WeekCanvas.STEP_HEIGHT)][(relX / WeekCanvas.WEEK_COLUMN_WIDTH)];
	}
}
