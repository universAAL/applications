package na.services.recipes.ui.single;

import java.awt.GridBagLayout;


import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;

import na.utils.lang.Messages;
import na.widgets.button.AdaptiveButton;
import na.widgets.label.AdaptiveLabel;
import na.widgets.panel.AdaptivePanel;


import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.net.URL;

@SuppressWarnings("serial") 
class PictShareFavouritePanel extends AdaptivePanel {
	
	private SingleRecipeWindow window;
	private JButton picture;
	private AdaptiveLabel extraInfo;
	private AdaptivePanel panel;
	private JButton img_favourite;
	private AdaptiveButton but_favourite;
	private JButton img_share;
	private AdaptiveButton but_share;
	
	public PictShareFavouritePanel() {
		this.setBorder(BorderFactory.createEmptyBorder());
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{100, 0};
		gridBagLayout.rowHeights = new int[]{5, 159, 100, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 1.0E-4};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 1.0, 0.0, 1.0E-4};
		setLayout(gridBagLayout);
		{
			picture = new JButton("");
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.insets = new Insets(0, 0, 5, 0);
			gbc.gridx = 0;
			gbc.gridy = 1;
			add(picture, gbc);
		}
		{
			extraInfo = new AdaptiveLabel();
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.insets = new Insets(0, 0, 5, 0);
			gbc.gridx = 0;
			gbc.gridy = 2;
			add(extraInfo, gbc);
		}
		{
			panel = new AdaptivePanel();
			panel.setBorder(BorderFactory.createEmptyBorder());
			GridBagLayout gridBagLayout_1 = new GridBagLayout();
			gridBagLayout_1.columnWidths = new int[]{5, 0, 0, 5, 0};
			gridBagLayout_1.rowHeights = new int[]{0, 20, 0, 0};
			gridBagLayout_1.columnWeights = new double[]{1.0, 0.0, 0.0, 1.0, 1.0E-4};
			gridBagLayout_1.rowWeights = new double[]{0.0, 0.0, 0.0, 1.0E-4};
			panel.setLayout(gridBagLayout_1);
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.insets = new Insets(0, 0, 5, 0);
			gbc.fill = GridBagConstraints.BOTH;
			gbc.gridx = 0;
			gbc.gridy = 3;
			add(panel, gbc);
			{
				img_favourite = new JButton("");
				URL picURL = this.getClass().getResource("not_favorite_icon.png");
				ImageIcon image = new ImageIcon(picURL);
				img_favourite.setSize(image.getIconWidth(), image.getIconHeight());
				img_favourite.setIcon(image);
				GridBagConstraints gbc_1 = new GridBagConstraints();
				gbc_1.insets = new Insets(0, 0, 5, 5);
				gbc_1.gridx = 1;
				gbc_1.gridy = 0;
				panel.add(img_favourite, gbc_1);
			}
			{
				but_favourite = new AdaptiveButton();
				but_favourite.setText("My Favourite");
				GridBagConstraints gbc_1 = new GridBagConstraints();
				gbc_1.fill = GridBagConstraints.HORIZONTAL;
				gbc_1.insets = new Insets(0, 0, 5, 5);
				gbc_1.gridx = 2;
				gbc_1.gridy = 0;
				panel.add(but_favourite, gbc_1);
			}
			{
				img_share = new JButton("");
				URL picURL = this.getClass().getResource("not_shared_icon.png");
				ImageIcon image = new ImageIcon(picURL);
				img_share.setSize(image.getIconWidth(), image.getIconHeight());
				img_share.setIcon(image);
				GridBagConstraints gbc_1 = new GridBagConstraints();
				gbc_1.insets = new Insets(0, 0, 0, 5);
				gbc_1.gridx = 1;
				gbc_1.gridy = 2;
				panel.add(img_share, gbc_1);
			}
			{
				but_share = new AdaptiveButton();
				but_share.setText(Messages.Recipes_Share);
				GridBagConstraints gbc_1 = new GridBagConstraints();
				gbc_1.fill = GridBagConstraints.HORIZONTAL;
				gbc_1.insets = new Insets(0, 0, 0, 5);
				gbc_1.gridx = 2;
				gbc_1.gridy = 2;
				panel.add(but_share, gbc_1);
			}
		}
	}
	
	protected void prepare(SingleRecipeWindow singleRecipeWindow) {
		this.window = singleRecipeWindow;
		
		this.window.favouriteButton = this.but_favourite;
		this.window.picture = this.picture;
		this.window.extraInfo = this.extraInfo;
		this.window.share_button = this.but_share;
		this.window.img_share = this.img_share;
		this.window.img_favourite = this.img_favourite;
	}
}
