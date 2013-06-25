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
	private String serverIp;
	private String userCode;
	/**
	 * The device ID. When the user management be made, the DEVICE_ID must
	 * content a reference to the user, in orden to not crossing the same device
	 * with different users. (TODO).
	 */
	private final String DEVICE_ID = "BEDROOM_DET";

	private WakeUpController() {
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
		NomhadGateway.getInstance().putMeasurement(serverIp, userCode,
				"123456", "SLEEPING", "GETTING_UP", new String("" + wuTime),
				DEVICE_ID);
		TimeSleepingController.getInstance().setWUTime((float) wuTime);
		wuTime = 0;
	}

}
