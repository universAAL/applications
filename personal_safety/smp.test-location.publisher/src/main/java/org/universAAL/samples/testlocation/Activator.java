package org.universAAL.samples.testlocation;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.universAAL.middleware.container.osgi.uAALBundleContainer;
/** 
 *  @author Miguel Angel Llorente-Carmona
 *  @author Angel Martinez-Cavero
 *  @author alfiva
 *  @author amedrano
 *  
 *  @version 1.0
 * 
 * */

public class Activator implements BundleActivator {

//	protected ContextPublisher cp;

	public void start(BundleContext context) throws Exception {
//		ContextProvider cpinfo = new ContextProvider("http://ontology.tsbtecnologias.es/Test.owl#TestContextProvider");
//		cpinfo.setType(ContextProviderType.gauge);
//		cp = new DefaultContextPublisher(context, cpinfo);
//		
//		User user=new User("http://ontology.tsbtecnologias.es/Test.owl#bilbo");
//		Location loc=new Location("http://ontology.tsbtecnologias.es/Test.owl#livingRoom","LivingRoom");
//		user.setLocation(loc);
//
//		ContextEvent ev1 = new ContextEvent(user, User.PROP_PHYSICAL_LOCATION);
//
//		cp.publish(ev1);
//		cp.publish(ev1);
//		cp.publish(ev1);
		
		GUIPanel.start(uAALBundleContainer.THE_CONTAINER
				.registerModule(new BundleContext[] { context }));
	}

	public void stop(BundleContext arg0) throws Exception {
//		cp.close();
		GUIPanel.stop(uAALBundleContainer.THE_CONTAINER
				.registerModule(new BundleContext[] { arg0 }));
	}

}

