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


public class DayGraphicReport extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2087849752847365853L;
	private DayCanvas canvas;
	private JLabel roomsLabel;
	protected int mouse_y;
	protected int mouse_x;
	protected boolean isMousePostitionPrintable;

	public DayGraphicReport(DayCollectionActivityMap dayMap) {

		System.out.println("THIS IS A NEW CANVAS!!");
		canvas = new DayCanvas(dayMap);
		canvas.setMap(dayMap);
		// addCanvasListener();
		setSize(canvas.WIDTH + 50, canvas.HEIGHT + 50);
		roomsLabel = new JLabel("ROOMS");
		roomsLabel.setVisible(true);
		getContentPane().add(canvas, BorderLayout.CENTER);
		getContentPane().add(roomsLabel, BorderLayout.PAGE_END);
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
				// System.out.println("x->" + canvas.getMousePosition().x);
				// System.out.println("y->" + canvas.getMousePosition().y);
				mouse_x = canvas.getMousePosition().x;
				mouse_y = canvas.getMousePosition().y;

				if (isMousePostitionPrintable
						&& mouse_x > WeekCanvas.X_OFFSET
						&& mouse_x < WeekCanvas.WEEK_RECTANGLE_WIDTH
								+ WeekCanvas.X_OFFSET
						&& mouse_y > WeekCanvas.Y_OFFSET
						&& mouse_y < WeekCanvas.WEEK_RECTANGLE_HEIGHT
								+ WeekCanvas.Y_OFFSET) {
					// System.out.println("Inside if");
					roomsLabel.setText(canvas.getMap()
							.getAtomicEntryFromCoordinates(mouse_x, mouse_y)
							.getAtomicMap().toString());

				}
			}

			public void mouseDragged(MouseEvent e) {
				// TODO Auto-generated method stub

			}
		});

	}

	
}
