package uAAL.simulation_services;

import org.universAAL.middleware.owl.MergedRestriction;
import org.universAAL.middleware.owl.OntologyManagement;
import org.universAAL.middleware.owl.SimpleOntology;
import org.universAAL.middleware.rdf.Resource;
import org.universAAL.middleware.rdf.TypeMapper;
import org.universAAL.middleware.rdf.impl.ResourceFactoryImpl;
import org.universAAL.middleware.service.owls.profile.ServiceProfile;
import org.universAAL.ontology.nutrition.NutritionOntology;
import org.universAAL.ontology.nutrition.NutritionService;
import org.universAAL.ontology.nutrition.Recipe;

import java.util.Hashtable;

public class ServiceGetRecipe extends NutritionService {

	public static final String NUTRITIONAL_SERVER_NAMESPACE = NutritionOntology.NAMESPACE;
	// All the static Strings are used to unique identify special functions and
	// objects
	public static final String MY_URI = NUTRITIONAL_SERVER_NAMESPACE
			+ "GetRecipeService"; // uri del servicio

	// parametros
	public static final String SERVICE_GET_RECIPE = NutritionService.SERVICE_GET_RECIPE;
	public static final String OUTPUT_GET_RECIPE = NutritionService.SERVICE_GET_RECIPE_OUTPUT;
	public static final String INPUT_GET_RECIPE = NutritionService.SERVICE_GET_RECIPE_INPUT;

	public static ServiceProfile[] profiles = new ServiceProfile[1];

	private static Hashtable serverNutritionalRestrictions = new Hashtable();

	static {
		OntologyManagement.getInstance().register(
				new SimpleOntology(MY_URI, NutritionService.MY_URI,
						new ResourceFactoryImpl() {
							@Override
							public Resource createInstance(String classURI,
									String instanceURI, int factoryIndex) {
								return new ServiceGetRecipe(instanceURI);
							}
						}));
		

		String[] ppControls = new String[] { NutritionService.PROP_OBTAINS_RECIPE };

		// The purpose of the rest of this static segment is to describe
		// services that we want to make available. We start with some
		// "class-level restrictions" that are inherent to the underlying
		// service component realized in the subpackage 'unit_impl'. That is, we
		// know from unit_impl.MyLighting.java that
		// 1. it controls lamps
		// 2. that can only be switched on and off

		// Before adding our own restrictions, we first "inherit" the
		// restrictions defined by the superclass
		addRestriction(
				(MergedRestriction) NutritionService
						.getClassRestrictionsOnProperty(
								NutritionService.MY_URI,
								NutritionService.PROP_OBTAINS_RECIPE).copy(),
				ppControls, serverNutritionalRestrictions);

		ServiceGetRecipe service = new ServiceGetRecipe(SERVICE_GET_RECIPE);
		service.addFilteringInput(INPUT_GET_RECIPE,
				TypeMapper.getDatatypeURI(Integer.class), 1, 1, new String[] {
						NutritionService.PROP_OBTAINS_RECIPE, Recipe.PROP_ID });
		service.addOutput(OUTPUT_GET_RECIPE, Recipe.MY_URI, 1, 1, new String[] {
				NutritionService.PROP_OBTAINS_RECIPE });
		ServiceGetRecipe.profiles[0] = service.myProfile;

	}

	public ServiceGetRecipe(String url) {
		super(url);
	}

}
