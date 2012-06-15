package org.universAAL.agenda.gui.util;

import java.awt.Color;
import java.awt.Font;
import java.util.Calendar;

import javax.swing.BorderFactory;
import javax.swing.border.Border;

public class GuiConstants {
    // colors
    public static final Color textActiveBackground = new Color(0xf1ee9d); // yellow
    public static final Color textActiveForeground = new Color(0x00c129); // green
    public static final Color textInactiveBackground = Color.WHITE;
    public static final Color textInActiveForeground = textActiveForeground;
    public static final Color jCalendarWeeksForeground = new Color(100, 100,
	    100); // dark grey

    public static final Color jCalendarWeekDaysForeground = new Color(15, 68,
	    137);
    
    //used in jCalendar, JEventInfo, JEventList
    public static final Color headerMessageForeground = Color.WHITE;
    public static final Color headerPanelBackground = Color.WHITE;
    public static final Color wholePanelBackground = Color.WHITE;
   
    public static final Color jCalendarWeekDaysPanelBackground = Color.WHITE;
    public static final Color jCalendarWeekDaysBackground = new Color(0xb6d6fe);
    public static final Color jCalendarDayPanelBackground = Color.WHITE;
    public static final Color jCalendarRightButtonsPanelBackground = Color.WHITE;
    public static final Color jCalendarCurrentDayBackground = new Color(169,
	    224, 169); // light green

    public static final Color jDateActiveFGColor = new Color(15, 68, 137);
    public static final Color jDateInactiveBGColor = new Color(240, 240, 240);

    public static final Color jDateOtherDaysBackground = Color.WHITE;

    public static final Color jDateHasStoredEventsBackground = new Color(
	    0xded6f1);
    public static final Color jDateTooltipBackground = new Color(250, 250, 180,
	    150);

    public static final Color jDateLineColor = new Color(0xaabbaa);
    public static final Color jDateMouseEnteredForeground = Color.BLACK;
    public static final Color jDateTooltipLineColor = new Color(0, 0, 0, 180);

    public static final Color jDecoratorButtonLine = new Color(214, 215, 148);
    public static final Color jDecoratorButtonForeground = new Color(72, 142,
	    125);
    public static final Color jDecoratorButtonBackground = new Color(182, 214,
	    254);

    public static final Color labelColor = new Color(0x565656); // dark grey

    public static final Color breadcrumbsLabelColor = Color.WHITE;

    public static final Color datePanelNumbersForeground = Color.BLUE;
    public static final Color datePanelBackground = Color.WHITE;

    public static final Font jEventListLabelFont= new Font("MyriadPro", Font.PLAIN, 25);
    public static final Color jEventListLabelColor = Color.GRAY;
    
    public static final Color jEventListInfoForeground =new Color(0, 167, 127);
	public static final Color jEventListDataForeground =new Color(86, 86, 86);
	    public static final Color jEventListMoreInfoForeground = new Color(255, 102, 127);
    
    public static final Color jEventListInfoBackground = Color.WHITE;
    
    
    // Fonts
    public static final Font breadcrumbsLabelFont = new Font("MyriadPro",
	    Font.ITALIC, 25);
    // small and medium fonts used in JEventInfo, JSelectionCalendar,
    // ReminderScreen, DatePanel
    public static final Font smallFont = new Font("MyriadPro", Font.PLAIN, 15);
    public static final Font mediumFont = new Font("MyriadPro", Font.PLAIN, 20);
    
    public static final Font jEventInfoSectionTitleFont = new Font("MyriadPro", Font.PLAIN, 25);
    
    public static final Font jEventListInfoAndDataFont = new Font("MyriadPro", Font.PLAIN, 25);
    
    public static final Font jEventListMoreInfoFont = new Font("MyriadPro", Font.ITALIC, 25);

    public static final Font headerFont = new Font("MyriadPro", Font.PLAIN, 35);
    public static final Font jCalendarWeekDaysFont = new Font("MyriadPro",
	    Font.BOLD, 23);
    public static final Font jDateCurrentDateFont = new Font(
	    "MyriadPro", Font.PLAIN, 32); //$NON-NLS-1$

    public static final Font jDateOtherDateFont = new Font(
	    "MyriadPro", Font.ITALIC, 25); //$NON-NLS-1$

    // Borders
    public static final Border popupBorder = BorderFactory
	    .createLineBorder(Color.black);

    public static void main(String[] str) {
	Calendar c = Calendar.getInstance();
	c.set(Calendar.YEAR, 2012);
	c.set(Calendar.MONTH, 1);
	System.out.println("Max days: "
		+ c.getActualMaximum(Calendar.DAY_OF_MONTH)); // 29
    }
}
