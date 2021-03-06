package org.universAAL.ltba.functional.pir.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.Timer;

import org.universAAL.ltba.functional.pir.LTBAController;

import es.tsb.ltba.nomhad.gateway.NomhadGateway;

public class ShoppingController extends LTBAController implements ActionListener{

	private int times;
	private static ShoppingController INSTANCE;
	private Timer t;
	private String serverIp;
	private String userCode;
	/**
	 * The device ID. When the user management be made, the DEVICE_ID must
	 * content a reference to the user, in orden to not crossing the same device
	 * with different users. (TODO).
	 */
	private final String DEVICE_ID = "ALL_DET";

	private ShoppingController(){
		super();
		INSTANCE = this;
		t = new Timer(24 * 60 * 60 * 1000, this);
		Calendar today = new GregorianCalendar();
		Calendar startTime = new GregorianCalendar(today.get(Calendar.YEAR),
				today.get(Calendar.MONTH), today.get(Calendar.DATE), 12, 00);
		t.setInitialDelay((int) (startTime.getTimeInMillis() - System
				.currentTimeMillis()));
		t.start();
	}

	public static ShoppingController getInstance() {
		if (INSTANCE == null)
			return new ShoppingController();
		else
			return INSTANCE;
	}

	public int addBackFromShopping() {
		times++;
		return times;
	}

	public void actionPerformed(ActionEvent e) {
		System.out.println("Sending to Nomhad awakening times>" + times);
		NomhadGateway.getInstance().putMeasurement(serverIp, userCode,
				userPassword, "SHOPPING_INDICATORS_GROUP", "SHOPPING",
				new String("" + times), DEVICE_ID);
		times = 0;
	}

}
