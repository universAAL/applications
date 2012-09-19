/*
	Copyright 2012 CERTH, http://www.certh.gr
	
	See the NOTICE file distributed with this work for additional 
	information regarding copyright ownership
	
	Licensed under the Apache License, Version 2.0 (the "License");
	you may not use this file except in compliance with the License.
	You may obtain a copy of the License at
	
	  http://www.apache.org/licenses/LICENSE-2.0
	
	Unless required by applicable law or agreed to in writing, software
	distributed under the License is distributed on an "AS IS" BASIS,
	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
	See the License for the specific language governing permissions and
	limitations under the License.
 */
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
