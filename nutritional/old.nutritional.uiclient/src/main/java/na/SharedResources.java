package na;

import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.util.Constants;
import org.universAAL.ontology.profile.AssistedPerson;;

public class SharedResources {

    public static final String CLIENT_LIGHTING_UI_NAMESPACE = "urn:samples.lighting.uiclient:";

    public static ModuleContext moduleContext;

    static ServiceProvider serviceProvider;
    //static UIProvider uIProvider;

    static final AssistedPerson testUser = new AssistedPerson(
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
		//new LightingConsumer(moduleContext);

		SharedResources.serviceProvider = new ServiceProvider(
			moduleContext);
//		SharedResources.uIProvider = new UIProvider(moduleContext);
	    }
	}.start();
    }
}
