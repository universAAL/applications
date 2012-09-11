package org.universAAL.AALapplication.food_shopping.service.client;

import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.util.Constants;
import org.universAAL.ontology.profile.AssistedPerson;;

public class SharedResources {

    public static final String CLIENT_SHOPPING_UI_NAMESPACE = "urn:food_shopping.service.uiclient:";
    //private static final String FOODMANAGEMENT_CONSUMER_NAMESPACE = "http://ontology.universaal.org/FoodManagementClient.owl#";
    
    public static ModuleContext moduleContext;

    static ServiceProvider serviceProvider;
    static UIProvider uIProvider;

    static final AssistedPerson testUser = new AssistedPerson(Constants.uAAL_MIDDLEWARE_LOCAL_ID_PREFIX + "saied");

    public void start() throws Exception {
	new Thread() {
	    public void run() {
	    	new FoodManagementClient(moduleContext);
	    	SharedResources.serviceProvider = new ServiceProvider(moduleContext);
	    	SharedResources.uIProvider = new UIProvider(moduleContext);
	    }
	}.start();
    }
}
