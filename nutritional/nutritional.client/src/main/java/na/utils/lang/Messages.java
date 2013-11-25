package na.utils.lang;

import na.oasisUtils.profile.ProfileConnector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class Messages extends MyNLS {
    @SuppressWarnings("unused")
    private static Log log = LogFactory.getLog(Messages.class);
    private static final String BUNDLE_NAME_ENGLISH = "na.utils.lang.lang_english";
    private static final String BUNDLE_NAME_SPANISH = "na.utils.lang.lang_spanish";
    private static final String BUNDLE_NAME_BULGARIAN = "na.utils.lang.lang_bulgarian";
    private static final String BUNDLE_NAME_ROMANIAN = "na.utils.lang.lang_romanian";

    public static String MainMenu_But_Help;
    public static String MainMenu_But_MyNutritionalProfile;
    public static String MainMenu_But_NutritionalMenus;
    public static String MainMenu_But_Recipes;
    public static String MainMenu_But_ShoppingList;
    public static String MainMenu_But_SpeechEngine;

    public static String Scheduler_PopUp_OK;
    // help
    public static String Help_Answer_HowCanItHelp;
    public static String Help_Answer_HowNutritionistKnows;
    public static String Help_Answer_IdontUnderestandButtons;
    public static String Help_Answer_WhatIs;
    public static String Help_Answer_WhatNutritionalProfile;
    public static String Help_Question_HowCanItHelp;
    public static String Help_Question_HowNutritionistKnows;
    public static String Help_Question_IdontUnderestandButtons;
    public static String Help_Question_WhatIs;
    public static String Help_Question_WhatNutritionalProfile;

    // my nutritional profile
    public static String Profile_My_Eating_Preferences;
    public static String Profile_PendingQuestionaire;
    public static String Profile_AskingNutritionist;
    public static String Profile_Prefer_MyProfile;
    public static String Profile_Prefer_DietType_I_eat;
    public static String Profile_Prefer_Food;
    public static String Profile_Prefer_MealTimes;
    public static String Profile_Prefer_Breakfast;
    public static String Profile_Prefer_Lunch;
    public static String Profile_Prefer_Dinner;
    public static String Profile_Prefer_ISpeciallyLike;
    public static String Profile_Prefer_IDontLike;
    public static String Profile_Prefer_MyFaoriteRecipesAre;
    public static String Profile_Prefer_IamAllergicTo;
    public static String Profile_Prefer_Ihave;
    public static String Profile_Prefer_IamIntolerantTo;
    public static String NutriProfile_MyDietTypeIs;

    public static String Profile_ChronicDisease_CHOLESTEROL;
    public static String Profile_ChronicDisease_CARDIOVASCULAR;
    public static String Profile_ChronicDisease_DIABETES;
    public static String Profile_ChronicDisease_DIGESTIVE;
    public static String Profile_ChronicDisease_OVERWEIGHT;
    public static String Profile_ChronicDisease_URINE;
    public static String Profile_ChronicDisease_RESPIRATORY;

    public static String Questionnaire_nextQuestion;
    public static String Questionnaire_Continue;
    public static String Questionnaire_Start;
    public static String Questionnaire_MoreQuestionnaires;
    public static String Questionnaire_PreviousQuestionnaires;
    public static String Questionnaire_PendingQuestionnairesIntroduction;
    public static String Questionnaire_NoPendingQuestionnaires;
    public static String Questionnaire_Hour;
    public static String Questionnaire_Minute;
    public static String Questionnaire_Centimeters;
    public static String Questionnaire_Kilograms;
    public static String Questionnaire_PleaseChooseAnAnswerFirst;
    public static String Questionnaire_QuestionnaireHasEnded;
    public static String Questionnaire_ServiceNotAvailable;
    public static String Questionnaire_ContinueAfterEnded;
    public static String Questionnaire_previousQuestion;

    // menus
    public static String Menus_NO_snack;
    public static String Menus_ChangeBreakfast;
    public static String Menus_ChangeLunch;
    public static String Menus_ChangeDinner;
    public static String Menus_Today_text;
    public static String Menus_Tomorrow_text;
    public static String Menus_Week_text;
    public static String Menus_Tips_text;
    public static String Menus_SnacksSuggestions;
    public static String Menus_Today_breakfast;
    public static String Menus_Today_midmorning_snack;
    public static String Menus_Today_afternoon_snack;
    public static String Menus_Today_afterdinner_snack;
    public static String Menus_SeeRecipe;
    public static String Menus_Today_lunch;
    public static String Menus_Today_dinner;
    public static String Menus_Tips_nextTip;
    public static String Menus_Tips_previousTip;
    public static String Menus_ServiceNotAvailable;
    public static String Menus_Calendar_Monday;
    public static String Menus_Calendar_Tuesday;
    public static String Menus_Calendar_Wednesday;
    public static String Menus_Calendar_Thursday;
    public static String Menus_Calendar_Friday;
    public static String Menus_Calendar_Saturday;
    public static String Menus_Calendar_Sunday;
    public static String Menus_ChangeMeal_PopUp_Title;
    public static String Menus_ChangeMeal_PopUp_Message;

    // recipes
    public static String Recipes_AddToFavorites;
    public static String Recipes_RemoveFromFavorites;
    public static String Recipes_AlreadyFavorite;
    public static String Recipes_Today;
    public static String Recipes_Tomorrow;
    public static String Recipes_Favourites;
    public static String Recipes_Today_breakfast;
    public static String Recipes_SeeRecipe;
    public static String Recipes_Today_lunch;
    public static String Recipes_Today_dinner;
    public static String Recipes_Ingredients;
    public static String Recipes_Preparation;
    public static String Recipes_MyFavorite;
    public static String Recipes_NoFavorites;
    public static String Recipes_ReadProcedure;
    public static String Recipes_ActivateSpeechEngineFirst;
    public static String Recipes_Shared_Already;
    public static String Recipes_Share;
    public static String Recipes_PreviousFavourites;
    public static String Recipes_MoreFavourites;
    public static String Recipes_AddFavourite_PopUp_Title;
    public static String Recipes_AddFavourite_PopUp_Message;
    public static String Recipes_RemoveFavourite_PopUp_Title;
    public static String Recipes_RemoveFavourite_PopUp_Message;
    public static String Recipes_Share_PopUp_Title;
    public static String Recipes_Share_PopUp_Message;

    // shopping
    public static String ShoppingList_GoingShopping;
    public static String ShoppingList_AddFoodItem;
    public static String ShoppingList_AlreadyBoughtIt;
    public static String ShoppingList_Item;
    public static String ShoppingList_MoreItems;
    public static String ShoppingList_NoiTemsFound;
    public static String ShoppingList_NoMoreItems;
    public static String ShoppingList_NoPreviousItems;
    public static String ShoppingList_PreviousItems;
    public static String ShoppingList_Print;
    public static String ShoppingList_Digital;
    public static String ShoppingList_Quantity;
    public static String ShoppingList_SendCarer;
    public static String ShoppingList_SendECommerce;
    public static String ShoppingList_SendToEmail;
    public static String ShoppingList_SL_Title;
    public static String ShoppingList_UpdateShoppingList;
    public static String ShoppingList_ObtainShoppingListFor;
    public static String ShoppingList_AddFood_AddItemToShoppingList;
    public static String ShoppingList_DAYS;
    public static String ShoppingList_GoingShopping_ShoppingList_DAY;
    public static String ShoppingList_NotPrinted;
    public static String ShoppingList_AlreadyPrinted;
    public static String ShoppingList_MoreDays;
    public static String ShoppingList_LessDays;
    public static String ShoppingQuestion;
    public static String Answer_Yes;
    public static String Answer_No;
    public static String Answer_Cancel;
    public static String ShoppingQuestionAlreadyGenerated;
    public static String Shopping_FoodItemAlreadyExists;
    public static String Shopping_email_Subject;
    public static String Shopping_email_information;
    public static String Shopping_email_mailSent;
    public static String Shopping_email_mailSentCarer;
    public static String Shopping_email_mainNotSent;
    public static String Shopping_email_letter_header;
    public static String Shopping_email_letter_body;
    public static String Shopping_email_letter_name;
    public static String Shopping_email_letter_periodOfTime;
    public static String Shopping_email_letter_generated;
    public static String Shopping_email_letter_signature;
    public static String Shopping_email_carerNotSent;
    public static String Shopping_email_letter_patientName;
    public static String Shopping_email_letter_carerBody;
    public static String Shopping_email_letter_carerHeader;
    public static String Shopping_lunchOutside;
    public static String Month_January;
    public static String Month_February;
    public static String Month_March;
    public static String Month_April;
    public static String Month_May;
    public static String Month_June;
    public static String Month_July;
    public static String Month_August;
    public static String Month_September;
    public static String Month_October;
    public static String Month_November;
    public static String Month_December;
    public static String Connection_errorNutritional;
    public static String Connection_errorLoginNutritional;
    public static String Shopping_pdf_MyShoppingList;
    public static String Shopping_UseSocialCommunitiesAgenda;
    // public static String ;
    // public static String ;
    // public static String ;
    // public static String ;
    // public static String ;
    // public static String ;
    // public static String ;

    public static String Basic_Continue;

    // //////////////////////////////////////////////////////////////////////////
    //
    // Constructor
    //
    // //////////////////////////////////////////////////////////////////////////
    private Messages() {
	// do not instantiate
    }

    static {
	int code = ProfileConnector.getInstance().getCodeLang();
	switch (code) {
	case 1:
	    MyNLS.initializeMessages(BUNDLE_NAME_SPANISH, Messages.class);
	    break;
	case 2:
	    MyNLS.initializeMessages(BUNDLE_NAME_ENGLISH, Messages.class);
	    break;
	case 7:
	    MyNLS.initializeMessages(BUNDLE_NAME_BULGARIAN, Messages.class);
	    break;
	case 8:
	    MyNLS.initializeMessages(BUNDLE_NAME_ROMANIAN, Messages.class);
	    break;
	default:
	    MyNLS.initializeMessages(BUNDLE_NAME_ENGLISH, Messages.class);
	    break;
	}
    }
}
