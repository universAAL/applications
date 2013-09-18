package org.universAAL.AALapplication.ZWaveDataConsumer;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.container.osgi.uAALBundleContainer;


public class Activator implements BundleActivator {
	public static BundleContext osgiContext = null;
	public static ModuleContext context = null;
	public static MotionSubscriber csubscriber = null;

	public void start(BundleContext bcontext) throws Exception {
		System.out.print("Starting ZWave Info Consumer");
		Activator.osgiContext = bcontext;
		Activator.context = uAALBundleContainer.THE_CONTAINER
			.registerModule(new Object[] { bcontext });
			//scallee=new AALfficiencyProvider(context);
			new Thread() {
			    public void run() {
			    	new MotionSubscriber(context);
			    }
			}.start();
//			new Thread() {
//			    public void run() {
//			    	new ClosureSubscriber(context);
//			    }
//			}.start();
			new Thread() {
				public void run() {
					new PowerSubscriber(context);
				}
			}.start();
			}

	public void stop(BundleContext arg0) throws Exception {
		csubscriber.close();
	}

}
