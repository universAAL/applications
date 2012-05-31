package osgi;

import na.Arranque;
import nna.utils.Utils;
import nna.SharedResources;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.universAAL.middleware.container.osgi.uAALBundleContainer;
import org.universAAL.middleware.util.Constants;
import org.universAAL.ontology.profile.User;

import uAAL.services.ServiceGetRecipeCallee;
import uAAL.services.ServiceGetTodayMenuCallee;


public class Activator implements BundleActivator {
    
    SharedResources sr;
    static final User testUser = new User(Constants.uAAL_MIDDLEWARE_LOCAL_ID_PREFIX + "saied");

    public void start(BundleContext context) throws Exception {
    	Utils.println("STARTING NUTRITIONAL UI");
		SharedResources.moduleContext = uAALBundleContainer.THE_CONTAINER
			.registerModule(new Object[] { context });
	
		sr = new SharedResources();
		sr.start();
		Arranque arranque = new Arranque();
    	arranque.inicializar(testUser.getLocalName());
//    	new ServiceGetRecipeCallee(SharedResources.moduleContext);
//    	new ServiceGetTodayMenuCallee(SharedResources.moduleContext);
    	
		Utils.println("NUTRITIONAL UI STARTED");
    }

    public void stop(BundleContext context) throws Exception {
    	Utils.println("STOPPING NUTRITIONAL UI");
    }

}
