/**
 * 
 */
package org.universAAL.ltba.manager;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.universAAL.ltba.service.LTBAProvider;
import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.container.osgi.uAALBundleContainer;

/**
 * @author mllorente
 *
 */
public class Activator implements BundleActivator{

	private LTBAProvider provider;

	public void start(BundleContext context) throws Exception {
		ModuleContext mc = uAALBundleContainer.THE_CONTAINER
		.registerModule(new Object[] { context });
		ConsequenceListener listener = new ConsequenceListener(mc);
		provider = new LTBAProvider(mc,listener);
		
	}

	public void stop(BundleContext context) throws Exception {
		// TODO Auto-generated method stub
		
	}

}
