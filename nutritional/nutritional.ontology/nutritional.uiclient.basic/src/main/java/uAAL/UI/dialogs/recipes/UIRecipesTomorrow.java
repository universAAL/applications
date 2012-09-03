package uAAL.UI.dialogs.recipes;

import java.util.Arrays;
import java.util.Locale;

import na.miniDao.DayMenu;
import na.miniDao.Dish;
import na.miniDao.Meal;
import na.services.nutritionalMenus.business.NewBusiness;
import na.services.nutritionalMenus.ui.today.DishesComparator;
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
import org.universAAL.middleware.ui.rdf.ChoiceItem;
import org.universAAL.middleware.ui.rdf.Form;
import org.universAAL.middleware.ui.rdf.Group;
import org.universAAL.middleware.ui.rdf.Label;
import org.universAAL.middleware.ui.rdf.MediaObject;
import org.universAAL.middleware.ui.rdf.Select1;
import org.universAAL.middleware.ui.rdf.SimpleOutput;
import org.universAAL.middleware.ui.rdf.Submit;
import uAAL.UI.dialogs.CustomUICaller;
import uAAL.UI.dialogs.UIMainProvider;
import uAAL.UI.dialogs.recipes.UIRecipe_Detail;

/**
 * @author hecgamar
 * 
 */
public class UIRecipesTomorrow extends CustomUICaller {

	private static Log log = LogFactory.getLog(UIRecipesTomorrow.class);
	private final static String window = "UIRecipesTomorrow#";
	static final String MY_UI_NAMESPACE = SharedResources.CLIENT_NUTRITIONAL_UI_NAMESPACE
			+ window;

	static final String SUBMIT_GOBACK = MY_UI_NAMESPACE + "back";
	static final String SUBMIT_TODAY = MY_UI_NAMESPACE + "today";
	static final String SUBMIT_TOMORROW = MY_UI_NAMESPACE + "tomorrow";
	static final String SUBMIT_WEEK = MY_UI_NAMESPACE + "week";
	static final String SUBMIT_TIPS = MY_UI_NAMESPACE + "tips";
	static final String SUBMIT_SEE_DETAILS = MY_UI_NAMESPACE + "seeDetails@";
	static final String SUBMIT_ChangeMeal = MY_UI_NAMESPACE + "changeMeal";

	
	static final String USER_INPUT_SELECTED_RECIPE = MY_UI_NAMESPACE
			+ "selectedRecipe";

	static final String USER_INPUT_SELECTED_LAMP = MY_UI_NAMESPACE
			+ "selectedLamp";

	static final PropertyPath PROP_PATH_SCALE_VALUE = new PropertyPath(null,
			false, new String[] { USER_INPUT_SELECTED_LAMP, SUBMIT_GOBACK });
	
	static final PropertyPath PROP_PATH_SELECTED_RECIPE = new PropertyPath(null,
			false, new String[] { USER_INPUT_SELECTED_RECIPE });
	
	static final PropertyPath PROP_PATH_SELECTED_LAMP = new PropertyPath(null,
			false, new String[] { USER_INPUT_SELECTED_LAMP });

	private Form mainDialog = null;
	private CustomUICaller parentUICaller = null;
	private ModuleContext myContext;

	public UIRecipesTomorrow(ModuleContext context) {
		super(context);
		this.myContext = context;
	}


	private Form initMainDialog() {
		Utils.println(window + " createTomorrowMenusMainDialog");
		Form f = Form.newDialog("Nutritional Menus, tomorrow", new Resource());

		/*
		Group groupActions = new Group(f.getIOControls(), new Label(
				"Actions", null), PROP_PATH_SCALE_VALUE, null, null);

		Select1 select = new Select1(groupActions,
				new Label("Recipes", null), PROP_PATH_SELECTED_RECIPE, null, null);
		Submit but_seeDetails = new Submit(groupActions,
				new Label("See Recipe", null),
				SUBMIT_SEE_DETAILS);
				*/
		
		DayMenu dayMenu = this.getMenu();
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
					log.error("meal is null, index: " + meal_index);
				} else {
					String category = meal.getCategory();
					Group groupMeal = new Group(f.getIOControls(), new Label(
							category, null), PROP_PATH_SCALE_VALUE, null, null);
					int num_dishes = meal.getDishes().length;
					
					// Sort dishes
					Dish[] sortedDishes = meal.getDishes();
					Arrays.sort(sortedDishes, new DishesComparator());
					for (int index_dishes = 0; index_dishes < num_dishes; index_dishes++) {
						Group groupDish = new Group(groupMeal, new Label(
								"Dish", null), PROP_PATH_SCALE_VALUE, null,
								null);
						Dish dish = sortedDishes[index_dishes];
						if (dish == null) {
							log.error("dish is null, index: " + index_dishes);
						} else {
//							select.addChoiceItem(new ChoiceItem(dish.getDescription(), null, new Integer(dish.getRecipeID())));
							
							if (dish.getImage()!= null && dish.getImage().length()>0) {
								new MediaObject(groupDish, new Label("Image", null), "image/jpg", dish.getImage()); //
							}
							
							Utils.println(window + "image: " + dish.getImage());
							SimpleOutput label_description = new SimpleOutput(
									groupDish, new Label("Dish:", null), null,
									dish.getDescription());
							if (dish.getRecipeID() != -1) {
								new Submit(groupDish,
										new Label("See Recipe", null),
										SUBMIT_SEE_DETAILS+dish.getRecipeID());
							}
//							Submit but_changeMeal = new Submit(groupDish,
//									new Label("Change meal", null),
//									SUBMIT_ChangeMeal);
							// MediaObject img_picture = new
							// MediaObject(groupDish, new Label(
							// "See details", null), Prop_C, "image/jpeg");
						}

					}
				}
			}

		} else {
			log.error("Couldn't get today menu");
			SimpleOutput so = new SimpleOutput(f.getIOControls(), null, null,
					"Couldn't get today menu");
		}
		// add an exit button for quitting the dialog
		new Submit(f.getSubmits(), new Label("Today", null), SUBMIT_TODAY);
		new Submit(f.getSubmits(), new Label("Tomorrow", null), SUBMIT_TOMORROW);
		new Submit(f.getSubmits(), new Label("Week", null), SUBMIT_WEEK);
		new Submit(f.getSubmits(), new Label("Tips", null), SUBMIT_TIPS);
		new Submit(f.getSubmits(), new Label("Go back!", null), SUBMIT_GOBACK);

		return f;
	}
	
	public void handleUIResponse(UIResponse uir) {
		Utils.println(window + " Recibo ID: " + uir.getSubmissionID());
		if (uir != null) {
			if (SUBMIT_GOBACK.equals(uir.getSubmissionID())) {
				Utils.println(window + "  go back to previous screen");
//				this.parentUICaller.callMainForm();
//				this.backToMainMenusDialog();
				this.backToMainMenusDialog(this.getContext());
				return; // Cancel Dialog, go back to main dialog
			}

			if (SUBMIT_TODAY.equals(uir.getSubmissionID())) {
				Utils.println(window + " Mostrar ventana TODAY!!");
				this.showTodayMainDialog(this);
				return;
			}
			if (SUBMIT_TOMORROW.equals(uir.getSubmissionID())) {
				Utils.println(window + " Mostrar ventana TOMORROW!!");
				this.showTomorrwoMainDialog(this);
				return;
			}
			if (SUBMIT_WEEK.equals(uir.getSubmissionID())) {
				Utils.println(window + " Mostrar ventana WEEK!!");
				// this.showMenuMainDialog();
				return;
			}
			if (SUBMIT_TIPS.equals(uir.getSubmissionID())) {
				Utils.println(window + " Mostrar ventana TIPS!!");
				// this.showMenuMainDialog();
				return;
			}
			/*
			if ((SUBMIT_SEE_DETAILS).equals(uir.getSubmissionID())) {
			    // get scale value
			    Object o = uir.getUserInput(PROP_PATH_SELECTED_RECIPE.getThePath());
			    if (o instanceof Integer) {
			    	if (o instanceof Integer) {
						int idReceta = ((Integer) o).intValue();
						Utils.println(window + " Mostrar ventana detalle receta!!");
						Utils.println("Encuentro un ide de receta: "+idReceta);
						this.showRecipeDetailDialog(idReceta);
					} else {
						Utils.println("No encuentro un id de receta");
					}
			    } else {
			    	Utils.println("No es un integer");
			    }
				
				
				return;
			}
			*/
			if ((uir.getSubmissionID()).startsWith(SUBMIT_SEE_DETAILS)) {
				String[] values = uir.getSubmissionID().split("@");
				int idReceta = new Integer(values[1]);
				Utils.println(window + " Mostrar ventana detalle receta!!");
				Utils.println("Encuentro un ide de receta: "+idReceta);
				this.showRecipeDetailDialog(idReceta);
				return;
			}
		}
		Utils.println(window + " Continues");
	}
	
	public void showRecipeDetailDialog(int recipeID) {
		Utils.println(window + " showRecipeDetailDialog");
		UIRecipe_Detail r = new UIRecipe_Detail(this.myContext, recipeID);
		r.startMainDialog(this);
	}

	public void showTodayMainDialog(CustomUICaller parentForm) {
		Utils.println(window + " showTodayRecipesMainDialog");
		UIRecipesToday menus = new UIRecipesToday(this.myContext);
		menus.startMainDialog(this);
	}
	
	public void showTomorrwoMainDialog(CustomUICaller parentForm) {
		Utils.println(window + " showTomorrowRecipesMainDialog");
		UIRecipesTomorrow menus = new UIRecipesTomorrow(this.myContext);
		menus.startMainDialog(this);
	}

	public void startMainDialog(CustomUICaller parentForm) {
		Utils.println(window + " showMainDialog");
		this.parentUICaller = parentForm;
		if (mainDialog == null)
			mainDialog = initMainDialog();
		UIRequest out = new UIRequest(SharedResources.testUser, mainDialog,
				LevelRating.middle, Locale.ENGLISH, PrivacyLevel.insensible);
		sendUIRequest(out);
	}
	
	public void backToMainMenusDialog() {
		Utils.println(window + " showMain MainDialog");
		UIMainProvider main = new UIMainProvider(myContext);
		main.startMainDialog();
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
		Utils.println(window + " showing my MainForm!");
		this.startMainDialog(this.parentUICaller);
	}

	private DayMenu getMenu() {
		Utils.println(window + " getMenu!");
		try {
			NewBusiness business = new NewBusiness();
			na.miniDao.DayMenu tomorrowMenuDescription;
			tomorrowMenuDescription = business.getTomorrowMenu();
			return tomorrowMenuDescription;
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
