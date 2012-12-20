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
package org.universAAL.ontology.biomedicalsensors;

import org.universAAL.ontology.phThing.Device;

/**
 * Ontological representation of a Composite Biomedical Sensor.
 * 
 * @author joemoul, billyk
 * 
 */
public class CompositeBiomedicalSensor extends Device {

	public static final String MY_URI = BiomedicalSensorsOntology.NAMESPACE
			+ "CompositeBiomedicalSensor";
	public static final String PROP_CONNECTION_TYPE = BiomedicalSensorsOntology.NAMESPACE
			+ "connectionType";
	public static final String PROP_IS_CONNECTED = BiomedicalSensorsOntology.NAMESPACE
			+ "isConnected";
	public static final String PROP_SENSOR_TYPE = BiomedicalSensorsOntology.NAMESPACE
			+ "sensorType";
	public static final String PROP_DISCOVERED_BT_SERVICE = BiomedicalSensorsOntology.NAMESPACE
			+ "serviceURL";
	public static final String PROP_SENSOR_NAME = BiomedicalSensorsOntology.NAMESPACE
			+ "sensorName";
	public static final String PROP_LAST_MEASUREMENTS = BiomedicalSensorsOntology.NAMESPACE
			+ "lastMeasurement";

	public CompositeBiomedicalSensor() {
		super();
	}

	public CompositeBiomedicalSensor(String uri, ConnectionType connectionType,
			SensorType sensorType, String btServiceURL, Boolean state,
			MeasuredEntity[] lastmeas) {
		super(uri);
		if (connectionType == null)
			throw new IllegalArgumentException();
		props.put(PROP_CONNECTION_TYPE, connectionType);
		props.put(PROP_SENSOR_TYPE, sensorType);
		this.setDiscoveredServiceURL(btServiceURL);
		props.put(PROP_IS_CONNECTED, state);
		this.setLastMeasurement(lastmeas);
	}

	public CompositeBiomedicalSensor(String uri) {
		super(uri);
	}

	public boolean getConnectionStatus() {
		return ((Boolean) props
				.get(CompositeBiomedicalSensor.PROP_IS_CONNECTED))
				.booleanValue();
	}

	public void setConnectionStatus(Boolean state) {
		props.put(CompositeBiomedicalSensor.PROP_IS_CONNECTED, state);
	}

	public SensorType getSensorType() {
		return ((SensorType) props
				.get(CompositeBiomedicalSensor.PROP_SENSOR_TYPE));
	}

	public void setSensorType(SensorType type) {
		props.put(CompositeBiomedicalSensor.PROP_SENSOR_TYPE, type);
	}

	public ConnectionType getConnectionType() {
		// return ((String)
		// props.get(CompositeBiomedicalSensor.PROP_CONNECTION_TYPE));
		return (ConnectionType) props
				.get(CompositeBiomedicalSensor.PROP_CONNECTION_TYPE);
	}

	public void setConnectionType(ConnectionType type) {
		props.put(CompositeBiomedicalSensor.PROP_CONNECTION_TYPE, type);
	}

	public String getDiscoveredServiceURL() {
		return ((String) props
				.get(CompositeBiomedicalSensor.PROP_DISCOVERED_BT_SERVICE));
	}

	public void setDiscoveredServiceURL(String URL) {
		props.put(CompositeBiomedicalSensor.PROP_DISCOVERED_BT_SERVICE, URL);
	}

	public MeasuredEntity[] getLastMeasurement() {
		MeasuredEntity me[] = (MeasuredEntity[]) props
				.get(CompositeBiomedicalSensor.PROP_LAST_MEASUREMENTS);
		return me;
	}

	public MeasuredEntity getLastMeasurementOf(String measName) {
		MeasuredEntity me[] = (MeasuredEntity[]) props
				.get(CompositeBiomedicalSensor.PROP_LAST_MEASUREMENTS);
		for (int i = 0; i < me.length; i++) {
			if (me[i].getMeasurementName().equals(measName)) {
				return me[i];
			}
		}
		return null;
	}

	public void setLastMeasurement(MeasuredEntity[] meas) {

		props.put(CompositeBiomedicalSensor.PROP_LAST_MEASUREMENTS, meas);

	}

	public String getClassURI() {
		return MY_URI;
	}
}
