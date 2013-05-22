package org.universAAL.AALapplication.nutritional.dialogs;

import java.util.Arrays;
import java.util.Locale;

import na.miniDao.DayMenu;
import na.miniDao.Dish;
import na.miniDao.Meal;
import na.services.nutritionalMenus.business.NewBusiness;
import na.utils.DishesComparator;
import na.utils.OASIS_ServiceUnavailable;
import na.ws.NutriSecurityException;

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

/**
 * The UI class that builds the Menus Form and handles its associated response
 * submits.
 * 
 * @author alfiva
 * 
 */
public class UIMenus {

    public static final String PREFIX = InterfaceProvider.MY_UI_NAMESPACE
	    + "UIMenus"; //$NON-NLS-1$

    static final String SUBMIT_GOBACK = PREFIX + "back"; //$NON-NLS-1$
    static final String SUBMIT_TODAY = PREFIX + "today"; //$NON-NLS-1$
    static final String SUBMIT_TOMORROW = PREFIX + "tomorrow"; //$NON-NLS-1$
    static final String SUBMIT_WEEK = PREFIX + "week"; //$NON-NLS-1$
    static final String SUBMIT_TIPS = PREFIX + "tips"; //$NON-NLS-1$
    static final String SUBMIT_SEE_DETAILS = PREFIX + "seeDetails@"; //$NON-NLS-1$
    //    static final String SUBMIT_CHANGEMEAL = PREFIX + "changeMeal"; //$NON-NLS-1$
    //    static final String SUBMIT_ACTIONS = PREFIX + "actions"; //$NON-NLS-1$
    //    static final String USER_INPUT_REFX = PREFIX + "selectedRecipe"; //$NON-NLS-1$
    static final String USER_INPUT_REF1 = PREFIX + "ref1"; //$NON-NLS-1$
    static final PropertyPath PROP_PATH_REF1 = new PropertyPath(null, false,
	    new String[] { USER_INPUT_REF1 });
    // static final PropertyPath PROP_PATH_SELECTED_RECIPE = new PropertyPath(
    // null, false, new String[] { USER_INPUT_REFX });
    // static final PropertyPath PROP_PATH_SELECTED_LAMP = new
    // PropertyPath(null,
    // false, new String[] { USER_INPUT_REF1 });
    static final String SUBMIT_SHOPPING = PREFIX + "shopping"; //$NON-NLS-1$
    static final String SUBMIT_PROFILE = PREFIX + "profile"; //$NON-NLS-1$
    static final String SUBMIT_HELP = PREFIX + "help"; //$NON-NLS-1$
    static final String SUBMIT_EXIT = PREFIX + "exit"; //$NON-NLS-1$

    /**
     * Compose the form for this dialog.
     * 
     * @param today
     *            Set to true to show todays menu or false to show tomorrow.
     * @return the form.
     */
    public static Form getForm(boolean today) {
	Utils.println("UI Menus form"); //$NON-NLS-1$
	Form f = Form
		.newDialog(
			Messages.getString("UIMenus.0") //$NON-NLS-1$
				+ (today ? Messages.getString("UIMenus.1") : Messages.getString("UIMenus.2")), new Resource()); //$NON-NLS-1$ //$NON-NLS-2$
	f.setProperty("http://ontology.itaca.es/ClassicGUI.owl#layout",
		"vertical,left");
	// Group groupActions = new Group(f.getIOControls(), new
	// Label("Actions",
	// null), PROP_PATH_REF1, null, null);

	// Select1 select = new Select1(groupActions, new Label("Recipes",
	// null),
	// PROP_PATH_SELECTED_RECIPE, null, null);
	// new Submit(groupActions, new Label("See Recipe", null),
	// SUBMIT_ACTIONS);

	DayMenu dayMenu = getMenu(today);
	if (dayMenu != null) {
	    int[] meals_indexes = { dayMenu.getBreakfastIndex(),
		    dayMenu.getLunchIndex(), dayMenu.getDinnerIndex(),
		    dayMenu.getMidmorningSnackIndex(),
		    dayMenu.getAfternoonSnackIndex(),
		    dayMenu.getAfterdinnerSnackIndex() };
	    // for each meal, add a group
	    int num_meals = dayMenu.getMeals().length;
	    for (int index_meals = 0; index_meals < num_meals; index_meals++) {
		int meal_index = meals_indexes[index_meals];
		Meal meal = dayMenu.getMeals(meal_index);
		if (meal == null) {
		    Utils.println("UIMenus: meal is null, index: " + meal_index); //$NON-NLS-1$
		} else {
		    String category = meal.getCategory();
		    Group groupMeal = new Group(f.getIOControls(), new Label(
			    category, null), PROP_PATH_REF1, null, null);
		    int num_dishes = meal.getDishes().length;

		    // Sort dishes
		    Dish[] sortedDishes = meal.getDishes();
		    Arrays.sort(sortedDishes, new DishesComparator());
		    for (int index_dishes = 0; index_dishes < num_dishes; index_dishes++) {
			Group groupDish = new Group(groupMeal, new Label(""
			/* Messages.getString("UIMenus.3") */, null),
				PROP_PATH_REF1, null, //$NON-NLS-1$
				null);
			Dish dish = sortedDishes[index_dishes];
			if (dish == null) {
			    Utils.println("UIMenus: dish is null, index: " //$NON-NLS-1$
				    + index_dishes);
			} else {
			    // select.addChoiceItem(new ChoiceItem(dish
			    // .getDescription(), null, new Integer(dish
			    // .getRecipeID())));
			    Utils.println("UIMenus: image: " + dish.getImage()); //$NON-NLS-1$
			    new SimpleOutput(groupDish,
				    new Label(""/*Messages.getString("UIMenus.5")*/, null), null, //$NON-NLS-1$
				    dish.getDescription());
			    if (dish.getImage() != null
				    && dish.getImage().length() > 0) {
				new MediaObject(groupDish, new Label(
					Messages.getString("UIMenus.4"), //$NON-NLS-1$
					null), "image", //$NON-NLS-1$
					InterfaceProvider.IMG_URL
						+ dish.getImage());
			    }

			    if (dish.getRecipeID() != -1) {
				new Submit(groupDish, new Label(
					Messages.getString("UIMenus.6"), //$NON-NLS-1$
					null), SUBMIT_SEE_DETAILS
					+ dish.getRecipeID());
				Utils.println("recipe ID ..........." //$NON-NLS-1$
					+ dish.getRecipeID());
			    }
			}

		    }
		}
	    }

	} else {
	    Utils.println("UIMenus: Couldn't get today menu"); //$NON-NLS-1$
	    new SimpleOutput(f.getIOControls(), null, null,
		    Messages.getString("UIMenus.7")); //$NON-NLS-1$
	}
	// add an exit button for quitting the dialog
	if (today) {
	    new Submit(f.getSubmits(), new Label(
		    Messages.getString("UIMenus.9"), null), SUBMIT_TOMORROW); //$NON-NLS-1$
	} else {
	    new Submit(f.getSubmits(), new Label(
		    Messages.getString("UIMenus.8"), null), SUBMIT_TODAY); //$NON-NLS-1$
	}
	new Submit(f.getSubmits(), new Label(
		Messages.getString("UIMenus.10"), null), SUBMIT_WEEK); //$NON-NLS-1$
	new Submit(f.getSubmits(), new Label(
		Messages.getString("UIMenus.11"), null), SUBMIT_TIPS); //$NON-NLS-1$
	new Submit(f.getSubmits(), new Label(
		Messages.getString("UIMain.6"), null), //$NON-NLS-1$
		SUBMIT_SHOPPING);
	new Submit(f.getSubmits(), new Label(
		Messages.getString("UIMain.7"), null), SUBMIT_PROFILE); //$NON-NLS-1$
	new Submit(f.getSubmits(), new Label(
		Messages.getString("UIMain.8"), null), SUBMIT_HELP); //$NON-NLS-1$
	new Submit(f.getSubmits(), new Label(
		Messages.getString("UIMain.9"), null), SUBMIT_EXIT); //$NON-NLS-1$
	//	new Submit(f.getSubmits(), new Label(Messages.getString("UIMenus.12"), null), SUBMIT_GOBACK); //$NON-NLS-1$

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
	Utils.println("Received delegation of UI response in UIMenus"); //$NON-NLS-1$
	if (uir != null) {
	    String id = uir.getSubmissionID();
	    if (SUBMIT_GOBACK.equals(id)) {
		Utils.println("UIMenus: submit go back"); //$NON-NLS-1$
		UIRequest out = new UIRequest(SharedResources.testUser,
			// UIMain.getForm(), LevelRating.middle, Locale.ENGLISH,
			UIMenus.getForm(true), LevelRating.middle,
			Locale.ENGLISH, PrivacyLevel.insensible);
		SharedResources.uIProvider.sendUIRequest(out);
		return;
	    }

	    if (SUBMIT_TODAY.equals(id)) {
		Utils.println("UIMEnus: submit today"); //$NON-NLS-1$
		UIRequest out = new UIRequest(SharedResources.testUser,
			UIMenus.getForm(true), LevelRating.middle,
			Locale.ENGLISH, PrivacyLevel.insensible);
		SharedResources.uIProvider.sendUIRequest(out);
		return;
	    }
	    if (SUBMIT_TOMORROW.equals(id)) {
		Utils.println("UImenus: submit tomorrow"); //$NON-NLS-1$
		UIRequest out = new UIRequest(SharedResources.testUser,
			UIMenus.getForm(false), LevelRating.middle,
			Locale.ENGLISH, PrivacyLevel.insensible);
		SharedResources.uIProvider.sendUIRequest(out);
		return;
	    }
	    if (SUBMIT_WEEK.equals(id)) {
		Utils.println(" TODO UIMenus: submit week"); //$NON-NLS-1$
		UIRequest out = new UIRequest(
			SharedResources.testUser,
			Form.newMessage(Messages.getString("UIMenus.13"), //$NON-NLS-1$
				Messages.getString("UIMenus.14")), //$NON-NLS-1$
			LevelRating.middle, Locale.ENGLISH,
			PrivacyLevel.insensible);
		SharedResources.uIProvider.sendUIRequest(out);
		return;
		// TODO
	    }
	    if (SUBMIT_TIPS.equals(id)) {
		Utils.println(" TODO UImenus: submit tips"); //$NON-NLS-1$
		UIRequest out = new UIRequest(
			SharedResources.testUser,
			Form.newMessage(Messages.getString("UIMenus.13"), //$NON-NLS-1$
				Messages.getString("UIMenus.14")), //$NON-NLS-1$
			LevelRating.middle, Locale.ENGLISH,
			PrivacyLevel.insensible);
		SharedResources.uIProvider.sendUIRequest(out);
		return;
		// TODO
	    }
	    if (id.startsWith(SUBMIT_SEE_DETAILS)) {
		String[] values = uir.getSubmissionID().split("@"); //$NON-NLS-1$
		int idReceta = new Integer(values[1]);
		Utils.println("UIMenus: submit details: " + idReceta); //$NON-NLS-1$
		UIRequest out = new UIRequest(SharedResources.testUser,
			UIRecipeDetail.getForm(idReceta), LevelRating.middle,
			Locale.ENGLISH, PrivacyLevel.insensible);
		SharedResources.uIProvider.sendUIRequest(out);
		return;
	    }
	    // if (id.startsWith(SUBMIT_ACTIONS)) {
	    // Integer idRecipe = (Integer) uir
	    // .getUserInput(PROP_PATH_SELECTED_RECIPE.getThePath());
	    //		Utils.println("UIMenus: submit actions: " + idRecipe); //$NON-NLS-1$
	    // UIRequest out = new UIRequest(SharedResources.testUser,
	    // UIRecipeDetail.getForm(idRecipe.intValue()),
	    // LevelRating.middle, Locale.ENGLISH,
	    // PrivacyLevel.insensible);
	    // SharedResources.uIProvider.sendUIRequest(out);
	    // return;
	    // }
	    if (SUBMIT_SHOPPING.equals(id)) {
		Utils.println("UIMain submit shopping"); //$NON-NLS-1$
		UIRequest out = new UIRequest(SharedResources.testUser,
			UIShopping.getForm(), LevelRating.middle,
			Locale.ENGLISH, PrivacyLevel.insensible);
		SharedResources.uIProvider.sendUIRequest(out);
		return;
	    }
	    if (SUBMIT_PROFILE.equals(id)) {
		Utils.println("UIMain submit profile"); //$NON-NLS-1$
		UIRequest out = new UIRequest(SharedResources.testUser,
			UIProfile.getForm(), LevelRating.middle,
			Locale.ENGLISH, PrivacyLevel.insensible);
		SharedResources.uIProvider.sendUIRequest(out);
		return;
	    }
	    if (SUBMIT_HELP.equals(id)) {
		Utils.println("UIMain submit help"); //$NON-NLS-1$
		UIRequest out = new UIRequest(SharedResources.testUser,
			UIHelp.getForm(), LevelRating.middle, Locale.ENGLISH,
			PrivacyLevel.insensible);
		SharedResources.uIProvider.sendUIRequest(out);
		return;
	    }
	    if (SUBMIT_EXIT.equals(id))
		return; // Cancel Dialog, go back to main dialog
	}
	Utils.println("Finished delegation of UI response in UIMenus"); //$NON-NLS-1$
    }

    private static DayMenu getMenu(boolean today) {
	Utils.println("UIMenus calling getMenu: " + today); //$NON-NLS-1$
	try {
	    NewBusiness business = new NewBusiness();
	    na.miniDao.DayMenu menuDescription;
	    if (today) {
		menuDescription = business.getTodayMenu();
	    } else {
		menuDescription = business.getTomorrowMenu();
	    }
	    return menuDescription;
	} catch (NutriSecurityException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} catch (OASIS_ServiceUnavailable e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	return null;
    }

}
