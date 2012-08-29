package org.universAAL.ltba.activity.representation;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.universAAL.ltba.activity.ActivityEntry;
import org.universAAL.ltba.activity.DailyActivity;

public class WeeklyGraphicReport extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2087849752847365853L;
	private WeekCanvas canvas;
	private JLabel roomsLabel;
	private JPanel panel;
	protected int mouse_y;
	protected int mouse_x;
	protected boolean isMousePostitionPrintable;

	public WeeklyGraphicReport(DayCollectionActivityMap weekMap) {

		System.out.println("THIS IS A NEW CANVAS!!");
		canvas = new WeekCanvas(weekMap);
		Color[][] colorData = new Color[canvas.DAY_COLUMN_LENGTH][7];
		// colorData = getColorDataFrom(data);
		canvas.setMap(weekMap);
		addCanvasListener();
		// canvas.updateData(colorData);
		setSize(canvas.WIDTH + 50, canvas.HEIGHT + 50);
		roomsLabel = new JLabel("ROOMS");
		roomsLabel.setVisible(true);
		//panel = new JPanel(new BorderLayout());
		getContentPane().add(canvas,BorderLayout.CENTER);
		getContentPane().add(roomsLabel,BorderLayout.PAGE_END);
		// pack();
		setVisible(true);
		repaint();

	}

	private void addCanvasListener() {
		canvas.addMouseListener(new MouseListener() {

			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			public void mouseExited(MouseEvent e) {
				isMousePostitionPrintable = false;

			}

			public void mouseEntered(MouseEvent e) {
				isMousePostitionPrintable = true;
			}

			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub

			}
		});

		canvas.addMouseMotionListener(new MouseMotionListener() {

			public void mouseMoved(MouseEvent e) {
				//System.out.println("x->" + canvas.getMousePosition().x);
				//System.out.println("y->" + canvas.getMousePosition().y);
				mouse_x = canvas.getMousePosition().x;
				mouse_y = canvas.getMousePosition().y;

				if (isMousePostitionPrintable
						&& mouse_x > WeekCanvas.X_OFFSET
						&& mouse_x < WeekCanvas.WEEK_RECTANGLE_WIDTH
								+ WeekCanvas.X_OFFSET
						&& mouse_y > WeekCanvas.Y_OFFSET
						&& mouse_y < WeekCanvas.WEEK_RECTANGLE_HEIGHT
								+ WeekCanvas.Y_OFFSET) {
					//System.out.println("Inside if");
					roomsLabel.setText(canvas.getMap().getAtomicEntryFromCoordinates(mouse_x,
							mouse_y).getAtomicMap().toString());

				}
			}

			public void mouseDragged(MouseEvent e) {
				// TODO Auto-generated method stub

			}
		});

	}

	private Color[][] getColorDataFrom(ArrayList<DailyActivity> dayList) {
		Color[][] colorArray = new Color[canvas.DAY_COLUMN_LENGTH][7];
		int day_of_week = 0;
		// For each day
		Color lastColor = Color.DARK_GRAY;
		for (DailyActivity dailyActivity : dayList) {
			ArrayList<ActivityEntry> entryList = dailyActivity.getEntryList();
			int lastStep = 0;

			// For each entry
			for (ActivityEntry activityEntry : entryList) {
				// Get hour, minutes and seconds in local variables for rounding
				// off
				int h = activityEntry.getHour();
				int m = activityEntry.getMinute();
				int s = activityEntry.getSecond();
				// Rounding off
				if (s >= 30)
					m++;
				int r = m % (60 / WeekCanvas.HOUR_STEPS);
				if (r >= 3)
					m = m + (60 / WeekCanvas.HOUR_STEPS) - r;
				else
					m = m - r;
				// get the index in the table which represents the graphical
				// points(of this entry)
				int newStep = h * WeekCanvas.HOUR_STEPS + m
						* WeekCanvas.HOUR_STEPS / 60;
				// from the last point in the table to this one, the color must
				// be the last color found
				for (int i = lastStep; i < newStep; i++) {

					colorArray[i][day_of_week] = lastColor;
				}
				lastColor = activityEntry.getIntensity().getIntensityColor();
				lastStep = newStep;
			}
			for (int i = lastStep; i < colorArray.length; i++) {
				colorArray[i][day_of_week] = lastColor;
			}

			day_of_week++;

		}

		// TODO Comment
		// for (int i = 0; i < colorArray.length; i++) {
		//
		// for (int j = 0; j < colorArray[0].length; j++) {
		//
		// System.out.println(colorArray[i]);
		//
		// }
		//
		// }

		return colorArray;

	}

	private ArrayList<ActivityEntry>[][] getEntryDataFrom(
			ArrayList<DailyActivity> dayList) {
		__WeekActivityMap[][] entriesArray = new __WeekActivityMap[canvas.DAY_COLUMN_LENGTH][7];
		int day_of_week = 0;
		// For each day
		Color lastColor = Color.DARK_GRAY;
		for (DailyActivity dailyActivity : dayList) {
			ArrayList<ActivityEntry> entryList = dailyActivity.getEntryList();
			int lastStep = 0;

			Color[][] colorArray = null;
			// For each entry
			for (ActivityEntry activityEntry : entryList) {
				// Get hour, minutes and seconds in local variables for rounding
				// off
				int h = activityEntry.getHour();
				int m = activityEntry.getMinute();
				int s = activityEntry.getSecond();
				// Rounding off
				if (s >= 30)
					m++;
				int r = m % (60 / WeekCanvas.HOUR_STEPS);
				if (r >= 3)
					m = m + (60 / WeekCanvas.HOUR_STEPS) - r;
				else
					m = m - r;
				// get the index in the table which represents the graphical
				// points(of this entry)
				int newStep = h * WeekCanvas.HOUR_STEPS + m
						* WeekCanvas.HOUR_STEPS / 60;
				// from the last point in the table to this one, the color must
				// be the last color found
				for (int i = lastStep; i < newStep; i++) {

					colorArray[i][day_of_week] = lastColor;
				}
				lastColor = activityEntry.getIntensity().getIntensityColor();
				lastStep = newStep;
			}
			for (int i = lastStep; i < colorArray.length; i++) {
				colorArray[i][day_of_week] = lastColor;
			}

			day_of_week++;

		}

		return null;

	}

	// public void printCanvas() {
	// canvas = new WeeklyGraphicReportCanvas();
	// setSize(WIDTH + 50, HEIGHT + 50);
	// getContentPane().add(canvas);
	// pack();
	// repaint();
	// }

	// public void updateLastDay(Color[] c) {
	// canvas.updateLastDay(c);
	// }

}
