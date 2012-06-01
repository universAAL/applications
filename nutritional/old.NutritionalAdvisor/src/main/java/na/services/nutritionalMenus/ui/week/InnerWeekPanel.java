package na.services.nutritionalMenus.ui.week;

import na.miniDao.DayMenu;
import na.services.nutritionalMenus.MenusSubServiceLauncher;
import na.utils.lang.Messages;
import na.utils.ServiceInterface;
import na.utils.Utils;
import na.widgets.label.AdaptiveLabel;
import na.widgets.panel.AdaptivePanel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class InnerWeekPanel extends AdaptivePanel {
	private Log log = LogFactory.getLog(InnerWeekPanel.class);	
	@SuppressWarnings("unused")
	private MenusSubServiceLauncher launcher;
	private List<AdaptiveLabel> labels = new ArrayList<AdaptiveLabel>();

	public InnerWeekPanel() {
		GridBagLayout gridBagLayout_1 = new GridBagLayout();
		gridBagLayout_1.columnWidths = new int[]{100, 50, 50, 50, 0};
		gridBagLayout_1.rowHeights = new int[]{50, 50, 50, 50, 50, 50, 50, 50, 0};
		gridBagLayout_1.columnWeights = new double[]{1.0, 1.0, 1.0, 1.0, 1.0E-4};
		gridBagLayout_1.rowWeights = new double[]{1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0E-4};
		setLayout(gridBagLayout_1);
		
		// Monday to Friday
		String weekDays[] = Utils.Dates.getDaysOfWeek();
		
		{
			AdaptiveLabel lblBreakfast = new AdaptiveLabel();
			lblBreakfast.setText(Messages.Menus_Today_breakfast);
			lblBreakfast.setFunction(ServiceInterface.Function_boldLabel);
			lblBreakfast.adapt();
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.insets = new Insets(0, 0, 5, 5);
			gbc.gridx = 1;
			gbc.gridy = 0;
			add(lblBreakfast, gbc);
		}
		{
			AdaptiveLabel lblLunch = new AdaptiveLabel();
			lblLunch.setText(Messages.Menus_Today_lunch);
			lblLunch.setFunction(ServiceInterface.Function_boldLabel);
			lblLunch.adapt();
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.insets = new Insets(0, 0, 5, 5);
			gbc.gridx = 2;
			gbc.gridy = 0;
			add(lblLunch, gbc);
		}
		{
			AdaptiveLabel lblDinner = new AdaptiveLabel();
			lblDinner.setText(Messages.Menus_Today_dinner);
			lblDinner.setFunction(ServiceInterface.Function_boldLabel);
			lblDinner.adapt();
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.insets = new Insets(0, 0, 5, 0);
			gbc.gridx = 3;
			gbc.gridy = 0;
			add(lblDinner, gbc);
		}
		{
			AdaptiveLabel lblMonday = new AdaptiveLabel();
			lblMonday.setText(weekDays[0]);
			lblMonday.setFunction(ServiceInterface.Function_boldLabel);
			lblMonday.adapt();
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.insets = new Insets(0, 0, 5, 5);
			gbc.gridx = 0;
			gbc.gridy = 1;
			add(lblMonday, gbc);
		}
		{
			AdaptiveLabel label = new AdaptiveLabel();
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.anchor = GridBagConstraints.WEST;
			gbc.insets = new Insets(0, 0, 5, 5);
			gbc.gridx = 1;
			gbc.gridy = 1;
			add(label, gbc);
			this.labels.add(label);
		}
		{
			AdaptiveLabel label = new AdaptiveLabel();
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.anchor = GridBagConstraints.WEST;
			gbc.insets = new Insets(0, 0, 5, 5);
			gbc.gridx = 2;
			gbc.gridy = 1;
			add(label, gbc);
			this.labels.add(label);
		}
		{
			AdaptiveLabel label = new AdaptiveLabel();
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.anchor = GridBagConstraints.WEST;
			gbc.insets = new Insets(0, 0, 5, 0);
			gbc.gridx = 3;
			gbc.gridy = 1;
			add(label, gbc);
			this.labels.add(label);
		}
		{
			AdaptiveLabel lblTuesday = new AdaptiveLabel();
			lblTuesday.setText(weekDays[1]);
			lblTuesday.setFunction(ServiceInterface.Function_boldLabel);
			lblTuesday.adapt();
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.insets = new Insets(0, 0, 5, 5);
			gbc.gridx = 0;
			gbc.gridy = 2;
			add(lblTuesday, gbc);
		}
		{
			AdaptiveLabel label = new AdaptiveLabel();
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.anchor = GridBagConstraints.WEST;
			gbc.insets = new Insets(0, 0, 5, 5);
			gbc.gridx = 1;
			gbc.gridy = 2;
			add(label, gbc);
			this.labels.add(label);
		}
		{
			AdaptiveLabel label = new AdaptiveLabel();
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.anchor = GridBagConstraints.WEST;
			gbc.insets = new Insets(0, 0, 5, 5);
			gbc.gridx = 2;
			gbc.gridy = 2;
			add(label, gbc);
			this.labels.add(label);
		}
		{
			AdaptiveLabel label = new AdaptiveLabel();
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.anchor = GridBagConstraints.WEST;
			gbc.insets = new Insets(0, 0, 5, 0);
			gbc.gridx = 3;
			gbc.gridy = 2;
			add(label, gbc);
			this.labels.add(label);
		}
		{
			AdaptiveLabel lblWednesday = new AdaptiveLabel();
			lblWednesday.setText(weekDays[2]);
			lblWednesday.setFunction(ServiceInterface.Function_boldLabel);
			lblWednesday.adapt();
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.insets = new Insets(0, 0, 5, 5);
			gbc.gridx = 0;
			gbc.gridy = 3;
			add(lblWednesday, gbc);
		}
		{
			AdaptiveLabel label = new AdaptiveLabel();
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.anchor = GridBagConstraints.WEST;
			gbc.insets = new Insets(0, 0, 5, 5);
			gbc.gridx = 1;
			gbc.gridy = 3;
			add(label, gbc);
			this.labels.add(label);
		}
		{
			AdaptiveLabel label = new AdaptiveLabel();
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.anchor = GridBagConstraints.WEST;
			gbc.insets = new Insets(0, 0, 5, 5);
			gbc.gridx = 2;
			gbc.gridy = 3;
			add(label, gbc);
			this.labels.add(label);
		}
		{
			AdaptiveLabel label = new AdaptiveLabel();
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.anchor = GridBagConstraints.WEST;
			gbc.insets = new Insets(0, 0, 5, 0);
			gbc.gridx = 3;
			gbc.gridy = 3;
			this.labels.add(label);
			add(label, gbc);
		}
		{
			AdaptiveLabel lblThursday = new AdaptiveLabel();
			lblThursday.setText(weekDays[3]);
			lblThursday.setFunction(ServiceInterface.Function_boldLabel);
			lblThursday.adapt();
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.insets = new Insets(0, 0, 5, 5);
			gbc.gridx = 0;
			gbc.gridy = 4;
			add(lblThursday, gbc);
		}
		{
			AdaptiveLabel label = new AdaptiveLabel();
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.anchor = GridBagConstraints.WEST;
			gbc.insets = new Insets(0, 0, 5, 5);
			gbc.gridx = 1;
			gbc.gridy = 4;
			add(label, gbc);
			this.labels.add(label);
		}
		{
			AdaptiveLabel label = new AdaptiveLabel();
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.anchor = GridBagConstraints.WEST;
			gbc.insets = new Insets(0, 0, 5, 5);
			gbc.gridx = 2;
			gbc.gridy = 4;
			add(label, gbc);
			this.labels.add(label);
		}
		{
			AdaptiveLabel label = new AdaptiveLabel();
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.anchor = GridBagConstraints.WEST;
			gbc.insets = new Insets(0, 0, 5, 0);
			gbc.gridx = 3;
			gbc.gridy = 4;
			add(label, gbc);
			this.labels.add(label);
		}
		{
			AdaptiveLabel lblFriday = new AdaptiveLabel();
			lblFriday.setText(weekDays[4]);
			lblFriday.setFunction(ServiceInterface.Function_boldLabel);
			lblFriday.adapt();
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.insets = new Insets(0, 0, 5, 5);
			gbc.gridx = 0;
			gbc.gridy = 5;
			add(lblFriday, gbc);
		}
		{
			AdaptiveLabel label = new AdaptiveLabel();
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.anchor = GridBagConstraints.WEST;
			gbc.insets = new Insets(0, 0, 5, 5);
			gbc.gridx = 1;
			gbc.gridy = 5;
			add(label, gbc);
			this.labels.add(label);
		}
		{
			AdaptiveLabel label = new AdaptiveLabel();
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.anchor = GridBagConstraints.WEST;
			gbc.insets = new Insets(0, 0, 5, 5);
			gbc.gridx = 2;
			gbc.gridy = 5;
			add(label, gbc);
			this.labels.add(label);
		}
		{
			AdaptiveLabel label = new AdaptiveLabel();
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.anchor = GridBagConstraints.WEST;
			gbc.insets = new Insets(0, 0, 5, 0);
			gbc.gridx = 3;
			gbc.gridy = 5;
			add(label, gbc);
			this.labels.add(label);
		}
		{
			AdaptiveLabel lblSaturday = new AdaptiveLabel();
			lblSaturday.setText(weekDays[5]);
			lblSaturday.setFunction(ServiceInterface.Function_boldLabel);
			lblSaturday.adapt();
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.insets = new Insets(0, 0, 5, 5);
			gbc.gridx = 0;
			gbc.gridy = 6;
			add(lblSaturday, gbc);
		}
		{
			AdaptiveLabel label = new AdaptiveLabel();
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.anchor = GridBagConstraints.WEST;
			gbc.insets = new Insets(0, 0, 5, 5);
			gbc.gridx = 1;
			gbc.gridy = 6;
			add(label, gbc);
			this.labels.add(label);
		}
		{
			AdaptiveLabel label = new AdaptiveLabel();
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.anchor = GridBagConstraints.WEST;
			gbc.insets = new Insets(0, 0, 5, 5);
			gbc.gridx = 2;
			gbc.gridy = 6;
			add(label, gbc);
			this.labels.add(label);
		}
		{
			AdaptiveLabel label = new AdaptiveLabel();
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.anchor = GridBagConstraints.WEST;
			gbc.insets = new Insets(0, 0, 5, 0);
			gbc.gridx = 3;
			gbc.gridy = 6;
			add(label, gbc);
			this.labels.add(label);
		}
		{
			AdaptiveLabel lblSunday = new AdaptiveLabel();
			lblSunday.setText(weekDays[6]);
			lblSunday.setFunction(ServiceInterface.Function_boldLabel);
			lblSunday.adapt();
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.insets = new Insets(0, 0, 0, 5);
			gbc.gridx = 0;
			gbc.gridy = 7;
			add(lblSunday, gbc);
		}
		{
			AdaptiveLabel label = new AdaptiveLabel();
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.anchor = GridBagConstraints.WEST;
			gbc.insets = new Insets(0, 0, 0, 5);
			gbc.gridx = 1;
			gbc.gridy = 7;
			add(label, gbc);
			this.labels.add(label);
		}
		{
			AdaptiveLabel label = new AdaptiveLabel();
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.anchor = GridBagConstraints.WEST;
			gbc.insets = new Insets(0, 0, 0, 5);
			gbc.gridx = 2;
			gbc.gridy = 7;
			add(label, gbc);
			this.labels.add(label);
		}
		{
			AdaptiveLabel label = new AdaptiveLabel();
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.anchor = GridBagConstraints.WEST;
			gbc.gridx = 3;
			gbc.gridy = 7;
			add(label, gbc);
			this.labels.add(label);
		}
	}
	
	public void setLauncher(MenusSubServiceLauncher subServiceLauncher) {
		this.launcher = subServiceLauncher;
	}

	public void drawContent(DayMenu[] menus) {
		log.info("drawing...");
		int[] indexes =Utils.Dates.getIntDaysOfWeek();
		
		{
			int break_index = -3;
			for (int index=0; index<7; index++) {
				int i = indexes[index];
				int pos = 0;
				if (menus[i]==null) {
					log.info("menu "+i+" is null");
					continue;
				}
				int meal_index = menus[i].getBreakfastIndex();
				if (meal_index!=-1 && menus[i].getMeals()[meal_index]!=null) {
					break_index = break_index+3;
					// TODO: ordenar dishes
					for (na.miniDao.Dish dish : menus[i].getMeals()[meal_index].getDishes()) {
						if (pos>1) //more than two doesn't fit in the table
							break;
						String temp = dish.getDescription().substring(0, 1).toUpperCase() + dish.getDescription().substring(1);
						this.labels.get(break_index).setText(temp);
						pos++;
					}
				}
			}
		}
		{
			int lunch_index = -2;
			for (int index=0; index<7; index++) {
				int i = indexes[index];
				int pos = 0;
				if (menus[i]==null) {
					log.info("menu "+i+" is null");
					continue;
				}
				int meal_index = menus[i].getLunchIndex();
				if (meal_index!=-1 && menus[i].getMeals()[meal_index]!=null) {
					lunch_index = lunch_index+3;
					for (na.miniDao.Dish dish : menus[i].getMeals()[meal_index].getDishes()) {
						if (pos>1) //more than two doesn't fit in the table
							break;
						String temp = dish.getDescription().substring(0, 1).toUpperCase() + dish.getDescription().substring(1);
						this.labels.get(lunch_index).setText(temp);
						pos++;
					}
				}
			}
		}
		{
			int dinner_index = -1;
			for (int index=0; index<7; index++) {
				int i = indexes[index];
				int pos = 0;
				if (menus[i]==null) {
					log.info("menu "+i+" is null");
					continue;
				}
				int meal_index = menus[i].getDinnerIndex();
				if (meal_index!=-1 && menus[i].getMeals()[meal_index]!=null) {
					dinner_index = dinner_index+3;
					for (na.miniDao.Dish dish : menus[i].getMeals()[meal_index].getDishes()) {
						if (pos>1) //more than two doesn't fit in the table
							break;
						String temp = dish.getDescription().substring(0, 1).toUpperCase() + dish.getDescription().substring(1);
						this.labels.get(dinner_index).setText(temp);
						pos++;
					}
				}
			}
		}
	}

}
