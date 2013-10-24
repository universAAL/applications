package org.universAAL.ltba.functional.pir.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.Timer;

import org.universAAL.ltba.functional.pir.LTBAController;

import es.tsb.ltba.nomhad.gateway.NomhadGateway;

public class OutOfHomeController extends LTBAController implements ActionListener {

	private static OutOfHomeController INSTANCE;
	private Timer t;
	private String serverIp;
	private String userCode;
	private final String DEVICE_ID = "HALL_DET";
	private OutOfHomeController() {
		super();
	}

	public static OutOfHomeController getInstance() {
		if (INSTANCE == null)
			return new OutOfHomeController();
		else
			return INSTANCE;
	}

	public void outOfHomeStart(float hour) {
		NomhadGateway.getInstance().putMeasurement(serverIP, userCode,
				userPassword, "GOING_OUT_INDICATORS_GROUP", "OUT_OF_HOME_START",
				new String("" + hour), DEVICE_ID);	
	}

	public void outOfHomeStop(float hour) {
		NomhadGateway.getInstance().putMeasurement(serverIP, userCode,
				userPassword, "GOING_OUT_INDICATORS_GROUP", "OUT_OF_HOME_STOP",
				new String("" + hour), DEVICE_ID);
	}

	public void actionPerformed(ActionEvent e) {
		// NomhadGateway.getInstance().putMeasurement("192.168.238.40", "A100",
		// "123456", "SLEEPING", "GOING_TO_BED", new String("" + gtbTime));
		// TimeSleepingController.setGTBTime((float) gtbTime);
	}

}
