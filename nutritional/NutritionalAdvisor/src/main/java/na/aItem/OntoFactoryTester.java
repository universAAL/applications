package na.aItem;

import java.rmi.RemoteException;

import org.universAAL.ontology.nutrition.Dish;
import org.universAAL.ontology.nutrition.FoodCategory;
import org.universAAL.ontology.nutrition.FoodSubCategory;
import org.universAAL.ontology.nutrition.Ingredient;
import org.universAAL.ontology.nutrition.Meal;
import org.universAAL.ontology.nutrition.MenuDay;
import org.universAAL.ontology.nutrition.Recipe;

import na.miniDao.DayMenu;
import na.miniDao.FoodItem;
import na.miniDao.ShoppingList;
import na.oasisUtils.trustedSecurityNetwork.TSFConnector;
import na.utils.Utils;
import na.ws.NutriSecurityException;
import na.ws.NutritionalAdvisorProxy;

public class OntoFactoryTester {

	public void test_ontoFactory() {
    	try {
    		Utils.println("Start testing");		
			NutritionalAdvisorProxy ws = new NutritionalAdvisorProxy();
			
			String token;
			token = ws.getToken("David_Shopland", "5a019bc5dad45f1652c141bc9004598b", 2);
			Utils.println("Token is: "+token);
			TSFConnector.getInstance().setToken(token);

			Utils.println("Test: foodCategory");
			int ID = 1;
			FoodCategory foodCat = OntoFactory.getFoodCategory(ID);
			if (foodCat!=null) {
				Utils.println("Found: "+foodCat.getName());
			} else {
				Utils.println("Not found with ID = "+ ID);
			}
			ID = 2;
			foodCat = OntoFactory.getFoodCategory(ID);
			if (foodCat!=null) {
				Utils.println("Found: "+foodCat.getName());
			} else {
				Utils.println("Not found with ID = "+ ID);
			}
			
			ID = 14;
			foodCat = OntoFactory.getFoodCategory(ID);
			if (foodCat!=null) {
				Utils.println("Found: "+foodCat.getName());
			} else {
				Utils.println("Not found with ID = "+ ID);
			}
			
			Utils.println("Test: foodSubCategory");
			ID = 3;
			FoodSubCategory foodSubCat = OntoFactory.getFoodSubCategory(ID);
			if (foodSubCat!=null) {
				Utils.println("Found: "+foodSubCat.getName());
			} else {
				Utils.println("Not found with ID = "+ ID);
			}
			
			ID = 163;
			foodSubCat = OntoFactory.getFoodSubCategory(ID);
			if (foodSubCat!=null) {
				Utils.println("Found: "+foodSubCat.getName());
			} else {
				Utils.println("Not found with ID = "+ ID);
			}
			
			ID = 200;
			foodSubCat = OntoFactory.getFoodSubCategory(ID);
			if (foodSubCat!=null) {
				Utils.println("Found: "+foodSubCat.getName());
			} else {
				Utils.println("Not found with ID = "+ ID);
			}
			
			Utils.println("Test: getRecipe by ID");
			ID = 163;
			Recipe recipe = OntoFactory.getRecipeFromID(ID);
			if (recipe!=null) {
				Utils.println("Found: "+recipe.getName());
				Ingredient[] ings =  recipe.getIngredients();
				for (Ingredient ingredient : ings) {
					if (ingredient!=null && ingredient.getFood()!=null)
						Utils.println("Ingredient: "+ingredient.getQuantity()+ " "+ ingredient.getFood().getName() + ingredient.getMeasureUnit().getType());
					if (ingredient!=null && ingredient.getMeasureUnit()!=null)
						Utils.println("Ingredient: "+ ingredient.getMeasureUnit().getType());
				}
			} else {
				Utils.println("Not found with ID = "+ ID);
			}
			
			ID = 10003;
			recipe = OntoFactory.getRecipeFromID(ID);
			if (recipe!=null) {
				Utils.println("Found: "+recipe.getName());
			} else {
				Utils.println("Not found with ID = "+ ID);
			}
			
			Utils.println("Test: todayMenu");
			DayMenu today =  ws.getTodayMenu(token);
			MenuDay menu = OntoFactory.getMenuDay(today);
			if (menu!=null) {
				Utils.println("Found menu: "+menu.getID());
				for (Meal meal : menu.getMeals()) {
					Utils.println(" meal: "+meal.getMealCategory().name());
					for (Dish dish : meal.getDishes()) {
						Utils.println("  dish: "+dish.getName());
					}
				}
			} else {
				Utils.println("Not found menu for today :(");
			}
			
			Utils.println("Test: shopping list");
			ShoppingList shop = ws.getCustomShoppingListDays(token, 1, Utils.Dates.getMyCalendar().DAY_OF_WEEK);
			if (shop!=null) {
				Utils.println("Found shopping list: "+ shop.getItems().length + " items");
				int i = 0;
				for (FoodItem item : shop.getItems()) {
					Utils.println(" item: " + i + " " + item.getFoodID() + " " + item.getName());
					i++;
				}
			} else {
				Utils.println("Not found shopping list :(");
			}
			
			Utils.println("End testing.");
    	} catch (NutriSecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.exit(0);
	}

}
