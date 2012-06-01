package na.services.shoppinglist.ui.shoppingList;

import java.awt.GridBagLayout;


import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.List;

import na.utils.lang.Messages;
import na.services.shoppinglist.ui.SubServiceFrame;
import na.utils.ServiceInterface;
import na.widgets.checkbox.AdaptiveCheckBox;
import na.widgets.label.AdaptiveLabel;
import na.widgets.panel.AdaptivePanel;

@SuppressWarnings("serial")
class ListItems extends AdaptivePanel {
	
	private List<AdaptiveLabel> listItemsName = new ArrayList<AdaptiveLabel>();
	private List<AdaptiveLabel> listItemsQuantity = new ArrayList<AdaptiveLabel>();
	private List<AdaptiveCheckBox> listItemsAlready = new ArrayList<AdaptiveCheckBox>();
	private List<AdaptiveLabel> listItemsExtra = new ArrayList<AdaptiveLabel>();
	
	public ListItems() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{200, 100, 0, 50, 0};
		gridBagLayout.rowHeights = new int[]{30, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 1.0E-4};
		gridBagLayout.rowWeights = new double[]{0.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0E-4};
		setLayout(gridBagLayout);
		{
			AdaptiveLabel lblItem = new AdaptiveLabel();
			lblItem.setText(Messages.ShoppingList_Item);
			lblItem.setFunction(ServiceInterface.Function_boldLabel);
			lblItem.adapt();
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.anchor = GridBagConstraints.WEST;
			gbc.insets = new Insets(0, 0, 5, 5);
			gbc.gridx = 0;
			gbc.gridy = 0;
			add(lblItem, gbc);
		}
		{
			AdaptiveLabel lblQuantity = new AdaptiveLabel();
			lblQuantity.setText(Messages.ShoppingList_Quantity);
			lblQuantity.setFunction(ServiceInterface.Function_boldLabel);
			lblQuantity.adapt();
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.anchor = GridBagConstraints.WEST;
			gbc.insets = new Insets(0, 0, 5, 5);
			gbc.gridx = 1;
			gbc.gridy = 0;
			add(lblQuantity, gbc);
		}
		{
			AdaptiveLabel dptvlblExtra = new AdaptiveLabel();
			dptvlblExtra.setFunction(ServiceInterface.Function_boldLabel);
			dptvlblExtra.adapt();
			dptvlblExtra.setText("Extra");
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.insets = new Insets(0, 0, 5, 5);
			gbc.gridx = 2;
			gbc.gridy = 0;
			add(dptvlblExtra, gbc);
		}
		{
			AdaptiveLabel lblDelete = new AdaptiveLabel();
			lblDelete.setText(Messages.ShoppingList_AlreadyBoughtIt);
			lblDelete.setFunction(ServiceInterface.Function_boldLabel);
			lblDelete.adapt();
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.insets = new Insets(0, 0, 5, 0);
			gbc.gridx = 3;
			gbc.gridy = 0;
			add(lblDelete, gbc);
		}
		{
			AdaptiveLabel label = new AdaptiveLabel();
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.anchor = GridBagConstraints.WEST;
			gbc.insets = new Insets(0, 0, 5, 5);
			gbc.gridx = 0;
			gbc.gridy = 1;
			add(label, gbc);
			listItemsName.add(label);
		}
		{
			AdaptiveLabel label = new AdaptiveLabel();
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.anchor = GridBagConstraints.WEST;
			gbc.insets = new Insets(0, 0, 5, 5);
			gbc.gridx = 1;
			gbc.gridy = 1;
			add(label, gbc);
			listItemsQuantity.add(label);
		}
		{
			AdaptiveLabel adaptiveLabel = new AdaptiveLabel();
			listItemsExtra.add(adaptiveLabel);
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.insets = new Insets(0, 0, 5, 5);
			gbc.gridx = 2;
			gbc.gridy = 1;
			add(adaptiveLabel, gbc);
		}
		{
			AdaptiveCheckBox checkBox = new AdaptiveCheckBox();
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.insets = new Insets(0, 0, 5, 0);
			gbc.gridx = 3;
			gbc.gridy = 1;
			add(checkBox, gbc);
			listItemsAlready.add(checkBox);
		}
		{
			AdaptiveLabel label = new AdaptiveLabel();
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.anchor = GridBagConstraints.WEST;
			gbc.insets = new Insets(0, 0, 5, 5);
			gbc.gridx = 0;
			gbc.gridy = 2;
			add(label, gbc);
			listItemsName.add(label);
		}
		{
			AdaptiveLabel label = new AdaptiveLabel();
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.anchor = GridBagConstraints.WEST;
			gbc.insets = new Insets(0, 0, 5, 5);
			gbc.gridx = 1;
			gbc.gridy = 2;
			add(label, gbc);
			listItemsQuantity.add(label);
		}
		{
			AdaptiveLabel adaptiveLabel = new AdaptiveLabel(); listItemsExtra.add(adaptiveLabel);
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.insets = new Insets(0, 0, 5, 5);
			gbc.gridx = 2;
			gbc.gridy = 2;
			add(adaptiveLabel, gbc);
		}
		{
			AdaptiveCheckBox checkBox = new AdaptiveCheckBox();
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.insets = new Insets(0, 0, 5, 0);
			gbc.gridx = 3;
			gbc.gridy = 2;
			add(checkBox, gbc);
			listItemsAlready.add(checkBox);
		}
		{
			AdaptiveLabel label = new AdaptiveLabel();
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.anchor = GridBagConstraints.WEST;
			gbc.insets = new Insets(0, 0, 5, 5);
			gbc.gridx = 0;
			gbc.gridy = 3;
			add(label, gbc);
			listItemsName.add(label);
		}
		{
			AdaptiveLabel label = new AdaptiveLabel();
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.anchor = GridBagConstraints.WEST;
			gbc.insets = new Insets(0, 0, 5, 5);
			gbc.gridx = 1;
			gbc.gridy = 3;
			add(label, gbc);
			listItemsQuantity.add(label);
		}
		{
			AdaptiveLabel adaptiveLabel = new AdaptiveLabel(); listItemsExtra.add(adaptiveLabel);
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.insets = new Insets(0, 0, 5, 5);
			gbc.gridx = 2;
			gbc.gridy = 3;
			add(adaptiveLabel, gbc);
		}
		{
			AdaptiveCheckBox checkBox = new AdaptiveCheckBox();
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.insets = new Insets(0, 0, 5, 0);
			gbc.gridx = 3;
			gbc.gridy = 3;
			add(checkBox, gbc);
			listItemsAlready.add(checkBox);
		}
		{
			AdaptiveLabel label = new AdaptiveLabel();
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.anchor = GridBagConstraints.WEST;
			gbc.insets = new Insets(0, 0, 5, 5);
			gbc.gridx = 0;
			gbc.gridy = 4;
			add(label, gbc);
			listItemsName.add(label);
		}
		{
			AdaptiveLabel label = new AdaptiveLabel();
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.anchor = GridBagConstraints.WEST;
			gbc.insets = new Insets(0, 0, 5, 5);
			gbc.gridx = 1;
			gbc.gridy = 4;
			add(label, gbc);
			listItemsQuantity.add(label);
		}
		{
			AdaptiveLabel adaptiveLabel = new AdaptiveLabel(); listItemsExtra.add(adaptiveLabel);
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.insets = new Insets(0, 0, 5, 5);
			gbc.gridx = 2;
			gbc.gridy = 4;
			add(adaptiveLabel, gbc);
		}
		{
			AdaptiveCheckBox checkBox = new AdaptiveCheckBox();
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.insets = new Insets(0, 0, 5, 0);
			gbc.gridx = 3;
			gbc.gridy = 4;
			add(checkBox, gbc);
			listItemsAlready.add(checkBox);
		}
		{
			AdaptiveLabel label = new AdaptiveLabel();
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.anchor = GridBagConstraints.WEST;
			gbc.insets = new Insets(0, 0, 5, 5);
			gbc.gridx = 0;
			gbc.gridy = 5;
			add(label, gbc);
			listItemsName.add(label);
		}
		{
			AdaptiveLabel label = new AdaptiveLabel();
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.anchor = GridBagConstraints.WEST;
			gbc.insets = new Insets(0, 0, 5, 5);
			gbc.gridx = 1;
			gbc.gridy = 5;
			add(label, gbc);
			listItemsQuantity.add(label);
		}
		{
			AdaptiveLabel adaptiveLabel = new AdaptiveLabel(); listItemsExtra.add(adaptiveLabel);
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.insets = new Insets(0, 0, 5, 5);
			gbc.gridx = 2;
			gbc.gridy = 5;
			add(adaptiveLabel, gbc);
		}
		{
			AdaptiveCheckBox checkBox = new AdaptiveCheckBox();
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.insets = new Insets(0, 0, 5, 0);
			gbc.gridx = 3;
			gbc.gridy = 5;
			add(checkBox, gbc);
			listItemsAlready.add(checkBox);
		}
		{
			AdaptiveLabel label = new AdaptiveLabel();
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.anchor = GridBagConstraints.WEST;
			gbc.insets = new Insets(0, 0, 5, 5);
			gbc.gridx = 0;
			gbc.gridy = 6;
			add(label, gbc);
			listItemsName.add(label);
		}
		{
			AdaptiveLabel label = new AdaptiveLabel();
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.anchor = GridBagConstraints.WEST;
			gbc.insets = new Insets(0, 0, 5, 5);
			gbc.gridx = 1;
			gbc.gridy = 6;
			add(label, gbc);
			listItemsQuantity.add(label);
		}
		{
			AdaptiveLabel adaptiveLabel = new AdaptiveLabel(); listItemsExtra.add(adaptiveLabel);
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.insets = new Insets(0, 0, 5, 5);
			gbc.gridx = 2;
			gbc.gridy = 6;
			add(adaptiveLabel, gbc);
		}
		{
			AdaptiveCheckBox checkBox = new AdaptiveCheckBox();
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.insets = new Insets(0, 0, 5, 0);
			gbc.gridx = 3;
			gbc.gridy = 6;
			add(checkBox, gbc);
			listItemsAlready.add(checkBox);
		}
		{
			AdaptiveLabel label = new AdaptiveLabel();
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.anchor = GridBagConstraints.WEST;
			gbc.insets = new Insets(0, 0, 5, 5);
			gbc.gridx = 0;
			gbc.gridy = 7;
			add(label, gbc);
			listItemsName.add(label);
		}
		{
			AdaptiveLabel label = new AdaptiveLabel();
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.anchor = GridBagConstraints.WEST;
			gbc.insets = new Insets(0, 0, 5, 5);
			gbc.gridx = 1;
			gbc.gridy = 7;
			add(label, gbc);
			listItemsQuantity.add(label);
		}
		{
			AdaptiveLabel adaptiveLabel = new AdaptiveLabel(); listItemsExtra.add(adaptiveLabel);
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.insets = new Insets(0, 0, 5, 5);
			gbc.gridx = 2;
			gbc.gridy = 7;
			add(adaptiveLabel, gbc);
		}
		{
			AdaptiveCheckBox checkBox = new AdaptiveCheckBox();
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.insets = new Insets(0, 0, 5, 0);
			gbc.gridx = 3;
			gbc.gridy = 7;
			add(checkBox, gbc);
			listItemsAlready.add(checkBox);
		}
		{
			AdaptiveLabel label = new AdaptiveLabel();
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.anchor = GridBagConstraints.WEST;
			gbc.insets = new Insets(0, 0, 5, 5);
			gbc.gridx = 0;
			gbc.gridy = 8;
			add(label, gbc);
			listItemsName.add(label);
		}
		{
			AdaptiveLabel label = new AdaptiveLabel();
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.anchor = GridBagConstraints.WEST;
			gbc.insets = new Insets(0, 0, 5, 5);
			gbc.gridx = 1;
			gbc.gridy = 8;
			add(label, gbc);
			listItemsQuantity.add(label);
		}
		{
			AdaptiveLabel adaptiveLabel = new AdaptiveLabel(); listItemsExtra.add(adaptiveLabel);
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.insets = new Insets(0, 0, 5, 5);
			gbc.gridx = 2;
			gbc.gridy = 8;
			add(adaptiveLabel, gbc);
		}
		{
			AdaptiveCheckBox checkBox = new AdaptiveCheckBox();
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.insets = new Insets(0, 0, 5, 0);
			gbc.gridx = 3;
			gbc.gridy = 8;
			add(checkBox, gbc);
			listItemsAlready.add(checkBox);
		}
		{
			AdaptiveLabel label = new AdaptiveLabel();
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.anchor = GridBagConstraints.WEST;
			gbc.insets = new Insets(0, 0, 5, 5);
			gbc.gridx = 0;
			gbc.gridy = 9;
			add(label, gbc);
			listItemsName.add(label);
		}
		{
			AdaptiveLabel label = new AdaptiveLabel();
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.anchor = GridBagConstraints.WEST;
			gbc.insets = new Insets(0, 0, 5, 5);
			gbc.gridx = 1;
			gbc.gridy = 9;
			add(label, gbc);
			listItemsQuantity.add(label);
		}
		{
			AdaptiveLabel adaptiveLabel = new AdaptiveLabel(); listItemsExtra.add(adaptiveLabel);
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.insets = new Insets(0, 0, 5, 5);
			gbc.gridx = 2;
			gbc.gridy = 9;
			add(adaptiveLabel, gbc);
		}
		{
			AdaptiveCheckBox checkBox = new AdaptiveCheckBox();
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.insets = new Insets(0, 0, 5, 0);
			gbc.gridx = 3;
			gbc.gridy = 9;
			add(checkBox, gbc);
			listItemsAlready.add(checkBox);
		}
		{
			AdaptiveLabel label = new AdaptiveLabel();
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.anchor = GridBagConstraints.WEST;
			gbc.insets = new Insets(0, 0, 0, 5);
			gbc.gridx = 0;
			gbc.gridy = 10;
			add(label, gbc);
			listItemsName.add(label);
		}
		{
			AdaptiveLabel label = new AdaptiveLabel();
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.anchor = GridBagConstraints.WEST;
			gbc.insets = new Insets(0, 0, 0, 5);
			gbc.gridx = 1;
			gbc.gridy = 10;
			add(label, gbc);
			listItemsQuantity.add(label);
		}
		{
			AdaptiveLabel adaptiveLabel = new AdaptiveLabel(); listItemsExtra.add(adaptiveLabel);
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.insets = new Insets(0, 0, 0, 5);
			gbc.gridx = 2;
			gbc.gridy = 10;
			add(adaptiveLabel, gbc);
		}
		{
			AdaptiveCheckBox checkBox = new AdaptiveCheckBox();
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.gridx = 3;
			gbc.gridy = 10;
			add(checkBox, gbc);
			listItemsAlready.add(checkBox);
		}
	}

	protected void getReady(SubServiceFrame service) {
		service.listItemsName = listItemsName;
		service.listItemsQuantity = this.listItemsQuantity;
		service.listItemsAlready = this.listItemsAlready;
		service.listItemsExtra = this.listItemsExtra;
		for (AdaptiveCheckBox item : this.listItemsAlready) {
//			item.addItemListener(service);
			item.addActionListener(service);
		}
	}

}
