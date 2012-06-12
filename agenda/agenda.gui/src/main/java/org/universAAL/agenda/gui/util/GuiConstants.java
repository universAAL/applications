package org.universAAL.agenda.gui.util;

import java.awt.Color;
import java.awt.Font;
import java.util.Calendar;

import javax.swing.BorderFactory;
import javax.swing.border.Border;

public class GuiConstants {
	// colors
	public static final Color textActiveBackground = new Color(0xf1ee9d);
	public static final Color textActiveForeground = new Color(0x00c129);
	public static final Color textInactiveBackground = Color.white;
	public static final Color textInActiveForeground = textActiveForeground;

	// label color
	public static final Color labelColor = new Color(0x565656);

	// Fonts
	public static final Font miniFont = new Font("MyriadPro", Font.PLAIN, 10);
	public static final Font smallFont = new Font("MyriadPro", Font.PLAIN, 15);
	public static final Font mediumFont = new Font("MyriadPro", Font.PLAIN, 20);
	public static final Font largeFont = new Font("MyriadPro", Font.PLAIN, 25);

	// Borders
	public static final Border popupBorder = BorderFactory
			.createLineBorder(Color.black);

	public static void main(String[] str) {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, 2007);
		c.set(Calendar.MONTH, 1);
		System.out.println("Max days: "
				+ c.getActualMaximum(Calendar.DAY_OF_MONTH));
	}
}
