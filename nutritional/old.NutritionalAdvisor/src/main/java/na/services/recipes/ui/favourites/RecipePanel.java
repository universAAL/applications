package na.services.recipes.ui.favourites;

import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import java.awt.GridBagConstraints;
import javax.swing.JButton;

import na.services.recipes.RecipesSubServiceLauncher;
import na.utils.OASIS_ServiceUnavailable;
import na.utils.ServiceInterface;
import na.utils.Utils;
import na.widgets.label.AdaptiveLabel;
import na.widgets.panel.AdaptivePanel;


import java.awt.Insets;
import java.net.URL;

@SuppressWarnings("serial")
public class RecipePanel extends AdaptivePanel {
	
	private JButton image;
	private AdaptiveLabel label;
	
	public RecipePanel() {
		this.setBorder(BorderFactory.createEmptyBorder());
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0};
		gridBagLayout.rowHeights = new int[]{0, 10, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 1.0E-4};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 1.0E-4};
		setLayout(gridBagLayout);
		{
			image = new JButton("New button");
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.insets = new Insets(0, 0, 5, 0);
			gbc.gridx = 0;
			gbc.gridy = 0;
			add(image, gbc);
		}
		{
			label = new AdaptiveLabel();
			label.setText("New label");
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.gridx = 0;
			gbc.gridy = 2;
			add(label, gbc);
		}
	}
	
	public void getReady(final RecipesSubServiceLauncher launcher, int recipID,String text) {
		this.label.setText(Utils.Strings.capitalize(text));
		this.image.setText("");
		
		byte[] pictBytes = null;
		try {
			pictBytes = launcher.getDishPicture(recipID);
		} catch (OASIS_ServiceUnavailable e) {
			e.printStackTrace();
		}
		final int recipeID = recipID;
		// Image
		if (pictBytes==null) {
			URL picbfURL = this.getClass().getResource(ServiceInterface.ImageNotAvailable);
			ImageIcon im = new ImageIcon(picbfURL);
			this.image.setIcon(im);
			this.image.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
//					butShowRecipe(evt, recipeID);
				launcher.showRecipe(recipeID);
				}
			});
		} else {
			ImageIcon j = new ImageIcon(pictBytes);
			this.image.setIcon(j);
			this.image.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
//				butShowRecipe(evt, recipeID);
				launcher.showRecipe(recipeID);
				}
			});
			this.image.setSize(j.getIconWidth(), j.getIconHeight());
		}
	}

}
