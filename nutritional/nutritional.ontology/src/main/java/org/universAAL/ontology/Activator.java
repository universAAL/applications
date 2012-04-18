package org.universAAL.ontology;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.universAAL.middleware.owl.OntologyManagement;
import org.universAAL.ontology.nutrition.DayOfWeek;
import org.universAAL.ontology.nutrition.Dish;
import org.universAAL.ontology.nutrition.DishCategory;
import org.universAAL.ontology.nutrition.Food;
import org.universAAL.ontology.nutrition.FoodCategory;
import org.universAAL.ontology.nutrition.FoodSubCategory;
import org.universAAL.ontology.nutrition.Ingredient;
import org.universAAL.ontology.nutrition.Meal;
import org.universAAL.ontology.nutrition.MealCategory;
import org.universAAL.ontology.nutrition.MeasureUnit;
import org.universAAL.ontology.nutrition.MenuDay;
import org.universAAL.ontology.nutrition.NutritionOntology;
import org.universAAL.ontology.nutrition.NutritionService;
import org.universAAL.ontology.nutrition.Recipe;
import org.universAAL.ontology.nutrition.ShoppingList;

//You need an Activator in your ontology bundle because it must be started...
public class Activator implements BundleActivator {

    static BundleContext context = null;

    NutritionOntology nutritionOntology = new NutritionOntology();

    public void start(BundleContext context) throws Exception {
	Activator.context = context;
	OntologyManagement.getInstance().register(nutritionOntology);
    }

    public void stop(BundleContext arg0) throws Exception {
	OntologyManagement.getInstance().unregister(nutritionOntology);
    }
    
    /*
    public void start(BundleContext context) throws Exception {
		Activator.context = context;
		System.out.println("Nutrition ontology starting...");
		// Every class of the Nutrition ontology, order matters
		// enumerations first
		Class.forName(DishCategory.class.getName());
		Class.forName(FoodCategory.class.getName());
		Class.forName(FoodSubCategory.class.getName());
		Class.forName(DayOfWeek.class.getName());
		Class.forName(MealCategory.class.getName());
		
		// concepts
		Class.forName(Food.class.getName());
		Class.forName(MeasureUnit.class.getName());
		Class.forName(Ingredient.class.getName());
		Class.forName(ShoppingList.class.getName());
		Class.forName(Recipe.class.getName());
		Class.forName(Dish.class.getName());
		Class.forName(Meal.class.getName());
		Class.forName(MenuDay.class.getName());
		
		// service
		Class.forName(NutritionService.class.getName());
		
		System.out.println("Nutrition ontology started");
    }

    public void stop(BundleContext arg0) throws Exception {
	// You don´t need to do anything here...
    	System.out.println("Nutrition ontology bundle stop.");
    }
    */
}
