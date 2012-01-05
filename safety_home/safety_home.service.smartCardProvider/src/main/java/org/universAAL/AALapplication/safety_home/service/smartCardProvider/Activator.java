package org.universAAL.AALapplication.safety_home.service.smartCardProvider;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class Activator implements BundleActivator{

	public void start(final BundleContext context) throws Exception {
		System.out.println("Smart Card Provider started ...");
		new Thread() {
			public void run() {
				new CPublisher(context);
			}
		}.start();
	}

	public void stop(BundleContext arg0) throws Exception {
	}

}
