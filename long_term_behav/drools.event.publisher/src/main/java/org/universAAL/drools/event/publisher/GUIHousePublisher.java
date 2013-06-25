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
package org.universAAL.drools.event.publisher;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;
import javax.swing.SpinnerModel;
import javax.swing.Timer;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.osgi.framework.BundleContext;
import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.container.osgi.uAALBundleContainer;
import org.universAAL.middleware.container.utils.LogUtils;

/**
 * @author Miguel Llorente (mllorente)
 * 
 *         IMPORTANT NOTE: This class has been coded just for developing,
 *         testing and demo purposes. Due to this, the class is quite bad
 *         structured, as is much more efficient to generate a component
 *         containing a the border, the label, the button and all the actions
 *         related to them (action listeners, timers an so.) In new versions it
 *         would be convenient to redefine the structure of the class to provide
 *         a better understanding and make the modification and maintenance
 *         easier.
 * 
 */
public class GUIHousePublisher extends JFrame {

	private final static long BASE_DATE = 82800000;

	private static BundleContext context;
	private static ModuleContext mc;
	private static GUIHousePublisher INSTANCE = null;
	private static Timer tKitchen, tBedroom, tBathroom, tLivingRoom, tHall,
			tGarden, tClock;
	private static JButton bKitchen, bBedroom, bBathroom, bLivingRoom, bHall,
			bGarden, bDay, bWeek, bMonth;
	private static JLabel lKitchen, lBedroom, lBathroom, lLivingRoom, lHall,
			lGarden, lClock;
	private static long dKitchen, dBedroom, dBathroom, dLivingRoom, dHall,
			dGarden;
	private static ActionListener buttonListener;

	private static JSpinner sClock;

	private static JButton bClock;

	private static JCheckBox cClock;

	private final static String sinceLast = "since last detection.";
	private final static String simulatedTime = "Simulated time: ";
	private final static JLabel nothing = new JLabel();
	private static long pseudoClockDiff = 0;

	/**
	 * Serializable
	 */
	private static final long serialVersionUID = -3561358927431152303L;

	private GUIHousePublisher() {
		super();
		INSTANCE = this;
	}

	private static GUIHousePublisher getInstance() {
		if (INSTANCE == null) {
			return new GUIHousePublisher();
		} else {
			return INSTANCE;
		}
	}

	public static void init(BundleContext ctx) {

		context = ctx;

		mc = uAALBundleContainer.THE_CONTAINER
				.registerModule(new Object[] { context });

		lKitchen = new JLabel("00:00:00 " + sinceLast);
		lBathroom = new JLabel("00:00:00 " + sinceLast);
		lLivingRoom = new JLabel("00:00:00 " + sinceLast);
		lBedroom = new JLabel("00:00:00 " + sinceLast);
		lGarden = new JLabel("00:00:00 " + sinceLast);
		lHall = new JLabel("00:00:00 " + sinceLast);
		lClock = new JLabel(simulatedTime + " 00:00:00");
		// SNIPPER
		Calendar auxCalendar = Calendar.getInstance();
		auxCalendar.add(Calendar.YEAR, -10);
		Date initDate = auxCalendar.getTime();
		auxCalendar.add(Calendar.YEAR, 20);
		Date endDate = auxCalendar.getTime();
		SpinnerModel mod = new SpinnerDateModel(Calendar.getInstance()
				.getTime(), initDate, endDate, Calendar.DAY_OF_MONTH);
		sClock = new JSpinner(mod);
		sClock
				.setEditor(new JSpinner.DateEditor(sClock,
						"HH:mm:ss dd/MM/yyyy"));
		sClock.setValue(Calendar.getInstance().getTime());
		// CLOCK CHECKBOX
		cClock = new JCheckBox("Submit enabled", false);

		initTimers();
		initButtonListener();
		initGUIElements();

		GUIHousePublisher ghp = getInstance();
		ghp.setSize(640, 540);

		bKitchen = new JButton("PRESENCE DETECTOR");

		JPanel pKitchen = new JPanel(new GridLayout(2, 1, 7, 7));
		pKitchen.setBorder(BorderFactory.createTitledBorder("Kitchen"));
		pKitchen.add(lKitchen);
		pKitchen.add(bKitchen);

		bBathroom = new JButton("PRESENCE DETECTOR");

		JPanel pBathroom = new JPanel(new GridLayout(2, 1, 7, 7));
		pBathroom.setBorder(BorderFactory.createTitledBorder("Bathroom"));
		pBathroom.add(lBathroom);
		pBathroom.add(bBathroom);

		bLivingRoom = new JButton("PRESENCE DETECTOR");

		JPanel pLivingRoom = new JPanel(new GridLayout(2, 1, 7, 7));
		pLivingRoom.setBorder(BorderFactory.createTitledBorder("LivingRoom"));
		pLivingRoom.add(lLivingRoom);
		pLivingRoom.add(bLivingRoom);

		bBedroom = new JButton("PRESENCE DETECTOR");

		JPanel pBedroom = new JPanel(new GridLayout(2, 1, 7, 7));
		pBedroom.setBorder(BorderFactory.createTitledBorder("Bedroom"));
		pBedroom.add(lBedroom);
		pBedroom.add(bBedroom);

		bGarden = new JButton("PRESENCE DETECTOR");

		JPanel pGarden = new JPanel(new GridLayout(2, 1, 7, 7));
		pGarden.add(lGarden);
		pGarden.add(bGarden);
		pGarden.setBorder(BorderFactory
				.createTitledBorder("Different event (Device)"));

		bHall = new JButton("PRESENCE DETECTOR");

		JPanel pHall = new JPanel(new GridLayout(2, 1, 7, 7));
		pHall.setBorder(BorderFactory.createTitledBorder("Hall"));
		pHall.add(lHall);
		pHall.add(bHall);

		// Random activity generators

		// final DaySequenceGenerator dsg = new DaySequenceGenerator(mc);
		bDay = new JButton("Generate Day");
		bDay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LogUtils.logDebug(mc, getClass(), "init", new String[]{"Generate day pressed"}, null);
				// dsg.generateDaySecuence(1);
			}
		});
		bWeek = new JButton("Generate Week");
		bWeek.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LogUtils.logDebug(mc, getClass(), "init", new String[]{"Generate week pressed"}, null);
				// dsg.generateDaySecuence(7);
			}
		});
		bMonth = new JButton("Generate Month");
		bMonth.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				LogUtils.logDebug(mc, getClass(), "init", new String[]{"Generate month pressed"}, null);
				// dsg.generateDaySecuence(31);
			}
		});

		JPanel pGenerators = new JPanel();
		pGenerators.add(bDay);
		pGenerators.add(bWeek);
		pGenerators.add(bMonth);

		// LABEL

		JPanel pClockL = new JPanel(new GridLayout(3, 2, 14, 7));
		JPanel pClockR = new JPanel(new GridLayout(3, 2, 14, 7));
		// BUTTON
		bClock = new JButton("Submit");
		bClock.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if (cClock.isSelected()) {
					Date d = (Date) sClock.getValue();
					pseudoClockDiff = d.getTime()
							- Calendar.getInstance().getTimeInMillis();
					updateSimuClockLabel(lClock, d.getTime());
					// ConsequenceListener.getInstance().setTimeForDebug(
					// ((Date) sClock.getValue()).getTime());
				}
			}
		});

		sClock.addChangeListener(new ChangeListener() {

			public void stateChanged(ChangeEvent e) {
				if (!cClock.isSelected()) {
					Date d = (Date) sClock.getValue();
					pseudoClockDiff = d.getTime()
							- Calendar.getInstance().getTimeInMillis();
					updateSimuClockLabel(lClock, d.getTime());
					// ConsequenceListener.getInstance().setTimeForDebug(
					// ((Date) sClock.getValue()).getTime());
				}

			}
		});

		pClockL.add(nothing);
		pClockL.add(sClock);
		pClockL.add(bClock);

		pClockR.add(lClock);
		pClockR.add(cClock);
		pClockR.add(nothing);

		bKitchen.addActionListener(buttonListener);
		bBathroom.addActionListener(buttonListener);
		bBedroom.addActionListener(buttonListener);
		bLivingRoom.addActionListener(buttonListener);
		bGarden.addActionListener(buttonListener);
		bHall.addActionListener(buttonListener);

		JPanel pGeneral = new JPanel(new GridLayout(5, 2, 30, 20));
		pGeneral.add(pClockL);
		pGeneral.add(pClockR);
		pGeneral.add(pKitchen);
		pGeneral.add(pLivingRoom);
		pGeneral.add(pBathroom);
		pGeneral.add(pBedroom);
		pGeneral.add(pGarden);
		pGeneral.add(pHall);
		pGeneral.add(pGenerators);

		ghp.setLayout(new FlowLayout(FlowLayout.CENTER, 25, 25));
		ghp.add(pGeneral);
		ghp.setVisible(true);
		// ghp.pack();
		ghp.repaint();
	}

	private static void initGUIElements() {
		// TODO Auto-generated method stub

	}

	private static void initButtonListener() {
		// ConsequenceListener.getInstance().startDebugTime();

		buttonListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Object src = e.getSource();
				if (src.equals(bKitchen)) {
					StaticPublisher.publishMotionEventDetected("Kitchen",
							context);
					dKitchen = resetLabel(dKitchen);
					updateLabel(lKitchen, dKitchen);
					if (!tKitchen.isRunning()) {
						LogUtils.logDebug(mc, getClass(), "initButtonListener", new String[]{"Generate day pressed"}, null);
						tKitchen.start();
					}
				} else if (src.equals(bBathroom)) {
					StaticPublisher.publishMotionEventDetected("Bathroom",
							context);
					dBathroom = resetLabel(dBathroom);
					updateLabel(lBathroom, dBathroom);
					if (!tBathroom.isRunning()) {
						tBathroom.start();
					}
				} else if (src.equals(bBedroom)) {
					StaticPublisher.publishMotionEventDetected("Bedroom",
							context);
					dBedroom = resetLabel(dBedroom);
					updateLabel(lBedroom, dBedroom);
					if (!tBedroom.isRunning()) {
						tBedroom.start();
					}
				} else if (src.equals(bLivingRoom)) {
					StaticPublisher.publishMotionEventDetected("Living Room",
							context);
					dLivingRoom = resetLabel(dLivingRoom);
					updateLabel(lLivingRoom, dLivingRoom);
					if (!tLivingRoom.isRunning()) {
						tLivingRoom.start();
					}
				} else if (src.equals(bHall)) {
					StaticPublisher.publishMotionEventDetected("Hall", context);
					dHall = resetLabel(dHall);
					updateLabel(lHall, dHall);
					if (!tHall.isRunning()) {
						tHall.start();
					}
				} else if (src.equals(bGarden)) {
					StaticPublisher.publishDifferentContextEvent("Garden",
							context);
					dGarden = resetLabel(dGarden);
					updateLabel(lGarden, dGarden);
					if (!tGarden.isRunning()) {
						tGarden.start();
					}
				}
			}
		};
	}

	// One timer for updating all!!!
	private static void initTimers() {

		tClock = new Timer(1000, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// dBathroom = addSecondToCounter(dBathroom);
				updateSimuClockLabel(lClock, pseudoClockDiff
						+ Calendar.getInstance().getTimeInMillis());
			}
		});
		tClock.start();
		tBathroom = new Timer(1000, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dBathroom = addSecondToCounter(dBathroom);
				updateLabel(lBathroom, dBathroom);
			}
		});
		tBedroom = new Timer(1000, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dBedroom = addSecondToCounter(dBedroom);
				updateLabel(lBedroom, dBedroom);
			}
		});
		tLivingRoom = new Timer(1000, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dLivingRoom = addSecondToCounter(dLivingRoom);
				updateLabel(lLivingRoom, dLivingRoom);
			}
		});
		tHall = new Timer(1000, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dHall = addSecondToCounter(dHall);
				updateLabel(lHall, dHall);
			}
		});
		tGarden = new Timer(1000, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dGarden = addSecondToCounter(dGarden);
				updateLabel(lGarden, dGarden);
			}
		});
		tKitchen = new Timer(1000, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dKitchen = addSecondToCounter(dKitchen);
				updateLabel(lKitchen, dKitchen);
			}
		});

	}

	protected static void updateSimuClockLabel(JLabel label, long timeInMillis) {
		SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
		label.setText(simulatedTime + format.format(new Date(timeInMillis)));
	}

	private static long addSecondToCounter(long d) {

		return d + 1000;

	}

	private static long resetLabel(long d) {
		return BASE_DATE;
	}

	private static void updateLabel(JLabel label, long d) {

		SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
		label.setText(format.format(new Date(d)) + " " + sinceLast);

	}

}
