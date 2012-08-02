/**
 * 
 */
package org.universAAL.ltba.publisher;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

/**
 * @author mllorente
 *
 */
public class Activator implements BundleActivator {

	/* (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		StaticPublisher.publishStaticScript(context);
		GUIHousePublisher.init(context);
	}

	/* (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		// TODO Auto-generated method stub

	}

}
