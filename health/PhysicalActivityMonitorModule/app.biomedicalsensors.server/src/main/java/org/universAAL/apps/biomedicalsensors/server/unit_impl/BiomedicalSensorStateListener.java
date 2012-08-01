package org.universAAL.apps.biomedicalsensors.server.unit_impl;

/**
 * @author joemoul,billyk
 * 
 */
public interface BiomedicalSensorStateListener {
	public void BioSensorStateChanged(int sensorID, boolean isConnected);
}
