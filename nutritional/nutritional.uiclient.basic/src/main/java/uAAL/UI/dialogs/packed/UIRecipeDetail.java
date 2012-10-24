package uAAL.UI.dialogs.packed;

import java.util.Locale;

import na.miniDao.Recipe;
import na.miniDao.RecipeIngredient;
import na.services.recipes.business.Business;
import na.utils.OASIS_ServiceUnavailable;
import nna.SharedResources;
import nna.utils.Utils;

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
    static final String SUBMIT_WEEK = PREFIX + "week"; //$NON-NLS-1$
    static final String SUBMIT_TIPS = PREFIX + "tips"; //$NON-NLS-1$
    static final String SUBMIT_SEE_DETAILS = PREFIX + "seeDetails"; //$NON-NLS-1$

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
	Form f = Form.newDialog(Messages.getString("UIRecipeDetail.1"), new Resource()); //$NON-NLS-1$
	f.setProperty("http://ontology.itaca.es/ClassicGUI.owl#layout", "constant,vertical,left");
	Recipe recipe = getRecipe(idRecipe);
	if (recipe != null) {
	    String recipeName = recipe.getCourse();
	    String dishCategory = recipe.getDishCategory();
	    String recipeProcedure = recipe.getProcedure();
	    String recipePicture = recipe.getPicture();
	    // ingredients?

//	    new InputField(f.getIOControls(), new Label(Messages.getString("UIRecipeDetail.2"), null), //$NON-NLS-1$
//		    PROP_PATH_RECIPE_NAME, null, recipeName);
//	    new InputField(f.getIOControls(), new Label(Messages.getString("UIRecipeDetail.3"), null), //$NON-NLS-1$
//		    PROP_PATH_RECIPE_CATEGORY, null, dishCategory);
	    Group groupDesc = new Group(f.getIOControls(), new Label(Messages.getString("UIRecipeDetail.16"), null), //$NON-NLS-1$
			PROP_PATH_RECIPE_NAME, null, null);
	    if(recipeName!=null && !recipeName.isEmpty()){
		new SimpleOutput(groupDesc, new Label(Messages.getString("UIRecipeDetail.2"), null), //$NON-NLS-1$
			PROP_PATH_RECIPE_NAME, recipeName);
	    }
	    if(dishCategory!=null && !dishCategory.isEmpty()){
		new SimpleOutput(groupDesc, new Label(Messages.getString("UIRecipeDetail.3"), null), //$NON-NLS-1$
			PROP_PATH_RECIPE_CATEGORY, dishCategory);
	    }
	    if (recipePicture != null && !recipePicture.isEmpty()) {
		new MediaObject(groupDesc, new Label(Messages.getString("UIRecipeDetail.7"), null), //$NON-NLS-1$
			"image", InterfaceProvider.IMG_URL+recipePicture); // //$NON-NLS-1$
	    }

	    Group groupIngredients = new Group(f.getIOControls(), new Label(
		    Messages.getString("UIRecipeDetail.4"), null), PROP_PATH_RECIPE_CATEGORY, null, null); //$NON-NLS-1$
	    RecipeIngredient[] ingredients = recipe.getRecipeIngredients();
	    if (ingredients != null && ingredients.length > 0) {
		for (int indexIngredient = 0; indexIngredient < ingredients.length; indexIngredient++) {
		    RecipeIngredient ingredient = ingredients[indexIngredient];
		    if (ingredient != null) {
			new SimpleOutput(groupIngredients, new Label("·",null), null,
				ingredient.getDescription());
		    } else {
			Utils.println("UIRecipeDetail: Ingredient with index: " //$NON-NLS-1$
				+ indexIngredient + " is null"); //$NON-NLS-1$
		    }

		}
	    }

	    Group groupProcedure = new Group(f.getIOControls(), new Label(
		    Messages.getString("UIRecipeDetail.5"), null), PROP_PATH_RECIPE_CATEGORY, null, null); //$NON-NLS-1$
//	    new TextArea(groupProcedure, new Label(Messages.getString("UIRecipeDetail.6"), null), //$NON-NLS-1$
//		    PROP_PATH_RECIPE_PROCEDURE, null, recipeProcedure);
	    new SimpleOutput(groupProcedure, new Label("", null), //$NON-NLS-1$
		    PROP_PATH_RECIPE_PROCEDURE, recipeProcedure.isEmpty()?Messages.getString("UIRecipeDetail.15"):recipeProcedure);

	    // acciones
//	    Group groupActions = new Group(f.getIOControls(), new Label(
//		    Messages.getString("UIRecipeDetail.8"), null), PROP_PATH_RECIPE_CATEGORY, null, null); //$NON-NLS-1$
	    new Submit(f.getSubmits()/*groupActions*/, new Label(Messages.getString("UIRecipeDetail.9"), null), //$NON-NLS-1$
		    SUBMIT_SEE_DETAILS);
	    new Submit(f.getSubmits()/*groupActions*/, new Label(Messages.getString("UIRecipeDetail.10"), null), //$NON-NLS-1$
		    SUBMIT_SEE_DETAILS);

	} else {
	    Utils.println("UIRecipeDetail: Couldn't get recipe"); //$NON-NLS-1$
	    new SimpleOutput(f.getIOControls(), null, null,
		    Messages.getString("UIRecipeDetail.11")); //$NON-NLS-1$
	}

	// add an exit button for quitting the dialog
	new Submit(f.getSubmits(), new Label(Messages.getString("UIRecipeDetail.12"), null), SUBMIT_GOBACK); //$NON-NLS-1$

	return f;
    }

    /**
     * The main InterfaceProvider delegates calls to the handleUIResponse of the
     * UICaller to this one if the prefix of the pressed submit ID matches this class´
     * one (it´s one of its submits).
     * 
     * @param uir
     *            The UI Response to handle.
     */
    public static void handleUIResponse(UIResponse uir) {
	Utils.println("Received delegation of UI response in UIRecipeDetail"); //$NON-NLS-1$
	if (uir != null) {	
	    String id=uir.getSubmissionID();
	    if (SUBMIT_GOBACK.equals(id)) {
		Utils.println("UIRecipeDetail: submit go back"); //$NON-NLS-1$
		UIRequest out = new UIRequest(SharedResources.testUser,
//			UIMain.getForm(), LevelRating.middle, Locale.ENGLISH,
			UIMenus.getForm(true), LevelRating.middle, Locale.ENGLISH,
			PrivacyLevel.insensible);
		SharedResources.uIProvider.sendUIRequest(out);
		return;
	    }
/*
	    if (SUBMIT_TODAY.equals(id)) {
		Utils.println("TODO UIRecipeDetail: submit today"); //$NON-NLS-1$
		UIRequest out = new UIRequest(SharedResources.testUser,
			Form.newMessage(Messages.getString("UIRecipeDetail.13"), //$NON-NLS-1$
				Messages.getString("UIRecipeDetail.14")), //$NON-NLS-1$
			LevelRating.middle, Locale.ENGLISH,
			PrivacyLevel.insensible);
		SharedResources.uIProvider.sendUIRequest(out);
		return;
		// TODO
	    }
	    if (SUBMIT_WEEK.equals(id)) {
		Utils.println("TODO UIRecipeDetail:  submit week"); //$NON-NLS-1$
		UIRequest out = new UIRequest(SharedResources.testUser,
			Form.newMessage(Messages.getString("UIRecipeDetail.13"), //$NON-NLS-1$
				Messages.getString("UIRecipeDetail.14")), //$NON-NLS-1$
			LevelRating.middle, Locale.ENGLISH,
			PrivacyLevel.insensible);
		SharedResources.uIProvider.sendUIRequest(out);
		return;
		// TODO
	    }
	    if (SUBMIT_TIPS.equals(id)) {
		Utils.println("TODO UIRecipeDetail: submit tips"); //$NON-NLS-1$
		UIRequest out = new UIRequest(SharedResources.testUser,
			Form.newMessage(Messages.getString("UIRecipeDetail.13"), //$NON-NLS-1$
				Messages.getString("UIRecipeDetail.14")), //$NON-NLS-1$
			LevelRating.middle, Locale.ENGLISH,
			PrivacyLevel.insensible);
		SharedResources.uIProvider.sendUIRequest(out);
		return;
		// TODO
	    }*/
	    if (SUBMIT_SEE_DETAILS.equals(id)) {
		Utils.println("TODO UIRecipeDetail: submit tips"); //$NON-NLS-1$
		UIRequest out = new UIRequest(SharedResources.testUser,
			Form.newMessage(Messages.getString("UIRecipeDetail.13"), //$NON-NLS-1$
				Messages.getString("UIRecipeDetail.14")), //$NON-NLS-1$
			LevelRating.middle, Locale.ENGLISH,
			PrivacyLevel.insensible);
		SharedResources.uIProvider.sendUIRequest(out);
		return;
		// TODO 2 buttons!
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
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	return null;
    }

}
