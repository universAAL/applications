package org.universAAL.AALapplication.nutritional.dialogs;

import java.util.Locale;

import na.miniDao.Recipe;
import na.oasisUtils.profile.ProfileConnector;
import na.services.recipes.business.Business;
import na.utils.OASIS_ServiceUnavailable;
import na.ws.UserNutritionalProfile;

import org.universAAL.AALapplication.nutritional.SharedResources;
import org.universAAL.AALapplication.nutritional.services.OntoFactory;
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
import org.universAAL.middleware.ui.rdf.SimpleOutput;
import org.universAAL.middleware.ui.rdf.Submit;
import org.universAAL.ontology.nutrition.Food;
import org.universAAL.ontology.nutrition.profile.NutritionalPreferences;
import org.universAAL.ontology.nutrition.profile.NutritionalSubProfile;




/**
 * The UI class that builds the Shopping Form and handles its associated
 * response submits.
 * 
 * @author alfiva
 * 
 */
public class UIItemList {

    public static final String PREFIX = InterfaceProvider.MY_UI_NAMESPACE
	    + "UIItemList"; //$NON-NLS-1$
    static final String USER_INPUT_REF1 = PREFIX + "ref1"; //$NON-NLS-1$
    static final String SUBMIT_REMOVE = PREFIX + "rem"; //$NON-NLS-1$
    static final String SUBMIT_GOBACK = PREFIX + "back"; //$NON-NLS-1$

    /**
     * Compose the form for this dialog.
     * 
     * @return The form.
     */
    public static Form getForm(boolean dislikeInsteadOfRecipe) {
	Utils.println("UI Item List"); //$NON-NLS-1$
	Form f = Form
		.newDialog(
			dislikeInsteadOfRecipe ? Messages
				.getString("UIItemList.0") : Messages.getString("UIItemList.1"), new Resource()); //$NON-NLS-1$ //$NON-NLS-2$
	f.setProperty(
		"http://ontology.itaca.es/ClassicGUI.owl#layout", "vertical,right");//$NON-NLS-1$ //$NON-NLS-2$

	if (dislikeInsteadOfRecipe) {
	    ProfileConnector profConnect = ProfileConnector.getInstance();
	    profConnect.downloadProfileFromServer();
	    UserNutritionalProfile prof = profConnect.getProfile();
	    NutritionalSubProfile nutrProf = OntoFactory.getNutrProfile(prof);
	    NutritionalPreferences nutrPref = nutrProf
		    .getNutritionalPreferences();
	    Food[] items = nutrPref.getFoodDislike();
	    int k = 0;
	    if (items.length == 0) {
		new SimpleOutput(f.getIOControls(), new Label("", null), null,
			"No items found");
	    }
	    for (Food i : items) {
		Group row = new Group(f.getIOControls(), new Label("", null),
			new PropertyPath(null, false,
				new String[] { USER_INPUT_REF1 + k }), null,
			null);
		new SimpleOutput(row, new Label("", null), null, i.getName());
		new Submit(row, new Label(Messages.getString("UIItemList.3"),
			null), SUBMIT_REMOVE + k + "@" + dislikeInsteadOfRecipe
			+ "@" + i.getID());
		k++;
	    }
	} else {
	    Business business = new Business();
	    Recipe[] items;
	    try {
		items = business.getMyFavoriteRecipes();
		int k = 0;
		if (items.length == 0) {
		    new SimpleOutput(f.getIOControls(), new Label("", null),
			    null, "No items found");
		}
		for (Recipe i : items) {
		    Group row = new Group(f.getIOControls(),
			    new Label("", null), new PropertyPath(null, false,
				    new String[] { USER_INPUT_REF1 + k }),
			    null, null);
		    new SimpleOutput(row, new Label("", null), null,
			    i.getCourse());
		    new Submit(row, new Label(
			    Messages.getString("UIItemList.3"), null),
			    SUBMIT_REMOVE + k + "@" + dislikeInsteadOfRecipe
				    + "@" + i.getRecipeID());
		    k++;
		}
	    } catch (OASIS_ServiceUnavailable e) {
		new SimpleOutput(f.getIOControls(), new Label("", null), null,
			"No items found");
		e.printStackTrace();
	    }

	}

	new Submit(f.getSubmits(), new Label(
		Messages.getString("UIItemList.2"), null), SUBMIT_GOBACK); //$NON-NLS-1$

	return f;
    }

    /**
     * The main InterfaceProvider delegates calls to the handleUIResponse of the
     * UICaller to this one if the prefix of the pressed submit ID matches this
     * class´ one (it´s one of its submits).
     * 
     * @param uir
     *            The UI Response to handle.
     */
    public static void handleUIResponse(UIResponse uir) {
	Utils.println("Received delegation of UI response in UIItemList"); //$NON-NLS-1$
	if (uir != null) {
	    String id = uir.getSubmissionID();
	    if (SUBMIT_GOBACK.equals(id)) {
		Utils.println("UIRecipeDetail: submit go back"); //$NON-NLS-1$
		UIRequest out = new UIRequest(SharedResources.testUser,
			UIProfile.getForm(), LevelRating.middle,
			Locale.ENGLISH, PrivacyLevel.insensible);
		SharedResources.uIProvider.sendUIRequest(out);
		return;
	    }
	    if (id.startsWith(SUBMIT_REMOVE)) {
		Utils.println("UIRecipeDetail: submit remove"); //$NON-NLS-1$
		String[] values = uir.getSubmissionID().split("@"); //$NON-NLS-1$
		int idItem = Integer.parseInt(values[2]);
		boolean flag = Boolean.parseBoolean(values[1]);
		if (flag) {
		    removeDislike(idItem);
		} else {
		    removeRecipe(idItem);
		}
		UIRequest out = new UIRequest(SharedResources.testUser,
			UIItemList.getForm(flag), LevelRating.middle,
			Locale.ENGLISH, PrivacyLevel.insensible);
		SharedResources.uIProvider.sendUIRequest(out);
		return;
	    }
	}
	Utils.println("Finished delegation of UI response in UIItemList"); //$NON-NLS-1$
    }

    private static boolean removeRecipe(int idItem) {
	System.out.println(">>>>>>>>>>>>>>>>>>>>>>>REMOVE RECIPE: " + idItem);
	Business business = new Business();
	try {
	    business.removeRecipeFromFavoritesWebServer(idItem);
	} catch (OASIS_ServiceUnavailable e) {
	    e.printStackTrace();
	    return false;
	}
	return true;
    }

    private static void removeDislike(int idItem) {
	System.out.println(">>>>>>>>>>>>>>>>>>>>>>>REMOVE FOOD: " + idItem);
    }

}
