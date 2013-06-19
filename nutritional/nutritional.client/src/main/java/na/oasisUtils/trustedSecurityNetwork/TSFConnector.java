package na.oasisUtils.trustedSecurityNetwork;

import na.oasisUtils.profile.ProfileConnector;

public class TSFConnector {

    private String token = "";
    private static TSFConnector instance;

    private String socialNetwork_name = null;

    private String socialNetwork_pass = null; 

    private TSFConnector() {

    }

    public static TSFConnector getInstance() {
	if (instance == null) {
	    instance = new TSFConnector();
	}
	return instance;
    }

    public String getToken() {
	return this.token;
    }

    public void setToken(String token) {
	this.token = token;
    }

    public String getSocialCommunitiesUsername() {
	if (socialNetwork_name != null)
	    return socialNetwork_name;
	else {
	    String user = ProfileConnector.getInstance()
		    .getSocialCommunityUsername();
	    return user;
	}
    }

    public String getSocialCommunitiesPassword() {
	if (socialNetwork_name != null)
	    return socialNetwork_name;
	else {
	    String user = ProfileConnector.getInstance()
		    .getSocialCommunityPassword();
	    return user;
	}
    }
}
