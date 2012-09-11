package na.services.scheduler;


import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;

import na.widgets.panel.AdaptivePanel;
import na.widgets.textbox.AdaptiveTextBox;


@SuppressWarnings("serial")
class PopUp extends AdaptivePanel {
	
	private AdaptiveTextBox box;
//	AdaptiveButton button_ok;
	private JButton image;
	
	public PopUp() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{10, 200, 10, 0};
		gridBagLayout.rowHeights = new int[]{10, 200, 10, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 1.0, 0.0, 1.0E-4};
		gridBagLayout.rowWeights = new double[]{0.0, 1.0, 0.0, 1.0E-4};
		setLayout(gridBagLayout);
		{
			AdaptivePanel panel = new AdaptivePanel();
			panel.setBorder(BorderFactory.createEmptyBorder());
			GridBagLayout gridBagLayout_1 = new GridBagLayout();
			gridBagLayout_1.columnWidths = new int[]{0, 0};
			gridBagLayout_1.rowHeights = new int[]{0, 0, 0};
			gridBagLayout_1.columnWeights = new double[]{1.0, 1.0E-4};
			gridBagLayout_1.rowWeights = new double[]{0.0, 1.0, 1.0E-4};
			panel.setLayout(gridBagLayout_1);
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.insets = new Insets(0, 0, 5, 5);
			gbc.fill = GridBagConstraints.BOTH;
			gbc.gridx = 1;
			gbc.gridy = 1;
			add(panel, gbc);
			{
				this.image = new JButton();
				GridBagConstraints gbc_1 = new GridBagConstraints();
				gbc_1.fill = GridBagConstraints.HORIZONTAL;
				gbc_1.insets = new Insets(0, 0, 5, 0);
				gbc_1.gridx = 0;
				gbc_1.gridy = 0;
				panel.add(this.image, gbc_1);
			}
			{
				box = new AdaptiveTextBox();
				box.setColumns(20);
				box.setRows(5);
				box.setLineWrap(true);
				box.setEditable(false);
				box.setWrapStyleWord(true);
				box.setSize(300, 200);
				GridBagConstraints gbc_1 = new GridBagConstraints();
				gbc_1.fill = GridBagConstraints.BOTH;
				gbc_1.gridx = 0;
				gbc_1.gridy = 1;
				panel.add(box, gbc_1);
			}
		}
	}
	
	public void setText(String text) {
		this.box.setText(text);
	}
	
	public void setImage(ImageIcon icon) {
		if (icon!=null) {
			this.image.setIcon(icon);
			this.image.setSize(icon.getIconWidth(), icon.getIconHeight());
		}
	}

}
