package na.aItem;


import na.oasisUtils.ami.AmiConnector;
import na.oasisUtils.trustedSecurityNetwork.TSFConnector;
import na.utils.OASIS_ServiceUnavailable;
import na.utils.ServiceInterface;
import na.utils.Utils;

import org.osgi.framework.BundleContext;
import org.universAAL.middleware.context.ContextPublisher;
import org.universAAL.middleware.context.DefaultContextPublisher;
import org.universAAL.middleware.context.owl.ContextProvider;
import org.universAAL.middleware.context.owl.ContextProviderType;
import org.universAAL.middleware.service.CallStatus;
import org.universAAL.middleware.service.ServiceCall;
import org.universAAL.middleware.service.ServiceCallee;
import org.universAAL.middleware.service.ServiceResponse;
import org.universAAL.middleware.service.owls.process.ProcessOutput;
import org.universAAL.ontology.nutrition.Recipe;

public class GetRecipeCallee extends ServiceCallee{

private static final ServiceResponse invalidInput = new ServiceResponse(CallStatus.serviceSpecificFailure);
	
	private ContextPublisher cp;

	static {
		invalidInput.addOutput(new ProcessOutput(ServiceResponse.PROP_SERVICE_SPECIFIC_ERROR,"Invalid input!"));
	}
	
	public GetRecipeCallee(BundleContext context) {
		// The parent need to know the profiles of the available functions to register them
		super(context, GetRecipeProvidedService.profiles);
		
		// prepare for context publishing
		ContextProvider info =  new ContextProvider(
				GetRecipeProvidedService.NUTRITIONAL_SERVER_NAMESPACE + "NutritionContextProvider");
		info.setType(ContextProviderType.controller);
		cp = new DefaultContextPublisher(context, info);
		
		Utils.println("Constructor: getRecipeServiceProvider");
	}

	public void communicationChannelBroken() {
		Utils.println("Channel broken :(");
	}

	public ServiceResponse handleCall(ServiceCall call) {
		Utils.println("Handling call...");
		if (call == null)
			return null;
		
		String operation = call.getProcessURI();
		if (operation == null)
			return null;
		
		if (operation.startsWith(GetRecipeProvidedService.SERVICE_GET_RECIPE))
			return getRecipe();
		
		return null;
	}

	private ServiceResponse getRecipe() {
		Utils.println("Get recipe... ask web service");
		try {
			ServiceResponse sr = new ServiceResponse(CallStatus.succeeded);
			String[] input = {TSFConnector.getInstance().getToken(), String.valueOf(22)};
			AmiConnector ami = AmiConnector.getAMI();
			na.miniDao.Recipe recipe;
			
				recipe = (na.miniDao.Recipe)ami.invokeOperation(ServiceInterface.DOMAIN_Nutrition, ServiceInterface.OP_GetUserRecipe, input, false);
			
	
			// create and add a ProcessOutput-Event that binds the output URI to the created list of lamps
	//		Recipe recipe = new Recipe();
	//		recipe.setName("La receta de Héctor y Juan");
	//		recipe.setDishCategory(DishCategory.VALUE_FIRST_COURSE);
	//		recipe.setFavourite(false);
	//		List<Ingredient> list = new ArrayList<Ingredient>();
	//		Ingredient ing = new Ingredient();
	//			ing.setID(21);
	//			ing.setQuantity("500");
	//		list.add(ing);
	//		Ingredient[] ingredients = list.toArray(new Ingredient[list.size()]);
	//		recipe.setIngredients(ingredients);
	//		
			if (recipe!=null) {
				Recipe recipeOntologic = OntoFactory.getRecipe(recipe);
				sr.addOutput(new ProcessOutput(GetRecipeProvidedService.OUTPUT_GET_RECIPE, recipeOntologic));
			} else {
				Utils.println("No recipe found");
			}
			return sr;
		} catch (OASIS_ServiceUnavailable e) {
			e.printStackTrace();
		}
		return null;
	}

}
