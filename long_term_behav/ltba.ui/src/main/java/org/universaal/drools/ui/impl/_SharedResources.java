package org.universaal.drools.ui.impl;

import org.universAAL.middleware.container.ModuleContext;

public class _SharedResources {

	public static final String DROOLS_UI_NAMESPACE = "http://www.tsbtecnologias.es/DroolsUI.owl#";
	public static ModuleContext moduleContext;
	static LTBAUIProvider serviceProvider;

	// static ServiceProvider serviceProvider;
	static UIProvider uIProvider;

//	static final AssistedPerson testUser = new AssistedPerson(
//			Constants.uAAL_MIDDLEWARE_LOCAL_ID_PREFIX + "saied");

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
				_SharedResources.serviceProvider = new LTBAUIProvider(
						moduleContext);
				_SharedResources.uIProvider = new UIProvider(moduleContext);
			}
		}.start();
	}

}
