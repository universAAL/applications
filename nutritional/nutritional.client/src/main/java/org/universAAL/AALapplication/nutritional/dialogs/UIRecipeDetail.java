package org.universAAL.AALapplication.nutritional.dialogs;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Locale;
import org.universAAL.middleware.xsd.Base64Binary;

import na.miniDao.Recipe;
import na.miniDao.RecipeIngredient;
import na.oasisUtils.profile.ProfileConnector;
import na.services.recipes.business.Business;
import na.utils.OASIS_ServiceUnavailable;
import na.ws.UserNutritionalProfile;

import org.universAAL.AALapplication.nutritional.SharedResources;
import org.universAAL.AALapplication.nutritional.utils.Utils;
import org.universAAL.middleware.owl.supply.LevelRating;
import org.universAAL.middleware.rdf.PropertyPath;
import org.universAAL.middleware.rdf.Resource;
import org.universAAL.middleware.ui.UIRequest;
import org.universAAL.middleware.ui.UIResponse;
import org.universAAL.middleware.ui.owl.PrivacyLevel;
import org.universAAL.middleware.ui.rdf.Form;
import org.universAAL.middleware.ui.rdf.Group;
import org.universAAL.middleware.ui.rdf.Label;
import org.universAAL.middleware.ui.rdf.MediaObject;
import org.universAAL.middleware.ui.rdf.SimpleOutput;
import org.universAAL.middleware.ui.rdf.Submit;
import org.universAAL.ontology.recommendations.HorizontalAlignment;
import org.universAAL.ontology.recommendations.VerticalLayout;

/**
 * The UI class that builds the Recipe Form and handles its associated response
 * submits.
 * 
 * @author alfiva
 * 
 */
public class UIRecipeDetail {

    public static final String PREFIX = InterfaceProvider.MY_UI_NAMESPACE
	    + "UIRecipeDetail"; //$NON-NLS-1$

    static final String SUBMIT_GOBACK = PREFIX + "back"; //$NON-NLS-1$
    static final String SUBMIT_TODAY = PREFIX + "today"; //$NON-NLS-1$
    static final String SUBMIT_TOMORROW = PREFIX + "tomorrow"; //$NON-NLS-1$
   // static final String SUBMIT_WEEK = PREFIX + "week"; //$NON-NLS-1$
   // static final String SUBMIT_TIPS = PREFIX + "tips"; //$NON-NLS-1$
  //  static final String SUBMIT_ADDTOFAV = PREFIX + "addToFav"; //$NON-NLS-1$
    static final String SUBMIT_ADDDISLIKE = PREFIX + "rem"; //$NON-NLS-1$

    static final String USER_INPUT_REF1 = PREFIX + "ref1"; //$NON-NLS-1$
    static final String USER_INPUT_REF2 = PREFIX + "ref2"; //$NON-NLS-1$
    static final String USER_INPUT_REF3 = PREFIX + "ref3"; //$NON-NLS-1$

    static final PropertyPath PROP_PATH_RECIPE_NAME = new PropertyPath(null,
	    false, new String[] { USER_INPUT_REF1 });

    static final PropertyPath PROP_PATH_RECIPE_CATEGORY = new PropertyPath(
	    null, false, new String[] { USER_INPUT_REF2 });
    static final PropertyPath PROP_PATH_RECIPE_PROCEDURE = new PropertyPath(
	    null, false, new String[] { USER_INPUT_REF3 });

    /**
     * Compose the form for this dialog.
     * 
     * @param idRecipe
     *            The ID of the recipe to show.
     * @return The form.
     */
    public static Form getForm(int idRecipe) {
	Utils.println(Messages.getString("UIRecipeDetail.0")); //$NON-NLS-1$
	Form f = Form.newDialog(
		Messages.getString("UIRecipeDetail.1"), new Resource()); //$NON-NLS-1$
	 f.getIOControls().addAppearanceRecommendation(new VerticalLayout());
	    f.getIOControls().addAppearanceRecommendation(HorizontalAlignment.left);
//	f.setProperty("http://ontology.itaca.es/ClassicGUI.owl#layout",
//		"constant,vertical,left");
	Recipe recipe = getRecipe(idRecipe);
	if (recipe != null) {
	    String recipeName = recipe.getCourse();
	    String dishCategory = recipe.getDishCategory();
	    String recipeProcedure = recipe.getProcedure();
	    String recipePicture = recipe.getPicture();

	    Group groupDesc = new Group(f.getIOControls(), new Label(
		    Messages.getString("UIRecipeDetail.16"), null), //$NON-NLS-1$
		    PROP_PATH_RECIPE_NAME, null, null);
	    groupDesc.addAppearanceRecommendation(new VerticalLayout());
	    groupDesc.addAppearanceRecommendation(HorizontalAlignment.left);
	    if (recipeName != null && !recipeName.isEmpty()) {
		new SimpleOutput(groupDesc, new Label(
			Messages.getString("UIRecipeDetail.2"), null), //$NON-NLS-1$
			PROP_PATH_RECIPE_NAME, recipeName);
	    }
	    if (dishCategory != null && !dishCategory.isEmpty()) {
		new SimpleOutput(groupDesc, new Label(
			Messages.getString("UIRecipeDetail.3"), null), //$NON-NLS-1$
			PROP_PATH_RECIPE_CATEGORY, dishCategory);
	    }
	    if (recipePicture != null && !recipePicture.isEmpty()) {
	    	//byte[] decoded;
			try {
				//decoded = Base64Binary.decode(InterfaceProvider.IMG_URL + recipePicture);
				int dot_position = recipePicture.lastIndexOf(".");
				String extension= recipePicture.substring(recipePicture.lastIndexOf("."));
			 	String final_name = recipePicture.substring(0, dot_position)
						.replace("_", "").replace(" ", "")
						.replace("-", "").replace("ñ", "n");
			new MediaObject(groupDesc, new Label(
				new String(" "),new String(" ")), //$NON-NLS-1$
				"image", InterfaceProvider.IMG_URL.concat(final_name).concat(extension));
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	    } else new MediaObject(groupDesc, new Label(
				new String(" "), //$NON-NLS-1$
				new String(" ")), "image", //$NON-NLS-1$
				InterfaceProvider.IMG_URL.concat("default.jpg"));


	    Group groupIngredients = new Group(
		    f.getIOControls(),
		    new Label(Messages.getString("UIRecipeDetail.4"), null), PROP_PATH_RECIPE_CATEGORY, null, null); //$NON-NLS-1$
	    groupIngredients.addAppearanceRecommendation(new VerticalLayout());
	    groupIngredients.addAppearanceRecommendation(HorizontalAlignment.left);
	    RecipeIngredient[] ingredients = recipe.getRecipeIngredients();
	    if (ingredients != null && ingredients.length > 0) {
		for (int indexIngredient = 0; indexIngredient < ingredients.length; indexIngredient++) {
		    RecipeIngredient ingredient = ingredients[indexIngredient];
		    if (ingredient != null) {
			new SimpleOutput(groupIngredients,
				new Label("�", null), null,
				ingredient.getDescription());
//			new Submit(groupIngredients, new Label(
//				Messages.getString("UIItemList.3"), null),
//				SUBMIT_ADDDISLIKE + indexIngredient + "@"
//					+ ingredient.getFoodID() + "@"
//					+ ingredient.getDescription() + "@"
//					+ idRecipe);
		    } else {
			Utils.println("UIRecipeDetail: Ingredient with index: " //$NON-NLS-1$
				+ indexIngredient + " is null"); //$NON-NLS-1$
		    }

		}
	    }

	    Group groupProcedure = new Group(
		    f.getIOControls(),
		    new Label(Messages.getString("UIRecipeDetail.5"), null), PROP_PATH_RECIPE_CATEGORY, null, null); //$NON-NLS-1$
	    groupProcedure.addAppearanceRecommendation(new VerticalLayout());
	    groupProcedure.addAppearanceRecommendation(HorizontalAlignment.left);
	    new SimpleOutput(groupProcedure,
		    new Label("", null), //$NON-NLS-1$
		    PROP_PATH_RECIPE_PROCEDURE,
		    (recipeProcedure != null && !recipeProcedure.isEmpty()) ? recipeProcedure
			    : Messages.getString("UIRecipeDetail.15"));

//	    new Submit(f.getSubmits(), new Label(
//		    Messages.getString("UIRecipeDetail.9"), null), //$NON-NLS-1$
//		    SUBMIT_ADDTOFAV + "@" + recipe.getRecipeID());

	} else {
	    Utils.println("UIRecipeDetail: Couldn't get recipe"); //$NON-NLS-1$
	    new SimpleOutput(f.getIOControls(), null, null,
		    Messages.getString("UIRecipeDetail.11")); //$NON-NLS-1$
	}

	// add an exit button for quitting the dialog
	new Submit(f.getSubmits(), new Label(
		Messages.getString("UIRecipeDetail.12"), null), SUBMIT_GOBACK); //$NON-NLS-1$

	return f;
    }

    /**
     * The main InterfaceProvider delegates calls to the handleUIResponse of the
     * UICaller to this one if the prefix of the pressed submit ID matches this
     * class� one (it�s one of its submits).
     * 
     * @param uir
     *            The UI Response to handle.
     */
    public static void handleUIResponse(UIResponse uir) {
	Utils.println("Received delegation of UI response in UIRecipeDetail"); //$NON-NLS-1$
	if (uir != null) {
	    String id = uir.getSubmissionID();
	    if (SUBMIT_GOBACK.equals(id)) {
		Utils.println("UIRecipeDetail: submit go back"); //$NON-NLS-1$
		UIRequest out = new UIRequest(SharedResources.user,
			// UIMain.getForm(), LevelRating.middle, Locale.ENGLISH,
			UIMenus.getForm(true), LevelRating.middle,
			Locale.getDefault(), PrivacyLevel.insensible);
		SharedResources.uIProvider.sendUIRequest(out);
		return;
	    }
//	    if (id.startsWith(SUBMIT_ADDTOFAV)) {
//		Utils.println("UIRecipeDetail: submit fav"); //$NON-NLS-1$
//		UIRequest out;
//		String[] values = uir.getSubmissionID().split("@"); //$NON-NLS-1$
//		int idItem = Integer.parseInt(values[1]);
//		Business business = new Business();
//		try {
//		    business.addRecipeToFavoritesWebServer(idItem);
//		    out = new UIRequest(SharedResources.user,
//			    UIRecipeDetail.getForm(idItem), LevelRating.middle,
//			    Locale.getDefault(), PrivacyLevel.insensible);
//		} catch (OASIS_ServiceUnavailable e) {
//		    e.printStackTrace();
//		    out = new UIRequest(
//			    SharedResources.user,
//			    Form.newMessage(
//				    Messages.getString("UIRecipeDetail.13"), //$NON-NLS-1$
//				    Messages.getString("UIRecipeDetail.14")), //$NON-NLS-1$
//			    LevelRating.middle, Locale.getDefault(),
//			    PrivacyLevel.insensible);
//		}
//		SharedResources.uIProvider.sendUIRequest(out);
//		return;
//	    }
	    if (id.startsWith(SUBMIT_ADDDISLIKE)) {
		Utils.println("UIRecipeDetail: submit dislike"); //$NON-NLS-1$
		UIRequest out;
		String[] values = uir.getSubmissionID().split("@"); //$NON-NLS-1$
		int idItem = Integer.parseInt(values[1]);
		String nameItem = values[2];
		int idRecipe = Integer.parseInt(values[3]);
		// TODO Check @food@
		ProfileConnector profConnect = ProfileConnector.getInstance();
		profConnect.downloadProfileFromServer();
		profConnect.addFood_Dislikes(idItem + "@food@" + nameItem);
		out = new UIRequest(SharedResources.user,
			UIRecipeDetail.getForm(idRecipe), LevelRating.middle,
			Locale.getDefault(), PrivacyLevel.insensible);
		SharedResources.uIProvider.sendUIRequest(out);
	    }
	}
	Utils.println("Finished delegation of UI response in UIRecipeDetail"); //$NON-NLS-1$
    }

    private static Recipe getRecipe(int idCurrentRecipe) {
	Utils.println("UIRecipeDetail calls getRecipe with id: " + idCurrentRecipe); //$NON-NLS-1$
	try {
	    Business business = new Business();
	    na.miniDao.Recipe recipe;
	    // int recipeID = 65;
	    recipe = business.getRecipe(idCurrentRecipe);
	    return recipe;
	} catch (OASIS_ServiceUnavailable e) {
	    e.printStackTrace();
	}
	return null;
    }

}
