package org.universAAL.ltba.pir;

import org.universAAL.ltba.activity.Room;

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
		String ip = System.getProperty("es.tsbtecnologias.nomhad.server.ip");
		String usr = System.getProperty("es.tsbtecnologias.nomhad.usercode");
		if (ip != null) {
			serverIp = ip;
		}
		if (usr != null) {
			userCode = usr;
		}
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

	public void activityDetected(String index, Room room) {
		if (room != null) {
			System.out.println("Imprimiendo el valor: " + index + " en "
					+ room.getRoomStringNoBlanks());
			NomhadGateway.getInstance().putMeasurement(serverIp, userCode,
					"123456", INDICATOR_GROUP,
					INDICATOR + "_" + room.getRoomStringNoBlanks(),
					new String(index));
		} else {
			activityDetected(index);
		}
	}

}
