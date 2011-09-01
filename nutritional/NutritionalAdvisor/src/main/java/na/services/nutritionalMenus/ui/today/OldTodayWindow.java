package na.services.nutritionalMenus.ui.today;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JButton;

import na.miniDao.Dish;
import na.miniDao.Meal;
import na.services.nutritionalMenus.MenusSubServiceLauncher;
import na.utils.lang.Messages;
import na.services.nutritionalMenus.ui.CustomFrame;
import na.utils.OASIS_ServiceUnavailable;
import na.utils.ServiceInterface;
import na.utils.Utils;
import na.widgets.button.AdaptiveButton;
import na.widgets.label.AdaptiveLabel;
import na.widgets.panel.AdaptivePanel;
import na.widgets.textbox.AdaptiveTextBox;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SuppressWarnings("serial")
public class OldTodayWindow extends AdaptivePanel {
	private Log log = LogFactory.getLog(OldTodayWindow.class);
	private na.miniDao.DayMenu dayMenu = null;
	private List<GridBagConstraints> menuImages = new ArrayList<GridBagConstraints>();
	private List<IngredientsPanel> dishes = new ArrayList<IngredientsPanel>();
	private List<SnacksPanel> snacks = new ArrayList<SnacksPanel>();
	private List<AdaptiveButton> changeMealButtons = new ArrayList<AdaptiveButton>();
	private AdaptivePanel mainPanel;
	private MenusSubServiceLauncher launcher;
	
	public MenusSubServiceLauncher getLauncher() {
		return launcher;
	}

	public void setLauncher(MenusSubServiceLauncher launcher) {
		this.launcher = launcher;
	}

	private OldTodayWindow() {
		this.setBorder(BorderFactory.createEmptyBorder());
		GridBagLayout gridBagLayout_1 = new GridBagLayout();
		gridBagLayout_1.columnWidths = new int[]{500, 0};
		gridBagLayout_1.rowHeights = new int[]{400, 0};
		gridBagLayout_1.columnWeights = new double[]{1.0, 1.0E-4};
		gridBagLayout_1.rowWeights = new double[]{1.0, 1.0E-4};
		setLayout(gridBagLayout_1);
		{
			mainPanel = new AdaptivePanel();
			mainPanel.setBorder(BorderFactory.createEmptyBorder());
			GridBagLayout gridBagLayout = new GridBagLayout();
			gridBagLayout.columnWidths = new int[]{237, 237, 237, 0};
			gridBagLayout.rowHeights = new int[]{30, 159, 5, 159, 50, 50, 0};
			gridBagLayout.columnWeights = new double[]{1.0, 1.0, 1.0, 1.0E-4};
			gridBagLayout.rowWeights = new double[]{1.0, 0.0, 0.0, 1.0, 1.0, 1.0, 1.0E-4};
			mainPanel.setLayout(gridBagLayout);
			GridBagConstraints gbc_1 = new GridBagConstraints();
			gbc_1.fill = GridBagConstraints.BOTH;
			gbc_1.gridx = 0;
			gbc_1.gridy = 0;
			add(mainPanel, gbc_1);
			{
//				AdaptiveLabel label = new AdaptiveLabel();
//				label.setText(Messages.NutritionalMenus_title_Today_breakfast);
//				GridBagConstraints gbc = new GridBagConstraints();
//				gbc.insets = new Insets(0, 0, 5, 5);
//				gbc.gridx = 0;
//				gbc.gridy = 0;
//				mainPanel.add(label, gbc);
				CustomFrame cf = new CustomFrame();
				AdaptiveLabel label = new AdaptiveLabel();
				label.setFunction(ServiceInterface.Function_boldLabel);
				label.adapt();
				label.setText(Messages.Menus_Today_breakfast);
				cf.setLabel(label);
				GridBagConstraints gbc = new GridBagConstraints();
				gbc.fill = GridBagConstraints.HORIZONTAL;
				gbc.insets = new Insets(0, 6, 5, 5);
				gbc.gridx = 0;
				gbc.gridy = 0;
				mainPanel.add(cf, gbc);
			}
			{
				CustomFrame cf = new CustomFrame();
				AdaptiveLabel label = new AdaptiveLabel();
				label.setText(Messages.Menus_Today_lunch);
				label.setFunction(ServiceInterface.Function_boldLabel);
				label.adapt();
				cf.setLabel(label);
				GridBagConstraints gbc = new GridBagConstraints();
				gbc.fill = GridBagConstraints.HORIZONTAL;
				gbc.insets = new Insets(0, 6, 5, 5);
				gbc.gridx = 1;
				gbc.gridy = 0;
				mainPanel.add(cf, gbc);
			}
			{
				CustomFrame cf = new CustomFrame();
				AdaptiveLabel label = new AdaptiveLabel();
				label.setText(Messages.Menus_Today_dinner);
				label.setFunction(ServiceInterface.Function_boldLabel);
				label.adapt();
				cf.setLabel(label);
				GridBagConstraints gbc = new GridBagConstraints();
				gbc.fill = GridBagConstraints.HORIZONTAL;
				gbc.insets = new Insets(0, 6, 5, 5);
				gbc.gridx = 2;
				gbc.gridy = 0;
				mainPanel.add(cf, gbc);
			}
			{
				GridBagConstraints gbc = new GridBagConstraints();
				gbc.insets = new Insets(0, 0, 5, 5);
				gbc.gridx = 0;
				gbc.gridy = 1;
				this.menuImages.add(gbc);
			}
			{
				GridBagConstraints gbc = new GridBagConstraints();
				gbc.insets = new Insets(0, 0, 5, 5);
				gbc.gridx = 1;
				gbc.gridy = 1;
				this.menuImages.add(gbc);
			}
			{
				GridBagConstraints gbc = new GridBagConstraints();
				gbc.insets = new Insets(0, 0, 5, 5);
				gbc.gridx = 2;
				gbc.gridy = 1;
				this.menuImages.add(gbc);
			}
			{
				IngredientsPanel dishes = new IngredientsPanel();
				GridBagConstraints gbc = new GridBagConstraints();
				gbc.anchor = GridBagConstraints.NORTH;
				gbc.insets = new Insets(0, 0, 5, 5);
				gbc.gridx = 0;
				gbc.gridy = 3;
				mainPanel.add(dishes, gbc);
				this.dishes.add(dishes);
			}
			{
				IngredientsPanel dishes = new IngredientsPanel();
				GridBagConstraints gbc = new GridBagConstraints();
				gbc.anchor = GridBagConstraints.NORTH;
				gbc.insets = new Insets(0, 0, 5, 5);
				gbc.gridx = 1;
				gbc.gridy = 3;
				mainPanel.add(dishes, gbc);
				this.dishes.add(dishes);
			}
			{
				IngredientsPanel dishes = new IngredientsPanel();
				GridBagConstraints gbc = new GridBagConstraints();
				gbc.anchor = GridBagConstraints.NORTH;
				gbc.insets = new Insets(0, 0, 5, 0);
				gbc.gridx = 2;
				gbc.gridy = 3;
				mainPanel.add(dishes, gbc);
				this.dishes.add(dishes);
			}
			{
				AdaptiveButton button = new AdaptiveButton();
				button.setText(Messages.Menus_ChangeBreakfast);
				GridBagConstraints gbc = new GridBagConstraints();
				gbc.anchor = GridBagConstraints.NORTH;
				gbc.insets = new Insets(0, 0, 5, 5);
				gbc.gridx = 0;
				gbc.gridy = 4;
				mainPanel.add(button, gbc);
				this.changeMealButtons.add(button);
			}
			{
				AdaptiveButton button = new AdaptiveButton();
				button.setText(Messages.Menus_ChangeLunch);
				GridBagConstraints gbc = new GridBagConstraints();
				gbc.anchor = GridBagConstraints.NORTH;
				gbc.insets = new Insets(0, 0, 5, 5);
				gbc.gridx = 1;
				gbc.gridy = 4;
				mainPanel.add(button, gbc);
				this.changeMealButtons.add(button);
			}
			{
				AdaptiveButton button = new AdaptiveButton();
				button.setText(Messages.Menus_ChangeDinner);
				GridBagConstraints gbc = new GridBagConstraints();
				gbc.anchor = GridBagConstraints.NORTH;
				gbc.insets = new Insets(0, 0, 5, 0);
				gbc.gridx = 2;
				gbc.gridy = 4;
				mainPanel.add(button, gbc);
				this.changeMealButtons.add(button);
			}
			{
				//breakfast snack
				SnacksPanel snacks = new SnacksPanel();
				GridBagConstraints gbc = new GridBagConstraints();
				gbc.fill = GridBagConstraints.HORIZONTAL;
				gbc.anchor = GridBagConstraints.NORTH;
				gbc.insets = new Insets(0, 0, 0, 5);
				gbc.gridx = 0;
				gbc.gridy = 5;
				mainPanel.add(snacks, gbc);
				this.snacks.add(snacks);
			}
			{
				//lunch snack
				SnacksPanel snacks = new SnacksPanel();
				GridBagConstraints gbc = new GridBagConstraints();
				gbc.fill = GridBagConstraints.HORIZONTAL;
				gbc.anchor = GridBagConstraints.NORTH;
				gbc.insets = new Insets(0, 0, 0, 5);
				gbc.gridx = 1;
				gbc.gridy = 5;
				mainPanel.add(snacks, gbc);
				this.snacks.add(snacks);
			}
			{
				//dinner snack
				SnacksPanel snacks = new SnacksPanel();
				GridBagConstraints gbc = new GridBagConstraints();
				gbc.fill = GridBagConstraints.HORIZONTAL;
				gbc.anchor = GridBagConstraints.NORTH;
				gbc.gridx = 2;
				gbc.gridy = 5;
				mainPanel.add(snacks, gbc);
				this.snacks.add(snacks);
			}
		}
	}

	public void setDayMenu(na.miniDao.DayMenu dayMenu) {
		this.dayMenu = dayMenu;
	}

	public na.miniDao.DayMenu getDayMenu() {
		return dayMenu;
	}

	@SuppressWarnings("unchecked")
	public void drawContent(final boolean isToday) {
		if (this.dayMenu != null) {
			log.info("Drawing... content");
			na.miniDao.DayMenu menu = dayMenu;
			int[] meals_indexes = {menu.getBreakfastIndex(), menu.getLunchIndex(), menu.getDinnerIndex(), menu.getMidmorningSnackIndex(), menu.getAfternoonSnackIndex(), menu.getAfterdinnerSnackIndex()};
			// 3 iteraciones, 0=>breakfast; 1=>lunch; 2=>dinner
			for (int loop=0; loop<3; loop++) {
				try {
					// get current meal
					int meal_index = meals_indexes[loop];
					if (meal_index==-1)
						continue;
					na.miniDao.Meal meal = menu.getMeals()[meal_index];
					
					int pos = 0;
					
					// draw meal's dishes
					// Sort dishes
					Dish[] sortedDishes = meal.getDishes();
					Arrays.sort(sortedDishes, new DishesComparator());
					for (na.miniDao.Dish dish : sortedDishes) {
						na.widgets.textbox.AdaptiveTextBox meal_text = new na.widgets.textbox.AdaptiveTextBox();
						String text = dish.getDescription();
						meal_text.setText(Utils.Strings.capitalize(text));
						meal_text.setLineWrap(true);
						meal_text.setWrapStyleWord(true);
						meal_text.setRows(2);
						meal_text.validate();
						meal_text.setAlignmentY(AdaptiveTextBox.TOP_ALIGNMENT);
						
						GridBagConstraints g = ((GridBagConstraints)((IngredientsPanel)this.dishes.get(loop)).dishes.get(pos));
						((IngredientsPanel)this.dishes.get(loop)).add(meal_text,g);
						meal_text.validate();
						
						// See recipe
						final int meal_id = dish.getRecipeID(); 
//						if (meal_id != -1 && dish.isContainsRecipe()) {
							na.widgets.button.AdaptiveButton seeRecipeBut= new AdaptiveButton();
							seeRecipeBut.setText(Messages.Menus_SeeRecipe);
							seeRecipeBut.addActionListener(new java.awt.event.ActionListener() {
								public void actionPerformed(java.awt.event.ActionEvent evt) {
									butShowRecipe(evt, meal_id);
								}
							});
							GridBagConstraints gr = ((GridBagConstraints)((IngredientsPanel)this.dishes.get(loop)).seeRecipesButton.get(pos));
							((IngredientsPanel)this.dishes.get(loop)).add(seeRecipeBut,gr);
//						}
						
						pos++;
					}
					
					byte[] pictBytes = this.getMealPicture(meal);
					// Image
					if (pictBytes==null) {
						URL picbfURL = this.getClass().getResource(ServiceInterface.ImageNotAvailable);
						ImageIcon i = new ImageIcon(picbfURL);
						JButton breakfast_img = new JButton(i);
						breakfast_img.setBounds(5, 5, 237, 159);
						this.mainPanel.add(breakfast_img, ((GridBagConstraints)this.menuImages.get(loop)));
					} else {
						ImageIcon j = new ImageIcon(pictBytes);
						JButton breakfast_img = new JButton(j);
						breakfast_img.setBounds(5, 5, 237, 159);
						this.mainPanel.add(breakfast_img, ((GridBagConstraints)this.menuImages.get(loop)));
					}
					

					// Change meal
					final int va = loop;
					if (Utils.Dates.getIsLastDayOfWeek()==true) {
						this.changeMealButtons.get(loop).setVisible(false);
					} else {
						this.changeMealButtons.get(loop).setVisible(true);
						this.changeMealButtons.get(loop).addActionListener(new java.awt.event.ActionListener() {
							public void actionPerformed(java.awt.event.ActionEvent evt) {
								final int result = Utils.showPopUp(Messages.Menus_ChangeMeal_PopUp_Title, Messages.Menus_ChangeMeal_PopUp_Message, JOptionPane.YES_NO_OPTION);
								if (result == 0) {
									if (va==0)
										butChangeTodayBreakfast(evt, isToday);
									else if (va==1)
										butChangeTodayLunch(evt, isToday);
									else
										butChangeTodayDinner(evt, isToday);
								}
							}
						});
					}
				} catch (Exception e) {
					log.error("there was an error drawing today's menu");
					e.printStackTrace();
				}
			}
			
			// S N A C K S
			{
				String[] titles = {Messages.Menus_Today_midmorning_snack,
						Messages.Menus_Today_afternoon_snack,
						Messages.Menus_Today_afterdinner_snack};

				// draw components
				for (int loop=0; loop<3; loop++) {
					int meal_index = meals_indexes[loop+3];
					
					if (meal_index != -1) {
						na.miniDao.Meal meal = menu.getMeals()[meal_index];
						// title
						((SnacksPanel)this.snacks.get(loop)).title.setText(Utils.Strings.capitalize(titles[loop]));
		
						// Dishes
						int pos = 0;
						for (na.miniDao.Dish dish : meal.getDishes()) {
							na.widgets.label.AdaptiveLabel breakfast_meals = new AdaptiveLabel();
							breakfast_meals.setText(Utils.Strings.capitalize(dish.getDescription()));
							((SnacksPanel)this.snacks.get(loop)).itemsPanel.add(breakfast_meals);
							pos++;
						}
					} else {
						log.info("Showing no snacks");
//						((SnacksPanel)this.snacks.get(loop)).title.setText(Utils.Strings.capitalize(Messages.Menus_NO_snack));
						((SnacksPanel)this.snacks.get(loop)).setVisible(false);
					}
				} 
			}
		} else {
			log.error("Error fatal! There is no dayMenu content!!!");
		}
		this.repaintCanvas();
	}
	
	private void repaintCanvas() {
		this.validate();
		this.repaint();
	}

	private byte[] getMealPicture(Meal meal) {
		// buscar main course 	 = 4
		// buscar first course	 = 3
		// buscar starter course = 6
		// buscar side dish		 = 5
		// buscar breakfast		 = 7
		// mostrar no encontrado = 
		byte[] main = null;
		byte[] first = null;
		byte[] starter = null;
		byte[] side = null;
		byte[] breakfast = null;
		
		// tan pronto encuentro una imagen, termino
		for (Dish dish : meal.getDishes()) {
			if (dish.getDishCategory()==4 && dish.getImage()!=null) {
				byte[] picBytes = null;
				try {
					picBytes = this.launcher.getDishPicture(dish.getRecipeID());
				} catch (OASIS_ServiceUnavailable e) {
					e.printStackTrace();
					log.error("couldn't get img, service not available");
				}
				if (picBytes!=null) {
					main = picBytes;
					return main;
				}
			} else if (dish.getDishCategory()==3 && dish.getImage()!=null) {
				byte[] picBytes = null;
				try {
					picBytes = this.launcher.getDishPicture(dish.getRecipeID());
				} catch (OASIS_ServiceUnavailable e) {
					e.printStackTrace();
					log.error("couldn't get img, service not available");
				}
				if (picBytes!=null) {
					first = picBytes;
					return first;
				}
			} else if (dish.getDishCategory()==6 && dish.getImage()!=null) {
				byte[] picBytes = null;
				try {
					picBytes = this.launcher.getDishPicture(dish.getRecipeID());
				} catch (OASIS_ServiceUnavailable e) {
					e.printStackTrace();
					log.error("couldn't get img, service not available");
				}
				if (picBytes!=null) {
					starter = picBytes;
					return starter;
				}
			} else if (dish.getDishCategory()==5 && dish.getImage()!=null) {
				byte[] picBytes = null;
				try {
					picBytes = this.launcher.getDishPicture(dish.getRecipeID());
				} catch (OASIS_ServiceUnavailable e) {
					e.printStackTrace();
					log.error("couldn't get img, service not available");
				}
				if (picBytes!=null) {
					side = picBytes;
					return side;
				}
			} else if (dish.getDishCategory()==7 && dish.getImage()!=null) {
				byte[] picBytes = null;
				try {
					picBytes = this.launcher.getDishPicture(dish.getRecipeID());
				} catch (OASIS_ServiceUnavailable e) {
					e.printStackTrace();
					log.error("couldn't get img, service not available");
				}
				if (picBytes!=null) {
					breakfast = picBytes;
					return breakfast;
				}
			} else {
				log.error("Wrong dishCategory: "+dish.getDishCategory() + " image: "+dish.getImage());
			}
		}
		
//		if (main!=null) {
//			return main;
//		} else if (first!=null) {
//			return first;
//		} else if (starter!=null) {
//			return starter;
//		} else if (side!=null) {
//			return side;
//		} else if (breakfast!=null) {
//			return breakfast;
//		}
		
		return null;
	}
	
	private void butShowRecipe(java.awt.event.ActionEvent evt, int recipeID) {
		log.info("Showing recipe ID: "+recipeID);
		launcher.showRecipe(recipeID);
    }
	
	private void butChangeTodayBreakfast(java.awt.event.ActionEvent evt, boolean today) {
		log.info("Changing BREAKFAST");
		this.butChangeBreakfast(today);
		if (today) {
			launcher.showTodayMenus();
		} else {
			launcher.showTomorrowMenus();
		}
    }
	
	private void butChangeTodayLunch(java.awt.event.ActionEvent evt, boolean today) {
		log.info("Changing LUNCH");
		this.butChangeLunch(today);
		if (today)
			launcher.showTodayMenus();
		else
			launcher.showTomorrowMenus();
    }
	private void butChangeTodayDinner(java.awt.event.ActionEvent evt, boolean today) {
		log.info("Changing DINNER");
		this.butChangeDinner(today);
		if (today)
			launcher.showTodayMenus();
		else
			launcher.showTomorrowMenus();
    }
	

	private void butChangeBreakfast(boolean today) {
		if (today)
			launcher.changeMeal(ServiceInterface.today_int,ServiceInterface.MealCategory_BreakFast);
		else
			launcher.changeMeal(ServiceInterface.tomorrow_int,ServiceInterface.MealCategory_BreakFast);
    }
	
	private void butChangeLunch(boolean today) {
		if (today)
			launcher.changeMeal(ServiceInterface.today_int,ServiceInterface.MealCategory_Lunch);
		else
			launcher.changeMeal(ServiceInterface.tomorrow_int,ServiceInterface.MealCategory_Lunch);
    }
	
	private void butChangeDinner(boolean today) {
		if (today)
			launcher.changeMeal(ServiceInterface.today_int,ServiceInterface.MealCategory_Dinner);
		else
			launcher.changeMeal(ServiceInterface.tomorrow_int,ServiceInterface.MealCategory_Dinner);
    }

}

