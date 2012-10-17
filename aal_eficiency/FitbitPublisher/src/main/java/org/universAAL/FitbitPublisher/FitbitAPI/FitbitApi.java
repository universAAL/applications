package org.universAAL.FitbitPublisher.FitbitAPI;

/* 
 * Author: Carsten Ringe
 * Project repository: https://github.com/MoriTanosuke/fitbitclient 
*/

import org.scribe.builder.api.DefaultApi10a;
import org.scribe.model.Token;

public class FitbitApi extends DefaultApi10a {

	public static final String BASE_URL = "https://api.fitbit.com";
	private static final String ENDPOINT_ACCESSTOKEN = BASE_URL
			+ "/oauth/access_token";
	private static final String ENDPOINT_REQUESTTOKEN = BASE_URL
			+ "/oauth/request_token";
	private static final String ENDPOINT_AUTHORIZATION = "https://www.fitbit.com/oauth/authorize?oauth_token=%s&oauth_secret=%s";

	@Override
	public String getAccessTokenEndpoint() {
		return ENDPOINT_ACCESSTOKEN;
	}

	@Override
	public String getRequestTokenEndpoint() {
		return ENDPOINT_REQUESTTOKEN;
	}

	@Override
	public String getAuthorizationUrl(Token requestToken) {
		return String.format(ENDPOINT_AUTHORIZATION, requestToken.getToken(),
				requestToken.getSecret());
	}

}
