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

package org.universAAL.AALapplication.safety_home.service.server.DeviceHandlers;

import jcontrol.framework.webservices.transport.http.exceptions.HTTPException;

import org.askit.domotics.devices.DomoticsException;
import org.askit.domotics.groups.KeyMatic;
import org.askit.domotics.groups.PilotSite;
/**
 * @author dimokas
 *
 */

public class DoorLockUnlock {


	private PilotSite d;
	private KeyMatic k;
	
	public DoorLockUnlock(){
		d = new PilotSite();
		k = d.getKeyMatic();
	}
	
	public boolean unlock(){

		System.out.print("Run keymatic unlock...");
		try {
			k.unlock();
		} 
		catch (DomoticsException e) { 
			//e.printStackTrace(); 
			return true; 
		}
		System.out.println("finished!");
		return true;
	}
	
	public boolean lock(){

		System.out.print("Run keymatic lock...");
		try {
			k.lock();
            System.out.println("d.getHS485().isDoorLocked()="+d.getHS485().isDoorLocked());
            System.out.println("d.getHS485().isDoorOpened()="+d.getHS485().isDoorOpened());
		} 
		catch (DomoticsException e) { 
			//e.printStackTrace(); 
			return true; 
		}
		System.out.println("finished!");
		return true;
	}
	
	public static void main(String[] args) throws HTTPException {
		
		DoorLockUnlock door = new DoorLockUnlock();
		door.unlock();
		door.lock();
	}
}
