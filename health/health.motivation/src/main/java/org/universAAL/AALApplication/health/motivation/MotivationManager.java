package org.universAAL.AALApplication.health.motivation;

import org.universAAL.middleware.container.ModuleContext;

public class MotivationManager {
	public static ModuleContext context=null;
	public static SCaller scaller=null;
	public static CSubscriber csubscriber=null;
	
	public static void start(ModuleContext context) throws Exception {
		MotivationManager.context=context;
		scaller=new SCaller(context);
		csubscriber=new CSubscriber(context);
	}

	public static void stop(ModuleContext arg0) throws Exception {
		scaller.close();
		csubscriber.close();
	}
	
}
