package nna;

import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.util.Constants;
import org.universAAL.ontology.profile.AssistedPerson;

import uAAL.UI.dialogs.UIMainProvider;




public class SharedResources {

    public static final String CLIENT_NUTRITIONAL_UI_NAMESPACE = "urn:samples.nutrition.uiclient:";

    public static ModuleContext moduleContext;

    static UIServiceProvider serviceProvider;
    static UIMainProvider uIProvider;

    public static final AssistedPerson testUser = new AssistedPerson(
	    Constants.uAAL_MIDDLEWARE_LOCAL_ID_PREFIX + "saied");

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext
     * )
     */
    public void start() throws Exception {
	new Thread() {
	    public void run() {
	    	System.out.println("Run de SharedResources");
		//new NutritionalConsumer(moduleContext);

		SharedResources.serviceProvider = new UIServiceProvider(
			moduleContext);
		SharedResources.uIProvider = new UIMainProvider(moduleContext);
	    }
	}.start();
    }
}
