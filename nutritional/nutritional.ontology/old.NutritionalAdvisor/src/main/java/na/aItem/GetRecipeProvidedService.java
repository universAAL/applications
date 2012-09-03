package na.aItem;


import org.universAAL.middleware.owl.Restriction;
import org.universAAL.middleware.service.owls.profile.ServiceProfile;
import org.universAAL.ontology.nutrition.NutritionService;
import org.universAAL.ontology.nutrition.Recipe;

import java.util.Hashtable;

public class GetRecipeProvidedService extends NutritionService {

	public static final String NUTRITIONAL_SERVER_NAMESPACE = NutritionService.NUTRITIONAL_SERVER_NAMESPACE;
	// All the static Strings are used to unique identify special functions and objects
	public static final String MY_URI = NUTRITIONAL_SERVER_NAMESPACE + "GetRecipeService";
	
	public static final String SERVICE_GET_RECIPE 			= NutritionService.SERVICE_GET_RECIPE;
	
	static final String OUTPUT_GET_RECIPE = NutritionService.OUTPUT_GET_RECIPE;
	
	public static ServiceProfile[] profiles = new ServiceProfile[1];
	private static Hashtable serverLevelRestrictions = new Hashtable();
	
	static {
		register(GetRecipeProvidedService.class);
		
		String[] ppControls = new String[] {NutritionService.PROP_GET_RECIPE};
		
		addRestriction((Restriction)
				NutritionService.getClassRestrictionsOnProperty(NutritionService.PROP_GET_RECIPE).copy(),
				ppControls,
				serverLevelRestrictions);
		
		GetRecipeProvidedService nuevo = new GetRecipeProvidedService(SERVICE_GET_RECIPE);
		nuevo.addOutput(OUTPUT_GET_RECIPE, Recipe.MY_URI, 1, 1, ppControls);
		GetRecipeProvidedService.profiles[0] = nuevo.myProfile;
	}
	
	
	public GetRecipeProvidedService(String url) {
		super(url);
	}

}
