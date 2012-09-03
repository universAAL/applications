package na;

import na.oasisUtils.trustedSecurityNetwork.Login;
import na.utils.InitialSetup;
import na.utils.Setup;

public class Arranque {

	public void inicializar(String user) {
		Setup.AMI_UserName = user;
		// 2. Setup NA DIR
		InitialSetup.initNutriAdvisorFolder();

		Login login = new Login();
		boolean login_succesful = login.logMeIn();
	}
	
}
