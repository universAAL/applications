package org.universAAL.ontology.nutrition.profile;

import javax.xml.datatype.XMLGregorianCalendar;
import org.universAAL.middleware.owl.ManagedIndividual;
import org.universAAL.ontology.location.Location;
import org.universAAL.ontology.nutrition.NutritionOntology;

public class NutritionalHabits extends ManagedIndividual {
    public static final String MY_URI = NutritionOntology.NAMESPACE
	    + "NutritionalHabits";
    public static final String PROP__CONSUMES_ALCOHOL = NutritionOntology.NAMESPACE
	    + "ConsumesAlcohol";
    public static final String PROP_BREAKFAST_PLACE = NutritionOntology.NAMESPACE
	    + "breakfastPlace";
    public static final String PROP_LUNCH_PLACE = NutritionOntology.NAMESPACE
	    + "lucnhPlace";
    public static final String PROP_DINNER_PLACE = NutritionOntology.NAMESPACE
	    + "dinnerPlace";
    public static final String PROP_MORNINGSNACK_PLACE = NutritionOntology.NAMESPACE
	    + "morningSnackPlace";
    public static final String PROP_AFTERNOONSNACK_PLACE = NutritionOntology.NAMESPACE
	    + "afternoonSnackPlace";
    public static final String PROP_AFTERDINNERSNACK_PLACE = NutritionOntology.NAMESPACE
	    + "afterDinnerSnackPlace";
    public static final String PROP__RECOMMENDED_DAILY_MEALS = NutritionOntology.NAMESPACE
	    + "RecommendedDailyMeals";
    public static final String PROP__GET_UP_HOUR = NutritionOntology.NAMESPACE
	    + "GetUpHour";
    public static final String PROP__AFTERNOON_SNACK_HOUR = NutritionOntology.NAMESPACE
	    + "AfternoonSnackHour";
    public static final String PROP__RECOMMENDED_FOOD_INTAKE_CALORIES = NutritionOntology.NAMESPACE
	    + "RecommendedFoodIntakeCalories";
    public static final String PROP__MORNING_SNACK_HOUR = NutritionOntology.NAMESPACE
	    + "MorningSnackHour";
    public static final String PROP__LUNCH_HOUR = NutritionOntology.NAMESPACE
	    + "LunchHour";
    public static final String PROP__RELIGION_CONSTRAINT = NutritionOntology.NAMESPACE
	    + "ReligionConstraint";
    public static final String PROP__NUMBER_DAILY_MEALS = NutritionOntology.NAMESPACE
	    + "NumberDailyMeals";
    public static final String PROP__SELF_COOKING = NutritionOntology.NAMESPACE
	    + "SelfCooking";
    public static final String PROP_DIET_TYPE = NutritionOntology.NAMESPACE
	    + "dietType";
    public static final String PROP__CONSUMES_TOBACCO = NutritionOntology.NAMESPACE
	    + "ConsumesTobacco";
    public static final String PROP__NUTRITIONAL_GOAL = NutritionOntology.NAMESPACE
	    + "NutritionalGoal";
    public static final String PROP__SELF_SHOPPING = NutritionOntology.NAMESPACE
	    + "SelfShopping";
    public static final String PROP__TYPE_DAILY_MEALS = NutritionOntology.NAMESPACE
	    + "TypeDailyMeals";
    public static final String PROP__CONSUMES_DRUGS = NutritionOntology.NAMESPACE
	    + "ConsumesDrugs";
    public static final String PROP__COMPANIONS_AT_TABLE = NutritionOntology.NAMESPACE
	    + "CompanionsAtTable";
    public static final String PROP__NUMBER_COMPANIONS_AT_TABLE = NutritionOntology.NAMESPACE
	    + "NumberCompanionsAtTable";
    public static final String PROP__BREAKFAST_HOUR = NutritionOntology.NAMESPACE
	    + "BreakfastHour";
    public static final String PROP__BED_HOUR = NutritionOntology.NAMESPACE
	    + "BedHour";
    public static final String PROP__AFTER_DINNER_SNACK_HOUR = NutritionOntology.NAMESPACE
	    + "AfterDinnerSnackHour";
    public static final String PROP__DINNER_HOUR = NutritionOntology.NAMESPACE
	    + "DinnerHour";

    public NutritionalHabits() {
	super();
    }

    public NutritionalHabits(String uri) {
	super(uri);
    }

    public String getClassURI() {
	return MY_URI;
    }

    public int getPropSerializationType(String arg0) {
	// TODO Implement or if for Device subclasses: remove
	return 0;
    }

    public boolean isWellFormed() {
	return true && hasProperty(PROP__CONSUMES_ALCOHOL)
		&& hasProperty(PROP_BREAKFAST_PLACE)
		&& hasProperty(PROP_LUNCH_PLACE)
		&& hasProperty(PROP_DINNER_PLACE)
		&& hasProperty(PROP_AFTERDINNERSNACK_PLACE)
		&& hasProperty(PROP_AFTERNOONSNACK_PLACE)
		&& hasProperty(PROP_MORNINGSNACK_PLACE)
		&& hasProperty(PROP__RECOMMENDED_DAILY_MEALS)
		&& hasProperty(PROP__GET_UP_HOUR)
		&& hasProperty(PROP__AFTERNOON_SNACK_HOUR)
		&& hasProperty(PROP__RECOMMENDED_FOOD_INTAKE_CALORIES)
		&& hasProperty(PROP__MORNING_SNACK_HOUR)
		&& hasProperty(PROP__LUNCH_HOUR)
		&& hasProperty(PROP__RELIGION_CONSTRAINT)
		&& hasProperty(PROP__NUMBER_DAILY_MEALS)
		&& hasProperty(PROP__SELF_COOKING)
		&& hasProperty(PROP_DIET_TYPE)
		&& hasProperty(PROP__CONSUMES_TOBACCO)
		&& hasProperty(PROP__NUTRITIONAL_GOAL)
		&& hasProperty(PROP__SELF_SHOPPING)
		&& hasProperty(PROP__TYPE_DAILY_MEALS)
		&& hasProperty(PROP__CONSUMES_DRUGS)
		&& hasProperty(PROP__COMPANIONS_AT_TABLE)
		&& hasProperty(PROP__NUMBER_COMPANIONS_AT_TABLE)
		&& hasProperty(PROP__BREAKFAST_HOUR)
		&& hasProperty(PROP__BED_HOUR)
		&& hasProperty(PROP__AFTER_DINNER_SNACK_HOUR)
		&& hasProperty(PROP__DINNER_HOUR);
    }

    public XMLGregorianCalendar getAfterDinnerSnackHour() {
	return (XMLGregorianCalendar) getProperty(PROP__AFTER_DINNER_SNACK_HOUR);
    }

    public void setAfterDinnerSnackHour(XMLGregorianCalendar newPropValue) {
	if (newPropValue != null)
	    changeProperty(PROP__AFTER_DINNER_SNACK_HOUR, newPropValue);
    }

    public XMLGregorianCalendar getAfternoonSnackHour() {
	return (XMLGregorianCalendar) getProperty(PROP__AFTERNOON_SNACK_HOUR);
    }

    public void setAfternoonSnackHour(XMLGregorianCalendar newPropValue) {
	if (newPropValue != null)
	    changeProperty(PROP__AFTERNOON_SNACK_HOUR, newPropValue);
    }

    public XMLGregorianCalendar getBedHour() {
	return (XMLGregorianCalendar) getProperty(PROP__BED_HOUR);
    }

    public void setBedHour(XMLGregorianCalendar newPropValue) {
	if (newPropValue != null)
	    changeProperty(PROP__BED_HOUR, newPropValue);
    }

    public XMLGregorianCalendar getBreakfastHour() {
	return (XMLGregorianCalendar) getProperty(PROP__BREAKFAST_HOUR);
    }

    public void setBreakfastHour(XMLGregorianCalendar newPropValue) {
	if (newPropValue != null)
	    changeProperty(PROP__BREAKFAST_HOUR, newPropValue);
    }

    public XMLGregorianCalendar getDinnerHour() {
	return (XMLGregorianCalendar) getProperty(PROP__DINNER_HOUR);
    }

    public void setDinnerHour(XMLGregorianCalendar newPropValue) {
	if (newPropValue != null)
	    changeProperty(PROP__DINNER_HOUR, newPropValue);
    }

    public XMLGregorianCalendar getGetUpHour() {
	return (XMLGregorianCalendar) getProperty(PROP__GET_UP_HOUR);
    }

    public void setGetUpHour(XMLGregorianCalendar newPropValue) {
	if (newPropValue != null)
	    changeProperty(PROP__GET_UP_HOUR, newPropValue);
    }

    public XMLGregorianCalendar getLunchHour() {
	return (XMLGregorianCalendar) getProperty(PROP__LUNCH_HOUR);
    }

    public void setLunchHour(XMLGregorianCalendar newPropValue) {
	if (newPropValue != null)
	    changeProperty(PROP__LUNCH_HOUR, newPropValue);
    }

    public XMLGregorianCalendar getMorningSnackHour() {
	return (XMLGregorianCalendar) getProperty(PROP__MORNING_SNACK_HOUR);
    }

    public void setMorningSnackHour(XMLGregorianCalendar newPropValue) {
	if (newPropValue != null)
	    changeProperty(PROP__MORNING_SNACK_HOUR, newPropValue);
    }

    public boolean isCompanionsAtTable() {
	Boolean b = (Boolean) getProperty(PROP__COMPANIONS_AT_TABLE);
	return (b == null) ? false : b.booleanValue();
    }

    public void setCompanionsAtTable(boolean newPropValue) {
	changeProperty(PROP__COMPANIONS_AT_TABLE, new Boolean(newPropValue));
    }

    public boolean isConsumesAlcohol() {
	Boolean b = (Boolean) getProperty(PROP__CONSUMES_ALCOHOL);
	return (b == null) ? false : b.booleanValue();
    }

    public void setConsumesAlcohol(boolean newPropValue) {
	changeProperty(PROP__CONSUMES_ALCOHOL, new Boolean(newPropValue));
    }

    public boolean isConsumesTobacco() {
	Boolean b = (Boolean) getProperty(PROP__CONSUMES_TOBACCO);
	return (b == null) ? false : b.booleanValue();
    }

    public void setConsumesTobacco(boolean newPropValue) {
	changeProperty(PROP__CONSUMES_TOBACCO, new Boolean(newPropValue));
    }

    public boolean isConsumesDrugs() {
	Boolean b = (Boolean) getProperty(PROP__CONSUMES_DRUGS);
	return (b == null) ? false : b.booleanValue();
    }

    public void setConsumesDrugs(boolean newPropValue) {
	changeProperty(PROP__CONSUMES_DRUGS, new Boolean(newPropValue));
    }

    public boolean isSelfCooking() {
	Boolean b = (Boolean) getProperty(PROP__SELF_COOKING);
	return (b == null) ? false : b.booleanValue();
    }

    public void setSelfCooking(boolean newPropValue) {
	changeProperty(PROP__SELF_COOKING, new Boolean(newPropValue));
    }

    public boolean isSelfShopping() {
	Boolean b = (Boolean) getProperty(PROP__SELF_SHOPPING);
	return (b == null) ? false : b.booleanValue();
    }

    public void setSelfShopping(boolean newPropValue) {
	changeProperty(PROP__SELF_SHOPPING, new Boolean(newPropValue));
    }

    public int getNumberCompanionsAtTable() {
	Integer i = (Integer) getProperty(PROP__NUMBER_COMPANIONS_AT_TABLE);
	return (i == null) ? 0 : i.intValue();
    }

    public void setNumberCompanionsAtTable(int newPropValue) {
	changeProperty(PROP__NUMBER_COMPANIONS_AT_TABLE, new Integer(
		newPropValue));
    }

    public int getNumberDailyMeals() {
	Integer i = (Integer) getProperty(PROP__NUMBER_DAILY_MEALS);
	return (i == null) ? 0 : i.intValue();
    }

    public void setNumberDailyMeals(int newPropValue) {
	changeProperty(PROP__NUMBER_DAILY_MEALS, new Integer(newPropValue));
    }

    public String getNutritionalGoal() {
	return (String) getProperty(PROP__NUTRITIONAL_GOAL);
    }

    public void setNutritionalGoal(String newPropValue) {
	if (newPropValue != null)
	    changeProperty(PROP__NUTRITIONAL_GOAL, newPropValue);
    }

    public int getRecommendedDailyMeals() {
	Integer i = (Integer) getProperty(PROP__RECOMMENDED_DAILY_MEALS);
	return (i == null) ? 0 : i.intValue();
    }

    public void setRecommendedDailyMeals(int newPropValue) {
	changeProperty(PROP__RECOMMENDED_DAILY_MEALS, new Integer(newPropValue));
    }

    public int getRecommendedFoodIntakeCalories() {
	Integer i = (Integer) getProperty(PROP__RECOMMENDED_FOOD_INTAKE_CALORIES);
	return (i == null) ? 0 : i.intValue();
    }

    public void setRecommendedFoodIntakeCalories(int newPropValue) {
	changeProperty(PROP__RECOMMENDED_FOOD_INTAKE_CALORIES, new Integer(
		newPropValue));
    }

    public String getReligionConstraint() {
	return (String) getProperty(PROP__RELIGION_CONSTRAINT);
    }

    public void setReligionConstraint(String newPropValue) {
	if (newPropValue != null)
	    changeProperty(PROP__RELIGION_CONSTRAINT, newPropValue);
    }

    public String getTypeDailyMeals() {
	return (String) getProperty(PROP__TYPE_DAILY_MEALS);
    }

    public void setTypeDailyMeals(String newPropValue) {
	if (newPropValue != null)
	    changeProperty(PROP__TYPE_DAILY_MEALS, newPropValue);
    }

    public Location getBreakfastPlace() {
	return (Location) getProperty(PROP_BREAKFAST_PLACE);
    }

    public void setBreakfastPlace(Location newPropValue) {
	if (newPropValue != null)
	    changeProperty(PROP_BREAKFAST_PLACE, newPropValue);
    }

    public Location getLucnhPlace() {
	return (Location) getProperty(PROP_LUNCH_PLACE);
    }

    public void setLunchPlace(Location newPropValue) {
	if (newPropValue != null)
	    changeProperty(PROP_LUNCH_PLACE, newPropValue);
    }

    public Location getDinnertPlace() {
	return (Location) getProperty(PROP_DINNER_PLACE);
    }

    public void setDinnertPlace(Location newPropValue) {
	if (newPropValue != null)
	    changeProperty(PROP_DINNER_PLACE, newPropValue);
    }

    public Location getAfterDinnerSnackPlace() {
	return (Location) getProperty(PROP_AFTERDINNERSNACK_PLACE);
    }

    public void setAfterDinnerSnackPlace(Location newPropValue) {
	if (newPropValue != null)
	    changeProperty(PROP_AFTERDINNERSNACK_PLACE, newPropValue);
    }

    public Location getAfternoonSnackPlace() {
	return (Location) getProperty(PROP_AFTERNOONSNACK_PLACE);
    }

    public void setAfternoonSnackPlace(Location newPropValue) {
	if (newPropValue != null)
	    changeProperty(PROP_AFTERNOONSNACK_PLACE, newPropValue);
    }

    public Location getMorningSnackPlace() {
	return (Location) getProperty(PROP_MORNINGSNACK_PLACE);
    }

    public void setMorningSnackPlace(Location newPropValue) {
	if (newPropValue != null)
	    changeProperty(PROP_MORNINGSNACK_PLACE, newPropValue);
    }

    public DietType getDietType() {
	return (DietType) getProperty(PROP_DIET_TYPE);
    }

    public void setDietType(DietType newPropValue) {
	if (newPropValue != null)
	    changeProperty(PROP_DIET_TYPE, newPropValue);
    }
}
