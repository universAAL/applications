/**
 * Interface that every OASIS service must implement
 */

package na.utils;

import javax.swing.JFrame;

public interface ServiceInterface {

    /*
     * Name of each service that the application loads The service must use the
     * correspondent constant to register the service This constant is
     * fully-qualified name of the Service main class, found by
     * (class.getName())
     */
    // public static final String SERVICE_NUTRITIONAL_ADVISOR =
    // na.services.ServiceLauncher.class.getName();

    public static final String DIR_USER_PROFILE = "";
    public static final String SETUP_FILENAME = "setup.properties";
    public static final String DIR_NUTRITIONAL_ROOT = Setup.DEFAULT_PATH_Nutritional_DESCRIPTOR
	    + "/";
    public static final String DIR_NUTRITIONAL = Setup.DEFAULT_PATH_Nutritional_DESCRIPTOR
	    + "/" + Setup.getAMI_UserName() + "/";
    // public static final String DIR_NUTRITIONAL_USER = DIR_NUTRITIONAL +
    // Setup.getAMI_UserName()+"/";
    public static final String USER_PROFILE_FILENAME = "UserProfile.xml";
    // public static final String USER_NUTRITIONAL_PROFILE_FILENAME =
    // "UserNutritionalProfile.xml";
    public static final String ADVISE_REPOSITORY_FILENAME = "advises.properties";
    public static final String FOOD_INVENTORY_FILENAME = "food_inventory.properties";
    public static final String SHOPPING_DATA_FILENAME = "shoppingData.properties";
    public static final String EXTRA_SHOPPING_ITEMS_FILENAME = "extra_shopping_items.properties";
    public static final String PATH_FOOD_INVENTORY = DIR_USER_PROFILE
	    + DIR_NUTRITIONAL + FOOD_INVENTORY_FILENAME;
    public static final String SHARED_RECIPES_FILENAME = "shared_recipes.properties";
    public static final String PATH_SHARED_RECIPES = DIR_USER_PROFILE
	    + DIR_NUTRITIONAL + SHARED_RECIPES_FILENAME;
    public static final String PATH_ADVISE_REPOSITORY = DIR_USER_PROFILE
	    + DIR_NUTRITIONAL + ADVISE_REPOSITORY_FILENAME;
    public static final String PATH_SHOPPING_DATA = DIR_USER_PROFILE
	    + DIR_NUTRITIONAL + SHOPPING_DATA_FILENAME;
    public static final String PATH_EXTRA_SHOPPING_ITEMS = DIR_USER_PROFILE
	    + DIR_NUTRITIONAL + EXTRA_SHOPPING_ITEMS_FILENAME;
    public static final String FILENAME_USER_PROP = "user.properties";
    public static final String PATH_USER_FILE = DIR_USER_PROFILE
	    + DIR_NUTRITIONAL + FILENAME_USER_PROP;
    public static final String PATH_TIPS_IMAGES = DIR_USER_PROFILE
	    + DIR_NUTRITIONAL + "tips";
    public static final String PATH_TIPS = DIR_USER_PROFILE + DIR_NUTRITIONAL;
    public static final String PATH_MULTITEL_XMLS = DIR_USER_PROFILE
	    + DIR_NUTRITIONAL + "Multitel/";

    static final String DIR_IMAGES = "images";
    static final String DIR_IMAGES_fulldir = DIR_NUTRITIONAL + "images/";

    public static final String TIP = "tip";
    public static final String TIP_File = "tips_en.properties";
    public static final String DISH = "dish";
    public static final String PATH_DISH_PICTURES = DIR_USER_PROFILE
	    + DIR_NUTRITIONAL + DIR_IMAGES + "/" + DISH + "/";
    public static final String PATH_TIP_PICTURES = DIR_USER_PROFILE
	    + DIR_NUTRITIONAL + DIR_IMAGES + "/" + TIP + "/";

    public static final String PATH_PDF_FILE = DIR_USER_PROFILE
	    + DIR_NUTRITIONAL + "ShoppingList.pdf";
    public static final String CACHE_FOLDER_name = "cache";
    public static final String CACHE_FOLDER = DIR_NUTRITIONAL + "cache/";

    public static final String DATE_FORMAT = "yyyy-MM-dd";
    public static final int MealCategory_BreakFast = 1;
    public static final int MealCategory_Lunch = 3;
    public static final int MealCategory_Dinner = 5;
    public static final String MealCategory_BreakFast_Str = "breakfast";
    public static final String MealCategory_Lunch_str = "lunch";
    public static final String MealCategory_Dinner_str = "dinner";

    public static final String DOMAIN_SocialCommunity = "social_platform";
    public static final String DOMAIN_Nutrition = "Nutrition";
    public static final String DOMAIN_EnvironmentalControl = "Device";

    // listado de operaciones, IDEAL operations del AMI (nombres genéricos que
    // ofrecen un servicio)
    public static final String OP_GetTodayMenu = "getTodayMenuIdeal";
    public static final String OP_GetTomorrowMenu = "getTomorrowMenuIdeal";
    public static final String OP_GetTips = "getMyTipsIdeal";
    public static final String OP_GetThisWeekMenu = "getThisWeekMenuIdeal";
    public static final String OP_GetDishPicture = "getDishPictureIdeal";
    public static final String OP_ChangeMeal = "changeMealIdeal";
    public static final String OP_GetFavouriteRecipes = "getFavouriteRecipesIdeal";
    public static final String OP_GetUserRecipe = "getUserRecipeIdeal";
    public static final String OP_AddFavouriteRecipe = "addFavouriteRecipeIdeal";
    public static final String OP_DeleteFavouriteRecipe = "deleteFavouriteRecipeIdeal";
    public static final String OP_GetCustomShoppingList = "getCustomShoppingListDaysIdeal";
    public static final String OP_GetSmartCustomShoppingList = "getSmartCustomShoppingListDaysIdeal";;
    public static final String OP_GetFoodCategories = "getFoodCategoriesIdeal";
    public static final String OP_GetFoodsByCategory = "getFoodsByCategoryIdeal";
    public static final String OP_GetPendingQuestionnaires = "getPendingQuestionnairesIdeal";
    public static final String OP_GetStartQuestionnaire = "getStartingQuestionnaireIdeal";
    public static final String OP_SetQuestionnaireAnswer = "setQuestionnaireAnswerIdeal";
    public static final String OP_GetNextQuestion = "getNextQuestionIdeal";
    public static final String OP_GetPreviousQuestion = "getPreviousQuestionIdeal";
    public static final String OP_GetToken = "getTokenIdeal";
    public static final String OP_GetMyToken = "getMyTokenIdeal";
    public static final String OP_GetMyAdvises = "getMyAdvisesIdeal";
    public static final String OP_GetAdvisePicture = "getAdvisePictureIdeal";
    public static final String OP_GetTipPicture = "getTipPictureIdeal";
    public static final String OP_GetLocalisedProfile = "getMyLocalisedDataIdeal";
    public static final String OP_GetFullFood = "getFullFoodIdeal";
    public static final String OP_GetFullFoodCategories = "getFullFoodCategoriesIdeal";

    public static final String OP_SetProfileProperty = "setProfileProperty";
    public static final String OP_SetSingleProfileProperty = "setSingleProfileProperty";
    public static final String OP_GetPatientsList = "getPatientsList";
    public static final String OP_GetTokenX = "getToken";

    // listado de operaciones, el nombre de la operacion con la implementacion
    // de ITACA
    public static final String favOp_GetToken = "getToken";
    public static final String favOp_GetTodayMenu = "getTodayMenu";
    public static final String favOp_GetTomorrowMenu = "getTomorrowMenu";
    public static final String favOp_GetDishPicture = "getDishPicture";
    public static final String favOp_GetTips = "getMyTips";
    public static final String favOp_GetRecipe = "getUserRecipe";
    public static final String favOp_GetFavouriteRecipes = "getFavouriteRecipes";
    public static final String favOp_AddFavouriteRecipe = "addFavouriteRecipe";
    public static final String favOp_ChangeMeal = "changeMeal";
    public static final String favOp_DeleteFavouriteRecipe = "deleteFavouriteRecipe";
    public static final String favOp_getCustomShoppingList = "getCustomShoppingListDays";
    public static final String favOp_getSmartCustomShoppingList = "getSmartCustomShoppingListDays";
    public static final String favOp_GetFoodCategories = "getFoodCategories";
    public static final String favOp_GetFoodsByCategory = "getFoodsByCategory";
    public static final String favOp_GetMyAdvises = "getMyAdvises";
    public static final String favOp_GetTipPicture = "getTipPicture";
    public static final String favOp_GetAdvisePicture = "getAdvisePicture";
    public static final String favOp_GetThisWeekMenu = "getThisWeekMenu";
    public static final String favOp_GetPendingQuestionnaires = "getPendingQuestionnaires";
    public static final String favOp_GetQuestion = "getNextQuestion";
    public static final String favOp_GetPreviousQuestion = "getPreviousQuestion";
    public static final String favOp_GetStartingQuestionnaire = "getStartingQuestionnaire";
    public static final String favOp_setQuestionnaireAnswer = "setQuestionnaireAnswer";
    public static final String favOp_getLocalisedProfile = "getMyLocalisedData";

    public static final String Function_boldLabel = "boldLabel";
    public static final String OP_Social_FriendsLogin = "getTokens";
    public static final String OP_Social_GetMyGroupsResponse = "socialNetwork_getMyGroups";
    public static final String OP_Social_GetGroups = "getmygroupsIdeal";
    public static final String OP_Social_AddTopic = "addtopicIdeal";

    public static final String OP_Social_AgendaLogin = "getTokensIdeal";
    public static final String OP_Social_AgendaGetMyEvents = "getmyeventsIdeal";

    public static final String favOp_Social_AgendaLogin = "gettoken";
    public static final String favOp_Social_GetMyEvents = "getmyevents";
    public static final String favOp_Social_GetMyGroups = "getmygroups";
    public static final String favOp_Social_AddTopic = "addtopic";

    public static final String ImageNotAvailable = "/na/utils/not_available.jpg";

    public static final int today_int = 0;
    public static final int tomorrow_int = 1;

    public static final String OP_GetTemperature = "getConsumerSwitchStateAgeIdeal";
    public static final String favOp_GetTemperature = "getTemperature";

    public static final String TAG_LUNCH_OUTSIDE = "lunch outside";

    // public static boolean stopThreads = false;

    /**
     * Sets the Service GUI on top of the parent widget Take into account the
     * limited size
     * 
     * @param parent
     */
    public void showApplicationNutritional(
	    na.widgets.panel.AdaptivePanel parent, JFrame frame);

    // public void showRecipe(widgets.panel.AdaptivePanel parent, int recipeID);

    public class DishCategories {
	public static final int MainCourse = 4;
	public static final int FirstCourse = 3;
	public static final int SideDish = 5;
	public static final int Dessert = 1;
	public static final int Drink = 2;
	public static final int Starter = 6;
	public static final int Breakfast = 7;
	public static final int Snack = 8;
    }
}
