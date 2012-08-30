package org.universAAL.ltba.activity.representation;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.rdf.Resource;
import org.universaal.ltba.ui.activator.MainLTBAUIProvider;


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
	ModuleContext mc;
	Resource inputUser;

	public DayGraphicReport(DayCollectionActivityMap dayMap,
			ModuleContext context, final Resource inputUser) {

		System.out.println("THIS IS A NEW CANVAS!!");
		mc = context;
		canvas = new DayCanvas(dayMap);
		canvas.setMap(dayMap);
		// addCanvasListener();
		setSize(canvas.WIDTH + 50, canvas.HEIGHT + 50);
		roomsLabel = new JLabel("ROOMS");
		roomsLabel.setVisible(true);
		JButton bDone = new JButton("Done");
		this.inputUser = inputUser;
		bDone.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				new MainLTBAUIProvider(mc).showDialog(inputUser);

			}
		});
		bDone.setPreferredSize(new Dimension(100, 80));
		getContentPane().add(canvas, BorderLayout.CENTER);
		JPanel endPane = new JPanel();
		endPane.add(roomsLabel);
		endPane.add(bDone);
		getContentPane().add(endPane, BorderLayout.PAGE_END);

		setVisible(true);
		toFront();
		setExtendedState(MAXIMIZED_BOTH);
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
