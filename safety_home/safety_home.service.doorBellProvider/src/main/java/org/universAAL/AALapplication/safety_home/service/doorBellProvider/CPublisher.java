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

package org.universAAL.AALapplication.safety_home.service.doorBellProvider;

import org.universAAL.AALapplication.safety_home.service.doorBellSoapClient.SOAPClient;
import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.context.ContextEvent;
import org.universAAL.middleware.context.ContextEventPattern;
import org.universAAL.middleware.context.ContextPublisher;
import org.universAAL.middleware.context.DefaultContextPublisher;
import org.universAAL.middleware.context.owl.ContextProvider;
import org.universAAL.middleware.context.owl.ContextProviderType;
import org.universAAL.middleware.owl.MergedRestriction;
import org.universAAL.ontology.location.indoor.Room;
import org.universAAL.ontology.phThing.Device;
import org.universAAL.ontology.Safety.DoorBell;
import org.universAAL.ontology.Safety.HumiditySensor;

/**
 * @author dimokas
 * 
 */

public class CPublisher extends ContextPublisher{
	public static final String SAFETY_DOORBELL_PROVIDER_NAMESPACE = "http://ontology.universaal.org/SafetyDoorBellProvider.owl#";
	public static final String MY_URI = SAFETY_DOORBELL_PROVIDER_NAMESPACE + "DoorBell";
	static final String DEVICE_URI_PREFIX = CPublisher.SAFETY_DOORBELL_PROVIDER_NAMESPACE + "controlledDevice";
	static final String LOCATION_URI_PREFIX = "urn:aal_space:myHome#";
	
	private ContextPublisher cp;
	private int cnt=0;
	
	protected CPublisher(ModuleContext context, ContextProvider providerInfo) {
		super(context, providerInfo);
	}
	
	protected CPublisher(ModuleContext context) {
		super(context, getProviderInfo());
		try{
			cp = new DefaultContextPublisher(context, getProviderInfo());
			invoke();
		}
		catch (InterruptedException e){
			e.printStackTrace();
		}
	}

	public CPublisher(ModuleContext context, ContextProvider providerInfo, ContextPublisher cp) {
		super(context, providerInfo);
		try{
			this.cp = cp;
			invoke();
		}
		catch (InterruptedException e){
			e.printStackTrace();
		}
	}
	
	public void invoke() throws InterruptedException{
		//Thread.sleep(80000); // Delete after test
		//publishDoorBell(0);

		while (true){
			Thread.sleep(3000);
			publishDoorBell(0);
		}
		
	}
	
	private void publishDoorBell(int deviceID){
		Device device=null;
		
		if(deviceID==0){
			DoorBell doorbell = new DoorBell(CPublisher.DEVICE_URI_PREFIX + deviceID);
			device=(Device)doorbell;
			//boolean isEnabled = SOAPClient.isDoorBellEnabled();
			boolean isEnabled = true;
			if (isEnabled){
				if (cnt==0){
					doorbell.setIsEnabled(isEnabled);
					System.out.println("############### PUBLISHING EVENT ###############");
					cp.publish(new ContextEvent(doorbell, DoorBell.PROP_IS_ENABLED));
					System.out.println("################################################");
				}
				cnt=1;
			}
		}
	}

	 private static ContextProvider getProviderInfo() {
		  ContextEventPattern cep8 = new ContextEventPattern();
		  cep8.addRestriction(MergedRestriction.getAllValuesRestriction(ContextEvent.PROP_RDF_SUBJECT,HumiditySensor.MY_URI));
		  ContextProvider info = new ContextProvider(SAFETY_DOORBELL_PROVIDER_NAMESPACE + "DoorBellContextProvider");
		  info.setType(ContextProviderType.controller);
		  info.setProvidedEvents(new ContextEventPattern[]{cep8});

		  return info;
	}

	public void communicationChannelBroken() {
		// TODO Auto-generated method stub
		
	}
}
