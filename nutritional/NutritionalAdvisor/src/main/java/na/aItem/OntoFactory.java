package na.aItem;

import java.util.ArrayList;
import java.util.List;

import na.miniDao.FoodItem;
import na.miniDao.RecipeIngredient;
import na.miniDao.full.FoodSubcategory;
import na.oasisUtils.ami.AmiConnector;
import na.oasisUtils.ami.NutritionalCache;
import na.oasisUtils.trustedSecurityNetwork.TSFConnector;
import na.utils.OASIS_ServiceUnavailable;
import na.utils.ServiceInterface;
import na.utils.Utils;

import org.universAAL.ontology.nutrition.Dish;
import org.universAAL.ontology.nutrition.Food;
import org.universAAL.ontology.nutrition.FoodCategory;
import org.universAAL.ontology.nutrition.FoodSubCategory;
import org.universAAL.ontology.nutrition.Ingredient;
import org.universAAL.ontology.nutrition.Meal;
import org.universAAL.ontology.nutrition.MeasureUnit;
import org.universAAL.ontology.nutrition.MenuDay;
import org.universAAL.ontology.nutrition.Recipe;
import org.universAAL.ontology.nutrition.ShoppingList;

/**
 * A factory for creating Ontological objects.
 * Currently supports:
 * 		Recipes 		tested
 * 		Ingredient 		tested
 * 		Shopping List	tested
 * 		Food 			tested
 * 		FoodCategory 	tested
 * 		FoodSubCategory tested
 * 		Dish 			tested
 * 		Meal 			tested
 * 		MenuDay 		tested
 * 		
 * Missing:
 * 		Exercise, questionnaire
 * 		Advise, OtherCondition
 * 		Tip
 */
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
		FoodItem fi = new FoodItem();
		fi.setFoodID(ref.getFoodID());
		fi.setFoodSubCategoryID(ref.getFoodSubCategoryID());
		fi.setFoodCategoryID(ref.getFoodCategoryID());
		Food f = OntoFactory.getFood(fi);
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
		AmiConnector ami = AmiConnector.getAMI();
		String[] input = {String.valueOf(ref.getFoodID()), TSFConnector.getInstance().getToken()};
		try {
			//TODO revisar
			na.miniDao.Food food = (na.miniDao.Food)ami.invokeOperation(ServiceInterface.DOMAIN_Nutrition, ServiceInterface.OP_GetFullFood, input, false);
			// ID
			f.setID(ref.getFoodID());
			// NAME
			f.setName(food.getName());
			// CARBOHYDRATES
			f.setCarbohydrates(food.getCarbohydrates());
			// FAT
			f.setFat(food.getFat());
			// KCAL
			f.setKilocalories(food.getKCal());
			// FOOD SubCATEGORY
			f.setFoodSubCategory(OntoFactory.getFoodSubCategory(ref.getFoodSubCategoryID()));
		} catch (OASIS_ServiceUnavailable e) {
			Utils.println("Error en el servicio :(");
			e.printStackTrace();
		}
		return f;
	}
	
	public static FoodCategory getFoodCategory(int foodCateogoryID) {
//		na.miniDao.FoodCategory ref = Cacher.get().getFoodCategory(foodCateogoryID); //crear clase que busca las categorias de una cache, si no esta la descarga
//		if (ref==null)
//			return null;
//		FoodCategory fc = new FoodCategory();
//		// ID
//		fc.setID(ref.getID());
//		// NAME
//		fc.setName(ref.getDescription());
//		return fc;
		
		NutritionalCache nc = new NutritionalCache();
		Object o = nc.getCachedObject(nc.DATA_FOODCATEGORIES, nc.EXPIRE_DAILY);
		na.miniDao.full.FoodCategory[] fc = null;
		if (o!=null) {
			fc = (na.miniDao.full.FoodCategory[]) o;
			//find in cache
			//find category
			for (na.miniDao.full.FoodCategory cat : fc) {
				if (cat!=null && cat.getID()== foodCateogoryID) {
					FoodCategory ontoFood = new FoodCategory();
					// ID
					ontoFood.setID(cat.getID());
					// NAME
					ontoFood.setName(cat.getName());
					return ontoFood;
				}
			}
			Utils.println("Category not found ID in cache: "+foodCateogoryID);
		} else {
			AmiConnector ami = AmiConnector.getAMI();
			String[] input = {TSFConnector.getInstance().getToken()};
			try {
				na.miniDao.full.FoodCategory[] categories = (na.miniDao.full.FoodCategory[])ami.invokeOperation(ServiceInterface.DOMAIN_Nutrition, ServiceInterface.OP_GetFullFoodCategories, input, false);
				if (categories ==null) {
					Utils.println("Found null categories :(");
				} else {
					System.out.println("foods loaded from WS");
					nc.storeObject(categories, nc.DATA_FOODCATEGORIES);
					
					//find category
					for (na.miniDao.full.FoodCategory cat : categories) {
						if (cat!=null && cat.getID()== foodCateogoryID) {
							FoodCategory ontoFood = new FoodCategory();
							// ID
							ontoFood.setID(cat.getID());
							// NAME
							ontoFood.setName(cat.getName());
							return ontoFood;
						}
					}
				}
				Utils.println("Category not found ID: "+foodCateogoryID);
			} catch (OASIS_ServiceUnavailable e) {
				Utils.println("Error en el servicio :(");
				e.printStackTrace();
			} 
		}
		return null;
	}
	
	public static FoodSubCategory getFoodSubCategory(int foodSubCateogoryID) {
//		na.miniDao.FoodSubCategory ref; // = Cacher.get().getFoodCategory(foodSubCateogoryID); // crear clase que busca las categorias de una cache, si no esta la descarga
//		if (ref==null)
//			return null;
//		FoodCategory fc = new FoodCategory();
//		// ID
//		fc.setID(ref.getID());
//		// NAME
//		fc.setName(ref.getDescription());
//		return fc;
//		return null;
		NutritionalCache nc = new NutritionalCache();
		Object o = nc.getCachedObject(nc.DATA_FOODCATEGORIES, nc.EXPIRE_DAILY);
		na.miniDao.full.FoodCategory[] fc = null;
		if (o!=null) {
			fc = (na.miniDao.full.FoodCategory[]) o;
			//find category in cache
			for (na.miniDao.full.FoodCategory cat : fc) {
				if (cat!=null && cat.getSubCategories()!=null && cat.getSubCategories().length>0) {
					for (FoodSubcategory subCat : cat.getSubCategories()) {
						if (subCat!=null && subCat.getID()==foodSubCateogoryID) {
							FoodSubCategory ontoFood = new FoodSubCategory();
							// ID
							ontoFood.setID(subCat.getID());
							// NAME
							ontoFood.setName(subCat.getName());
							return ontoFood;
						}
					}
				}
			}
			Utils.println("SubCategory not found ID in cache: "+foodSubCateogoryID);
		} else {
			AmiConnector ami = AmiConnector.getAMI();
			String[] input = {TSFConnector.getInstance().getToken()};
			try {
				na.miniDao.full.FoodCategory[] categories = (na.miniDao.full.FoodCategory[])ami.invokeOperation(ServiceInterface.DOMAIN_Nutrition, ServiceInterface.OP_GetFullFoodCategories, input, false);
				System.out.println("foods loaded from WS");
				nc.storeObject(categories, nc.DATA_FOODCATEGORIES);
				
				//find category
				for (na.miniDao.full.FoodCategory cat : categories) {
					if (cat!=null && cat.getSubCategories()!=null && cat.getSubCategories().length>0) {
						for (FoodSubcategory subCat : cat.getSubCategories()) {
							if (subCat!=null && subCat.getID()==foodSubCateogoryID) {
								FoodSubCategory ontoFood = new FoodSubCategory();
								// ID
								ontoFood.setID(subCat.getID());
								// NAME
								ontoFood.setName(subCat.getName());
								return ontoFood;
							}
						}
					}
				}
				Utils.println("SubCategory not found ID: "+foodSubCateogoryID);
			} catch (OASIS_ServiceUnavailable e) {
				Utils.println("Error en el servicio :(");
				e.printStackTrace();
			} 
		}
		return null;
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
		Utils.println("Looking for category: "+ref.getCategory()+"<");
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

	public static Recipe getRecipeFromID(int recipeID) {
		AmiConnector ami = AmiConnector.getAMI();
		String[] input = {TSFConnector.getInstance().getToken(), String.valueOf(recipeID)};
		try {
			na.miniDao.Recipe recipe = (na.miniDao.Recipe)ami.invokeOperation(ServiceInterface.DOMAIN_Nutrition, ServiceInterface.OP_GetUserRecipe, input, false);
			return OntoFactory.getRecipe(recipe);
		} catch (OASIS_ServiceUnavailable e) {
			Utils.println("Error en el servicio :(");
			e.printStackTrace();
		}

		return null;
	}
	

	private static Food getFoodByID(int foodID) {
//		AmiConnector ami = AmiConnector.getAMI();
//		String[] input = {TSFConnector.getInstance().getToken(), String.valueOf(foodID)};
//		try {
//			na.miniDao.Recipe recipe = (na.miniDao.Recipe)ami.invokeOperation(ServiceInterface.DOMAIN_Nutrition, ServiceInterface.OP_GetFullFood, input, false);
//			return OntoFactory.getFood(ref);
//		} catch (OASIS_ServiceUnavailable e) {
//			Utils.println("Error en el servicio :(");
//			e.printStackTrace();
//		}
//
		return null;
	}
}
