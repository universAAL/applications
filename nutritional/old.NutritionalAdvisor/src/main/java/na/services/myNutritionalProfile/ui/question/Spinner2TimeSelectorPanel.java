package na.services.myNutritionalProfile.ui.question;


import java.awt.GridBagLayout;

import javax.swing.BorderFactory;

import na.services.myNutritionalProfile.ui.question.handler.HourHandler;
import na.services.myNutritionalProfile.ui.question.handler.MinuteHandler;
import na.utils.lang.Messages;
import na.widgets.label.AdaptiveLabel;
import na.widgets.panel.AdaptivePanel;
import na.widgets.spinner2.AdaptiveSpinner2;

import java.awt.GridBagConstraints;
import java.awt.Insets;


@SuppressWarnings("serial")
public class Spinner2TimeSelectorPanel extends AdaptivePanel {
	
	private int min_hour = 0;
	private int max_hour = 23;
	private int min_min = 0;
	private	int max_min= 59;
	
	AdaptiveSpinner2 hourSpinner;
	AdaptiveSpinner2 minuteSpinner;
	
	public Spinner2TimeSelectorPanel() {
		this.setBorder(BorderFactory.createEmptyBorder());
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 5, 0, 25, 0, 5, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};
		gridBagLayout.rowWeights = new double[]{0.0, 1.0E-4};
		setLayout(gridBagLayout);
		{
			AdaptiveLabel lblHour = new AdaptiveLabel();
			lblHour.setText(Messages.Questionnaire_Hour);
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.insets = new Insets(0, 0, 0, 5);
			gbc.gridx = 0;
			gbc.gridy = 0;
			add(lblHour, gbc);
		}
		{
//			SpinnerModel model_h = new SpinnerNumberModel(12, min_hour, max_hour, 1);
			hourSpinner = new AdaptiveSpinner2();
//			hourSpinner.setModel(model_h);
			hourSpinner.setMax(max_hour);
			hourSpinner.setMin(min_hour);
			hourSpinner.setValue(12);
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.insets = new Insets(0, 0, 0, 5);
			gbc.gridx = 2;
			gbc.gridy = 0;
			add(hourSpinner, gbc);
		}
		{
			AdaptiveLabel lblMinute = new AdaptiveLabel();
			lblMinute.setText(Messages.Questionnaire_Minute);
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.insets = new Insets(0, 0, 0, 5);
			gbc.gridx = 4;
			gbc.gridy = 0;
			add(lblMinute, gbc);
		}
		{
//			SpinnerModel model_m = new SpinnerNumberModel(30, min_min, max_min, 1);
			minuteSpinner = new AdaptiveSpinner2();
//			minuteSpinner.setModel(model_m);
			minuteSpinner.setMax(max_min);
			minuteSpinner.setMin(min_min);
			minuteSpinner.setValue(30);
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.gridx = 6;
			gbc.gridy = 0;
			add(minuteSpinner, gbc);
		}
	}

	public void setHour(String hour) {
		this.hourSpinner.setValue(new Integer(hour));
	}
	
	public void setMinute(String minute) {
		this.minuteSpinner.setValue(new Integer(minute));
	}
	
	public void setMinuteChangeListener(MinuteHandler minuteHandler) {
		this.minuteSpinner.addChangeListener(minuteHandler);
	}
	
	public void setHourChangeListener(HourHandler hourHandler) {
		this.hourSpinner.addChangeListener(hourHandler);
	}
	
	public Integer getHour() {
		return (Integer) this.hourSpinner.getValue();
	}
	
	public Integer getMinute() {
		return (Integer) this.minuteSpinner.getValue();
	}
}
