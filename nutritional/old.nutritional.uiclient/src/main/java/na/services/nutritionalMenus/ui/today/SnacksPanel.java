package na.services.nutritionalMenus.ui.today;

import javax.swing.BoxLayout;

import na.utils.ServiceInterface;
import na.widgets.label.AdaptiveLabel;
import na.widgets.panel.AdaptivePanel;
import net.miginfocom.swing.MigLayout;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JPanel;

class SnacksPanel extends AdaptivePanel {
	
	protected AdaptiveLabel title;
	public JPanel itemsPanel;
	
	public SnacksPanel() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{64, 0};
		gridBagLayout.rowHeights = new int[]{14, 0, 10, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 1.0E-4};
		gridBagLayout.rowWeights = new double[]{0.0, 1.0, 0.0, 1.0E-4};
		setLayout(gridBagLayout);
		{
			AdaptiveLabel label = new AdaptiveLabel();
			label.setFunction(ServiceInterface.Function_boldLabel);
			label.adapt();
			label.setText("Default value");
			this.title = label;
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.insets = new Insets(0, 0, 5, 0);
			gbc.anchor = GridBagConstraints.NORTH;
			gbc.gridx = 0;
			gbc.gridy = 0;
			add(label, gbc);
		}
		{
			itemsPanel = new JPanel();
			itemsPanel.setLayout(new MigLayout("fill, flowY"));
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.insets = new Insets(0, 0, 5, 0);
			gbc.fill = GridBagConstraints.VERTICAL;
			gbc.gridx = 0;
			gbc.gridy = 1;
			add(itemsPanel, gbc);
		}
	}

}
