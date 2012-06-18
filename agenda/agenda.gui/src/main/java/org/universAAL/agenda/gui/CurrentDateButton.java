package org.universAAL.agenda.gui;

import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.SwingConstants;

public class CurrentDateButton extends JButton {

    private static final long serialVersionUID = 8230972694812926078L;

    public CurrentDateButton() {
	Calendar fecha = java.util.Calendar.getInstance();
	String day = String.valueOf(fecha.get(java.util.Calendar.DATE));
	String month = String.valueOf(fecha.get(java.util.Calendar.MONTH) + 1);
	String year = String.valueOf(fecha.get(java.util.Calendar.YEAR));
	if (day.length() < 2)
	    day = "0" + day;
	if (month.length() < 2)
	    month = "0" + month;
	month = monthChange(month);// Change the month to letters
	
	String date = "<html><table><tr><td> <font face=Myriad Pro size=18 color=#0000FF>"
		+ day 
		+ "</font></td><td><font face=Myriad Pro size=6 color=#0000FF>"
		+ month
		+ "</font><br><font face=Myriad Pro size=6 color=#0000FF>"
		+ year + "</font></td></table>";

	setHorizontalTextPosition(SwingConstants.LEFT);
	setText(date);
	// removed sound icon
	// setIcon(voiceImage);
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
	    monthLetters = Messages.getString("CurrentDateButton.January");
	else if (month.equals("02"))
	    monthLetters = Messages.getString("CurrentDateButton.February");
	else if (month.equals("03"))
	    monthLetters = Messages.getString("CurrentDateButton.March");
	else if (month.equals("04"))
	    monthLetters = Messages.getString("CurrentDateButton.April");
	else if (month.equals("05"))
	    monthLetters = Messages.getString("CurrentDateButton.May");
	else if (month.equals("06"))
	    monthLetters = Messages.getString("CurrentDateButton.June");
	else if (month.equals("07"))
	    monthLetters = Messages.getString("CurrentDateButton.July");
	else if (month.equals("08"))
	    monthLetters = Messages.getString("CurrentDateButton.August");
	else if (month.equals("09"))
	    monthLetters = Messages.getString("CurrentDateButton.September");
	else if (month.equals("10"))
	    monthLetters = Messages.getString("CurrentDateButton.October");
	else if (month.equals("11"))
	    monthLetters = Messages.getString("CurrentDateButton.November");
	else if (month.equals("12"))
	    monthLetters = Messages.getString("CurrentDateButton.December");

	return monthLetters;

    }

}
