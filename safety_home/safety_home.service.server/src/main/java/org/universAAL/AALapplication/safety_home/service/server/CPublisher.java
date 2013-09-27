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

package org.universAAL.AALapplication.safety_home.service.server;

import org.universAAL.AALapplication.safety_home.service.server.unit_impl.MyDevices;
import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.context.ContextEvent;
import org.universAAL.middleware.context.ContextEventPattern;
import org.universAAL.middleware.context.ContextPublisher;
import org.universAAL.middleware.context.DefaultContextPublisher;
import org.universAAL.middleware.context.owl.ContextProvider;
import org.universAAL.middleware.context.owl.ContextProviderType;
import org.universAAL.middleware.owl.MergedRestriction;
import org.universAAL.ontology.Safety.Door;
import org.universAAL.ontology.location.indoor.Room;
import org.universAAL.ontology.phThing.Device;

/**
 * @author dimokas
 * 
 */

public class CPublisher extends ContextPublisher{
	public static final String SAFETY_DOOR_PROVIDER_NAMESPACE = "http://ontology.universaal.org/SafetyDoorProvider.owl#";
	public static final String MY_URI = SAFETY_DOOR_PROVIDER_NAMESPACE + "FrontDoorStatus";
	static final String DEVICE_URI_PREFIX = CPublisher.SAFETY_DOOR_PROVIDER_NAMESPACE + "controlledDevice";
	static final String LOCATION_URI_PREFIX = "urn:aal_space:myHome#";
	
	private ContextPublisher cp;
	
	private int previousState = 0;
	private int state = 0;
	
	protected CPublisher(ModuleContext context) {
		super(context, getProviderInfo());
		try{
			this.cp = new DefaultContextPublisher(context, getProviderInfo());
			invoke();
		}
		catch (InterruptedException e){
			e.printStackTrace();
		}
	}

	public void invoke() throws InterruptedException{
		while (true){
			Thread.sleep(20000);
			publishDoorStatus(0);
		}
	}
	
	private void publishDoorStatus(int deviceID) throws InterruptedException{
		Device device=null;
		
		if(deviceID==0){
			Door door = new Door(CPublisher.DEVICE_URI_PREFIX + deviceID);
			device=(Device)door;
			boolean isDoorOpen = SafetyProvider.getTheServer().isOpen(0);
			if (isDoorOpen){
				door.setStatus(101);
				this.state=1;
			}	
			else
				this.state=this.previousState=0;
			
			if (previousState!=state){
				//int sequenceOfNotifications = 0;
				//while (sequenceOfNotifications<1){
					System.out.println("############### PUBLISHING EVENT ###############");
					door.setStatus(101);
					this.cp.publish(new ContextEvent(door, Door.PROP_DEVICE_STATUS));
					System.out.println("################################################");
					this.previousState = state;
					//sequenceOfNotifications++;
					//Thread.sleep(5000);
				//}
			}
		}
	}

	 private static ContextProvider getProviderInfo() {
		  ContextEventPattern cep9 = new ContextEventPattern();
		  cep9.addRestriction(MergedRestriction.getAllValuesRestriction(ContextEvent.PROP_RDF_SUBJECT,Door.MY_URI));
		  ContextProvider info = new ContextProvider(SAFETY_DOOR_PROVIDER_NAMESPACE + "DoorStatusContextProvider");
		  info.setType(ContextProviderType.controller);
		  info.setProvidedEvents(new ContextEventPattern[]{cep9});
		  return info;
	}

	public void communicationChannelBroken() {
		// TODO Auto-generated method stub
		
	}
}
