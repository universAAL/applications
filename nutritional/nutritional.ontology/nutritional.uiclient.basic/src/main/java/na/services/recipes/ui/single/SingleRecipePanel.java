package na.services.recipes.ui.single;


import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

import na.utils.lang.Messages;
import na.services.recipes.ui.CustomFrame;
import na.utils.ButtonLab;
import na.utils.ServiceInterface;
import na.widgets.button.AdaptiveButton;
import na.widgets.label.AdaptiveLabel;
import na.widgets.panel.AdaptivePanel;
import na.widgets.textbox.AdaptiveTextBox;


@SuppressWarnings("serial") 
class SingleRecipePanel extends AdaptivePanel {
	
	private SingleRecipeWindow window;
	private AdaptiveLabel recipeTitle;
	private na.widgets.textbox.AdaptiveTextBox ingredients;
	private na.widgets.textbox.AdaptiveTextBox procedure;
	private PictShareFavouritePanel panel;
	private AdaptiveButton btnReadRecipe;
	private JLabel errorLabel;
	
	public SingleRecipePanel() {
		this.setBorder(BorderFactory.createEmptyBorder());
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{250, 100, 100, 0};
		gridBagLayout.rowHeights = new int[]{5, 30, 20, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 1.0, 1.0, 1.0E-4};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 1.0, 1.0E-4};
		setLayout(gridBagLayout);
		{
			CustomFrame cf = new CustomFrame();
			recipeTitle = new AdaptiveLabel();
			recipeTitle.setText("Title");
			recipeTitle.setFunction(ServiceInterface.Function_boldLabel);
			recipeTitle.adapt();
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.insets = new Insets(0, 0, 5, 5);
			gbc.gridx = 0;
			gbc.gridy = 1;
			cf.setLabel(recipeTitle);
			add(cf, gbc);
		}
		{
			CustomFrame cf = new CustomFrame();
			AdaptiveLabel l = new AdaptiveLabel();
			l.setText(Messages.Recipes_Ingredients);
			l.setFunction(ServiceInterface.Function_boldLabel);
			l.adapt();
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.insets = new Insets(0, 0, 5, 5);
			gbc.gridx = 1;
			gbc.gridy = 1;
			cf.setLabel(l);
			add(cf, gbc);
		}
		{
			CustomFrame cf = new CustomFrame();
			AdaptiveLabel lblTtitle = new AdaptiveLabel();
			lblTtitle.setText(Messages.Recipes_Preparation);
			lblTtitle.setFunction(ServiceInterface.Function_boldLabel);
			lblTtitle.adapt();
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.insets = new Insets(0, 0, 5, 0);
			gbc.gridx = 2;
			gbc.gridy = 1;
			cf.setLabel(lblTtitle);
			add(cf, gbc);
		}
		
		{
			panel = new PictShareFavouritePanel();
			GridBagConstraints gbc_2 = new GridBagConstraints();
			gbc_2.insets = new Insets(0, 0, 0, 5);
			gbc_2.fill = GridBagConstraints.BOTH;
			gbc_2.gridx = 0;
			gbc_2.gridy = 2;
			add(panel, gbc_2);
		}
				
		{
			ingredients = new na.widgets.textbox.AdaptiveTextBox();
			ingredients.setText("");
			ingredients.setLineWrap(true);
			ingredients.setWrapStyleWord(true);
			ingredients.setEditable(false);
			ingredients.setRows(25);
			ingredients.setAlignmentY(AdaptiveTextBox.TOP_ALIGNMENT);
			ingredients.setSize(250, 300);
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.anchor = GridBagConstraints.NORTH;
//			gbc.fill = GridBagConstraints.VERTICAL;
			gbc.insets = new Insets(0, 0, 0, 5);
			gbc.gridx = 1;
			gbc.gridy = 2;
			add(ingredients, gbc);
		}
		
		{
			AdaptivePanel ingredPanel = new AdaptivePanel();
			ingredPanel.setBorder(BorderFactory.createEmptyBorder());
			GridBagLayout gridBagLayout_1 = new GridBagLayout();
			gridBagLayout_1.columnWidths = new int[]{0, 0};
			gridBagLayout_1.rowHeights = new int[]{0, 0, 0, 0};
			gridBagLayout_1.columnWeights = new double[]{1.0, 1.0E-4};
			gridBagLayout_1.rowWeights = new double[]{1.0, 0.0, 1.0, 1.0E-4};
			ingredPanel.setLayout(gridBagLayout_1);

			GridBagConstraints gbc_1 = new GridBagConstraints();
			gbc_1.fill = GridBagConstraints.VERTICAL;
			gbc_1.gridx = 2;
			gbc_1.gridy = 2;
			add(ingredPanel, gbc_1);
			
			procedure = new na.widgets.textbox.AdaptiveTextBox();
			procedure.setText("");
			procedure.setLineWrap(true);
			procedure.setSize(250, 300);
			procedure.setRows(25);
			procedure.setEditable(false);
			procedure.setWrapStyleWord(true);
			procedure.setAlignmentY(AdaptiveTextBox.TOP_ALIGNMENT);
//			JLabel label = new JLabel("New label");
			GridBagConstraints gbc_2 = new GridBagConstraints();
			gbc_2.anchor = GridBagConstraints.NORTH;
			gbc_2.insets = new Insets(0, 0, 5, 0);
			gbc_2.gridx = 0;
			gbc_2.gridy = 0;
			ingredPanel.add(procedure, gbc_2);
			
			btnReadRecipe = new AdaptiveButton();
			btnReadRecipe.setText(Messages.Recipes_ReadProcedure);
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.insets = new Insets(0, 0, 5, 0);
			gbc.gridx = 0;
			gbc.gridy = 1;
			ingredPanel.add(btnReadRecipe, gbc);
			ButtonLab.getInstance().addObject(btnReadRecipe, ButtonLab.recipes_Read);
			
			errorLabel = new AdaptiveLabel();
			errorLabel.setForeground(Color.RED);
			GridBagConstraints gbc_3 = new GridBagConstraints();
			gbc_3.gridx = 0;
			gbc_3.gridy = 2;
			ingredPanel.add(errorLabel, gbc_3);
		}
	}

	
	protected void prepare(SingleRecipeWindow singleRecipeWindow) {
		this.window = singleRecipeWindow;
		this.panel.prepare(this.window);
		
		this.window.recipeTitle = this.recipeTitle;
		this.window.procedure = this.procedure;
		this.window.ingredients = this.ingredients;
		this.window.but_readRecipe = this.btnReadRecipe;
		this.window.errorLabel = this.errorLabel;
	}

}
