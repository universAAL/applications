package na.services.recipes.business;

/**
 * Métodos de alto nivel de la capa de negocio correspondiente a:
 * Nutritional Menus (Nutritional Advisor)
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import na.miniDao.Meal;
import na.oasisUtils.ami.AmiConnector;
import na.oasisUtils.trustedSecurityNetwork.TSFConnector;
import na.utils.OASIS_ServiceUnavailable;
import na.utils.ServiceInterface;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class Business {
    private Log log = LogFactory.getLog(Business.class);

    /**
     * Methods related to Nutritional Advisor -> Nutritional Menus
     * 
     * @throws OASIS_ServiceUnavailable
     */

    /*
     * Gets today's menu's for current user
     */
    public na.miniDao.DayMenu getTodayRecipes() throws OASIS_ServiceUnavailable {
	return this.getDayRecipe(ServiceInterface.OP_GetTodayMenu);
    }

    /*
     * Gets tomorrow's menu's for current user
     */
    public na.miniDao.DayMenu getTomorrowRecipes()
	    throws OASIS_ServiceUnavailable {
	return this.getDayRecipe(ServiceInterface.OP_GetTomorrowMenu);
    }

    /*
     * Gets today's menu's for current user Connect with AMI, day
     * ["getTodayMenu", "getTomorrowMenu"]
     */
    public na.miniDao.DayMenu getDayRecipe(String operation)
	    throws OASIS_ServiceUnavailable {
	AmiConnector ami = AmiConnector.getAMI();
	if (ami != null) {
	    String[] input = { TSFConnector.getInstance().getToken() };
	    na.miniDao.DayMenu menus = (na.miniDao.DayMenu) ami
		    .invokeOperation(ServiceInterface.DOMAIN_Nutrition,
			    operation, input, false);

	    if (menus != null) {
		log.info("RecipeBusiness, UsersMenus found ID: "
			+ menus.getUsersMenusID());

		if (menus.getMeals().length == 0) {
		    log.info("RecipeBusiness, no menus!");
		    return null;
		} else { // Everything ok
			 // filter meals with no recipes
		    List<Meal> list = new ArrayList<Meal>();
		    for (na.miniDao.Meal meal : menus.getMeals()) {
			// log.info("meal: "+meal.getCategory() +
			// " dishes: "+Arrays.toString(meal.getDishes()));
			// mostrar solo break, lunch, dinner
			if (meal.getCategory().compareToIgnoreCase(
				ServiceInterface.MealCategory_BreakFast_Str) == 0
				|| meal.getCategory()
					.compareToIgnoreCase(
						ServiceInterface.MealCategory_Lunch_str) == 0
				|| meal.getCategory()
					.compareToIgnoreCase(
						ServiceInterface.MealCategory_Dinner_str) == 0) {
			    list.add(meal);
			}
			// boolean deleteMeal = true;
			// for (miniDao.Dish dish : meal.getDishes()) {
			// // log.info("dish: "+dish.getRecipeID());
			// if (dish.getRecipeID()!=-1 &&
			// dish.isHasProcedure()==true) {
			// log.info(" do not delete");
			// deleteMeal = false;
			// break;
			// }
			// }
			// if (!deleteMeal) {
			// list.add(meal);
			// }
		    }
		    na.miniDao.Meal[] meals = (na.miniDao.Meal[]) list
			    .toArray(new na.miniDao.Meal[list.size()]);
		    Arrays.sort(meals, new Comparator<Meal>() {

			// @Override
			public int compare(Meal o1, Meal o2) {
			    if (o1.getCategory()
				    .compareToIgnoreCase(
					    ServiceInterface.MealCategory_BreakFast_Str) == 0) {
				return -1;
			    }
			    if (o2.getCategory()
				    .compareToIgnoreCase(
					    ServiceInterface.MealCategory_BreakFast_Str) == 0) {
				return 1;
			    }
			    if (o1.getCategory().compareToIgnoreCase(
				    ServiceInterface.MealCategory_Lunch_str) == 0) {
				return -1;
			    }
			    if (o2.getCategory().compareToIgnoreCase(
				    ServiceInterface.MealCategory_Lunch_str) == 0) {
				return 1;
			    }
			    if (o1.getCategory().compareToIgnoreCase(
				    ServiceInterface.MealCategory_Dinner_str) == 0) {
				return -1;
			    }
			    if (o2.getCategory().compareToIgnoreCase(
				    ServiceInterface.MealCategory_Dinner_str) == 0) {
				return 1;
			    }
			    return 0;
			}
		    });
		    menus.setMeals(meals);
		    return menus;
		}
	    } else
		log.info("RecipeBusiness, no menus found.");
	}
	return null;
    }

    /**
     * Obtiene las recetas favoritas del usuario, consultado el Web Service, sin
     * importar lo que ponga en el Profile
     * 
     * @return the my favorite recipes
     * @throws OASIS_ServiceUnavailable
     *             the oASI s_ service unavailable
     */
    public na.miniDao.Recipe[] getMyFavoriteRecipes()
	    throws OASIS_ServiceUnavailable {
	AmiConnector ami = AmiConnector.getAMI();
	if (ami != null) {
	    String[] input = { TSFConnector.getInstance().getToken() };
	    na.miniDao.Recipe[] recipes = (na.miniDao.Recipe[]) ami
		    .invokeOperation(ServiceInterface.DOMAIN_Nutrition,
			    ServiceInterface.OP_GetFavouriteRecipes, input,
			    false);

	    if (recipes != null) {
		log.info("RecipeBusiness, FavouriteRecipes found: "
			+ recipes.length);

		if (recipes.length == 0) {
		    log.info("RecipeBusiness, no favourite recipes!!");
		    return recipes;
		} else { // Everything ok
		    return recipes;
		}
	    } else
		log.info("RecipeBusiness, no recipes found!");
	}
	return null;
    }

    public na.miniDao.Recipe getRecipe(int recipeID)
	    throws OASIS_ServiceUnavailable {
	AmiConnector ami = AmiConnector.getAMI();
	if (ami != null) {
	    String[] input = { TSFConnector.getInstance().getToken(),
		    String.valueOf(recipeID) };
	    na.miniDao.Recipe recipe = (na.miniDao.Recipe) ami.invokeOperation(
		    ServiceInterface.DOMAIN_Nutrition,
		    ServiceInterface.OP_GetUserRecipe, input, false);

	    if (recipe != null) {
		return recipe;
	    } else
		log.info("RecipeBusiness, no menus found.");
	}
	return null;
    }

    /**
     * Intenta guardar la receta via WS remoto
     * 
     * @return true when everything went fine
     * @throws OASIS_ServiceUnavailable
     *             the oASI s_ service unavailable
     */
    public boolean addRecipeToFavoritesWebServer(int recipeID)
	    throws OASIS_ServiceUnavailable {
	AmiConnector ami = AmiConnector.getAMI();
	if (ami != null) {
	    String[] input = { TSFConnector.getInstance().getToken(),
		    String.valueOf(recipeID) };
	    String recipe = (String) ami.invokeOperation(
		    ServiceInterface.DOMAIN_Nutrition,
		    ServiceInterface.OP_AddFavouriteRecipe, input, false);

	    if (recipe != null && recipe.equalsIgnoreCase("success")) {
		return true;
	    } else {
		log.info("Couldn't add the recipe");
	    }
	}
	return false;
    }

    public boolean removeRecipeFromFavoritesWebServer(int recipeID)
	    throws OASIS_ServiceUnavailable {
	AmiConnector ami = AmiConnector.getAMI();
	if (ami != null) {
	    String[] input = { TSFConnector.getInstance().getToken(),
		    String.valueOf(recipeID) };
	    String recipe = (String) ami.invokeOperation(
		    ServiceInterface.DOMAIN_Nutrition,
		    ServiceInterface.OP_DeleteFavouriteRecipe, input, false);

	    if (recipe.equalsIgnoreCase("success")) {
		return true;
	    } else
		log.info("Couldn't delete the recipe");
	}
	return false;
    }

    // public boolean shareRecipeToSocialCommunity(int recipeID, String course,
    // String procedure) throws OASIS_ServiceUnavailable {
    // AmiConnector ami = AmiConnector.getAMI();
    // if (ami != null) {
    // // 1. Login
    // String[] input =
    // {TSFConnector.getInstance().getSocialCommunitiesUsername(),
    // TSFConnector.getInstance().getSocialCommunitiesPassword()};
    // na.oasisUtils.socialCommunity.Friends.AuthToken token=
    // (na.oasisUtils.socialCommunity.Friends.AuthToken)
    // ami.invokeOperation(ServiceInterface.DOMAIN_SocialCommunity,
    // ServiceInterface.OP_Social_FriendsLogin, input,
    // Setup.use_WS_toConnectToSocialCommunities());
    // if (token==null)
    // return false;
    //
    // // 2. GetMyGroups
    // na.oasisUtils.socialCommunity.Friends.AuthToken[] input2 = {token};
    // GroupsResponse groupResponse= (GroupsResponse)
    // ami.invokeOperation(ServiceInterface.DOMAIN_SocialCommunity,
    // ServiceInterface.OP_Social_GetMyGroupsResponse, input2,
    // Setup.use_WS_toConnectToSocialCommunities());
    // if (groupResponse==null)
    // return false;
    //
    // // 3. GetGroups
    // Group[] groupList= groupResponse.getGroups();
    // for (Group group : groupList) {
    // log.info("group: "+ group.getId() +" "+ group.getName());
    // // // 3. Encontrar grupo y añadir receta
    // if (group!=null && group.getName()!=null &&
    // group.getName().compareToIgnoreCase("Shared Recipes")==0) {
    // FriendsPortProxy friends = new FriendsPortProxy();
    // Object[] input4 = {group,token, friends, course, procedure};
    // StatusResponse response= (StatusResponse)
    // ami.invokeOperation(ServiceInterface.DOMAIN_SocialCommunity,
    // ServiceInterface.OP_Social_AddTopic, input4,
    // Setup.use_WS_toConnectToSocialCommunities());
    // log.info("response: "+response);
    // if (response!=null && response.getStatus().compareTo("0")==0)
    // return true;
    // // else return false;
    // }
    // }
    //
    // // no existe el grupo, lo creo? //TODO: Crear grupo en Social Communities
    // si no existe?
    // } else {
    // log.info("Couldn't get AMI");
    // }
    // return false;
    // }

    public byte[] getDishPicture(int recipeID) throws OASIS_ServiceUnavailable {
	AmiConnector ami = AmiConnector.getAMI();
	if (ami != null) {
	    String[] input = { TSFConnector.getInstance().getToken(),
		    "" + recipeID };
	    byte[] picBytes = (byte[]) ami.invokeOperation(
		    ServiceInterface.DOMAIN_Nutrition,
		    ServiceInterface.OP_GetDishPicture, input, false);

	    if (picBytes != null) {
		log.info("Business, bytes found");
		return picBytes;
	    } else
		log.info("Business, no bytes found.");
	}
	return null;
    }

}
