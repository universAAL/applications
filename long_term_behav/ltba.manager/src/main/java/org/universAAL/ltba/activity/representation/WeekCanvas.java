package org.universAAL.ltba.activity.representation;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

import org.universAAL.ltba.activity.ActivityIntensity;

public class WeekCanvas extends Canvas {

	public static final int WIDTH = 1000;
	public static final int HEIGHT = 640;
	public static final int X_FACTOR = WIDTH / 100;
	public static final int X_OFFSET = 5 * X_FACTOR;
	public static final int Y_FACTOR = HEIGHT / 100;
	public static final int Y_OFFSET = 5 * Y_FACTOR;
	public static final int HOUR_AXIS_GAP = 10;// In pixels
	public static final int HOUR_STEPS = 12; // 4 = steps of 15 min ; 12 = steps
	// of 5 minutes
	public static final int DAY_COLUMN_LENGTH = 24 * HOUR_STEPS;
	public static final int STEP_HEIGHT = (HEIGHT - 2 * Y_OFFSET)
			/ DAY_COLUMN_LENGTH;

	public static final int WEEK_COLUMN_WIDTH = (WIDTH - 2 * X_OFFSET) / 7;
	public static final int WEEK_RECTANGLE_WIDTH = 7 * WEEK_COLUMN_WIDTH;
	public static final int WEEK_RECTANGLE_HEIGHT = DAY_COLUMN_LENGTH
			* STEP_HEIGHT;

	private DayCollectionActivityMap map;
	/**
	 * @return the map
	 */
	public DayCollectionActivityMap getMap() {
		return map;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1490623906115082342L;

	private boolean isMousePostitionPrintable = false;
	private int mouse_x;
	private int mouse_y;

	public WeekCanvas(DayCollectionActivityMap myMap) {
		setSize(WIDTH + 50, HEIGHT + 50);
		setBackground(Color.white);
		this.map = myMap;
	}

	

	@Override
	public void paint(Graphics g) {

		for (int j = 0; j < 7; j++) {

			g.setColor(Color.BLACK);
			Date thisDate = new Date(map.getTable()[0][j].getTimestamp());
			SimpleDateFormat df = new SimpleDateFormat();
			df.applyPattern("EE dd-MM-yyyy");

			g.drawString(df.format(thisDate), 20 + X_OFFSET + j
					* WEEK_COLUMN_WIDTH, Y_OFFSET - 10);

			for (int i = 0; i < DAY_COLUMN_LENGTH; i++) {

				// System.out.println("Drawing:" + colorArray[i][j]);
				// System.out.println("IN  " + i + " - "+j);
				// System.out.println("RECT:"+(Y_OFFSET
				// + (i+1) * STEP_HEIGHT )+" TO "+((Y_OFFSET
				// + (i+1) * STEP_HEIGHT) + STEP_HEIGHT));
				// g.setColor(colorArray[i][j]);

				Collection<ActivityIntensity> valueCol = map.getTable()[i][j]
						.getAtomicMap().values();
				// if(j==0)
				// System.out.println("VALUES OF MAP IN CANVAS (FIRST COL): "+valueCol);
				ActivityIntensity biggerIntensity = ActivityIntensity.NULL;
				for (ActivityIntensity intensity : valueCol) {
					// System.out.println("Col: "+j+" Row: "+i+" this value:"+intensity);
					// System.out.println("From all values: "+valueCol);

					if (intensity.getIntensityValue() > biggerIntensity
							.getIntensityValue()) {
						biggerIntensity = intensity;
					}
				}
				// System.out.println("PAINTING-->"+biggerIntensity.getIntensityColor().toString());
				g.setColor(biggerIntensity.getIntensityColor());
				// g.setColor(map.getWeekArray()[i][j].getEntry(Room.BEDROOOM).getIntensityColor());
				g.fillRect(X_OFFSET + j * WEEK_COLUMN_WIDTH, Y_OFFSET + (i)
						* STEP_HEIGHT, WEEK_COLUMN_WIDTH, STEP_HEIGHT);

			}

		}

		g.setColor(Color.BLACK);

		g.drawRect(X_OFFSET, Y_OFFSET, WEEK_RECTANGLE_WIDTH,
				WEEK_RECTANGLE_HEIGHT);
		// System.out.println("PAINTING BLACK RECTANGLE:");
		// System.out.println("X1="+X_OFFSET);
		// System.out.println("Y1="+Y_OFFSET);
		// System.out.println("X2="+((WIDTH - 2 * X_OFFSET)+X_OFFSET));
		// System.out.println("Y2="+((HEIGHT - 2 * Y_OFFSET)+Y_OFFSET));

		// Draw vertical lines
		for (int i = 1; i <= 7; i++) {
			g.drawLine(X_OFFSET + i * WEEK_COLUMN_WIDTH, Y_OFFSET, X_OFFSET + i
					* WEEK_COLUMN_WIDTH, Y_OFFSET + WEEK_RECTANGLE_HEIGHT);
		}

		g.drawString("00:00 AM", WIDTH - X_OFFSET + HOUR_AXIS_GAP, Y_OFFSET);
		g.drawString("08:00 AM", WIDTH - X_OFFSET + HOUR_AXIS_GAP, Y_OFFSET + 1
				* ((HEIGHT - 2 * Y_OFFSET) / 4));
		g.drawString("12:00 PM", WIDTH - X_OFFSET + HOUR_AXIS_GAP, Y_OFFSET + 2
				* ((HEIGHT - 2 * Y_OFFSET) / 4));
		g.drawString("20:00 PM", WIDTH - X_OFFSET + HOUR_AXIS_GAP, Y_OFFSET + 3
				* ((HEIGHT - 2 * Y_OFFSET) / 4));
		
	}
	
	@Override
	public void repaint() {
	
		super.repaint();
	}

	// protected void updateLastDay(Color[] c) {
	// colorAux = c;
	// }

	public void setMap(DayCollectionActivityMap map) {
		this.map = map;
	}

}
