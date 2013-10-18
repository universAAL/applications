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
	 * The device ID. When the user management be made, the DEVICE_ID must
	 * content a reference to the user, in orden to not crossing the same device
	 * with different users. (TODO).
	 */
	private final String DEVICE_ID = "BEDROOM_DET";

	private GoingToBedController() {
		super();
		INSTANCE = this;
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
		NomhadGateway.getInstance().putMeasurement(serverIP, userCode,
				"123456", "SLEEPING", "GOING_TO_BED", new String("" + gtbTime),
				DEVICE_ID);
		TimeSleepingController.setGTBTime((float) gtbTime);
		gtbTime = 0;
	}

}
