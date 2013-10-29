package org.universAAL.ltba.functional.pir.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.Timer;

import org.universAAL.ltba.functional.pir.LTBAController;
import org.universAAL.middleware.container.utils.LogUtils;

import es.tsb.ltba.nomhad.gateway.NomhadGateway;

public class PresenceInBathController extends LTBAController {

	private static PresenceInBathController INSTANCE;
	public static float start_measure = -1;

	/**
	 * The device ID. When the user management be made, the DEVICE_ID must
	 * content a reference to the user, in order to not crossing the same device
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
		System.out.println("PRESENCEINBATHCONTROLLER->START start_measure: "
				+ hour);
		start_measure = hour;

	}

	public void presenceInBathStop(float hour) {
		System.out.println("PRESENCEINBATHCONTROLLER->STOP start_measure->"
				+ start_measure);
		if (start_measure != -1) {

			System.out.println("PRESENCE STOPPED-> NOW I HAVE TO POST"
					+ " start_measure:" + start_measure + " stop_measure: "
					+ hour);

			NomhadGateway.getInstance().putMeasurement(serverIP,
					Calendar.getInstance().get(Calendar.YEAR),
					Calendar.getInstance().get(Calendar.MONTH),
					Calendar.getInstance().get(Calendar.DAY_OF_MONTH), 12, 00,
					userCode, userPassword, "PRESENCE_IN_BATH",
					"PRESENCE_IN_BATH_START", new String("" + start_measure),
					DEVICE_ID);

			NomhadGateway.getInstance().putMeasurement(serverIP,
					Calendar.getInstance().get(Calendar.YEAR),
					Calendar.getInstance().get(Calendar.MONTH),
					Calendar.getInstance().get(Calendar.DAY_OF_MONTH), 12, 00,
					userCode, userPassword, "PRESENCE_IN_BATH",
					"PRESENCE_IN_BATH_END", new String("" + hour), DEVICE_ID);
			start_measure = -1;
		}
	}

}
