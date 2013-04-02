package org.universAAL.ltba.pir;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.Timer;

import es.tsb.ltba.nomhad.gateway.NomhadGateway;

public class CurrentActivityController {

	private final String INDICATOR_GROUP = "CURRENT_ACTIVITY_INDICATORS_GROUP";
	private final String INDICATOR = "CURRENT_ACTIVITY_LEVEL";
	private String serverIp = "192.168.238.40";
	private String userCode = "A100";

	private static CurrentActivityController INSTANCE;

	private CurrentActivityController() {
		super();
		INSTANCE = this;
	}

	public static CurrentActivityController getInstance() {
		if (INSTANCE == null)
			return new CurrentActivityController();
		else
			return INSTANCE;
	}

	public void activityDetected(String index) {
		System.out.println("Imprimiendo el valor: " + index);
		NomhadGateway.getInstance().putMeasurement(serverIp, userCode,
				"123456", INDICATOR_GROUP, INDICATOR, new String(index));
	}

}
