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

package org.universAAL.AALapplication.safety_home.service.windowProvider;

import org.universAAL.AALapplication.safety_home.service.windowSoapClient.SOAPClient;
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
import org.universAAL.ontology.Safety.HumiditySensor;
import org.universAAL.ontology.Safety.Window;

/**
 * @author dimokas
 * 
 */

public class CPublisher extends ContextPublisher{
	public static final String SAFETY_WINDOW_PROVIDER_NAMESPACE = "http://ontology.universaal.org/SafetyWindowProvider.owl#";
	public static final String MY_URI = SAFETY_WINDOW_PROVIDER_NAMESPACE + "Window";
	static final String DEVICE_URI_PREFIX = CPublisher.SAFETY_WINDOW_PROVIDER_NAMESPACE
			+ "controlledDevice";
	static final String LOCATION_URI_PREFIX = "urn:aal_space:myHome#";
	
	private ContextPublisher cp;
	private int previousState = -1;
	private int state = 0;
	
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
		while (true){
			Thread.sleep(11000);
			publishWindowStatus(0);
		}
	}
	
	private void publishWindowStatus(int deviceID){
		Device device=null;
		
		//System.out.println("previous state="+previousState);
		//System.out.println("state="+state);
		//if (previousState != state){
			if(deviceID==0){
				Window window = new Window(CPublisher.DEVICE_URI_PREFIX + deviceID);
				device=(Device)window;
				window.setDeviceLocation(new Room(CPublisher.LOCATION_URI_PREFIX + "window"));
				if (SOAPClient.isWindowClosed()){
					window.setSensorStatus(new Integer(0));
					state = 0;
				}
				else{
					window.setSensorStatus(new Integer(100));
					state = 1;
				}
				if (previousState != state){
					System.out.println("############### PUBLISHING EVENT ###############");
					cp.publish(new ContextEvent(window, Window.PROP_SENSOR_STATUS));
					System.out.println("################################################");
					this.previousState = state;
				}
			}
		//}
	}

	 private static ContextProvider getProviderInfo() {
		 ContextEventPattern cep2 = new ContextEventPattern();
		 cep2.addRestriction(MergedRestriction.getAllValuesRestriction(ContextEvent.PROP_RDF_SUBJECT, Window.MY_URI));
		 ContextProvider info = new ContextProvider(SAFETY_WINDOW_PROVIDER_NAMESPACE + "WindowContextProvider");
		 info.setType(ContextProviderType.controller);
		 info.setProvidedEvents(new ContextEventPattern[]{cep2});
		 return info;
	}

	public void communicationChannelBroken() {
		// TODO Auto-generated method stub
		
	}

}
