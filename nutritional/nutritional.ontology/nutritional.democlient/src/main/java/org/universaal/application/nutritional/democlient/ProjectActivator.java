package org.universaal.application.nutritional.democlient;

import java.util.List;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.container.osgi.uAALBundleContainer;
import org.universAAL.middleware.rdf.Resource;
import org.universAAL.middleware.service.CallStatus;
import org.universAAL.middleware.service.DefaultServiceCaller;
import org.universAAL.middleware.service.ServiceRequest;
import org.universAAL.middleware.service.ServiceResponse;
import org.universAAL.ontology.nutrition.DishCategory;
import org.universAAL.ontology.nutrition.Ingredient;
import org.universAAL.ontology.nutrition.MenuDay;
import org.universAAL.ontology.nutrition.NutritionService;
import org.universAAL.ontology.nutrition.Recipe;


public class ProjectActivator implements BundleActivator {

	static ModuleContext context;

	public void start(BundleContext arg0) throws Exception {	
		context = uAALBundleContainer.THE_CONTAINER
                .registerModule(new Object[] {arg0});	
		context.logDebug("Initialising Project", null);

		/*
		 * uAAL stuff
		 */
		context.logInfo("Project started", null);
		
		
		System.out.println("Tester: starting bundle");
				DefaultServiceCaller caller = new DefaultServiceCaller(context);
		
		ServiceRequest service = new ServiceRequest( new NutritionService(), null);
		
		//getRecipe with ID 30
		service.addValueFilter(new String[] { NutritionService.PROP_OBTAINS_RECIPE, Recipe.PROP_ID },
				new Integer(30));
		service.addRequiredOutput(NutritionService.SERVICE_GET_RECIPE_OUTPUT, 
				new String[] { NutritionService.PROP_OBTAINS_RECIPE });
		 ServiceResponse sr = caller.call(service);
		 if (sr.getCallStatus() == CallStatus.succeeded) {
			 System.out.println("Call status succeed!");
			 List l = sr.getOutput(NutritionService.SERVICE_GET_RECIPE_OUTPUT, true);
			 if (l==null) {
				 System.out.println("I recieved null :(");
			 } else {
				Resource res = (Resource) l.get(0);
				 System.out.println("Found:: "+res.getLocalName()+" "+res.getResourceLabel() + " "+res.getType());
				 Recipe r = (Recipe) l.get(0);
				 if (r!=null) {
					 System.out.println("Recipe name: "+r.getName());
					 System.out.println("Recipe procedure: "+r.getProcedure());
					 DishCategory dc = r.getDishCategory();
					 if (dc!=null)
						 System.out.println("Recipe category: "+r.getDishCategory().ord() + " " + r.getDishCategory().name());
					 else
						 System.out.println("Recipe category is null :(");
					 if (r.getIngredients()!=null && r.getIngredients().length>0) {
						 for (Ingredient ing : r.getIngredients()) {
							System.out.println("Ingredient: "+ing);
						}
					 } else {
						 System.out.println("Ingredients is null :(");
					 }
				 } else {
					 System.out.println("Recipe is null :(");
				 }
			 }
		 } else {
			 System.out.println("There was an error! "+sr.getCallStatus().getLocalName());
		 }
		 
		
		// getTodayMenu
		 ServiceRequest serviceMenu = new ServiceRequest( new NutritionService(), null);
		 serviceMenu.addRequiredOutput(NutritionService.SERVICE_GET_TODAY_MENU_OUTPUT, 
				new String[] { NutritionService.PROP_OBTAINS_MENU });
		 sr = caller.call(serviceMenu);
		 if (sr.getCallStatus() == CallStatus.succeeded) {
			 System.out.println("Call status succeed!");
			 List l = sr.getOutput(NutritionService.SERVICE_GET_TODAY_MENU_OUTPUT, true);
			 if (l==null) {
				 System.out.println("I recieved null :(");
			 } else {
				Resource res = (Resource) l.get(0);
				 System.out.println("Found: "+res.getLocalName()+" "+res.getResourceLabel() + " "+res.getType());
				 MenuDay m = (MenuDay) l.get(0);
				 if (m!=null) {
					 // use MenuDay getters to access properties
				 } else {
					 System.out.println("MenuDay is null :(");
				 }
			 }
		 } else {
			 System.out.println("Eerror! "+sr.getCallStatus().getLocalName());
		 }
		
		System.out.println("Bundle started and done");
	}


	public void stop(BundleContext arg0) throws Exception {
		// TODO Auto-generated method stub

	}

}
