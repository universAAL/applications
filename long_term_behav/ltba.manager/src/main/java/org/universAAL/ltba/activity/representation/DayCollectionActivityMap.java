package org.universAAL.ltba.activity.representation;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.universAAL.ltba.activity.ActivityEntry;
import org.universAAL.ltba.activity.ActivityIntensity;
import org.universAAL.ltba.activity.DailyActivity;
import org.universAAL.ltba.activity.Room;

/**
 * A collection of days with activity information.
 * 
 * @author mllorente
 * 
 */
public class DayCollectionActivityMap {
	/**
	 * Table with entries
	 */
	private AtomicEntry[][] table;
	/**
	 * Rows of the table
	 */
	private int rows;
	/**
	 * Columns of the table
	 */
	private int cols;
	/**
	 * The time which represents each row
	 */
	private int timeStepInSeconds;

	private ArrayList<Room> detectedRooms;

	/**
	 * @return the detectedRooms
	 */
	public ArrayList<Room> getDetectedRooms() {
		return detectedRooms;
	}

	/**
	 * Constructor for the day collection activity map from a list of detected
	 * activities in the bus.
	 * 
	 * @param rows
	 *            Rows of the table (The number of time slots in a day).
	 * @param cols
	 *            Columns of the table (The number of days to be represented).
	 * @param list
	 *            The list of activities retrieved from the bus.
	 */
	public DayCollectionActivityMap(int rows, int cols,
			ArrayList<DailyActivity> list) {
		this.rows = rows;
		this.cols = cols;
		timeStepInSeconds = (24 * 60 * 60) / rows;
		table = new AtomicEntry[rows][cols];
		detectedRooms = new ArrayList<Room>();
		/* The calendar for the starting date of the collection */
		Calendar initCal = Calendar.getInstance();
		initCal.set(Calendar.YEAR, list.get(0).getYear());
		initCal.set(Calendar.MONTH, list.get(0).getMonth());
		initCal.set(Calendar.DAY_OF_MONTH, list.get(0).getDay());
		initCal.set(Calendar.HOUR_OF_DAY, 0);
		initCal.set(Calendar.MINUTE, 0);
		initCal.set(Calendar.SECOND, 0);
		/*
		 * Generates the table of activity entries from a initial date and a
		 * number of rows and columns
		 */
		initTable(initCal);
		/*
		 * Set the table values with the information prceding from the events of
		 * the list
		 */
		setEvents(list);
		/* Propagate the events for modelling a real day of the user */
		propagateEvents();
		// //Print if debugging
		// for (int i = 0; i < table[0].length; i++) {
		// System.out.println("DAY " + i + "("
		// + initCal.get(Calendar.DAY_OF_MONTH) + "/"
		// + initCal.get(Calendar.MONTH) + ")" + " ACTIVITY INDEX: "
		// + getDayActivityMean(i));
		// }
	}

	/**
	 * Returns the mean of the activity values of a day. This mean is correlated
	 * with the activity index of the assisted person in a day.
	 * 
	 * @param index
	 *            Index of the day in a the table.
	 * @return The mean of the activity intensity in a day.
	 */
	public float getDayActivityMean(int index) {
		float addition = 0;

		for (int i = 0; i < table.length; i++) {
			Map<Room, ActivityIntensity> m = table[i][index].getAtomicMap();
			Collection<ActivityIntensity> list = m.values();
			for (ActivityIntensity intensity : list) {
				addition += intensity.getIntensityValue();
			}
		}

		return addition / rows;
	}

	/**
	 * Initialize the table of entries beginning in a given date.
	 * 
	 * @param initCal
	 *            Starting date.
	 */
	private void initTable(Calendar initCal) {
		// JUST FOR DEBUGGING (UNCOMMENT IF NEEDED)
		// Calendar c = Calendar.getInstance();
		// int ld = c.get(Calendar.DAY_OF_MONTH);
		// int lm = c.get(Calendar.MONTH);
		for (int j = 0; j < table[0].length; j++) {
			for (int i = 0; i < table.length; i++) {
				table[i][j] = new AtomicEntry(initCal.getTimeInMillis()
						+ (long) ((long) (j * table.length + i)
								* timeStepInSeconds * 1000), timeStepInSeconds);

				// PRINT FOR DEBUGGING (UNCOMMENT)
				// c.setTimeInMillis(table[i][j].getTimestamp());
				// int d = c.get(Calendar.DAY_OF_MONTH);
				// int m = c.get(Calendar.MONTH);
				// if (d == ld && m == lm) {
				// } else {
				// System.out
				// .println(d
				// + "--"
				// + m
				// + "\n"
				// + (long) (initCal.getTimeInMillis() + ((long) (j
				// * table.length + i)
				// * timeStepInSeconds * 1000))
				// + "("
				// + ((j * table.length + i)
				// * timeStepInSeconds * 1000) + ")"
				// + "::i->" + i + "/j->" + j);
				// ld = d;
				// lm = m;
				//
				// }

			}
		}
	}

	/**
	 * Set the events extracted from the bus in a table. Set the events means
	 * put the events happened in the context bus in its correct position in the
	 * table, according with the timestamp.
	 * 
	 * @param dayList
	 *            List of activity events.
	 */
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
				table[rowInTable][colInTable].putEntry(entry.getRoom(), entry
						.getIntensity());

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
				boolean newRoom = true;
				for (Room room : detectedRooms) {
					if (room.getRoomString().equals(
							entry.getRoom().getRoomString())) {
						newRoom = false;
					}
				}
				if (newRoom) {
					detectedRooms.add(entry.getRoom());
				}

			}

			colInTable++;

		}
	}

	/**
	 * Since the context bus only register an event when the intensity of the
	 * activity is changed, it is neccesary to extend the events in order to set
	 * the actual status on every moment in the table. Before the execution of
	 * this method the table would be in the next state (in a simplification):
	 * <code>
	 * -- <br>--<br>--<br>LOW<br>--<br>--<br>MEDIUM<br>--<br>--<br>--<br>LOW<br>--<br>--<br>NULL<br>--<br>--<br></code>
	 * And after the execution if will be as follows:
	 * 
	 * <code>NULL<br>NULL<br><br>NULL<br>LOW<br>LOW<br>LOW<br>MEDIUM<br>MEDIUM<br>MEDIUM<br>MEDIUM<br>LOW<br>LOW<br>LOW<br>NULL<br>NULL<br>NULL<br></code>
	 */
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
				if (!table[i][j].getAtomicMap().isEmpty()) {
					HashMap<Room, ActivityIntensity> hm = (HashMap<Room, ActivityIntensity>) table[i][j]
							.getAtomicMap();
					table[i][j].setAtomicMap(propagatedState);
					Set<Room> keys = hm.keySet();
					for (Room room : keys) {
						table[i][j].putEntry(room, hm.get(room));
					}
					propagatedState = new HashMap<Room, ActivityIntensity>(
							table[i][j].getAtomicMap());
				} else {
					table[i][j].setAtomicMap(propagatedState);
				}
			}
		}
	}

	/**
	 * Returns the table with the atomic entries.
	 * 
	 * @return Table with atomic entries.
	 */
	public AtomicEntry[][] getTable() {
		return table;
	}

	/**
	 * Prints the table.
	 */
	public void auxPrinter() {
		for (int j = 0; j < cols; j++) {
			for (int i = 0; i < rows; i++) {
				if (j == 0)
					System.out.println(table[i][j].getAtomicMap());
			}
		}
	}

	/**
	 * Obtain the atomic entry (rooms and intensity of activity) in a point of
	 * the table. Useful for graphical representations.
	 * 
	 * @param x
	 *            X position in the table (pixels)
	 * @param y
	 *            Y position in the table (pixels)
	 * @return The atomic entry.
	 */
	public AtomicEntry getAtomicEntryFromCoordinates(int x, int y) {
		int relX = x - WeekCanvas.X_OFFSET;
		int relY = y - WeekCanvas.Y_OFFSET;
		return table[(relY / WeekCanvas.STEP_HEIGHT)][(relX / WeekCanvas.WEEK_COLUMN_WIDTH)];
	}
}
