/**
 * 
 */
package org.universAAL.drools.event.publisher;

import org.osgi.framework.BundleContext;
import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.container.osgi.uAALBundleContainer;
import org.universAAL.middleware.context.ContextEvent;
import org.universAAL.middleware.context.ContextEventPattern;
import org.universAAL.middleware.context.ContextPublisher;
import org.universAAL.middleware.context.DefaultContextPublisher;
import org.universAAL.middleware.context.owl.ContextProvider;
import org.universAAL.middleware.context.owl.ContextProviderType;
import org.universAAL.middleware.owl.supply.LevelRating;
import org.universAAL.middleware.rdf.Resource;
import org.universAAL.ontology.activityhub.MotionSensor;
import org.universAAL.ontology.activityhub.MotionSensorEvent;
import org.universAAL.ontology.location.Location;
import org.universAAL.ontology.phThing.Device;
import org.universAAL.ontology.phThing.Sensor;

/**
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

	/**
	 * A different event.
	 */
	public static void publishDifferentContextEvent(String location,
			BundleContext theContext) {
		if (cp == null)
			initHelperClasses(theContext);
		Device d = new Device("http://www.tsbtecnologias.es/Device.owl#Device");
		d.setBatteryLevel(LevelRating.full);
		d.setLocation(new Location("MyLocation"));
		cp.publish(new ContextEvent(d, Device.PROP_BATTERY_LEVEL));
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
