/*******************************************************************************
 * Copyright 2012 , http://www.prosyst.com - ProSyst Software GmbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/

package org.universAAL.AALapplication.medication_manager.persistence.layer;

import org.universAAL.AALapplication.medication_manager.persistence.impl.Activator;
import org.universAAL.AALapplication.medication_manager.persistence.impl.MedicationManagerPersistenceException;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * @author George Fournadjiev
 */
public final class Week {

  private final Timestamp begin;
  private final Timestamp now;
  private final Timestamp end;

  private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("d.M.yyyy");
  private static final SimpleDateFormat SIMPLE_DATE_FORMAT_FULL = new SimpleDateFormat("d.M.yyyy HH:mm");
  private static final DateFormat DATE_FORMATTER = DateFormat.getDateInstance(DateFormat.DEFAULT);
  private static final DateFormat DATE_FULL_FORMATTER = DateFormat.getDateTimeInstance(DateFormat.DEFAULT, DateFormat.SHORT);

  private Week(Timestamp begin, Timestamp end, Timestamp now) {

//    validateParameter(begin, "begin");
//    validateParameter(end, "end");

    this.begin = begin;
    this.end = end;
    this.now = now;
  }

  public Timestamp getBegin() {
    return begin;
  }

  public Timestamp getNow() {
    return now;
  }

  public Timestamp getEnd() {
    return end;
  }

  @Override
  public String toString() {
    return getFormattedTextWeek(this);
  }

  private static String getFormattedTextWeek(Week week) {
    Date startDate = week.getBegin();
    Date endDate = week.getEnd();


    String startDayText = DATE_FORMATTER.format(startDate);
    String endDayText = DATE_FORMATTER.format(endDate);
    DateFormatSymbols dfs = DateFormatSymbols.getInstance();
    String dayOfWeek = getCurrentDayOfWeek(dfs);
    Date now = new Date();

    String currentDataFull = DATE_FULL_FORMATTER.format(now);

    return Activator.getMessage("medication.manager.week.title", startDayText, endDayText, dayOfWeek, currentDataFull);


  }

  public static String getUsLocaleFormattedTextWeek(Week week) {

    Date startDate = week.getBegin();
    Date endDate = week.getEnd();


    StringBuffer sb = new StringBuffer();
    sb.append("Start date: Monday ");
    String startDayText = SIMPLE_DATE_FORMAT.format(startDate);
    sb.append(startDayText);

    sb.append("; End date: Sunday ");
    String endDayText = SIMPLE_DATE_FORMAT.format(endDate);
    sb.append(endDayText);

    sb.append("; Current time: ");

    DateFormatSymbols dfs = DateFormatSymbols.getInstance(Locale.ENGLISH);
    String dayOfWeek = getCurrentDayOfWeek(dfs);

    sb.append(dayOfWeek);
    sb.append(' ');

    Date now = new Date();

    sb.append(SIMPLE_DATE_FORMAT_FULL.format(now));

    return sb.toString();


  }

  private static String getCurrentDayOfWeek(DateFormatSymbols dfs) {

    String weekdays[] = dfs.getWeekdays();

    Calendar cal = Calendar.getInstance();
    int day = cal.get(Calendar.DAY_OF_WEEK);

    String nameOfDay = weekdays[day];

    return nameOfDay;

  }

  public static Week createWeek(Calendar selectedWeekStartDay) {

    if (selectedWeekStartDay.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY) {
      throw new MedicationManagerPersistenceException("The currentWeekMonday parameter is not MONDAY");
    }

    selectedWeekStartDay.set(Calendar.HOUR_OF_DAY, 0);
    selectedWeekStartDay.set(Calendar.MINUTE, 0);
    selectedWeekStartDay.set(Calendar.SECOND, 0);
    selectedWeekStartDay.set(Calendar.MILLISECOND, 0);

    Date beginDate = selectedWeekStartDay.getTime();
    Timestamp begin = new Timestamp(beginDate.getTime());

    Calendar endCalendar = Calendar.getInstance();
    endCalendar.setTime(beginDate);
    endCalendar.add(Calendar.DAY_OF_YEAR, 6);

    if (endCalendar.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
      throw new MedicationManagerPersistenceException("The endCalendar is not SUNDAY");
    }

    endCalendar.set(Calendar.HOUR_OF_DAY, 23);
    endCalendar.set(Calendar.MINUTE, 59);
    endCalendar.set(Calendar.SECOND, 59);
    endCalendar.set(Calendar.MILLISECOND, 999);

    Date endDate = endCalendar.getTime();
    Timestamp end = new Timestamp(endDate.getTime());

    Calendar today = Calendar.getInstance();
    today.setTime(new Date());

    Timestamp now = null;

    if (today.after(selectedWeekStartDay) && today.before(endCalendar)) {
      Date nowDate = today.getTime();
      now = new Timestamp(nowDate.getTime());
    }

    return new Week(begin, end, now);
  }

  public static Calendar getMondayOfTheCurrentWeek() {
    Calendar now = Calendar.getInstance();
    now.setFirstDayOfWeek(Calendar.MONDAY);
    now.setTime(new Date());
    int weekday = now.get(Calendar.DAY_OF_WEEK);
    if (weekday != Calendar.MONDAY) {
      now.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
    }
    return now;
  }
}
