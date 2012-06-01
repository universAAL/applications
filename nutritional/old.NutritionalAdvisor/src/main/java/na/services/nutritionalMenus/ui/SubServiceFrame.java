package na.services.nutritionalMenus.ui;


import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.GridLayout;

import javax.swing.BorderFactory;

import na.services.nutritionalMenus.MenusSubServiceLauncher;
import na.widgets.panel.AdaptivePanel;


@SuppressWarnings("serial")
public class SubServiceFrame extends AdaptivePanel {
	
	private MenusSubServiceLauncher launcher;
	private Menu secNavBar;
	public AdaptivePanel content;
	
	public SubServiceFrame(MenusSubServiceLauncher launch) {
		this.launcher = launch;
		this.setBorder(BorderFactory.createEmptyBorder());
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
			content.setBorder(BorderFactory.createEmptyBorder());
			content.setLayout(new GridLayout(1, 0, 0, 0));
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.fill = GridBagConstraints.BOTH;
			gbc.gridx = 1;
			gbc.gridy = 0;
			add(content, gbc);
		}
	}
	

}
