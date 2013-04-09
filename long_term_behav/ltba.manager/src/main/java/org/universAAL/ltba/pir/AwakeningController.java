package org.universAAL.ltba.pir;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.Timer;

import es.tsb.ltba.nomhad.gateway.NomhadGateway;

public class AwakeningController implements ActionListener {

	private static int AWAKENING_UPDATE_PERIOD = 24 * 60 * 60 * 1000;
	private static int AWAKENING_REPORT_HOUR = 12;
	private static int AWAKENING_REPORT_MINUTES = 00;
	private final String INDICATOR_GROUP = "ACTIVITIES";
	private final String INDICATOR = "AWAKENING_COUNT";
	private String serverIp = "192.168.238.40";
	private String userCode = "A100";

	private int times;
	private static AwakeningController INSTANCE;
	private Timer t;

	private AwakeningController() {
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
		t = new Timer(AWAKENING_UPDATE_PERIOD, this);
		Calendar today = new GregorianCalendar();
		Calendar startTime = new GregorianCalendar(today.get(Calendar.YEAR),
				today.get(Calendar.MONTH), today.get(Calendar.DATE),
				AWAKENING_REPORT_HOUR, AWAKENING_REPORT_MINUTES);
		t.setInitialDelay((int) (startTime.getTimeInMillis() - System
				.currentTimeMillis()));
		t.start();
	}

	public static AwakeningController getInstance() {
		if (INSTANCE == null)
			return new AwakeningController();
		else
			return INSTANCE;
	}

	public int addAwakening() {
		times++;
		return times;
	}

	public void actionPerformed(ActionEvent e) {
		System.out.println("Sending to Nomhad awakening times>" + times);
		NomhadGateway.getInstance().putMeasurement(serverIp, userCode,
				"123456", INDICATOR_GROUP, INDICATOR, new String("" + times));
		times = 0;
	}

}
