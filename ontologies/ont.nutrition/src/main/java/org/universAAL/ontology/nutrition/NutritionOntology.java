/*
	Copyright 2011-2012 Itaca-TSB, http://www.tsb.upv.es
	Tecnologías para la Salud y el Bienestar 
	
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
package org.universAAL.ontology.nutrition;

import org.universAAL.middleware.owl.DataRepOntology;
import org.universAAL.middleware.owl.ManagedIndividual;
import org.universAAL.middleware.owl.MergedRestriction;
import org.universAAL.middleware.owl.OntClassInfoSetup;
import org.universAAL.middleware.owl.Ontology;
import org.universAAL.middleware.rdf.Resource;
import org.universAAL.middleware.rdf.TypeMapper;
import org.universAAL.middleware.service.owl.Service;
import org.universAAL.middleware.service.owl.ServiceBusOntology;
import org.universAAL.ontology.NutritionFactory;
import org.universAAL.ontology.location.Location;
import org.universAAL.ontology.nutrition.profile.*;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 * 
 * @author Hector Galan
 * 
 */
public final class NutritionOntology extends Ontology {

    private static NutritionFactory factory = new NutritionFactory();

    public static final String NAMESPACE = "http://ontology.universAAL.org/Nutrition.owl#";

    public NutritionOntology() {
	super(NAMESPACE);
    }

    public void create() {
	System.out.println("Nutri: Creating nutri ontology");
	Resource r = getInfo();
	r.setResourceComment("The ontology defining concepts about nutrition.");
	r.setResourceLabel("Nutrition");

	addImport(DataRepOntology.NAMESPACE);
	addImport(ServiceBusOntology.NAMESPACE);

	OntClassInfoSetup oci;

	// load FoodSubCategory
	oci = createNewOntClassInfo(FoodSubCategory.MY_URI, factory,
		NutritionFactory.FactoryIndex_FoodSubCategory);
	oci.setResourceComment("Food Sub Cateogory");
	oci.setResourceLabel("ShoppingList");
	oci.addSuperClass(ManagedIndividual.MY_URI);
	oci.addDatatypeProperty(FoodSubCategory.PROP_ID).setFunctional(); // Integer
	oci.addDatatypeProperty(FoodSubCategory.PROP_NAME).setFunctional(); // String
	oci.addObjectProperty(FoodSubCategory.PROP_FOODCATEGORY)
		.setFunctional(); // String
	oci.addRestriction(MergedRestriction
		.getAllValuesRestrictionWithCardinality(
			FoodSubCategory.PROP_ID, TypeMapper
				.getDatatypeURI(Integer.class), 1, 1));

	// load Enum. DayOfWeek
	oci = createNewAbstractOntClassInfo(DayOfWeek.MY_URI);
	oci.setResourceComment("Enumarion for days of week");
	oci.setResourceLabel("DayOfWeek");
	oci.toEnumeration(new ManagedIndividual[] { DayOfWeek.SUNDAY,
		DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY,
		DayOfWeek.THURSDAY, DayOfWeek.FRIDAY, DayOfWeek.SATURDAY });

	// load Enum. MealCategory
	oci = createNewAbstractOntClassInfo(MealCategory.MY_URI);
	oci.setResourceComment("Enumarion for categories of meals");
	oci.setResourceLabel("MealCategory");
	oci.toEnumeration(new ManagedIndividual[] { MealCategory.BREAKFAST,
		MealCategory.MIDMORNING_SNACK, MealCategory.LUNCH,
		MealCategory.AFTERNOON_SNACK, MealCategory.DINNER,
		MealCategory.AFTERDINNER_SNACK });

	// load Enum. DishCategory
	oci = createNewAbstractOntClassInfo(DishCategory.MY_URI);
	oci.setResourceComment("Enumarion for categories of dishes");
	oci.setResourceLabel("DishCategory");
	oci.toEnumeration(new ManagedIndividual[] { DishCategory.DESSERT,
		DishCategory.DRINK, DishCategory.FIRST_COURSE,
		DishCategory.MAIN_COURSE, DishCategory.SIDE_DISH,
		DishCategory.STARTER, DishCategory.BREAKFAST,
		DishCategory.SNACK });

	// load Enum. MeasureUnit
	oci = createNewAbstractOntClassInfo(MeasureUnit.MY_URI);
	oci.setResourceComment("Enumarion for measure units");
	oci.setResourceLabel("MeasureUnit");
	oci.toEnumeration(new ManagedIndividual[] { MeasureUnit.MILLIGRAMS,
		MeasureUnit.GRAMS, MeasureUnit.KILOGRAMS,
		MeasureUnit.MILLILITERS, MeasureUnit.LITERS, MeasureUnit.CUPS,
		MeasureUnit.TABLESPOONS, MeasureUnit.TEASPOONS,
		MeasureUnit.UNITS, MeasureUnit.HANDFULLS });

	// load Food
	oci = createNewOntClassInfo(Food.MY_URI, factory,
		NutritionFactory.FactoryIndex_Food);
	oci.setResourceComment("Ingredient");
	oci.setResourceLabel("Ingredient");
	oci.addSuperClass(ManagedIndividual.MY_URI);
	oci.addDatatypeProperty(Food.PROP_ID).setFunctional(); // Integer
	oci.addDatatypeProperty(Food.PROP_CARBOHIDRATES).setFunctional(); // Double
	oci.addDatatypeProperty(Food.PROP_FAT).setFunctional(); // Double
	oci.addDatatypeProperty(Food.PROP_KILOCALORIES).setFunctional(); // Double
	oci.addDatatypeProperty(Food.PROP_NAME).setFunctional(); // String
	oci.addObjectProperty(Food.PROP_FOODSUBCATEGORY).setFunctional(); // FoodSubCategory
	oci.addRestriction(MergedRestriction
		.getAllValuesRestrictionWithCardinality(Food.PROP_ID,
			TypeMapper.getDatatypeURI(Integer.class), 1, 1));
	oci.addRestriction(MergedRestriction
		.getAllValuesRestrictionWithCardinality(
			Food.PROP_CARBOHIDRATES, TypeMapper
				.getDatatypeURI(Double.class), 0, 1));
	oci.addRestriction(MergedRestriction
		.getAllValuesRestrictionWithCardinality(Food.PROP_FAT,
			TypeMapper.getDatatypeURI(Double.class), 0, 1));
	oci.addRestriction(MergedRestriction
		.getAllValuesRestrictionWithCardinality(Food.PROP_KILOCALORIES,
			TypeMapper.getDatatypeURI(Double.class), 0, 1));
	oci.addRestriction(MergedRestriction
		.getAllValuesRestrictionWithCardinality(Food.PROP_NAME,
			TypeMapper.getDatatypeURI(String.class), 1, 1));
	oci.addRestriction(MergedRestriction
		.getAllValuesRestrictionWithCardinality(
			Food.PROP_FOODSUBCATEGORY, TypeMapper
				.getDatatypeURI(FoodSubCategory.MY_URI), 0, 1));

	// load Ingredient
	oci = createNewOntClassInfo(Ingredient.MY_URI, factory,
		NutritionFactory.FactoryIndex_Ingredient);
	oci.setResourceComment("Ingredient");
	oci.setResourceLabel("Ingredient");
	oci.addSuperClass(ManagedIndividual.MY_URI);
	oci.addDatatypeProperty(Ingredient.PROP_ID).setFunctional(); // Integer
	oci.addDatatypeProperty(Ingredient.PROP_QUANTITY).setFunctional(); // String
	oci.addObjectProperty(Ingredient.PROP_FOOD).setFunctional(); // Food
	oci.addObjectProperty(Ingredient.PROP_MEASURE_UNIT).setFunctional(); // MeasureUnit
	oci.addRestriction(MergedRestriction
		.getAllValuesRestrictionWithCardinality(Ingredient.PROP_ID,
			TypeMapper.getDatatypeURI(Integer.class), 1, 1));
	oci.addRestriction(MergedRestriction
		.getAllValuesRestrictionWithCardinality(
			Ingredient.PROP_QUANTITY, TypeMapper
				.getDatatypeURI(String.class), 1, 1));
	oci.addRestriction(MergedRestriction
		.getAllValuesRestrictionWithCardinality(Ingredient.PROP_FOOD,
			TypeMapper.getDatatypeURI(Food.MY_URI), 1, 1));
	oci.addRestriction(MergedRestriction
		.getAllValuesRestrictionWithCardinality(
			Ingredient.PROP_MEASURE_UNIT, TypeMapper
				.getDatatypeURI(MeasureUnit.MY_URI), 1, 1));

	// load ShoppingList
	oci = createNewOntClassInfo(ShoppingList.MY_URI, factory,
		NutritionFactory.FactoryIndex_ShoppingList);
	oci.setResourceComment("Shopping list");
	oci.setResourceLabel("ShoppingList");
	oci.addSuperClass(ManagedIndividual.MY_URI);
	oci.addDatatypeProperty(ShoppingList.PROP_ID).setFunctional(); // Integer
	oci.addDatatypeProperty(ShoppingList.PROP_DATEINTERVAL).setFunctional(); // String
	oci.addObjectProperty(ShoppingList.PROP_INGREDIENTS).setFunctional(); // Ingredient
	oci.addRestriction(MergedRestriction
		.getAllValuesRestrictionWithCardinality(ShoppingList.PROP_ID,
			TypeMapper.getDatatypeURI(Integer.class), 1, 1));
	oci.addRestriction(MergedRestriction
		.getAllValuesRestrictionWithCardinality(
			ShoppingList.PROP_DATEINTERVAL, TypeMapper
				.getDatatypeURI(String.class), 1, 1));
	oci.addRestriction(MergedRestriction.getAllValuesRestriction(
		ShoppingList.PROP_INGREDIENTS, Ingredient.MY_URI));

	// load Recipe
	oci = createNewOntClassInfo(Recipe.MY_URI, factory,
		NutritionFactory.FactoryIndex_Recipe);
	oci.setResourceComment("Single recipe");
	oci.setResourceLabel("Recipe");
	oci.addSuperClass(ManagedIndividual.MY_URI);
	oci.addObjectProperty(Recipe.PROP_DISH_CATEGORY).setFunctional(); // DishCategory
	oci.addDatatypeProperty(Recipe.PROP_ID).setFunctional(); // Integer
	oci.addObjectProperty(Recipe.PROP_INGREDIENTS).setFunctional(); // Ingredient
	oci.addDatatypeProperty(Recipe.PROP_IS_FAVOURITE).setFunctional(); // Boolean
	oci.addDatatypeProperty(Recipe.PROP_NAME).setFunctional(); // String
	oci.addDatatypeProperty(Recipe.PROP_PICTURE).setFunctional(); // String
	oci.addDatatypeProperty(Recipe.PROP_PROCEDURE).setFunctional(); // String
	oci.addRestriction(MergedRestriction
		.getAllValuesRestrictionWithCardinality(
			Recipe.PROP_DISH_CATEGORY, DishCategory.MY_URI, 1, 1));
	oci.addRestriction(MergedRestriction
		.getAllValuesRestrictionWithCardinality(Recipe.PROP_ID,
			TypeMapper.getDatatypeURI(Integer.class), 1, 1));
	// System.out.println("Recipe.PropIngredients: "+Recipe.PROP_INGREDIENTS+
	// "  Ingredient.MyUri: "+Ingredient.MY_URI);
	oci.addRestriction(MergedRestriction.getAllValuesRestriction(
		Recipe.PROP_INGREDIENTS, Ingredient.MY_URI));
	oci.addRestriction(MergedRestriction
		.getAllValuesRestrictionWithCardinality(
			Recipe.PROP_IS_FAVOURITE, TypeMapper
				.getDatatypeURI(Boolean.class), 1, 1));
	oci.addRestriction(MergedRestriction
		.getAllValuesRestrictionWithCardinality(Recipe.PROP_NAME,
			TypeMapper.getDatatypeURI(String.class), 1, 1));
	oci.addRestriction(MergedRestriction
		.getAllValuesRestrictionWithCardinality(Recipe.PROP_PICTURE,
			TypeMapper.getDatatypeURI(String.class), 0, 1));
	oci.addRestriction(MergedRestriction
		.getAllValuesRestrictionWithCardinality(Recipe.PROP_PROCEDURE,
			TypeMapper.getDatatypeURI(String.class), 0, 1));

	// load Dish
	oci = createNewOntClassInfo(Dish.MY_URI, factory,
		NutritionFactory.FactoryIndex_Dish);
	oci.setResourceComment("Single dish");
	oci.setResourceLabel("Dish");
	oci.addSuperClass(ManagedIndividual.MY_URI);
	oci.addDatatypeProperty(Dish.PROP_ID).setFunctional(); // Integer
	oci.addDatatypeProperty(Dish.PROP_NAME).setFunctional(); // String
	oci.addDatatypeProperty(Dish.PROP_PICTURE).setFunctional(); // String
	oci.addDatatypeProperty(Dish.PROP_CONTAINS_PROCEDURE).setFunctional(); // Boolean
	oci.addDatatypeProperty(Dish.PROP_CONTAINS_RECIPE).setFunctional(); // Boolean
	oci.addObjectProperty(Dish.PROP_DISH_CATEGORY).setFunctional(); // DishCategory
	oci.addObjectProperty(Dish.PROP_RECIPE).setFunctional(); // Recipe
	oci.addRestriction(MergedRestriction
		.getAllValuesRestrictionWithCardinality(Dish.PROP_ID,
			TypeMapper.getDatatypeURI(Integer.class), 1, 1));
	oci.addRestriction(MergedRestriction
		.getAllValuesRestrictionWithCardinality(Dish.PROP_NAME,
			TypeMapper.getDatatypeURI(String.class), 1, 1));
	oci.addRestriction(MergedRestriction
		.getAllValuesRestrictionWithCardinality(Dish.PROP_PICTURE,
			TypeMapper.getDatatypeURI(String.class), 0, 1));
	oci.addRestriction(MergedRestriction
		.getAllValuesRestrictionWithCardinality(
			Dish.PROP_CONTAINS_PROCEDURE, TypeMapper
				.getDatatypeURI(Boolean.class), 1, 1));
	oci.addRestriction(MergedRestriction
		.getAllValuesRestrictionWithCardinality(
			Dish.PROP_CONTAINS_RECIPE, TypeMapper
				.getDatatypeURI(Boolean.class), 1, 1));
	oci.addRestriction(MergedRestriction
		.getAllValuesRestrictionWithCardinality(
			Dish.PROP_DISH_CATEGORY, DishCategory.MY_URI, 1, 1));
	oci.addRestriction(MergedRestriction.getAllValuesRestriction(
		Dish.PROP_RECIPE, Recipe.MY_URI));

	// load Meal
	oci = createNewOntClassInfo(Meal.MY_URI, factory,
		NutritionFactory.FactoryIndex_Meal);
	oci.setResourceComment("Single meal");
	oci.setResourceLabel("Meal");
	oci.addSuperClass(ManagedIndividual.MY_URI);
	oci.addDatatypeProperty(Meal.PROP_ID).setFunctional(); // Integer
	oci.addObjectProperty(Meal.PROP_MEAL_CATEGORY).setFunctional(); // MealCategory
	oci.addObjectProperty(Meal.PROP_DISHES).setFunctional(); // Dish
	oci.addRestriction(MergedRestriction
		.getAllValuesRestrictionWithCardinality(Meal.PROP_ID,
			TypeMapper.getDatatypeURI(Integer.class), 1, 1));
	oci.addRestriction(MergedRestriction
		.getAllValuesRestrictionWithCardinality(
			Meal.PROP_MEAL_CATEGORY, MealCategory.MY_URI, 1, 1));
	oci.addRestriction(MergedRestriction.getAllValuesRestriction(
		Meal.PROP_DISHES, Dish.MY_URI));

	// load MenuDay
	oci = createNewOntClassInfo(MenuDay.MY_URI, factory,
		NutritionFactory.FactoryIndex_MenuDay);
	oci.setResourceComment("Menu for a single day");
	oci.setResourceLabel("MenuDay");
	oci.addSuperClass(ManagedIndividual.MY_URI);
	oci.addDatatypeProperty(MenuDay.PROP_ID).setFunctional(); // Integer
	oci.addObjectProperty(MenuDay.PROP_DAYOFWEEK).setFunctional(); // DayOfWeek
	oci.addObjectProperty(MenuDay.PROP_MEALS).setFunctional(); // Meal
	oci.addRestriction(MergedRestriction
		.getAllValuesRestrictionWithCardinality(MenuDay.PROP_ID,
			TypeMapper.getDatatypeURI(Integer.class), 1, 1));
	oci.addRestriction(MergedRestriction
		.getAllValuesRestrictionWithCardinality(MenuDay.PROP_DAYOFWEEK,
			TypeMapper.getDatatypeURI(Boolean.class), 1, 1));
	oci.addRestriction(MergedRestriction.getAllValuesRestriction(
		MenuDay.PROP_MEALS, Dish.MY_URI));

	// ******* Declaration of enumeration classes of the ontology ******* //
	// ******* Add content to enumeration classes of the ontology ******* //
	OntClassInfoSetup oci_DietType = createNewAbstractOntClassInfo(DietType.MY_URI);
	oci_DietType.setResourceComment("");
	oci_DietType.setResourceLabel("DietType");
	oci_DietType.toEnumeration(new ManagedIndividual[] { DietType.Saxon,
		DietType.Mediterranean, DietType.Vegetarian, DietType.Vegan,
		DietType.Other });

	// ******* Declaration of regular classes of the ontology ******* //
	OntClassInfoSetup oci_NutritionalHabits = createNewOntClassInfo(
		NutritionalHabits.MY_URI, factory, 10);
	OntClassInfoSetup oci_NutritionalPreferences = createNewOntClassInfo(
		NutritionalPreferences.MY_URI, factory, 11);
	OntClassInfoSetup oci_NutritionalSubProfile = createNewOntClassInfo(
		NutritionalSubProfile.MY_URI, factory, 12);

	// ******* Add content to regular classes of the ontology ******* //

	oci_NutritionalHabits.setResourceComment("");
	oci_NutritionalHabits.setResourceLabel("NutritionalHabits");
	oci_NutritionalHabits.addSuperClass(ManagedIndividual.MY_URI);
	oci_NutritionalHabits.addDatatypeProperty(
		NutritionalHabits.PROP__CONSUMES_ALCOHOL).setFunctional();
	oci_NutritionalHabits.addRestriction(MergedRestriction
		.getAllValuesRestrictionWithCardinality(
			NutritionalHabits.PROP__CONSUMES_ALCOHOL,
			TypeMapper.getDatatypeURI(Boolean.class), 0, 1));

	oci_NutritionalHabits.addObjectProperty(
		NutritionalHabits.PROP_BREAKFAST_PLACE).setFunctional();
	oci_NutritionalHabits.addRestriction(MergedRestriction
		.getAllValuesRestrictionWithCardinality(
			NutritionalHabits.PROP_BREAKFAST_PLACE,
			Location.MY_URI, 0, 1));
	oci_NutritionalHabits.addObjectProperty(
		NutritionalHabits.PROP_LUNCH_PLACE).setFunctional();
	oci_NutritionalHabits.addRestriction(MergedRestriction
		.getAllValuesRestrictionWithCardinality(
			NutritionalHabits.PROP_LUNCH_PLACE, Location.MY_URI, 0,
			1));
	oci_NutritionalHabits.addObjectProperty(
		NutritionalHabits.PROP_DINNER_PLACE).setFunctional();
	oci_NutritionalHabits.addRestriction(MergedRestriction
		.getAllValuesRestrictionWithCardinality(
			NutritionalHabits.PROP_DINNER_PLACE, Location.MY_URI,
			0, 1));
	oci_NutritionalHabits.addObjectProperty(
		NutritionalHabits.PROP_AFTERDINNERSNACK_PLACE).setFunctional();
	oci_NutritionalHabits.addRestriction(MergedRestriction
		.getAllValuesRestrictionWithCardinality(
			NutritionalHabits.PROP_AFTERDINNERSNACK_PLACE,
			Location.MY_URI, 0, 1));
	oci_NutritionalHabits.addObjectProperty(
		NutritionalHabits.PROP_AFTERNOONSNACK_PLACE).setFunctional();
	oci_NutritionalHabits.addRestriction(MergedRestriction
		.getAllValuesRestrictionWithCardinality(
			NutritionalHabits.PROP_AFTERNOONSNACK_PLACE,
			Location.MY_URI, 0, 1));
	oci_NutritionalHabits.addObjectProperty(
		NutritionalHabits.PROP_MORNINGSNACK_PLACE).setFunctional();
	oci_NutritionalHabits.addRestriction(MergedRestriction
		.getAllValuesRestrictionWithCardinality(
			NutritionalHabits.PROP_MORNINGSNACK_PLACE,
			Location.MY_URI, 0, 1));

	oci_NutritionalHabits.addDatatypeProperty(
		NutritionalHabits.PROP__RECOMMENDED_DAILY_MEALS)
		.setFunctional();
	oci_NutritionalHabits.addRestriction(MergedRestriction
		.getAllValuesRestrictionWithCardinality(
			NutritionalHabits.PROP__RECOMMENDED_DAILY_MEALS,
			TypeMapper.getDatatypeURI(Integer.class), 0, 1));

	oci_NutritionalHabits.addDatatypeProperty(
		NutritionalHabits.PROP__GET_UP_HOUR).setFunctional();
	oci_NutritionalHabits.addRestriction(MergedRestriction
		.getAllValuesRestrictionWithCardinality(
			NutritionalHabits.PROP__GET_UP_HOUR,
			TypeMapper.getDatatypeURI(XMLGregorianCalendar.class),
			0, 1));

	oci_NutritionalHabits.addDatatypeProperty(
		NutritionalHabits.PROP__AFTERNOON_SNACK_HOUR).setFunctional();
	oci_NutritionalHabits.addRestriction(MergedRestriction
		.getAllValuesRestrictionWithCardinality(
			NutritionalHabits.PROP__AFTERNOON_SNACK_HOUR,
			TypeMapper.getDatatypeURI(XMLGregorianCalendar.class),
			0, 1));

	oci_NutritionalHabits.addDatatypeProperty(
		NutritionalHabits.PROP__RECOMMENDED_FOOD_INTAKE_CALORIES)
		.setFunctional();
	oci_NutritionalHabits
		.addRestriction(MergedRestriction
			.getAllValuesRestrictionWithCardinality(
				NutritionalHabits.PROP__RECOMMENDED_FOOD_INTAKE_CALORIES,
				TypeMapper.getDatatypeURI(Integer.class), 0, 1));

	oci_NutritionalHabits.addDatatypeProperty(
		NutritionalHabits.PROP__MORNING_SNACK_HOUR).setFunctional();
	oci_NutritionalHabits.addRestriction(MergedRestriction
		.getAllValuesRestrictionWithCardinality(
			NutritionalHabits.PROP__MORNING_SNACK_HOUR,
			TypeMapper.getDatatypeURI(XMLGregorianCalendar.class),
			0, 1));

	oci_NutritionalHabits.addDatatypeProperty(
		NutritionalHabits.PROP__LUNCH_HOUR).setFunctional();
	oci_NutritionalHabits.addRestriction(MergedRestriction
		.getAllValuesRestrictionWithCardinality(
			NutritionalHabits.PROP__LUNCH_HOUR,
			TypeMapper.getDatatypeURI(XMLGregorianCalendar.class),
			0, 1));

	oci_NutritionalHabits.addDatatypeProperty(
		NutritionalHabits.PROP__RELIGION_CONSTRAINT).setFunctional();
	oci_NutritionalHabits.addRestriction(MergedRestriction
		.getAllValuesRestrictionWithCardinality(
			NutritionalHabits.PROP__RELIGION_CONSTRAINT,
			TypeMapper.getDatatypeURI(String.class), 0, 1));

	oci_NutritionalHabits.addDatatypeProperty(
		NutritionalHabits.PROP__NUMBER_DAILY_MEALS).setFunctional();
	oci_NutritionalHabits.addRestriction(MergedRestriction
		.getAllValuesRestrictionWithCardinality(
			NutritionalHabits.PROP__NUMBER_DAILY_MEALS,
			TypeMapper.getDatatypeURI(Integer.class), 0, 1));

	oci_NutritionalHabits.addDatatypeProperty(
		NutritionalHabits.PROP__SELF_COOKING).setFunctional();
	oci_NutritionalHabits.addRestriction(MergedRestriction
		.getAllValuesRestrictionWithCardinality(
			NutritionalHabits.PROP__SELF_COOKING,
			TypeMapper.getDatatypeURI(Boolean.class), 0, 1));

	oci_NutritionalHabits.addObjectProperty(
		NutritionalHabits.PROP_DIET_TYPE).setFunctional();
	oci_NutritionalHabits
		.addRestriction(MergedRestriction
			.getAllValuesRestrictionWithCardinality(
				NutritionalHabits.PROP_DIET_TYPE,
				DietType.MY_URI, 0, 1));

	oci_NutritionalHabits.addDatatypeProperty(
		NutritionalHabits.PROP__CONSUMES_TOBACCO).setFunctional();
	oci_NutritionalHabits.addRestriction(MergedRestriction
		.getAllValuesRestrictionWithCardinality(
			NutritionalHabits.PROP__CONSUMES_TOBACCO,
			TypeMapper.getDatatypeURI(Boolean.class), 0, 1));

	oci_NutritionalHabits.addDatatypeProperty(
		NutritionalHabits.PROP__NUTRITIONAL_GOAL).setFunctional();
	oci_NutritionalHabits.addRestriction(MergedRestriction
		.getAllValuesRestrictionWithCardinality(
			NutritionalHabits.PROP__NUTRITIONAL_GOAL,
			TypeMapper.getDatatypeURI(String.class), 0, 1));

	oci_NutritionalHabits.addDatatypeProperty(
		NutritionalHabits.PROP__SELF_SHOPPING).setFunctional();
	oci_NutritionalHabits.addRestriction(MergedRestriction
		.getAllValuesRestrictionWithCardinality(
			NutritionalHabits.PROP__SELF_SHOPPING,
			TypeMapper.getDatatypeURI(Boolean.class), 0, 1));

	oci_NutritionalHabits.addDatatypeProperty(
		NutritionalHabits.PROP__TYPE_DAILY_MEALS).setFunctional();
	oci_NutritionalHabits.addRestriction(MergedRestriction
		.getAllValuesRestrictionWithCardinality(
			NutritionalHabits.PROP__TYPE_DAILY_MEALS,
			TypeMapper.getDatatypeURI(String.class), 0, 1));

	oci_NutritionalHabits.addDatatypeProperty(
		NutritionalHabits.PROP__CONSUMES_DRUGS).setFunctional();
	oci_NutritionalHabits.addRestriction(MergedRestriction
		.getAllValuesRestrictionWithCardinality(
			NutritionalHabits.PROP__CONSUMES_DRUGS,
			TypeMapper.getDatatypeURI(Boolean.class), 0, 1));

	oci_NutritionalHabits.addDatatypeProperty(
		NutritionalHabits.PROP__COMPANIONS_AT_TABLE).setFunctional();
	oci_NutritionalHabits.addRestriction(MergedRestriction
		.getAllValuesRestrictionWithCardinality(
			NutritionalHabits.PROP__COMPANIONS_AT_TABLE,
			TypeMapper.getDatatypeURI(Boolean.class), 0, 1));

	oci_NutritionalHabits.addDatatypeProperty(
		NutritionalHabits.PROP__NUMBER_COMPANIONS_AT_TABLE)
		.setFunctional();
	oci_NutritionalHabits.addRestriction(MergedRestriction
		.getAllValuesRestrictionWithCardinality(
			NutritionalHabits.PROP__NUMBER_COMPANIONS_AT_TABLE,
			TypeMapper.getDatatypeURI(Integer.class), 0, 1));

	oci_NutritionalHabits.addDatatypeProperty(
		NutritionalHabits.PROP__BREAKFAST_HOUR).setFunctional();
	oci_NutritionalHabits.addRestriction(MergedRestriction
		.getAllValuesRestrictionWithCardinality(
			NutritionalHabits.PROP__BREAKFAST_HOUR,
			TypeMapper.getDatatypeURI(XMLGregorianCalendar.class),
			0, 1));

	oci_NutritionalHabits.addDatatypeProperty(
		NutritionalHabits.PROP__BED_HOUR).setFunctional();
	oci_NutritionalHabits.addRestriction(MergedRestriction
		.getAllValuesRestrictionWithCardinality(
			NutritionalHabits.PROP__BED_HOUR,
			TypeMapper.getDatatypeURI(XMLGregorianCalendar.class),
			0, 1));

	oci_NutritionalHabits.addDatatypeProperty(
		NutritionalHabits.PROP__AFTER_DINNER_SNACK_HOUR)
		.setFunctional();
	oci_NutritionalHabits.addRestriction(MergedRestriction
		.getAllValuesRestrictionWithCardinality(
			NutritionalHabits.PROP__AFTER_DINNER_SNACK_HOUR,
			TypeMapper.getDatatypeURI(XMLGregorianCalendar.class),
			0, 1));

	oci_NutritionalHabits.addDatatypeProperty(
		NutritionalHabits.PROP__DINNER_HOUR).setFunctional();
	oci_NutritionalHabits.addRestriction(MergedRestriction
		.getAllValuesRestrictionWithCardinality(
			NutritionalHabits.PROP__DINNER_HOUR,
			TypeMapper.getDatatypeURI(XMLGregorianCalendar.class),
			0, 1));

	oci_NutritionalPreferences.setResourceComment("");
	oci_NutritionalPreferences.setResourceLabel("NutritionalPreferences");
	oci_NutritionalPreferences.addSuperClass(ManagedIndividual.MY_URI);
	oci_NutritionalPreferences.addDatatypeProperty(
		NutritionalPreferences.PROP__PREF_CONVENIENCE).setFunctional();
	oci_NutritionalPreferences.addRestriction(MergedRestriction
		.getAllValuesRestrictionWithCardinality(
			NutritionalPreferences.PROP__PREF_CONVENIENCE,
			TypeMapper.getDatatypeURI(String.class), 1, 1));

	oci_NutritionalPreferences.addDatatypeProperty(
		NutritionalPreferences.PROP__PREFERRED_CUISINES)
		.setFunctional();
	oci_NutritionalPreferences.addRestriction(MergedRestriction
		.getAllValuesRestrictionWithCardinality(
			NutritionalPreferences.PROP__PREFERRED_CUISINES,
			TypeMapper.getDatatypeURI(String.class), 0, 1));

	oci_NutritionalPreferences.addDatatypeProperty(
		NutritionalPreferences.PROP__MEDICINE_FOOD_INTERACTIONS)
		.setFunctional();
	oci_NutritionalPreferences
		.addRestriction(MergedRestriction
			.getAllValuesRestrictionWithCardinality(
				NutritionalPreferences.PROP__MEDICINE_FOOD_INTERACTIONS,
				TypeMapper.getDatatypeURI(String.class), 0, 1));

	oci_NutritionalPreferences.addDatatypeProperty(
		NutritionalPreferences.PROP__PREFERRED_COOKING_TECHNIQUES)
		.setFunctional();
	oci_NutritionalPreferences
		.addRestriction(MergedRestriction
			.getAllValuesRestrictionWithCardinality(
				NutritionalPreferences.PROP__PREFERRED_COOKING_TECHNIQUES,
				TypeMapper.getDatatypeURI(String.class), 0, 1));

	oci_NutritionalPreferences.addObjectProperty(
		NutritionalPreferences.PROP_FOOD_DISLIKES).setFunctional();
	oci_NutritionalPreferences
		.addRestriction(MergedRestriction.getAllValuesRestriction(
			NutritionalPreferences.PROP_FOOD_DISLIKES, Food.MY_URI));

	oci_NutritionalPreferences.addObjectProperty(
		NutritionalPreferences.PROP_FAVOURITE_RECIPES).setFunctional();
	oci_NutritionalPreferences.addRestriction(MergedRestriction
		.getAllValuesRestriction(
			NutritionalPreferences.PROP_FAVOURITE_RECIPES,
			Recipe.MY_URI));

	oci_NutritionalSubProfile.setResourceComment("");
	oci_NutritionalSubProfile.setResourceLabel("NutritionalSubProfile");
	oci_NutritionalSubProfile.addSuperClass(ManagedIndividual.MY_URI);
	oci_NutritionalSubProfile.addObjectProperty(
		NutritionalSubProfile.PROP_NUTRITIONAL_HABITS).setFunctional();
	oci_NutritionalSubProfile.addRestriction(MergedRestriction
		.getAllValuesRestrictionWithCardinality(
			NutritionalSubProfile.PROP_NUTRITIONAL_HABITS,
			NutritionalHabits.MY_URI, 1, 1));

	oci_NutritionalSubProfile.addObjectProperty(
		NutritionalSubProfile.PROP_NUTRITIONAL_PREFERENCES)
		.setFunctional();
	oci_NutritionalSubProfile.addRestriction(MergedRestriction
		.getAllValuesRestrictionWithCardinality(
			NutritionalSubProfile.PROP_NUTRITIONAL_PREFERENCES,
			NutritionalPreferences.MY_URI, 1, 1));

	/*
	 * // load GetRecipeService oci =
	 * createNewOntClassInfo(NutritionService.MY_URI, factory,
	 * NutritionFactory.FactoryIndex_NutritionService); oci
	 * .setResourceComment
	 * ("The class of services controling nutrition services");
	 * oci.setResourceLabel("Nutrition"); oci.addSuperClass(Service.MY_URI);
	 * oci.addObjectProperty(NutritionService.PROP_SERVICE_GET_RECIPE);
	 * oci.addRestriction(MergedRestriction.getAllValuesRestriction(
	 * NutritionService.PROP_SERVICE_GET_RECIPE, NutritionService.MY_URI));
	 */

	// load NutritionService
	oci = createNewOntClassInfo(NutritionService.MY_URI, factory,
		NutritionFactory.FactoryIndex_NutritionService);
	oci
		.setResourceComment("The class of services controling nutrition services");
	oci.setResourceLabel("NutritionService");
	oci.addSuperClass(Service.MY_URI);
	// getRecipe
	oci.addObjectProperty(NutritionService.PROP_OBTAINS_RECIPE);
	oci.addRestriction(MergedRestriction.getAllValuesRestriction(
		NutritionService.PROP_OBTAINS_RECIPE, Recipe.MY_URI));
	// getTodayMenu
	oci.addObjectProperty(NutritionService.PROP_OBTAINS_MENU);
	oci.addRestriction(MergedRestriction.getAllValuesRestriction(
		NutritionService.PROP_OBTAINS_MENU, MenuDay.MY_URI));
	System.out.println("Nutri: ontology created");
    }
}
