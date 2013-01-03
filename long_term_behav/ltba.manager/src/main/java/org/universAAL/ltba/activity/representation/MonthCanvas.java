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

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.Calendar;
import java.util.HashMap;

import org.universAAL.ltba.activity.Room;

/**
 * Graphical representation of the month report.
 * 
 * @author mllorente
 * 
 */
public class MonthCanvas extends Canvas {

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
	public static final int COLUMNS = 7;
	public static final int DAY_COLUMN_LENGTH = 24 * HOUR_STEPS;
	public static final int STEP_HEIGHT = (HEIGHT - 2 * Y_OFFSET)
			/ DAY_COLUMN_LENGTH;

	public static final int WEEK_COLUMN_WIDTH = (WIDTH - 2 * X_OFFSET)
			/ COLUMNS;
	public static final int WEEK_RECTANGLE_WIDTH = COLUMNS * WEEK_COLUMN_WIDTH;
	public static final int WEEK_RECTANGLE_HEIGHT = DAY_COLUMN_LENGTH
			* STEP_HEIGHT;

	private DayCollectionActivityMap month;
	private HashMap<Room, Integer> columnIndexMap;
	private Font dayFont = new Font("dayFont", Font.PLAIN, 32);

	/**
	 * @return the map
	 */
	public DayCollectionActivityMap getMap() {
		return month;
	}

	public MonthCanvas(DayCollectionActivityMap myMap) {
		setSize(WIDTH + 50, HEIGHT + 50);
		setBackground(Color.white);
		this.month = myMap;
		fillColumnIndexMap();
	}

	private void fillColumnIndexMap() {
		columnIndexMap = new HashMap<Room, Integer>();
		int k = 0;
		for (Room room : month.getDetectedRooms()) {
			columnIndexMap.put(room, k);
			k++;
		}
	}

	@Override
	public void paint(Graphics g) {

		int dayOffset = 0;
		Calendar auxCal = Calendar.getInstance();
		auxCal.setTimeInMillis(month.getTable()[0][0].getTimestamp());

		int weekOffset = (auxCal.get(Calendar.DAY_OF_WEEK) - 2);
		if (weekOffset == -1) {
			weekOffset = 6;
		}

		for (int i = 0; i < month.getTable()[0].length; i++) {

			auxCal.setTimeInMillis(month.getTable()[0][i].getTimestamp());
			System.out.println("NEW DAY:-->" + auxCal.get(Calendar.DATE) + "-"
					+ (auxCal.get(Calendar.MONTH) + 1));
			switch (auxCal.get(Calendar.DAY_OF_WEEK)) {
			case Calendar.MONDAY:
				dayOffset = 0;
				break;
			case Calendar.TUESDAY:
				dayOffset = 1;
				break;
			case Calendar.WEDNESDAY:
				dayOffset = 2;
				break;
			case Calendar.THURSDAY:
				dayOffset = 3;
				break;
			case Calendar.FRIDAY:
				dayOffset = 4;
				break;
			case Calendar.SATURDAY:
				dayOffset = 5;
				break;
			case Calendar.SUNDAY:
				dayOffset = 6;
				break;
			default:
				dayOffset = 0;
				System.out.println("Default?");
				break;
			}
			float mean = month.getDayActivityMean(i);
			g.setColor(calculateColorFromActivityMean(mean));
			System.out.println("Painting: x->"
					+ (X_OFFSET + dayOffset * WEEK_COLUMN_WIDTH)
					+ " -- y->"
					+ (Y_OFFSET + (int) (weekOffset / 7)
							* WEEK_RECTANGLE_HEIGHT / 6) + " in day/month: "
					+ auxCal.get(Calendar.DAY_OF_MONTH) + "/"
					+ (auxCal.get(Calendar.MONTH) + 1));
			System.out.println();
			g.fillRect(X_OFFSET + dayOffset * WEEK_COLUMN_WIDTH, Y_OFFSET
					+ (int) (weekOffset / 7) * WEEK_RECTANGLE_HEIGHT / 6,
					WEEK_COLUMN_WIDTH, WEEK_RECTANGLE_HEIGHT / 6);
			g.setColor(Color.BLACK);
			g.setFont(dayFont);
			g
					.drawString(
							new String(auxCal.get(Calendar.DAY_OF_MONTH) + "/"
									+ (auxCal.get(Calendar.MONTH) + 1)),
							(int) (WEEK_COLUMN_WIDTH * 0.5 + X_OFFSET + (dayOffset * WEEK_COLUMN_WIDTH)) - 22,
							(WEEK_RECTANGLE_HEIGHT / 12)
									+ Y_OFFSET
									+ ((int) (weekOffset / 7)
											* WEEK_RECTANGLE_HEIGHT / 6) + 8);
			weekOffset++;
		}

		// Draw month rectangle
		g.setColor(Color.BLACK);
		g.drawRect(X_OFFSET, Y_OFFSET, WEEK_RECTANGLE_WIDTH,
				WEEK_RECTANGLE_HEIGHT);

		// Draw vertical lines
		for (int i = 1; i <= COLUMNS; i++) {
			g.drawLine(X_OFFSET + i * WEEK_COLUMN_WIDTH, Y_OFFSET, X_OFFSET + i
					* WEEK_COLUMN_WIDTH, Y_OFFSET + WEEK_RECTANGLE_HEIGHT);
		}

		// Draw horizontal lines
		for (int i = 1; i <= 6; i++) {
			g.drawLine(X_OFFSET, Y_OFFSET + i * WEEK_RECTANGLE_HEIGHT / 6,
					WEEK_RECTANGLE_WIDTH + X_OFFSET, Y_OFFSET + i
							* WEEK_RECTANGLE_HEIGHT / 6);
		}

		/*
		 * 
		 * g.drawString("00:00 AM", WIDTH - X_OFFSET + HOUR_AXIS_GAP, Y_OFFSET);
		 * g.drawString("08:00 AM", WIDTH - X_OFFSET + HOUR_AXIS_GAP, Y_OFFSET +
		 * 1 ((HEIGHT - 2 * Y_OFFSET) / 4)); g.drawString("12:00 PM", WIDTH -
		 * X_OFFSET + HOUR_AXIS_GAP, Y_OFFSET + 2 ((HEIGHT - 2 * Y_OFFSET) /
		 * 4)); g.drawString("20:00 PM", WIDTH - X_OFFSET + HOUR_AXIS_GAP,
		 * Y_OFFSET + 3 ((HEIGHT - 2 * Y_OFFSET) / 4));
		 */

	}

	/**
	 * Converts an activity in a color where more green means more activity and
	 * more red means less activity.
	 * 
	 * @param mean
	 *            The index of activity (the mean of activity in a day).
	 * @return
	 */
	private Color calculateColorFromActivityMean(float mean) {
		// Assuming 5 is the maximum in a day
		float normalyzedValue = mean / 5;
		int R = 0;
		int G = 0;
		int B = 0;
		if (normalyzedValue > 1) {
			normalyzedValue = 1;
		}
		if (normalyzedValue <= 0.5) {
			R = 255;
			G = (int) (255 * 2 * normalyzedValue);
		} else if (normalyzedValue > 0.5) {
			R = (int) ((int) 255 * (1 - (2 * (normalyzedValue - 0.5))));
			G = 255;
		}

		return new Color(R, G, B);
	}

	public void setMap(DayCollectionActivityMap map) {
		this.month = map;
	}

}
