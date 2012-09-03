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
package uAAL.simulation_services;

import na.miniDao.DayMenu;
import na.miniDao.Dish;
import na.miniDao.Meal;

import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.service.CallStatus;
import org.universAAL.middleware.service.ServiceCall;
import org.universAAL.middleware.service.ServiceCallee;
import org.universAAL.middleware.service.ServiceResponse;
import org.universAAL.middleware.service.owls.process.ProcessOutput;
import org.universAAL.ontology.nutrition.MenuDay;

import cache.SuperCache;

import uAAL.simulation_services.tests.OntoFactory;

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
		System.out.println("Constructor: getMenuDayCallee");
		/*
		 * // prepare for context publishing ContextProvider info = new
		 * ContextProvider(
		 * GetRecipeProvidedService.NUTRITIONAL_SERVER_NAMESPACE +
		 * "NutritionContextProvider");
		 * info.setType(ContextProviderType.controller); //
		 * info.setProvidedEvents(providedEvents(null)); cp = new
		 * DefaultContextPublisher(context, info);
		 */
		System.out.println("Constructor: ServiceGetMenuDayCallee done");
	}

	public ServiceResponse handleCall(ServiceCall call) {
		System.out.println("Handling call...");
		if (call == null)
			return null;

		String operation = call.getProcessURI();
		if (operation == null) {
			System.out.println("Operation is null, return null");
			return null;
		}

		if (operation.startsWith(ServiceGetTodayMenu.SERVICE_GET_TODAY_MENUDAY)) {
			/*
			 * Object input = call
			 * .getInputValue(ServiceGetTodayMenu.INPUT_GET_MENUDAY); if (input
			 * == null) { System.out.println("Input is null"); return null; }
			 * else { Integer i = (Integer) input;
			 * System.out.println("Input is: " + i); return getMenuDay(i); }
			 */
			return getMenuDay();
		}
		System.out.println("Returning null");
		return null;
	}

	private ServiceResponse getMenuDay() {
		System.out.println("Get menu... ask web service");
		ServiceResponse sr = new ServiceResponse(CallStatus.succeeded);
//		SuperCache sc = new SuperCache();
		
		//Create fake meal
		na.miniDao.Dish d = new Dish(4, "Paella", 3, null, null, 1, true, true);
		na.miniDao.Dish[] arrayDishes = {d};
		na.miniDao.Meal m = new Meal(2, "Lunch", arrayDishes); 
		na.miniDao.Meal[] arrayMeals = {m};
		
		na.miniDao.DayMenu daymenu = new DayMenu(1, 15, arrayMeals, 2, 0, 1, -1, -1, -1);
				
//		na.miniDao.DayMenu daymenu = (na.miniDao.DayMenu) sc
//				.getCachedObject("/cache/menuDay_56");

		if (daymenu != null) {
			System.out.println("Found recipe");
			MenuDay menuOntologic = OntoFactory.getMenuDay(daymenu);
			sr.addOutput(new ProcessOutput(
					ServiceGetTodayMenu.OUTPUT_GET_MENUDAY, menuOntologic));
		} else {
			System.out.println("No menu found");
		}
		return sr;
	}

	public void communicationChannelBroken() {
		System.out.println("Channel broken :(");
	}

}
