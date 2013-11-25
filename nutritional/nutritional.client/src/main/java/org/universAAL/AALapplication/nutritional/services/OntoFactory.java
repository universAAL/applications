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

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import na.miniDao.FoodItem;
import na.miniDao.RecipeIngredient;
import na.miniDao.full.FoodSubcategory;
import na.oasisUtils.ami.AmiConnector;
import na.oasisUtils.ami.NutritionalCache;
import na.oasisUtils.trustedSecurityNetwork.TSFConnector;
import na.services.recipes.business.Business;
import na.utils.NP.Nutrition.Habits;
import na.utils.NP.Nutrition.NutriPreferences;
import na.utils.OASIS_ServiceUnavailable;
import na.utils.ServiceInterface;
import na.utils.Utils;
import na.ws.UProperty;
import na.ws.UPropertyValues;
import na.ws.UserNutritionalProfile;

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
import org.universAAL.ontology.nutrition.profile.DietType;
import org.universAAL.ontology.nutrition.profile.NutritionalHabits;
import org.universAAL.ontology.nutrition.profile.NutritionalPreferences;
import org.universAAL.ontology.nutrition.profile.NutritionalSubProfile;

/**
 * A factory for creating Ontological objects. Currently supports: -Recipes
 * tested -Ingredient tested -Shopping List tested -Food tested -FoodCategory
 * tested -FoodSubCategory tested -Dish tested -Meal tested -MenuDay tested
 * -DishCategory tested (included in getDish) -MealCategory tested (included in
 * getMeal) -DayOfWeek tested (included in getMenuDay) -MeasureUnit tested
 * (included in getShoppingList)
 * 
 * 
 * Missing: Exercise, questionnaire Advise, OtherCondition Tip
 */
public class OntoFactory {

    /**
     * Gets a ontological representation of a recipe. Does not contain the
     * image.
     * 
     * @param recipe
     *            the recipe
     * @return the onto recipe
     */
    public static Recipe getRecipe(na.miniDao.Recipe recipe) {
	if (recipe == null)
	    return null;
	Recipe or = new Recipe();
	// ID
	or.setID(recipe.getRecipeID());
	// NAME
	if (recipe.getCourse() != null)
	    or.setName(recipe.getCourse());
	// PROCEDURE
	if (recipe.getProcedure() != null)
	    or.setProcedure(recipe.getProcedure());
	// FAVOURITE
	or.setFavourite(recipe.isFavouriteRecipe());
	// DISH_CATEGORY
	try {
	    if (recipe.getDishCategory() != null
		    && recipe.getDishCategory().length() > 0) {
		int val = new Integer(recipe.getDishCategory()).intValue();
		or.setDishCategory(val);
	    }
	} catch (NumberFormatException n) {
	    n.printStackTrace();
	}
	// PICTURE
	if (recipe.getPicture() != null && recipe.getPicture().length() > 0) {
	    or.setPicture(recipe.getPicture());
	}
	// INGREDIENTS
	Utils.println("Processing ingredients");
	if (recipe.getRecipeIngredients() != null
		&& recipe.getRecipeIngredients().length > 0) {
	    List<Ingredient> list = new ArrayList<Ingredient>();
	    for (RecipeIngredient ri : recipe.getRecipeIngredients()) {
		Ingredient ing = OntoFactory.getIngredient(ri);
		if (ing != null) {
		    Utils.println("Adding ingredient: " + ing);
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
	if (ref == null)
	    return null;
	Ingredient ing = new Ingredient();
	// ID
	ing.setID(ref.getFoodID());
	String q = "" + ref.getQuantity();
	// QUANTITY
	ing.setQuantity(q);
	// MEASURE UNIT
	MeasureUnit mu = MeasureUnit
		.getMeasureUnitByOrder(ref.getMeasUnitsID());
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
	if (ref == null)
	    return null;
	Ingredient ing = new Ingredient();
	// ID
	ing.setID(ref.getFoodID());
	String q = "" + ref.getAmount();
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
	if (ref == null)
	    return null;
	Food f = new Food();
	AmiConnector ami = AmiConnector.getAMI();
	String[] input = { String.valueOf(ref.getFoodID()),
		TSFConnector.getInstance().getToken() };
	try {
	    // TODO revisar
	    na.miniDao.Food food = (na.miniDao.Food) ami.invokeOperation(
		    ServiceInterface.DOMAIN_Nutrition,
		    ServiceInterface.OP_GetFullFood, input, false);
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
	    f.setFoodSubCategory(OntoFactory.getFoodSubCategory(ref
		    .getFoodSubCategoryID()));
	} catch (OASIS_ServiceUnavailable e) {
	    Utils.println("Error en el servicio :(");
	    e.printStackTrace();
	}
	return f;
    }

    public static FoodCategory getFoodCategory(int foodCateogoryID) {
	// na.miniDao.FoodCategory ref =
	// Cacher.get().getFoodCategory(foodCateogoryID); //crear clase que
	// busca las categorias de una cache, si no esta la descarga
	// if (ref==null)
	// return null;
	// FoodCategory fc = new FoodCategory();
	// // ID
	// fc.setID(ref.getID());
	// // NAME
	// fc.setName(ref.getDescription());
	// return fc;

	NutritionalCache nc = new NutritionalCache();
	Object o = nc.getCachedObject(nc.DATA_FOODCATEGORIES, nc.EXPIRE_DAILY);
	na.miniDao.full.FoodCategory[] fc = null;
	if (o != null) {
	    fc = (na.miniDao.full.FoodCategory[]) o;
	    // find in cache
	    // find category
	    for (na.miniDao.full.FoodCategory cat : fc) {
		if (cat != null && cat.getID() == foodCateogoryID) {
		    FoodCategory ontoFood = new FoodCategory();
		    // ID
		    ontoFood.setID(cat.getID());
		    // NAME
		    ontoFood.setName(cat.getName());
		    return ontoFood;
		}
	    }
	    Utils.println("Category not found ID in cache: " + foodCateogoryID);
	} else {
	    AmiConnector ami = AmiConnector.getAMI();
	    String[] input = { TSFConnector.getInstance().getToken() };
	    try {
		na.miniDao.full.FoodCategory[] categories = (na.miniDao.full.FoodCategory[]) ami
			.invokeOperation(ServiceInterface.DOMAIN_Nutrition,
				ServiceInterface.OP_GetFullFoodCategories,
				input, false);
		if (categories == null) {
		    Utils.println("Found null categories :(");
		} else {
		    Utils.println("foods loaded from WS");
		    nc.storeObject(categories, nc.DATA_FOODCATEGORIES);

		    // find category
		    for (na.miniDao.full.FoodCategory cat : categories) {
			if (cat != null && cat.getID() == foodCateogoryID) {
			    FoodCategory ontoFood = new FoodCategory();
			    // ID
			    ontoFood.setID(cat.getID());
			    // NAME
			    ontoFood.setName(cat.getName());
			    return ontoFood;
			}
		    }
		}
		Utils.println("Category not found ID: " + foodCateogoryID);
	    } catch (OASIS_ServiceUnavailable e) {
		Utils.println("Service error :(");
		e.printStackTrace();
	    }
	}
	return null;
    }

    public static FoodSubCategory getFoodSubCategory(int foodSubCateogoryID) {
	// na.miniDao.FoodSubCategory ref; // =
	// Cacher.get().getFoodCategory(foodSubCateogoryID); // crear clase que
	// busca las categorias de una cache, si no esta la descarga
	// if (ref==null)
	// return null;
	// FoodCategory fc = new FoodCategory();
	// // ID
	// fc.setID(ref.getID());
	// // NAME
	// fc.setName(ref.getDescription());
	// return fc;
	// return null;
	NutritionalCache nc = new NutritionalCache();
	Object o = nc.getCachedObject(nc.DATA_FOODCATEGORIES, nc.EXPIRE_DAILY);
	na.miniDao.full.FoodCategory[] fc = null;
	if (o != null) {
	    fc = (na.miniDao.full.FoodCategory[]) o;
	    // find category in cache
	    for (na.miniDao.full.FoodCategory cat : fc) {
		if (cat != null && cat.getSubCategories() != null
			&& cat.getSubCategories().length > 0) {
		    for (FoodSubcategory subCat : cat.getSubCategories()) {
			if (subCat != null
				&& subCat.getID() == foodSubCateogoryID) {
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
	    Utils.println("SubCategory not found ID in cache: "
		    + foodSubCateogoryID);
	} else {
	    AmiConnector ami = AmiConnector.getAMI();
	    String[] input = { TSFConnector.getInstance().getToken() };
	    try {
		na.miniDao.full.FoodCategory[] categories = (na.miniDao.full.FoodCategory[]) ami
			.invokeOperation(ServiceInterface.DOMAIN_Nutrition,
				ServiceInterface.OP_GetFullFoodCategories,
				input, false);
		Utils.println("foods loaded from WS");
		nc.storeObject(categories, nc.DATA_FOODCATEGORIES);

		// find category
		for (na.miniDao.full.FoodCategory cat : categories) {
		    if (cat != null && cat.getSubCategories() != null
			    && cat.getSubCategories().length > 0) {
			for (FoodSubcategory subCat : cat.getSubCategories()) {
			    if (subCat != null
				    && subCat.getID() == foodSubCateogoryID) {
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
		Utils.println("SubCategory not found ID: " + foodSubCateogoryID);
	    } catch (OASIS_ServiceUnavailable e) {
		Utils.println("Service error :(");
		e.printStackTrace();
	    }
	}
	return null;
    }

    public static Dish getDish(na.miniDao.Dish ref) {
	if (ref == null)
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
	if (ref == null)
	    return null;
	Meal m = new Meal();
	// ID
	m.setID(ref.getUsersMenuMealsID());
	// MEAL CATEGORY
	Utils.println("Looking for category: " + ref.getCategory() + "<");
	m.setMealCategory(ref.getCategory());
	// DISHES
	if (ref.getDishes() != null && ref.getDishes().length > 0) {
	    List<Dish> list = new ArrayList<Dish>();
	    for (na.miniDao.Dish dish : ref.getDishes()) {
		Dish d = OntoFactory.getDish(dish);
		if (d != null)
		    list.add(d);
	    }
	    m.setDishes(list.toArray(new Dish[list.size()]));
	}

	return m;
    }

    public static MenuDay getMenuDay(na.miniDao.DayMenu ref) {
	if (ref == null)
	    return null;
	MenuDay m = new MenuDay();
	// ID
	m.setID(ref.getUsersMenusID());
	// DAY OF WEEK
	m.setDayOfWeek(ref.getActualDay());
	// MEALS
	if (ref.getMeals() != null && ref.getMeals().length > 0) {
	    List<Meal> list = new ArrayList<Meal>();
	    for (na.miniDao.Meal meal : ref.getMeals()) {
		Meal d = OntoFactory.getMeal(meal);
		if (d != null)
		    list.add(d);
	    }
	    m.setMeals(list.toArray(new Meal[list.size()]));
	}
	return m;
    }

    public static ShoppingList getShoppingList(na.miniDao.ShoppingList ref) {
	if (ref == null)
	    return null;
	ShoppingList s = new ShoppingList();

	// s.setID(id)
	// s.setDateInterval(date)
	// MEALS
	if (ref.getItems() != null && ref.getItems().length > 0) {
	    List<Ingredient> list = new ArrayList<Ingredient>();
	    for (FoodItem meal : ref.getItems()) {
		Ingredient d = OntoFactory.getIngredient(meal);
		if (d != null)
		    list.add(d);
	    }
	    s.setIngredients(list.toArray(new Ingredient[list.size()]));
	}
	return s;
    }

    public static NutritionalSubProfile getNutrProfile(
	    UserNutritionalProfile profile) {
	NutritionalSubProfile nsp = new NutritionalSubProfile();// TODO: Set
								// predefined
								// URI?
	NutritionalHabits nhs = new NutritionalHabits();
	NutritionalPreferences npr = new NutritionalPreferences();
	HashMap<String, UProperty> map = new HashMap<String, UProperty>();
	// recorrer todas las propiedades y crear una tabla con sus c�digos para
	// f�cil acceso
	if (profile != null && profile.getProperties() != null
		&& profile.getProperties().length > 0) {
	    for (UProperty element : profile.getProperties()) {
		map.put(element.getCode(), element);
	    }

	    nhs.setGetUpHour(toHour(map.get(Habits.GET_UP_HOUR)));
	    nhs.setBedHour(toHour(map.get(Habits.BED_HOUR)));

	    nhs.setBreakfastHour(toHour(map.get(Habits.BREAKFAST_HOUR)));
	    // nhs.setBreakfastPlace(newPropValue);
	    nhs.setMorningSnackHour(toHour(map.get(Habits.MORNING_SNACK_HOUR)));
	    // nhs.setMorningSnackPlace(newPropValue);
	    nhs.setLunchHour(toHour(map.get(Habits.LUNCH_HOUR)));
	    // nhs.setLunchPlace(newPropValue);
	    nhs.setAfternoonSnackHour(toHour(map
		    .get(Habits.AFTERNOON_SNACK_HOUR)));
	    // nhs.setAfternoonSnackPlace(newPropValue);
	    nhs.setDinnerHour(toHour(map.get(Habits.DINNER_HOUR)));
	    // nhs.setDinnertPlace(newPropValue);
	    nhs.setAfterDinnerSnackHour(toHour(map
		    .get(Habits.AFTERDINNER_SNACK_HOUR)));
	    // nhs.setAfterDinnerSnackPlace(newPropValue);
	    if (map.get(Habits.CONSUMES_ALCOHOL) != null)
		nhs.setConsumesAlcohol(Boolean.parseBoolean(map
			.get(Habits.CONSUMES_ALCOHOL).getValues(0).getValue()));
	    if (map.get(Habits.CONSUMES_DRUGS) != null)
		nhs.setConsumesDrugs(Boolean.parseBoolean(map
			.get(Habits.CONSUMES_DRUGS).getValues(0).getValue()));
	    if (map.get(Habits.CONSUMES_TOBACCO) != null)
		nhs.setConsumesTobacco(Boolean.parseBoolean(map
			.get(Habits.CONSUMES_TOBACCO).getValues(0).getValue()));

	    if (map.get(Habits.SELF_COOKING) != null)
		nhs.setSelfCooking(Boolean.parseBoolean(map
			.get(Habits.SELF_COOKING).getValues(0).getValue()));
	    if (map.get(Habits.SELF_SHOPPING) != null)
		nhs.setSelfShopping(Boolean.parseBoolean(map
			.get(Habits.SELF_SHOPPING).getValues(0).getValue()));

	    if (map.get(Habits.COMPANIONS_AT_TABLE) != null)
		nhs.setCompanionsAtTable(Boolean.parseBoolean(map
			.get(Habits.COMPANIONS_AT_TABLE).getValues(0)
			.getValue()));
	    if (map.get(Habits.NUMBER_COMPANIONS_AT_TABLE) != null)
		nhs.setNumberCompanionsAtTable(Integer.parseInt(map
			.get(Habits.NUMBER_COMPANIONS_AT_TABLE).getValues(0)
			.getValue()));

	    if (map.get(Habits.NUMBER_DAILY_MEALS) != null)
		nhs.setNumberDailyMeals(Integer
			.parseInt(map.get(Habits.NUMBER_DAILY_MEALS)
				.getValues(0).getValue()));
	    if (map.get(Habits.RECOMMENDED_DAILY_MEALS) != null)
		nhs.setRecommendedDailyMeals(Integer.parseInt(map
			.get(Habits.RECOMMENDED_DAILY_MEALS).getValues(0)
			.getValue()));
	    // nhs.setTypeDailyMeals(newPropValue);

	    if (map.get(Habits.DIET_TYPE) != null)
		nhs.setDietType(DietType.valueOf(map.get(Habits.DIET_TYPE)
			.getValues(0).getValue().toLowerCase()));
	    if (map.get(Habits.RECOMMENDED_FOOD_INTAKE_CALORIES) != null)
		nhs.setRecommendedFoodIntakeCalories(Integer.parseInt(map
			.get(Habits.RECOMMENDED_FOOD_INTAKE_CALORIES)
			.getValues(0).getValue()));
	    // nhs.setNutritionalGoal(newPropValue);
	    // nhs.setReligionConstraint(newPropValue);

	    npr.setFoodDislike(getFood_Dislikes(
		    map.get(NutriPreferences.FOOD_DISLIKES)).toArray(
		    new Food[] {}));
	    if (map.get(NutriPreferences.MEDICINE_FOOD_INTERACTIONS) != null)
		npr.setMedicineFoodInteractions(map
			.get(NutriPreferences.MEDICINE_FOOD_INTERACTIONS)
			.getValues(0).getValue());
	    // npr.setPrefConvenience(newPropValue);
	    // npr.setPreferredCookingTechniques(newPropValue);
	    // npr.setPreferredCuisines(newPropValue);
	    // npr.setFavRecipes(getFavourite_Recipes(map.get(NutriPreferences.FAVOURITE_RECIPES)).toArray(new
	    // Recipe[]{}));//FavRecs in Profile is overriden by oasis
	    npr.setFavRecipes(getFavourite_Recipes_Override().toArray(
		    new Recipe[] {}));
	}
	nsp.setNutritionalHabits(nhs);
	nsp.setNutritionalPreferences(npr);
	return nsp;
    }

    /*
     * AUXILIAR METHODS
     */

    public static Recipe getRecipeFromID(int recipeID) {
	AmiConnector ami = AmiConnector.getAMI();
	String[] input = { TSFConnector.getInstance().getToken(),
		String.valueOf(recipeID) };
	try {
	    na.miniDao.Recipe recipe = (na.miniDao.Recipe) ami.invokeOperation(
		    ServiceInterface.DOMAIN_Nutrition,
		    ServiceInterface.OP_GetUserRecipe, input, false);
	    return OntoFactory.getRecipe(recipe);
	} catch (OASIS_ServiceUnavailable e) {
	    Utils.println("Service error :(");
	    e.printStackTrace();
	}

	return null;
    }

    public static List<Food> getFood_Dislikes(UProperty myProperty) {
	List<Food> lista = new ArrayList<Food>();
	for (UPropertyValues e : myProperty.getValues()) {
	    if (e != null) {
		String vals[] = e.getValue().split("@");
		na.miniDao.FoodItem item = new na.miniDao.FoodItem(
			Integer.parseInt(vals[0]), -1, vals[2], "", "", "", "",
			-1, -1);
		lista.add(getFood(item));
	    }
	}
	return lista;
    }

    public static List<Recipe> getFavourite_Recipes(UProperty myProperty) {
	List<Recipe> lista = new ArrayList<Recipe>();
	for (UPropertyValues e : myProperty.getValues()) {
	    if (e != null) {
		Recipe r = new Recipe();
		r.setName(e.getValue());
		lista.add(r);
	    }
	}
	return lista;
    }

    public static List<Recipe> getFavourite_Recipes_Override() {
	List<Recipe> lista = new ArrayList<Recipe>();
	Business business = new Business();
	na.miniDao.Recipe[] recipes;
	try {
	    recipes = business.getMyFavoriteRecipes();
	    for (na.miniDao.Recipe r : recipes) {
		if (r != null) {
		    Recipe ont = getRecipe(r);
		    lista.add(ont);
		}
	    }
	} catch (OASIS_ServiceUnavailable e) {
	    e.printStackTrace();
	}
	return lista;
    }

    private static XMLGregorianCalendar toHour(UProperty uProperty) {
	if (uProperty == null)
	    return null;
	GregorianCalendar c = new GregorianCalendar();
	String hour = uProperty.getValues(0).getValue();
	String[] sp = hour.replace(".", ":").split(":");
	if (sp.length < 2)
	    return null;
	try {
	    XMLGregorianCalendar xc = DatatypeFactory.newInstance()
		    .newXMLGregorianCalendar(c);
	    xc.setHour(Integer.parseInt(sp[0]));
	    xc.setMinute(Integer.parseInt(sp[1]));
	    return xc;
	} catch (DatatypeConfigurationException e) {
	    e.printStackTrace();
	    return null;
	}
    }

    private static Food getFoodByID(int foodID) {
	// AmiConnector ami = AmiConnector.getAMI();
	// String[] input = {TSFConnector.getInstance().getToken(),
	// String.valueOf(foodID)};
	// try {
	// na.miniDao.Recipe recipe =
	// (na.miniDao.Recipe)ami.invokeOperation(ServiceInterface.DOMAIN_Nutrition,
	// ServiceInterface.OP_GetFullFood, input, false);
	// return OntoFactory.getFood(ref);
	// } catch (OASIS_ServiceUnavailable e) {
	// Utils.println("Error en el servicio :(");
	// e.printStackTrace();
	// }
	//
	return null;
    }
}
