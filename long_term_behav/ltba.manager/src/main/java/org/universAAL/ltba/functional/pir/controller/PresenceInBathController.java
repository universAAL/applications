package org.universAAL.ltba.functional.pir.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.Timer;

import org.universAAL.ltba.functional.pir.LTBAController;

import es.tsb.ltba.nomhad.gateway.NomhadGateway;

public class PresenceInBathController extends LTBAController {

	private static PresenceInBathController INSTANCE;
	private Timer t;

	/**
	 * The device ID. When the user management be made, the DEVICE_ID must
	 * content a reference to the user, in orden to not crossing the same device
	 * with different users. (TODO).
	 */
	private final String DEVICE_ID = "BATH_DET";

	private PresenceInBathController() {
		super();
	}

	public static PresenceInBathController getInstance() {
		if (INSTANCE == null)
			return new PresenceInBathController();
		else
			return INSTANCE;
	}

	public void presenceInBathStart(float hour) {
		NomhadGateway.getInstance().putMeasurement(serverIP, userCode,
				userPassword, "PRESENCE_IN_BATH", "PRESENCE_IN_BATH_START",
				new String("" + hour), DEVICE_ID);
	}

	public void presenceInBathStop(float hour) {
		NomhadGateway.getInstance().putMeasurement(serverIP, userCode,
				userPassword, "PRESENCE_IN_BATH", "PRESENCE_IN_BATH_STOP",
				new String("" + hour), DEVICE_ID);
	}

}
