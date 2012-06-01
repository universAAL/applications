package na.services.shoppinglist.ui.shoppingList;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.BorderFactory;

import na.utils.lang.Messages;
import na.services.shoppinglist.ui.SubServiceFrame;
import na.utils.ServiceInterface;
import na.widgets.button.AdaptiveButton;
import na.widgets.label.AdaptiveLabel;
import na.widgets.panel.AdaptivePanel;


@SuppressWarnings("serial") 
class ShoppingListPanel extends AdaptivePanel {
	
	private AdaptiveButton btnPrevious;
	private AdaptiveButton btnNext;
	private AdaptiveLabel lblShoppingList;
	private ListItems itemsPanel;
	private AdaptiveLabel lab_info;
	
	public ShoppingListPanel() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0};
		gridBagLayout.rowHeights = new int[]{0, 200, 30, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 1.0E-4};
		gridBagLayout.rowWeights = new double[]{0.0, 1.0, 0.0, 0.0, 1.0E-4};
		setLayout(gridBagLayout);
		{
			lblShoppingList = new AdaptiveLabel();
			lblShoppingList.setText(Messages.ShoppingList_SL_Title);
			lblShoppingList.setFunction(ServiceInterface.Function_boldLabel);
			lblShoppingList.adapt();
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.insets = new Insets(0, 0, 5, 0);
			gbc.gridx = 0;
			gbc.gridy = 0;
			add(lblShoppingList, gbc);
		}
		{
			itemsPanel = new ListItems();
			itemsPanel.setBorder(BorderFactory.createEmptyBorder());
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.insets = new Insets(0, 0, 5, 0);
			gbc.fill = GridBagConstraints.BOTH;
			gbc.gridx = 0;
			gbc.gridy = 1;
			add(itemsPanel, gbc);
		}
		{
			AdaptivePanel buttonsPanel = new AdaptivePanel();
			buttonsPanel.setBorder(BorderFactory.createEmptyBorder());
			GridBagLayout gridBagLayout_1 = new GridBagLayout();
			gridBagLayout_1.columnWidths = new int[]{0, 0, 0, 0};
			gridBagLayout_1.rowHeights = new int[]{0, 0};
			gridBagLayout_1.columnWeights = new double[]{0.0, 1.0, 0.0, 1.0E-4};
			gridBagLayout_1.rowWeights = new double[]{1.0, 1.0E-4};
			buttonsPanel.setLayout(gridBagLayout_1);
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.insets = new Insets(0, 0, 5, 0);
			gbc.fill = GridBagConstraints.BOTH;
			gbc.gridx = 0;
			gbc.gridy = 2;
			add(buttonsPanel, gbc);
			{
				btnPrevious = new AdaptiveButton();
				btnPrevious.setText(Messages.ShoppingList_PreviousItems);
				GridBagConstraints gbc_1 = new GridBagConstraints();
				gbc_1.anchor = GridBagConstraints.WEST;
				gbc_1.insets = new Insets(0, 0, 0, 5);
				gbc_1.gridx = 0;
				gbc_1.gridy = 0;
				buttonsPanel.add(btnPrevious, gbc_1);
			}
			{
				btnNext = new AdaptiveButton();
				btnNext.setText(Messages.ShoppingList_MoreItems);
				GridBagConstraints gbc_1 = new GridBagConstraints();
				gbc_1.anchor = GridBagConstraints.EAST;
				gbc_1.gridx = 2;
				gbc_1.gridy = 0;
				buttonsPanel.add(btnNext, gbc_1);
			}
		}
		{
			lab_info = new AdaptiveLabel();
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.gridx = 0;
			gbc.gridy = 3;
			add(lab_info, gbc);
		}
	}

	protected void getReady(SubServiceFrame service) {
		service.lab_info = this.lab_info;
		service.but_moreItems = this.btnNext;
		service.but_previousItems = this.btnPrevious;
		this.itemsPanel.getReady(service);
	}

}
