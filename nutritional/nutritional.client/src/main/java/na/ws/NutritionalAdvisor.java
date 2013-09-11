/**
 * NutritionalAdvisor.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package na.ws;

public interface NutritionalAdvisor extends java.rmi.Remote {
    public java.lang.String addFavouriteRecipe(java.lang.String token,
	    int recipeID) throws java.rmi.RemoteException,
	    na.ws.NutriSecurityException;

    public boolean changeMeal(java.lang.String token, int day, int mealCategory)
	    throws java.rmi.RemoteException, na.ws.NutriSecurityException,
	    na.ws.TokenExpiredException;

    public java.lang.String deleteFavouriteRecipe(java.lang.String token,
	    int recipeID) throws java.rmi.RemoteException,
	    na.ws.NutriSecurityException, na.ws.TokenExpiredException;

    public byte[] getAdvisePicture(java.lang.String token, int adviseID)
	    throws java.rmi.RemoteException, na.ws.NutriSecurityException;

    public na.miniDao.ShoppingList getCustomShoppingListDays(
	    java.lang.String token, int windowSize, int startDay)
	    throws java.rmi.RemoteException, na.ws.NutriSecurityException;

    public na.miniDao.DayMenu getDayMenu(java.lang.String token, int day)
	    throws java.rmi.RemoteException, na.ws.NutriSecurityException,
	    na.ws.TokenExpiredException;

    public byte[] getDishPicture(java.lang.String token, int recipeID)
	    throws java.rmi.RemoteException, na.ws.NutriSecurityException;

    public na.miniDao.Recipe[] getFavouriteRecipes(java.lang.String token)
	    throws java.rmi.RemoteException, na.ws.NutriSecurityException,
	    na.ws.TokenExpiredException;

    public na.miniDao.FoodCategory[] getFoodCategories(java.lang.String token)
	    throws java.rmi.RemoteException;

    public na.miniDao.FoodItem[] getFoodsByCategory(int foodCategoriesID,
	    java.lang.String token) throws java.rmi.RemoteException;

    public na.miniDao.Food getFullFood(int foodID, java.lang.String token)
	    throws java.rmi.RemoteException;

    public na.miniDao.full.FoodCategory[] getFullFoodCategories(
	    java.lang.String token) throws java.rmi.RemoteException;

    public na.miniDao.Advise[] getMyAdvises(java.lang.String token)
	    throws java.rmi.RemoteException, na.ws.NutriSecurityException,
	    na.ws.TokenExpiredException;

    public na.ws.UProperty getMyLocalisedData(java.lang.String token,
	    na.ws.Translation data) throws java.rmi.RemoteException,
	    na.ws.NutriSecurityException, na.ws.TokenExpiredException;

    public na.miniDao.Tip[] getMyTips(java.lang.String token)
	    throws java.rmi.RemoteException, na.ws.NutriSecurityException,
	    na.ws.TokenExpiredException;

    public na.miniDao.Exercise getNextQuestion(java.lang.String token,
	    int exerciseID, int userQuestionnaireID, int questionID)
	    throws java.rmi.RemoteException, na.ws.NutriSecurityException,
	    na.ws.TokenExpiredException;

    public na.miniDao.Exercise[] getPendingQuestionnaires(java.lang.String token)
	    throws java.rmi.RemoteException, na.ws.NutriSecurityException,
	    na.ws.TokenExpiredException;

    public na.miniDao.Exercise getPreviousQuestion(java.lang.String token,
	    int exerciseID, int userQuestionnaireID, int questionID)
	    throws java.rmi.RemoteException, na.ws.NutriSecurityException,
	    na.ws.TokenExpiredException;

    public na.miniDao.ShoppingList getSmartCustomShoppingListDays(
	    java.lang.String token, int windowSize, int startDay,
	    java.lang.String[] outsideEvents) throws java.rmi.RemoteException,
	    na.ws.NutriSecurityException;

    public na.miniDao.Exercise getStartingQuestionnaire(java.lang.String token,
	    int questionnaireID, int exerciseID)
	    throws java.rmi.RemoteException, na.ws.NutriSecurityException,
	    na.ws.TokenExpiredException;

    public na.miniDao.DayMenu[] getThisWeekMenu(java.lang.String token)
	    throws java.rmi.RemoteException, na.ws.NutriSecurityException;

    public byte[] getTipPicture(java.lang.String token, int tipID)
	    throws java.rmi.RemoteException, na.ws.NutriSecurityException;

    public na.miniDao.DayMenu getTodayMenu(java.lang.String token)
	    throws java.rmi.RemoteException, na.ws.NutriSecurityException,
	    na.ws.TokenExpiredException;

    public java.lang.String getToken(java.lang.String username,
	    java.lang.String password, int preferredLanguage)
	    throws java.rmi.RemoteException, na.ws.NutriSecurityException;

    public na.miniDao.DayMenu getTomorrowMenu(java.lang.String token)
	    throws java.rmi.RemoteException, na.ws.NutriSecurityException,
	    na.ws.TokenExpiredException;

    public na.ws.UserNutritionalProfile getUserProfile(java.lang.String token,
	    int version) throws java.rmi.RemoteException,
	    na.ws.NutriSecurityException, na.ws.TokenExpiredException;

    public na.miniDao.Recipe getUserRecipe(java.lang.String token, int recipeID)
	    throws java.rmi.RemoteException;

    public na.miniDao.ShoppingList getWeeklyShoppingList(java.lang.String token)
	    throws java.rmi.RemoteException, na.ws.NutriSecurityException,
	    na.ws.TokenExpiredException;

    public boolean isTokenValid(java.lang.String token)
	    throws java.rmi.RemoteException;

    public java.lang.String[] setQuestionnaireAnswer(java.lang.String token,
	    int exerciseID, int questionID, na.miniDao.Answer[] userAnswers)
	    throws java.rmi.RemoteException, na.ws.NutriSecurityException,
	    na.ws.TokenExpiredException;

    // public boolean setSingleProfileProperty(java.lang.String token,
    // java.lang.String username, java.lang.String propertyName,
    // java.lang.String value) throws java.rmi.RemoteException,
    // na.ws.NutriSecurityException, na.ws.TokenExpiredException;
    // public boolean setProfileProperty(java.lang.String token,
    // java.lang.String username, java.lang.String propertyName,
    // java.lang.String[] value) throws java.rmi.RemoteException,
    // na.ws.NutriSecurityException, na.ws.TokenExpiredException;
    // public java.lang.String setSingleProfileProperty(String token, int type,
    // String propertyParentName, String propertyChildrenName, int patientID,
    // String[] values, boolean isMultiple) throws java.rmi.RemoteException,
    // na.ws.NutriSecurityException, na.ws.TokenExpiredException;;
    // public java.lang.String setProfileProperty(String token, int patientID,
    // String propertyName, String[] objects)throws java.rmi.RemoteException,
    // na.ws.NutriSecurityException, na.ws.TokenExpiredException;
}
