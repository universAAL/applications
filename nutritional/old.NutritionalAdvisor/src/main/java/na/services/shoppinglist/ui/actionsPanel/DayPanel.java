package na.services.shoppinglist.ui.actionsPanel;

import javax.swing.JPanel;

import na.widgets.label.AdaptiveLabel;

import java.awt.GridBagLayout;


import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.Insets;

@SuppressWarnings("serial")
public class DayPanel extends JPanel {
	
	public static final int GREEN = 1;
	public static final int RED = 2;
	public static final int DEFAULT = 3;
	
//	private int type = GREEN;
	private AdaptiveLabel number;
	private AdaptiveLabel day;
	
	public DayPanel() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{15, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 1.0E-4};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 1.0E-4};
		setLayout(gridBagLayout);
		{
			number = new AdaptiveLabel();
			number.setText("30");
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.insets = new Insets(0, 0, 5, 0);
			gbc.gridx = 0;
			gbc.gridy = 0;
			add(number, gbc);
		}
		{
			day = new AdaptiveLabel();
			day.setText("Mon");
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.gridx = 0;
			gbc.gridy = 2;
			add(day, gbc);
		}
		this.setType(DEFAULT);
	}
	
	public void setType(int type) {
		switch (type) {
		case GREEN:
			this.setBackground(new Color(194, 213, 29));
			break;
		case RED:
			this.setBackground(new Color(213, 98, 29));
			break;
		case DEFAULT:
			this.setBackground(new Color(240, 240, 240));
			break;

		default:
			break;
		}
	}

	public void setNumber(String num) {
		this.number.setText(num);
	}
	
	public void setDay(String day) {
		this.day.setText(day);
	}
}
