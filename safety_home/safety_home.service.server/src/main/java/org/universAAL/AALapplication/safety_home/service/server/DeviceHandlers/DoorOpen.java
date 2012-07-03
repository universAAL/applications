package org.universAAL.AALapplication.safety_home.service.server.DeviceHandlers;

import jcontrol.framework.webservices.transport.http.exceptions.HTTPException;

import org.askit.domotics.devices.DomoticsException;
import org.askit.domotics.groups.KeyMatic;
import org.askit.domotics.groups.PilotSite;

public class DoorOpen {

	private PilotSite d;
	private KeyMatic k;
	
	public DoorOpen(){
		d = new PilotSite();
		k = d.getKeyMatic();
	}
	
	public boolean open(){

		System.out.print("Run keymatic open...");
		try {
			k.open();
            System.out.println("d.getHS485().isDoorLocked()="+d.getHS485().isDoorLocked());
            System.out.println("d.getHS485().isDoorOpened()="+d.getHS485().isDoorOpened());
		} 
		catch (DomoticsException e) { 
			//e.printStackTrace(); 
			return false; 
		}
		System.out.println("finished!");
		return true;
	}
	
	public static void main(String[] args) throws HTTPException {
		
		DoorOpen door = new DoorOpen();
		door.open();
	}

}
