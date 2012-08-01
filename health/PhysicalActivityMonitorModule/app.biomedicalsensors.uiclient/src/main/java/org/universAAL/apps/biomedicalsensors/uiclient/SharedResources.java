package org.universAAL.apps.biomedicalsensors.uiclient;

import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.util.Constants;
import org.universAAL.ontology.profile.AssistedPerson;

;

public class SharedResources {

	public static final String CLIENT_BIOMEDICALSENSORS_UI_NAMESPACE = "urn:app.biomedicalsensors.uiclient:";

	public static ModuleContext moduleContext;

	static ServiceProvider serviceProvider;
	static UIProvider uIProvider;

	static final AssistedPerson testUser = new AssistedPerson(
			Constants.uAAL_MIDDLEWARE_LOCAL_ID_PREFIX + "joe");

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
				new BiomedicalSensorsServiceCaller(moduleContext);

				SharedResources.serviceProvider = new ServiceProvider(
						moduleContext);
				SharedResources.uIProvider = new UIProvider(moduleContext);
			}
		}.start();
	}
}
