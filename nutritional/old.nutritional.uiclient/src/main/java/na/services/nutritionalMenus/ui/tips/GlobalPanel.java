package na.services.nutritionalMenus.ui.tips;


import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;

import na.miniDao.Tip;
import na.services.nutritionalMenus.MenusSubServiceLauncher;
import na.widgets.panel.AdaptivePanel;



@SuppressWarnings("serial")
public class GlobalPanel extends AdaptivePanel {
	
	private TipsWindow mainPanel;
	
	public GlobalPanel() {
		this.setBorder(BorderFactory.createEmptyBorder());
		GridBagLayout gridBagLayout_1 = new GridBagLayout();
		gridBagLayout_1.columnWidths = new int[]{500, 0};
		gridBagLayout_1.rowHeights = new int[]{400, 0};
		gridBagLayout_1.columnWeights = new double[]{1.0, 1.0E-4};
		gridBagLayout_1.rowWeights = new double[]{1.0, 1.0E-4};
		setLayout(gridBagLayout_1);
		
		mainPanel = new TipsWindow(); 
		GridBagConstraints gbc_1 = new GridBagConstraints();
		gbc_1.fill = GridBagConstraints.BOTH;
		gbc_1.gridx = 0;
		gbc_1.gridy = 0;
		add(mainPanel, gbc_1);
	}

	public void setData(MenusSubServiceLauncher subServiceLauncher,
			Tip[] tipsDescriptions, int i) {
		mainPanel.setLauncher(subServiceLauncher);
		mainPanel.setTips(tipsDescriptions);
		mainPanel.setCurrent_tip(i);
		mainPanel.drawContent();
	}

}
