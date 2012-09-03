package na.services.nutritionalMenus.ui.tips;

import java.awt.GridBagLayout;


import java.awt.GridBagConstraints;
import java.awt.Insets;

import na.utils.lang.Messages;
import na.widgets.button.AdaptiveButton;
import na.widgets.panel.AdaptivePanel;

@SuppressWarnings("serial")
class TipsButtonsPanel extends AdaptivePanel {
	
	private TipsWindow tipsParent;
	public AdaptiveButton previousTipButton;
	public AdaptiveButton nextTipButton;
	
	public TipsButtonsPanel() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{100, 200, 100, 0};
		gridBagLayout.rowHeights = new int[]{0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 1.0, 1.0, 1.0E-4};
		gridBagLayout.rowWeights = new double[]{1.0, 1.0E-4};
		setLayout(gridBagLayout);
		{
			previousTipButton = new AdaptiveButton();
			previousTipButton.setText(Messages.Menus_Tips_previousTip);
			previousTipButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent evt) {
					butPrevTipClicked(evt);
				}
			});
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.fill = GridBagConstraints.VERTICAL;
			gbc.insets = new Insets(0, 0, 0, 5);
			gbc.gridx = 0;
			gbc.gridy = 0;
			add(previousTipButton, gbc);
		}
		{
			nextTipButton = new AdaptiveButton();
			nextTipButton.setText(Messages.Menus_Tips_nextTip);
			nextTipButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent evt) {
					butNexTipClicked(evt);
				}
			});
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.fill = GridBagConstraints.VERTICAL;
			gbc.gridx = 2;
			gbc.gridy = 0;
			add(nextTipButton, gbc);
		}
	}

	protected void settipsParent(TipsWindow parent) {
		this.tipsParent = parent;
	}

//	public TipsWindow gettipsParent() {
//		return tipsParent;
//	}

	private void butNexTipClicked(java.awt.event.ActionEvent m) {
		this.tipsParent.butNextTipClicked(m);
	}
	
	private void butPrevTipClicked(java.awt.event.ActionEvent m) {
		this.tipsParent.butPreviousTipClicked(m);
	}
}
