package uAAL.simulation_services;

import org.universAAL.middleware.owl.MergedRestriction;
import org.universAAL.middleware.owl.OntologyManagement;
import org.universAAL.middleware.owl.SimpleOntology;
import org.universAAL.middleware.rdf.Resource;
import org.universAAL.middleware.rdf.impl.ResourceFactoryImpl;
import org.universAAL.middleware.service.owls.profile.ServiceProfile;
import org.universAAL.ontology.nutrition.MenuDay;
import org.universAAL.ontology.nutrition.NutritionOntology;
import org.universAAL.ontology.nutrition.NutritionService;

import java.util.Hashtable;

public class ServiceGetTodayMenu extends NutritionService {

	public static final String NUTRITIONAL_SERVER_NAMESPACE = NutritionOntology.NAMESPACE;
	// All the static Strings are used to unique identify special functions and
	// objects
	public static final String MY_URI = NUTRITIONAL_SERVER_NAMESPACE
			+ "GetMenuDayService"; // uri del servicio

	// parametros
	public static final String SERVICE_GET_MENUDAY = NutritionService.SERVICE_GET_TODAY_MENUDAY;
	public static final String OUTPUT_GET_MENUDAY = NutritionService.SERVICE_GET_TODAY_MENU_OUTPUT;
//	public static final String INPUT_GET_MENUDAY = NutritionService.SERVICE_GET_TODAY_MENU_INPUT;

	public static ServiceProfile[] profiles = new ServiceProfile[1];

	private static Hashtable serverNutritionalRestrictions = new Hashtable();

	static {
		OntologyManagement.getInstance().register(
				new SimpleOntology(MY_URI, NutritionService.MY_URI,
						new ResourceFactoryImpl() {
							@Override
							public Resource createInstance(String classURI,
									String instanceURI, int factoryIndex) {
								return new ServiceGetTodayMenu(instanceURI);
							}
						}));

		String[] ppControls = new String[] { NutritionService.PROP_OBTAINS_MENU };

		// The purpose of the rest of this static segment is to describe
		// services that we want to make available. We start with some
		// "class-level restrictions" that are inherent to the underlying
		// service component realized in the subpackage 'unit_impl'.

		// Before adding our own restrictions, we first "inherit" the
		// restrictions defined by the superclass
		addRestriction(
				(MergedRestriction) NutritionService
						.getClassRestrictionsOnProperty(
								NutritionService.MY_URI,
								NutritionService.PROP_OBTAINS_MENU).copy(),
				ppControls, serverNutritionalRestrictions);

		ServiceGetTodayMenu service = new ServiceGetTodayMenu(SERVICE_GET_MENUDAY);
		service.addOutput(OUTPUT_GET_MENUDAY, MenuDay.MY_URI, 1, 1,
				new String[] { NutritionService.PROP_OBTAINS_MENU });
		ServiceGetTodayMenu.profiles[0] = service.myProfile;

	}

	public ServiceGetTodayMenu(String url) {
		super(url);
	}

}
