package na.oasisUtils.trustedSecurityNetwork;

import na.oasisUtils.ami.AmiConnector;
import na.oasisUtils.profile.ProfileConnector;
import na.utils.OASIS_ServiceUnavailable;
import na.utils.ServiceInterface;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class Login {
    private Log log = LogFactory.getLog(Login.class);

    /**
     * Login user into the system using username, password and
     * preferredLanguage. Username and password retrieved from user.properties
     * file preferredLanguage from Profile.
     */
    public boolean logMeIn() {
	// read credentials from file
	String username = ProfileConnector.getInstance()
		.getNutritionalUsername();
	String password = ProfileConnector.getInstance()
		.getNutritionalPassword();
	String lang = ProfileConnector.getInstance().getScreenLanguage();
	log.info("Lang es: " + lang);
	int preferredLanguage = ProfileConnector.getInstance().getCodeLang();

	try {
	    // ask login
	    log.info("asking login with username: " + username + " lang: "
		    + preferredLanguage);
	    String[] input = { username, password, "" + preferredLanguage };
	    AmiConnector ami = AmiConnector.getAMI();
	    if (ami != null) {
		String token = (String) ami.invokeOperation(
			ServiceInterface.DOMAIN_Nutrition,
			ServiceInterface.OP_GetToken, input, false);
		if (token != null && token.length() == 32) {
		    TSFConnector.getInstance().setToken(token);
		    return true;
		} else {
		    log.error("E R R O R: Fallo de autenticación");
		}
		// log.info(" token recibido: "+TSFConnector.getInstance().getToken());
	    } else {
		log.fatal("Ami not available");
	    }
	    return false;
	} catch (OASIS_ServiceUnavailable e) {
	    e.printStackTrace();
	    log.fatal("could not authenticate, service unavailable: "
		    + e.getMessage());
	}
	return false;
    }

}
