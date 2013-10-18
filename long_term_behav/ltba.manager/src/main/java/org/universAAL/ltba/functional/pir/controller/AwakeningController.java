package org.universAAL.ltba.functional.pir.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.Timer;

import org.universAAL.ltba.functional.pir.LTBAController;
import org.universAAL.ltba.manager.Setup;

import es.tsb.ltba.nomhad.gateway.NomhadGateway;

public class AwakeningController extends LTBAController implements
		ActionListener {

	private static int AWAKENING_UPDATE_PERIOD = Setup.getAwakeningUpdatePeriod();
	private static int AWAKENING_REPORT_HOUR = Setup.getReportHour();
	private static int AWAKENING_REPORT_MINUTES = Setup.getReportMinutes();
	private final String INDICATOR_GROUP = "ACTIVITIES";
	private final String INDICATOR = "AWAKENING_COUNT";

	/**
	 * The device ID. When the user management be made, the DEVICE_ID must
	 * content a reference to the user, in orden to not crossing the same device
	 * with different users. (TODO).
	 */
	private final String DEVICE_ID = "BEDROOM_DET";

	private int times;
	private static AwakeningController INSTANCE;
	private Timer t;

	private AwakeningController() {
		super();
		INSTANCE = this;
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
		NomhadGateway.getInstance().putMeasurement(serverIP, userCode,
				userPassword, INDICATOR_GROUP, INDICATOR,
				new String("" + times), DEVICE_ID);
		times = 0;
	}

}
