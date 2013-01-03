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

import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.rdf.Resource;
import org.universaal.ltba.ui.activator.MainLTBAUIProvider;

public class MonthGraphicReport extends JFrame {

	private static final long serialVersionUID = 2087849752847365853L;
	private MonthCanvas canvas;
	private JLabel roomsLabel;
	protected int mouse_y;
	protected int mouse_x;
	protected boolean isMousePostitionPrintable;
	private ModuleContext mc;
	private Resource inputUser;

	/**
	 * Creates a graphic report from a DayCollectionActivityMap
	 * 
	 * @param monthMap
	 *            The map for the days to be represented.
	 * @param user
	 * @param context
	 */
	public MonthGraphicReport(DayCollectionActivityMap monthMap,
			ModuleContext context, Resource user) {
		mc = context;
		inputUser = user;
		canvas = new MonthCanvas(monthMap);
		canvas.setMap(monthMap);
		setSize(MonthCanvas.WIDTH + 50, MonthCanvas.HEIGHT + 50);
		roomsLabel = new JLabel("ROOMS");
		roomsLabel.setVisible(true);
		JButton bDone = new JButton("Done");
		bDone.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				new MainLTBAUIProvider(mc).showDialog(inputUser);

			}
		});
		bDone.setPreferredSize(new Dimension(100, 80));
		getContentPane().add(canvas, BorderLayout.CENTER);
		getContentPane().add(roomsLabel, BorderLayout.PAGE_END);
		getContentPane().add(bDone, BorderLayout.EAST);		
		setVisible(true);
		repaint();

	}

	/**
	 * Adds a canvas listener. Currently not used.
	 */
	@SuppressWarnings("unused")
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
