package org.universAAL.FitbitPublisher.FitbitAPI;

/* 
 * Author: Carsten Ringe
 * Project repository: https://github.com/MoriTanosuke/fitbitclient 
*/


import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.scribe.builder.ServiceBuilder;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.model.Verifier;
import org.scribe.oauth.OAuthService;
import org.universAAL.FitbitPublisher.utils.Setup;

public class FitbitService {
	
	private static final String YOUR_API_KEY = "415ed8103d0e4d62875a1c17f61ea03f";
	private static final String YOUR_API_SECRET = "f154b0b448cf4d9aa4ddff5e21bed622";
	private static final OAuthService service = new ServiceBuilder()
			.provider(FitbitApi.class).apiKey(YOUR_API_KEY)
			.apiSecret(YOUR_API_SECRET).build();
	private static final String FORMAT = "json";
	private static final SimpleDateFormat DATEFORMAT = new SimpleDateFormat(
			"yyyy-MM-dd");
	private Token accessToken;
	private Token requestToken;
	private final String user = "-";
	private static final Logger LOG = Logger.getLogger(FitbitService.class
			.getName());
	

	public FitbitService() throws FitbitException {
		this(load());
	}

	public FitbitService(final Token accessToken) {
		this.accessToken = accessToken;
	}

	public String getProfile() throws FitbitException {
		return get("profile");
	}

	public String getActivities() throws FitbitException {
		return get("activities/date", today());
	}

	public String getFoods() throws FitbitException {
		return get("foods/log/date", today());
	}

	public String getWater() throws FitbitException {
		return get("foods/log/water/date", today());
	}

	public String getSleep() throws FitbitException {
		return get("sleep/date", today());
	}

	public String getBloodPressure() throws FitbitException {
		return get("bp/date", today());
	}

	public String getBodyMeasurements() throws FitbitException {
		return get("body/date", today());
	}

	public String getAuthorizationUrl() {
		requestToken = service.getRequestToken();
		return service.getAuthorizationUrl(requestToken);
	}

	public void authorize(final String pin) throws FitbitException {
		System.out.print("Authorizing app");
		if (requestToken == null) {
			throw new FitbitException(
					"No request token available! Restart authorization.");
		}
		save(service.getAccessToken(requestToken, new Verifier(pin)));
	}

	private String getUser() {
		return user;
	}

	private static String getFormat() {
		return "." + FORMAT;
	}

	private String today() {
		//return DATEFORMAT.format(new Date());
		return "2012-06-18";
	}

	private String get(final String category) throws FitbitException {
		return get(category, "", "");
	}

	private String get(final String category, final String information)
			throws FitbitException {
		return get(category, information, "");
	}

	private String get(final String category, final String information,
			final String range) throws FitbitException {
		final OAuthRequest request = new OAuthRequest(Verb.GET, buildUrl(
				category, information, range));
		service.signRequest(accessToken, request);
		final Response response = request.send();
		final String data = response.getBody();
		return validate(data);
	}

	private String buildUrl(final String category, final String information,
			final String range) {
		final String url = getBaseUrl() + add(category) + add(information)
				+ add(range) + getFormat();
		if (LOG.isLoggable(Level.FINE)) {
			LOG.fine("buildUrl=" + url);
		}
		return url;
	}

	private String validate(String json) throws FitbitException {
		if (json.startsWith("{\"errors\"")) {
			throw new FitbitException("Error while talking to Fitbit! " + json);
		}
		return json;
	}

	private String getBaseUrl() {
		return FitbitApi.BASE_URL + "/1/user/" + getUser();
	}

	private String add(String element) {
		String s = "";
		if (element != null && !"".equals(element)) {
			s += "/" + element;
		}
		return s;
	}

	private void save(final Token accessToken) throws FitbitException {
		this.accessToken = accessToken;
		
		Setup s = new Setup();
		s.setAccessToken(accessToken.getToken());
		s.setAccessTokenSecret(accessToken.getSecret());
	}

	private static Token load() throws FitbitException {
		Setup s = new Setup();
		return new Token(s.getAccessToken(),s.getAccessTokenSecret());
	}

		
}