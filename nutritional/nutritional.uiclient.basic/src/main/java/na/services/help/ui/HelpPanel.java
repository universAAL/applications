package na.services.help.ui;



import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.BorderFactory;

import na.utils.ServiceInterface;
import na.utils.lang.Messages;
import na.widgets.label.AdaptiveLabel;
import na.widgets.panel.AdaptivePanel;
import na.widgets.textbox.AdaptiveTextBox;


@SuppressWarnings("serial")
class HelpPanel extends AdaptivePanel {
	
	
	public HelpPanel() {
		this.setBorder(BorderFactory.createEmptyBorder());
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{200, 0};
		gridBagLayout.rowHeights = new int[]{30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 1.0E-4};
		gridBagLayout.rowWeights = new double[]{1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0E-4};
		setLayout(gridBagLayout);
		{
			AdaptiveLabel label = new AdaptiveLabel();
			label.setFunction(ServiceInterface.Function_boldLabel);
			label.adapt();
			label.setText(Messages.Help_Question_WhatIs);
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.anchor = GridBagConstraints.WEST;
			gbc.insets = new Insets(0, 0, 5, 0);
			gbc.gridx = 0;
			gbc.gridy = 0;
			add(label, gbc);
		}
		{
			na.widgets.textbox.AdaptiveTextBox textBox = new na.widgets.textbox.AdaptiveTextBox();
			textBox.setText(Messages.Help_Answer_WhatIs);
			textBox.setLineWrap(true);
			textBox.setWrapStyleWord(true);
			textBox.setAlignmentY(AdaptiveTextBox.TOP_ALIGNMENT);
			
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.insets = new Insets(0, 20, 5, 0);
			gbc.fill = GridBagConstraints.BOTH;
			gbc.gridx = 0;
			gbc.gridy = 1;
			add(textBox, gbc);
		}
		{
			AdaptiveLabel label = new AdaptiveLabel();
			label.setFunction(ServiceInterface.Function_boldLabel);
			label.adapt();
			label.setText(Messages.Help_Question_HowCanItHelp);
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.anchor = GridBagConstraints.WEST;
			gbc.insets = new Insets(0, 0, 5, 0);
			gbc.gridx = 0;
			gbc.gridy = 2;
			add(label, gbc);
		}
		{
			na.widgets.textbox.AdaptiveTextBox textBox = new na.widgets.textbox.AdaptiveTextBox();
			textBox.setText(Messages.Help_Answer_HowCanItHelp);
			textBox.setLineWrap(true);
			textBox.setWrapStyleWord(true);
			textBox.setAlignmentY(AdaptiveTextBox.TOP_ALIGNMENT);
			
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.insets = new Insets(0, 20, 5, 0);
			gbc.fill = GridBagConstraints.BOTH;
			gbc.gridx = 0;
			gbc.gridy = 3;
			add(textBox, gbc);
		}
		{
			AdaptiveLabel label = new AdaptiveLabel();
			label.setFunction(ServiceInterface.Function_boldLabel);
			label.adapt();
			label.setText(Messages.Help_Question_WhatNutritionalProfile);
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.anchor = GridBagConstraints.WEST;
			gbc.insets = new Insets(0, 0, 5, 0);
			gbc.gridx = 0;
			gbc.gridy = 4;
			add(label, gbc);
		}
		{
			na.widgets.textbox.AdaptiveTextBox textBox = new na.widgets.textbox.AdaptiveTextBox();
			textBox.setText(Messages.Help_Answer_WhatNutritionalProfile);
			textBox.setLineWrap(true);
			textBox.setWrapStyleWord(true);
			textBox.setAlignmentY(AdaptiveTextBox.TOP_ALIGNMENT);
			
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.insets = new Insets(0, 20, 5, 0);
			gbc.fill = GridBagConstraints.BOTH;
			gbc.gridx = 0;
			gbc.gridy = 5;
			add(textBox, gbc);
		}
		{
			AdaptiveLabel label = new AdaptiveLabel();
			label.setFunction(ServiceInterface.Function_boldLabel);
			label.adapt();
			label.setText(Messages.Help_Question_HowNutritionistKnows);
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.anchor = GridBagConstraints.WEST;
			gbc.insets = new Insets(0, 0, 5, 0);
			gbc.gridx = 0;
			gbc.gridy = 6;
			add(label, gbc);
		}
		{
			na.widgets.textbox.AdaptiveTextBox textBox = new na.widgets.textbox.AdaptiveTextBox();
			textBox.setText(Messages.Help_Answer_HowNutritionistKnows);
			textBox.setLineWrap(true);
			textBox.setWrapStyleWord(true);
			textBox.setAlignmentY(AdaptiveTextBox.TOP_ALIGNMENT);
			
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.insets = new Insets(0, 20, 5, 0);
			gbc.fill = GridBagConstraints.BOTH;
			gbc.gridx = 0;
			gbc.gridy = 7;
			add(textBox, gbc);
		}
		{
			AdaptiveLabel label = new AdaptiveLabel();
			label.setFunction(ServiceInterface.Function_boldLabel);
			label.adapt();
			label.setText(Messages.Help_Question_IdontUnderestandButtons);
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.anchor = GridBagConstraints.WEST;
			gbc.insets = new Insets(0, 0, 5, 0);
			gbc.gridx = 0;
			gbc.gridy = 8;
			add(label, gbc);
		}
		{
			na.widgets.textbox.AdaptiveTextBox textBox = new na.widgets.textbox.AdaptiveTextBox();
			textBox.setText(Messages.Help_Answer_IdontUnderestandButtons);
			textBox.setLineWrap(true);
			textBox.setWrapStyleWord(true);
			textBox.setAlignmentY(AdaptiveTextBox.TOP_ALIGNMENT);
			
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.insets = new Insets(0, 20, 5, 0);
			gbc.fill = GridBagConstraints.BOTH;
			gbc.gridx = 0;
			gbc.gridy = 9;
			add(textBox, gbc);
		}
	}
	

}
