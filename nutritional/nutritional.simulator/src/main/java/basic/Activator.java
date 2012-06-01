package basic;



import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.container.osgi.uAALBundleContainer;

import uAAL.simulation_services.ServiceGetRecipeCallee;
import uAAL.simulation_services.ServiceGetTodayMenuCallee;

public class Activator implements BundleActivator{
	public static BundleContext context=null;
	public static ModuleContext mc;

	public void start(BundleContext context) throws Exception {
		System.out.println("Nutri: Nutrition service simulation starts");
		Activator.context=context;
		mc = uAALBundleContainer.THE_CONTAINER
				.registerModule(new Object[] { context });

		new ServiceGetRecipeCallee(mc);
		new ServiceGetTodayMenuCallee(mc);

		System.out.println("Nutri: Nutrition service simulation started");
	}

	public void stop(BundleContext arg0) throws Exception {
	}

}
