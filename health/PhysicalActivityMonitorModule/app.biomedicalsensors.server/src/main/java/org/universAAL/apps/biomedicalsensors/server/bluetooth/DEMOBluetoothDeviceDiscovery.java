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
package org.universAAL.apps.biomedicalsensors.server.bluetooth;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

import javax.bluetooth.DeviceClass;
import javax.bluetooth.DiscoveryAgent;
import javax.bluetooth.RemoteDevice;
import javax.bluetooth.ServiceRecord;
import javax.bluetooth.UUID;
import javax.microedition.io.StreamConnection;

/**
 * 
 * Class that discovers all bluetooth devices in the neighbourhood and displays
 * their name and bluetooth address.
 */
public class DEMOBluetoothDeviceDiscovery {

	// used to check whether bluetooth is open
	protected static boolean bluetoothOpen;

	// Bluetooth stream
	StreamConnection stream = null;
	// Input stream for reading bytes via bluetooth connection
	InputStream datin = null;
	// Output stream for writing bytes via bluetooth connection
	static OutputStream out = null;
	public static// holding the url for the bluetooth connection
	String url;
	static// holding the bytes of the requested bluetooth message
	byte message[];
	// holding the heart rate
	public double heartRate;
	// holding the breathing rate
	public double breathingRate;
	// holding the activity level-vmu
	public int activityLevel;
	// holding the skin temperature
	public int skinTemperature;
	// holding the posture
	public int posture;
	// holding the ROG status
	public String formattedTimeNow;
	// time of fetched measurement
	public int statusROG;
	// holding the CRC value
	byte crc;

	// holding the bioharness device name (serial) as read from memory
	public String bhDeviceName;

	// holding the use case, 0 for posture or 1 for heart rate
	int usecase;

	// vector containing the devices discovered
	private static Vector deviceList = new Vector();

	static DEMOBluetoothDeviceDiscovery bsInstance;
	/*-
	 * 
	 *  ---- Debug attributes ----
	 */

	static final boolean DEBUG = false;

	static final String nearestHospitals = "The nearest hospitals are ";

	/*-
	 * 
	 *  ---- Echo loop attributes ----
	 */

	// variable checking whether to send the alert to the medical center or not
	boolean sendAlertToMedicalCenter;
	// enable/disable worn alert
	boolean disableWornAlert;
	// parameters for formatting the current time of the sensor measurements
	Calendar currentDate;
	int year;
	int month;
	int day;
	int hour;
	int minute;
	int sec;

	Date timeNow;
	long currentTime;
	long currentTimeTemp;

	// holding the mess bytes of the general data packet
	Vector messVector;
	Vector messVectorTemp;

	// holding timestamps
	Vector timeVector;
	Vector timeVectorTemp;

	double heartRateLow;
	double heartRateHigh;

	double breathingRateLow;
	double breathingRateHigh;

	double activityLevelLow;
	double activityLevelHigh;

	double skinTemperatureLow;
	double skinTemperatureHigh;

	// currently not used
	double systolicBPLow;
	double systolicBPHigh;

	// currently not used
	double diastolicBPLow;
	double diastolicBPHigh;

	// currently not used
	double BMILow;
	double BMIHigh;

	double postureLow;
	double postureHigh;

	/*-
	 * 
	 *  ---- Bluetooth attributes ----
	 */
	protected UUID uuid = new UUID(0x1101); // serial port profile

	protected int inquiryMode = DiscoveryAgent.GIAC; // no pairing is needed

	protected int connectionOptions = ServiceRecord.NOAUTHENTICATE_NOENCRYPT;

	// methods of DiscoveryListener

	/**
	 * Method for enabling Bluetooth connections.
	 * 
	 * @param devicename
	 *            Connected device's URL
	 * @throws IOException
	 */
	public void startBT(String devicename) throws IOException {
		bluetoothOpen = true;
		DEMOBluetoothDeviceDiscovery.url = "DEMO_BT_SERVICE_URL";// reset bt url
		/*
		 * DEMOBluetoothDeviceDiscovery bluetoothDeviceDiscovery = new
		 * DEMOBluetoothDeviceDiscovery();
		 * bluetoothDeviceDiscovery.bhDeviceName=devicename;
		 */
		/*
		 * LocalDevice localDevice = LocalDevice.getLocalDevice();
		 * System.out.println("Address: " + localDevice.getBluetoothAddress());
		 * System.out.println("Name: " + localDevice.getFriendlyName());
		 * 
		 * 
		 * // find devices DiscoveryAgent agent =
		 * localDevice.getDiscoveryAgent();
		 * 
		 * System.out.println("Starting device inquiry...");
		 * agent.startInquiry(DiscoveryAgent.GIAC, bluetoothDeviceDiscovery);
		 */

	}

	/**
	 * This call back method will be called for each discovered bluetooth
	 * devices.
	 */
	public void getdatafrom() {
		// handleConnection(url);
	}

	// no need to implement this method since services are not being discovered
	public void servicesDiscovered(int transID, ServiceRecord[] records) {
		// log("Service discovered.");
		System.out.println("url : " + url);

	}

	// no need to implement this method since services are not being discovered
	public void serviceSearchCompleted(int transID, int respCode) {

		// handleConnection(url);
	}

	/**
	 * This callback method will be called when the device discovery is
	 * completed.
	 */
	public void inquiryCompleted(int discType) {

	}// end method

	public boolean handleConnection(final String url) {

		if (url == null) {

			System.out
					.println("No Bluetooth URL defined please get sersor INFO again!");
			return false;
		}

		System.out.println("Try to send message on connection URL:" + url);

		// Generated random values for the DEMO
		// activityLevel = 2;
		skinTemperature = 36;
		Format formatter;
		Date date = new Date();
		formatter = new SimpleDateFormat("dd-MM-yyy HH:mm:ss");
		formattedTimeNow = formatter.format(date);
		formatter = new SimpleDateFormat("ss");
		float rand = Integer.valueOf(formatter.format(date));
		posture = 60 + (int) rand;
		activityLevel = 2 + (int) rand;
		heartRate = 100 - rand;
		breathingRate = 23 - rand / 10;
		bluetoothOpen = false;

		return true;
	}

	public void deviceDiscovered(RemoteDevice arg0, DeviceClass arg1) {
		// TODO Auto-generated method stub

	}

}