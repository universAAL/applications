package org.universAAL.AALfficiencyTest;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.container.osgi.uAALBundleContainer;

import org.universAAL.middleware.service.ServiceCaller;
import org.universAAL.middleware.service.DefaultServiceCaller;

public class Activator implements BundleActivator {
    public static ModuleContext mc;
	public static ServiceCaller scaller=null;

    public void start(BundleContext context) throws Exception {
    	System.out.println("Activating");
		ModuleContext mc = uAALBundleContainer.THE_CONTAINER
		.registerModule(new Object[] { context });
	
		System.out.println("Now calling to AAlfficiency!!");
		AAlfficiencyTest.testCall4(mc);
		
    }

    public void stop(BundleContext arg0) throws Exception {
		scaller.close();
    }

}
