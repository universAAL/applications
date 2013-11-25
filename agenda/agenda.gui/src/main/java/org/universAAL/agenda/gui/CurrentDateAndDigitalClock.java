package org.universAAL.agenda.gui;

import java.awt.GridLayout;
import java.util.Calendar;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import org.universAAL.agenda.gui.util.GuiConstants;

public class CurrentDateAndDigitalClock {


    private static final long serialVersionUID = -7035246373415204037L;

    public CurrentDateAndDigitalClock() {

    }
    
   
    protected static JPanel  dateAndDigitalClockPanel=null;
    
//  public static JPanel getDateAndDigitalClockPanel(){
//      if (dateAndDigitalClockPanel==null)
//	  dateAndDigitalClockPanel=createDateAndDigitalClockPanel();
//      return dateAndDigitalClockPanel;
//  }
  public static JPanel getDateAndDigitalClockPanel(){
     return createDateAndDigitalClockPanel();
      
  }
    
    protected static JPanel createDateAndDigitalClockPanel(){
	JPanel jpanel=new JPanel(new GridLayout(2, 1, 1, 0));
	jpanel.add (createDateLabel());
	jpanel.add(createDigitalClockLabel());
	jpanel.setBackground(GuiConstants.wholePanelBackground);
	jpanel.setAlignmentY(SwingConstants.TOP);
	jpanel.setVisible(true);
	return jpanel;
    }

    protected static JLabel createDigitalClockLabel() {
	JLabel label =new DigitalClock();
	label.setFont(GuiConstants.digitalClockFont);
	label.setForeground(GuiConstants.digitalClockColor);
	label.setHorizontalAlignment(SwingConstants.CENTER);
	return label;
    }

    protected static JLabel createDateLabel() {
	JLabel label = new JLabel();
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
	label.setHorizontalAlignment(SwingConstants.CENTER);
	label.setText(date);
	return label;
    }

    protected static String monthChange(String month) {
	String monthLetters = null;
	if (month.equals("01"))
	    monthLetters = Messages.getString("CurrentDateAndDigitalClock.January");
	else if (month.equals("02"))
	    monthLetters = Messages.getString("CurrentDateAndDigitalClock.February");
	else if (month.equals("03"))
	    monthLetters = Messages.getString("CurrentDateAndDigitalClock.March");
	else if (month.equals("04"))
	    monthLetters = Messages.getString("CurrentDateAndDigitalClock.April");
	else if (month.equals("05"))
	    monthLetters = Messages.getString("CurrentDateAndDigitalClock.May");
	else if (month.equals("06"))
	    monthLetters = Messages.getString("CurrentDateAndDigitalClock.June");
	else if (month.equals("07"))
	    monthLetters = Messages.getString("CurrentDateAndDigitalClock.July");
	else if (month.equals("08"))
	    monthLetters = Messages.getString("CurrentDateAndDigitalClock.August");
	else if (month.equals("09"))
	    monthLetters = Messages.getString("CurrentDateAndDigitalClock.September");
	else if (month.equals("10"))
	    monthLetters = Messages.getString("CurrentDateAndDigitalClock.October");
	else if (month.equals("11"))
	    monthLetters = Messages.getString("CurrentDateAndDigitalClock.November");
	else if (month.equals("12"))
	    monthLetters = Messages.getString("CurrentDateAndDigitalClock.December");

	return monthLetters;

    }

}
