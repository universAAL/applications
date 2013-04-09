package org.universAAL.ltba.pir;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.Timer;

import es.tsb.ltba.nomhad.gateway.NomhadGateway;

public class TimeSleepingController implements ActionListener {

	private static float wuTime;
	private int times;
	private static TimeSleepingController INSTANCE;
	private Timer t;
	private String serverIp;
	private String userCode;
	private static float gtbTime;

	private TimeSleepingController() {
		super();
		INSTANCE = this;
		String ip = System.getProperty("es.tsbtecnologias.nomhad.server.ip");
		String usr = System.getProperty("es.tsbtecnologias.nomhad.usercode");
		if (ip != null) {
			serverIp = ip;
		}
		if (usr != null) {
			userCode = usr;
		}
		t = new Timer(24 * 60 * 60 * 1000, this);
		Calendar today = new GregorianCalendar();
		Calendar startTime = new GregorianCalendar(today.get(Calendar.YEAR),
				today.get(Calendar.MONTH), today.get(Calendar.DATE), 12, 30);
		t.setInitialDelay((int) (startTime.getTimeInMillis() - System
				.currentTimeMillis()));
		t.start();
	}

	public static TimeSleepingController getInstance() {
		if (INSTANCE == null)
			return new TimeSleepingController();
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
		System.out.println("Sending to Nomhad sleep time>"
				+ calculateSleepTime() + " and times>" + times);
		NomhadGateway.getInstance().putMeasurement(serverIp, userCode,
				"123456", "SLEEPING", "SLEEP_HOURS",
				new String("" + calculateSleepTime()));
		wuTime = 0;
	}

	private float calculateSleepTime() {
		if (gtbTime != 0 && wuTime != 0) {
			return wuTime - gtbTime;
		} else {
			return 0;
		}
	}

	public static void setGTBTime(float gtb) {
		gtbTime = gtb;
	}

	public static void setWUTime(float wu) {
		wuTime = wu;
	}
}
