package na.services.shoppinglist.ui.shoppingList;


import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

import javax.swing.BorderFactory;

import na.services.shoppinglist.ui.SubServiceFrame;
import na.widgets.panel.AdaptivePanel;


@SuppressWarnings("serial")
public class ShoppingListFrame extends AdaptivePanel {
	private ShoppingListPanel panel;
	
	public ShoppingListFrame() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{50, 0};
		gridBagLayout.rowHeights = new int[]{50, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 1.0E-4};
		gridBagLayout.rowWeights = new double[]{1.0, 1.0E-4};
		setLayout(gridBagLayout);
		{
			panel = new ShoppingListPanel();
			panel.setBorder(BorderFactory.createEmptyBorder());
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.fill = GridBagConstraints.BOTH;
			gbc.gridx = 0;
			gbc.gridy = 0;
			add(panel, gbc);
		}
	}
	
	
	public void getReady(SubServiceFrame service) {
		this.panel.getReady(service);
	}
	

}
