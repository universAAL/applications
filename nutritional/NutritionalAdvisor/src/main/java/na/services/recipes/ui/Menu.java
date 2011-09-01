package na.services.recipes.ui;


import na.utils.lang.Messages;
import na.services.recipes.RecipesSubServiceLauncher;
import na.utils.ButtonLab;
import na.widgets.button.AdaptiveButton;
import na.widgets.button.SecMenuButton;
import na.widgets.panel.SecNavigationBar;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

@SuppressWarnings("serial")
public class Menu extends SecNavigationBar {
	private Log log = LogFactory.getLog(Menu.class);	
	private List<SecMenuButton> buttons = new ArrayList<SecMenuButton>();
	protected RecipesSubServiceLauncher launcher;
	
	public Menu() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{100, 0};
		gridBagLayout.rowHeights = new int[]{5, 50, 50, 50, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 1.0E-4};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};
		setLayout(gridBagLayout);
		{
			SecMenuButton button = new SecMenuButton();
			button.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent evt) {
					butTodayClicked(evt);
				}
			});
			ButtonLab.getInstance().addObject(button, ButtonLab.recipes_Today);
			button.setText(Messages.Recipes_Today);
			GridBagConstraints gbc_1 = new GridBagConstraints();
			gbc_1.fill = GridBagConstraints.BOTH;
			gbc_1.insets = new Insets(0, 0, 5, 0);
			gbc_1.gridx = 0;
			gbc_1.gridy = 1;
			add(button, gbc_1);
			this.buttons.add(button);
			this.select(button);
		}
		{
			SecMenuButton button_1 = new SecMenuButton();
			button_1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent evt) {
					butTomorrowClicked(evt);
				}
			});
			button_1.setText(Messages.Recipes_Tomorrow);
			ButtonLab.getInstance().addObject(button_1, ButtonLab.recipes_Tomorrow);
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.fill = GridBagConstraints.BOTH;
			gbc.insets = new Insets(0, 0, 5, 0);
			gbc.gridx = 0;
			gbc.gridy = 2;
			add(button_1, gbc);
			this.buttons.add(button_1);
		}
		{
			SecMenuButton button_1 = new SecMenuButton();
			button_1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent evt) {
					butMyFavoritesClicked(evt);
				}
			});
			button_1.setText(Messages.Recipes_Favourites);
			ButtonLab.getInstance().addObject(button_1, ButtonLab.recipes_Favourites);
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.insets = new Insets(0, 0, 5, 0);
			gbc.fill = GridBagConstraints.BOTH;
			gbc.gridx = 0;
			gbc.gridy = 3;
			add(button_1, gbc);
			this.buttons.add(button_1);
		}
	}

	private void butTomorrowClicked(java.awt.event.ActionEvent evt) {
		this.select((AdaptiveButton)evt.getSource());
		launcher.showTomorrowRecipes();
	}

	private void butTodayClicked(java.awt.event.ActionEvent evt) {
		this.select((AdaptiveButton)evt.getSource());
		launcher.showTodayRecipes();
	}

	private void butMyFavoritesClicked(java.awt.event.ActionEvent evt) {
		log.info("Showing list of Favorites!!");
		this.select((AdaptiveButton)evt.getSource());
		 launcher.showMyFavoriteRecipes();
	}
	
	private void select(AdaptiveButton source) {
		Iterator<SecMenuButton> it = this.buttons.iterator();
		while (it.hasNext()) {
			AdaptiveButton but = it.next();
			but.setSelected(false);
		}
		source.setSelected(true);
	}

}
