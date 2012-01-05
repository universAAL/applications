package org.universAAL.ontology;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;


public class Activator implements BundleActivator {

	static BundleContext context = null;
	
	public void start(BundleContext context) throws Exception {
		Activator.context = context;
		Class.forName("org.universAAL.ontology.safetyDevices.Door");
		Class.forName("org.universAAL.ontology.safetyDevices.Window");
		Class.forName("org.universAAL.ontology.safetyDevices.LightSensor");
		Class.forName("org.universAAL.ontology.safetyDevices.TemperatureSensor");
		Class.forName("org.universAAL.ontology.safetyDevices.HumiditySensor");
		Class.forName("org.universAAL.ontology.safetyDevices.MotionSensor");
	}
	
	public void stop(BundleContext arg0) throws Exception {
		// TODO Auto-generated method stub
		
	}

}