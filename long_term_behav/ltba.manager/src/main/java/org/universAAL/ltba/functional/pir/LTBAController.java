package org.universAAL.ltba.functional.pir;

import org.universAAL.ltba.manager.Setup;

public class LTBAController {

	protected final String serverIP;
	protected final String userCode;
	protected final String userPassword;

	public LTBAController() {
		serverIP = Setup.getServerIP();
		userCode = Setup.getUserCode();
		userPassword = Setup.getUserPassword();
	}

}
