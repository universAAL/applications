/*
	Copyright 2011-2012 Itaca-TSB, http://www.tsb.upv.es
	Tecnolog�as para la Salud y el Bienestar
	
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
package org.universAAL.AALapplication.nutritional.services;

import na.oasisUtils.ami.AmiConnector;
import na.oasisUtils.trustedSecurityNetwork.TSFConnector;
import na.utils.OASIS_ServiceUnavailable;
import na.utils.ServiceInterface;
import na.utils.Utils;
import na.utils.cache.SuperCache;

import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.service.CallStatus;
import org.universAAL.middleware.service.ServiceCall;
import org.universAAL.middleware.service.ServiceCallee;
import org.universAAL.middleware.service.ServiceResponse;
import org.universAAL.middleware.service.owls.process.ProcessOutput;
import org.universAAL.ontology.nutrition.MenuDay;

public class ServiceGetTodayMenuCallee extends ServiceCallee {

    private static final ServiceResponse invalidInput = new ServiceResponse(
	    CallStatus.serviceSpecificFailure);

    static {
	invalidInput.addOutput(new ProcessOutput(
		ServiceResponse.PROP_SERVICE_SPECIFIC_ERROR, "Invalid input!"));
    }

    public ServiceGetTodayMenuCallee(ModuleContext context) {
	// The parent need to know the profiles of the available functions to
	// register them
    
	super(context, ServiceGetTodayMenu.profiles);
	System.out.println("SERVICE CALLEEEEEE 2");
	Utils.println("Constructor: getMenuDayCallee");
	
	 // prepare for context publishing ContextProvider info = new
//	 ContextProvider(
//	 GetRecipeProvidedService.NUTRITIONAL_SERVER_NAMESPACE +
//	 "NutritionContextProvider");
//	 info.setType(ContextProviderType.controller); //
//	 info.setProvidedEvents(providedEvents(null)); cp = new
//	 DefaultContextPublisher(context, info);
	 
	Utils.println("Constructor: ServiceGetMenuDayCallee done");
	System.out.println("SERVICE CALLEEEEEE 3");
    }

    public ServiceResponse handleCall(ServiceCall call) {
	Utils.println("Handling call...");
	if (call == null)
	    return null;

	String operation = call.getProcessURI();
	if (operation == null) {
	    Utils.println("Operation is null, return null");
	    return null;
	}

	if (operation.startsWith(ServiceGetTodayMenu.SERVICE_GET_TODAY_MENUDAY)) {
	    /*
	     * Object input = call
	     * .getInputValue(ServiceGetTodayMenu.INPUT_GET_MENUDAY); if (input
	     * == null) { Utils.println("Input is null"); return null; } else {
	     * Integer i = (Integer) input; Utils.println("Input is: " + i);
	     * return getMenuDay(i); }
	     */
	    return getMenuDay();
	}
	Utils.println("Returning null");
	return null;
    }

    private ServiceResponse getMenuDay() {
	Utils.println("Get menu... ask web service");
	try {
	    ServiceResponse sr = new ServiceResponse(CallStatus.succeeded);
	    String[] input = { TSFConnector.getInstance().getToken() };
	    AmiConnector ami = AmiConnector.getAMI();
	    na.miniDao.DayMenu daymenu;

	    daymenu = (na.miniDao.DayMenu) ami.invokeOperation(
		    ServiceInterface.DOMAIN_Nutrition,
		    ServiceInterface.OP_GetTodayMenu, input, false);

	    if (daymenu != null) {
		SuperCache sc = new SuperCache();
		sc.storeObject(daymenu, "menuDay_" + daymenu.getUsersMenusID());
		Utils.println("Found recipe");
		MenuDay menuOntologic = OntoFactory.getMenuDay(daymenu);
		sr.addOutput(new ProcessOutput(
			ServiceGetTodayMenu.OUTPUT_GET_MENUDAY, menuOntologic));
	    } else {
		Utils.println("No menu found");
	    }
	    return sr;
	} catch (OASIS_ServiceUnavailable e) {
	    e.printStackTrace();
	}
	return null;
    }

    public void communicationChannelBroken() {
	Utils.println("Channel broken :(");
    }

}
