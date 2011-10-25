
package org.universAAL.AALapplication.food_shopping.service.server.unit_impl;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import org.universAAL.middleware.util.LogUtils;
import org.universAAL.ontology.foodDevices.FoodItem;
import org.universAAL.AALapplication.food_shopping.service.server.Activator;


/**
 * @author dimokas
 *
 */
public class MyDevices {
	private class Device {
		String loc;
		boolean isOn;
		int temp;
		Hashtable foodItems = new Hashtable();
		
		public Device(String loc, boolean isOn) {
			this.loc = loc;
			this.isOn = isOn;
		}
		public Device(String loc, boolean isOn, int temp) {
			this.loc = loc;
			this.isOn = isOn;
			this.temp = temp;
		}
		public Device(String loc, boolean isOn, int temp, DeviceFoodItem dfi) {
			this.loc = loc;
			this.isOn = isOn;
			this.temp = temp;
			this.foodItems.put(dfi.getName(), dfi);
		}
	}
	
	private Device[] myDeviceDB = new Device[] {
			new Device("loc1", false, 0)
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
	
	public Hashtable[] getFoodItems() {
		Hashtable[] fooditems = new Hashtable[myDeviceDB.length];
		for (int i=0; i<myDeviceDB.length; i++)
			fooditems[i] = myDeviceDB[i].foodItems;
		return fooditems;
	}
	
	public String getDeviceLocation(int deviceID) {
		return myDeviceDB[deviceID].loc;
	}
	
	public boolean isOn(int deviceID) {
		return myDeviceDB[deviceID].isOn;
	}
	
	public int getDeviceTemperature(int deviceID) {
		return myDeviceDB[deviceID].temp;
	}

	public Hashtable getFoodItems(int deviceID){
		return myDeviceDB[deviceID].foodItems;
	}
	
	public void removeListener(DeviceStateListener l) {
		listeners.remove(l);
	}
	
	public void addFoodItem(int deviceID, FoodItem value) {
		if (myDeviceDB[deviceID].isOn) {
			String name = value.getName();
			int quantity = value.getQuantity();
			DeviceFoodItem dfi = new DeviceFoodItem(name,quantity); 			
			if (myDeviceDB[deviceID].foodItems.containsKey(name)){
				LogUtils.logInfo(Activator.logger, "MyDevices", "updateFoodItem", 
						new Object[] {"Food item " + name + " in ", "Device"+deviceID, " updated!"}, null);
				System.out.println("Server updated food item "+value.getName());
			}
			else{
				LogUtils.logInfo(Activator.logger, "MyDevices", "addFoodItem", 
						new Object[] {"Food item " + name + " in ", "Device"+deviceID, " added!"}, null);
				System.out.println("Server added food item "+value.getName());
			}
			myDeviceDB[deviceID].foodItems.put(name, (DeviceFoodItem)dfi);
		}
	}
	
	public void removeFoodItem(int deviceID, String key) {
		if (myDeviceDB[deviceID].isOn) {
			if (myDeviceDB[deviceID].foodItems.containsKey(key)){
				myDeviceDB[deviceID].foodItems.remove(key);
				LogUtils.logInfo(Activator.logger, "MyDevices", "removeFoodItem", 
						new Object[] {"Food item " + key + " in ", "Device"+deviceID, " removed!"}, null);
				System.out.println("Server removed food item "+ key);
			}
			else{
				LogUtils.logInfo(Activator.logger, "MyDevices", "removeFoodItem", 
						new Object[] {"Food item " + key + " in ", "Device"+deviceID, " failed to be removed!"}, null);
				System.out.println("Server failed to remove food item "+ key);
			}
		}
	}

	public void removeFoodItem(int deviceID, FoodItem value) {
		if (myDeviceDB[deviceID].isOn) {
			System.out.println("Server is going to remove food item "+value.getName());
			String name = value.getName();
			myDeviceDB[deviceID].foodItems.remove(name);
			LogUtils.logInfo(Activator.logger, "MyDevices", "removeFoodItem", 
					new Object[] {"Food item " + name + " in ", "Device"+deviceID, " removed!"}, null);
			System.out.println("Server removed food item "+value.getName());
		}
	}

	public void turnOff(int deviceID) {
		if (myDeviceDB[deviceID].isOn) {
			myDeviceDB[deviceID].isOn = false;
			myDeviceDB[deviceID].temp = 0;
			LogUtils.logInfo(Activator.logger, "MyDevices", "turnOff", 
					new Object[] {"Device in ", myDeviceDB[deviceID].loc, " turned off!"}, null);
			for (Iterator i=listeners.iterator(); i.hasNext();)
				((DeviceStateListener) i.next()).deviceStateChanged(deviceID, myDeviceDB[deviceID].loc, false);
		}
	}
	
	public void turnOn(int deviceID) {
		System.out.println("2. MyDevices");
		if (!myDeviceDB[deviceID].isOn) {
			myDeviceDB[deviceID].isOn = true;
			LogUtils.logInfo(Activator.logger, "MyDevices", "turnOn", 
					new Object[] {"Lamp in ", myDeviceDB[deviceID].loc, " turned on!"}, null);
			for (Iterator i=listeners.iterator(); i.hasNext();)
				((DeviceStateListener) i.next()).deviceStateChanged(deviceID, myDeviceDB[deviceID].loc, true);			
		}
	}

	public void changeTemperature(int deviceID, int inputTemp) {
		System.out.println("Server is going to change temperature ...");
		if (myDeviceDB[deviceID].isOn) {
			if (inputTemp>-18 && inputTemp<20){
				System.out.println("##########   Changing the refrigerator temperature !");
				myDeviceDB[deviceID].temp = inputTemp;
				LogUtils.logInfo(Activator.logger, "MyDevices", "changeTemperature", 
						new Object[] {"Refrigerator Temperature", myDeviceDB[deviceID].temp, " !"}, null);

			}
		}
	}
}
