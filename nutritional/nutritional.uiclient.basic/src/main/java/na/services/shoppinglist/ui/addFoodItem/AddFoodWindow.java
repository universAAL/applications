package na.services.shoppinglist.ui.addFoodItem;

import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.ListSelectionModel;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JScrollPane;

import na.utils.lang.Messages;
import na.widgets.button.AdaptiveButton;
import na.widgets.list.AdaptiveList;
import na.widgets.panel.AdaptivePanel;
import na.services.shoppinglist.ui.SubServiceFrame;

@SuppressWarnings("serial")
public class AddFoodWindow extends AdaptivePanel {
	
	private AdaptiveList foodListing;
	private List<AdaptiveButton> listCategoryButtons = new ArrayList<AdaptiveButton>();
	private AdaptiveButton addFoodBut;
	
	public AddFoodWindow() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{10, 80, 5, 100, 10, 0};
		gridBagLayout.rowHeights = new int[]{0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 1.0, 0.0, 1.0E-4};
		gridBagLayout.rowWeights = new double[]{1.0, 1.0E-4};
		setLayout(gridBagLayout);
		{
			AdaptivePanel categoryPanel = new AdaptivePanel();
			categoryPanel.setBorder(BorderFactory.createEmptyBorder());
			GridBagLayout gridBagLayout_1 = new GridBagLayout();
			gridBagLayout_1.columnWidths = new int[]{0, 0};
			gridBagLayout_1.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
			gridBagLayout_1.columnWeights = new double[]{0.0, 1.0E-4};
			gridBagLayout_1.rowWeights = new double[]{1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0E-4};
			categoryPanel.setLayout(gridBagLayout_1);
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.insets = new Insets(0, 0, 0, 5);
			gbc.fill = GridBagConstraints.BOTH;
			gbc.gridx = 1;
			gbc.gridy = 0;
			add(categoryPanel, gbc);
			{
				AdaptiveButton button = new AdaptiveButton();
				button.setText("Food Category");
				GridBagConstraints gbc_1 = new GridBagConstraints();
				gbc_1.fill = GridBagConstraints.HORIZONTAL;
				gbc_1.insets = new Insets(0, 0, 5, 0);
				gbc_1.gridx = 0;
				gbc_1.gridy = 0;
				categoryPanel.add(button, gbc_1);
				this.listCategoryButtons.add(button);
			}
			{
				AdaptiveButton adaptiveButton = new AdaptiveButton();
				adaptiveButton.setText("Food Category");
				GridBagConstraints gbc_1 = new GridBagConstraints();
				gbc_1.fill = GridBagConstraints.HORIZONTAL;
				gbc_1.insets = new Insets(0, 0, 5, 0);
				gbc_1.gridx = 0;
				gbc_1.gridy = 1;
				categoryPanel.add(adaptiveButton, gbc_1);
				this.listCategoryButtons.add(adaptiveButton);
			}
			{
				AdaptiveButton adaptiveButton = new AdaptiveButton();
				adaptiveButton.setText("Food Category");
				GridBagConstraints gbc_1 = new GridBagConstraints();
				gbc_1.fill = GridBagConstraints.HORIZONTAL;
				gbc_1.insets = new Insets(0, 0, 5, 0);
				gbc_1.gridx = 0;
				gbc_1.gridy = 2;
				categoryPanel.add(adaptiveButton, gbc_1);
				this.listCategoryButtons.add(adaptiveButton);
			}
			{
				AdaptiveButton adaptiveButton = new AdaptiveButton();
				adaptiveButton.setText("Food Category");
				GridBagConstraints gbc_1 = new GridBagConstraints();
				gbc_1.fill = GridBagConstraints.HORIZONTAL;
				gbc_1.insets = new Insets(0, 0, 5, 0);
				gbc_1.gridx = 0;
				gbc_1.gridy = 3;
				categoryPanel.add(adaptiveButton, gbc_1);
				this.listCategoryButtons.add(adaptiveButton);
			}
			{
				AdaptiveButton adaptiveButton = new AdaptiveButton();
				adaptiveButton.setText("Food Category");
				GridBagConstraints gbc_1 = new GridBagConstraints();
				gbc_1.fill = GridBagConstraints.HORIZONTAL;
				gbc_1.insets = new Insets(0, 0, 5, 0);
				gbc_1.gridx = 0;
				gbc_1.gridy = 4;
				categoryPanel.add(adaptiveButton, gbc_1);
				this.listCategoryButtons.add(adaptiveButton);
			}
			{
				AdaptiveButton adaptiveButton = new AdaptiveButton();
				adaptiveButton.setText("Food Category");
				GridBagConstraints gbc_1 = new GridBagConstraints();
				gbc_1.fill = GridBagConstraints.HORIZONTAL;
				gbc_1.insets = new Insets(0, 0, 5, 0);
				gbc_1.gridx = 0;
				gbc_1.gridy = 5;
				categoryPanel.add(adaptiveButton, gbc_1);
				this.listCategoryButtons.add(adaptiveButton);
			}
			{
				AdaptiveButton adaptiveButton = new AdaptiveButton();
				adaptiveButton.setText("Food Category");
				GridBagConstraints gbc_1 = new GridBagConstraints();
				gbc_1.fill = GridBagConstraints.HORIZONTAL;
				gbc_1.insets = new Insets(0, 0, 5, 0);
				gbc_1.gridx = 0;
				gbc_1.gridy = 6;
				categoryPanel.add(adaptiveButton, gbc_1);
				this.listCategoryButtons.add(adaptiveButton);
			}
			{
				AdaptiveButton adaptiveButton = new AdaptiveButton();
				adaptiveButton.setText("Food Category");
				GridBagConstraints gbc_1 = new GridBagConstraints();
				gbc_1.fill = GridBagConstraints.HORIZONTAL;
				gbc_1.insets = new Insets(0, 0, 5, 0);
				gbc_1.gridx = 0;
				gbc_1.gridy = 7;
				categoryPanel.add(adaptiveButton, gbc_1);
				this.listCategoryButtons.add(adaptiveButton);
			}
			{
				AdaptiveButton adaptiveButton = new AdaptiveButton();
				adaptiveButton.setText("Food Category");
				GridBagConstraints gbc_1 = new GridBagConstraints();
				gbc_1.fill = GridBagConstraints.HORIZONTAL;
				gbc_1.insets = new Insets(0, 0, 5, 0);
				gbc_1.gridx = 0;
				gbc_1.gridy = 8;
				categoryPanel.add(adaptiveButton, gbc_1);
				this.listCategoryButtons.add(adaptiveButton);
			}
			{
				AdaptiveButton adaptiveButton = new AdaptiveButton();
				adaptiveButton.setText("Food Category");
				GridBagConstraints gbc_1 = new GridBagConstraints();
				gbc_1.fill = GridBagConstraints.HORIZONTAL;
				gbc_1.insets = new Insets(0, 0, 5, 0);
				gbc_1.gridx = 0;
				gbc_1.gridy = 9;
				categoryPanel.add(adaptiveButton, gbc_1);
				this.listCategoryButtons.add(adaptiveButton);
			}
			{
				AdaptiveButton adaptiveButton = new AdaptiveButton();
				adaptiveButton.setText("Food Category");
				GridBagConstraints gbc_1 = new GridBagConstraints();
				gbc_1.fill = GridBagConstraints.HORIZONTAL;
				gbc_1.insets = new Insets(0, 0, 5, 0);
				gbc_1.gridx = 0;
				gbc_1.gridy = 10;
				categoryPanel.add(adaptiveButton, gbc_1);
				this.listCategoryButtons.add(adaptiveButton);
			}
			{
				AdaptiveButton adaptiveButton = new AdaptiveButton();
				adaptiveButton.setText("Food Category");
				GridBagConstraints gbc_1 = new GridBagConstraints();
				gbc_1.fill = GridBagConstraints.HORIZONTAL;
				gbc_1.gridx = 0;
				gbc_1.gridy = 11;
				categoryPanel.add(adaptiveButton, gbc_1);
				this.listCategoryButtons.add(adaptiveButton);
			}
		}
		{
			AdaptivePanel foodListPanel = new AdaptivePanel();
			foodListPanel.setBorder(BorderFactory.createEmptyBorder());
			GridBagLayout gridBagLayout_1 = new GridBagLayout();
			gridBagLayout_1.columnWidths = new int[]{220, 0};
			gridBagLayout_1.rowHeights = new int[]{0, 10, 0, 0};
			gridBagLayout_1.columnWeights = new double[]{1.0, 1.0E-4};
			gridBagLayout_1.rowWeights = new double[]{1.0, 0.0, 0.0, 1.0E-4};
			foodListPanel.setLayout(gridBagLayout_1);
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.insets = new Insets(0, 0, 0, 5);
			gbc.fill = GridBagConstraints.BOTH;
			gbc.gridx = 3;
			gbc.gridy = 0;
			add(foodListPanel, gbc);
			{
				JScrollPane scrollPane = new JScrollPane();
				GridBagConstraints gbc_1 = new GridBagConstraints();
				gbc_1.insets = new Insets(0, 0, 5, 0);
				gbc_1.fill = GridBagConstraints.BOTH;
				gbc_1.gridx = 0;
				gbc_1.gridy = 0;
				foodListPanel.add(scrollPane, gbc_1);
				{
					foodListing = new AdaptiveList();
					foodListing.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
					scrollPane.setViewportView(foodListing);
				}
			}
			{
				addFoodBut = new AdaptiveButton();
				addFoodBut.setText(Messages.ShoppingList_AddFood_AddItemToShoppingList);
				GridBagConstraints gbc_1 = new GridBagConstraints();
				gbc_1.fill = GridBagConstraints.HORIZONTAL;
				gbc_1.gridx = 0;
				gbc_1.gridy = 2;
				foodListPanel.add(addFoodBut, gbc_1);
			}
		}
	}

	public void getReady(final SubServiceFrame service, String[] foodCategories) {
		// draw food items
		service.foodListing = this.foodListing;
		this.foodListing.addListSelectionListener(service);
		
		//buttons
		service.listCategoryButtons = this.listCategoryButtons;
		service.drawFoodListByCategory(1);
		service.selectFoodListButton(0);
		for (int i=0; i<foodCategories.length; i++) {
			String food = foodCategories[i];
			String foodCatName = food.split("@")[1];
			final String foodID = food.split("@")[0];
			if (foodID.compareTo("13") == 0) //don's show special foods
				break;
			this.listCategoryButtons.get(i).setText(foodCatName);
			final int j=i;
			this.listCategoryButtons.get(i).addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent evt) {
					service.selectFoodListButton(j);
					service.drawFoodListByCategory(Integer.parseInt(foodID));
				}
			});
		}
		
		service.but_addFood = this.addFoodBut;
		this.addFoodBut.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				service.butAddToShoppingList(evt);
			}
		});
//		service.leftSide = this.empty;
//		service.leftPanel = this;
//		this.shoppingListPanel.getReady(service);
	}
	
	public void setLeftContent(AdaptivePanel content) {
//		this.add(content, leftConstraints);
//		this.validate();
//		this.repaint();
	}
}
