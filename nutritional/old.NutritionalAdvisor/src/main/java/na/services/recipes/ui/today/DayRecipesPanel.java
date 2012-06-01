package na.services.recipes.ui.today;


import na.services.recipes.RecipesSubServiceLauncher;
import na.utils.ServiceInterface;
import na.widgets.label.AdaptiveLabel;
import na.widgets.panel.AdaptivePanel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;



import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;

@SuppressWarnings("serial") 
class DayRecipesPanel extends AdaptivePanel {
	@SuppressWarnings("unused")
	private Log log = LogFactory.getLog(DayRecipesPanel.class);
	protected DayRecipesWindow dayRecipeWindow = null;
	private RecipesPanel firstPanel;
	private RecipesPanel secondPanel;
	private RecipesPanel thirdPanel;
	private List<AdaptiveLabel> mealCatLabels = new ArrayList<AdaptiveLabel>();
	
	public DayRecipesPanel() {
		GridBagLayout gridBagLayout_1 = new GridBagLayout();
		gridBagLayout_1.columnWidths = new int[]{500, 0};
		gridBagLayout_1.rowHeights = new int[]{40, 200, 30, 200, 30, 200, 0};
		gridBagLayout_1.columnWeights = new double[]{1.0, 1.0E-4};
		gridBagLayout_1.rowWeights = new double[]{0.0, 1.0, 0.0, 1.0, 0.0, 1.0, 1.0E-4};
		setLayout(gridBagLayout_1);
		{
//			JLabel label = new JLabel(Messages.Recipes_title_Today_lunch);
			AdaptiveLabel label = new AdaptiveLabel();
			label.setFunction(ServiceInterface.Function_boldLabel);
			label.adapt();
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.anchor = GridBagConstraints.BELOW_BASELINE;
			gbc.fill = GridBagConstraints.HORIZONTAL;
			gbc.insets = new Insets(0, 0, 5, 0);
			gbc.gridx = 0;
			gbc.gridy = 0;
			add(label, gbc);
			this.mealCatLabels.add(label);
		}
		{
			firstPanel = new RecipesPanel();
			firstPanel.setBorder(BorderFactory.createEmptyBorder());
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.insets = new Insets(0, 0, 5, 0);
			gbc.fill = GridBagConstraints.BOTH;
			gbc.gridx = 0;
			gbc.gridy = 1;
			add(firstPanel, gbc);
		}
		{
//			JLabel label = new JLabel(Messages.Recipes_title_Today_dinner);
			AdaptiveLabel label = new AdaptiveLabel();
			label.setFunction(ServiceInterface.Function_boldLabel);
			label.adapt();
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.fill = GridBagConstraints.HORIZONTAL;
			gbc.insets = new Insets(0, 0, 5, 0);
			gbc.gridx = 0;
			gbc.gridy = 2;
			add(label, gbc);
			this.mealCatLabels.add(label);
		}
		{
			secondPanel = new RecipesPanel();
			secondPanel.setBorder(BorderFactory.createEmptyBorder());
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.insets = new Insets(0, 0, 5, 0);
			gbc.fill = GridBagConstraints.BOTH;
			gbc.gridx = 0;
			gbc.gridy = 3;
			add(secondPanel, gbc);
		}
		{
//			JLabel label = new JLabel(Messages.Recipes_title_Today_breakfast);
			AdaptiveLabel label = new AdaptiveLabel();
			label.setFunction(ServiceInterface.Function_boldLabel);
			label.adapt();
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.fill = GridBagConstraints.HORIZONTAL;
			gbc.insets = new Insets(0, 0, 5, 0);
			gbc.gridx = 0;
			gbc.gridy = 4;
			add(label, gbc);
			this.mealCatLabels.add(label);
		}
		{
//			JLabel label = new JLabel("New label");
//			GridBagConstraints gbc = new GridBagConstraints();
//			gbc.gridx = 0;
//			gbc.gridy = 5;
//			add(label, gbc);
			thirdPanel = new RecipesPanel();
			thirdPanel.setBorder(BorderFactory.createEmptyBorder());
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.insets = new Insets(0, 0, 5, 0);
			gbc.fill = GridBagConstraints.BOTH;
			gbc.gridx = 0;
			gbc.gridy = 5;
			add(thirdPanel, gbc);
		}
	}
	
	private na.miniDao.DayMenu dayMenu = null;
//	private AdaptivePanel mainPanel;
	private RecipesSubServiceLauncher launcher;
	
	public RecipesSubServiceLauncher getLauncher() {
		return launcher;
	}

	public void setLauncher(RecipesSubServiceLauncher launcher) {
		this.launcher = launcher;
	}

	public void setDayMenu(na.miniDao.DayMenu dayMenu) {
		this.dayMenu = dayMenu;
	}

	public na.miniDao.DayMenu getDayMenu() {
		return dayMenu;
	}

	protected void drawContent() {
		firstPanel.dayRecipesWindow = this.dayRecipeWindow;
		secondPanel.dayRecipesWindow = this.dayRecipeWindow;
		thirdPanel.dayRecipesWindow = this.dayRecipeWindow;
		
		this.firstPanel.drawContent();
		this.secondPanel.drawContent();
		this.thirdPanel.drawContent();
		
		this.dayRecipeWindow.mealCatLabels = this.mealCatLabels;
	}
	
	
//	private void butShowRecipe(java.awt.event.ActionEvent evt, int recipeID) {
//		log.info("Showing recipe ID: "+recipeID);
//		launcher.showRecipe(recipeID);
//    }

	public void setWindow(DayRecipesWindow dayRecipesWindow) {
		this.dayRecipeWindow = dayRecipesWindow;
		
	}
	

}
