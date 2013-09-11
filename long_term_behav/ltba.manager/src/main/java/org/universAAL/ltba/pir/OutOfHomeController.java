package org.universAAL.ltba.pir;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.Timer;

import es.tsb.ltba.nomhad.gateway.NomhadGateway;

public class OutOfHomeController implements ActionListener {

	private static OutOfHomeController INSTANCE;
	private Timer t;
	private String serverIp;
	private String userCode;

	private OutOfHomeController() {
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
				today.get(Calendar.MONTH), today.get(Calendar.DATE), 23, 45);
		t.setInitialDelay((int) (startTime.getTimeInMillis() - System
				.currentTimeMillis()));
		t.start();
	}

	public static OutOfHomeController getInstance() {
		if (INSTANCE == null)
			return new OutOfHomeController();
		else
			return INSTANCE;
	}

	public void outOfHomeStart(float hour) {
		// TODO
	}

	public void outOfHomeStop(float hour) {
		// TODO
	}

	public void actionPerformed(ActionEvent e) {
		// NomhadGateway.getInstance().putMeasurement("192.168.238.40", "A100",
		// "123456", "SLEEPING", "GOING_TO_BED", new String("" + gtbTime));
		// TimeSleepingController.setGTBTime((float) gtbTime);
	}

}
