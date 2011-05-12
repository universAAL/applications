package org.universAAL.AALapplication.tstserv;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class Activator implements BundleActivator {
    public static BundleContext context = null;
    public static CSubscriber csubscriber = null;
    public static CPublisher cpublisher = null;
    public static SCallee scee = null;

    public void start(BundleContext context) throws Exception {
	Activator.context = context;
	csubscriber = new CSubscriber(context);
	// When we instantiate the publisher we have decided that it will send
	// some event
	cpublisher = new CPublisher(context);
	// Remember to instantiate the Callee associating the profiles from the
	// ProvidedService!!!
	scee = new SCallee(context, SCalleeProvidedService.profiles);
    }

    public void stop(BundleContext arg0) throws Exception {
	csubscriber.close();
	cpublisher.close();
	scee.close();
    }

}
