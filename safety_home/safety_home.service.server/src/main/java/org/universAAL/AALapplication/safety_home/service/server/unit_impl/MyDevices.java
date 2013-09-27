/*****************************************************************************************
 * Copyright 2012 CERTH, http://www.certh.gr - Center for Research and Technology Hellas
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *****************************************************************************************/

package org.universAAL.AALapplication.safety_home.service.server.unit_impl;

import java.util.ArrayList;
import java.util.Iterator;

import org.universAAL.AALapplication.safety_home.service.remoteDevicesClient.RemoteDevicesClient;
import org.universAAL.AALapplication.safety_home.service.server.Activator;
import org.universAAL.AALapplication.safety_home.service.server.DeviceHandlers.DoorLockUnlock;
import org.universAAL.AALapplication.safety_home.service.server.DeviceHandlers.DoorOpen;
import org.universAAL.middleware.container.utils.LogUtils;

/**
 * @author dimokas
 *
 * A simple model that is able to manage virtual devices
 *
 */
public class MyDevices {
	private class Device {
		String loc;
		boolean isUnLocked;
		boolean isOpen;
		
		Device(String loc, boolean isUnLocked) {
			this.loc = loc;
			this.isUnLocked = isUnLocked;
		}
		Device(String loc, boolean isUnLocked, boolean isOpen) {
			this.loc = loc;
			this.isUnLocked = isUnLocked;
			this.isOpen = isOpen;
		}
	}
	
	private class Lamp {
		String loc;
		boolean isOn;

		Lamp(String loc, boolean isOn) {
		    this.loc = loc;
		    this.isOn = isOn;
		}
	}

	private class Heating {
		String loc;
		boolean isOn;

		Heating(String loc, boolean isOn) {
		    this.loc = loc;
		    this.isOn = isOn;
		}
	}

	private Device[] myDeviceDB = new Device[] {
			new Device("loc1", false, false)
	};
	private Lamp[] myLamp = new Lamp[] { new Lamp("loc1", false)	};
	private Heating[] myHeating = new Heating[] { new Heating("loc1", false)	};

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
	
	public boolean isUnLocked(int deviceID) {
		return myDeviceDB[deviceID].isUnLocked;
	}

	public boolean isOpen(int deviceID) {
		return myDeviceDB[deviceID].isOpen;
	}
	
	public void removeListener(DeviceStateListener l) {
		listeners.remove(l);
	}
	
	/* Virtual Door Management */

	public boolean lock(int deviceID) {
		if (myDeviceDB[deviceID].isUnLocked) {
			myDeviceDB[deviceID].isUnLocked = false;
			//LogUtils.logInfo(Activator.logger, "MyDevices", "lock", 
			//		new Object[] {"Device in ", myDeviceDB[deviceID].loc, " locked!"}, null);
			for (Iterator i=listeners.iterator(); i.hasNext();)
				((DeviceStateListener) i.next()).deviceStateChanged(deviceID, myDeviceDB[deviceID].loc, false);
			System.out.println("##################### The door locked #####################");
			return true;
		}
		//else
			//return false;
		return true;
	}
	
	public boolean unlock(int deviceID) {
		if (!myDeviceDB[deviceID].isUnLocked) {
			myDeviceDB[deviceID].isUnLocked = true;
			//LogUtils.logInfo(Activator.logger, "MyDevices", "unlock", 
			//		new Object[] {"Device in ", myDeviceDB[deviceID].loc, " unlocked!"}, null);
			for (Iterator i=listeners.iterator(); i.hasNext();)
				((DeviceStateListener) i.next()).deviceStateChanged(deviceID, myDeviceDB[deviceID].loc, true);			
			System.out.println("##################### The door unlocked #####################");
			return true;
		}
		//else
			//return false;
		return true;
	}

	public boolean open(int deviceID) {
		if (myDeviceDB[deviceID].isUnLocked) {
			myDeviceDB[deviceID].isOpen = true;
			//LogUtils.logInfo(Activator.logger, "MyDevices", "open", 
			//		new Object[] {"Device in ", myDeviceDB[deviceID].loc, " opened!"}, null);
			for (Iterator i=listeners.iterator(); i.hasNext();)
				((DeviceStateListener) i.next()).deviceStateChanged(deviceID, myDeviceDB[deviceID].loc, true);			
			System.out.println("##################### The door opened #####################");
			return true;
		}
		else
			return false;
	}

	public boolean close(int deviceID) {
		if (myDeviceDB[deviceID].isOpen) {
			myDeviceDB[deviceID].isOpen = false;
			//LogUtils.logInfo(Activator.logger, "MyDevices", "open", 
			//		new Object[] {"Device in ", myDeviceDB[deviceID].loc, " opened!"}, null);
			for (Iterator i=listeners.iterator(); i.hasNext();)
				((DeviceStateListener) i.next()).deviceStateChanged(deviceID, myDeviceDB[deviceID].loc, true);			
			System.out.println("##################### The door closed #####################");
			return true;
		}
		else
			return false;
	}

	/* End of Virtual Door Management */


	/* Living Lab Door Management */

	public boolean unlock() {

		DoorLockUnlock door = new DoorLockUnlock();
		return door.unlock();
	}
	
	public boolean lock() {

		DoorLockUnlock door = new DoorLockUnlock();
		return door.lock();
	}
	
	public boolean open(){
		
		DoorOpen door = new DoorOpen();
		return door.open();
	}

	/* End of Living Lab Door Management */
	
	/* Lamp Management */
	
    public void turnOffLamp(int lampID) {
		//if (SOAPClient.isLampEnabled()) {
		    myLamp[lampID].isOn = false;
		    RemoteDevicesClient.turnOffLamp();
		    LogUtils.logDebug(Activator.mc, MyDevices.class, "Safety and Security", new Object[] { "Lamp turned off!" },null);
		//}
    }

    public void turnOnLamp(int lampID) {
		//if (!SOAPClient.isLampEnabled()) {
		    myLamp[lampID].isOn = true;
		    RemoteDevicesClient.turnOnLamp();
		    LogUtils.logDebug(Activator.mc, MyDevices.class, "Safety and Security", new Object[] { "Lamp turned on!" },null);
		//}
    }

	/* End of Lamp Management */

	/* Heating Management */
	
    public void turnOffHeating(int heatingID) {
		//if (SOAPClient.isHeatingEnabled()) {
		    myHeating[heatingID].isOn = false;
		    RemoteDevicesClient.turnOffHeating();
		    LogUtils.logDebug(Activator.mc, MyDevices.class, "Safety and Security", new Object[] { "Heating turned off!" },null);
		//}
    }

    public void turnOnHeating(int heatingID) {
		//if (!SOAPClient.isHeatingEnabled()) {
    		myHeating[heatingID].isOn = true;
		    RemoteDevicesClient.turnOnHeating();
		    LogUtils.logDebug(Activator.mc, MyDevices.class, "Safety and Security", new Object[] { "Heating turned on!" },null);
		//}
    }

	/* End of Heating Management */

}
