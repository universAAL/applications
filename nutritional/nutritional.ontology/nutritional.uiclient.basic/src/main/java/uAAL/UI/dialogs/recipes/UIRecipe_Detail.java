package uAAL.UI.dialogs.recipes;

import java.util.Locale;

import na.miniDao.DayMenu;
import na.miniDao.Dish;
import na.miniDao.Meal;
import na.miniDao.Recipe;
import na.miniDao.RecipeIngredient;
import na.services.recipes.business.*;
import na.utils.OASIS_ServiceUnavailable;
import nna.SharedResources;
import nna.utils.Utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.owl.supply.LevelRating;
import org.universAAL.middleware.rdf.PropertyPath;
import org.universAAL.middleware.rdf.Resource;
import org.universAAL.middleware.ui.UIRequest;
import org.universAAL.middleware.ui.UIResponse;
import org.universAAL.middleware.ui.owl.PrivacyLevel;
import org.universAAL.middleware.ui.rdf.Form;
import org.universAAL.middleware.ui.rdf.Group;
import org.universAAL.middleware.ui.rdf.InputField;
import org.universAAL.middleware.ui.rdf.Label;
import org.universAAL.middleware.ui.rdf.MediaObject;
import org.universAAL.middleware.ui.rdf.SimpleOutput;
import org.universAAL.middleware.ui.rdf.Submit;
import org.universAAL.middleware.ui.rdf.TextArea;

import uAAL.UI.dialogs.CustomUICaller;

/**
 * @author hecgamar
 * 
 */
public class UIRecipe_Detail extends CustomUICaller {

	private static Log log = LogFactory.getLog(UIRecipe_Detail.class);
	private final static String window = "UIRecipeDetail#";
	static final String MY_UI_NAMESPACE = SharedResources.CLIENT_NUTRITIONAL_UI_NAMESPACE
			+ window;

	static final String SUBMIT_GOBACK = MY_UI_NAMESPACE + "back";
	static final String SUBMIT_TODAY = MY_UI_NAMESPACE + "today";
	static final String SUBMIT_TOMORROW = MY_UI_NAMESPACE + "tomorrow";
	static final String SUBMIT_WEEK = MY_UI_NAMESPACE + "week";
	static final String SUBMIT_TIPS = MY_UI_NAMESPACE + "tips";
	static final String SUBMIT_SEE_DETAILS = MY_UI_NAMESPACE + "seeDetails";

	static final String USER_INPUT_SELECTED_LAMP = MY_UI_NAMESPACE
			+ "selectedLamp";
	static final String USER_INPUT_SELECTED_LAMP2 = MY_UI_NAMESPACE
			+ "selectedLamp2";
	static final String USER_INPUT_SELECTED_LAMP3 = MY_UI_NAMESPACE
			+ "selectedLamp3";

	static final PropertyPath PROP_PATH_RECIPE_NAME = new PropertyPath(null,
			false, new String[] { USER_INPUT_SELECTED_LAMP, SUBMIT_GOBACK });
	
	static final PropertyPath PROP_PATH_RECIPE_CATEGORY = new PropertyPath(null,
			false, new String[] { USER_INPUT_SELECTED_LAMP2, SUBMIT_GOBACK });
	static final PropertyPath PROP_PATH_RECIPE_PROCEDURE = new PropertyPath(null,
			false, new String[] { USER_INPUT_SELECTED_LAMP3, SUBMIT_GOBACK });
	
//	static final PropertyPath PROP_PATH_RECIPE_IMAGE = new PropertyPath(null,
//			false, new String[] { USER_INPUT_SELECTED_LAMP2, SUBMIT_GOBACK });

	private Form mainDialog = null;
	private CustomUICaller parentUICaller = null;
	private ModuleContext myContext;
	private int idCurrentRecipe = -1;

	public UIRecipe_Detail(ModuleContext context, int recipeID) {
		super(context);
		this.myContext = context;
		this.idCurrentRecipe = recipeID;
	}

	public void handleUIResponse(UIResponse uir) {
		Utils.println(window + " Recibo ID: " + uir.getSubmissionID());
		if (uir != null) {
			if (SUBMIT_GOBACK.equals(uir.getSubmissionID())) {
				Utils.println(window + "  go back to previous screen");
				this.parentUICaller.callMainForm();
//				this.backToMainMenusDialog(this.getContext());
				return; // Cancel Dialog, go back to main dialog
			}

			if (SUBMIT_TODAY.equals(uir.getSubmissionID())) {
				Utils.println(window + " Mostrar ventana TODAY!!");
				// this.startMenuMainDialog();
				return;
			}
			if (SUBMIT_WEEK.equals(uir.getSubmissionID())) {
				Utils.println(window + " Mostrar ventana WEEK!!");
				// this.startMenuMainDialog();
				return;
			}
			if (SUBMIT_TIPS.equals(uir.getSubmissionID())) {
				Utils.println(window + " Mostrar ventana TIPS!!");
				// this.startMenuMainDialog();
				return;
			}
		}
		Utils.println(window + " Continues");
	}

	
	public void startMainDialog(CustomUICaller parentForm) {
		Utils.println(window + " startMainDialog");
		this.parentUICaller = parentForm;
		if (mainDialog == null)
			mainDialog = initMainDialog();
		UIRequest out = new UIRequest(SharedResources.testUser, mainDialog,
				LevelRating.middle, Locale.ENGLISH, PrivacyLevel.insensible);
		sendUIRequest(out);
	}

	private Form initMainDialog() {
		Utils.println(window + " createMenusMainDialog");
		Form f = Form.newDialog("Recipe detail", new Resource());

		Recipe recipe = this.getRecipe();
		if (recipe != null) {
			String recipeName = recipe.getCourse();
			String dishCategory = recipe.getDishCategory();
			String recipeProcedure = recipe.getProcedure();
			//ingredients?
			
			InputField inputName = new InputField(f.getIOControls(), new Label( "Recipe name:", null), PROP_PATH_RECIPE_NAME, null, recipeName);
			InputField inputCategory = new InputField(f.getIOControls(), new Label( "Category:", null), PROP_PATH_RECIPE_CATEGORY, null, dishCategory);
			
			Group groupIngredients = new Group(f.getIOControls(), new Label(
					"Ingredients", null), PROP_PATH_RECIPE_CATEGORY, null, null);
			RecipeIngredient[] ingredients = recipe.getRecipeIngredients();
			if (ingredients!=null && ingredients.length>0) {
				for (int indexIngredient=0; indexIngredient<ingredients.length; indexIngredient++) {
					RecipeIngredient ingredient =  ingredients[indexIngredient];
					if (ingredient!=null) {
						SimpleOutput so = new SimpleOutput(groupIngredients, null, null, ingredient.getDescription());
					} else {
						log.error("Ingredient with index: "+indexIngredient+" is null");
					}
					
				}
			}
			
			Group groupProcedure = new Group(f.getIOControls(), new Label(
					"Procedure", null), PROP_PATH_RECIPE_CATEGORY, null, null);
			TextArea ta = new TextArea(groupProcedure, new Label( "Procedure:", null),
					 PROP_PATH_RECIPE_PROCEDURE, null, recipeProcedure);

			if (recipe.getPicture()!= null && recipe.getPicture().length()>0) {
				new MediaObject(f.getIOControls(), new Label("Image", null), "image/jpg", recipe.getPicture()); //
			}
			
//			MediaObject img_picture = new
//					 MediaObject(f.getIOControls(), new Label(
//					 "Picture", null), "image", "img/nutritional/en/lunch.jpg");
//					 img_picture.setPreferredResolution(125, 75);

					 //acciones
			 Group groupActions = new Group(f.getIOControls(), new Label(
						"Actions", null), PROP_PATH_RECIPE_CATEGORY, null, null);
			 Submit but_addToFavourites = new Submit(groupActions,
						new Label("Add to favourites", null),
						SUBMIT_SEE_DETAILS);
			 Submit but_shareRecipe = new Submit(groupActions,
						new Label("Share recipe", null),
						SUBMIT_SEE_DETAILS);
			
			/*
			// for each meal, add a group
			int num_meals = recipe.getMeals().length;
			for (int index_meals = 0; index_meals < num_meals; index_meals++) {
				Meal meal = recipe.getMeals(index_meals);
				if (meal == null) {
					log.error("meal is null, index: " + index_meals);
				} else {
					String category = meal.getCategory();
					Group groupMeal = new Group(f.getIOControls(), new Label(
							category, null), PROP_PATH_SCALE_VALUE, null, null);
					int num_dishes = meal.getDishes().length;
					for (int index_dishes = 0; index_dishes < num_dishes; index_dishes++) {
						Group groupDish = new Group(groupMeal, new Label(
								"Dish", null), PROP_PATH_SCALE_VALUE, null,
								null);
						Dish dish = meal.getDishes(index_dishes);
						if (dish == null) {
							log.error("dish is null, index: " + index_dishes);
						} else {
							Utils.println(window + "image: " + dish.getImage());
							SimpleOutput label_description = new SimpleOutput(
									groupDish, new Label("Dish:", null), null,
									dish.getDescription());
							if (dish.getRecipeID() != -1) {
								Submit but_seeDetails = new Submit(groupDish,
										new Label("See details", null),
										SUBMIT_SEE_DETAILS+dish.getRecipeID());
							}
							Submit but_changeMeal = new Submit(groupDish,
									new Label("Change meal", null),
									SUBMIT_SEE_DETAILS+dish.getRecipeID());
							// MediaObject img_picture = new
							// MediaObject(groupDish, new Label(
							// "See details", null), Prop_C, "image/jpeg");
						}

					}
				}
			}
			*/

			/*
			 * InputField in = new InputField(g, new Label( "Menus del día:",
			 * null), PROP_PATH_SCALE_VALUE, null, null);
			 * in.setAlertString("Expected: a number between 0 and 100!");
			 * 
			 * TextArea ta = new TextArea(g, new Label( "Introducción:", null),
			 * PROP_PATH_SCALE_VALUE, null, null);
			 * ta.setAlertString("Aqui falta algo");
			 * ta.setHelpString("Ayuda para el text");
			 */
		} else {
			log.error("Couldn't get recipe");
			SimpleOutput so = new SimpleOutput(f.getIOControls(), null, null,
					"Couldn't get recipe");
		}

		// add an exit button for quitting the dialog
//		new Submit(f.getSubmits(), new Label("Today", null), SUBMIT_TODAY);
//		new Submit(f.getSubmits(), new Label("Tomorrow", null), SUBMIT_TOMORROW);
//		new Submit(f.getSubmits(), new Label("Week", null), SUBMIT_WEEK);
//		new Submit(f.getSubmits(), new Label("Tips", null), SUBMIT_TIPS);
		new Submit(f.getSubmits(), new Label("Go back!", null), SUBMIT_GOBACK);

		return f;
	}

	public void communicationChannelBroken() {
		System.out.println(window + " communicationChannelBroken");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.universAAL.middleware.ui.UICaller#dialogAborted(java.lang.String)
	 */
	@Override
	public void dialogAborted(String dialogID) {
		Utils.println(window + " dialogAborted: " + dialogID);
	}

	@Override
	public void callMainForm() {
		// TODO
		// Utils.println(window+ " showing my MainForm!");
		// this.startMainDialog();
	}

	private Recipe getRecipe() {
		Utils.println(window + " getRecipe with id: "+this.idCurrentRecipe);
		try {
			Business business = new Business();
			na.miniDao.Recipe recipe;
//			int recipeID = 65;
			recipe = business.getRecipe(this.idCurrentRecipe);
			return recipe;
		} catch (OASIS_ServiceUnavailable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public Form getMainForm() {
		return this.mainDialog;
	}
}
