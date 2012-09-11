package na.services.recipes.ui;


import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.GridLayout;


import na.miniDao.Recipe;
import na.services.recipes.RecipesSubServiceLauncher;
import na.services.recipes.ui.favourites.RecipePanel;
import na.widgets.button.AdaptiveButton;
import na.widgets.panel.AdaptivePanel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@SuppressWarnings("serial")
public class SubServiceFrame extends AdaptivePanel {
	private Log log = LogFactory.getLog(SubServiceFrame.class);	
	private static final int NUM_RECIPES_PER_PAGE = 6;
	private RecipesSubServiceLauncher launcher;
	private Menu secNavBar;
	public AdaptivePanel content;
	public AdaptivePanel favourites_panel;
	public AdaptiveButton but_more;
	public AdaptiveButton but_previoues;
	
	private int fav_recipe_pos = 0;
	public Recipe[] recipes;
	
	public SubServiceFrame(RecipesSubServiceLauncher launch) {
		this.launcher = launch;
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{175, 100, 0};
		gridBagLayout.rowHeights = new int[]{20, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 1.0, 1.0E-4};
		gridBagLayout.rowWeights = new double[]{1.0, 1.0E-4};
		setLayout(gridBagLayout);
		{
			this.secNavBar = new Menu();
			secNavBar.launcher = this.launcher;
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.insets = new Insets(0, 0, 0, 5);
			gbc.fill = GridBagConstraints.BOTH;
			gbc.gridx = 0;
			gbc.gridy = 0;
			add(secNavBar, gbc);
		}
		{
			this.content = new AdaptivePanel();
			content.setLayout(new GridLayout(1, 0, 0, 0));
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.fill = GridBagConstraints.BOTH;
			gbc.gridx = 1;
			gbc.gridy = 0;
			add(content, gbc);
		}
	}

	public void drawMyFavourites() {
		//previous
		if (this.fav_recipe_pos<=0) {
			this.but_previoues.setVisible(false);
		} else {
			this.but_previoues.setVisible(true);
		}
		
		//more
		if ((this.fav_recipe_pos+NUM_RECIPES_PER_PAGE)>=this.recipes.length) {
			this.but_more.setVisible(false);
		} else {
			this.but_more.setVisible(true);
		}
		
		this.favourites_panel.removeAll();
		for (int i=this.fav_recipe_pos; i< (this.fav_recipe_pos+NUM_RECIPES_PER_PAGE) && i<this.recipes.length; i++) {
			if (this.recipes[i]!=null) {
				Recipe recipe = this.recipes[i]; 
				RecipePanel r = new RecipePanel();
				r.getReady(launcher, recipe.getRecipeID(), recipe.getCourse());
				this.favourites_panel.add(r);
			}
		}
		
		this.content.validate();
		this.content.repaint();
	}
	
	public void but_MoreFavouritesClicked() {
		log.info("morrr");
		this.fav_recipe_pos += NUM_RECIPES_PER_PAGE;
		this.drawMyFavourites();
	}
	
	public void but_PreviousFavouritesClicked() {
		this.fav_recipe_pos -= NUM_RECIPES_PER_PAGE;
		this.drawMyFavourites();
	}
	
	

}
