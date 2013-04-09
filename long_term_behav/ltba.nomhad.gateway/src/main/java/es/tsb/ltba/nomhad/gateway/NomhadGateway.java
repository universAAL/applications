package es.tsb.ltba.nomhad.gateway;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.http.client.ClientProtocolException;
import org.osgi.framework.BundleContext;

import es.tsb.ltba.nomhad.httpclient.NomhadHttpClient;

public class NomhadGateway {

	private static NomhadGateway INSTANCE;
	private static final String NOMHAD_URL_HEADER = "https://localhost:8443/nomhad/rest/2/cmr/patient/";
	private static final String OBSERVATIONS_REQUEST = "/observations";
	private static final String DEVICE_ID = "\"35-209900-176148-1\"";
	private static final String BODY = "{" +

	"\"meassurement\": {" + "\"indicatorsGroup\": \"INDICATOR_GROUP\","
			+ "  \"startDate\": \"19520723T120000+0100\"," + "\"devices\": ["
			+ "{" + "           \"id\": " + DEVICE_ID + ","
			+ "           \"specs\": [" + " \"MDC_DEV_SPEC_PROFILE_HYDRA\""
			+ "           ]," + "           \"observations\": ["
			+ "               {" + "                   \"type\": \"NO\","
			+ "                   \"indicator\": \"INDICATOR_MEASURED\","
			+ "\"value\": VALUE_MEASURED" + "}" + "]" + "}" + "]" + "}"
			+ "}            ";

	private NomhadGateway() {

	}

	public static NomhadGateway getInstance() {
		if (INSTANCE == null)
			return new NomhadGateway();
		else
			return INSTANCE;
	}

	public String putMeasurement(String server, String usr, String pwd,
			String indicatorGroup, String indicator, String measurement,
			long timeInMillis) {

		SimpleDateFormat beforeT = new java.text.SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat afterT = new SimpleDateFormat("hhmmss");
		Date currentDate = new Date(timeInMillis);
		String dateString = beforeT.format(currentDate);
		String hourString = afterT.format(Calendar.getInstance().getTime());
		String timestamp = dateString + "T" + hourString + "+0100";

		return putMeasurement(server, usr, pwd, indicatorGroup, indicator,
				measurement, timestamp);
	}

	public String putMeasurement(String server, String usr, String pwd,
			String indicatorGroup, String indicator, String measurement,
			String formattedate) {

		StringBuilder uri = new StringBuilder();
		String header = new String(NOMHAD_URL_HEADER);
		String body = new String(BODY);

		if (server.contains(":")) {
			header.replace("localhost:8443", server);
		} else {
			// System.out.println("Replacing localhost for " + server);
			header = header.replace("localhost", server);
			// System.out.println(header);
		}
//		System.out.println("DEPLOYED PROPERTY"
//				+ System.getProperty("es.tsbtecnologias.nomhad.ltba.deployed"));
		if (System.getProperty("es.tsbtecnologias.nomhad.ltba.deployed").equalsIgnoreCase("true")) {
//			System.out.println("REPLACING...");
			header = header.replace("nomhad", "ltba");
		}
		uri.append(header);
		uri.append(usr);
		uri.append(OBSERVATIONS_REQUEST);

		body = body.replace("INDICATOR_GROUP", indicatorGroup);
		body = body.replace("INDICATOR_MEASURED", indicator);
		body = body.replace("VALUE_MEASURED", measurement);
		body = body.replace("19520723T120000+0100", formattedate);

		NomhadHttpClient nhc = new NomhadHttpClient(usr, pwd);
		try {
			System.out.println(">>>>>>>>>NOMHAD POST<<<<<<<<<<<");
			System.out.println("URI: " + uri.toString());
			System.out.println("BODY: " + body.toString());
			System.out.println(nhc.post(uri.toString(), body));
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		nhc.stopClient();
		return uri.toString();

	}

	public String putMeasurement(String server, String usr, String pwd,
			String indicatorGroup, String indicator, String measurement) {

		SimpleDateFormat beforeT = new java.text.SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat afterT = new SimpleDateFormat("HHmmss");
		String date = beforeT.format(Calendar.getInstance().getTime());
		String hour = afterT.format(Calendar.getInstance().getTime());
		String timestamp = date + "T" + hour + "+0100";
		System.out.println(timestamp);
		return putMeasurement(server, usr, pwd, indicatorGroup, indicator,
				measurement, timestamp);

	}
}
