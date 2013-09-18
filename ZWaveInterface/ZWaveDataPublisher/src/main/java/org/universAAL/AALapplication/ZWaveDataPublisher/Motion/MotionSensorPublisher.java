/*
	Copyright 2011-2012 TSB, http://www.tsbtecnologias.es
	TSB - Tecnolog�as para la Salud y el Bienestar
	
	See the NOTICE file distributed with this work for additional 
	information regarding copyright ownership
	
	Licensed under the Apache License, Version 2.0 (the "License");
	you may not use this file except in compliance with the License.
	You may obtain a copy of the License at
	
	  http://www.apache.org/licenses/LICENSE-2.0
	
	Unless required by applicable law or agreed to in writing, software
	distributed under the License is distributed on an "AS IS" BASIS,
	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
	See the License for the specific language governing permissions and
	limitations under the License.
 */
package org.universAAL.AALapplication.ZWaveDataPublisher.Motion;

import org.osgi.framework.BundleContext;
import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.container.osgi.uAALBundleContainer;
import org.universAAL.middleware.context.ContextEvent;
import org.universAAL.middleware.context.ContextEventPattern;
import org.universAAL.middleware.context.ContextPublisher;
import org.universAAL.middleware.context.DefaultContextPublisher;
import org.universAAL.middleware.context.owl.ContextProvider;
import org.universAAL.middleware.context.owl.ContextProviderType;
import org.universAAL.ontology.activityhub.MotionSensorEvent;
import org.universAAL.ontology.activityhub.MotionSensor;
import org.universAAL.ontology.activityhub.ContactClosureSensorEvent;
import org.universAAL.ontology.activityhub.ContactClosureSensor;

public class MotionSensorPublisher {

	private ContextPublisher cp;
	ContextProvider info = new ContextProvider();
	ModuleContext mc;
	public final static String NAMESPACE = "http://tsbtecnologias.es/MotionSensorPublisher#";	
	
	public MotionSensorPublisher(BundleContext context) {
    	System.out.print("New Publisher\n");
    	info = new ContextProvider(
				"http://www.tsbtecnologias.es/ContextProvider.owl#ZWaveEventPublisher");
		mc = uAALBundleContainer.THE_CONTAINER
				.registerModule(new Object[] { context });
		info.setType(ContextProviderType.gauge);
		info
				.setProvidedEvents(new ContextEventPattern[] { new ContextEventPattern() });
		cp = new DefaultContextPublisher(mc, info);
	}
	
	public void publishMotionDetection(String message){

           String[] veraResponse = message.split(" ");
           
           if (veraResponse[0].compareTo("Motion")==0){
        	   String msURL = NAMESPACE + veraResponse[1];
          
        	   MotionSensorEvent mse = MotionSensorEvent.motion_detected;
        	   MotionSensor ms = new MotionSensor(msURL);
        	   ms.setMeasuredValue(mse);
           System.out.print("Publishing motion\n");
        	   cp.publish(new ContextEvent(ms, MotionSensor.PROP_MEASURED_VALUE));
           }
           else if (veraResponse[0].compareTo("Contact")==0){
        	   String msURL = NAMESPACE + veraResponse[1];
        	   
        	   ContactClosureSensorEvent cce = ContactClosureSensorEvent.contact_opened;
        	   ContactClosureSensor cc = new ContactClosureSensor(msURL);
        	   cc.setMeasuredValue(cce);
        	   System.out.print("Publishing contact\n");
        	   cp.publish(new ContextEvent(cc, ContactClosureSensor.PROP_MEASURED_VALUE));        	   
           }
	}
}
