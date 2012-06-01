package na.services.recipes.ui.favourites;


import java.awt.GridBagLayout;

import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.BorderFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


import na.services.recipes.ui.SubServiceFrame;
import na.utils.lang.Messages;
import na.widgets.button.AdaptiveButton;
import na.widgets.label.AdaptiveLabel;
import na.widgets.panel.AdaptivePanel;
import net.miginfocom.swing.MigLayout;


@SuppressWarnings("serial")
public class FavouritePanel extends AdaptivePanel {
	private Log log = LogFactory.getLog(FavouritePanel.class);
	private static final String NUMBER_OF_ANSWERS_PER_COLUMN = "3";
	private AdaptiveButton but_more;
	private AdaptiveButton but_previous;
	private AdaptiveLabel errorLabel;
	private AdaptivePanel panel_favourites;

	public FavouritePanel() {
		this.setBorder(BorderFactory.createEmptyBorder());
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{15, 0, 15, 0};
		gridBagLayout.rowHeights = new int[]{15, 0, 15, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 1.0, 0.0, 1.0E-4};
		gridBagLayout.rowWeights = new double[]{0.0, 1.0, 0.0, 1.0E-4};
		setLayout(gridBagLayout);
		{
			AdaptivePanel panel = new AdaptivePanel();
			panel.setBorder(BorderFactory.createEmptyBorder());
			GridBagLayout gridBagLayout_1 = new GridBagLayout();
			gridBagLayout_1.columnWidths = new int[]{0, 0};
			gridBagLayout_1.rowHeights = new int[]{0, 5, 30, 0};
			gridBagLayout_1.columnWeights = new double[]{1.0, 1.0E-4};
			gridBagLayout_1.rowWeights = new double[]{1.0, 0.0, 0.0, 1.0E-4};
			panel.setLayout(gridBagLayout_1);
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.insets = new Insets(0, 0, 5, 5);
			gbc.fill = GridBagConstraints.BOTH;
			gbc.gridx = 1;
			gbc.gridy = 1;
			add(panel, gbc);
			{
				panel_favourites = new AdaptivePanel();
				panel_favourites.setBorder(BorderFactory.createEmptyBorder());
				panel_favourites.setLayout(new MigLayout("fill, flowX, wrap "+NUMBER_OF_ANSWERS_PER_COLUMN));
				GridBagLayout gridBagLayout_2 = new GridBagLayout();
				gridBagLayout_2.columnWidths = new int[]{0, 5, 0, 0};
				gridBagLayout_2.rowHeights = new int[]{0, 0};
				gridBagLayout_2.columnWeights = new double[]{1.0, 0.0, 0.0, 1.0E-4};
				gridBagLayout_2.rowWeights = new double[]{1.0, 1.0E-4};
				GridBagConstraints gbc_1 = new GridBagConstraints();
				gbc_1.insets = new Insets(0, 0, 5, 0);
				gbc_1.fill = GridBagConstraints.BOTH;
				gbc_1.gridx = 0;
				gbc_1.gridy = 0;
				panel.add(panel_favourites, gbc_1);
			}
			{
				AdaptivePanel panel_1 = new AdaptivePanel();
				panel_1.setBorder(BorderFactory.createEmptyBorder());
				GridBagLayout gridBagLayout_2 = new GridBagLayout();
				gridBagLayout_2.columnWidths = new int[]{100, 100, 100, 0};
				gridBagLayout_2.rowHeights = new int[]{0, 0};
				gridBagLayout_2.columnWeights = new double[]{0.0, 1.0, 0.0, 1.0E-4};
				gridBagLayout_2.rowWeights = new double[]{1.0, 1.0E-4};
				panel_1.setLayout(gridBagLayout_2);
				GridBagConstraints gbc_1 = new GridBagConstraints();
				gbc_1.fill = GridBagConstraints.BOTH;
				gbc_1.gridx = 0;
				gbc_1.gridy = 2;
				panel.add(panel_1, gbc_1);
				{
					this.but_previous = new AdaptiveButton();
					this.but_previous.setText(Messages.Recipes_PreviousFavourites);
					GridBagConstraints gbc_2 = new GridBagConstraints();
					gbc_2.fill = GridBagConstraints.HORIZONTAL;
					gbc_2.insets = new Insets(0, 0, 0, 5);
					gbc_2.gridx = 0;
					gbc_2.gridy = 0;
					panel_1.add(this.but_previous, gbc_2);
				}
				{
					errorLabel = new AdaptiveLabel();
					errorLabel.setText("error info");
					GridBagConstraints gbc_2 = new GridBagConstraints();
					gbc_2.insets = new Insets(0, 0, 0, 5);
					gbc_2.gridx = 1;
					gbc_2.gridy = 0;
					panel_1.add(errorLabel, gbc_2);
				}
				{
					this.but_more = new AdaptiveButton();
					this.but_more.setText(Messages.Recipes_MoreFavourites);
					GridBagConstraints gbc_2 = new GridBagConstraints();
					gbc_2.fill = GridBagConstraints.HORIZONTAL;
					gbc_2.gridx = 2;
					gbc_2.gridy = 0;
					panel_1.add(this.but_more, gbc_2);
				}
			}
		}
	}

	public void getReady(final SubServiceFrame service) {
		service.favourites_panel = this.panel_favourites;
		service.but_more = this.but_more;
		service.but_more.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				log.info("more!");
				service.but_MoreFavouritesClicked();
				}
			});
		service.but_previoues = this.but_previous;
		service.but_previoues.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				service.but_PreviousFavouritesClicked();
				}
			});
		// hide image by default
		this.errorLabel.setText("");
		this.errorLabel.setVisible(false);
	}

}
