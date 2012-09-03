package na.services.shoppinglist.ui.goingShop;




import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

import javax.swing.BorderFactory;

import na.services.shoppinglist.ui.SubServiceFrame;
import na.widgets.panel.AdaptivePanel;

@SuppressWarnings("serial")
public class ShoppingWindow extends AdaptivePanel {
	
	private ShoppingMainPanel panel;
	
	
	public ShoppingWindow() {
		this.setBorder(BorderFactory.createEmptyBorder());
		GridBagLayout gridBagLayout_1 = new GridBagLayout();
		gridBagLayout_1.columnWidths = new int[]{300, 0};
		gridBagLayout_1.rowHeights = new int[]{400, 0};
		gridBagLayout_1.columnWeights = new double[]{0.0, 1.0E-4};
		gridBagLayout_1.rowWeights = new double[]{1.0, 1.0E-4};
		setLayout(gridBagLayout_1);
		{
			panel = new ShoppingMainPanel();
			panel.setBorder(BorderFactory.createEmptyBorder());
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.anchor = GridBagConstraints.WEST;
			gbc.fill = GridBagConstraints.VERTICAL;
			gbc.gridx = 0;
			gbc.gridy = 0;
			add(panel, gbc);
		}
		
//		panel.dayRecipeWindow = this;
	}

	public void getReady(SubServiceFrame service) {
		this.panel.getReady(service);
	}

	
	
	

}
