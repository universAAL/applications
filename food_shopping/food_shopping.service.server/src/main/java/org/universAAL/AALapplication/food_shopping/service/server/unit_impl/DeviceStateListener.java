
package org.universAAL.AALapplication.food_shopping.service.server.unit_impl;

/**
 * @author dimokas
 *
 */
public interface DeviceStateListener {
	public void deviceStateChanged(int deviceID, String loc, boolean isOn);
}
