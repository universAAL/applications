package org.universAAL.ltba.functional.pir.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.Timer;

import org.universAAL.ltba.functional.pir.LTBAController;

import es.tsb.ltba.nomhad.gateway.NomhadGateway;

public class PresenceInKitchenController extends LTBAController implements
		ActionListener {

	private static PresenceInKitchenController INSTANCE;
	private Timer t;
	/**
	 * The device ID. When the user management be made, the DEVICE_ID must
	 * content a reference to the user, in orden to not crossing the same device
	 * with different users. (TODO).
	 */
	private final String DEVICE_ID = "KITCHEN_DET";
	private PresenceInKitchenController() {
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

	public static PresenceInKitchenController getInstance() {
		if (INSTANCE == null)
			return new PresenceInKitchenController();
		else
			return INSTANCE;
	}

	public void presenceInBathStart(float hour) {
		NomhadGateway.getInstance().putMeasurement(serverIP, userCode,
				userPassword, "PRESENCE_IN_KITCHEN", "PRESENCE_IN_KITCHEN_START",
				new String("" + hour), DEVICE_ID);	}

	public void presenceInBathStop(float hour) {
		NomhadGateway.getInstance().putMeasurement(serverIP, userCode,
				userPassword, "PRESENCE_IN_KITCHEN", "PRESENCE_IN_KITCHEN_STOP",
				new String("" + hour), DEVICE_ID);	}

	public void actionPerformed(ActionEvent e) {
		// NomhadGateway.getInstance().putMeasurement("192.168.238.40", "A100",
		// "123456", "SLEEPING", "GOING_TO_BED", new String("" + gtbTime));
		// TimeSleepingController.setGTBTime((float) gtbTime);
	}

}
