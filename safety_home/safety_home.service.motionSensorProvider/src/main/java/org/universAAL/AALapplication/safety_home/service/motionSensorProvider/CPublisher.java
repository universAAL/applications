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

package org.universAAL.AALapplication.safety_home.service.motionSensorProvider;

import org.universAAL.AALapplication.safety_home.service.motionSensorSoapClient.SOAPClient;
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
import org.universAAL.ontology.Safety.MotionSensor;

/**
 * @author dimokas
 * 
 */

public class CPublisher extends ContextPublisher{
	public static final String SAFETY_MOTION_PROVIDER_NAMESPACE = "http://ontology.universaal.org/SafetyMotionProvider.owl#";
	public static final String MY_URI = SAFETY_MOTION_PROVIDER_NAMESPACE + "Motion";
	static final String DEVICE_URI_PREFIX = CPublisher.SAFETY_MOTION_PROVIDER_NAMESPACE
			+ "controlledDevice";
	static final String LOCATION_URI_PREFIX = "urn:aal_space:test_environment#";
	
	private ContextPublisher cp;
	
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
			Thread.sleep(20500);
			publishMotionDetection(0);
		}
	}

	private void publishMotionDetection(int deviceID){
		Device device=null;		
		MotionSensor motion = new MotionSensor(CPublisher.DEVICE_URI_PREFIX + deviceID);
		device=(Device)motion;
		double m = SOAPClient.getMotionDetection();
		if (m>0.0){
			motion.setDeviceLocation(new Room(CPublisher.LOCATION_URI_PREFIX + "motion"));
			motion.setMotion(m);
			System.out.println("############### PUBLISHING EVENT ###############");
			cp.publish(new ContextEvent(motion, MotionSensor.PROP_MOTION));
			System.out.println("################################################");
		}
	}

	
	private static ContextProvider getProviderInfo() {
		ContextEventPattern cep6 = new ContextEventPattern();
		cep6.addRestriction(MergedRestriction.getAllValuesRestriction(ContextEvent.PROP_RDF_SUBJECT, MotionSensor.MY_URI));
		ContextProvider info = new ContextProvider(SAFETY_MOTION_PROVIDER_NAMESPACE + "MotionContextProvider");
		info.setType(ContextProviderType.controller);
		info.setProvidedEvents(new ContextEventPattern[]{cep6});

		return info;
	}

	public void communicationChannelBroken() {
		// TODO Auto-generated method stub
		
	}
}
