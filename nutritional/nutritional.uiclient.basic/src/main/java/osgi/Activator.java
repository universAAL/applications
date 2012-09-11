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
