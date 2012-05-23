package org.universAAL.agenda.gui;

import java.util.Calendar;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.SwingConstants;

import org.universAAL.agenda.gui.osgi.Activator;

public class VoiceButton extends JButton {

    private static final long serialVersionUID = 8230972694812926078L;

    public VoiceButton() {
	Calendar fecha = java.util.Calendar.getInstance();
	String dia = String.valueOf(fecha.get(java.util.Calendar.DATE));
	String mes = String.valueOf(fecha.get(java.util.Calendar.MONTH) + 1);
	String anio = String.valueOf(fecha.get(java.util.Calendar.YEAR));
	if (dia.length() < 2)
	    dia = "0" + dia;
	if (mes.length() < 2)
	    mes = "0" + mes;
	mes = monthChange(mes);// Change the month to letters
	String voiceDate = "<html><table><tr><td> <font face=Myriad Pro size=7 color=#565656>"
		+ dia
		+ "</font>"
		+ "</td><td><font face=Myriad Pro size=4 color=#565656>"
		+ mes
		+ "</font><br><font face=Myriad Pro size=3 color=#00a77f>"
		+ ""
		+ anio
		+ "</font></td><tr><td><font face=Myriad Pro size=4 color=#00a77f>Sound</font></td><td>"
		+ "<font face=Myriad Pro size=4 color=#565656>Off</font></td></tr></table>";

	ImageIcon voiceImage = new ImageIcon(getClass().getResource(
		Activator.ICON_PATH_PREFIX + "/sound.jpg"));
	setHorizontalTextPosition(SwingConstants.LEFT);
	setText(voiceDate);
	setIcon(voiceImage);
	setFocusPainted(false);
	setBorderPainted(false);
	setContentAreaFilled(false);

	// addActionListener(new ActionListener() {
	// public void actionPerformed(ActionEvent evento) {
	// voiceOption = true;
	// }
	// });
    }

    public String monthChange(String month) {
	String monthLetters = null;
	if (month.equals("01"))
	    monthLetters = "Jan";
	else if (month.equals("02"))
	    monthLetters = "Feb";
	else if (month.equals("03"))
	    monthLetters = "Mar";
	else if (month.equals("04"))
	    monthLetters = "Apr";
	else if (month.equals("05"))
	    monthLetters = "May";
	else if (month.equals("06"))
	    monthLetters = "Jun";
	else if (month.equals("07"))
	    monthLetters = "Jul";
	else if (month.equals("08"))
	    monthLetters = "Aug";
	else if (month.equals("09"))
	    monthLetters = "Sep";
	else if (month.equals("10"))
	    monthLetters = "Oct";
	else if (month.equals("11"))
	    monthLetters = "Nov";
	else if (month.equals("12"))
	    monthLetters = "Dec";

	return monthLetters;

    }

}
