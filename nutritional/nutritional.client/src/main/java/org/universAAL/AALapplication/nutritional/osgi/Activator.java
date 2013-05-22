/*
	Copyright 2011-2012 Itaca-TSB, http://www.tsb.upv.es
	Tecnologías para la Salud y el Bienestar
	
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
package org.universAAL.AALapplication.nutritional.osgi;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.universAAL.AALapplication.nutritional.Arranque;
import org.universAAL.AALapplication.nutritional.SharedResources;
import org.universAAL.AALapplication.nutritional.utils.Utils;
import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.container.osgi.uAALBundleContainer;
import org.universAAL.middleware.util.Constants;
import org.universAAL.ontology.profile.User;

public class Activator implements BundleActivator {

    SharedResources sr;
    private ModuleContext moduleContext;
    private Arranque arranque;
    static final User testUser = new User(
	    Constants.uAAL_MIDDLEWARE_LOCAL_ID_PREFIX + "saied");

    public void start(BundleContext context) throws Exception {
	Utils.println("STARTING NUTRITIONAL UI");
	moduleContext = uAALBundleContainer.THE_CONTAINER
		.registerModule(new Object[] { context });

	sr = new SharedResources(moduleContext);
	arranque = new Arranque();
	new Thread() {
	    public void run() {
		sr.start();
		arranque.inicializar(testUser.getLocalName());
	    }
	}.start();

	Utils.println("NUTRITIONAL UI STARTED");
    }

    public void stop(BundleContext context) throws Exception {
	Utils.println("STOPPING NUTRITIONAL UI");
    }

}
