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

import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.service.CallStatus;
import org.universAAL.middleware.service.ServiceCall;
import org.universAAL.middleware.service.ServiceCallee;
import org.universAAL.middleware.service.ServiceResponse;
import org.universAAL.middleware.service.owls.process.ProcessOutput;
import org.universAAL.ontology.nutrition.Recipe;

public class ServiceGetRecipeCallee extends ServiceCallee {

    private static final ServiceResponse invalidInput = new ServiceResponse(
	    CallStatus.serviceSpecificFailure);

    static {
	invalidInput.addOutput(new ProcessOutput(
		ServiceResponse.PROP_SERVICE_SPECIFIC_ERROR, "Invalid input!"));
    }

    /*
     * LightingProvider(ModuleContext context) { // as a service providing
     * component, we have to extend ServiceCallee // this in turn requires that
     * we introduce which services we would like // to // provide to the
     * universAAL-based AAL Space super(context,
     * ProvidedLightingService.profiles);
     * 
     * // this is just an example that wraps a faked "original server" theServer
     * = new MyLighting();
     * 
     * // prepare for context publishing ContextProvider info = new
     * ContextProvider( ProvidedLightingService.LIGHTING_SERVER_NAMESPACE +
     * "LightingContextProvider"); info.setType(ContextProviderType.controller);
     * info.setProvidedEvents(providedEvents(theServer)); cp = new
     * DefaultContextPublisher(context, info);
     * 
     * // now we are ready to listen to the changes on the server side
     * theServer.addListener(this); }
     */

    public ServiceGetRecipeCallee(ModuleContext context) {
	// The parent need to know the profiles of the available functions to
	// register them
	super(context, ServiceGetRecipe.profiles);
	Utils.println("Constructor: getRecipeCallee");
	/*
	 * // prepare for context publishing ContextProvider info = new
	 * ContextProvider(
	 * GetRecipeProvidedService.NUTRITIONAL_SERVER_NAMESPACE +
	 * "NutritionContextProvider");
	 * info.setType(ContextProviderType.controller); //
	 * info.setProvidedEvents(providedEvents(null)); cp = new
	 * DefaultContextPublisher(context, info);
	 */
	Utils.println("Constructor: getRecipeServiceProvider done");
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

	if (operation.startsWith(ServiceGetRecipe.SERVICE_GET_RECIPE)) {
	    Object input = call
		    .getInputValue(ServiceGetRecipe.INPUT_GET_RECIPE);
	    if (input == null) {
		Utils.println("Input is null");
		return null;
	    } else {
		Integer i = (Integer) input;
		Utils.println("Input is: " + i);
		return getRecipe(i);
	    }
	}
	Utils.println("Returning null");
	return null;
    }

    private ServiceResponse getRecipe(int recipeID) {
	try {
	    ServiceResponse sr = new ServiceResponse(CallStatus.succeeded);
	    String[] input = { TSFConnector.getInstance().getToken(),
		    String.valueOf(recipeID) };
	    AmiConnector ami = AmiConnector.getAMI();
	    na.miniDao.Recipe recipe;

	    recipe = (na.miniDao.Recipe) ami.invokeOperation(
		    ServiceInterface.DOMAIN_Nutrition,
		    ServiceInterface.OP_GetUserRecipe, input, false);

	    // create and add a ProcessOutput-Event that binds the output URI to
	    // the created list of lamps
	    // Recipe recipe = new Recipe();
	    // recipe.setName("La receta de H�ctor y Juan");
	    // recipe.setDishCategory(DishCategory.VALUE_FIRST_COURSE);
	    // recipe.setFavourite(false);
	    // List<Ingredient> list = new ArrayList<Ingredient>();
	    // Ingredient ing = new Ingredient();
	    // ing.setID(21);
	    // ing.setQuantity("500");
	    // list.add(ing);
	    // Ingredient[] ingredients = list.toArray(new
	    // Ingredient[list.size()]);
	    // recipe.setIngredients(ingredients);
	    //
	    if (recipe != null) {
		// SuperCache sc = new SuperCache();
		// sc.storeObject(recipe, "recipe_"+recipeID);
		Utils.println("Found recipe");
		Recipe recipeOntologic = OntoFactory.getRecipe(recipe);
		sr.addOutput(new ProcessOutput(
			ServiceGetRecipe.OUTPUT_GET_RECIPE, recipeOntologic));
	    } else {
		Utils.println("No recipe found");
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
