package org.universAAL.AALApplication.health.motivation;

import org.osgi.framework.BundleContext;
import org.universAAL.AALApplication.health.motivation.osgi.Activator;

public class MotivationManager {
	public static SCaller scaller=null;
	public static CSubscriber csubscriber=null;
	
	public static void start(BundleContext context) throws Exception {
		Activator.context=context;
		scaller=new SCaller(context);
		csubscriber=new CSubscriber(context);
	}

	public static void stop(BundleContext arg0) throws Exception {
		scaller.close();
		csubscriber.close();
	}
}
