package na.services.myNutritionalProfile.ui.question;


import java.awt.GridBagLayout;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;



import na.services.myNutritionalProfile.ui.SubServiceFrame;
import na.utils.lang.Messages;
import na.utils.ServiceInterface;
import na.widgets.button.AdaptiveButton;
import na.widgets.label.AdaptiveLabel;
import na.widgets.panel.AdaptivePanel;
import na.widgets.textbox.AdaptiveTextBox;
import net.miginfocom.swing.MigLayout;


@SuppressWarnings("serial")
public class QuestionPanel extends AdaptivePanel {
	private static final String NUMBER_OF_ANSWERS_PER_ROW = "10";
	
	private AdaptivePanel answers_panel;
	private AdaptiveLabel question_questionnaire_title;
	private na.widgets.textbox.AdaptiveTextBox question_text;
	private na.widgets.textbox.AdaptiveTextBox question_description;
	private AdaptiveButton but_next_question;
	private AdaptiveButton but_previous_question;
	private AdaptiveButton image;
	private List<Component> listAnswers = new ArrayList<Component>();
	private AdaptiveLabel errorLabel;
	

	public QuestionPanel() {
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
			gridBagLayout_1.rowHeights = new int[]{0, 0, 0, 0, 5, 30, 0};
			gridBagLayout_1.columnWeights = new double[]{1.0, 1.0E-4};
			gridBagLayout_1.rowWeights = new double[]{0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 1.0E-4};
			panel.setLayout(gridBagLayout_1);
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.insets = new Insets(0, 0, 5, 5);
			gbc.fill = GridBagConstraints.BOTH;
			gbc.gridx = 1;
			gbc.gridy = 1;
			add(panel, gbc);
			{
				this.question_questionnaire_title = new AdaptiveLabel();
				this.question_questionnaire_title.setText("Questionnaire Title");
				GridBagConstraints gbc_1 = new GridBagConstraints();
				gbc_1.fill = GridBagConstraints.HORIZONTAL;
				gbc_1.insets = new Insets(0, 0, 5, 0);
				gbc_1.gridx = 0;
				gbc_1.gridy = 0;
				panel.add(this.question_questionnaire_title, gbc_1);
			}
			{
				this.question_text = new na.widgets.textbox.AdaptiveTextBox();
				question_text.setEditable(false);
				this.question_text.setFunction(ServiceInterface.Function_boldLabel);
				this.question_text.adapt();
				this.question_text.setText("Question Text");
				this.question_text.setLineWrap(true);
				this.question_text.setWrapStyleWord(true);
				this.question_text.setAlignmentY(AdaptiveTextBox.TOP_ALIGNMENT);
				GridBagConstraints gbc_1 = new GridBagConstraints();
				gbc_1.fill = GridBagConstraints.HORIZONTAL;
				gbc_1.insets = new Insets(0, 0, 5, 0);
				gbc_1.gridx = 0;
				gbc_1.gridy = 1;
				panel.add(this.question_text, gbc_1);
			}
			{
				this.question_description = new na.widgets.textbox.AdaptiveTextBox();
				question_description.setEditable(false);
				this.question_description.setText("Question Description");
				this.question_description.setLineWrap(true);
				this.question_description.setWrapStyleWord(true);
				this.question_description.setAlignmentY(AdaptiveTextBox.TOP_ALIGNMENT);
				GridBagConstraints gbc_1 = new GridBagConstraints();
				gbc_1.fill = GridBagConstraints.HORIZONTAL;
				gbc_1.insets = new Insets(0, 0, 5, 0);
				gbc_1.gridx = 0;
				gbc_1.gridy = 2;
				panel.add(this.question_description, gbc_1);
			}
			{
				AdaptivePanel panel_1 = new AdaptivePanel();
				panel_1.setBorder(BorderFactory.createEmptyBorder());
				GridBagLayout gridBagLayout_2 = new GridBagLayout();
				gridBagLayout_2.columnWidths = new int[]{0, 5, 0, 0};
				gridBagLayout_2.rowHeights = new int[]{0, 0};
				gridBagLayout_2.columnWeights = new double[]{1.0, 0.0, 0.0, 1.0E-4};
				gridBagLayout_2.rowWeights = new double[]{1.0, 1.0E-4};
				panel_1.setLayout(gridBagLayout_2);
				GridBagConstraints gbc_1 = new GridBagConstraints();
				gbc_1.insets = new Insets(0, 0, 5, 0);
				gbc_1.fill = GridBagConstraints.BOTH;
				gbc_1.gridx = 0;
				gbc_1.gridy = 3;
				panel.add(panel_1, gbc_1);
				{
					answers_panel = new AdaptivePanel();
					answers_panel.setBorder(BorderFactory.createEmptyBorder());
					answers_panel.setLayout(new MigLayout("fill, flowY, wrap "+NUMBER_OF_ANSWERS_PER_ROW));
					
//					for (int i=0; i<40; i++) {
//						answers_panel.add(new JButton("Answer number: "+i));
//					}
					
					GridBagConstraints gbc_2 = new GridBagConstraints();
					gbc_2.insets = new Insets(0, 0, 0, 5);
					gbc_2.fill = GridBagConstraints.BOTH;
					gbc_2.gridx = 0;
					gbc_2.gridy = 0;
					panel_1.add(answers_panel, gbc_2);
				}
				{
					this.image = new AdaptiveButton();
					this.image.setText("image");
					GridBagConstraints gbc_2 = new GridBagConstraints();
					gbc_2.gridx = 2;
					gbc_2.gridy = 0;
					panel_1.add(this.image, gbc_2);
				}
			}
			{
				AdaptivePanel panel_1 = new AdaptivePanel();
				panel_1.setBorder(BorderFactory.createEmptyBorder());
				GridBagLayout gridBagLayout_2 = new GridBagLayout();
				gridBagLayout_2.columnWidths = new int[]{0, 100, 0, 0};
				gridBagLayout_2.rowHeights = new int[]{0, 0};
				gridBagLayout_2.columnWeights = new double[]{0.0, 1.0, 0.0, 1.0E-4};
				gridBagLayout_2.rowWeights = new double[]{1.0, 1.0E-4};
				panel_1.setLayout(gridBagLayout_2);
				GridBagConstraints gbc_1 = new GridBagConstraints();
				gbc_1.fill = GridBagConstraints.BOTH;
				gbc_1.gridx = 0;
				gbc_1.gridy = 5;
				panel.add(panel_1, gbc_1);
				{
					this.but_previous_question = new AdaptiveButton();
					this.but_previous_question.setText(Messages.Questionnaire_previousQuestion);
					GridBagConstraints gbc_2 = new GridBagConstraints();
					gbc_2.insets = new Insets(0, 0, 0, 5);
					gbc_2.gridx = 0;
					gbc_2.gridy = 0;
					panel_1.add(this.but_previous_question, gbc_2);
				}
				{
					errorLabel = new AdaptiveLabel();
					errorLabel.setText("error info");
					GridBagConstraints gbc_2 = new GridBagConstraints();
					gbc_2.insets = new Insets(0, 0, 0, 5);
					gbc_2.gridx = 1;
					gbc_2.gridy = 0;
					panel_1.add(errorLabel, gbc_2);
				}
				{
					this.but_next_question = new AdaptiveButton();
					this.but_next_question.setText(Messages.Questionnaire_nextQuestion);
					GridBagConstraints gbc_2 = new GridBagConstraints();
					gbc_2.gridx = 2;
					gbc_2.gridy = 0;
					panel_1.add(this.but_next_question, gbc_2);
				}
			}
		}
	}

	public void getReady(SubServiceFrame service) {
		this.listAnswers.removeAll(listAnswers);
		service.errorLabel = this.errorLabel;
		service.question_description = this.question_description;
		service.question_image = this.image;
//		service.question_listAnswers = this.listAnswers;
		service.question_questionnaire_title = this.question_questionnaire_title;
		service.question_text = this.question_text;
		service.question_but_next_question = this.but_next_question;
		service.question_but_previous_question = this.but_previous_question;
		service.question_answers_panel = this.answers_panel;
		
		// hide image by default
		this.image.setVisible(false);
		this.errorLabel.setText("");
		this.errorLabel.setVisible(false);
	}

}
