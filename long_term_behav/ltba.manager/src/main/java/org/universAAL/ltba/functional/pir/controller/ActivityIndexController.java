package org.universAAL.ltba.functional.pir.controller;

import org.universAAL.ltba.functional.pir.LTBAController;
import org.universAAL.ltba.manager.ConsequenceListener;
import org.universAAL.middleware.container.utils.LogUtils;

import es.tsb.ltba.nomhad.gateway.NomhadGateway;

public class ActivityIndexController extends LTBAController {

	private final String INDICATOR_GROUP = "INDEXES";
	private final String INDICATOR = "ACTIVITY_LEVEL";

	private static ActivityIndexController INSTANCE;
	/**
	 * The device ID. When the user management be made, the DEVICE_ID must
	 * content a reference to the user, in orden to not crossing the same device
	 * with different users. (TODO).
	 */
	private final String DEVICE_ID = "ALL_DET";

	// private Timer t;

	private ActivityIndexController() {
		super();
		INSTANCE = this;

		// String ip = System.getProperty("es.tsbtecnologias.nomhad.server.ip");
		// String usr = System.getProperty("es.tsbtecnologias.nomhad.usercode");
		// String ip = Setup.getServerURL();
		// String usr = Setup.getUserCode();
		// if (ip != null) {
		// serverIp = ip;
		// }
		// if (usr != null) {
		// userCode = usr;
		// }
	}

	public static ActivityIndexController getInstance() {
		if (INSTANCE == null)
			return new ActivityIndexController();
		else
			return INSTANCE;
	}

	public void addActivityIndex(String index) {
		LogUtils.logInfo(ConsequenceListener.getInstance().getModuleContext(),
				getClass(), "addActivityIndex",
				new String[] { "Sending to Nomhad >FROM>FILE> ActivityIndex: "
						+ index }, null);
		System.out.println("Sending to Nomhad activityIndex>" + index);
		NomhadGateway.getInstance().putMeasurement(serverIP, userCode,
				userPassword, INDICATOR_GROUP, INDICATOR, new String(index),
				DEVICE_ID);
	}

}
