package org.universAAL.AALapplication.nutritional;

import org.universAAL.AALapplication.nutritional.dialogs.InterfaceProvider;
import org.universAAL.AALapplication.nutritional.services.ServiceGetRecipeCallee;
import org.universAAL.AALapplication.nutritional.services.ServiceGetTodayMenuCallee;
import org.universAAL.AALapplication.nutritional.utils.Utils;
import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.service.ServiceCall;
import org.universAAL.middleware.util.Constants;
import org.universAAL.ontology.profile.AssistedPerson;
import org.universAAL.ontology.profile.User;

public class SharedResources {

    public static final String CLIENT_NUTRITIONAL_UI_NAMESPACE = "urn:nutrition.client:";

    private static ModuleContext moduleContext;

    public static UIServiceProvider serviceProvider;
    public static InterfaceProvider uIProvider;
    
    public static ServiceGetTodayMenuCallee serviceGetTodayMenuCallee;
    public static ServiceGetRecipeCallee serviceGetRecipeCallee;

 //   public static final AssistedPerson testUser = new AssistedPerson(Constants.uAAL_MIDDLEWARE_LOCAL_ID_PREFIX + "saied");
  
    public static User user = null;

    public SharedResources(ModuleContext context) {
	moduleContext = context;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext
     * )
     */
    public void start() {
		SharedResources.serviceProvider = new UIServiceProvider(moduleContext);
		SharedResources.uIProvider = new InterfaceProvider(moduleContext);
		SharedResources.serviceGetTodayMenuCallee = new ServiceGetTodayMenuCallee(moduleContext);
		SharedResources.serviceGetRecipeCallee = new ServiceGetRecipeCallee(moduleContext);
    }
    
    public void stop(){
		SharedResources.serviceProvider.close();
		SharedResources.uIProvider.close();
		SharedResources.serviceGetTodayMenuCallee.close();
		SharedResources.serviceGetRecipeCallee.close();
   }

    public static ModuleContext getMContext() {
	return moduleContext;
    }
}
