
package org.universAAL.AALapplication.safety_home.service.server.unit_impl;

import java.util.ArrayList;
import java.util.Iterator;
import org.universAAL.middleware.util.LogUtils;
import org.universAAL.AALapplication.safety_home.service.server.Activator;


/**
 * @author dimokas
 *
 * A simple model that is able to manage virtual devices
 *
 */
public class MyDevices {
	private class Device {
		String loc;
		boolean isOn;

		Device(String loc, boolean isOn) {
			this.loc = loc;
			this.isOn = isOn;
		}
	}
	
	private Device[] myDeviceDB = new Device[] {
			new Device("loc1", false)
	};
	
	private ArrayList listeners = new ArrayList();
	
	public MyDevices() {}
	
	public void addListener(DeviceStateListener l) {
		listeners.add(l);
	}
	
	public int[] getDeviceIDs() {
		int[] ids = new int[myDeviceDB.length];
		for (int i=0; i<myDeviceDB.length; i++)
			ids[i] = i;
		return ids;
	}
	
	public String getDeviceLocation(int deviceID) {
		return myDeviceDB[deviceID].loc;
	}
	
	public boolean isOn(int deviceID) {
		return myDeviceDB[deviceID].isOn;
	}
	
	public void removeListener(DeviceStateListener l) {
		listeners.remove(l);
	}
	
	public void lock(int deviceID) {
		if (myDeviceDB[deviceID].isOn) {
			myDeviceDB[deviceID].isOn = false;
			LogUtils.logInfo(Activator.logger, "MyDevices", "lock", 
					new Object[] {"Device in ", myDeviceDB[deviceID].loc, " locked!"}, null);
			for (Iterator i=listeners.iterator(); i.hasNext();)
				((DeviceStateListener) i.next()).deviceStateChanged(deviceID, myDeviceDB[deviceID].loc, false);
		}
	}
	
	public void unlock(int deviceID) {
		if (!myDeviceDB[deviceID].isOn) {
			myDeviceDB[deviceID].isOn = true;
			LogUtils.logInfo(Activator.logger, "MyDevices", "unlock", 
					new Object[] {"Device in ", myDeviceDB[deviceID].loc, " unlocked!"}, null);
			for (Iterator i=listeners.iterator(); i.hasNext();)
				((DeviceStateListener) i.next()).deviceStateChanged(deviceID, myDeviceDB[deviceID].loc, true);			
		}
	}
}
