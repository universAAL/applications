/*
 Copyright 2008-2014 TSB, http://www.tsbtecnologias.es
 TSB - Tecnolog�as para la Salud y el Bienestar

 See the NOTICE file distributed with this work for additional 
 information regarding copyright ownership

 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

 http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
 */package es.tsb.ltba.nomhad.gateway;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Properties;

import org.apache.http.client.ClientProtocolException;
import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.container.osgi.util.BundleConfigHome;
import org.universAAL.middleware.container.utils.LogUtils;

import es.tsb.ltba.nomhad.httpclient.NomhadHttpClient;

public class NomhadGateway {

	private static NomhadGateway INSTANCE;

	private static final String NOMHAD_URL_HEADER = "https://localhost:8443/nomhad/rest/2/cmr/patient/";
	private static final String OBSERVATIONS_REQUEST = "/observations";
	private static final String DEVICE_ID = "\"35-209900-176148-1\"";
	private ModuleContext moduleContext = null;
	private final String HOME_FOLDER = "nomhad.gateway";
	private final String PROPERTIES_FILE = "nomhad.properties";
	private String home = new BundleConfigHome(HOME_FOLDER).getAbsolutePath();
	private Properties properties = new Properties();
	private String isDeployed;
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
		INSTANCE = this;
	}

	public static NomhadGateway getInstance() {
		if (INSTANCE == null)
			return new NomhadGateway();
		else
			return INSTANCE;
	}

	public void setModuleContext(ModuleContext mc) {
		moduleContext = mc;
	}

	/**
	 * Put a measurement in a specific time in the future or in the past. This
	 * time is indicated in milliseconds.
	 * 
	 * @param server
	 *            IP of the server.
	 * @param usr
	 *            user code.
	 * @param pwd
	 *            user password.
	 * @param indicatorGroup
	 *            indicator group where the measurement will be place.
	 * @param indicator
	 *            indicator corresponding to the measurement.
	 * @param measurement
	 *            measured value.
	 * @param timeInMillis
	 *            Time where the measurement was taken.
	 * @return response from the server.
	 */
	public String putMeasurement(String server, String usr, String pwd,
			String indicatorGroup, String indicator, String measurement,
			long timeInMillis, String deviceId) {

		SimpleDateFormat beforeT = new java.text.SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat afterT = new SimpleDateFormat("hhmmss");
		Date currentDate = new Date(timeInMillis);
		String dateString = beforeT.format(currentDate);
		String hourString = afterT.format(Calendar.getInstance().getTime());
		String timestamp = dateString + "T" + hourString + "+0100";

		return putMeasurement(server, usr, pwd, indicatorGroup, indicator,
				measurement, timestamp);
	}

	/**
	 * Put a measurement in Nomhad Server with a correctly formatted date.
	 * 
	 * @param server
	 *            IP of the server.
	 * @param usr
	 *            user code.
	 * @param pwd
	 *            user password.
	 * @param indicatorGroup
	 *            indicator group where the measurement will be place.
	 * @param indicator
	 *            indicator corresponding to the measurement.
	 * @param measurement
	 *            measured value.
	 * @param formattedate
	 *            Time formated according to the Nomhad requisites.
	 *            YYYYMMDD'T'HHmmSS'+0100'
	 * @return response from the server.
	 */
	public String putMeasurement(String server, String usr, String pwd,
			String indicatorGroup, String indicator, String measurement,
			String formattedate, String deviceId) {

		StringBuilder uri = new StringBuilder();
		String header = new String(NOMHAD_URL_HEADER);
		String body = new String(BODY);

		if (server.contains(":")) {
			header = header.replace("localhost:8443", server);
		} else {
			LogUtils.logDebug(moduleContext, getClass(), "putMeasurement",
					new String[] { "Replacing 'localhost' by " + server }, null);
			header = header = header.replace("localhost", server);
			LogUtils.logDebug(moduleContext, getClass(), "putMeasurement",
					new String[] { "New header: " + header }, null);
		}
		try {
			properties.load(new FileInputStream(home + "/" + PROPERTIES_FILE));
			isDeployed = properties
					.getProperty("es.tsbtecnologias.nomhad.ltba.deployed");
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		if (isDeployed.equalsIgnoreCase("true")) {
			LogUtils.logDebug(
					moduleContext,
					getClass(),
					"putMeasurement",
					new String[] { "Remote server is a deployed server (PRE or PRO but no DEV)" },
					null);
			header = header.replace("nomhad", "ltba");
		}
		uri.append(header);
		uri.append(usr);
		uri.append(OBSERVATIONS_REQUEST);

		body = body.replace("INDICATOR_GROUP", indicatorGroup);
		body = body.replace("INDICATOR_MEASURED", indicator);
		body = body.replace("VALUE_MEASURED", measurement);
		body = body.replace("19520723T120000+0100", formattedate);
		body = body.replace(DEVICE_ID, deviceId);

		NomhadHttpClient nhc = new NomhadHttpClient(usr, pwd);
		try {
			LogUtils.logInfo(
					moduleContext,
					getClass(),
					"putMeasurement",
					new String[] { "------<>-------POSTING TO NOMHAD-------------\nURI: "
							+ uri.toString()
							+ "\nBODY: "
							+ body.toString()
							+ "\nSERVER RESPONSE:"
							+ nhc.post(uri.toString(), body) }, null);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		nhc.stopClient();
		return uri.toString();

	}

	/**
	 * Put measurement with no date. The date taken for the measurement is the
	 * current time.
	 * 
	 * @param server
	 * @param usr
	 * @param pwd
	 * @param indicatorGroup
	 * @param indicator
	 * @param measurement
	 * @return response from the server.
	 */
	public String putMeasurement(String server, String usr, String pwd,
			String indicatorGroup, String indicator, String measurement,
			String deviceId) {

		SimpleDateFormat beforeT = new java.text.SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat afterT = new SimpleDateFormat("HHmmss");
		String date = beforeT.format(Calendar.getInstance().getTime());
		String hour = afterT.format(Calendar.getInstance().getTime());
		String timestamp = date + "T" + hour + "+0100";
		LogUtils.logDebug(moduleContext, getClass(), "putMeasurement",
				new String[] { "Current timestamp: " + timestamp }, null);
		return putMeasurement(server, usr, pwd, indicatorGroup, indicator,
				measurement, timestamp, deviceId);
	}

	/**
	 * Put measurement with a date without format.
	 * 
	 * @param server
	 * @param day
	 * @param month
	 * @param year
	 * @param hour_of_day
	 * @param minutes
	 * @param usr
	 * @param pwd
	 * @param indicatorGroup
	 * @param indicator
	 * @param measurement
	 * @param deviceId
	 * @return
	 */
	public String putMeasurement(String server, int day, int month, int year,
			int hour_of_day, int minutes, String usr, String pwd,
			String indicatorGroup, String indicator, String measurement,
			String deviceId) {

		SimpleDateFormat beforeT = new java.text.SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat afterT = new SimpleDateFormat("HHmmss");
		String date = beforeT.format(Calendar.getInstance().getTime());
		String hour = afterT.format(new GregorianCalendar(year, month, day,
				hour_of_day, minutes).getTime());
		String timestamp = date + "T" + hour + "+0100";
		LogUtils.logDebug(moduleContext, getClass(), "putMeasurement",
				new String[] { "Current timestamp: " + timestamp }, null);
		return putMeasurement(server, usr, pwd, indicatorGroup, indicator,
				measurement, timestamp, deviceId);
	}
}
