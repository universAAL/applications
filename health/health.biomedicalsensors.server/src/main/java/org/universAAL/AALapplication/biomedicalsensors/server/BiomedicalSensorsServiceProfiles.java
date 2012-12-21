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

import java.util.Hashtable;

import org.universAAL.middleware.owl.MergedRestriction;
import org.universAAL.middleware.owl.OntologyManagement;
import org.universAAL.middleware.owl.SimpleOntology;
import org.universAAL.middleware.rdf.Resource;
import org.universAAL.middleware.rdf.TypeMapper;
import org.universAAL.middleware.rdf.impl.ResourceFactoryImpl;
import org.universAAL.middleware.service.owls.profile.ServiceProfile;
import org.universAAL.ontology.biomedicalsensors.AlertService;
import org.universAAL.ontology.biomedicalsensors.BiomedicalSensorService;
import org.universAAL.ontology.biomedicalsensors.CompositeBiomedicalSensor;

/**
 * @author billyk, joemoul
 * 
 */
public class BiomedicalSensorsServiceProfiles extends BiomedicalSensorService {

	// All the static Strings are used to unique identify special functions and
	// objects
	public static final String BMS_SERVER_NAMESPACE = "http://ontology.universaal.org/BiomedicalSensorsAppServer.owl#";
	public static final String MY_URI = BMS_SERVER_NAMESPACE + "BMSService";

	static final String SERVICE_GET_CONTROLLED_SENSORS = BMS_SERVER_NAMESPACE
			+ "getControlledSensors";
	static final String SERVICE_GET_SENSOR_INFO = BMS_SERVER_NAMESPACE
			+ "getSensorInfo";
	static final String SERVICE_SENSOR_BT_SERVICE_URL = BMS_SERVER_NAMESPACE
			+ "getBTserviceURL";
	static final String SERVICE_GET_MEASUREMENTS = BMS_SERVER_NAMESPACE
			+ "getMeasurements";
	static final String SERVICE_START_WAIT_FOR_ALERTS = BMS_SERVER_NAMESPACE
			+ "startWaitForAlerts";
	static final String SERVICE_STOP_WAIT_FOR_ALERTS = BMS_SERVER_NAMESPACE
			+ "stopWaitForAlerts";
	static final String INPUT_SENSOR_URI = BMS_SERVER_NAMESPACE + "sensorURI";
	static final String OUTPUT_CONTROLLED_SENSORS = BMS_SERVER_NAMESPACE
			+ "controlledSensors";
	static final String OUTPUT_SENSOR_MEASUREMENTS = CompositeBiomedicalSensor.PROP_LAST_MEASUREMENTS;
	static final String OUTPUT_DISCOVERED_SERVICE = CompositeBiomedicalSensor.PROP_DISCOVERED_BT_SERVICE;
	static final String OUTPUT_SENSOR_INFO = CompositeBiomedicalSensor.PROP_SENSOR_TYPE;

	static final ServiceProfile[] profiles = new ServiceProfile[6];
	private static Hashtable serverBiomedicalSensorsRestrictions = new Hashtable();
	static {
		// Registration of all classes in the ontology
		OntologyManagement.getInstance().register(
				new SimpleOntology(MY_URI, BiomedicalSensorService.MY_URI,
						new ResourceFactoryImpl() {
							@Override
							public Resource createInstance(String classURI,
									String instanceURI, int factoryIndex) {
								return new BiomedicalSensorsServiceProfiles(
										instanceURI);
							}
						}));

		// Help structures to define property paths used more than once below
		String[] ppControls = new String[] { BiomedicalSensorService.PROP_CONTROLS };
		String[] ppmonitorsAlerts = new String[] {
				BiomedicalSensorService.PROP_CONTROLS,
				AlertService.PROP_MONITORS };
		String[] ppSensorType = new String[] {
				BiomedicalSensorService.PROP_CONTROLS,
				CompositeBiomedicalSensor.PROP_SENSOR_TYPE };
		String[] ppBTURL = new String[] {
				BiomedicalSensorService.PROP_CONTROLS,
				CompositeBiomedicalSensor.PROP_DISCOVERED_BT_SERVICE };
		String[] ppMeasurements = new String[] {
				BiomedicalSensorService.PROP_CONTROLS,
				CompositeBiomedicalSensor.PROP_LAST_MEASUREMENTS };

		// services that we want to make available.
		// Before adding our own restrictions, we first "inherit" the
		// restrictions defined by the superclass
		addRestriction(
				(MergedRestriction) BiomedicalSensorService
						.getClassRestrictionsOnProperty(
								BiomedicalSensorService.MY_URI,
								BiomedicalSensorService.PROP_CONTROLS).copy(),
				ppControls, serverBiomedicalSensorsRestrictions);

		/*
		 * addRestriction(MergedRestriction.getFixedValueRestriction(
		 * CompositeBiomedicalSensor.PROP_CONNECTION_TYPE, Zephyr.btSensor), new
		 * String[] { BiomedicalSensorService.PROP_CONTROLS,
		 * CompositeBiomedicalSensor.PROP_CONNECTION_TYPE },
		 * serverBiomedicalSensorsRestrictions);
		 */
		/*
		 * addRestriction(MergedRestriction.getFixedValueRestriction(
		 * CompositeBiomedicalSensor.PROP_SENSOR_TYPE,
		 * BPmonitor.OMRON),ppSensorType, serverBiomedicalSensorsRestrictions);
		 */

		/*
		 * create the service description #1 to be registered with the service
		 * bus
		 */
		// Create the service-object for retrieving the controlled sensors
		BiomedicalSensorsServiceProfiles getControlledSensors = new BiomedicalSensorsServiceProfiles(
				SERVICE_GET_CONTROLLED_SENSORS);

		getControlledSensors.addOutput(OUTPUT_CONTROLLED_SENSORS,
				CompositeBiomedicalSensor.MY_URI, 0, 0, ppControls);
		// Adding service profile to be registered with the service bus
		profiles[0] = getControlledSensors.myProfile;

		/*
		 * create the service description #2 to be registered with the service
		 * bus
		 */
		BiomedicalSensorsServiceProfiles getSensorInfo = new BiomedicalSensorsServiceProfiles(
				SERVICE_GET_SENSOR_INFO);
		// Filtering methog in order to get info for the sensor with the
		// specific URI
		getSensorInfo.addFilteringInput(INPUT_SENSOR_URI,
				CompositeBiomedicalSensor.MY_URI, 1, 1, ppControls);
		getSensorInfo.addOutput(OUTPUT_SENSOR_INFO,
				TypeMapper.getDatatypeURI(String.class), 1, 1, ppSensorType);
		getSensorInfo.addOutput(OUTPUT_DISCOVERED_SERVICE,
				TypeMapper.getDatatypeURI(String.class), 1, 1, ppBTURL);
		// Adding service profile to be registered with the service bus
		profiles[1] = getSensorInfo.myProfile;

		/*
		 * create the service description #3 to be registered with the service
		 * bus
		 */
		BiomedicalSensorsServiceProfiles getBTServiceURL = new BiomedicalSensorsServiceProfiles(
				SERVICE_SENSOR_BT_SERVICE_URL);

		getBTServiceURL.addFilteringInput(INPUT_SENSOR_URI,
				CompositeBiomedicalSensor.MY_URI, 1, 1, ppControls);
		getBTServiceURL.myProfile.addChangeEffect(ppBTURL, new Boolean(true));
		getBTServiceURL.addOutput(OUTPUT_DISCOVERED_SERVICE,
				TypeMapper.getDatatypeURI(String.class), 1, 1, ppBTURL);
		// Adding service profile to be registered with the service bus
		profiles[2] = getBTServiceURL.myProfile;

		/*
		 * create the service description #4 to be registered with the service
		 * bus
		 */
		BiomedicalSensorsServiceProfiles getMeasurements = new BiomedicalSensorsServiceProfiles(
				SERVICE_GET_MEASUREMENTS);

		getMeasurements.addFilteringInput(INPUT_SENSOR_URI,
				CompositeBiomedicalSensor.MY_URI, 1, 1, ppControls);
		getMeasurements.myProfile.addChangeEffect(ppMeasurements, new Boolean(
				true));
		getMeasurements.addOutput(OUTPUT_SENSOR_MEASUREMENTS,
				TypeMapper.getDatatypeURI(String.class), 0, 0, ppMeasurements);
		// Adding service profile to be registered with the service bus
		profiles[3] = getMeasurements.myProfile;

		/*
		 * create the service description #5 to be registered with the service
		 * bus
		 */
		BiomedicalSensorsServiceProfiles startWaitForAlerts = new BiomedicalSensorsServiceProfiles(
				SERVICE_START_WAIT_FOR_ALERTS);

		startWaitForAlerts.addFilteringInput(INPUT_SENSOR_URI,
				CompositeBiomedicalSensor.MY_URI, 1, 1, ppmonitorsAlerts);
		startWaitForAlerts.myProfile.addChangeEffect(ppmonitorsAlerts,
				new Boolean(true));
		// Adding service profile to be registered with the service bus
		profiles[4] = startWaitForAlerts.myProfile;

		/*
		 * create the service description #6 to be registered with the service
		 * bus
		 */
		BiomedicalSensorsServiceProfiles stopWaitForAlerts = new BiomedicalSensorsServiceProfiles(
				SERVICE_STOP_WAIT_FOR_ALERTS);

		stopWaitForAlerts.addFilteringInput(INPUT_SENSOR_URI,
				CompositeBiomedicalSensor.MY_URI, 1, 1, ppmonitorsAlerts);
		stopWaitForAlerts.myProfile.addChangeEffect(ppmonitorsAlerts,
				new Boolean(false));
		profiles[5] = stopWaitForAlerts.myProfile;
	}

	private BiomedicalSensorsServiceProfiles(String uri) {
		super(uri);
	}

	public String getClassURI() {
		return MY_URI;
	}
}
