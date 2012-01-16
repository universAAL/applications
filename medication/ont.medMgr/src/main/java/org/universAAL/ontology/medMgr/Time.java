package org.universAAL.ontology.medMgr;

/**
 * @author George Fournadjiev
 */
public final class Time {

  private final int year;
  private final int day;
  private final int hour;
  private final int minutes;

  public Time(int year, int day, int hour, int minutes) {
    this.year = year;
    this.day = day;
    this.hour = hour;
    this.minutes = minutes;
  }

  public int getYear() {
    return year;
  }

  public int getDay() {
    return day;
  }

  public int getHour() {
    return hour;
  }

  public int getMinutes() {
    return minutes;
  }

  @Override
  public String toString() {
    return "Time{" +
        "year=" + year +
        ", day=" + day +
        ", hour=" + hour +
        ", minutes=" + minutes +
        '}';
  }
}
