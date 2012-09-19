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
package org.universAAL.apps.biomedicalsensors.server.unit_impl;

import java.util.ArrayList;

import org.universAAL.ontology.biomedicalsensors.BPmonitor;
import org.universAAL.ontology.biomedicalsensors.ConnectionsList;
import org.universAAL.ontology.biomedicalsensors.ConnectionType;
import org.universAAL.ontology.biomedicalsensors.MeasuredEntity;
import org.universAAL.ontology.biomedicalsensors.Scale;
import org.universAAL.ontology.biomedicalsensors.SensorType;
import org.universAAL.ontology.biomedicalsensors.Zephyr;

/**
 * @author billyk, joemoul
 *  
 * 
 */
public class MyBiomedicalSensors {
	private class BioSensor {
		ConnectionType connectionType;
		SensorType sensorType;
		String serviceURL;
		String sensorName;
		boolean isConnected;
		MeasuredEntity me[];

		BioSensor(ConnectionType connectionType, SensorType sensorType,
				String sensorName, boolean isConnected) {
			this.connectionType = connectionType;
			this.sensorType = sensorType;
			this.isConnected = isConnected;
			this.sensorName = sensorName;
			this.serviceURL = "";
			this.me = new MeasuredEntity[] {};
		}
	}

	//Array of the "available" sensors
	private BioSensor[] myBioSensorDB = new BioSensor[] {
			new BioSensor(ConnectionsList.bt, BPmonitor.brand1_bp_monitor, "",
					true),
			new BioSensor(ConnectionsList.bt, Zephyr.zephyr, "BH BHT001777",
					true),
			new BioSensor(ConnectionsList.bt, Zephyr.zephyr, "BH ZBH001892",
					true),
			new BioSensor(ConnectionsList.wifi, Scale.scale, "", true) };

	private ArrayList listeners = new ArrayList();

	public MyBiomedicalSensors() {
	}

	public void addListener(BiomedicalSensorStateListener l) {
		listeners.add(l);
	}

	public int[] getBioSensorIDs() {
		int[] ids = new int[myBioSensorDB.length];
		for (int i = 0; i < myBioSensorDB.length; i++)
			ids[i] = i;
		return ids;
	}

	public SensorType getBioSensorType(int id) {

		return myBioSensorDB[id].sensorType;

	}

	public String getBioSensorName(int id) {

		return myBioSensorDB[id].sensorName;

	}

	public ConnectionType getBioSensorConnectionType(int id) {

		return myBioSensorDB[id].connectionType;

	}

	public String getserviceURL(int id) {

		return myBioSensorDB[id].serviceURL;

	}

	public void setserviceURL(String url, int id) {

		myBioSensorDB[id].serviceURL = url;

	}

	public MeasuredEntity[] getLastMeasurements(int id) {

		return myBioSensorDB[id].me;

	}

	public void setLastMeasurements(MeasuredEntity[] measurement, int id) {

		myBioSensorDB[id].me = measurement;

	}

	// Method for adding the last measurements from the sensor
	public void addLastMeasurement(MeasuredEntity measurement, int id) {

		myBioSensorDB[id].me = measuredEntityPush(myBioSensorDB[id].me,
				measurement);

	}

	// Method for adding measured entities at the end of the array
	private static MeasuredEntity[] measuredEntityPush(MeasuredEntity[] orig,
			MeasuredEntity newItem) {
		MeasuredEntity[] longer = new MeasuredEntity[orig.length + 1];
		System.arraycopy(orig, 0, longer, 0, orig.length);
		longer[orig.length] = newItem;
		return longer;
	}

	public void removeListener(BiomedicalSensorStateListener l) {
		listeners.remove(l);
	}

}
