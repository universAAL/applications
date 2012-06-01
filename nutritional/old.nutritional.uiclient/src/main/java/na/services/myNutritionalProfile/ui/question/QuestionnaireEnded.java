package na.services.myNutritionalProfile.ui.question;

import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;

import na.services.myNutritionalProfile.ui.SubServiceFrame;
import na.utils.lang.Messages;
import na.widgets.button.AdaptiveButton;
import na.widgets.label.AdaptiveLabel;
import na.widgets.panel.AdaptivePanel;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.net.URL;

@SuppressWarnings("serial")
public class QuestionnaireEnded extends AdaptivePanel {
	
	private AdaptiveLabel label;
	private JButton image;
	private JButton button;
	
	public QuestionnaireEnded() {
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
			gridBagLayout_1.rowHeights = new int[]{0, 3, 30, 0, 0};
			gridBagLayout_1.columnWeights = new double[]{1.0, 1.0E-4};
			gridBagLayout_1.rowWeights = new double[]{1.0, 0.0, 0.0, 1.0, 1.0E-4};
			panel.setLayout(gridBagLayout_1);
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.insets = new Insets(0, 0, 5, 5);
			gbc.fill = GridBagConstraints.BOTH;
			gbc.gridx = 1;
			gbc.gridy = 1;
			add(panel, gbc);
			{
				image = new JButton();
				GridBagConstraints gbc_1 = new GridBagConstraints();
				gbc_1.anchor = GridBagConstraints.SOUTH;
				gbc_1.insets = new Insets(0, 0, 5, 0);
				gbc_1.gridx = 0;
				gbc_1.gridy = 0;
				panel.add(image, gbc_1);
				URL picURL = this.getClass().getResource("succeeded.png");
				ImageIcon j = new ImageIcon(picURL);
				image.setIcon(j);
			}
			{
				label = new AdaptiveLabel();
				label.setText(Messages.Questionnaire_QuestionnaireHasEnded);
				GridBagConstraints gbc_1 = new GridBagConstraints();
				gbc_1.insets = new Insets(0, 0, 5, 0);
				gbc_1.anchor = GridBagConstraints.NORTH;
				gbc_1.gridx = 0;
				gbc_1.gridy = 2;
				panel.add(label, gbc_1);
			}
			{
				button = new AdaptiveButton();
				button.setText(Messages.Questionnaire_ContinueAfterEnded);
				GridBagConstraints gbc_1 = new GridBagConstraints();
				gbc_1.gridx = 0;
				gbc_1.gridy = 3;
				panel.add(button, gbc_1);
			}
		}
	}
	
	public void getReady(final SubServiceFrame s) {
		button.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				s.launcher.showPendingQuestionnaires();
			}
		});
	}

}
