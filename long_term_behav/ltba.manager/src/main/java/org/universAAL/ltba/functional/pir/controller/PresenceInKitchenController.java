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
	private float start_measurement = -1;
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

	public void presenceInKitchenStart(float hour) {
		start_measurement = hour;
	}

	public void presenceInKitchenStop(float hour) {
		if (start_measurement != -1) {
			NomhadGateway.getInstance().putMeasurement(serverIP,
					Calendar.getInstance().get(Calendar.YEAR),
					Calendar.getInstance().get(Calendar.MONTH),
					Calendar.getInstance().get(Calendar.DAY_OF_MONTH), 12, 00,
					userCode, userPassword, "PRESENCE_IN_KITCHEN",
					"PRESENCE_IN_KITCHEN_START", new String("" + hour),
					DEVICE_ID);
			NomhadGateway.getInstance().putMeasurement(serverIP,
					Calendar.getInstance().get(Calendar.YEAR),
					Calendar.getInstance().get(Calendar.MONTH),
					Calendar.getInstance().get(Calendar.DAY_OF_MONTH), 12, 00,
					userCode, userPassword, "PRESENCE_IN_KITCHEN",
					"PRESENCE_IN_KITCHEN_END", new String("" + hour),
					DEVICE_ID);
			start_measurement = -1;
		}
	}

	public void actionPerformed(ActionEvent e) {
		// NomhadGateway.getInstance().putMeasurement("192.168.238.40", "A100",
		// "123456", "SLEEPING", "GOING_TO_BED", new String("" + gtbTime));
		// TimeSleepingController.setGTBTime((float) gtbTime);
	}

}
