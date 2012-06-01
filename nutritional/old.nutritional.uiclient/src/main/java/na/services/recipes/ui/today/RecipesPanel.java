package na.services.recipes.ui.today;

import na.services.recipes.RecipesSubServiceLauncher;
import na.widgets.label.AdaptiveLabel;
import na.widgets.panel.AdaptivePanel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;



import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;


@SuppressWarnings("serial") 
class RecipesPanel extends AdaptivePanel {
	private Log log = LogFactory.getLog(RecipesPanel.class);
	protected DayRecipesWindow dayRecipesWindow;
	private List<JButton> imgButtons;
	 private List<AdaptiveLabel> labels;
	
	public RecipesPanel() {
		GridBagLayout gridBagLayout_1 = new GridBagLayout();
		gridBagLayout_1.columnWidths = new int[]{100, 100, 100, 10, 0};
		gridBagLayout_1.rowHeights = new int[]{159, 30, 0};
		gridBagLayout_1.columnWeights = new double[]{1.0, 1.0, 1.0, 1.0, 1.0E-4};
		gridBagLayout_1.rowWeights = new double[]{1.0, 0.0, 1.0E-4};
		setLayout(gridBagLayout_1);
		this.imgButtons = new ArrayList<JButton>();
		labels = new ArrayList<AdaptiveLabel>();
		
		{
			JButton button = new JButton();
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.insets = new Insets(0, 0, 5, 5);
			gbc.gridx = 0;
			gbc.gridy = 0;
			add(button, gbc);
			this.imgButtons.add(button);
		}
		{
			JButton button = new JButton();
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.insets = new Insets(0, 0, 5, 5);
			gbc.gridx = 1;
			gbc.gridy = 0;
			add(button, gbc);
			this.imgButtons.add(button);
		}
		{
			JButton button = new JButton();
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.insets = new Insets(0, 0, 5, 5);
			gbc.gridx = 2;
			gbc.gridy = 0;
			add(button, gbc);
			this.imgButtons.add(button);
		}
		{
			JButton button = new JButton();
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.insets = new Insets(0, 0, 5, 0);
			gbc.gridx = 3;
			gbc.gridy = 0;
			add(button, gbc);
			this.imgButtons.add(button);
		}
		{
			AdaptiveLabel label = new AdaptiveLabel();
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.insets = new Insets(0, 0, 0, 5);
			gbc.gridx = 0;
			gbc.gridy = 1;
			add(label, gbc);
			this.labels.add(label);
		}
		{
			AdaptiveLabel label = new AdaptiveLabel();
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.insets = new Insets(0, 0, 0, 5);
			gbc.gridx = 1;
			gbc.gridy = 1;
			add(label, gbc);
			this.labels.add(label);
		}
		{
			AdaptiveLabel label = new AdaptiveLabel();
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.insets = new Insets(0, 0, 0, 5);
			gbc.gridx = 2;
			gbc.gridy = 1;
			add(label, gbc);
			this.labels.add(label);
		}
		{
			AdaptiveLabel label = new AdaptiveLabel();
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.gridx = 3;
			gbc.gridy = 1;
			add(label, gbc);
			this.labels.add(label);
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

	public void drawContent() {
		for (JButton but : this.imgButtons) {
			this.dayRecipesWindow.imgButtons.add(but);
		}
//		this.dayRecipesWindow.imgButtons = this.imgButtons;
		for (AdaptiveLabel label : this.labels) {
			this.dayRecipesWindow.mealLabels.add(label);
		}
	}
	
	
//	private void butShowRecipe(java.awt.event.ActionEvent evt, int recipeID) {
//		log.info("Showing recipe ID: "+recipeID);
//		launcher.showRecipe(recipeID);
//    }
	

}
