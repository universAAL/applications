package org.universAAL.ltba.pir;

import org.universAAL.ltba.manager.CommonUtils;

import es.tsb.ltba.nomhad.gateway.NomhadGateway;

public class ActivityIndexController {

	private final String INDICATOR_GROUP = "INDEXES";
	private final String INDICATOR = "ACTIVITY_LEVEL";
	private String serverIp = "192.168.238.40";
	private String userCode = "A100";
	private static ActivityIndexController INSTANCE;

	// private Timer t;

	private ActivityIndexController() {
		super();
		INSTANCE = this;
		String ip = System.getProperty("es.tsbtecnologias.nomhad.server.ip");
		String usr = System.getProperty("es.tsbtecnologias.nomhad.usercode");
		if (ip != null) {
			serverIp = ip;
		}
		if (usr != null) {
			userCode = usr;
		}
	}

	public static ActivityIndexController getInstance() {
		if (INSTANCE == null)
			return new ActivityIndexController();
		else
			return INSTANCE;
	}

	public void addActivityIndex(String index) {
		System.out.println("Sending to Nomhad activityIndex>" + index);
		NomhadGateway.getInstance().putMeasurement(serverIp, userCode,
				CommonUtils.NOMHAD_DEVICE_CODE, INDICATOR_GROUP, INDICATOR,
				new String(index));
	}

}
