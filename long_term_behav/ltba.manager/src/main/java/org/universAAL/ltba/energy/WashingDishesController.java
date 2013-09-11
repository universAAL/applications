package org.universAAL.ltba.energy;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.Timer;
import org.osgi.framework.BundleContext;
import es.tsb.ltba.nomhad.gateway.NomhadGateway;

public class WashingDishesController implements ActionListener {

	private double time;
	private int times;
	private static WashingDishesController INSTANCE;
	private Timer t;
	/**
	 * The device ID. When the user management be made, the DEVICE_ID must
	 * content a reference to the user, in orden to not crossing the same device
	 * with different users. (TODO).
	 */
	private final String DEVICE_ID = "DW_PLUG";

	private WashingDishesController() {
		super();
		INSTANCE = this;
		t = new Timer(24 * 60 * 60 * 1000, this);
		Calendar today = new GregorianCalendar();
		Calendar startTime = new GregorianCalendar(today.get(Calendar.YEAR),
				today.get(Calendar.MONTH), today.get(Calendar.DATE), 23, 00);
		t.setInitialDelay((int) (startTime.getTimeInMillis() - System
				.currentTimeMillis()));
		t.start();
	}

	public static WashingDishesController getInstance() {
		if (INSTANCE == null)
			return new WashingDishesController();
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

	private static Date get23pm() {
		Calendar tomorrow = new GregorianCalendar();
		tomorrow.add(Calendar.DATE, 0);
		Calendar result = new GregorianCalendar(tomorrow.get(Calendar.YEAR),
				tomorrow.get(Calendar.MONTH), tomorrow.get(Calendar.DATE), 23,
				00);
		return result.getTime();
	}

	public void actionPerformed(ActionEvent e) {
		System.out.println("Sending to Nomhad time>" + time + " and times>"
				+ times);
		NomhadGateway.getInstance().putMeasurement("192.168.238.40", "A100",
				"123456", "ACTIVITIES", "WASHING_DISHES",
				new String("" + time), DEVICE_ID);
		time = 0;
		times = 0;
	}

}
