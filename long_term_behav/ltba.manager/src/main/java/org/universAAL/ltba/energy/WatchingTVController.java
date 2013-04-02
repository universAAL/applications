package org.universAAL.ltba.energy;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.Timer;
import org.osgi.framework.BundleContext;
import es.tsb.ltba.nomhad.gateway.NomhadGateway;

public class WatchingTVController implements ActionListener {

	private double time;
	private int times;
	private static WatchingTVController INSTANCE;
	private Timer t;

	private WatchingTVController() {
		super();
		INSTANCE = this;
		t = new Timer(10 * 1000, this);
		Calendar today = new GregorianCalendar();
		Calendar startTime = new GregorianCalendar(today.get(Calendar.YEAR),
				today.get(Calendar.MONTH), today.get(Calendar.DATE), 14, 33);
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
				"123456", "ACTIVITIES", "TIME_WATCHING_TV",
				new String("" + time));
		time = 0;
		times = 0;
	}

}
