/*
	Copyright 2008-2010 Fraunhofer IGD, http://www.igd.fraunhofer.de
	Fraunhofer-Gesellschaft - Institute of Computer Graphics Research 
	
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
package na;

import na.aItem.GetRecipeCallee;
import na.utils.Utils;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.service.log.LogService;
import org.universAAL.middleware.util.Constants;
import org.universAAL.ontology.profile.ElderlyUser;



public class Activator implements BundleActivator {

//	http://ontology.persona.ima.igd.fhg.de/Lighting.owl#Lighting
	public static final String CLIENT_NUTRITIONAL_UI_NAMESPACE = "urn:uaalapplications.nutritional.uiclient:";

	
	static LogService log;
	static ServiceProvider serviceProvider;
//	static InputConsumer inputConsumer;

	static GetRecipeCallee recipeProvider;
	
	static final ElderlyUser testUser = new ElderlyUser(Constants.uAAL_MIDDLEWARE_LOCAL_ID_PREFIX + "saied");


	private static BundleContext CONTEXT;


	

	
	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	public void start(final BundleContext context) throws Exception {
		Utils.println("bundle starting");
		log = (LogService) context.getService(
				context.getServiceReference(LogService.class.getName()));
		Activator.serviceProvider = new ServiceProvider(context);
		Activator.recipeProvider = new GetRecipeCallee(context);
		CONTEXT = context;
		Utils.println("bundle started");
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		Utils.println("stopped");
	}
	
	public static BundleContext getBundleContext() {
		return CONTEXT;
	}
}
