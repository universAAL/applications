package org.universAAL.AALapplication.safety_home.service.windowProvider;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class Activator implements BundleActivator{

	public void start(final BundleContext context) throws Exception {
		System.out.println("Window Provider started ...");
		new Thread() {
			public void run() {
				new CPublisher(context);
			}
		}.start();
	}

	public void stop(BundleContext arg0) throws Exception {
	}

}
