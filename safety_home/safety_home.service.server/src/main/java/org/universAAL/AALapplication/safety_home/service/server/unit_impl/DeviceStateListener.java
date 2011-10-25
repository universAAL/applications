
package org.universAAL.AALapplication.safety_home.service.server.unit_impl;

/**
 * @author dimokas
 *
 */
public interface DeviceStateListener {
	public void deviceStateChanged(int deviceID, String loc, boolean isOn);
}
