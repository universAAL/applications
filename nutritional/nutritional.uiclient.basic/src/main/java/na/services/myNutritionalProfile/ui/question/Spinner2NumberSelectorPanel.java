package na.services.myNutritionalProfile.ui.question;


import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;

import na.services.myNutritionalProfile.ui.question.handler.NumberHandler;
import na.widgets.label.AdaptiveLabel;
import na.widgets.panel.AdaptivePanel;
import na.widgets.spinner.AdaptiveSpinner;
import na.widgets.spinner2.AdaptiveSpinner2;


import java.awt.GridBagConstraints;
import java.awt.Insets;


@SuppressWarnings("serial")
public class Spinner2NumberSelectorPanel extends AdaptivePanel {
	
	private int min = 0;
	private int max = 100000;
	private int default_value = 150;
	
	public void setMin(int mmin) {
		this.min = mmin;
//		SpinnerModel model = new SpinnerNumberModel(this.default_value, this.min, this.max, 1);
//		this.spinner.setModel(model);
		this.spinner.setMin(mmin);
	}

	public void setMax(int mmax) {
		this.max = mmax;
//		SpinnerModel model = new SpinnerNumberModel(this.default_value, this.min, this.max, 1);
//		this.spinner.setModel(model);
		this.spinner.setMax(mmax);
	}

	AdaptiveSpinner2 spinner;
	AdaptiveLabel label;
	
	public Spinner2NumberSelectorPanel() {
		this.setBorder(BorderFactory.createEmptyBorder());
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 5, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 1.0E-4};
		gridBagLayout.rowWeights = new double[]{0.0, 1.0E-4};
		setLayout(gridBagLayout);
		{
			label = new AdaptiveLabel();
			label.setText("Value");
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.insets = new Insets(0, 0, 0, 5);
			gbc.gridx = 0;
			gbc.gridy = 0;
			add(label, gbc);
		}
		{
//			SpinnerModel model_h = new SpinnerNumberModel(12, min, max, 1);
			spinner = new AdaptiveSpinner2();
//			spinner.setModel(model_h);
			spinner.setMin(min);
			spinner.setMax(max);
			spinner.setValue(this.default_value);
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.gridx = 2;
			gbc.gridy = 0;
			add(spinner, gbc);
		}
	}

	public void setValue(String hour) {
		this.spinner.setValue(new Integer(hour));
	}
	
	public void setChangeListener(NumberHandler numberHandler) {
		this.spinner.addChangeListener(numberHandler);
	}
	
	public Integer getValue() {
		return (Integer) this.spinner.getValue();
	}
	
	public void setTitle(String val) {
		this.label.setText(val);
	}
	
	public void setDeafultValue(int value) {
		this.default_value = value;
		this.spinner.setValue(value);
	}
}
