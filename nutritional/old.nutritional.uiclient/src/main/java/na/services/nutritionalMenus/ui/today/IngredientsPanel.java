package na.services.nutritionalMenus.ui.today;

import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.List;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import na.widgets.panel.AdaptivePanel;

@SuppressWarnings("serial")
public class IngredientsPanel extends AdaptivePanel {
	
	protected List<GridBagConstraints> dishes = new ArrayList<GridBagConstraints>();
	protected List<GridBagConstraints> seeRecipesButton = new ArrayList<GridBagConstraints>();
	
	public IngredientsPanel() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{150, 90, 0};
		gridBagLayout.rowHeights = new int[]{30, 30, 30, 30, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 0.0, 1.0E-4};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 1.0E-4};
		setLayout(gridBagLayout);
		{
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.insets = new Insets(0, 0, 5, 5);
			gbc.gridx = 0;
			gbc.gridy = 0;
			this.dishes.add(gbc);
		}
		{
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.insets = new Insets(0, 0, 5, 5);
			gbc.gridx = 1;
			gbc.gridy = 0;
			this.seeRecipesButton.add(gbc);
		}
		{
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.insets = new Insets(0, 0, 5, 5);
			gbc.gridx = 0;
			gbc.gridy = 1;
			this.dishes.add(gbc);
		}
		{
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.insets = new Insets(0, 0, 5, 5);
			gbc.gridx = 1;
			gbc.gridy = 1;
			this.seeRecipesButton.add(gbc);
		}
		{
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.insets = new Insets(0, 0, 5, 5);
			gbc.gridx = 0;
			gbc.gridy = 2;
			this.dishes.add(gbc);
		}
		{
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.insets = new Insets(0, 0, 5, 5);
			gbc.gridx = 1;
			gbc.gridy = 2;
			this.seeRecipesButton.add(gbc);
		}
		{
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.insets = new Insets(0, 0, 5, 5);
			gbc.gridx = 0;
			gbc.gridy = 3;
			this.dishes.add(gbc);
		}
		{
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.gridx = 1;
			gbc.gridy = 3;
			this.seeRecipesButton.add(gbc);
		}
	}

}
