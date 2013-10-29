package org.universAAL.agenda.gui.util;

import java.awt.Color;
import java.awt.Font;

/**
 * Color and font constants used in this gui.
 * 
 * @author eandgrg
 * 
 */

public class GuiConstants {

    public static final Color textActiveBackground = new Color(0xf1ee9d); // yellow
    public static final Color textActiveForeground = new Color(0x00c129); // green
    public static final Color textInactiveBackground = Color.WHITE;
    public static final Color textInActiveForeground = textActiveForeground;
    
    public static final Font bigButtonsBigFont = new Font("MyriadPro",Font.ITALIC, 40);
    public static final Font bigButtonsSmallFont = new Font("MyriadPro",Font.ITALIC, 25);
    public static final Font mediumButtonsFont = new Font("MyriadPro",Font.ITALIC, 25);
    public static final Font mediumButtonsSmallFont = new Font("MyriadPro",Font.ITALIC, 20);
    public static final Font smallButtonsFont = new Font("MyriadPro",Font.ITALIC, 20);
    
    
    public static final Font digitalClockFont = new Font("MyriadPro",Font.PLAIN, 24);
    public static final Color digitalClockColor = new Color(0x0000ff);

    public static final Color labelColor = new Color(0x565656); // dark grey

    // used for breadcrumbs
    public static final Font breadcrumbsLabelFont = new Font("MyriadPro",
	    Font.ITALIC, 25);
    public static final Color breadcrumbsLabelColor = Color.WHITE;

    // used in jCalendar, JEventInfo, JEventList, JSearchEvents,
    // JSelectionCalendar
    public static final Color headerMessageForeground = Color.WHITE;
    public static final Color headerPanelBackground = Color.WHITE;
    public static final Color wholePanelBackground = Color.WHITE;

    // small and medium fonts used in JEventInfo, JSelectionCalendar,
    // ReminderScreen, DatePanel
    public static final Font smallFont = new Font("MyriadPro", Font.PLAIN, 15);
    public static final Font mediumFont = new Font("MyriadPro", Font.PLAIN, 20);
    public static final Font headerFont = new Font("MyriadPro", Font.PLAIN, 35);

    // used in CalendarEntry
    public static final Color calendarEntrySelectedForeground = new Color(0, 0,
	    0, 255);
    public static final Color calendarEntryNotSelectedForeground = new Color(0,
	    0, 0, 100);
    public static final Font calendarEntryLabelFont = new Font("MyriadPro",
	    Font.PLAIN, 25);

    // used in jCalendar
    public static final Color jCalendarWeeksForeground = new Color(100, 100,
	    100); // dark grey
    public static final Color jCalendarWeekDaysForeground = new Color(15, 68,
	    137);
    public static final Color jCalendarWeekDaysPanelBackground = Color.WHITE;
    public static final Color jCalendarWeekDaysBackground = new Color(0xb6d6fe);//light blue
    public static final Color jCalendarDayPanelBackground = Color.WHITE;
    public static final Color jCalendarRightButtonsPanelBackground = Color.WHITE;
    public static final Color jCalendarCurrentDayBackground = new Color(169,
	    224, 169); // light green
    public static final Font jCalendarWeekDaysFont = new Font("MyriadPro",
	    Font.BOLD, 23);

    // used in jDate
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
    public static final Font jDateCurrentDateFont = new Font(
	    "MyriadPro", Font.PLAIN, 32); //$NON-NLS-1$
    public static final Font jDateOtherDateFont = new Font(
	    "MyriadPro", Font.ITALIC, 25); //$NON-NLS-1$

    // used in jDecorator
    public static final Color jDecoratorButtonLine = new Color(214, 215, 148);
    public static final Color jDecoratorButtonForeground = new Color(72, 142,
	    125);
    public static final Color jDecoratorButtonBackground = new Color(182, 214,
	    254);

    // used in jSearchEvents
    public static final Color jSearchEventsTitleColor = new Color(0x565656); // dark
    // grey
    public static final Color jSearchEventsPanelBackground = Color.WHITE;
    public static final Color jSearchEventsBackground = Color.WHITE;
    public static final Color jSearchEventsBlueForeground = Color.BLUE;
    public static final Color jSearchEventsGreenForeground = new Color(0, 102,
	    102);
    public static final Font jSearchEventsFont = new Font("MyriadPro",
	    Font.PLAIN, 25);
    public static final Font jSearchEventsLabelFont = new Font("MyriadPro",
	    Font.PLAIN, 20);
    public static final Font jSearchEventsDummyLabelFont = new Font(
	    "MyriadPro", Font.PLAIN, 15);

    // used in jSelectionCalendar
    public static final Color jSelectionCalendarBorderColor = Color.LIGHT_GRAY;
    public static final Color jSelectionCalendarCausionLabelRed = Color.RED;
    public static final Color jSelectionCalendarCausionLabelGreen = Color.GREEN;
    public static final Color jSelectionCalendarCalendarName = Color.BLUE;
    public static final Color jSelectionCalendarTextFieldBackground = new Color(
	    0xffff64); // for calendar name
    public static final Color jSelectionCalendarRootBorderColor = Color.BLACK;

    // used in ReminderScreen
    public static final Color reminderScreenBorderColor = Color.LIGHT_GRAY;
    public static final Font reminderScreenFont = new Font("MyriadPro",
	    Font.PLAIN, 20);

    // used in ReminderPanel
    public static final Font reminderPanelLabelFont = new Font("MyriadPro",
	    Font.PLAIN, 20);
    public static final Font reminderPanelLabelFontItalic = new Font(
	    "MyriadPro", Font.ITALIC, 20);
    public static final Font reminderPanelTitleFont = new Font("MyriadPro",
	    Font.PLAIN, 25);
    public static final Color reminderPanelTitleColor = new Color(0x565656);

    // used in DatePanel
    public static final Color datePanelNumbersForeground = Color.BLUE;
    public static final Color datePanelBackground = Color.WHITE;

    // used in JEventList
    public static final Font jEventListLabelFont = new Font("MyriadPro",
	    Font.PLAIN, 25);
    public static final Color jEventListLabelColor = Color.GRAY;
    public static final Color jEventListInfoForeground = new Color(0, 167, 127);
    public static final Color jEventListDataForeground = new Color(86, 86, 86);
    public static final Color jEventListMoreInfoForeground = new Color(255,
	    102, 127);
    public static final Color jEventListInfoBackground = Color.WHITE;
    public static final Font jEventListInfoAndDataFont = new Font("MyriadPro",
	    Font.PLAIN, 25);
    public static final Font jEventListMoreInfoFont = new Font("MyriadPro",
	    Font.ITALIC, 25);

    // used in JEventInfo
    public static final Font jEventInfoSectionTitleFont = new Font("MyriadPro",
	    Font.PLAIN, 25);

}
