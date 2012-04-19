package na.services.shoppinglist.ui.goingShop;



import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;


import java.awt.Insets;

import javax.swing.BorderFactory;

import na.services.shoppinglist.ui.SubServiceFrame;
import na.services.shoppinglist.ui.shoppingList.ShoppingListFrame;
import na.widgets.panel.AdaptivePanel;

@SuppressWarnings("serial")
public class ShoppingMainPanel extends AdaptivePanel {
	
//	protected ShoppingWindow dayRecipeWindow = null;
	private ShoppingListFrame shoppingListPanel;
//	ActionsWindow actionsPanel;
	private AdaptivePanel empty;
	private GridBagConstraints leftConstraints;
	
	public ShoppingMainPanel() {
		GridBagLayout gridBagLayout_1 = new GridBagLayout();
		gridBagLayout_1.columnWidths = new int[]{400, 300, 0};
		gridBagLayout_1.rowHeights = new int[]{200, 0};
		gridBagLayout_1.columnWeights = new double[]{0.0, 0.0, 1.0E-4};
		gridBagLayout_1.rowWeights = new double[]{1.0, 1.0E-4};
		setLayout(gridBagLayout_1);
		{
			empty = new AdaptivePanel();
			empty.setBorder(BorderFactory.createEmptyBorder());
//			actionsPanel = new ActionsWindow();
			leftConstraints = new GridBagConstraints();
			leftConstraints.anchor = GridBagConstraints.WEST;
			leftConstraints.insets = new Insets(0, 0, 0, 5);
			leftConstraints.fill = GridBagConstraints.VERTICAL;
			leftConstraints.gridx = 0;
			leftConstraints.gridy = 0;
			add(empty, leftConstraints);
		}
		{
			shoppingListPanel = new ShoppingListFrame();
//			shoppingListPanel.setBorder(BorderFactory.createEmptyBorder());
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.anchor = GridBagConstraints.WEST;
			gbc.fill = GridBagConstraints.VERTICAL;
			gbc.gridx = 1;
			gbc.gridy = 0;
			add(shoppingListPanel, gbc);
		}
	}
	
	protected void getReady(SubServiceFrame service) {
		service.leftSide = this.empty;
		service.leftPanel = this;
		this.shoppingListPanel.getReady(service);
	}
	
	public void setLeftContent(AdaptivePanel content) {
		this.add(content, leftConstraints);
		this.validate();
		this.repaint();
	}

}
