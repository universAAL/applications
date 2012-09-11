package org.universAAL.AALapplication.food_shopping.service.RFidProvider;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.container.osgi.uAALBundleContainer;


public class Activator implements BundleActivator {
	
	public static BundleContext osgiContext = null;
    public static ModuleContext context = null;

	public static CPublisher cpublisher=null;

    public void start(BundleContext bcontext) throws Exception {
	Activator.osgiContext = bcontext;
	Activator.context = uAALBundleContainer.THE_CONTAINER.registerModule(new Object[] { bcontext });
		cpublisher=new CPublisher(Activator.context);
    }

    public void stop(BundleContext arg0) throws Exception {
		cpublisher.close();
    }

/*	
    private CPublisher provider = null;
    public static ModuleContext mc;
	
	public void start(final BundleContext context) throws Exception {
		mc = uAALBundleContainer.THE_CONTAINER.registerModule(new Object[] { context });
		System.out.println("Food Shopping RFid Reader Provider started ...");
		new Thread() {
			public void run() {
				provider = new CPublisher(mc);
			}
		}.start();
	}

	public void stop(BundleContext context) throws Exception {
		if (provider != null) {
		    provider.close();
		    provider = null;
		}
	}
*/
}