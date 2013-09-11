package na.utils;

import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import na.miniDao.NutriCalendar;
import na.utils.lang.Messages;
import na.widgets.button.AdaptiveButton;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public final class Utils {

    private Utils() {
    }

    private static Log log = LogFactory.getLog(Utils.class);
    // Date format, used in Postgre DB and configuration files
    private static final String DATE_FORMAT = "yyyy-MM-dd";
    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd=H:m";
    private static final boolean DEVELOPMENT = false;
    static private final int YEAR = 2010;
    static private final int MONTH = 3; // April
    static private final int DAY = 27;
    static private final String TODAY_DATE = "" + YEAR + "-" + (MONTH + 1)
	    + "-" + DAY;

    public static class Strings {

	/**
	 * Return the given String with the first letter in Uppercase. Does not
	 * modify the original String.
	 * 
	 * @param s
	 *            the s
	 * @return the string
	 */
	public static String capitalize(String s) {
	    if ((s == null))
		return s;
	    String copy = new String(s);
	    if (copy.length() > 0) {
		copy = copy.substring(0, 1).toUpperCase()
			+ copy.substring(1).toLowerCase();
	    }
	    return copy;
	}

	/**
	 * Takes a string which contains a number and returns a rounded version
	 * 
	 * @param s
	 *            the s
	 * @return the string
	 */
	public static String roundUp(String s) {
	    Double d = new Double(s);
	    return "" + d.intValue();
	}

	public static String Max40Chars(String courseName) {
	    if (courseName != null && courseName.length() > 40)
		return courseName.substring(0, 40);
	    return courseName;
	}
    }

    public static class Dates {

	static public int getIntToday() {
	    Calendar cal = Dates.getMyCalendar();
	    return cal.get(Calendar.DAY_OF_WEEK);
	}

	static public int getIntTomorrow() {
	    Calendar cal = Dates.getMyCalendar();
	    cal.add(Calendar.DAY_OF_WEEK, 1);
	    return cal.get(Calendar.DAY_OF_WEEK);
	}

	static public Calendar getMyCalendar() {
	    // String timeZone = LoginManager.getTimeZone();
	    // Calendar cal = new
	    // GregorianCalendar(TimeZone.getTimeZone(timeZone));
	    Calendar cal = Calendar.getInstance();
	    // log.info("Current date: " + getStringDateTime(cal));
	    return cal;
	}

	public static Calendar getTodayCalendar() {
	    Calendar today = Utils.Dates.getMyCalendar();
	    if (DEVELOPMENT)
		return getCalendarFromStringDate(TODAY_DATE);
	    return today;
	}

	/**
	 * Returns true if today is the last day of the week the checks if is
	 * last day of week.
	 * 
	 * @return the checks if is last day of week
	 */
	public static boolean getIsLastDayOfWeek() {
	    Calendar todayCal = getTodayCalendar();
	    int today = getIntToday();
	    int firstDay = todayCal.getFirstDayOfWeek();
	    int lastDayOfWeek = firstDay - 1;
	    if (firstDay == Calendar.SUNDAY)
		lastDayOfWeek = Calendar.SATURDAY;
	    return today == lastDayOfWeek;
	}

	public static String getTodayDate() {
	    if (DEVELOPMENT)
		return TODAY_DATE;
	    Format formatter;
	    // Get today's date
	    Calendar cal = Dates.getMyCalendar();
	    Date date = cal.getTime();
	    formatter = new SimpleDateFormat(DATE_FORMAT);
	    return formatter.format(date);
	}

	public static String getStringDate(Calendar cal) {
	    Format formatter;
	    // Get today's date
	    Date date = cal.getTime();
	    formatter = new SimpleDateFormat(DATE_FORMAT);
	    return formatter.format(date);
	}

	public static String getStringDateTime(Calendar cal) {
	    Format formatter;
	    // Get today's date
	    Date date = cal.getTime();
	    formatter = new SimpleDateFormat(DATE_TIME_FORMAT);
	    return formatter.format(date);
	}

	// private static String getTomorrowDateStr() {
	// Format formatter;
	// formatter = new SimpleDateFormat(DATE_FORMAT);
	// return formatter.format(getTomorrowDate());
	// }
	//

	// private static Date getTomorrowDate() {
	// //Get today's date
	// Date date = new Date();
	// if (DEVELOPMENT)
	// date = getFakeDate();
	// Calendar calendario = Utils.Dates.getMyCalendar();
	// calendario.setTime(date);
	// int day = calendario.get(Calendar.DAY_OF_MONTH);
	// //Set tomorrow's date
	// calendario.set(Calendar.DAY_OF_MONTH, day + 1);
	// date = calendario.getTime();
	//
	// return date;
	// }
	//
	// static private String getStringDate(Date date){
	// Format formatter;
	// formatter = new SimpleDateFormat(DATE_FORMAT);
	// return formatter.format(date);
	//
	// }

	// private static String getNextDay(Date day) {
	// Calendar calendario = Utils.Dates.getMyCalendar();
	// calendario.setTime(day);
	// int today = calendario.get(Calendar.DAY_OF_MONTH);
	// //Set next date
	// calendario.set(Calendar.DAY_OF_MONTH, today + 1);
	// return getStringDate(calendario.getTime());
	// }

	private static Date getFakeDate() {
	    Calendar calendario = Calendar.getInstance();
	    calendario.set(YEAR, MONTH, DAY);
	    return calendario.getTime();
	}

	static public Calendar getCalendarFromStringDate(String day) {
	    try {
		DateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
		Date date = (Date) formatter.parse(day);
		Calendar cal = Utils.Dates.getMyCalendar();
		cal.setTime(date);
		return cal;
	    } catch (ParseException e) {
		log.info("Exception :" + e);
	    }
	    return null;
	}

	public static int daysBetween(Calendar startDate, Calendar endDate) {
	    Calendar date = (Calendar) startDate.clone();
	    int daysBetween = 0;
	    while (date.before(endDate)) {
		date.add(Calendar.DAY_OF_MONTH, 1);
		daysBetween++;
	    }
	    return daysBetween;
	}

	/**
	 * Gets today and the next 6 days.
	 * 
	 * @return the next seven days
	 */
	static private Calendar[] getNextSevenDays() {
	    // Get today's date
	    Date date = new Date();
	    if (DEVELOPMENT)
		date = getFakeDate();
	    Calendar calendar = Utils.Dates.getMyCalendar();
	    calendar.setTime(date);
	    Calendar week[] = new Calendar[7];
	    week[0] = calendar;
	    for (int i = 1; i < 7; i++) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DAY_OF_MONTH, i);
		week[i] = cal;
	    }

	    return week;
	}

	static public int[] getNextIntSevenDays() {
	    Calendar[] week = Utils.Dates.getNextSevenDays();
	    int[] intWeek = new int[7];
	    for (int i = 0; i < 7; i++) {
		intWeek[i] = week[i].get(Calendar.DAY_OF_MONTH);
	    }
	    return intWeek;
	}

	public static String getStringMonth(int month) {
	    switch (month) {
	    case Calendar.JANUARY:
		return Messages.Month_January;
	    case Calendar.FEBRUARY:
		return Messages.Month_February;
	    case Calendar.MARCH:
		return Messages.Month_March;
	    case Calendar.APRIL:
		return Messages.Month_April;
	    case Calendar.MAY:
		return Messages.Month_May;
	    case Calendar.JUNE:
		return Messages.Month_June;
	    case Calendar.JULY:
		return Messages.Month_July;
	    case Calendar.AUGUST:
		return Messages.Month_August;
	    case Calendar.SEPTEMBER:
		return Messages.Month_September;
	    case Calendar.OCTOBER:
		return Messages.Month_October;
	    case Calendar.NOVEMBER:
		return Messages.Month_November;
	    case Calendar.DECEMBER:
		return Messages.Month_December;
	    }
	    return "month";
	}

	private static String getStringDay(int day) {
	    // log.info("Day: " + day);
	    switch (day) {
	    case Calendar.MONDAY:
		return Messages.Menus_Calendar_Monday;
	    case Calendar.TUESDAY:
		return Messages.Menus_Calendar_Tuesday;
	    case Calendar.WEDNESDAY:
		return Messages.Menus_Calendar_Wednesday;
	    case Calendar.THURSDAY:
		return Messages.Menus_Calendar_Thursday;
	    case Calendar.FRIDAY:
		return Messages.Menus_Calendar_Friday;
	    case Calendar.SATURDAY:
		return Messages.Menus_Calendar_Saturday;
	    case Calendar.SUNDAY:
		return Messages.Menus_Calendar_Sunday;
	    default:
		return "Unknown day of week";
	    }
	}

	static public String[] getDaysOfWeek() {
	    Calendar cal = Dates.getMyCalendar();
	    int day = cal.getFirstDayOfWeek();
	    // int day = Calendar.SATURDAY;
	    // log.info("First day: "+day + " " + getStringDay(day));
	    String[] weekDays = new String[7];
	    for (int i = 0; i < (8 - day); i++) {
		weekDays[i] = getStringDay(day + i);
	    }
	    for (int i = (8 - day), j = 1; i < 7; i++, j++) {
		weekDays[i] = getStringDay(j);
	    }
	    return weekDays;
	}

	static public String[] getDaysOfWeekFromNow() {
	    Calendar cal = Dates.getMyCalendar();
	    String[] weekDays = new String[7];
	    int day = cal.get(Calendar.DAY_OF_WEEK);
	    for (int i = 0; i < (8 - day); i++) {
		weekDays[i] = getStringDay(day + i);
	    }
	    for (int i = (8 - day), j = 1; i < 7; i++, j++) {
		weekDays[i] = getStringDay(j);
	    }
	    return weekDays;
	}

	static public int[] getIntDaysOfWeek() {
	    Calendar cal = Dates.getMyCalendar();
	    int day = cal.getFirstDayOfWeek();
	    // log.info("First day: "+day + " " + getStringDay(day));
	    int[] weekDays = new int[7];

	    for (int i = 0; i < 7; i++) {
		weekDays[i] = day + i;
		if (weekDays[i] > 7)
		    weekDays[i] = weekDays[i] - 7;
	    }
	    // int i=0;
	    // for (; i<(8-day); i++) {
	    // weekDays[i] = day+i-1;
	    // }
	    // for (int j=0; (j+i)<day; j++) {
	    // weekDays[i+j] = day+j-1;
	    // }
	    // log.info("Days: ");
	    // for (int r=0; r<weekDays.length; r++) {
	    // log.info(""+weekDays[r]+", ");
	    // }
	    // log.info("");
	    return weekDays;
	}

	public static MiniCalendar getCurrentAdviceTime() {
	    Calendar cal = Dates.getMyCalendar();
	    TimeZone cetTime = TimeZone.getTimeZone("CET");
	    cal.setTimeZone(cetTime);
	    MiniCalendar mini = new MiniCalendar();
	    mini.year = cal.get(Calendar.YEAR);
	    mini.month = cal.get(Calendar.MONTH);
	    mini.day = cal.get(Calendar.DAY_OF_MONTH);
	    mini.hour = cal.get(Calendar.HOUR_OF_DAY);
	    mini.minute = cal.get(Calendar.MINUTE);

	    return mini;
	}

	public static boolean isSameDay(Calendar cal1, Calendar cal2) {
	    if (cal1 == null || cal2 == null) {
		throw new IllegalArgumentException("The dates must not be null");
	    }
	    return (cal1.get(Calendar.ERA) == cal2.get(Calendar.ERA)
		    && cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) && cal1
			.get(Calendar.DAY_OF_YEAR) == cal2
		    .get(Calendar.DAY_OF_YEAR));
	}

	public static Calendar getJavaCalendar(NutriCalendar n) {
	    Calendar cal = Calendar.getInstance();
	    cal.setTimeZone(TimeZone.getTimeZone(n.getTimeZone()));
	    cal.setTimeInMillis(n.getTimeInMillis());
	    return cal;
	}
    }

    public static class Errors {
	public static ErrorWindow showServiceNotAvailableError(String message) {
	    ErrorWindow error = new ErrorWindow();
	    error.label.setText(message);
	    return error;
	}

	public static void showError(JPanel parentt, String message) {
	    Utils.draw(parentt,
		    Utils.Errors.showServiceNotAvailableError(message));
	}

	public static void showErrorPopup(String text) {
	    JOptionPane.showMessageDialog(null, text, "Error",
		    JOptionPane.ERROR_MESSAGE);
	}

    }

    public static void removeActionsListeners(AdaptiveButton button) {
	ActionListener[] als = button.getActionListeners();
	if (als != null && als.length > 0) {
	    for (ActionListener actionListener : als) {
		button.removeActionListener(actionListener);
	    }
	}
    }

    private static void draw(JPanel parentt, JPanel child) {
	parentt.removeAll(); // borra todo el contenido del panel sobre el que
			     // muestra el mensaje
	parentt.add(child);
	parentt.validate();
	parentt.repaint();
    }

    public static void showLoginErrorPopup(String text) {
	JOptionPane.showMessageDialog(null,
		Messages.Connection_errorLoginNutritional + ": " + text,
		"Error", JOptionPane.ERROR_MESSAGE);
    }

    public static void showConnectionErrorPopup(String text) {
	JOptionPane.showMessageDialog(null,
		Messages.Connection_errorNutritional + ": " + text, "Error",
		JOptionPane.ERROR_MESSAGE);
    }

    // public static int showPopUp(String title, String text, int type) {
    // int result = JOptionPane.showConfirmDialog(
    // null,
    // text,
    // title,
    // JOptionPane.YES_NO_OPTION
    // );
    // return result;
    // }

    public static void println(String msg) {
	System.out.println("Nutri: " + msg);
    }

    public static int showPopUp(String title, String text, int yesNoOption,
	    JPanel parent) {
	Utils.println("parent: " + parent.getName());
	int result = JOptionPane.showConfirmDialog(parent, text, title,
		JOptionPane.YES_NO_OPTION);
	return result;
    }
}
