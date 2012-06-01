package na.services.recipes.ui;

import java.awt.GridBagLayout;

import na.widgets.label.AdaptiveLabel;

/*
 * Draw a grey frame with nice border
 */

@SuppressWarnings("serial")
public class CustomFrame  extends na.widgets.panel.AdaptivePanel {
	public CustomFrame() {
		GridBagLayout gridBagLayout_1 = new GridBagLayout();
		gridBagLayout_1.columnWidths = new int[]{100, 0};
		gridBagLayout_1.rowHeights = new int[]{20, 0};
		gridBagLayout_1.columnWeights = new double[]{1.0, 1.0E-4};
		gridBagLayout_1.rowWeights = new double[]{1.0, 1.0E-4};
		setLayout(gridBagLayout_1);
	}
	
	private AdaptiveLabel label;

	public void setLabel(AdaptiveLabel label) {
		this.label = label;
		this.add(this.label);
	}

	public AdaptiveLabel getLabel() {
		return label;
	}
	
//	protected void paintComponent(Graphics g) {
//        super.paintComponent(g);
//        Graphics2D g2 = (Graphics2D)g;
//        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
//                            RenderingHints.VALUE_ANTIALIAS_ON);
//        
//        Insets insets = getInsets();    // border info
//        g2.setColor(new Color(232, 232, 232)); //light gray
//        g2.fillRect(insets.left, insets.top, this.getWidth(), this.getHeight());
//        g2.setColor(new Color(187, 187, 187)); //dark gray
//        g2.drawRect(insets.left, insets.top, this.getWidth()-1, this.getHeight()-1);
//	}
}
