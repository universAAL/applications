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
import org.universAAL.middleware.service.CallStatus;
import org.universAAL.middleware.service.ServiceCall;
import org.universAAL.middleware.service.ServiceCallee;
import org.universAAL.middleware.service.ServiceResponse;
import org.universAAL.middleware.service.owl.InitialServiceDialog;
import org.universAAL.middleware.service.owls.profile.ServiceProfile;
import org.universAAL.ontology.Shopping.*;

/**
 * @author dimokas
 * 
 */
public class ServiceProvider extends ServiceCallee {

    public static final String UI_SHOPPING_CLIENT_NAMESPACE = SharedResources.CLIENT_SHOPPING_UI_NAMESPACE + "ServiceProvider#";
    public static final String START_URI = UI_SHOPPING_CLIENT_NAMESPACE + "MainDialog";

    ServiceProvider(ModuleContext mc) {
    	super(mc, new ServiceProfile[] { InitialServiceDialog
    			.createInitialDialogProfile(FoodManagement.MY_URI,
				"http://www.certh.gr",
				"Food and Shopping UI Client", START_URI) });
    }

    public void communicationChannelBroken() {
    }

    public ServiceResponse handleCall(ServiceCall call) {
		if (call != null) {
		    String operation = call.getProcessURI();
		    if (operation != null && operation.startsWith(START_URI)) {
				System.out.println("-- Food and Shopping UI Client Main Menu --");
				SharedResources.uIProvider.startMainDialog();
				ServiceResponse sr = new ServiceResponse(CallStatus.succeeded);
				return sr;
		    }
		}
		return null;
    }
}