package na.oasisUtils.trustedSecurityNetwork;

import javax.swing.JPanel;
import java.awt.GridBagLayout;
import javax.swing.JLabel;


import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JTextField;
import javax.swing.JButton;

import na.widgets.panel.AdaptivePanel;

public class RequestPasswordWindow extends AdaptivePanel {
	private JTextField textUsername;
	private JTextField textPassword;
	public RequestPasswordWindow() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{20, 0, 20, 0};
		gridBagLayout.rowHeights = new int[]{20, 0, 20, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 1.0, 0.0, 1.0E-4};
		gridBagLayout.rowWeights = new double[]{0.0, 1.0, 0.0, 1.0E-4};
		setLayout(gridBagLayout);
		{
			AdaptivePanel panel = new AdaptivePanel();
			GridBagLayout gridBagLayout_1 = new GridBagLayout();
			gridBagLayout_1.columnWidths = new int[]{0, 0, 0, 0};
			gridBagLayout_1.rowHeights = new int[]{0, 0, 0, 0, 0};
			gridBagLayout_1.columnWeights = new double[]{0.0, 0.0, 1.0, 1.0E-4};
			gridBagLayout_1.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 1.0E-4};
			panel.setLayout(gridBagLayout_1);
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.insets = new Insets(0, 0, 5, 5);
			gbc.fill = GridBagConstraints.BOTH;
			gbc.gridx = 1;
			gbc.gridy = 1;
			add(panel, gbc);
			{
				JLabel lblUsername = new JLabel("Username:");
				GridBagConstraints gbc_1 = new GridBagConstraints();
				gbc_1.anchor = GridBagConstraints.EAST;
				gbc_1.insets = new Insets(0, 0, 5, 5);
				gbc_1.gridx = 1;
				gbc_1.gridy = 1;
				panel.add(lblUsername, gbc_1);
			}
			{
				textUsername = new JTextField();
				textUsername.setEditable(false);
				GridBagConstraints gbc_1 = new GridBagConstraints();
				gbc_1.insets = new Insets(0, 0, 5, 0);
				gbc_1.fill = GridBagConstraints.HORIZONTAL;
				gbc_1.gridx = 2;
				gbc_1.gridy = 1;
				panel.add(textUsername, gbc_1);
				textUsername.setColumns(10);
			}
			{
				JLabel lblPassword = new JLabel("Password:");
				GridBagConstraints gbc_1 = new GridBagConstraints();
				gbc_1.anchor = GridBagConstraints.EAST;
				gbc_1.insets = new Insets(0, 0, 5, 5);
				gbc_1.gridx = 1;
				gbc_1.gridy = 2;
				panel.add(lblPassword, gbc_1);
			}
			{
				textPassword = new JTextField();
				GridBagConstraints gbc_1 = new GridBagConstraints();
				gbc_1.insets = new Insets(0, 0, 5, 0);
				gbc_1.fill = GridBagConstraints.HORIZONTAL;
				gbc_1.gridx = 2;
				gbc_1.gridy = 2;
				panel.add(textPassword, gbc_1);
				textPassword.setColumns(10);
			}
			{
				JButton btnLogin = new JButton("Login");
				GridBagConstraints gbc_1 = new GridBagConstraints();
				gbc_1.gridx = 2;
				gbc_1.gridy = 3;
				panel.add(btnLogin, gbc_1);
			}
		}
	}

}
