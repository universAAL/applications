package org.universAAL.agenda.gui.util;

import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.SwingConstants;


public class ButtonCreator {

  //  private static ButtonCreator buttonCreator;

//    public static ButtonCreator getInstance() {
//	if (buttonCreator == null)
//	    buttonCreator = new ButtonCreator();
//	return buttonCreator;
//    }
/**
 * 
 * @param iconName icon name
 * @param text text on the button
 * @param font font of the text
 * @return button instance
 */
    public static JButton createButton(ImageIcon icon, String text, Font font) {
	JButton jbutton = new JButton(icon);
	jbutton.setText("<html><center>" +text+"</center></html>");
	jbutton.setVerticalTextPosition(SwingConstants.CENTER);
	jbutton.setHorizontalTextPosition(SwingConstants.CENTER);
	jbutton.setFont(font);
	return jbutton;

    }
}
