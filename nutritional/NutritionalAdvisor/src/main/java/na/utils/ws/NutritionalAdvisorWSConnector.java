package na.utils.ws;

import java.rmi.RemoteException;

import na.miniDao.Advise;
import na.miniDao.Exercise;
import na.miniDao.Recipe;
import na.ws.NutriSecurityException;
import na.ws.NutritionalAdvisorProxy;
import na.ws.TokenExpiredException;
import na.ws.Translation;
import na.ws.UProperty;
import na.utils.ServiceInterface;
import na.utils.Utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;



public class NutritionalAdvisorWSConnector {
	private Log log = LogFactory.getLog(NutritionalAdvisorWSConnector.class);
	private NutritionalAdvisorProxy nm = new NutritionalAdvisorProxy();
//	SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
	
	private static final boolean SHOW_CONNECTION_LOG = true; 
	
	
	/*
	 * Invokes the requested operation if available. Basic implementation
	 */
	public Object invokeOperation(String operationName, int uniqueID, Object[] input3) throws RemoteException {
		Object[] input = input3;
		if (operationName.compareTo(ServiceInterface.OP_GetTodayMenu) == 0) {
			return this.getTodayMenu((String)input[0]);
		} else if (operationName.compareTo(ServiceInterface.OP_GetTomorrowMenu) == 0) {
			return this.getTomorrowMenu((String)input[0]);
		} else if (operationName.compareTo(ServiceInterface.OP_GetThisWeekMenu) == 0) {
			return this.getThisWeekMenu((String)input[0]);
		} else if (operationName.compareTo(ServiceInterface.OP_ChangeMeal) == 0) {
			try {
				return this.changeMeal((String)input[0], Integer.parseInt((String)input[1]), Integer.parseInt((String)input[2]));
			} catch (NumberFormatException e) {
				e.printStackTrace();
				return null;
			}
		} else if (operationName.compareTo(ServiceInterface.OP_GetUserRecipe) == 0) {
			return this.getUserRecipe((String)input[0], Integer.parseInt((String)input[1]));
//		} else if (alignedOperationName.compareTo("getUserProfile") == 0) {
//			return this.getUserProfile((String)input[0]);
//		} else if (alignedOperationName.compareTo("getWeeklyShoppingList") == 0) {
//			return this.getWeeklyShoppingList((String)input[0]);
		} else if (operationName.compareTo(ServiceInterface.OP_GetCustomShoppingList) == 0) {
			return this.getCustomShoppingList((String)input[0], Integer.parseInt((String)input[1]),  ((Integer)input[2]).intValue());
		} else if (operationName.compareTo(ServiceInterface.OP_GetFoodCategories) == 0) {
			return this.getFoodCategories((String)input[0]);
		} else if (operationName.compareTo(ServiceInterface.OP_GetFoodsByCategory) == 0) {
			return this.getFoodsByCategory(Integer.parseInt((String)input[0]), (String)input[1]);
//		} else if (alignedOperationName.compareTo("getDailyWarnings") == 0) {
//			return this.getDailyWarnings((String)input[0]);
		} else if (operationName.compareTo(ServiceInterface.OP_GetPendingQuestionnaires) == 0) {
			return this.getPendingQuestionnaires((String)input[0]);
		} else if (operationName.compareTo(ServiceInterface.OP_GetStartQuestionnaire) == 0) {
			return this.getStartQuestionnaire((String)input[0], Integer.parseInt((String)input[1]), Integer.parseInt((String)input[2]));
		} else if (operationName.compareTo(ServiceInterface.OP_GetNextQuestion) == 0) {
			return this.getQuestion((String)input[0], Integer.parseInt((String)input[1]), Integer.parseInt((String)input[2]), Integer.parseInt((String)input[3]));
		} else if (operationName.compareTo(ServiceInterface.OP_GetPreviousQuestion) == 0) {
			return this.getPreviousQuestion((String)input[0], Integer.parseInt((String)input[1]), Integer.parseInt((String)input[2]));
		} else if (operationName.compareTo(ServiceInterface.OP_GetToken) == 0) {
			return this.getToken((String)input[0],(String)input[1], Integer.parseInt((String)input[2]));
		} else if (operationName.compareTo(ServiceInterface.OP_GetMyAdvises) == 0) {
			return this.getMyAdvises((String)input[0]);
		} else if (operationName.compareTo(ServiceInterface.OP_GetFavouriteRecipes) == 0) {
			return this.getFavouriteRecipes((String)input[0]);
		} else if (operationName.compareTo(ServiceInterface.OP_AddFavouriteRecipe) == 0) {
			return this.addFavouriteRecipe((String)input[0], Integer.parseInt((String)input[1]));
		} else if (operationName.compareTo(ServiceInterface.OP_DeleteFavouriteRecipe) == 0) {
			return this.deleteFavouriteRecipes((String)input[0], Integer.parseInt((String)input[1]));
		} else if (operationName.compareTo(ServiceInterface.OP_GetTips) == 0) {
			return this.getTips((String)input[0]);
		} else if (operationName.compareTo(ServiceInterface.OP_GetDishPicture) == 0) {
			return this.getDishPicture((String)input[0], Integer.parseInt((String)input[1]));
//		} else if (operationName.compareTo(ServiceInterface.OP_GetMyModernAdvises) == 0) {
//			return this.getMyModernAdvises((String)input[0]);
		} else if (operationName.compareTo(ServiceInterface.OP_GetAdvisePicture) == 0) {
			return this.getAdvisePicture((String)input[0], Integer.parseInt((String)input[1]));
		} else if (operationName.compareTo(ServiceInterface.OP_SetQuestionnaireAnswer) == 0) {
			return this.setQuestionnaireAnswer((String)input[0], Integer.parseInt((String)input[1]), Integer.parseInt((String)input[2]), (na.miniDao.Answer[])input[3]);
		} else if (operationName.compareTo(ServiceInterface.OP_GetTipPicture) == 0) {
			return this.getTipPicture((String)input[0], Integer.parseInt((String)input[1]));
		} else if (operationName.compareTo(ServiceInterface.OP_GetSmartCustomShoppingList) == 0) {
			return this.getSmartCustomShoppingList((String)input[0], Integer.parseInt((String)input[1]),  ((Integer)input[2]).intValue(), (String[])input[3]);
//		} else if (operationName.compareTo(ServiceInterface.OP_GetMyToken) == 0) {
//			return this.getMyToken((String)input[0], (String)input[1],  new Integer((String)input[2]));
		} else if (operationName.compareTo(ServiceInterface.OP_GetLocalisedProfile) == 0) {
			return this.getLocalisedData((String)input[0], (Translation)input[1]);
		} else if (operationName.compareTo(ServiceInterface.OP_GetFullFoodCategories) == 0) {
			return this.getFullFoodCategories((String)input[0]);
		} else if (operationName.compareTo(ServiceInterface.OP_GetFullFood) == 0) {
			return this.getFullFood(Integer.parseInt((String)input[0]), (String)input[1]);
		} else{
			log.error("NutritionalAdvisorWSConnector: Unknown operation: "+operationName);
			return null;
		}
	}
	
	private Object getFullFood(int foodID, String token) throws RemoteException {
		return nm.getFullFood(foodID, token);
	}

	private na.miniDao.full.FoodCategory[] getFullFoodCategories(String token) throws RemoteException {
		return nm.getFullFoodCategories(token);
	}

	private UProperty getLocalisedData(String token, Translation data) throws NutriSecurityException, TokenExpiredException, RemoteException {
		return nm.getMyLocalisedData(token, data);
	}

	private Object getMyToken(String username, String userid, int prefLang) throws NutriSecurityException, RemoteException {
		log.info("NutritionalAdvisorWSConnector: getMyToken for userrr: " + username + " userid: "+userid);
		String res = nm.getToken(username, userid, prefLang);
		return res;
	}

	private Object getTipPicture(String token, int tipID) {
		log.info("NutritionalAdvisorWSConnector: request web service getTipPicture with token:" + token+ " tipID: "+tipID);
		try {
			byte[] result = nm.getTipPicture(token, tipID);
			return result;
		} catch (RemoteException e) {
			this.showConnectionError(e);
		}
		return null;
	}

	public Object invokeOperationSetQuestionnaireAnswer(String domainName,
			String alignedOperationName, int uniqueID, Object[] input) {
		return this.setQuestionnaireAnswer((String)input[0], (new Integer((String)input[1])).intValue(),
				(new Integer((String)input[1])).intValue(),
				(na.miniDao.Answer[])input[2]);
	}
	
	/*
	 * Inner classes
	 */
	private na.miniDao.DayMenu getTodayMenu(String token) throws NutriSecurityException, TokenExpiredException, RemoteException {
		
		log.info("NutritionalAdvisorWSConnector: request web service getTodayMenu for user:" + token);
//		try {
			long start = System.nanoTime();    
			na.miniDao.DayMenu menus;
				menus = nm.getTodayMenu(token);
			long elapsedTime = System.nanoTime() - start;
			double seconds = (double)elapsedTime / 1000000000.0;
			log.info(">>>> Elapsed time: "+ seconds + " seconds");
			if (menus!= null)
				log.info("Found menu: "+menus.getUsersMenusID());
			else
				log.info("Couldn't find any menu for user: " + token);
			return menus;
//		} catch (RemoteException e) {
//			this.showConnectionError(e);
//		}
//		return null;
	}


	private na.miniDao.DayMenu getTomorrowMenu(String token) {
//		NutritionalMenusProxy nm = new NutritionalMenusProxy();
		log.info("NutritionalAdvisorWSConnector: request web service getTomorrowMenu for user:" + token);
		try {
			long start = System.nanoTime();
			na.miniDao.DayMenu menus = nm.getTomorrowMenu(token);
			long elapsedTime = System.nanoTime() - start;
			double seconds = (double)elapsedTime / 1000000000.0;
			log.info(">>>> Elapsed time: "+ seconds + " seconds");
			if (menus!= null)
				log.info("Found menu: "+menus.getUsersMenusID());
			else
				log.info("Couldn't find any menu for user: " + token);
			return menus;
		} catch (RemoteException e) {
			this.showConnectionError(e);
		}
		return null;
	}
	
	private na.miniDao.DayMenu[] getThisWeekMenu(String token) {
		log.info("NutritionalAdvisorWSConnector: request web service getSmallThisWeekMenu for user:" + token);
		try {
			long start = System.nanoTime();
			na.miniDao.DayMenu[] menus = nm.getThisWeekMenu(token);
			long elapsedTime = System.nanoTime() - start;
			double seconds = (double)elapsedTime / 1000000000.0;
			log.info(">>>> Elapsed time: "+ seconds + " seconds");
			if (menus!= null)
				log.info("Found menu length: "+menus.length);
			else
				log.info("Couldn't find any menu for user: " + token);
			return menus;
		} catch (RemoteException e) {
			this.showConnectionError(e);
		}
		return null;
	}

	private boolean changeMeal(String token, int day, int mealCategory) {
//		NutritionalMenusProxy nm = new NutritionalMenusProxy();
		log.info("NutritionalAdvisorWSConnector: request web service changeMeal for user:" + token + " day: " + day + " mealCategory: " + mealCategory);
		try {
			return nm.changeMeal(token, day, mealCategory);
		} catch (RemoteException e) {
			this.showConnectionError(e);
		}
		return false;
	}
	
	
//	private miniDao.Recipe getRecipe(String token, int recipeID) {
//		log.info("NutritionalAdvisorWSConnector: request web service getRecipe for recipe:" + recipeID);
//		try {
//			long start = System.nanoTime();
//			miniDao.Recipe recipe = nm.getUserRecipe(token, recipeID);
//			long elapsedTime = System.nanoTime() - start;
//			double seconds = (double)elapsedTime / 1000000000.0;
//			log.info(">>>> Elapsed time: "+ seconds + " seconds");
//			if (recipe!= null)
//				log.info("Found recipe: "+recipe.getRecipeID());
//			else
//				log.info("Couldn't find any recipe: " + recipeID);
//			return recipe;
//		} catch (RemoteException e) {
//			this.showConnectionError(e);
//		}
//		return null;
//	}
	
	private na.miniDao.Recipe getUserRecipe(String token, int recipeID) {
		log.info("NutritionalAdvisorWSConnector: request web service getUserRecipe for recipe:" + recipeID);
		try {
			long start = System.nanoTime();
			na.miniDao.Recipe recipe = nm.getUserRecipe(token, recipeID);
			long elapsedTime = System.nanoTime() - start;
			double seconds = (double)elapsedTime / 1000000000.0;
			log.info(">>>> Elapsed time: "+ seconds + " seconds");
			if (recipe!= null)
				log.info("Found recipe: "+recipe.getRecipeID());
			else
				log.info("Couldn't find any recipe: " + recipeID);
			return recipe;
		} catch (RemoteException e) {
			this.showConnectionError(e);
		}
		return null;
	}
	
//	private byte[] getUserProfile(String token) {
//		log.info("NutritionalAdvisorWSConnector: request web service getUserProfile for user:" + token);
////		try {
////			return nm.getUserProfile(token); 
////		} catch (RemoteException e) {
////			this.showConnectionError(e);
////		}
//		return null;
//	}
//	
//	private miniDao.ShoppingList getWeeklyShoppingList(String token) {
//		log.info("NutritionalAdvisorWSConnector: request web service getShoppingList for user:" + token);
//		try {
//			miniDao.ShoppingList list = nm.getWeeklyShoppingList(token);
//			if (list!= null)
//				log.info("Found lists!");
//			else
//				log.info("Couldn't find any list for user: "+token);
//			return list;
//		} catch (RemoteException e) {
//			this.showConnectionError(e);
//		}
//		return null;
//	}
	
	private na.miniDao.ShoppingList getCustomShoppingList(String token, int size, int startDay) {
		log.info("NutritionalAdvisorWSConnector: request web service getCustomShoppingList for user:" + token);
		try {
//			log.info("Fecha de inicio: "+Utils.getStringDate(startDay) + " " + Utils.getStringDay(startDay.get(Calendar.DAY_OF_WEEK)));
			na.miniDao.ShoppingList list = nm.getCustomShoppingListDays(token, size, startDay);
			if (list!= null)
				log.info("Found lists!");
			else
				log.info("Couldn't find any list for user: "+token);
			return list;
		} catch (RemoteException e) {
			this.showConnectionError(e);
		}
		return null;
	}
	
	private na.miniDao.ShoppingList getSmartCustomShoppingList(String token, int size, int startDay, String[] outsideEvents) {
		log.info("NutritionalAdvisorWSConnector: request web service getSmartCustomShoppingList for user:" + token);
		try {
			na.miniDao.ShoppingList list = nm.getSmartCustomShoppingListDays(token, size, startDay, outsideEvents);
			if (list!= null)
				log.info("Found lists!");
			else
				log.info("Couldn't find any list for user: "+token);
			return list;
		} catch (RemoteException e) {
			this.showConnectionError(e);
		}
		return null;
	}
	
	private na.miniDao.FoodCategory[] getFoodCategories(String token) {
		NutritionalAdvisorProxy nm = new NutritionalAdvisorProxy();
		log.info("NutritionalAdvisorWSConnector: request web service getFoodCategories");
		try {
			na.miniDao.FoodCategory[] list = nm.getFoodCategories(token);
			if (list!= null)
				log.info("Found foodCategories!");
			else
				log.info("Couldn't find any foodCategory");
			return list;
		} catch (RemoteException e) {
			this.showConnectionError(e);
		}
		return null;
	}
	
	private na.miniDao.FoodItem[] getFoodsByCategory(int foodCategoriesID, String token) {
		NutritionalAdvisorProxy nm = new NutritionalAdvisorProxy();
		log.info("NutritionalAdvisorWSConnector: request web service getFoodsByCategory");
		try {
			na.miniDao.FoodItem[] list = nm.getFoodsByCategory(foodCategoriesID, token);
			if (list!= null)
				log.info("Found foods!");
			else
				log.info("Couldn't find any food for category: "+ foodCategoriesID);
			return list;
		} catch (RemoteException e) {
			this.showConnectionError(e);
		}
		return null;
	}
	
	private na.miniDao.Exercise[] getPendingQuestionnaires(String token) {
		log.info("NutritionalAdvisorWSConnector: request web service getPendingQuestionnaires for user:" + token);
		try {
			return nm.getPendingQuestionnaires(token);
		} catch (RemoteException e) {
			this.showConnectionError(e);
		}
		return null;
	}
	
	private na.miniDao.Exercise getStartQuestionnaire(String token, int userQuestionnaire, int exerciseID) {
		log.info("NutritionalAdvisorWSConnector: request web service getStartQuestionnaire with ID:" + userQuestionnaire);
		try {
			return nm.getStartingQuestionnaire(token,userQuestionnaire, exerciseID);
		} catch (RemoteException e) {
			this.showConnectionError(e);
		}
		return null;
	}
	
	private Exercise getQuestion(String token, int exerciseID, int userQuestionnaireID, int questionID) {
		log.info("NutritionalAdvisorWSConnector: request web service getQuestion with ID:" + questionID);
		try {
			return nm.getNextQuestion(token, exerciseID,userQuestionnaireID, questionID);
		} catch (RemoteException e) {
			this.showConnectionError(e);
		}
		return null;
	}
	

	private Exercise getPreviousQuestion(String token, int exerciseID, int userQuestionnaireID) {
		log.info("NutritionalAdvisorWSConnector: request web service getPreviousQuestion");
		try {
			return nm.getPreviousQuestion(token, exerciseID,userQuestionnaireID, 1);
		} catch (RemoteException e) {
			this.showConnectionError(e);
		}
		return null;
	}
	
	private Advise[] getMyAdvises(String token) {
		log.info("NutritionalAdvisorWSConnector: request web service getMyAdvises with token:" + token);
		try {
			return nm.getMyAdvises(token);
		} catch (RemoteException e) {
			this.showConnectionError(e);
		}
		return null;
	}
	
//	private Advise[] getMyModernAdvises(String token) {
//		log.info("NutritionalAdvisorWSConnector: request web service getMyModernAdvises with token:" + token);
//		try {
//			return nm.getMyAdvises(token);
//		} catch (RemoteException e) {
//			this.showConnectionError(e);
//		}
//		return null;
//	}
//	
	private na.miniDao.Recipe[] getFavouriteRecipes(String token) {
		log.info("NutritionalAdvisorWSConnector: request web service getFavouriteRecipes with token:" + token);
		try {
			na.miniDao.Recipe[] result = nm.getFavouriteRecipes(token);
			log.info("result: "+result.length);
			if (result!= null) {
				for (Recipe recipe : result) {
					log.info("looping recipeID: "+recipe.getRecipeID());
				}
				if (result.length == 1) {
					if (result[0].getRecipeID()==-1) {
						log.info("No recipes in the server");
						return new na.miniDao.Recipe[0];
					} else {
						log.info("Servidor con 1 receta: "+result[0].getRecipeID());
					}
				} else {
					log.info("result tamaño: " + result.length);
				}
			} else {
				log.info("result es null");
			}
			return result;
		} catch (RemoteException e) {
			this.showConnectionError(e);
		}
		return null;
	}
	
	private String addFavouriteRecipe(String token, int recipeID) {
		log.info("NutritionalAdvisorWSConnector: request web service addFavouriteRecipes with token:" + token);
		try {
			return nm.addFavouriteRecipe(token, recipeID);
		} catch (RemoteException e) {
			this.showConnectionError(e);
		}
		return null;
	}

	private String deleteFavouriteRecipes(String token, int recipeID) {
		log.info("NutritionalAdvisorWSConnector: request web service getFavouriteRecipes with token:" + token);
		try {
			String result = nm.deleteFavouriteRecipe(token, recipeID);
			log.info("result is: "+result);
			return result;
		} catch (RemoteException e) {
			this.showConnectionError(e);
		}
		return null;
	}
	
	private na.miniDao.Tip[] getTips(String token) {
		log.info("NutritionalAdvisorWSConnector: request web service getTips with token:" + token);
		try {
			na.miniDao.Tip[] result = nm.getMyTips(token);
			log.info("result is: "+result.length + " tips found");
			return result;
		} catch (RemoteException e) {
			this.showConnectionError(e);
		}
		return null;
	}
	
	private Object getDishPicture(String token, int recipeID) {
		log.info("NutritionalAdvisorWSConnector: request web service getDishPicture with token:" + token+ " recipeID: "+recipeID);
		try {
			byte[] result = nm.getDishPicture(token, recipeID);
			return result;
		} catch (RemoteException e) {
			this.showConnectionError(e);
		}
		return null;
	}
	
	private Object getAdvisePicture(String token, int adviseID) {
		log.info("NutritionalAdvisorWSConnector: request web service getAdvisePicture with token:" + token+ " adviseID: "+adviseID);
		try {
			byte[] result = nm.getAdvisePicture(token, adviseID);
			return result;
		} catch (RemoteException e) {
			this.showConnectionError(e);
		}
		return null;
	}
	
	private String[] setQuestionnaireAnswer(String token, int exerciseID, int questionID, na.miniDao.Answer[] answers) {
		log.info("NutritionalAdvisorWSConnector: request web service setQuestionnaireAnswer with ID:" + exerciseID);
		try {
			return nm.setQuestionnaireAnswer(token, exerciseID, questionID, answers);
		} catch (RemoteException e) {
			this.showConnectionError(e);
		}
		return null;
	}
	
//	private String shiftMenuWeek(String token) {
//		log.info("NutritionalAdvisorWSConnector: shiftWeekMenu for user with ID:" + token);
//		try {
//			SchedulerProxy sche = new SchedulerProxy();
//			if (sche.shiftMenuWeek(token)) {
//				return "ok";
//			} else {
//				return "error";
//			}
//			
//		} catch (RemoteException e) {
//			this.showConnectionError(e);
//		}
//		return null;
//	}
	
	
	private String getToken(String username, String password, int preferredLanguage) throws RemoteException{
		log.info("NutritionalAdvisorWSConnector: getToken for userrr: " + username + " password: "+password);
//		try {
			String res = nm.getToken(username, password, preferredLanguage);
			return res;
			
//		} catch (RemoteException e) {
//			this.showConnectionError(e);
//		}
//		return null;
	}
	
//	private int[] convertStringArrayToIntArray(String[] sArray) {
//		if (sArray != null) {
//			int intArray[] = new int[sArray.length];
//			for (int i = 0; i < sArray.length; i++) {
//				intArray[i] = Integer.parseInt(sArray[i]);
//			}
//			return intArray;
//		}
//		return null;
//	}

	private void showConnectionError(RemoteException e) {
		if (SHOW_CONNECTION_LOG) {
			log.info("Error: "+e.toString());
			log.info("Stack Trace:");
			e.printStackTrace();
		} else {
			log.info(" >>> Error al conectar: " + e.getCause());
		}
	}

	
}
