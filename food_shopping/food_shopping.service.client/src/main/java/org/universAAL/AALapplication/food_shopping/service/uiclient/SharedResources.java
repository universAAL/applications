/*****************************************************************************************
 * Copyright 2012 CERTH, http://www.certh.gr - Center for Research and Technology Hellas
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *****************************************************************************************/

package org.universAAL.AALapplication.food_shopping.service.uiclient;

import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.util.Constants;
import org.universAAL.ontology.profile.User;

/**
 * @author dimokas
 * 
 */
public class SharedResources {

    public static final String CLIENT_SHOPPING_UI_NAMESPACE = "urn:shopping";

    public static ModuleContext moduleContext;

    public static ServiceProvider serviceProvider;
    public static UIProvider uIProvider;

    //public static final AssistedPerson testUser = new AssistedPerson(Constants.uAAL_MIDDLEWARE_LOCAL_ID_PREFIX + "saied");

    public static User testUser = new User(Constants.uAAL_MIDDLEWARE_LOCAL_ID_PREFIX + "saied");
    
    public SharedResources(ModuleContext context) {
    	moduleContext = context;
    }
    
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