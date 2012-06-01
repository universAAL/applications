package na.services.recipes.ui.today;


import na.utils.lang.Messages;
import na.services.recipes.RecipesSubServiceLauncher;
import na.utils.OASIS_ServiceUnavailable;
import na.utils.ServiceInterface;
import na.utils.Utils;
import na.widgets.label.AdaptiveLabel;
import na.widgets.panel.AdaptivePanel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;



import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;

@SuppressWarnings("serial")
public class DayRecipesWindow extends AdaptivePanel {
	private Log log = LogFactory.getLog(DayRecipesWindow.class);
	protected List<JButton> imgButtons = new ArrayList<JButton>();
	protected List<AdaptiveLabel> mealCatLabels = new ArrayList<AdaptiveLabel>();
	protected List<AdaptiveLabel> mealLabels = new ArrayList<AdaptiveLabel>();
	private DayRecipesPanel panel;
	private na.miniDao.DayMenu dayMenu = null;
	private RecipesSubServiceLauncher launcher;
	
	
	public DayRecipesWindow() {
		GridBagLayout gridBagLayout_1 = new GridBagLayout();
		gridBagLayout_1.columnWidths = new int[]{500, 0};
		gridBagLayout_1.rowHeights = new int[]{400, 0};
		gridBagLayout_1.columnWeights = new double[]{1.0, 1.0E-4};
		gridBagLayout_1.rowWeights = new double[]{1.0, 1.0E-4};
		setLayout(gridBagLayout_1);
		{
			panel = new DayRecipesPanel();
			panel.setBorder(BorderFactory.createEmptyBorder());
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.fill = GridBagConstraints.BOTH;
			gbc.gridx = 0;
			gbc.gridy = 0;
			add(panel, gbc);
		}
		
		panel.dayRecipeWindow = this;
	}
	
	public RecipesSubServiceLauncher getLauncher() {
		return launcher;
	}

	public void setLauncher(RecipesSubServiceLauncher launcher) {
		this.launcher = launcher;
		this.panel.setWindow(this);
	}

	public void setDayMenu(na.miniDao.DayMenu dayMenu) {
		this.dayMenu = dayMenu;
	}

	public na.miniDao.DayMenu getDayMenu() {
		return dayMenu;
	}

	public void drawContent() {
		// recorrer meals, para cada meal, si hay receta mostrarla
		// por cada meal category una fila
		
		if (this.dayMenu != null) {
			this.panel.drawContent();
			
			log.info("Dibujando RECETASS!!!");

			int row_index = 0;
			
			for (int index = 0; index < 3; index++) {
				int meal_index = 0+index*4;
				boolean is_empty_row = true;
				// si el menu no tiene meals, continuar
				if (index >= dayMenu.getMeals().length) {
					log.info("index="+index +" dayMenu.mealsLength="+dayMenu.getMeals().length);
					continue;
				}
				
				// draw Meal Category, solo break, lunch, meal
				String mealCatTitle = "meal category";
				System.out.println("Category is: "+dayMenu.getMeals()[index].getCategory());
				if (dayMenu.getMeals()[index].getCategory().compareToIgnoreCase(ServiceInterface.MealCategory_Lunch_str)==0) {
					mealCatTitle = Messages.Recipes_Today_lunch;
				} else if (dayMenu.getMeals()[index].getCategory().compareToIgnoreCase(ServiceInterface.MealCategory_BreakFast_Str)==0) {
					mealCatTitle = Messages.Recipes_Today_breakfast;
				} else if (dayMenu.getMeals()[index].getCategory().compareToIgnoreCase(ServiceInterface.MealCategory_Dinner_str)==0) {
					mealCatTitle = Messages.Recipes_Today_dinner;
				} 
//				else if (dayMenu.getMeals()[index].getCategory().compareToIgnoreCase("Afternoon snack")==0) {
//					mealCatTitle = Messages.Menus_Today_afternoon_snack;
//				} else if (dayMenu.getMeals()[index].getCategory().compareToIgnoreCase("Midmorning snack")==0) {
//					mealCatTitle = Messages.Menus_Today_midmorning_snack;
//				} else if (dayMenu.getMeals()[index].getCategory().compareToIgnoreCase("Afterdinner snack")==0) {
//					mealCatTitle = Messages.Menus_Today_afterdinner_snack;
//				}
				this.mealCatLabels.get(row_index).setText(Utils.Strings.capitalize(mealCatTitle));
				if ((dayMenu.getMeals()[index] != null)) {
					for (na.miniDao.Dish dish : dayMenu.getMeals()[index].getDishes()) {
						String courseName = dish.getDescription();
						final int recipeID = dish.getRecipeID();

						if (recipeID==-1) {
							log.info(courseName+" no es una receta, continuar");
							continue;
						}
//						if (!dish.isContainsRecipe()) {
//							log.info(courseName+" no es una receta de verdad, continuar");
//							continue;
//						}
						
						// Draw courseName
						courseName = Utils.Strings.capitalize(Utils.Strings.capitalize(courseName));
						this.mealLabels.get(meal_index).setText(Utils.Strings.capitalize(courseName));
						
						byte[] pictBytes = null;
						try {
							pictBytes = this.launcher.getDishPicture(dish.getRecipeID());
						} catch (OASIS_ServiceUnavailable e) {
							e.printStackTrace();
							log.error("Couldn't get image :( service unavailable");
						}
						// Image
						if (pictBytes==null) {
							URL picbfURL = this.getClass().getResource(ServiceInterface.ImageNotAvailable);
							ImageIcon i = new ImageIcon(picbfURL);
							this.imgButtons.get(meal_index).addActionListener(new java.awt.event.ActionListener() {
								public void actionPerformed(java.awt.event.ActionEvent evt) {
									butShowRecipe(evt, recipeID);
									}
								});
							this.imgButtons.get(meal_index).setIcon(i);
						} else {
							ImageIcon j = new ImageIcon(pictBytes);
							this.imgButtons.get(meal_index).setIcon(j);
							this.imgButtons.get(meal_index).addActionListener(new java.awt.event.ActionListener() {
							public void actionPerformed(java.awt.event.ActionEvent evt) {
								butShowRecipe(evt, recipeID);
								}
							});
							this.imgButtons.get(meal_index).setSize(j.getIconWidth(), j.getIconHeight());
						}

						is_empty_row = false;
						log.info("Index: "+index+" Receta mostrada: "+courseName);
						meal_index++;
					}
				} else {
					log.info("Meal: "+index+ " es null");
				}

				// si se ha añadido algún elemento, continuar en la siguiente fila
				if (!is_empty_row)
					row_index++;
				
			} // end looping meal categories
			
		} else {
			log.info("Error: set menu info first!");
		}
	}
	
	
	private void butShowRecipe(java.awt.event.ActionEvent evt, int recipeID) {
		log.info("Showing recipe ID: "+recipeID);
		launcher.showRecipe(recipeID);
    }
	

}
