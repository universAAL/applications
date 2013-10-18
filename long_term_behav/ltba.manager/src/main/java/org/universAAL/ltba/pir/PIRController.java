package org.universAAL.ltba.pir;

import org.universAAL.ltba.manager.Setup;

public class PIRController {

	protected final String serverIP;
	protected final String userCode;
	protected final String userPassword;

	public PIRController() {
		serverIP = Setup.getServerIP();
		userCode = Setup.getUserCode();
		userPassword = Setup.getUserPassword();
	}

}
