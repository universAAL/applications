package org.universAAL.ltba.functional.pir.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.Timer;

import org.universAAL.ltba.functional.pir.LTBAController;

public class PresenceInBathController extends LTBAController implements
		ActionListener {

	private static PresenceInBathController INSTANCE;
	private Timer t;

	private PresenceInBathController() {
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

	public static PresenceInBathController getInstance() {
		if (INSTANCE == null)
			return new PresenceInBathController();
		else
			return INSTANCE;
	}

	public void presenceInBathStart(float hour) {
		// TODO
	}

	public void presenceInBathStop(float hour) {
		// TODO
	}

	public void actionPerformed(ActionEvent e) {
		// NomhadGateway.getInstance().putMeasurement("192.168.238.40", "A100",
		// "123456", "SLEEPING", "GOING_TO_BED", new String("" + gtbTime));
		// TimeSleepingController.setGTBTime((float) gtbTime);
	}

}