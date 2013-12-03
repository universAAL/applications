package org.universAAL.ltba.functional.pir.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.Timer;

import org.universAAL.ltba.functional.pir.LTBAController;

import es.tsb.ltba.nomhad.gateway.NomhadGateway;

public class WakeUpController extends LTBAController implements ActionListener {

	private double wuTime;
	private int times;
	private static WakeUpController INSTANCE;
	private Timer t;

	/**
	 * The device ID. When the user management be made, the DEVICE_ID must
	 * content a reference to the user, in orden to not crossing the same device
	 * with different users. (TODO).
	 */
	private final String DEVICE_ID = "BEDROOM_DET";

	private WakeUpController() {
		super();
		INSTANCE = this;

		// Check the wake up hour at 13.00 PM
		Calendar thisTime = Calendar.getInstance();
		GregorianCalendar thisMorning = new GregorianCalendar(
				thisTime.get(Calendar.YEAR), thisTime.get(Calendar.MONTH),
				thisTime.get(Calendar.DAY_OF_MONTH), 13, 00);
		t = new Timer(24 * 60 * 60 * 1000, this);
		if (thisMorning.getTimeInMillis() < thisTime.getTimeInMillis()) {
			t.setInitialDelay((24 * 60 * 60 * 1000)
					- ((int) (thisMorning.getTimeInMillis() - thisTime
							.getTimeInMillis())));
		} else {
			t.setInitialDelay((int) (thisMorning.getTimeInMillis() - thisTime
					.getTimeInMillis()));
		}

		// Calendar today = new GregorianCalendar();
		// Calendar startTime = new GregorianCalendar(today.get(Calendar.YEAR),
		// today.get(Calendar.MONTH), today.get(Calendar.DATE), 12, 00);
		// t.setInitialDelay((int) (startTime.getTimeInMillis() - System
		// .currentTimeMillis()));
		t.start();
	}

	public static WakeUpController getInstance() {
		if (INSTANCE == null)
			return new WakeUpController();
		else
			return INSTANCE;
	}

	public void addWakingUpHour(float hour) {
		System.out.println("Adding..." + hour);
		if (wuTime < hour) {
			wuTime = hour;
		}
	}

	public void actionPerformed(ActionEvent e) {
		System.out.println("Sending to Nomhad time>" + wuTime + " and times>"
				+ times);
		// NomhadGateway.getInstance().putMeasurement(serverIP, userCode,
		// userPassword, "SLEEPING", "GETTING_UP", new String("" + wuTime),
		// DEVICE_ID);
		NomhadGateway.getInstance().putMeasurement(serverIP,
				Calendar.getInstance().get(Calendar.DAY_OF_MONTH),
				Calendar.getInstance().get(Calendar.MONTH),
				Calendar.getInstance().get(Calendar.YEAR), 12, 00, userCode,
				userPassword, "SLEEPING", "GETTING_UP",
				new String("" + wuTime), DEVICE_ID);
		TimeSleepingController.getInstance().setWUTime((float) wuTime);
		wuTime = 0;
	}

}
