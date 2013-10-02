package org.universAAL.ltba.pir;

import org.universAAL.ltba.activity.Room;
import org.universAAL.ltba.manager.ConsequenceListener;
import org.universAAL.middleware.container.utils.LogUtils;

import es.tsb.ltba.nomhad.gateway.NomhadGateway;

public class CurrentActivityController extends PIRController {

	private final String INDICATOR_GROUP = "CURRENT_ACTIVITY_INDICATORS_GROUP";
	private final String INDICATOR = "CURRENT_ACTIVITY_LEVEL";
	// private String serverURL = "192.168.238.40";
	// private String userCode = "A100";
	/**
	 * The device ID. When the user management be made, the DEVICE_ID must
	 * content a reference to the user, in orden to not crossing the same device
	 * with different users. (TODO).
	 */
	private final String DEVICE_ID_ALL = "ALL_DET";
	private final String DEVICE_ID_ROOM = "_DET";

	private static CurrentActivityController INSTANCE;

	private CurrentActivityController() {
		super();
		INSTANCE = this;
//		String ip = System.getProperty("es.tsbtecnologias.nomhad.server.ip");
//		String usr = System.getProperty("es.tsbtecnologias.nomhad.usercode");
//		if (ip != null) {
//			serverURL = ip;
//		}
//		if (usr != null) {
//			userCode = usr;
//		}
	}

	public static CurrentActivityController getInstance() {
		if (INSTANCE == null)
			return new CurrentActivityController();
		else
			return INSTANCE;
	}

	public void activityDetected(String index) {
		LogUtils.logDebug(ConsequenceListener.getInstance().getModuleContext(),
				getClass(), "ActivityDetected(generic)",
				new String[] { "Printing value: " + index }, null);
		System.out.println("Imprimiendo el valor: " + index);
		NomhadGateway.getInstance().putMeasurement(serverIP, userCode,
				"123456", INDICATOR_GROUP, INDICATOR, new String(index),
				DEVICE_ID_ALL);
	}

	public void activityDetected(String index, Room room) {
		if (room != null) {
			LogUtils.logDebug(ConsequenceListener.getInstance()
					.getModuleContext(), getClass(),
					"ActivityDetected(by room)",
					new String[] { "Printing value: " + index }, null);
			System.out.println("Printing value: " + index + " in "
					+ room.getRoomStringNoBlanks());
			NomhadGateway.getInstance().putMeasurement(serverIP, userCode,
					"123456", INDICATOR_GROUP,
					INDICATOR + "_" + room.getRoomStringNoBlanks(),
					new String(index),
					room.getRoomStringNoBlanks() + DEVICE_ID_ROOM);
		} else {
			activityDetected(index);
		}
	}

}
