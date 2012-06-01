package na.services.help.ui;


import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.BorderFactory;

import na.services.help.HelpSubServiceLauncher;
import na.widgets.panel.AdaptivePanel;



@SuppressWarnings("serial")
public class SubServiceFrame extends AdaptivePanel {
	
	@SuppressWarnings("unused")
	private HelpSubServiceLauncher launcher;
	
	public SubServiceFrame(HelpSubServiceLauncher launch) {
		this.launcher = launch;
		this.setBorder(BorderFactory.createEmptyBorder());
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{15, 200, 15, 0};
		gridBagLayout.rowHeights = new int[]{15, 200, 15, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 1.0, 0.0, 1.0E-4};
		gridBagLayout.rowWeights = new double[]{0.0, 1.0, 0.0, 1.0E-4};
		setLayout(gridBagLayout);
		{
			HelpPanel panel = new HelpPanel();
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.insets = new Insets(0, 0, 5, 5);
			gbc.fill = GridBagConstraints.BOTH;
			gbc.gridx = 1;
			gbc.gridy = 1;
			add(panel, gbc);
		}
	}
	

}
