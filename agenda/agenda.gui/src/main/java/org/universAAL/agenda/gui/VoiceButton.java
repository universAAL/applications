package org.universAAL.agenda.gui;

import java.util.Calendar;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.SwingConstants;

public class VoiceButton extends JButton {

    private static final long serialVersionUID = 8230972694812926078L;

    public VoiceButton() {
	Calendar fecha = java.util.Calendar.getInstance();
	String day = String.valueOf(fecha.get(java.util.Calendar.DATE));
	String month = String.valueOf(fecha.get(java.util.Calendar.MONTH) + 1);
	String year = String.valueOf(fecha.get(java.util.Calendar.YEAR));
	if (day.length() < 2)
	    day = "0" + day;
	if (month.length() < 2)
	    month = "0" + month;
	month = monthChange(month);// Change the month to letters
	

	//date and month in 1st row, year in 2nd
//	String voiceDate = "<html><table><tr><td> <font face=Myriad Pro size=7 color=#565656>"
//		+ day 
//		+ "</font></td><td><font face=Myriad Pro size=7 color=#565656>"
//		+ month
//		+ "</font><tr><font face=Myriad Pro size=14 color=#565656>"
//		+ year + "</font></td></tr></table>";
	
	
	String voiceDate = "<html><table><tr><td> <font face=Myriad Pro size=12 color=#565656>"
		+ day 
		+ "</font></td><td><font face=Myriad Pro size=5 color=#565656>"
		+ month
		+ "</font><br><font face=Myriad Pro size=5 color=#565656>"
		+ year + "</font></td></table>";

	// +
	// "</font></td><tr><td><font face=Myriad Pro size=4 color=#00a77f>Sound</font></td><td>"
	// +
	// "<font face=Myriad Pro size=4 color=#565656>Off</font></td></tr></table>";
	// removed sound icon
	// ImageIcon voiceImage = new ImageIcon(getClass().getResource(
	// IconsHome.getIconsHomePath() + "/sound.jpg"));
	setHorizontalTextPosition(SwingConstants.LEFT);
	setText(voiceDate);
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
