package org.universAAL.ltba.pir;

import org.universAAL.ltba.manager.Setup;

public class PIRController {

	protected final String serverIP;
	protected final String userCode;

	public PIRController() {
		serverIP = Setup.getServerIP();
		userCode = Setup.getUserCode();
	}

}
