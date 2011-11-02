package na.services.nutritionalMenus.ui.week;


import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.util.Arrays;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

import na.miniDao.DayMenu;
import na.miniDao.Dish;
import na.services.nutritionalMenus.ui.today.DishesComparator;
import na.utils.lang.Messages;
import na.utils.Utils;
import na.widgets.panel.AdaptivePanel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


@SuppressWarnings("serial")
public class WeeklyWindow extends AdaptivePanel {
	private Log log = LogFactory.getLog(WeeklyWindow.class);	
	private CustomTable table;
	private JScrollPane scrollPane;
	
	public WeeklyWindow() {
		this.setBorder(BorderFactory.createEmptyBorder());
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{20, 0, 20, 0};
		gridBagLayout.rowHeights = new int[]{20, 0, 20, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 1.0, 0.0, 1.0E-4};
		gridBagLayout.rowWeights = new double[]{0.0, 1.0, 0.0, 1.0E-4};
		setLayout(gridBagLayout);
		{
			AdaptivePanel panel = new AdaptivePanel();
			panel.setBorder(BorderFactory.createEmptyBorder());
			GridBagLayout gridBagLayout_1 = new GridBagLayout();
			gridBagLayout_1.columnWidths = new int[]{0, 0};
			gridBagLayout_1.rowHeights = new int[]{0, 0};
			gridBagLayout_1.columnWeights = new double[]{1.0, 1.0E-4};
			gridBagLayout_1.rowWeights = new double[]{1.0, 1.0E-4};
			panel.setLayout(gridBagLayout_1);
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.insets = new Insets(0, 0, 5, 5);
			gbc.fill = GridBagConstraints.HORIZONTAL;
			gbc.gridx = 1;
			gbc.gridy = 1;
			add(panel, gbc);
			{
				scrollPane = new JScrollPane();
				GridBagConstraints gbc_1 = new GridBagConstraints();
				gbc_1.fill = GridBagConstraints.BOTH;
//				gbc_1.fill = GridBagConstraints.BOTH;
				panel.add(scrollPane, gbc_1);
				
			}
		}
	}

	public void getReady(DayMenu[] menus) {
		// Monday to Friday
		String weekDays[] = Utils.Dates.getDaysOfWeek();
		
		Object[][] data = new Object [][] {
                {weekDays[0], null, null, null},
                {weekDays[1], null, null, null},
                {weekDays[2], null, null, null},
                {weekDays[3], null, null, null},
                {weekDays[4], null, null, null},
                {weekDays[5], null, null, null},
                {weekDays[6], null, null, null}
            };
		int[] indexes =Utils.Dates.getIntDaysOfWeek();
		
		// 3 iteraciones, una por meal
		for (int it=0; it<3; it++) 
		{
			int break_index = -1;
			for (int index=0; index<7; index++) {
//				int i = indexes[index];
				int i = index;
				int pos = 0;
				if (menus[i]==null) {
					log.info("menu "+i+" is null");
					continue;
				}
				int meal_index = -1;
				if (it==0)
					meal_index = menus[i].getBreakfastIndex();
				else if (it==1)
					meal_index = menus[i].getLunchIndex();
				else
					meal_index = menus[i].getDinnerIndex();
				try {
					if (meal_index!=-1 && menus[i].getMeals()[meal_index]!=null) {
						break_index++;
						// TODO: ordenar dishes previamente?
						// Sort dishes
						Dish[] sortedDishes = menus[i].getMeals()[meal_index].getDishes();
						Arrays.sort(sortedDishes, new DishesComparator());
						for (na.miniDao.Dish dish : sortedDishes) {
							if (dish!=null && dish.getDescription()!=null) {
								if (pos>1) //more than two doesn't fit in the table
									break;
								String temp = dish.getDescription().substring(0, 1).toUpperCase() + dish.getDescription().substring(1);
								data[break_index][it+1] = temp;
							}
							pos++;
						}
					}
				} catch (Exception e) {
					log.error("There was an error, drawing week menu");
					e.printStackTrace();
				}
			}
		}
		
			table = new CustomTable();
			
			// disable selections
			table.setFocusable(false);
			table.setCellSelectionEnabled(false);
			table.setColumnSelectionAllowed(false);
			table.setRowSelectionAllowed(false);
			
			// header height
			table.getTableHeader().setPreferredSize(new Dimension(table.getTableHeader().getWidth(),40));
			
			// row height
			table.setRowHeight(50);
			
			table.setModel(new javax.swing.table.DefaultTableModel(
		            data,
		            new String [] {
		                "", Messages.Menus_Today_breakfast, Messages.Menus_Today_lunch, Messages.Menus_Today_dinner
		            }
		        )
			{
	            boolean[] canEdit = new boolean [] {
	                false, false, false, false, false, false, false
	            };

	            public boolean isCellEditable(int rowIndex, int columnIndex) {
	                return canEdit [columnIndex];
	            }
	        }
			);
			scrollPane.setViewportView(table);
	}
	
	
}
