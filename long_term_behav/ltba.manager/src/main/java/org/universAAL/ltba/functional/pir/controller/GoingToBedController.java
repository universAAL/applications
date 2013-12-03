package org.universAAL.ltba.functional.pir.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.Timer;

import org.universAAL.ltba.functional.pir.LTBAController;

import es.tsb.ltba.nomhad.gateway.NomhadGateway;

public class GoingToBedController extends LTBAController implements
		ActionListener {

	private double gtbTime;
	private int times;
	private static GoingToBedController INSTANCE;
	private Timer t;

	/**
	 * The device ID. When the user management will be implemented, the
	 * DEVICE_ID must content a reference to the user, in order not to using the
	 * same device with different users. (TODO).
	 */
	private final String DEVICE_ID = "BEDROOM_DET";

	private GoingToBedController() {
		super();
		INSTANCE = this;
		// CHECK THIS AT 23.45 PM
		t = new Timer(24 * 60 * 60 * 1000, this);
		Calendar today = new GregorianCalendar();
		Calendar startTime = new GregorianCalendar(today.get(Calendar.YEAR),
				today.get(Calendar.MONTH), today.get(Calendar.DATE), 23, 45);
		t.setInitialDelay((int) (startTime.getTimeInMillis() - System
				.currentTimeMillis()));
		t.start();
	}

	public static GoingToBedController getInstance() {
		if (INSTANCE == null)
			return new GoingToBedController();
		else
			return INSTANCE;
	}

	public void addGoingToBedHour(float hour) {
		System.out.println("Adding..." + hour);
		if (gtbTime < hour) {
			gtbTime = hour;
		}
	}

	public void actionPerformed(ActionEvent e) {
		System.out.println("Sending to Nomhad time>" + gtbTime + " and times>"
				+ times);
		// This condition ensures that the going to bed graph is over the waking
		// up time.
		// gtbtime = 01.00am -> 25 ; gtbtime = 03.00am -> 27 and so on...
		if (gtbTime < 8) {
			gtbTime = gtbTime + 24;
		}
//		NomhadGateway.getInstance().putMeasurement(serverIP, userCode,
//				userPassword, "SLEEPING", "GOING_TO_BED",
//				new String("" + gtbTime), DEVICE_ID);
		NomhadGateway.getInstance().putMeasurement(serverIP,
				Calendar.getInstance().get(Calendar.DAY_OF_MONTH),
				Calendar.getInstance().get(Calendar.MONTH),
				Calendar.getInstance().get(Calendar.YEAR), 12, 00, userCode,
				userPassword, "SLEEPING", "GOING_TO_BED",
				new String("" + gtbTime), DEVICE_ID);
		TimeSleepingController.setGTBTime((float) gtbTime);
		gtbTime = 0;
	}

}
