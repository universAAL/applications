package na.services.myNutritionalProfile.ui;

import java.awt.GridBagLayout;

import javax.swing.BorderFactory;

import na.utils.lang.Messages;
import na.utils.ServiceInterface;
import na.widgets.button.AdaptiveButton;
import na.widgets.label.AdaptiveLabel;
import na.widgets.panel.AdaptivePanel;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.List;



@SuppressWarnings("serial")
public class PendingQuestionnairesPanel extends AdaptivePanel {
	
	private List<AdaptiveLabel> listTitles = new ArrayList<AdaptiveLabel>();
	private List<AdaptiveLabel> listDescriptions = new ArrayList<AdaptiveLabel>();
	private List<AdaptiveLabel> listStates = new ArrayList<AdaptiveLabel>();
	private List<AdaptiveButton> listButtons = new ArrayList<AdaptiveButton>();
	private List<AdaptivePanel> listPanels = new ArrayList<AdaptivePanel>();
	private AdaptiveButton butPreviousQuestionnaires;
	private AdaptiveButton butNextQuestionnaires;
	private AdaptiveLabel pendingIntro;
	private AdaptivePanel panelButtons;
	
	public PendingQuestionnairesPanel() {
		this.setBorder(BorderFactory.createEmptyBorder());
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{15, 0, 300, 0};
		gridBagLayout.rowHeights = new int[]{15, 0, 15, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 1.0, 0.0, 1.0E-4};
		gridBagLayout.rowWeights = new double[]{0.0, 1.0, 0.0, 1.0E-4};
		setLayout(gridBagLayout);
		{
			AdaptivePanel panel = new AdaptivePanel();
			panel.setBorder(BorderFactory.createEmptyBorder());
			GridBagLayout gridBagLayout_1 = new GridBagLayout();
			gridBagLayout_1.columnWidths = new int[]{0, 0};
			gridBagLayout_1.rowHeights = new int[]{30, 0, 30, 0};
			gridBagLayout_1.columnWeights = new double[]{1.0, 1.0E-4};
			gridBagLayout_1.rowWeights = new double[]{0.0, 1.0, 0.0, 1.0E-4};
			panel.setLayout(gridBagLayout_1);
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.insets = new Insets(0, 0, 5, 5);
			gbc.fill = GridBagConstraints.BOTH;
			gbc.gridx = 1;
			gbc.gridy = 1;
			add(panel, gbc);
			{
				this.pendingIntro = new AdaptiveLabel();
				this.pendingIntro.setText(Messages.Questionnaire_PendingQuestionnairesIntroduction);
				GridBagConstraints gbc_1 = new GridBagConstraints();
				gbc_1.anchor = GridBagConstraints.WEST;
				gbc_1.insets = new Insets(0, 0, 5, 0);
				gbc_1.gridx = 0;
				gbc_1.gridy = 0;
				panel.add(this.pendingIntro, gbc_1);
			}
			{
				AdaptivePanel panel_1 = new AdaptivePanel();
				panel_1.setBorder(BorderFactory.createEmptyBorder());
				GridBagLayout gridBagLayout_2 = new GridBagLayout();
				gridBagLayout_2.columnWidths = new int[]{0, 0};
				gridBagLayout_2.rowHeights = new int[]{0, 0, 0, 0, 0};
				gridBagLayout_2.columnWeights = new double[]{1.0, 1.0E-4};
				gridBagLayout_2.rowWeights = new double[]{1.0, 1.0, 1.0, 0.0, 1.0E-4};
				panel_1.setLayout(gridBagLayout_2);
				GridBagConstraints gbc_1 = new GridBagConstraints();
				gbc_1.insets = new Insets(0, 0, 5, 0);
				gbc_1.fill = GridBagConstraints.BOTH;
				gbc_1.gridx = 0;
				gbc_1.gridy = 1;
				panel.add(panel_1, gbc_1);
				{
					AdaptivePanel panel_2 = new AdaptivePanel();
					this.listPanels.add(panel_2);
					GridBagLayout gridBagLayout_3 = new GridBagLayout();
					gridBagLayout_3.columnWidths = new int[]{1, 0, 5, 100, 0};
					gridBagLayout_3.rowHeights = new int[]{1, 0, 5, 0};
					gridBagLayout_3.columnWeights = new double[]{0.0, 1.0, 0.0, 0.0, 1.0E-4};
					gridBagLayout_3.rowWeights = new double[]{0.0, 1.0, 0.0, 1.0E-4};
					panel_2.setLayout(gridBagLayout_3);
					GridBagConstraints gbc_2 = new GridBagConstraints();
					gbc_2.insets = new Insets(0, 0, 5, 0);
					gbc_2.fill = GridBagConstraints.BOTH;
					gbc_2.gridx = 0;
					gbc_2.gridy = 0;
					panel_1.add(panel_2, gbc_2);
					{
						AdaptivePanel panel_3 = new AdaptivePanel();
						panel_3.setBorder(BorderFactory.createEmptyBorder());
						GridBagLayout gridBagLayout_4 = new GridBagLayout();
						gridBagLayout_4.columnWidths = new int[]{0, 0};
						gridBagLayout_4.rowHeights = new int[]{0, 0, 0, 0};
						gridBagLayout_4.columnWeights = new double[]{1.0, 1.0E-4};
						gridBagLayout_4.rowWeights = new double[]{1.0, 1.0, 1.0, 1.0E-4};
						panel_3.setLayout(gridBagLayout_4);
						GridBagConstraints gbc_3 = new GridBagConstraints();
						gbc_3.insets = new Insets(0, 0, 5, 5);
						gbc_3.fill = GridBagConstraints.HORIZONTAL;
						gbc_3.gridx = 1;
						gbc_3.gridy = 1;
						panel_2.add(panel_3, gbc_3);
						{
							AdaptiveLabel label = new AdaptiveLabel();
							label.setFunction(ServiceInterface.Function_boldLabel);
							label.adapt();
							label.setText("Title: My first questionnaire");
							GridBagConstraints gbc_4 = new GridBagConstraints();
							gbc_4.anchor = GridBagConstraints.WEST;
							gbc_4.insets = new Insets(0, 0, 5, 0);
							gbc_4.gridx = 0;
							gbc_4.gridy = 0;
							panel_3.add(label, gbc_4);
							this.listTitles.add(label);
						}
						{
							AdaptiveLabel label = new AdaptiveLabel();
							label.setText("Description: Description fo the questionnaire");
							GridBagConstraints gbc_4 = new GridBagConstraints();
							gbc_4.anchor = GridBagConstraints.WEST;
							gbc_4.insets = new Insets(0, 0, 5, 0);
							gbc_4.gridx = 0;
							gbc_4.gridy = 1;
							panel_3.add(label, gbc_4);
							this.listDescriptions.add(label);
						}
						{
							AdaptiveLabel label = new AdaptiveLabel();
							label.setText("State: Not started");
							GridBagConstraints gbc_4 = new GridBagConstraints();
							gbc_4.anchor = GridBagConstraints.WEST;
							gbc_4.gridx = 0;
							gbc_4.gridy = 2;
							panel_3.add(label, gbc_4);
							this.listStates.add(label);
						}
					}
					{
						AdaptiveButton button = new AdaptiveButton();
						button.setText("New button");
						GridBagConstraints gbc_3 = new GridBagConstraints();
						gbc_3.fill = GridBagConstraints.HORIZONTAL;
						gbc_3.insets = new Insets(0, 0, 5, 0);
						gbc_3.gridx = 3;
						gbc_3.gridy = 1;
						panel_2.add(button, gbc_3);
						this.listButtons.add(button);
					}
				}
				{
					AdaptivePanel panel_2 = new AdaptivePanel();
					this.listPanels.add(panel_2);
					GridBagLayout gridBagLayout_3 = new GridBagLayout();
					gridBagLayout_3.columnWidths = new int[]{1, 0, 5, 100, 0};
					gridBagLayout_3.rowHeights = new int[]{1, 0, 5, 0};
					gridBagLayout_3.columnWeights = new double[]{0.0, 1.0, 0.0, 0.0, 1.0E-4};
					gridBagLayout_3.rowWeights = new double[]{0.0, 1.0, 0.0, 1.0E-4};
					panel_2.setLayout(gridBagLayout_3);
					GridBagConstraints gbc_2 = new GridBagConstraints();
					gbc_2.insets = new Insets(0, 0, 5, 0);
					gbc_2.fill = GridBagConstraints.BOTH;
					gbc_2.gridx = 0;
					gbc_2.gridy = 1;
					panel_1.add(panel_2, gbc_2);
					{
						AdaptivePanel panel_3 = new AdaptivePanel();
						panel_3.setBorder(BorderFactory.createEmptyBorder());
						GridBagLayout gridBagLayout_4 = new GridBagLayout();
						gridBagLayout_4.columnWidths = new int[]{0, 0};
						gridBagLayout_4.rowHeights = new int[]{0, 0, 0, 0};
						gridBagLayout_4.columnWeights = new double[]{1.0, 1.0E-4};
						gridBagLayout_4.rowWeights = new double[]{1.0, 1.0, 1.0, 1.0E-4};
						panel_3.setLayout(gridBagLayout_4);
						GridBagConstraints gbc_3 = new GridBagConstraints();
						gbc_3.insets = new Insets(0, 0, 5, 5);
						gbc_3.fill = GridBagConstraints.HORIZONTAL;
						gbc_3.gridx = 1;
						gbc_3.gridy = 1;
						panel_2.add(panel_3, gbc_3);
						{
							AdaptiveLabel label = new AdaptiveLabel();
							label.setText("Title: My first questionnaire");
							label.setFunction(ServiceInterface.Function_boldLabel);
							label.adapt();
							GridBagConstraints gbc_4 = new GridBagConstraints();
							gbc_4.anchor = GridBagConstraints.WEST;
							gbc_4.insets = new Insets(0, 0, 5, 0);
							gbc_4.gridx = 0;
							gbc_4.gridy = 0;
							panel_3.add(label, gbc_4);
							this.listTitles.add(label);
						}
						{
							AdaptiveLabel label = new AdaptiveLabel();
							label.setText("Description: Description fo the questionnaire");
							GridBagConstraints gbc_4 = new GridBagConstraints();
							gbc_4.anchor = GridBagConstraints.WEST;
							gbc_4.insets = new Insets(0, 0, 5, 0);
							gbc_4.gridx = 0;
							gbc_4.gridy = 1;
							panel_3.add(label, gbc_4);
							this.listDescriptions.add(label);
						}
						{
							AdaptiveLabel label = new AdaptiveLabel();
							label.setText("State: Not started");
							GridBagConstraints gbc_4 = new GridBagConstraints();
							gbc_4.anchor = GridBagConstraints.WEST;
							gbc_4.gridx = 0;
							gbc_4.gridy = 2;
							panel_3.add(label, gbc_4);
							this.listStates.add(label);
						}
					}
					{
						AdaptiveButton button = new AdaptiveButton();
						button.setText("New button");
						GridBagConstraints gbc_3 = new GridBagConstraints();
						gbc_3.fill = GridBagConstraints.HORIZONTAL;
						gbc_3.insets = new Insets(0, 0, 5, 0);
						gbc_3.gridx = 3;
						gbc_3.gridy = 1;
						panel_2.add(button, gbc_3);
						this.listButtons.add(button);
					}
				}
				{
					AdaptivePanel panel_2 = new AdaptivePanel();
					this.listPanels.add(panel_2);
					GridBagLayout gridBagLayout_3 = new GridBagLayout();
					gridBagLayout_3.columnWidths = new int[]{1, 0, 5, 100, 0};
					gridBagLayout_3.rowHeights = new int[]{1, 0, 5, 0};
					gridBagLayout_3.columnWeights = new double[]{0.0, 1.0, 0.0, 0.0, 1.0E-4};
					gridBagLayout_3.rowWeights = new double[]{0.0, 1.0, 0.0, 1.0E-4};
					panel_2.setLayout(gridBagLayout_3);
					GridBagConstraints gbc_2 = new GridBagConstraints();
					gbc_2.insets = new Insets(0, 0, 5, 0);
					gbc_2.fill = GridBagConstraints.BOTH;
					gbc_2.gridx = 0;
					gbc_2.gridy = 2;
					panel_1.add(panel_2, gbc_2);
					{
						AdaptivePanel panel_3 = new AdaptivePanel();
						panel_3.setBorder(BorderFactory.createEmptyBorder());
						GridBagLayout gridBagLayout_4 = new GridBagLayout();
						gridBagLayout_4.columnWidths = new int[]{0, 0};
						gridBagLayout_4.rowHeights = new int[]{0, 0, 0, 0};
						gridBagLayout_4.columnWeights = new double[]{1.0, 1.0E-4};
						gridBagLayout_4.rowWeights = new double[]{1.0, 1.0, 1.0, 1.0E-4};
						panel_3.setLayout(gridBagLayout_4);
						GridBagConstraints gbc_3 = new GridBagConstraints();
						gbc_3.insets = new Insets(0, 0, 5, 5);
						gbc_3.fill = GridBagConstraints.HORIZONTAL;
						gbc_3.gridx = 1;
						gbc_3.gridy = 1;
						panel_2.add(panel_3, gbc_3);
						{
							AdaptiveLabel label = new AdaptiveLabel();
							label.setText("Title: My first questionnaire");
							label.setFunction(ServiceInterface.Function_boldLabel);
							label.adapt();
							GridBagConstraints gbc_4 = new GridBagConstraints();
							gbc_4.anchor = GridBagConstraints.WEST;
							gbc_4.insets = new Insets(0, 0, 5, 0);
							gbc_4.gridx = 0;
							gbc_4.gridy = 0;
							panel_3.add(label, gbc_4);
							this.listTitles.add(label);
						}
						{
							AdaptiveLabel label = new AdaptiveLabel();
							label.setText("Description: Description fo the questionnaire");
							GridBagConstraints gbc_4 = new GridBagConstraints();
							gbc_4.anchor = GridBagConstraints.WEST;
							gbc_4.insets = new Insets(0, 0, 5, 0);
							gbc_4.gridx = 0;
							gbc_4.gridy = 1;
							panel_3.add(label, gbc_4);
							this.listDescriptions.add(label);
						}
						{
							AdaptiveLabel label = new AdaptiveLabel();
							label.setText("State: Not started");
							GridBagConstraints gbc_4 = new GridBagConstraints();
							gbc_4.anchor = GridBagConstraints.WEST;
							gbc_4.gridx = 0;
							gbc_4.gridy = 2;
							panel_3.add(label, gbc_4);
							this.listStates.add(label);
						}
					}
					{
						AdaptiveButton button = new AdaptiveButton();
						button.setText("New button");
						GridBagConstraints gbc_3 = new GridBagConstraints();
						gbc_3.fill = GridBagConstraints.HORIZONTAL;
						gbc_3.insets = new Insets(0, 0, 5, 0);
						gbc_3.gridx = 3;
						gbc_3.gridy = 1;
						panel_2.add(button, gbc_3);
						this.listButtons.add(button);
					}
				}
				{
					AdaptivePanel panel_2 = new AdaptivePanel();
					this.listPanels.add(panel_2);
					GridBagLayout gridBagLayout_3 = new GridBagLayout();
					gridBagLayout_3.columnWidths = new int[]{1, 0, 5, 100, 0};
					gridBagLayout_3.rowHeights = new int[]{1, 0, 5, 0};
					gridBagLayout_3.columnWeights = new double[]{0.0, 1.0, 0.0, 0.0, 1.0E-4};
					gridBagLayout_3.rowWeights = new double[]{0.0, 1.0, 0.0, 1.0E-4};
					panel_2.setLayout(gridBagLayout_3);
					GridBagConstraints gbc_2 = new GridBagConstraints();
					gbc_2.insets = new Insets(0, 0, 5, 0);
					gbc_2.fill = GridBagConstraints.BOTH;
					gbc_2.gridx = 0;
					gbc_2.gridy = 3;
					panel_1.add(panel_2, gbc_2);
					{
						AdaptivePanel panel_3 = new AdaptivePanel();
						panel_3.setBorder(BorderFactory.createEmptyBorder());
						GridBagLayout gridBagLayout_4 = new GridBagLayout();
						gridBagLayout_4.columnWidths = new int[]{0, 0};
						gridBagLayout_4.rowHeights = new int[]{0, 0, 0, 0};
						gridBagLayout_4.columnWeights = new double[]{1.0, 1.0E-4};
						gridBagLayout_4.rowWeights = new double[]{1.0, 1.0, 1.0, 1.0E-4};
						panel_3.setLayout(gridBagLayout_4);
						GridBagConstraints gbc_3 = new GridBagConstraints();
						gbc_3.insets = new Insets(0, 0, 5, 5);
						gbc_3.fill = GridBagConstraints.HORIZONTAL;
						gbc_3.gridx = 1;
						gbc_3.gridy = 1;
						panel_2.add(panel_3, gbc_3);
						{
							AdaptiveLabel label = new AdaptiveLabel();
							label.setText("Default title");
							label.setFunction(ServiceInterface.Function_boldLabel);
							label.adapt();
							GridBagConstraints gbc_4 = new GridBagConstraints();
							gbc_4.anchor = GridBagConstraints.WEST;
							gbc_4.insets = new Insets(0, 0, 5, 0);
							gbc_4.gridx = 0;
							gbc_4.gridy = 0;
							panel_3.add(label, gbc_4);
							this.listTitles.add(label);
						}
						{
							AdaptiveLabel label = new AdaptiveLabel();
							label.setText("Default description");
							GridBagConstraints gbc_4 = new GridBagConstraints();
							gbc_4.anchor = GridBagConstraints.WEST;
							gbc_4.insets = new Insets(0, 0, 5, 0);
							gbc_4.gridx = 0;
							gbc_4.gridy = 1;
							panel_3.add(label, gbc_4);
							this.listDescriptions.add(label);
						}
						{
							AdaptiveLabel label = new AdaptiveLabel();
							label.setText("Default state");
							GridBagConstraints gbc_4 = new GridBagConstraints();
							gbc_4.anchor = GridBagConstraints.WEST;
							gbc_4.gridx = 0;
							gbc_4.gridy = 2;
							panel_3.add(label, gbc_4);
							this.listStates.add(label);
						}
					}
					{
						AdaptiveButton button = new AdaptiveButton();
						button.setText("New button");
						GridBagConstraints gbc_3 = new GridBagConstraints();
						gbc_3.fill = GridBagConstraints.HORIZONTAL;
						gbc_3.insets = new Insets(0, 0, 5, 0);
						gbc_3.gridx = 3;
						gbc_3.gridy = 1;
						panel_2.add(button, gbc_3);
						this.listButtons.add(button);
					}
				}
			}
			{
				AdaptivePanel panel_1 = new AdaptivePanel();
				this.panelButtons = panel_1;
				GridBagLayout gridBagLayout_2 = new GridBagLayout();
				gridBagLayout_2.columnWidths = new int[]{0, 0, 0, 0};
				gridBagLayout_2.rowHeights = new int[]{0, 0};
				gridBagLayout_2.columnWeights = new double[]{0.0, 1.0, 0.0, 1.0E-4};
				gridBagLayout_2.rowWeights = new double[]{0.0, 1.0E-4};
				panel_1.setLayout(gridBagLayout_2);
				GridBagConstraints gbc_1 = new GridBagConstraints();
				gbc_1.fill = GridBagConstraints.BOTH;
				gbc_1.gridx = 0;
				gbc_1.gridy = 2;
				panel.add(panel_1, gbc_1);
				{
					this.butPreviousQuestionnaires = new AdaptiveButton();
					this.butPreviousQuestionnaires.setText(Messages.Questionnaire_PreviousQuestionnaires);
					GridBagConstraints gbc_2 = new GridBagConstraints();
					gbc_2.insets = new Insets(0, 0, 0, 5);
					gbc_2.gridx = 0;
					gbc_2.gridy = 0;
					panel_1.add(this.butPreviousQuestionnaires, gbc_2);
				}
				{
					this.butNextQuestionnaires = new AdaptiveButton();
					this.butNextQuestionnaires.setText(Messages.Questionnaire_MoreQuestionnaires);
					GridBagConstraints gbc_2 = new GridBagConstraints();
					gbc_2.gridx = 2;
					gbc_2.gridy = 0;
					panel_1.add(this.butNextQuestionnaires, gbc_2);
				}
			}
		}
	}

	public void getReady(final SubServiceFrame s) {
		s.pending_defaultBorder = this.panelButtons.getBorder();
		s.pending_intro = this.pendingIntro;
		s.pending_listButtons = this.listButtons;
		s.pending_listDescriptions = this.listDescriptions;
		s.pending_listStates = this.listStates;
		s.pending_listTitles = this.listTitles;
		s.pending_butNextQuestionnaires = this.butNextQuestionnaires;
		s.pending_butNextQuestionnaires.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				s.butMoreQuestionnairesClicked(evt);
			}
		});
		s.pending_butPreviousQuestionnaires = this.butPreviousQuestionnaires;
		s.pending_butPreviousQuestionnaires.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				s.butPreviousQuestionnairesClicked(evt);
			}
		});
		s.pending_panelButtons = this.panelButtons;
		s.pending_listPanels = this.listPanels;
	}
	
}
