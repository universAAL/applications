/*
	Copyright 2008-2014 TSB, http://www.tsbtecnologias.es
	TSB - Tecnologías para la Salud y el Bienestar
	
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
package org.universAAL.ltba.publisher;

import org.osgi.framework.BundleContext;
import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.container.osgi.uAALBundleContainer;
import org.universAAL.middleware.context.ContextEvent;
import org.universAAL.middleware.context.ContextEventPattern;
import org.universAAL.middleware.context.ContextPublisher;
import org.universAAL.middleware.context.DefaultContextPublisher;
import org.universAAL.middleware.context.owl.ContextProvider;
import org.universAAL.middleware.context.owl.ContextProviderType;
import org.universAAL.middleware.rdf.Resource;
import org.universAAL.ontology.activityhub.ContactClosureSensor;
import org.universAAL.ontology.activityhub.ContactClosureSensorEvent;
import org.universAAL.ontology.activityhub.MotionSensor;
import org.universAAL.ontology.activityhub.MotionSensorEvent;
import org.universAAL.ontology.device.Sensor;
import org.universAAL.ontology.location.Location;

/**
 * Publisher of context events.
 * 
 * @author mllorente
 * 
 */
public class StaticPublisher {
    private static final String TSB_NAMESPACE = "http://www.tsbtecnologias.es/";
    private static ContextPublisher cp;
    private static ContextProvider info = new ContextProvider();
    private static ModuleContext mc;

    public static void publishStaticScript(BundleContext theContext) {

	if (cp == null)
	    initHelperClasses(theContext);

	// parseScript();
    }

    public static void publishMotionEventDetected(String location,
	    BundleContext theContext) {
	if (cp == null)
	    initHelperClasses(theContext);
	MotionSensor ms = new MotionSensor(
		"http://www.tsbtecnologias.es/MotionSensor.owl#MotionSensor");
	MotionSensorEvent mse = new MotionSensorEvent(TSB_NAMESPACE
		+ "MotionSensorEvent.owl#MotionSensorEvent");
	Location motionSensorLocation = new Location(TSB_NAMESPACE
		+ "Location.owl#MotionSensorTestLocation", location);
	ms.setLocation(motionSensorLocation);
	ms.setProperty(MotionSensor.PROP_MEASURED_VALUE, mse.motion_detected);

	cp.publish(new ContextEvent(ms, Sensor.PROP_MEASURED_VALUE));
    }

    public static void publishMagneticContactEventDetected(String name,
	    Boolean open, BundleContext theContext) {
	if (cp == null)
	    initHelperClasses(theContext);
	ContactClosureSensor ccs = new ContactClosureSensor(
		"http://www.tsbtecnologias.es/MotionSensor.owl#ContactClosureSensor");
	ContactClosureSensorEvent ccse = new ContactClosureSensorEvent(
		TSB_NAMESPACE + "ContactClosureSensorEvent.owl#ContactEvent");
	Location ccsLocation = new Location(TSB_NAMESPACE
		+ "Location.owl#ContactClosureSensorTestLocation", name);
	ccs.setLocation(ccsLocation);
	if (open) {
	    ccs.setProperty(ContactClosureSensor.PROP_MEASURED_VALUE,
		    ccse.contact_opened);
	} else {
	    ccs.setProperty(ContactClosureSensor.PROP_MEASURED_VALUE,
		    ccse.contact_closed);
	}
	cp.publish(new ContextEvent(ccs, Sensor.PROP_MEASURED_VALUE));
    }

    private static void initHelperClasses(BundleContext theContext) {

	info = new ContextProvider(
		"http://www.tsbtecnologias.es/ContextProvider.owl#ScriptPublisher");
	mc = uAALBundleContainer.THE_CONTAINER
		.registerModule(new Object[] { theContext });
	info.setType(ContextProviderType.gauge);
	info
		.setProvidedEvents(new ContextEventPattern[] { new ContextEventPattern() });
	cp = new DefaultContextPublisher(mc, info);
    }

    private static void parseScript() {

	Resource r;
	ContextEvent ce;

    }

}
