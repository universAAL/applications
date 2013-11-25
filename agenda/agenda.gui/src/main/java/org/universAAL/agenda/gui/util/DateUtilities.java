package org.universAAL.agenda.gui.util;

import java.util.Calendar;
import java.util.Locale;

public class DateUtilities {
    public static String[] months = new String[] {
	    Messages.getString("DateUtilities.January"), Messages.getString("DateUtilities.February"), Messages.getString("DateUtilities.March"), Messages.getString("DateUtilities.April"), Messages.getString("DateUtilities.May"), Messages.getString("DateUtilities.June"), Messages.getString("DateUtilities.July"), Messages.getString("DateUtilities.August"), Messages.getString("DateUtilities.September"), Messages.getString("DateUtilities.October"), Messages.getString("DateUtilities.November"), Messages.getString("DateUtilities.December") }; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$ //$NON-NLS-7$ //$NON-NLS-8$ //$NON-NLS-9$ //$NON-NLS-10$ //$NON-NLS-11$ //$NON-NLS-12$

    public static String forEnglishLangReturnDayEnding(String dayNumber) {

	if (Locale.getDefault().getLanguage() == Locale.ENGLISH.getLanguage()) {
	    if (dayNumber.endsWith("11") || dayNumber.endsWith("12") || dayNumber.endsWith("13")) //$NON-NLS-1$
		return "th"; //$NON-NLS-1$
	    if (dayNumber.endsWith("1")) //$NON-NLS-1$
		return "st"; //$NON-NLS-1$
	    if (dayNumber.endsWith("2")) //$NON-NLS-1$
		return "nd"; //$NON-NLS-1$
	    if (dayNumber.endsWith("3")) //$NON-NLS-1$
		return "rd"; //$NON-NLS-1$
	    return "th"; //$NON-NLS-1$
	} else
	    return "";
    }

    public static Calendar dti2calendar(DateTimeInstance dti) {
	if (dti == null)
	    return null;

	Calendar c = Calendar.getInstance();
	DateInstance date = dti.date;
	TimeInstance time = dti.time;

	c.set(Calendar.YEAR, date.year);
	c.set(Calendar.MONTH, date.month - 1);
	c.set(Calendar.DAY_OF_MONTH, Math.min(date.day, c
		.getActualMaximum(Calendar.DAY_OF_MONTH)));
	c.set(Calendar.HOUR_OF_DAY, time.hour);
	c.set(Calendar.MINUTE, time.minute);
	c.set(Calendar.SECOND, time.second);

	return c;
    }

    public static DateTimeInstance calendar2dti(Calendar c) {
	if (c == null)
	    return null;

	int year = c.get(Calendar.YEAR);
	int month = c.get(Calendar.MONTH) + 1;
	int day = c.get(Calendar.DAY_OF_MONTH);
	int hour = c.get(Calendar.HOUR_OF_DAY);
	int minute = c.get(Calendar.MINUTE);
	return new DateTimeInstance(new DateInstance(year, month, day),
		new TimeInstance(hour, minute));
    }
}
