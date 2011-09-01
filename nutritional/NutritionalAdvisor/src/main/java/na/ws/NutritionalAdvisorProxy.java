package na.ws;

public class NutritionalAdvisorProxy implements na.ws.NutritionalAdvisor {
  private String _endpoint = null;
  private na.ws.NutritionalAdvisor nutritionalAdvisor = null;
  
  public NutritionalAdvisorProxy() {
    _initNutritionalAdvisorProxy();
  }
  
  public NutritionalAdvisorProxy(String endpoint) {
    _endpoint = endpoint;
    _initNutritionalAdvisorProxy();
  }
  
  private void _initNutritionalAdvisorProxy() {
    try {
      nutritionalAdvisor = (new na.ws.NutritionalAdvisorServiceLocator()).getNutritionalAdvisorPort();
      if (nutritionalAdvisor != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)nutritionalAdvisor)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)nutritionalAdvisor)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (nutritionalAdvisor != null)
      ((javax.xml.rpc.Stub)nutritionalAdvisor)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public na.ws.NutritionalAdvisor getNutritionalAdvisor() {
    if (nutritionalAdvisor == null)
      _initNutritionalAdvisorProxy();
    return nutritionalAdvisor;
  }
  
  public java.lang.String addFavouriteRecipe(java.lang.String token, int recipeID) throws java.rmi.RemoteException, na.ws.NutriSecurityException{
    if (nutritionalAdvisor == null)
      _initNutritionalAdvisorProxy();
    return nutritionalAdvisor.addFavouriteRecipe(token, recipeID);
  }
  
  public boolean changeMeal(java.lang.String token, int day, int mealCategory) throws java.rmi.RemoteException, na.ws.NutriSecurityException, na.ws.TokenExpiredException{
    if (nutritionalAdvisor == null)
      _initNutritionalAdvisorProxy();
    return nutritionalAdvisor.changeMeal(token, day, mealCategory);
  }
  
  public java.lang.String deleteFavouriteRecipe(java.lang.String token, int recipeID) throws java.rmi.RemoteException, na.ws.NutriSecurityException, na.ws.TokenExpiredException{
    if (nutritionalAdvisor == null)
      _initNutritionalAdvisorProxy();
    return nutritionalAdvisor.deleteFavouriteRecipe(token, recipeID);
  }
  
  public byte[] getAdvisePicture(java.lang.String token, int adviseID) throws java.rmi.RemoteException, na.ws.NutriSecurityException{
    if (nutritionalAdvisor == null)
      _initNutritionalAdvisorProxy();
    return nutritionalAdvisor.getAdvisePicture(token, adviseID);
  }
  
  public na.miniDao.ShoppingList getCustomShoppingListDays(java.lang.String token, int windowSize, int startDay) throws java.rmi.RemoteException, na.ws.NutriSecurityException{
    if (nutritionalAdvisor == null)
      _initNutritionalAdvisorProxy();
    return nutritionalAdvisor.getCustomShoppingListDays(token, windowSize, startDay);
  }
  
  public na.miniDao.DayMenu getDayMenu(java.lang.String token, int day) throws java.rmi.RemoteException, na.ws.NutriSecurityException, na.ws.TokenExpiredException{
    if (nutritionalAdvisor == null)
      _initNutritionalAdvisorProxy();
    return nutritionalAdvisor.getDayMenu(token, day);
  }
  
  public byte[] getDishPicture(java.lang.String token, int recipeID) throws java.rmi.RemoteException, na.ws.NutriSecurityException{
    if (nutritionalAdvisor == null)
      _initNutritionalAdvisorProxy();
    return nutritionalAdvisor.getDishPicture(token, recipeID);
  }
  
  public na.miniDao.Recipe[] getFavouriteRecipes(java.lang.String token) throws java.rmi.RemoteException, na.ws.NutriSecurityException, na.ws.TokenExpiredException{
    if (nutritionalAdvisor == null)
      _initNutritionalAdvisorProxy();
    return nutritionalAdvisor.getFavouriteRecipes(token);
  }
  
  public na.miniDao.FoodCategory[] getFoodCategories(java.lang.String token) throws java.rmi.RemoteException{
    if (nutritionalAdvisor == null)
      _initNutritionalAdvisorProxy();
    return nutritionalAdvisor.getFoodCategories(token);
  }
  
  public na.miniDao.FoodItem[] getFoodsByCategory(int foodCategoriesID, java.lang.String token) throws java.rmi.RemoteException{
    if (nutritionalAdvisor == null)
      _initNutritionalAdvisorProxy();
    return nutritionalAdvisor.getFoodsByCategory(foodCategoriesID, token);
  }
  
  public na.miniDao.Advise[] getMyAdvises(java.lang.String token) throws java.rmi.RemoteException, na.ws.NutriSecurityException, na.ws.TokenExpiredException{
    if (nutritionalAdvisor == null)
      _initNutritionalAdvisorProxy();
    return nutritionalAdvisor.getMyAdvises(token);
  }
  
  public na.ws.UProperty getMyLocalisedData(java.lang.String token, na.ws.Translation data) throws java.rmi.RemoteException, na.ws.NutriSecurityException, na.ws.TokenExpiredException{
    if (nutritionalAdvisor == null)
      _initNutritionalAdvisorProxy();
    return nutritionalAdvisor.getMyLocalisedData(token, data);
  }
  
  public na.miniDao.Tip[] getMyTips(java.lang.String token) throws java.rmi.RemoteException, na.ws.NutriSecurityException, na.ws.TokenExpiredException{
    if (nutritionalAdvisor == null)
      _initNutritionalAdvisorProxy();
    return nutritionalAdvisor.getMyTips(token);
  }
  
  public na.miniDao.Exercise getNextQuestion(java.lang.String token, int exerciseID, int userQuestionnaireID, int questionID) throws java.rmi.RemoteException, na.ws.NutriSecurityException, na.ws.TokenExpiredException{
    if (nutritionalAdvisor == null)
      _initNutritionalAdvisorProxy();
    return nutritionalAdvisor.getNextQuestion(token, exerciseID, userQuestionnaireID, questionID);
  }
  
  public na.miniDao.Exercise[] getPendingQuestionnaires(java.lang.String token) throws java.rmi.RemoteException, na.ws.NutriSecurityException, na.ws.TokenExpiredException{
    if (nutritionalAdvisor == null)
      _initNutritionalAdvisorProxy();
    return nutritionalAdvisor.getPendingQuestionnaires(token);
  }
  
  public na.miniDao.Exercise getPreviousQuestion(java.lang.String token, int exerciseID, int userQuestionnaireID, int questionID) throws java.rmi.RemoteException, na.ws.NutriSecurityException, na.ws.TokenExpiredException{
    if (nutritionalAdvisor == null)
      _initNutritionalAdvisorProxy();
    return nutritionalAdvisor.getPreviousQuestion(token, exerciseID, userQuestionnaireID, questionID);
  }
  
  public na.miniDao.ShoppingList getSmartCustomShoppingListDays(java.lang.String token, int windowSize, int startDay, java.lang.String[] outsideEvents) throws java.rmi.RemoteException, na.ws.NutriSecurityException{
    if (nutritionalAdvisor == null)
      _initNutritionalAdvisorProxy();
    return nutritionalAdvisor.getSmartCustomShoppingListDays(token, windowSize, startDay, outsideEvents);
  }
  
  public na.miniDao.Exercise getStartingQuestionnaire(java.lang.String token, int questionnaireID, int exerciseID) throws java.rmi.RemoteException, na.ws.NutriSecurityException, na.ws.TokenExpiredException{
    if (nutritionalAdvisor == null)
      _initNutritionalAdvisorProxy();
    return nutritionalAdvisor.getStartingQuestionnaire(token, questionnaireID, exerciseID);
  }
  
  public na.miniDao.DayMenu[] getThisWeekMenu(java.lang.String token) throws java.rmi.RemoteException, na.ws.NutriSecurityException{
    if (nutritionalAdvisor == null)
      _initNutritionalAdvisorProxy();
    return nutritionalAdvisor.getThisWeekMenu(token);
  }
  
  public byte[] getTipPicture(java.lang.String token, int tipID) throws java.rmi.RemoteException, na.ws.NutriSecurityException{
    if (nutritionalAdvisor == null)
      _initNutritionalAdvisorProxy();
    return nutritionalAdvisor.getTipPicture(token, tipID);
  }
  
  public na.miniDao.DayMenu getTodayMenu(java.lang.String token) throws java.rmi.RemoteException, na.ws.NutriSecurityException, na.ws.TokenExpiredException{
    if (nutritionalAdvisor == null)
      _initNutritionalAdvisorProxy();
    return nutritionalAdvisor.getTodayMenu(token);
  }
  
  public java.lang.String getToken(java.lang.String username, java.lang.String password, int preferredLanguage) throws java.rmi.RemoteException, na.ws.NutriSecurityException{
    if (nutritionalAdvisor == null)
      _initNutritionalAdvisorProxy();
    return nutritionalAdvisor.getToken(username, password, preferredLanguage);
  }
  
  public na.miniDao.DayMenu getTomorrowMenu(java.lang.String token) throws java.rmi.RemoteException, na.ws.NutriSecurityException, na.ws.TokenExpiredException{
    if (nutritionalAdvisor == null)
      _initNutritionalAdvisorProxy();
    return nutritionalAdvisor.getTomorrowMenu(token);
  }
  
  public na.ws.UserNutritionalProfile getUserProfile(java.lang.String token, int version) throws java.rmi.RemoteException, na.ws.NutriSecurityException, na.ws.TokenExpiredException{
    if (nutritionalAdvisor == null)
      _initNutritionalAdvisorProxy();
    return nutritionalAdvisor.getUserProfile(token, version);
  }
  
  public na.miniDao.Recipe getUserRecipe(java.lang.String token, int recipeID) throws java.rmi.RemoteException{
    if (nutritionalAdvisor == null)
      _initNutritionalAdvisorProxy();
    return nutritionalAdvisor.getUserRecipe(token, recipeID);
  }
  
  public na.miniDao.ShoppingList getWeeklyShoppingList(java.lang.String token) throws java.rmi.RemoteException, na.ws.NutriSecurityException, na.ws.TokenExpiredException{
    if (nutritionalAdvisor == null)
      _initNutritionalAdvisorProxy();
    return nutritionalAdvisor.getWeeklyShoppingList(token);
  }
  
  public boolean isTokenValid(java.lang.String token) throws java.rmi.RemoteException{
    if (nutritionalAdvisor == null)
      _initNutritionalAdvisorProxy();
    return nutritionalAdvisor.isTokenValid(token);
  }
  
  public java.lang.String[] setQuestionnaireAnswer(java.lang.String token, int exerciseID, int questionID, na.miniDao.Answer[] userAnswers) throws java.rmi.RemoteException, na.ws.NutriSecurityException, na.ws.TokenExpiredException{
    if (nutritionalAdvisor == null)
      _initNutritionalAdvisorProxy();
    return nutritionalAdvisor.setQuestionnaireAnswer(token, exerciseID, questionID, userAnswers);
  }
  
  
}