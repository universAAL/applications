package na.services.nutritionalMenus.ui.week;



import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.BorderFactory;

import na.widgets.panel.AdaptivePanel;

public class WeekPanel extends AdaptivePanel {
	
	public WeekPanel(InnerWeekPanel innerWeekPanel) {
		this.setBorder(BorderFactory.createEmptyBorder());
		GridBagLayout gridBagLayout_1 = new GridBagLayout();
		gridBagLayout_1.columnWidths = new int[]{30, 200, 30, 0};
		gridBagLayout_1.rowHeights = new int[]{30, 200, 30, 0};
		gridBagLayout_1.columnWeights = new double[]{0.0, 1.0, 0.0, 1.0E-4};
		gridBagLayout_1.rowWeights = new double[]{0.0, 1.0, 0.0, 1.0E-4};
		setLayout(gridBagLayout_1);
		{
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.insets = new Insets(0, 0, 5, 5);
			gbc.fill = GridBagConstraints.BOTH;
			gbc.gridx = 1;
			gbc.gridy = 1;
			add(innerWeekPanel, gbc);
		}
	}


}
