package na.services.recipes.ui.single;


import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.net.URL;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import na.utils.lang.Messages;
import na.miniDao.Recipe;
import na.services.recipes.RecipesSubServiceLauncher;
import na.utils.OASIS_ServiceUnavailable;
import na.utils.ServiceInterface;
import na.utils.SharedRecipes;
import na.utils.Utils;
import na.widgets.button.AdaptiveButton;
import na.widgets.label.AdaptiveLabel;
import na.widgets.panel.AdaptivePanel;
import na.widgets.textbox.AdaptiveTextBox;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@SuppressWarnings("serial")
public class SingleRecipeWindow extends AdaptivePanel {
	private Log log = LogFactory.getLog(SingleRecipeWindow.class);	
	private RecipesSubServiceLauncher launcher;
	private Recipe recipe;
	private SingleRecipePanel panel;
	protected JButton picture;
	protected AdaptiveButton favouriteButton;
	protected AdaptiveTextBox ingredients;
	protected AdaptiveTextBox procedure;
	protected AdaptiveLabel recipeTitle;
	protected AdaptiveLabel extraInfo;
	protected AdaptiveButton but_readRecipe;
	protected JLabel errorLabel;
	protected AdaptiveButton share_button;
	protected JButton img_favourite;
	protected JButton img_share;
	
	public SingleRecipeWindow() {
		this.setBorder(BorderFactory.createEmptyBorder());
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{200, 0};
		gridBagLayout.rowHeights = new int[]{0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 1.0E-4};
		gridBagLayout.rowWeights = new double[]{1.0, 1.0E-4};
		setLayout(gridBagLayout);
		{
			this.panel = new SingleRecipePanel();
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.fill = GridBagConstraints.BOTH;
			gbc.gridx = 0;
			gbc.gridy = 0;
			add(this.panel, gbc);
		}
	}

	public RecipesSubServiceLauncher getLauncher() {
		return launcher;
	}

	public void setLauncher(RecipesSubServiceLauncher launcher) {
		this.launcher = launcher;
//		this.panel.setWindow(this);
	}

	public void setRecipe(Recipe recipe) {
		this.recipe = recipe;
	}

	public void drawContent() {
		this.panel.prepare(this);
		
		log.info("Dibujando receta...");
		// Name
		this.recipeTitle.setText(Utils.Strings.Max40Chars(Utils.Strings.capitalize(this.recipe.getCourse())));
		
		// Ingredients
		String ingredient_list = "";
		if (recipe.getRecipeIngredients()!=null) {
			for (int i=0; i<recipe.getRecipeIngredients().length; i++) {
				ingredient_list += recipe.getRecipeIngredients()[i].getQuantity() +
				" "+ recipe.getRecipeIngredients()[i].getMeasureDescription() +
				" "+ recipe.getRecipeIngredients()[i].getDescription() + "\n";
			}
			this.ingredients.setText(ingredient_list);
		}
		
		// Procedure
		this.procedure.setText(Utils.Strings.capitalize(this.recipe.getProcedure()));
		
		// Picture
//		byte[] pictBytes = recipe.getMultimediaBytes();
		byte[] pictBytes = null;
		try {
			pictBytes = this.launcher.getDishPicture(recipe.getRecipeID());
		} catch (OASIS_ServiceUnavailable e) {
			e.printStackTrace();
			log.error("picture not downloaded, service unavailable");
		}
		if (pictBytes==null) {
			log.info("no se encontro imagen");
			URL picbfURL = this.getClass().getResource(ServiceInterface.ImageNotAvailable);
			ImageIcon i = new ImageIcon(picbfURL);
			this.picture.setIcon(i);
			this.picture.setSize(237, 159);
		} else {
			ImageIcon j = new ImageIcon(pictBytes);
			this.picture.setIcon(j);
			this.picture.setSize(j.getIconWidth(), j.getIconHeight());
		}
		
//		add to favorites button
		final int id = recipe.getRecipeID();
		final String name = recipe.getCourse();
		if (recipe.isFavouriteRecipe()) {
			URL picURL = this.getClass().getResource("favorite_icon.png");
			ImageIcon image = new ImageIcon(picURL);
			img_favourite.setSize(image.getIconWidth(), image.getIconHeight());
			img_favourite.setIcon(image);
			this.extraInfo.setText(Messages.Recipes_AlreadyFavorite);
			this.extraInfo.setName(Messages.Recipes_MyFavorite);
			
			this.favouriteButton.setText(Messages.Recipes_RemoveFromFavorites);
			this.favouriteButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent evt) {
					butRemoveFromFavorites(evt, id, name);
				}
			});
		} else {
			this.favouriteButton.setText(Messages.Recipes_AddToFavorites);
			this.favouriteButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent evt) {
					butAddToFavorites(evt, id, name);
				}
			});
		}
		
		if (recipe.getProcedure()!=null && recipe.getProcedure().length()>0) {
			this.but_readRecipe.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent evt) {
					butReadRecipeClicked(evt, recipe.getProcedure());
				}
			});
		} else {
			this.but_readRecipe.setVisible(false);
		}
		
		// share with social community button
		if (this.recipeAlreadyShared(recipe.getRecipeID()) == true) {
			log.info("Recipe already shared");
			URL picURL = this.getClass().getResource("share_icon.png");
			ImageIcon image = new ImageIcon(picURL);
			img_share.setSize(image.getIconWidth(), image.getIconHeight());
			img_share.setIcon(image);
			
			this.share_button.setText(Messages.Recipes_Shared_Already);
			this.share_button.setEnabled(false);
		} else {
			log.info("show button: share recipe");
			this.share_button.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent evt) {
					butShareRecipeClicked(evt, recipe.getRecipeID(), recipe.getCourse(), recipe.getProcedure());
				}
			});
		}
	}
	
	private boolean recipeAlreadyShared(int recipeID) {
		return SharedRecipes.isShared(recipeID);
	}
	
	private void butShareRecipeClicked(java.awt.event.ActionEvent evt, int recipeID, String course, String procedure){
//		log.info("click!!!");
		final int result = Utils.showPopUp(Messages.Recipes_Share_PopUp_Title, Messages.Recipes_Share_PopUp_Message , JOptionPane.YES_NO_OPTION, this);
		if (result == 0) {
			try {
				if (this.launcher.shareRecipe(recipeID, course, procedure)) {
					SharedRecipes.addRecipe(recipeID);
					this.share_button.setText(Messages.Recipes_Shared_Already);
					
					URL picURL = this.getClass().getResource("share_icon.png");
					ImageIcon image = new ImageIcon(picURL);
					img_share.setSize(image.getIconWidth(), image.getIconHeight());
					img_share.setIcon(image);
				}
			} catch (OASIS_ServiceUnavailable e) {
				e.printStackTrace();
				Utils.Errors.showErrorPopup("There was an error");
			}
		}
	}

	private void butRemoveFromFavorites(java.awt.event.ActionEvent evt, final int recipeID, final String name) {
		final int result = Utils.showPopUp(Messages.Recipes_RemoveFavourite_PopUp_Title, Messages.Recipes_RemoveFavourite_PopUp_Message , JOptionPane.YES_NO_OPTION, this);
		if (result == 0) {
			try {
				log.info("======> Removing recipe from favorites: " + recipeID);
				if (launcher.removeRecipeFromFavorites(recipeID)) {
					URL picURL = this.getClass().getResource("not_favorite_icon.png");
					ImageIcon image = new ImageIcon(picURL);
					img_favourite.setSize(image.getIconWidth(), image.getIconHeight());
					img_favourite.setIcon(image);
					((AdaptiveButton)evt.getSource()).setText(Messages.Recipes_AddToFavorites);
					this.extraInfo.setText("");
					((AdaptiveButton)evt.getSource()).addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent evt) {
							butAddToFavorites(evt, recipeID, name);
						}
					});
				}
			} catch (OASIS_ServiceUnavailable e) {
				e.printStackTrace();
				Utils.Errors.showErrorPopup("There was an error");
			}
		}
	}
	
	private void butAddToFavorites(java.awt.event.ActionEvent evt, int recipeID, String name) {
		final int result = Utils.showPopUp(Messages.Recipes_AddFavourite_PopUp_Title, Messages.Recipes_AddFavourite_PopUp_Message , JOptionPane.YES_NO_OPTION, this);
		if (result == 0) {
			try {
				log.info("======> Adding recipe to favorites: " + recipeID);
				if (launcher.addRecipeToFavorites(recipeID, name)) {
					((AdaptiveButton)evt.getSource()).setText(Messages.Recipes_MyFavorite);
					URL picURL = this.getClass().getResource("favorite_icon.png");
					ImageIcon image = new ImageIcon(picURL);
					img_favourite.setSize(image.getIconWidth(), image.getIconHeight());
					img_favourite.setIcon(image);
				}
			} catch (OASIS_ServiceUnavailable e) {
				e.printStackTrace();
				Utils.Errors.showErrorPopup("There was an error");
			}
		}
	}
	
	private void butReadRecipeClicked(java.awt.event.ActionEvent evt, String recipeProcedure) {
//		if (MultitelHandler.isSpeechEngineActivate()) {
//			this.errorLabel.setVisible(false);
//			this.errorLabel.setText("");
//			MultitelHandler.talk(recipeProcedure);
//		} else {
//			this.errorLabel.setText(Messages.Recipes_ActivateSpeechEngineFirst);
//			this.errorLabel.setVisible(true);
//		}
		System.out.println("Not available");	
	}
}
