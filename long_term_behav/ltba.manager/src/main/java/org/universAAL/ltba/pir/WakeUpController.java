package org.universAAL.ltba.pir;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.Timer;

import es.tsb.ltba.nomhad.gateway.NomhadGateway;

public class WakeUpController implements ActionListener {

	private double wuTime;
	private int times;
	private static WakeUpController INSTANCE;
	private Timer t;

	private WakeUpController() {
		super();
		INSTANCE = this;
		t = new Timer(24 * 60 * 60 * 1000, this);
		Calendar today = new GregorianCalendar();
		Calendar startTime = new GregorianCalendar(today.get(Calendar.YEAR),
				today.get(Calendar.MONTH), today.get(Calendar.DATE), 12, 00);
		t.setInitialDelay((int) (startTime.getTimeInMillis() - System
				.currentTimeMillis()));
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
		NomhadGateway.getInstance().putMeasurement("192.168.238.40", "A100",
				"123456", "SLEEPING", "GETTING_UP", new String("" + wuTime));
		TimeSleepingController.getInstance().setWUTime((float) wuTime);
		wuTime = 0;
	}

}
