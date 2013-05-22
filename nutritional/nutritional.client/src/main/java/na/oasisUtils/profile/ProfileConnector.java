package na.oasisUtils.profile;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import na.miniDao.profile.PatientList;
import na.oasisUtils.ami.AmiConnector;
import na.oasisUtils.trustedSecurityNetwork.TSFConnector;
import na.utils.NP;
import na.utils.OASIS_ServiceUnavailable;
import na.utils.ServiceInterface;
import na.utils.NP.Common;
import na.utils.NP.Nutrition;
import na.utils.NP.Nutrition.Anthropometrics;
import na.utils.NP.Nutrition.Habits;
import na.utils.NP.Nutrition.Health;
import na.utils.NP.Nutrition.NutriPreferences;
import na.utils.Utils;
import na.ws.NutriSecurityException;
import na.ws.NutritionalAdvisorProxy;
import na.ws.TokenExpiredException;
import na.ws.UProperty;
import na.ws.UPropertyValues;
import na.ws.UserNutritionalProfile;
import na.ws.extra.User;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.universAAL.ontology.nutrition.profile.NutritionalHabits;

public class ProfileConnector {
    private static Log log = LogFactory.getLog(ProfileConnector.class);
    private static ProfileConnector instance;
    private static UserNutritionalProfile nutritionalProfile;
    private static Map<String, UProperty> map;

    private String codeLang;

    // private int langID;

    private ProfileConnector() {
    }

    public static void closeProfile() {
    }

    /**
     * Gets the single instance of ProfileConnector.
     * 
     * @return single instance of ProfileConnector
     */
    public static ProfileConnector getInstance() {
	if (instance == null) {
	    instance = new ProfileConnector();
	}
	// log.info("Profile: running: "+ instance.frontEnd.isRunning());
	return instance;
    }

    private UProperty getUProperty(String propertyName) {
	if (ProfileConnector.map != null) {
	    UProperty o = map.get(propertyName);
	    if (o != null) {
		Utils.println("property found: " + propertyName);
		return o;
	    } else {
		Utils.println("property NOT found: " + propertyName);
	    }
	} else {
	    Utils.println("MAP IS NULL!");
	}
	return null;
    }

    /*
     * C O M M O N P R O F I L E
     */

    public int getUserID() {
	String property = Common.USER_ID;
	UProperty myProperty = this.getUProperty(property);
	if (myProperty != null) {
	    return Integer.parseInt(myProperty.getValues(0).getValue());
	} else {
	    return 1;
	}
    }

    public String getName() {
	String property = Common.FIRST_NAME;
	UProperty myProperty = this.getUProperty(property);
	return myProperty.getValues(0).getValue();
    }

    public String getSurname() {
	String property = Common.LAST_NAME;
	UProperty myProperty = this.getUProperty(property);
	return myProperty.getValues(0).getValue();
    }

    // public List<String> getSpokenLanguages() {
    // String property = Common.SPOKEN_LANGUAGES;
    // UProperty myProperty = this.getUProperty(property);
    // List<String> lista = new ArrayList<String>();
    // for (UPropertyValues e : myProperty.getValues()) {
    // if (e!=null)
    // lista.add(e.getValue());
    // }
    // return lista;
    // }

    public String getScreenLanguage() {
	this.codeLang = "GB";
	return this.codeLang;
	// if (this.codeLang!=null)
	// return this.codeLang;
	// String property = Common.PREFERRED_LANGUAGE;
	// GenericProfileProperty myProperty = this.getProperty(property);
	// String val = myProperty.getFirstValue();
	// this.codeLang = val;
	// log.info("Profile: Preferred language is: "+val);
	// return val;
    }

    public int getUserAge() {
	String property = Common.AGE;
	UProperty myProperty = this.getUProperty(property);
	return new Integer(myProperty.getValues(0).getValue());
    }

    public String getPreferredLanguage() {
	String property = "/common/preferred_language";
	UProperty myProperty = this.getUProperty(property);
	return myProperty.getValues(0).getValue();
    }

    public String getEmail() {
	String property = Common.EMAIL;
	UProperty myProperty = this.getUProperty(property);
	return myProperty.getValues(0).getValue();
    }

    /*
     * N U T R I T I O N A L P R O F I L E
     */

    public String getCarerEmail() {
	String property = Nutrition.CARER_EMAIL;
	UProperty myProperty = this.getUProperty(property);
	return myProperty.getValues(0).getValue();
    }

    public String getDietType() {
	String property = Habits.DIET_TYPE;
	UProperty myProperty = this.getUProperty(property);
	return myProperty.getValues(0).getValue();
    }

    public String getBreakfastTime() {
	String property = Habits.BREAKFAST_HOUR;
	UProperty myProperty = this.getUProperty(property);
	return myProperty.getValues(0).getValue();
    }

    public String getLunchTime() {
	String property = Habits.LUNCH_HOUR;
	UProperty myProperty = this.getUProperty(property);
	return myProperty.getValues(0).getValue();
    }

    public String getDinnerTime() {
	String property = Habits.DINNER_HOUR;
	UProperty myProperty = this.getUProperty(property);
	return myProperty.getValues(0).getValue();
    }

    public List<String> getPreferred_foods() {
	String property = NutriPreferences.PREFERRRED_FOODS;
	UProperty myProperty = this.getUProperty(property);
	List<String> lista = new ArrayList<String>();
	if (myProperty != null) {
	    for (UPropertyValues e : myProperty.getValues()) {
		if (e != null)
		    lista.add(e.getValue());
	    }
	}
	return lista;
    }

    public List<String> getFood_Dislikes() {
	String property = NutriPreferences.FOOD_DISLIKES;
	UProperty myProperty = this.getUProperty(property);
	List<String> lista = new ArrayList<String>();
	if (myProperty != null) {
	    for (UPropertyValues e : myProperty.getValues()) {
		if (e != null)
		    lista.add(e.getValue());
	    }
	}
	return lista;
    }

    public boolean addFood_Dislikes(String food) {
	List<String> foods = getFood_Dislikes();
	foods.add(food);
	try {
	    // return setUserValue(NutriPreferences.FOOD_DISLIKES, foods);
	    return setUserValue(Habits.AFTERDINNER_SNACK_HOUR, "23.00");
	    // return (getPatientsList()!=null);
	    // return (getPatientID()!=null);
	    // return
	    // setUserValue(NutritionalHabits.PROP__AFTER_DINNER_SNACK_HOUR, ,
	    // foods);
	} catch (OASIS_ServiceUnavailable e) {
	    e.printStackTrace();
	    return false;
	}
    }

    private boolean setUserValue(String prop, String values)
	    throws OASIS_ServiceUnavailable {
	AmiConnector ami = AmiConnector.getAMI();
	if (ami != null) {
	    Object[] input = { TSFConnector.getInstance().getToken(),
		    getUserID(), prop, values };// ????????????
	    String out = (String) ami.invokeOperation(
		    ServiceInterface.DOMAIN_Nutrition,
		    ServiceInterface.OP_SetProfileProperty, input, false);

	    if (out != null && out.equalsIgnoreCase("success")) {
		return true;
	    } else {
		log.info("Couldn't set the property");
	    }
	}
	return false;
    }

    public PatientList[] getPatientsList() throws OASIS_ServiceUnavailable {
	AmiConnector ami = AmiConnector.getAMI();
	if (ami != null) {
	    Object[] input = { TSFConnector.getInstance().getToken() };// ????????????
	    Object out = ami.invokeOperation(ServiceInterface.DOMAIN_Nutrition,
		    ServiceInterface.OP_GetPatientsList, input, false);

	    if (out != null) {
		return (PatientList[]) out;
	    } else {
		log.info("Couldn't get the list");
	    }
	}
	return null;
    }

    public User getPatientID() throws OASIS_ServiceUnavailable {
	AmiConnector ami = AmiConnector.getAMI();
	if (ami != null) {
	    Object[] input = { "version 1.0", getNutritionalUsername(),
		    getNutritionalPassword() };
	    Object out = ami.invokeOperation(ServiceInterface.DOMAIN_Nutrition,
		    ServiceInterface.OP_GetTokenX, input, false);

	    if (out != null) {
		return (User) out;
	    } else {
		log.info("Couldn't get the id");
	    }
	}
	return null;
    }

    private boolean setUserValue(String prop, int type, String propParent,
	    String propChild, String value) throws OASIS_ServiceUnavailable {
	AmiConnector ami = AmiConnector.getAMI();
	if (ami != null) {
	    Object[] input = { TSFConnector.getInstance().getToken(), type,
		    propParent, propChild, getUserID(), value, false };// ????????????
	    String out = (String) ami.invokeOperation(
		    ServiceInterface.DOMAIN_Nutrition,
		    ServiceInterface.OP_SetSingleProfileProperty, input, false);

	    if (out != null && out.equalsIgnoreCase("success")) {
		return true;
	    } else {
		log.info("Couldn't set the property");
	    }
	}
	return false;
    }

    public List<String> getFavourite_Recipes() {
	String property = NutriPreferences.FAVOURITE_RECIPES;
	UProperty myProperty = this.getUProperty(property);
	List<String> lista = new ArrayList<String>();
	for (UPropertyValues e : myProperty.getValues()) {
	    if (e != null)
		lista.add(e.getValue());
	}
	return lista;
    }

    public List<String> getFood_Allergies() {
	String property = Health.FOOD_ALLERGIES;
	UProperty myProperty = this.getUProperty(property);
	List<String> lista = new ArrayList<String>();
	for (UPropertyValues e : myProperty.getValues()) {
	    if (e != null)
		lista.add(e.getValue());
	}
	return lista;
    }

    public List<String> getLocalChronic_diseases() {
	String property = Health.CHRONIC_DISEASES;
	UProperty myProperty = this.getUProperty(property);
	List<String> lista = new ArrayList<String>();
	for (UPropertyValues e : myProperty.getValues()) {
	    if (e != null)
		lista.add(e.getValue());
	}
	return lista;
    }

    public List<String> getExternalChronic_diseases() {
	List<String> listado = new ArrayList<String>();
	if (this.getHasExternalCholesterol() == true)
	    listado.add("Cholesterol");
	if (this.getHasDiabetes() == true)
	    listado.add("Diabetes");
	if (this.getHasCardiovascular() == true)
	    listado.add("Cardiovascular");
	if (this.getHasOther() == true)
	    listado.add("Other");
	if (this.getHasOverweight() == true)
	    listado.add("Overweight");
	if (this.getHasRespiratory() == true)
	    listado.add("Respiratory problem");
	return listado;
    }

    public boolean getHasLocalCholesterol() {
	String property = NP.Nutrition.Health.HEALTH_CONDITION_CHOLESTEROL;
	UProperty myProperty = this.getUProperty(property);
	if (myProperty != null && myProperty.getValues() != null
		&& myProperty.getValues(0).getValue().length() > 0)
	    return true;
	else
	    return false;
    }

    public boolean getHasExternalCholesterol() {
	String property = NP.External.HealthMonitoring.CONDITION_CHOLESTEROL;
	UProperty myProperty = this.getUProperty(property);
	if (myProperty != null && myProperty.getValues() != null
		&& myProperty.getValues(0).getValue().length() > 0)
	    return true;
	else
	    return false;
    }

    public boolean getHasDiabetes() {
	String property = NP.External.HealthMonitoring.CONDITION_DIABETES;
	UProperty myProperty = this.getUProperty(property);
	if (myProperty != null && myProperty.getValues() != null
		&& myProperty.getValues(0).getValue().length() > 0)
	    return true;
	else
	    return false;
    }

    public boolean getHasCardiovascular() {
	String property = NP.External.HealthMonitoring.CONDITION_CARDIO;
	UProperty myProperty = this.getUProperty(property);
	if (myProperty != null && myProperty.getValues() != null
		&& myProperty.getValues(0).getValue().length() > 0)
	    return true;
	else
	    return false;
    }

    public boolean getHasOther() {
	String property = NP.External.HealthMonitoring.CONDITION_OTHER;
	UProperty myProperty = this.getUProperty(property);
	if (myProperty != null && myProperty.getValues() != null
		&& myProperty.getValues(0).getValue().length() > 0)
	    return true;
	else
	    return false;
    }

    public boolean getHasOverweight() {
	String property = NP.External.HealthMonitoring.CONDITION_OVERWEIGHT;
	UProperty myProperty = this.getUProperty(property);
	if (myProperty != null && myProperty.getValues() != null
		&& myProperty.getValues(0).getValue().length() > 0)
	    return true;
	else
	    return false;
    }

    public boolean getHasRespiratory() {
	String property = NP.External.HealthMonitoring.CONDITION_RESPIRATORY;
	UProperty myProperty = this.getUProperty(property);
	if (myProperty != null && myProperty.getValues() != null
		&& myProperty.getValues(0).getValue().length() > 0)
	    return true;
	else
	    return false;
    }

    public List<String> getFood_Intolerances() {
	String property = Health.FOOD_INTOLERANCES;
	UProperty myProperty = this.getUProperty(property);
	List<String> lista = new ArrayList<String>();
	for (UPropertyValues e : myProperty.getValues()) {
	    if (e != null)
		lista.add(e.getValue());
	}
	return lista;
    }

    public String getHeight() {
	String property = Anthropometrics.HEIGHT;
	UProperty myProperty = this.getUProperty(property);
	return myProperty.getValues(0).getValue();
    }

    public String getWeight() {
	String property = Anthropometrics.WEIGHT;
	UProperty myProperty = this.getUProperty(property);
	return myProperty.getValues(0).getValue();
    }

    public String getSmokes() {
	String property = NP.Nutrition.Habits.CONSUMES_TOBACCO;
	UProperty myProperty = this.getUProperty(property);
	return myProperty.getValues(0).getValue();
    }

    public String getConsumesAlcohol() {
	String property = NP.Nutrition.Habits.CONSUMES_ALCOHOL;
	UProperty myProperty = this.getUProperty(property);
	return myProperty.getValues(0).getValue();
    }

    // public void setNutritionData() {
    // String userName = Setup.getAMI_UserName();
    // log.info("Setting nutrition data for patient: "+ userName);
    //
    // ProfileDesktopFrontend fe = this.frontEnd;
    // // User user = new User();
    // // user.setUserName(USERNAME);
    // // fe.init(user, "oasis");
    // // fe.downloadProfileFromServer();
    // fe.reload();
    //
    // // CARER EMAIL
    // setUserValue(fe, Nutrition.CARER_EMAIL, AMIPrefTypes.AMI_STRING,
    // "hecgamar@itaca.upv.es");
    //
    // // DIET TYPE
    // setUserValue(fe, Habits.DIET_TYPE, AMIPrefTypes.AMI_STRING,
    // "mediterranean");
    //
    // // BREAKFAST HOUR
    // setUserValue(fe, Habits.BREAKFAST_HOUR, AMIPrefTypes.AMI_STRING,
    // "08:30");
    //
    // // LUNCH HOUR
    // setUserValue(fe, Habits.LUNCH_HOUR, AMIPrefTypes.AMI_STRING, "13:30");
    //
    // // DINNER HOUR
    // setUserValue(fe, Habits.DINNER_HOUR, AMIPrefTypes.AMI_STRING, "20:00");
    //
    // // height
    // setUserValue(fe, Anthropometrics.HEIGHT, AMIPrefTypes.AMI_DOUBLE, "157");
    //
    // // weight
    // setUserValue(fe, Anthropometrics.WEIGHT, AMIPrefTypes.AMI_DOUBLE, "57");
    //
    // // // FAVOURITE RECIPES
    // List<String> list = new ArrayList<String>();
    // // setUserValue(fe, NutriPreferences.FAVOURITE_RECIPES,
    // AMIPrefTypes.AMI_STRING_LIST, list);
    //
    // // // CHRONIC DISEASES, deprecated
    // // list = new ArrayList<String>();
    // // list.add("diabetes");
    // // setUserValue(fe, Health.CHRONIC_DISEASES,
    // AMIPrefTypes.AMI_STRING_LIST, list);
    //
    // // ALLERGIES
    // list = new ArrayList<String>();
    // list.add("0@1@Milk products");
    // setUserValue(fe, Health.FOOD_ALLERGIES, AMIPrefTypes.AMI_STRING_LIST,
    // list);
    //
    // // DISLIKES
    // list = new ArrayList<String>();
    // list.add("49@Globe Artichoke");
    // setUserValue(fe, NutriPreferences.FOOD_DISLIKES,
    // AMIPrefTypes.AMI_STRING_LIST, list);
    //
    // // CHRONIC DISEASES
    // list = new ArrayList<String>();
    // list.add("milky products");
    // setUserValue(fe, Health.FOOD_INTOLERANCES, AMIPrefTypes.AMI_STRING_LIST,
    // list);
    //
    // // PREFERRED FOODS
    // list = new ArrayList<String>();
    // list.add("5@Rice raw");
    // list.add("856@Rice boiled");
    // list.add("170@chicken breast");
    // setUserValue(fe, NutriPreferences.PREFERRRED_FOODS,
    // AMIPrefTypes.AMI_STRING_LIST, list);
    //
    // // Weekly Phisycal Activity Level
    // setUserValue(fe, NP.Nutrition.PhysicalActivity.LEVEL_WEEKLY_ACTIVITY,
    // AMIPrefTypes.AMI_STRING, "moderate");
    //
    // // abdominal_skinfold
    // setUserValue(fe, NP.Nutrition.Anthropometrics.ABDOMINAL_SKINFOLD,
    // AMIPrefTypes.AMI_DOUBLE, "26.54");
    //
    // // subscapular sk
    // setUserValue(fe, NP.Nutrition.Anthropometrics.SUBSCAPULAR_SKINFOLD,
    // AMIPrefTypes.AMI_DOUBLE, "20.94");
    //
    // // suprailiac skin
    // setUserValue(fe, NP.Nutrition.Anthropometrics.SUPRAILIAC_SKINFOLD,
    // AMIPrefTypes.AMI_DOUBLE, "17.29");
    //
    // // triceps skin
    // setUserValue(fe, NP.Nutrition.Anthropometrics.TRICEPS_SKINFOLD,
    // AMIPrefTypes.AMI_DOUBLE, "16.12");
    //
    // log.info("Flushing... ");
    // this.frontEnd.flush();
    // log.info("DONE");
    //
    // log.info("Updating server...");
    // boolean res = this.frontEnd.upoladProfileToServer();
    // log.info("DONE: "+res);
    //
    // log.info("Data created succesfully!");
    // }

    // public void setExtraNutritionData() {
    // String userName = Setup.getAMI_UserName();
    // System.out.println("Setting extra nutrition data for patient: "+
    // userName);
    //
    // ProfileDesktopFrontend fe = this.frontEnd;
    //
    // setUserValue(fe, Nutrition.USERNAME, AMIPrefTypes.AMI_STRING,
    // "David_Shopland");
    // setUserValue(fe, Nutrition.PASSWORD, AMIPrefTypes.AMI_STRING,
    // "5a019bc5dad45f1652c141bc9004598b");
    // // User user = new User();
    // // user.setUserName(USERNAME);
    // // fe.init(user, "oasis");
    // // fe.downloadProfileFromServer();
    // // fe.reload();
    //
    // // // CARER EMAIL
    // // setUserValue(fe, Nutrition.Habits.NUTRITIONAL_GOAL,
    // AMIPrefTypes.AMI_STRING, "losing weight");
    // //
    // //// // DIET TYPE
    // //// setUserValue(fe, Habits.DIET_TYPE, AMIPrefTypes.AMI_STRING,
    // "mediterranean");
    // //
    // // // rec daily meals
    // // setUserValue(fe, Habits.RECOMMENDED_DAILY_MEALS,
    // AMIPrefTypes.AMI_STRING, "3");
    // //
    // // // rec food intake kcal
    // // setUserValue(fe, Habits.RECOMMENDED_FOOD_INTAKE_CALORIES,
    // AMIPrefTypes.AMI_STRING, "1000");
    //
    // System.out.println("Flushing... ");
    // this.frontEnd.flush();
    // System.out.println("DONE");
    //
    // System.out.println("Updating server...");
    // boolean res = this.frontEnd.upoladProfileToServer();
    // System.out.println("DONE: "+res);
    //
    // System.out.println("Data created succesfully!");
    // }

    public boolean downloadProfileFromServer() {
	/*
	 * Si existe, enviar version actual del profile del usuario logeado si
	 * error, null o caducado, descargar y guardar. si no, nada Si no
	 * existe, descargar y guardar.
	 */
	Utils.println("Downloading profile...");
	NutritionalAdvisorProxy na = new NutritionalAdvisorProxy();
	try {
	    UserNutritionalProfile profile = na.getUserProfile(TSFConnector
		    .getInstance().getToken(), -1);
	    if (profile.getStatusResponse().compareTo(
		    NP.StatusResponse.STATUS_EXPIRED) == 0
		    || profile.getStatusResponse().compareTo(
			    NP.StatusResponse.STATUS_INVALID) == 0)
		ProfileConnector.setProfile(profile);
	    return true;
	} catch (NutriSecurityException e) {
	    e.printStackTrace();
	} catch (TokenExpiredException e) {
	    e.printStackTrace();
	} catch (RemoteException e) {
	    e.printStackTrace();
	}

	return false;
    }

    private static void setProfile(UserNutritionalProfile profile) {
	ProfileConnector.nutritionalProfile = profile;
	ProfileConnector.map = null;
	map = new HashMap<String, UProperty>();
	// recorrer todas las propiedades y crear una tabla con sus códigos para
	// fácil acceso
	if (nutritionalProfile != null
		&& nutritionalProfile.getProperties() != null
		&& nutritionalProfile.getProperties().length > 0) {
	    for (UProperty element : nutritionalProfile.getProperties()) {
		map.put(element.getCode(), element);
	    }
	    Utils.println("user has: " + map.size() + " properties!");
	} else {
	    Utils.println("there are no properties!");
	}
    }

    public static UserNutritionalProfile getProfile() {
	return ProfileConnector.nutritionalProfile;
    }

    // protected void setCommonData() {
    // String userName = Setup.getAMI_UserName();
    // log.info("Profile: Setting Common data for user: "+userName);
    // ProfileDesktopFrontend fe = new ProfileDesktopFrontendImpl();
    // User user = new User();
    // user.setUserName(userName);
    // fe.init(user, "oasis");
    // fe.downloadProfileFromServer();
    // fe.reload();
    // setUserValue(fe, "/common/user_email", AMIPrefTypes.AMI_STRING,
    // "hecgamar@gmail.com");
    // setUserValue(fe, "/common/city", AMIPrefTypes.AMI_STRING, "Valencia");
    // setUserValue(fe, "/common/sex", AMIPrefTypes.AMI_STRING, "MALE");
    // setUserValue(fe, "/common/surname", AMIPrefTypes.AMI_STRING, "Smith");
    // setUserValue(fe, "/common/user_id", AMIPrefTypes.AMI_STRING,
    // "itaca_user");
    // setUserValue(fe, "/common/name", AMIPrefTypes.AMI_STRING, "Peter");
    // setUserValue(fe, "/common/age_category", AMIPrefTypes.AMI_STRING,
    // "Elderly");
    // setUserValue(fe, "/common/age", AMIPrefTypes.AMI_INTEGER, "50");
    // setUserValue(fe, "/common/preferred_language", AMIPrefTypes.AMI_STRING,
    // "GB");
    // setUserValue(fe, "/common/country", AMIPrefTypes.AMI_STRING, "GB");
    // setUserValue(fe, "/common/timezone", AMIPrefTypes.AMI_INTEGER, "1");
    // List<String> sl = new ArrayList<String>();
    // sl.add("GB");
    // setUserValue(fe, "/common/spoken_language", AMIPrefTypes.AMI_STRING_LIST,
    // sl);
    // fe.flush();
    // boolean res = fe.upoladProfileToServer();
    // log.info("Profile: uploaded: " + res);
    //
    // }

    // private static void setUserValue(ProfileDesktopFrontend fe, String
    // propName, AMIPrefTypes type, List<String> values) {
    // GenericProfileProperty pp = new GenericProfileProperty();
    // pp.setType(type);
    // pp.setAbsoluteName(propName);
    // pp.setValue(values);
    // fe.setProfileProperty(pp);
    // }
    //
    // private static void setUserValue(ProfileDesktopFrontend fe, String
    // propName, AMIPrefTypes type, String value) {
    // GenericProfileProperty pp = new GenericProfileProperty();
    // pp.setType(type);
    // pp.setAbsoluteName(propName);
    // pp.setValue(value);
    // fe.setProfileProperty(pp);
    // }

    // public boolean shareRecipeOnProfile(int recipeID, String recipeName) {
    // // get recipes
    // // search recipe
    // // substitute recipe or add
    // // store value
    // String userName = Setup.getAMI_UserName();
    // log.info("Profile: Sharing a recipe on NutritionProfile: "+userName);
    // ProfileDesktopFrontend fe = this.frontEnd;
    // List<String> recipeList = this.getFavourite_Recipes();
    // boolean found = false;
    // int id = -1;
    // // String name = "";
    // for (String recipe : recipeList) {
    // if (recipe!=null && recipe.length()>2) {
    // String[] parts = recipe.split("@");
    // if (parts.length == 2) {
    // id = new Integer(parts[0]).intValue();
    // // name = parts[1];
    // if (id == recipeID) {
    // found = true;
    // recipe = ""+recipeID+"@"+recipeName;
    // break;
    // }
    // }
    // }
    // }
    // if (found == false) {
    // String newRecipe = ""+recipeID+"@"+recipeName;
    // recipeList.add(newRecipe);
    // }
    //
    // setUserValue(fe, NP.Nutrition.NutriPreferences.FAVOURITE_RECIPES,
    // AMIPrefTypes.AMI_STRING_LIST, recipeList);
    // fe.flush();
    // boolean res = fe.upoladProfileToServer();
    // log.info("Profile: uploaded! " + res);
    // return res;
    // }

    // public boolean unshareRecipeOnProfile(int recipeID) {
    // // get recipes
    // // search recipe
    // // substitute recipe or add
    // // store value
    // String userName = Setup.getAMI_UserName();
    // log.info("Profile: UnSharing a recipe: " + recipeID +
    // " on NutritionProfile: "+userName);
    // ProfileDesktopFrontend fe = this.frontEnd;
    // List<String> recipeList = this.getFavourite_Recipes();
    // List<String> newRecipeList = new ArrayList<String>();
    // int id = -1;
    // // String name = "";
    // for (String recipe : recipeList) {
    // if (recipe!=null && recipe.length()>2) {
    // String[] parts = recipe.split("@");
    // if (parts.length == 2) {
    // id = new Integer(parts[0]).intValue();
    // if (id == recipeID) {
    //
    // } else {
    // newRecipeList.add(recipe);
    // }
    // }
    // }
    // }
    //
    // setUserValue(fe, NP.Nutrition.NutriPreferences.FAVOURITE_RECIPES,
    // AMIPrefTypes.AMI_STRING_LIST, newRecipeList);
    // fe.flush();
    // boolean res = fe.upoladProfileToServer();
    // log.info("Profile: NutritionProfile uploaded! " + res);
    // return res;
    // }

    // public void startListenToFatigueChanges() {
    // log.info("Profile: startListeningToFatigueChanges...");
    // GenericProfileProperty gpp = new GenericProfileProperty();
    // gpp.setAbsoluteName(NP.External.ActivityCoach.FATIGUE_LEVEL);
    // instance.frontEnd.addAMIPropertyChangeListener(gpp, instance);
    // log.info("Profile: startListeningToFatigueChanges: SET");
    // }

    // @Override
    // public void amiPropertyChange(AmiPropertyChangeEvent p) {
    // log.info("Profile: property change!");
    // String newValue = p.getNewValue();
    // if (newValue!=null) {
    // log.info("Profile: new value for fatigue level: "+newValue);
    // ExtraAdvise e = new ExtraAdvise();
    // e.advise.setTitle("HIGH FATIGUE LEVEL!");
    // e.advise.setMessage("You are fatigued!");
    // } else {
    // log.info("Profile: propertyChange is null");
    // }
    // }

    public String getHasHighActivityLevel() {
	// first get the last Profile
	// if (ProfileConnector.getInstance().downloadProfileFromServer()==
	// true) {
	String property = NP.External.ActivityCoach.ACTIVITY_LEVEL;
	UProperty myProperty = this.getUProperty(property);
	if (myProperty != null && myProperty.getValues() != null
		&& myProperty.getValues(0).getValue().length() > 0)
	    return myProperty.getValues(0).getValue();
	else
	    return null;
	// }
	// return null;
    }

    public String getUsername() {
	String property = NP.Common.USERNAME;
	UProperty myProperty = this.getUProperty(property);
	return myProperty.getValues(0).getValue();
	// UProperty myProperty = this.getUProperty(property);
	// if (myProperty==null)
	// myProperty = new UProperty();
	// myProperty.setValue("David_Shopland");
	// if (myProperty!=null && myProperty.getFirstValue()!=null &&
	// myProperty.getFirstValue().length()>0)
	// return myProperty.getFirstValue();
	// else
	// return null;
    }

    public String getSocialCommunityUsername() {
	String property = NP.External.Social.USERNAME;
	UProperty myProperty = this.getUProperty(property);
	return myProperty.getValues(0).getValue();
	// GenericProfileProperty myProperty = this.getProperty(property);
	// if (myProperty!=null && myProperty.getFirstValue()!=null &&
	// myProperty.getFirstValue().length()>0)
	// return myProperty.getFirstValue();
	// else
	// return null;
    }

    public String getSocialCommunityPassword() {
	String property = NP.External.Social.PASSWORD;
	UProperty myProperty = this.getUProperty(property);
	return myProperty.getValues(0).getValue();
	// GenericProfileProperty myProperty = this.getProperty(property);
	// if (myProperty!=null && myProperty.getFirstValue()!=null &&
	// myProperty.getFirstValue().length()>0)
	// return myProperty.getFirstValue();
	// else
	// return null;
    }

    public String getNutritionalUsername() {
	return "David_Shopland";
	// String property = NP.Nutrition.USERNAME;
	// UProperty myProperty = this.getUProperty(property);
	// return myProperty.getValues(0).getValue();
    }

    public String getNutritionalPassword() {
	return "5a019bc5dad45f1652c141bc9004598b";
	// String property = NP.Nutrition.PASSWORD;
	// UProperty myProperty = this.getUProperty(property);
	// return myProperty.getValues(0).getValue();
    }

    public int getCodeLang() {
	if (codeLang == null)
	    this.getScreenLanguage();
	if (codeLang.compareTo("EN") == 0 || codeLang.compareTo("GB") == 0) {
	    return 2;
	} else if (codeLang.compareTo("ES") == 0) {
	    return 1;
	} else if (codeLang.compareTo("BG") == 0) {
	    return 7;
	}
	return 2;
    }

    // public String getUserID() {
    // String property = NP.Common.USER_ID;
    // GenericProfileProperty myProperty = this.getProperty(property);
    // if (myProperty!=null && myProperty.getFirstValue()!=null &&
    // myProperty.getFirstValue().length()>0)
    // return myProperty.getFirstValue();
    // else
    // return null;
    // }
}
