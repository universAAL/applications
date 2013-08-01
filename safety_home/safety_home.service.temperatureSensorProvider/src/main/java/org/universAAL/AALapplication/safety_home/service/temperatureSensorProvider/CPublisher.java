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

package org.universAAL.AALapplication.safety_home.service.temperatureSensorProvider;

import org.osgi.framework.BundleContext;
import org.universAAL.AALapplication.safety_home.service.temperatureSensorSoapClient.SOAPClient;
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
import org.universAAL.ontology.Safety.TemperatureSensor;
/**
 * @author dimokas
 * 
 */

public class CPublisher extends ContextPublisher{
	public static final String SAFETY_TEMPERATURE_PROVIDER_NAMESPACE = "http://ontology.universaal.org/SafetyTemperatureProvider.owl#";
	public static final String MY_URI = SAFETY_TEMPERATURE_PROVIDER_NAMESPACE + "Temperature";
	static final String DEVICE_URI_PREFIX = CPublisher.SAFETY_TEMPERATURE_PROVIDER_NAMESPACE
			+ "controlledDevice";
	static final String LOCATION_URI_PREFIX = "urn:aal_space:myHome#";
	
	private ContextPublisher cp;

	private float previousTemp = -100;
	private float currentTemp = -20;

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
		//getUsers();
		while(true){
			Thread.sleep(14500);
			publishTemperature(0);
		}
	}
	
	private void publishTemperature(int deviceID){
		Device device=null;
		//System.out.println("previous Temperature="+previousTemp);
		//System.out.println("current Temperature="+currentTemp);
		
		if(deviceID==0){
			TemperatureSensor temperature = new TemperatureSensor(CPublisher.DEVICE_URI_PREFIX + deviceID);
			device=(Device)temperature;
			temperature.setDeviceLocation(new Room(CPublisher.LOCATION_URI_PREFIX + "temperature"));
			currentTemp = SOAPClient.getTemperature();
			temperature.setTemperature(currentTemp);
			
			if (previousTemp != currentTemp){
				System.out.println("############### PUBLISHING EVENT ###############");
				cp.publish(new ContextEvent(temperature, TemperatureSensor.PROP_TEMPERATURE));
				System.out.println("################################################");
				previousTemp=currentTemp;
			}
		}
	}

	private static ContextProvider getProviderInfo() {
		ContextEventPattern cep4 = new ContextEventPattern();
		cep4.addRestriction(MergedRestriction.getAllValuesRestriction(ContextEvent.PROP_RDF_SUBJECT, TemperatureSensor.MY_URI));
		ContextProvider info = new ContextProvider(SAFETY_TEMPERATURE_PROVIDER_NAMESPACE + "TemperatureContextProvider");
		info.setType(ContextProviderType.controller);
		info.setProvidedEvents(new ContextEventPattern[]{cep4});

		return info;
	}

	public void communicationChannelBroken() {
		// TODO Auto-generated method stub
		
	}
}
