package org.universAAL.ltba.functional.energy.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.Timer;
import org.osgi.framework.BundleContext;
import org.universAAL.ltba.functional.pir.LTBAController;

import es.tsb.ltba.nomhad.gateway.NomhadGateway;

public class WatchingTVController extends LTBAController implements
		ActionListener {

	private double time;
	private int times;
	private static WatchingTVController INSTANCE;
	private Timer t;
	/**
	 * The device ID. When the user management be made, the DEVICE_ID must
	 * content a reference to the user, in orden to not crossing the same device
	 * with different users. (TODO).
	 */
	private final String DEVICE_ID = "TV_PLUG";

	private WatchingTVController() {
		super();
		INSTANCE = this;
		t = new Timer(10 * 1000, this);
		Calendar today = new GregorianCalendar();
		Calendar startTime = new GregorianCalendar(today.get(Calendar.YEAR),
				today.get(Calendar.MONTH), today.get(Calendar.DATE), 3, 00);
		t.setDelay(24 * 60 * 60 * 1000);
		t.setInitialDelay((int) (startTime.getTimeInMillis() - System
				.currentTimeMillis()));
		t.start();
	}

	public static WatchingTVController getInstance() {
		if (INSTANCE == null)
			return new WatchingTVController();
		else
			return INSTANCE;
	}

	public double addTime(long timeToAddInMillis) {

		double timeInHours = (double) timeToAddInMillis / (3600 * 1000);
		System.out.println("Time in millis: " + timeToAddInMillis
				+ " Time in hours: " + timeInHours);
		time += timeInHours;
		times++;
		return time;
	}

	public void actionPerformed(ActionEvent e) {
		System.out.println("Sending to Nomhad time>" + time + " and times>"
				+ times);
		NomhadGateway.getInstance().putMeasurement(serverIP, userCode,
				userPassword, "ACTIVITIES", "TIME_WATCHING_TV",
				new String("" + time), DEVICE_ID);
		time = 0;
		times = 0;
	}

}
