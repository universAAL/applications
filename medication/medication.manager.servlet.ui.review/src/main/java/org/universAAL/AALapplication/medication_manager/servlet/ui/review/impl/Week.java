package org.universAAL.AALapplication.medication_manager.servlet.ui.review.impl;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

import static org.universAAL.AALapplication.medication_manager.servlet.ui.review.impl.Util.*;

/**
 * @author George Fournadjiev
 */
public final class Week {

  private final Timestamp begin;
  private final Timestamp now;
  private final Timestamp end;

  private Week(Timestamp begin, Timestamp end, Timestamp now) {

    validateParameter(begin, "begin");
    validateParameter(end, "end");

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
    return "Week{" +
        "begin=" + begin +
        ", now=" + now +
        ", end=" + end +
        '}';
  }

  public static Week createWeek(Calendar selectedWeekStartDay) {

    if (selectedWeekStartDay.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY) {
      throw new MedicationManagerReviewException("The currentWeekMonday parameter is not MONDAY");
    }

    selectedWeekStartDay.set(Calendar.HOUR, 0);
    selectedWeekStartDay.set(Calendar.MINUTE, 0);
    selectedWeekStartDay.set(Calendar.SECOND, 0);
    selectedWeekStartDay.set(Calendar.MILLISECOND, 0);

    Date beginDate = selectedWeekStartDay.getTime();
    Timestamp begin = new Timestamp(beginDate.getTime());

    Calendar endCalendar = Calendar.getInstance();
    endCalendar.setTime(beginDate);
    endCalendar.add(Calendar.DAY_OF_YEAR, 6);

    if (endCalendar.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
      throw new MedicationManagerReviewException("The endCalendar is not SUNDAY");
    }

    endCalendar.set(Calendar.HOUR, 23);
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
}
