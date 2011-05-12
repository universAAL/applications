package org.universAAL.AALapplication.tstserv;

import org.osgi.framework.BundleContext;
import org.universAAL.middleware.context.ContextEvent;
import org.universAAL.middleware.context.ContextPublisher;
import org.universAAL.middleware.context.owl.ContextProvider;
import org.universAAL.middleware.context.owl.ContextProviderType;
import org.universAAL.ontology.weather.TempSensor;

public class CPublisher extends ContextPublisher {

    protected CPublisher(BundleContext context, ContextProvider providerInfo) {
	super(context, providerInfo);
	// TODO Auto-generated constructor stub
    }

    protected CPublisher(BundleContext context) {
	// We must pass our info
	super(context, getProviderInfo());
	// We build the ontological data (now the subject)
	TempSensor ts = new TempSensor();
	// We give it a property
	ts.setMeasuredValue(38);
	// We build the event sayinng that the subject is the sensor, the
	// predicate the measured value, and the object is automatically gotten
	// from the predicate
	ContextEvent ev1 = new ContextEvent(ts, TempSensor.PROP_MEASURED_VALUE);
	// We publish!
	this.publish(ev1);
    }

    private static ContextProvider getProviderInfo() {
	// We build or info with our URI...
	ContextProvider cpinfo = new ContextProvider(
		"http://ontology.tsbtecnologias.es/Test.owl#TestContextProvider");
	// ...and our type. Gauge= we just give context info.
	cpinfo.setType(ContextProviderType.gauge);
	return cpinfo;
    }

    public void communicationChannelBroken() {
	// TODO Auto-generated method stub

    }

}
