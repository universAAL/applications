package na.aItem;

import java.util.ArrayList;
import java.util.List;

import na.miniDao.FoodItem;
import na.miniDao.RecipeIngredient;
import na.utils.Utils;

import org.universAAL.ontology.nutrition.Dish;
import org.universAAL.ontology.nutrition.Food;
import org.universAAL.ontology.nutrition.FoodCategory;
import org.universAAL.ontology.nutrition.Ingredient;
import org.universAAL.ontology.nutrition.Meal;
import org.universAAL.ontology.nutrition.MeasureUnit;
import org.universAAL.ontology.nutrition.MenuDay;
import org.universAAL.ontology.nutrition.Recipe;
import org.universAAL.ontology.nutrition.ShoppingList;

public class OntoFactory {
	
	/**
	 * Gets a ontological representation of a recipe.
	 * Does not contain the image.
	 *
	 * @param recipe the recipe
	 * @return the onto recipe
	 */
	public static Recipe getRecipe(na.miniDao.Recipe recipe) {
		Utils.println("getOntoRecipe...");
		if (recipe==null)
			return null;
		Recipe or = new Recipe();
		// ID
		or.setID(recipe.getRecipeID());
		// NAME
		if (recipe.getCourse()!=null)
			or.setName(recipe.getCourse());
		// PROCEDURE
		if (recipe.getProcedure()!=null)
			or.setProcedure(recipe.getProcedure());
		// FAVOURITE
		or.setFavourite(recipe.isFavouriteRecipe());
		// DISH_CATEGORY
		try {
			if (recipe.getDishCategory()!=null && recipe.getDishCategory().length()>0) {
				int val = new Integer(recipe.getDishCategory()).intValue();
				or.setDishCategory(val);
			}
		} catch (NumberFormatException n) {
			n.printStackTrace();
		}
		// PICTURE
		if (recipe.getPicture()!=null && recipe.getPicture().length()>0) {
			or.setPicture(recipe.getPicture());
		}
		// INGREDIENTS
		Utils.println("Processing ingredients");
		if (recipe.getRecipeIngredients()!=null && recipe.getRecipeIngredients().length>0) {
			List<Ingredient> list = new ArrayList<Ingredient>(); 
			for (RecipeIngredient ri : recipe.getRecipeIngredients()) {
				Ingredient ing = OntoFactory.getIngredient(ri);
				if (ing!=null) {
					Utils.println("Adding: "+ing);
					list.add(ing);
				}
			}
			or.setIngredients(list.toArray(new Ingredient[list.size()]));
		} else {
			Utils.println("Recipe with no ingredients");
		}
		
		return or;
	}
	
	public static Ingredient getIngredient(RecipeIngredient ref) {
		if (ref==null)
			return null;
		Ingredient ing = new Ingredient();
		// ID
		ing.setID(ref.getFoodID());
		String q = ""+ ref.getQuantity();
		// QUANTITY
		ing.setQuantity(q);
		// MEASURE UNIT
		MeasureUnit mu = MeasureUnit.getMeasureUnitByOrder(ref.getMeasUnitsID());
		ing.setMeasureUnit(mu);
		// FOOD
		Food f = OntoFactory.getFood(null);
		ing.setFood(f);
		
		return ing;
	}
	
	public static Ingredient getIngredient(FoodItem ref) {
		if (ref==null)
			return null;
		Ingredient ing = new Ingredient();
		// ID
		ing.setID(ref.getFoodID());
		String q = ""+ ref.getAmount();
		// QUANTITY
		ing.setQuantity(q);
		// MEASURE UNIT
		MeasureUnit mu = MeasureUnit.getUnitsByValue(ref.getUnits());
		ing.setMeasureUnit(mu);
		// FOOD
		Food f = OntoFactory.getFood(null);
		ing.setFood(f);
		
		return ing;
	}
	
	public static Food getFood(na.miniDao.FoodItem ref) {
		if (ref==null)
			return null;
		Food f = new Food();
//		ref.get
		// ID
		f.setID(ref.getFoodID());
		// NAME
		f.setName(ref.getName());
		// CARBOHYDRATES
		f.setCarbohydrates(-1); //TODO
		// FAT
		f.setFat(-1);
		// KCAL
		f.setKilocalories(-1);
		// FOOD SUBCATEGORY
//		f.setFoodCategory(foodSubCategory); //TODO
		// FOOD CATEGORY
		
		return f;
	}
	
	public static FoodCategory getFoodCategory(na.miniDao.FoodCategory ref) {
		if (ref==null)
			return null;
		FoodCategory fc = new FoodCategory();
		// ID
		fc.setID(ref.getID());
		// NAME
		fc.setName(ref.getDescription());
		return fc;
	}
	
	public static Dish getDish(na.miniDao.Dish ref) {
		if (ref==null)
			return null;
		Dish d = new Dish();
		// ID
		d.setID(ref.getRecipeID());
		// NAME
		d.setName(ref.getDescription());
		// PICTURE
		d.setPicture(ref.getImage());
		// HAS PROCEDURE
		d.setProcedure(ref.isHasProcedure());
		// RECIPE
		d.setRecipe(OntoFactory.getRecipeFromID(ref.getRecipeID()));
		// DISH CATEGORY
		d.setDishCategory(ref.getDishCategory());
		// CONTAINS RECIPE
		d.setContainsRecipe(true);
		
		return d;
	}
	
	public static Meal getMeal(na.miniDao.Meal ref) {
		if (ref==null)
			return null;
		Meal m = new Meal();
		// ID
		m.setID(ref.getUsersMenuMealsID());
		// MEAL CATEGORY
		m.setMealCategory(ref.getCategory());
		// DISHES
		if (ref.getDishes()!=null && ref.getDishes().length>0) {
			List<Dish> list = new ArrayList<Dish>();
			for (na.miniDao.Dish dish : ref.getDishes()) {
				Dish d = OntoFactory.getDish(dish);
				if (d!=null)
					list.add(d);
			}
			m.setDishes(list.toArray(new Dish[list.size()]));
		}
		
		return m;
	}
	
	public static MenuDay getMenuDay(na.miniDao.DayMenu ref) {
		if (ref==null)
			return null;
		MenuDay m = new MenuDay();
		// ID
		m.setID(ref.getUsersMenusID());
		// DAY OF WEEK
		m.setDayOfWeek(ref.getActualDay());
		// MEALS
		if (ref.getMeals()!=null && ref.getMeals().length>0) {
			List<Meal> list = new ArrayList<Meal>();
			for (na.miniDao.Meal meal : ref.getMeals()) {
				Meal d = OntoFactory.getMeal(meal);
				if (d!=null)
					list.add(d);
			}
			m.setMeals(list.toArray(new Meal[list.size()]));
		}
		return m;
	}
	
	
	public static ShoppingList getShoppingList(na.miniDao.ShoppingList ref) {
		if (ref==null)
			return null;
		ShoppingList s = new ShoppingList();
		
//		s.setID(id)
//		s.setDateInterval(date)
		// MEALS
		if (ref.getItems()!=null && ref.getItems().length>0) {
			List<Ingredient> list = new ArrayList<Ingredient>();
			for (FoodItem meal : ref.getItems()) {
				Ingredient d = OntoFactory.getIngredient(meal);
				if (d!=null)
					list.add(d);
			}
			s.setIngredients(list.toArray(new Ingredient[list.size()]));
		}
		return s;
	}
	
	/*
	 * AUXILIAR METHODS
	 */

	private static Recipe getRecipeFromID(int recipeID) {
		//TODO
		return null;
	}
}
