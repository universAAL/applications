/*
	Copyright 2012 CERTH, http://www.certh.gr
	
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
 */
package org.universAAL.AALapplication.biomedicalsensors.server;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.universAAL.AALapplication.biomedicalsensors.server.bluetooth.DEMOBluetoothDeviceDiscovery;
import org.universAAL.AALapplication.biomedicalsensors.server.unit_impl.BiomedicalSensorStateListener;
import org.universAAL.AALapplication.biomedicalsensors.server.unit_impl.MyBiomedicalSensors;
import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.context.ContextEvent;
import org.universAAL.middleware.context.ContextEventPattern;
import org.universAAL.middleware.context.ContextPublisher;
import org.universAAL.middleware.context.DefaultContextPublisher;
import org.universAAL.middleware.context.owl.ContextProvider;
import org.universAAL.middleware.context.owl.ContextProviderType;
import org.universAAL.middleware.owl.Intersection;
import org.universAAL.middleware.owl.MergedRestriction;
import org.universAAL.middleware.owl.TypeURI;
import org.universAAL.middleware.service.CallStatus;
import org.universAAL.middleware.service.ServiceCall;
import org.universAAL.middleware.service.ServiceCallee;
import org.universAAL.middleware.service.ServiceResponse;
import org.universAAL.middleware.service.owls.process.ProcessOutput;
import org.universAAL.ontology.biomedicalsensors.BiomedicalSensorsOntology;
import org.universAAL.ontology.biomedicalsensors.CompositeBiomedicalSensor;
import org.universAAL.ontology.biomedicalsensors.MeasuredEntity;
import org.universAAL.ontology.biomedicalsensors.SensorType;
import org.universAAL.ontology.biomedicalsensors.Zephyr;

/**
 * @author joemoul,billyk
 * 
 */
public class BiomedicalSensorsCallee extends ServiceCallee implements
		BiomedicalSensorStateListener {
	static boolean ruleFired = false;
	static String ruleID;
	static String ruleInfoType;
	static String ruleTitle;
	static String ruleDesc;
	static String ruleIntensity;

	// Thread Specific vars
	Thread echo;
	CallStatus cs;

	private static Boolean cronJob = true;

	private static final ServiceResponse invalidInput = new ServiceResponse(
			CallStatus.serviceSpecificFailure);
	static {
		invalidInput.addOutput(new ProcessOutput(
				ServiceResponse.PROP_SERVICE_SPECIFIC_ERROR, "Invalid input!"));
	}

	static final String SENSOR_URI_PREFIX = BiomedicalSensorsServiceProfiles.BMS_SERVER_NAMESPACE
			+ "controlledSensor";
	static final String LOCATION_URI_PREFIX = "urn:aal_space:myHome#";

	private static String constructSensorURIfromLocalID(int localID,
			SensorType stype) {
		return stype.getClassURI() + "-" + localID;
	}

	private static String constructLocationURIfromLocalID(String localID) {
		return LOCATION_URI_PREFIX + localID;
	}

	// Get the local ID
	private static int extractLocalIDfromSensorURI(String bsURI) {
		return Integer.parseInt(bsURI.substring(bsURI.lastIndexOf("-") + 1));
	}

	// Get all the registered sensors
	private static CompositeBiomedicalSensor[] getAllControlledSensors(
			MyBiomedicalSensors theServer) {
		int[] sensors = theServer.getBioSensorIDs();
		CompositeBiomedicalSensor[] result = new CompositeBiomedicalSensor[sensors.length];
		for (int i = 0; i < sensors.length; i++)
			result[i] = new CompositeBiomedicalSensor(
			// first param: instance URI
					constructSensorURIfromLocalID(sensors[i],
							theServer.getBioSensorType(i)),
					// second param:connection type
					theServer.getBioSensorConnectionType(i),
					// third param: Sensor Type
					theServer.getBioSensorType(i),
					// 4rth param: bluetooth service url
					"",
					// 5th param: is connected
					true,
					// 6th get the last mesurements
					theServer.getLastMeasurements(i));
		return result;
	}

	/**
	 * Method to construct the ontological declaration of context events
	 * published by BiomedicalSensorsCallee.
	 */
	private static ContextEventPattern[] providedEvents() {

		MergedRestriction predicateRestriction = MergedRestriction
				.getFixedValueRestriction(ContextEvent.PROP_RDF_PREDICATE,
						CompositeBiomedicalSensor.PROP_LAST_MEASUREMENTS);

		MergedRestriction objectRestriction = MergedRestriction
				.getAllValuesRestrictionWithCardinality(
						ContextEvent.PROP_RDF_OBJECT, MeasuredEntity.MY_URI, 1,
						100);

		Intersection xsection = new Intersection();
		xsection.addType(new TypeURI(CompositeBiomedicalSensor.MY_URI, false));
		xsection.addType(MergedRestriction.getFixedValueRestriction(
				CompositeBiomedicalSensor.PROP_SENSOR_TYPE, Zephyr.zephyr));
		MergedRestriction subjectRestriction = MergedRestriction
				.getAllValuesRestrictionWithCardinality(
						ContextEvent.PROP_RDF_SUBJECT, xsection, 1, 1);

		// creating a ContextEventPattern and adding the above restrictions to
		// it
		ContextEventPattern cep2 = new ContextEventPattern();
		cep2.addRestriction(subjectRestriction);
		cep2.addRestriction(predicateRestriction);
		cep2.addRestriction(objectRestriction);

		// return all the descriptions of the context providers events
		return new ContextEventPattern[] { cep2 };
	}

	private static ContextEventPattern[] droolsProvidedEvents() {

		MergedRestriction subjectRestriction = MergedRestriction
				.getAllValuesRestrictionWithCardinality(
						ContextEvent.PROP_RDF_SUBJECT, new TypeURI(
								CompositeBiomedicalSensor.MY_URI, false), 1, 1);

		MergedRestriction objectRestriction = MergedRestriction
				.getAllValuesRestrictionWithCardinality(
						ContextEvent.PROP_RDF_OBJECT, MeasuredEntity.MY_URI, 1,
						1);

		MergedRestriction predicateRestriction = MergedRestriction
				.getFixedValueRestriction(ContextEvent.PROP_RDF_PREDICATE,
						CompositeBiomedicalSensor.PROP_LAST_MEASUREMENTS);

		// creating a ContextEventPattern and adding the above restrictions to
		// it
		ContextEventPattern cep2 = new ContextEventPattern();
		cep2.addRestriction(subjectRestriction);
		cep2.addRestriction(predicateRestriction);
		cep2.addRestriction(objectRestriction);

		// return all the descriptions of the context providers events
		return new ContextEventPattern[] { cep2 };
	}

	// the server being wrapped and bound to universAAL
	private MyBiomedicalSensors theServer;

	// needed for publishing context events
	private ContextPublisher cp;
	private ContextPublisher droolscp;

	BiomedicalSensorsCallee(ModuleContext context) {

		// provided services to the universAAL-based AAL Space
		super(context, BiomedicalSensorsServiceProfiles.profiles);

		theServer = new MyBiomedicalSensors();

		// preparation for publishing context events
		ContextProvider info = new ContextProvider(
				BiomedicalSensorsServiceProfiles.BMS_SERVER_NAMESPACE
						+ "BiomedicalSensorsContextProvider");
		info.setType(ContextProviderType.controller);
		info.setProvidedEvents(providedEvents());
		cp = new DefaultContextPublisher(context, info);

		// create context event that publishes measurements when they are
		// received to be used by drools reasoner and others
		// preparation for publishing context events
		ContextProvider droolsInfo = new ContextProvider(
				BiomedicalSensorsOntology.NAMESPACE + "droolsContextProvider");
		droolsInfo.setType(ContextProviderType.controller);
		droolsInfo.setProvidedEvents(droolsProvidedEvents());
		droolscp = new DefaultContextPublisher(context, droolsInfo);
		// listen to the changes on the server side
		theServer.addListener(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.universAAL.middleware.service.ServiceCallee#communicationChannelBroken
	 * ()
	 */
	public void communicationChannelBroken() {
	}

	// Service response including all available biomedical sensors
	private ServiceResponse getControlledBioSensors() {

		ServiceResponse sr = new ServiceResponse(CallStatus.succeeded);

		// list with all the available sensors
		int[] bs = theServer.getBioSensorIDs();
		ArrayList al = new ArrayList(bs.length);
		for (int i = 0; i < bs.length; i++) {
			CompositeBiomedicalSensor cbs = new CompositeBiomedicalSensor(
			// first param: instance URI
					constructSensorURIfromLocalID(bs[i],
							theServer.getBioSensorType(i)),
					// second param:connection type
					theServer.getBioSensorConnectionType(i),
					// third param: Sensor Type
					theServer.getBioSensorType(i),
					// 4rth param: bluetooth service url
					"",
					// 5th param: is connected
					true,
					// 6th param: get the last measurements
					theServer.getLastMeasurements(i));
			al.add(cbs);
		}
		// ProcessOutput-Event that binds the output URI to the created list of
		// sensors
		sr.addOutput(new ProcessOutput(
				BiomedicalSensorsServiceProfiles.OUTPUT_CONTROLLED_SENSORS, al));
		return sr;
	}

	// Service response with info about the selected sensor
	private ServiceResponse getSensorInfo(String bsURI) {
		try {
			// collect the needed data
			int bsID = extractLocalIDfromSensorURI(bsURI);
			// System.out.println("local ID: " + bsID);
			SensorType sensorType = theServer.getBioSensorType(bsID);

			// bluetooth service URL
			String btURL = theServer.getserviceURL(bsID);
			if (btURL.equals("")) {
				DEMOBluetoothDeviceDiscovery zeph = new DEMOBluetoothDeviceDiscovery();

				System.out.println("FETCHING URL for: "
						+ theServer.getBioSensorName(bsID));
				zeph.startBT(theServer.getBioSensorName(bsID));
				// fetch service from bluetooth
				if (DEMOBluetoothDeviceDiscovery.url != null) {
					System.out.println("URL FOUND: "
							+ DEMOBluetoothDeviceDiscovery.url);
					btURL = DEMOBluetoothDeviceDiscovery.url;

					// set bluetooth service URL
					theServer.setserviceURL(btURL, bsID);
				} else {
					ServiceResponse sr = new ServiceResponse(
							CallStatus.serviceSpecificFailure);
					sr.addOutput(new ProcessOutput(
							ServiceResponse.PROP_SERVICE_SPECIFIC_ERROR,
							"Requested bluetooth device not found!"));
					return sr;
				}

			}

			ServiceResponse sr = new ServiceResponse(CallStatus.succeeded);
			// ProcessOutput-Event that binds the info about sensors
			sr.addOutput(new ProcessOutput(
					BiomedicalSensorsServiceProfiles.OUTPUT_SENSOR_INFO,
					sensorType));
			// ProcessOutput-Event that binds the discovered service
			sr.addOutput(new ProcessOutput(
					BiomedicalSensorsServiceProfiles.OUTPUT_DISCOVERED_SERVICE,
					btURL));
			return sr;

		} catch (Exception e) {
			return invalidInput;
		}
	}

	private ServiceResponse startWaitForAlerts(final String bsURI) {
		try {
			// collect the needed data
			ServiceResponse sr = null;
			ServiceResponse infoSR = new ServiceResponse();
			final int bsID = extractLocalIDfromSensorURI(bsURI);
			String btURL = theServer.getserviceURL(bsID);
			if (btURL.equals("")) {
				infoSR = getSensorInfo(bsURI);

				if (infoSR.getCallStatus() != CallStatus.succeeded) {
					sr = new ServiceResponse(CallStatus.serviceSpecificFailure);
					return sr;
				}
			}

			cronJob = true; // corresponding to START Monitoring
			Thread echo = new Thread() {
				public void run() {
					int j = 0;
					// boolean postureAlert = false;

					// Arraylist to store last measurements in order to handle
					// rules requiring more than just the last set.
					ArrayList<MeasuredEntity[]> last10 = new ArrayList<MeasuredEntity[]>();
					while (cronJob) {
						j++;
						// getting current measurement set from sensor and put
						// them in the SERVER
						ServiceResponse getMeasSR = getMeasurements(bsURI);
						try {
							// wait
							Thread.sleep(4000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}

						if (getMeasSR.getCallStatus() == CallStatus.succeeded) {
							// if succeed read measurement set from SERVER
							MeasuredEntity[] me = theServer
									.getLastMeasurements(bsID);

							CompositeBiomedicalSensor droolscbs = new CompositeBiomedicalSensor(
									bsURI,
									theServer.getBioSensorConnectionType(bsID),
									theServer.getBioSensorType(bsID),
									theServer.getserviceURL(bsID), true,
									theServer.getLastMeasurements(bsID));
							droolscp.publish(new ContextEvent(
									droolscbs,
									CompositeBiomedicalSensor.PROP_LAST_MEASUREMENTS));
							// System.out.println("SEND DROOLS CP");

							// add this set to the Arraylist
							last10.add(me);

							/*
							 * for (int i = 0; i < me.length; i++) {
							 * System.out.println("SERVER MEASUREMENT: " +
							 * me[i].getMeasurementName() + ":" +
							 * me[i].getMeasurementValue() +
							 * me[i].getMeasurementUnit()); }
							 */

							// RULES to fire alert
							if (ruleFired) {
								ruleFired = false; // wait for the next context
													// event
								Format formatter;
								Date date = new Date();
								formatter = new SimpleDateFormat(
										"dd-MM-yyy HH:mm:ss");
								String formattedTimeNow = formatter
										.format(date);

								// Flag posture alert as true
								// postureAlert = true;

								// meanhistory+2 is the max number of
								// measurements to send upon alert
								int measHistory = 13; // 4
								// Changing last measurement to include at most
								// meanhistory+2 last measurement sets.
								theServer.setLastMeasurements(
										last10.get(Math.max(0, last10.size()
												- measHistory)), bsID);

								if (last10.size() > 1)
									for (int k = Math.max(1, last10.size()
											- measHistory + 1); k < last10
											.size(); k++) {
										for (int l = 0; l < 5; l++) {
											theServer.addLastMeasurement(
													last10.get(k)[l], bsID);
										}
									}

								MeasuredEntity alertType = new MeasuredEntity(
										MeasuredEntity.MY_URI, ruleTitle,
										"true", "", "", formattedTimeNow, "",
										"");
								theServer.addLastMeasurement(alertType, bsID);

								MeasuredEntity alertIntensity = new MeasuredEntity(
										MeasuredEntity.MY_URI, "ALERT",
										ruleIntensity, "", "",
										formattedTimeNow, "", "");
								theServer.addLastMeasurement(alertIntensity,
										bsID);

								MeasuredEntity alertID = new MeasuredEntity(
										MeasuredEntity.MY_URI, "DESC",
										ruleDesc, "", "", formattedTimeNow, "",
										"");
								theServer.addLastMeasurement(alertID, bsID);

								CompositeBiomedicalSensor cbs = new CompositeBiomedicalSensor(
										bsURI,
										theServer
												.getBioSensorConnectionType(bsID),
										theServer.getBioSensorType(bsID),
										theServer.getserviceURL(bsID), true,
										theServer.getLastMeasurements(bsID));
								if (cronJob)
									cp.publish(new ContextEvent(
											cbs,
											CompositeBiomedicalSensor.PROP_LAST_MEASUREMENTS));

							}

							cs = CallStatus.succeeded;

							/*
							 * System.out.println("WAITING FOR ALERTS " + j +
							 * " for local ID: " + bsID);
							 */
						} else {
							cs = CallStatus.serviceSpecificFailure;
							System.out
									.println("FAILED TO START WAITING FOR ALERTS for local ID: "
											+ bsID);
						}
					}
				}
			};
			echo.start();

			sr = new ServiceResponse(cs);

			return sr;
		} catch (Exception e) {
			return invalidInput;
		}
	}

	private ServiceResponse stopWaitForAlerts(String bsURI) {
		try {
			// collect the needed data
			int bsID = extractLocalIDfromSensorURI(bsURI);
			cronJob = false;
			System.out.println("STOPPED WAITING FOR ALERTS FOR LOCAL ID: "
					+ bsID);

			ServiceResponse sr = new ServiceResponse(CallStatus.succeeded);

			return sr;

		} catch (Exception e) {
			return invalidInput;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.universAAL.middleware.service.ServiceCallee#handleCall(org.universAAL
	 * .middleware.service.ServiceCall)
	 * 
	 * Since this class is a child of ServiceCallee it is registered to the
	 * service-bus Every service call that passes the restrictions will take
	 * affect here Given by the URI of the request we know what specific
	 * function we have to call
	 */
	public ServiceResponse handleCall(ServiceCall call) {
		if (call == null)
			return null;

		String operation = call.getProcessURI();
		if (operation == null)
			return null;

		if (operation
				.startsWith(BiomedicalSensorsServiceProfiles.SERVICE_GET_CONTROLLED_SENSORS))
			return getControlledBioSensors();

		Object input = call
				.getInputValue(BiomedicalSensorsServiceProfiles.INPUT_SENSOR_URI);
		if (input == null)
			return null;

		if (operation
				.startsWith(BiomedicalSensorsServiceProfiles.SERVICE_GET_SENSOR_INFO))
			return getSensorInfo(input.toString());

		if (operation
				.startsWith(BiomedicalSensorsServiceProfiles.SERVICE_GET_MEASUREMENTS))
			return getMeasurements(input.toString());
		if (operation
				.startsWith(BiomedicalSensorsServiceProfiles.SERVICE_START_WAIT_FOR_ALERTS))
			return startWaitForAlerts(input.toString());
		if (operation
				.startsWith(BiomedicalSensorsServiceProfiles.SERVICE_STOP_WAIT_FOR_ALERTS))
			return stopWaitForAlerts(input.toString());

		return null;
	}

	// Publishing events every time the state of a biomedical sensor is changed
	public void BioSensorStateChanged(int lampID, boolean isOn) {

	}

	// ServiceResponse method for getting the measurements from bluetooth and
	// then assign them to the SERVER with setLastMeasurements
	private ServiceResponse getMeasurements(String bsURI) {
		int bsID = extractLocalIDfromSensorURI(bsURI);
		// System.out.println("LOCAL ID: " + bsID);
		SensorType sensorType = theServer.getBioSensorType(bsID);
		if (sensorType.equals(Zephyr.zephyr)) {
			try {
				// fetch measurements for Bluetooth URL

				DEMOBluetoothDeviceDiscovery zeph = new DEMOBluetoothDeviceDiscovery();
				if (!theServer
						.getserviceURL(extractLocalIDfromSensorURI(bsURI))
						.equals("")) {

					boolean gotres = zeph.handleConnection(theServer
							.getserviceURL(extractLocalIDfromSensorURI(bsURI)));
					if (gotres) {
						ArrayList<MeasuredEntity> al = new ArrayList(2);
						/*
						 * System.out.println("Activity level: " +
						 * zeph.activityLevel);
						 * System.out.println("Heart Rate: " + zeph.heartRate);
						 * System.out.println("Posture: " + zeph.posture);
						 * System.out.println("Breathing Rate: " +
						 * zeph.breathingRate);
						 * System.out.println("Date - Time: " +
						 * zeph.formattedTimeNow);
						 */
						ServiceResponse sr = new ServiceResponse(
								CallStatus.succeeded);

						MeasuredEntity me = new MeasuredEntity(
								MeasuredEntity.MY_URI, "Heart Rate",
								String.valueOf(zeph.heartRate), "+/-1",
								"pulse/min", zeph.formattedTimeNow,
								"LP72677-5",
								"http://purl.bioontology.org/ontology/LOINC");
						MeasuredEntity me1 = new MeasuredEntity(
								MeasuredEntity.MY_URI, "Activity Level",
								String.valueOf(zeph.activityLevel), "N/A", "",
								zeph.formattedTimeNow, "LP115576-3",
								"http://purl.bioontology.org/ontology/LOINC");
						MeasuredEntity me2 = new MeasuredEntity(
								MeasuredEntity.MY_URI, "Breathing Rate",
								String.valueOf(zeph.breathingRate), "N/A",
								"min^-1", zeph.formattedTimeNow, "9279-1",
								"http://purl.bioontology.org/ontology/LOINC");
						MeasuredEntity me3 = new MeasuredEntity(
								MeasuredEntity.MY_URI, "Posture",
								String.valueOf(zeph.posture), "+/-1",
								"degrees", zeph.formattedTimeNow, "LP120681-4",
								"http://purl.bioontology.org/ontology/LOINC");
						MeasuredEntity me4 = new MeasuredEntity(
								MeasuredEntity.MY_URI, "Skin Temp",
								String.valueOf(zeph.skinTemperature), "+/-1",
								"degrees C", zeph.formattedTimeNow, "39106-0",
								"http://purl.bioontology.org/ontology/LOINC");

						al.add(me);
						al.add(me1);
						al.add(me2);
						al.add(me3);
						al.add(me4);

						sr.addOutput(new ProcessOutput(
								BiomedicalSensorsServiceProfiles.OUTPUT_SENSOR_MEASUREMENTS,
								al));
						theServer.setLastMeasurements(new MeasuredEntity[] {
								me, me1, me2, me3, me4 }, bsID);
						return sr;
					} else {
						ServiceResponse sr = new ServiceResponse(
								CallStatus.serviceSpecificFailure);
						sr.addOutput(new ProcessOutput(
								ServiceResponse.PROP_SERVICE_SPECIFIC_ERROR,
								"No measurements fetched. Check connection quality."));
						System.out
								.println("No measurements fetched.Got response "
										+ gotres
										+ ". Check connection quality.");
						return sr;
					}
				} else {
					ServiceResponse sr = new ServiceResponse(
							CallStatus.serviceSpecificFailure);
					sr.addOutput(new ProcessOutput(
							ServiceResponse.PROP_SERVICE_SPECIFIC_ERROR,
							"Try get info (of the selected sensor) first."));
					return sr;
				}
			} catch (Exception e) {
				return invalidInput;
			}
		} else {
			ServiceResponse sr = new ServiceResponse(
					CallStatus.serviceSpecificFailure);
			sr.addOutput(new ProcessOutput(
					ServiceResponse.PROP_SERVICE_SPECIFIC_ERROR,
					"Requested service is only for BioHarness Zephyr sensors"));
			return sr;
		}
	}

}
