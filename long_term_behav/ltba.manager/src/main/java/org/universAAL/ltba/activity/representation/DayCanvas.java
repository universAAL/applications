package org.universAAL.ltba.activity.representation;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.universAAL.ltba.activity.ActivityIntensity;
import org.universAAL.ltba.activity.Room;

//the mother of the lamb
public class DayCanvas extends Canvas {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final int WIDTH = 1000;
	public static final int HEIGHT = 640;
	public static final int X_FACTOR = WIDTH / 100;
	public static final int X_OFFSET = 5 * X_FACTOR;
	public static final int Y_FACTOR = HEIGHT / 100;
	public static final int Y_OFFSET = 5 * Y_FACTOR;
	public static final int HOUR_AXIS_GAP = 10;// In pixels
	public static final int HOUR_STEPS = 12; // 4 = steps of 15 min ; 12 = steps
	// of 5 minutes
	public static final int COLUMNS = 6; // TODO This should be dynamic
	// depending of the number of rooms
	public static final int DAY_COLUMN_LENGTH = 24 * HOUR_STEPS;
	public static final int STEP_HEIGHT = (HEIGHT - 2 * Y_OFFSET)
			/ DAY_COLUMN_LENGTH;

	public static final int WEEK_COLUMN_WIDTH = (WIDTH - 2 * X_OFFSET)
			/ COLUMNS;
	public static final int WEEK_RECTANGLE_WIDTH = COLUMNS * WEEK_COLUMN_WIDTH;
	public static final int WEEK_RECTANGLE_HEIGHT = DAY_COLUMN_LENGTH
			* STEP_HEIGHT;

	private DayCollectionActivityMap week;
	private HashMap<Room, Integer> columnIndexMap;

	/**
	 * @return the map
	 */
	public DayCollectionActivityMap getMap() {
		return week;
	}

	public DayCanvas(DayCollectionActivityMap myMap) {
		setSize(WIDTH + 50, HEIGHT + 50);
		setBackground(Color.white);
		this.week = myMap;
		fillColumnIndexMap();
	}

	private void fillColumnIndexMap() {
		columnIndexMap = new HashMap<Room, Integer>();
		int k = 0;
		for (Room room : week.getDetectedRooms()) {
			System.out.println("Putting:" + room + " " + k);
			columnIndexMap.put(room, k);
			k++;
		}
	}

	@Override
	public void paint(Graphics g) {

		for (int j = 0; j < week.getDetectedRooms().size(); j++) {

			g.setColor(Color.BLACK);
			// PRINT DATE
			// Date thisDate = new
			// Date(map.getWeekArray()[0][j].getTimestamp());
			// SimpleDateFormat df = new SimpleDateFormat();
			// df.applyPattern("EE dd-MM-yyyy");
			// g.drawString(df.format(thisDate), 20 + X_OFFSET + j
			// * WEEK_COLUMN_WIDTH, Y_OFFSET - 10);

			g.drawString(week.getDetectedRooms().get(j).getRoomString(), 30
					+ X_OFFSET + j * WEEK_COLUMN_WIDTH, Y_OFFSET - 10);

			for (int i = 0; i < DAY_COLUMN_LENGTH; i++) {

				// System.out.println("Drawing:" + colorArray[i][j]);
				// System.out.println("IN  " + i + " - "+j);
				// System.out.println("RECT:"+(Y_OFFSET
				// + (i+1) * STEP_HEIGHT )+" TO "+((Y_OFFSET
				// + (i+1) * STEP_HEIGHT) + STEP_HEIGHT));
				// g.setColor(colorArray[i][j]);

				Map<Room, ActivityIntensity> atomap = week.getTable()[i][0]
						.getAtomicMap();
				Set<Room> atomapKeys = atomap.keySet();

				// if(j==0)
				// System.out.println("VALUES OF MAP IN CANVAS (FIRST COL): "+valueCol);

				for (Room room : atomapKeys) {

					// System.out.println("PAINTING-->"+biggerIntensity.getIntensityColor().toString());
					g.setColor(atomap.get(room).getIntensityColor());
					// g.setColor(map.getWeekArray()[i][j].getEntry(Room.BEDROOOM).getIntensityColor());
					g.fillRect(X_OFFSET + columnIndexMap.get(room)
							* WEEK_COLUMN_WIDTH, Y_OFFSET + (i) * STEP_HEIGHT,
							WEEK_COLUMN_WIDTH, STEP_HEIGHT);

				}

			}

		}

		g.setColor(Color.BLACK);

		g.drawRect(X_OFFSET, Y_OFFSET, WEEK_RECTANGLE_WIDTH,
				WEEK_RECTANGLE_HEIGHT);

		// Draw vertical lines
		for (int i = 1; i <= COLUMNS; i++) {
			g.drawLine(X_OFFSET + i * WEEK_COLUMN_WIDTH, Y_OFFSET, X_OFFSET + i
					* WEEK_COLUMN_WIDTH, Y_OFFSET + WEEK_RECTANGLE_HEIGHT);
		}

		// Draw horizontal lines

		for (int i = 1; i <= 24; i++) {
			g
					.drawLine(X_OFFSET, Y_OFFSET + i * WEEK_RECTANGLE_HEIGHT
							/ 24, WIDTH - X_OFFSET, Y_OFFSET + i
							* WEEK_RECTANGLE_HEIGHT / 24);
		}

		g.drawString("00:00 AM", WIDTH - X_OFFSET + HOUR_AXIS_GAP, Y_OFFSET);
		g.drawString("08:00 AM", WIDTH - X_OFFSET + HOUR_AXIS_GAP, Y_OFFSET + 1
				* ((HEIGHT - 2 * Y_OFFSET) / 4));
		g.drawString("12:00 PM", WIDTH - X_OFFSET + HOUR_AXIS_GAP, Y_OFFSET + 2
				* ((HEIGHT - 2 * Y_OFFSET) / 4));
		g.drawString("20:00 PM", WIDTH - X_OFFSET + HOUR_AXIS_GAP, Y_OFFSET + 3
				* ((HEIGHT - 2 * Y_OFFSET) / 4));

	}

	public void setMap(DayCollectionActivityMap map) {
		this.week = map;
	}

}
