package org.universAAL.AALapplication.safety_home.service.server;

//import org.apache.log4j.Logger;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.universAAL.middleware.util.LogUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

/**
 * @author dimokas
 *
 */
public class Activator implements BundleActivator {
	
	//public static Logger logger = LoggerFactory.getLogger(Activator.class);
	
	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	public void start(final BundleContext context) throws Exception {
		System.out.println("Safety at Home Service started ...");
		new Thread() {
			public void run() {
				new SafetyProvider(context);
			}
		}.start();
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
	}
}
