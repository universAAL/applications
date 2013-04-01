package org.universAAL.AALapplication.medication_manager.persistence.layer.dto;

import org.universAAL.AALapplication.medication_manager.persistence.impl.MedicationManagerPersistenceException;

import java.util.StringTokenizer;

import static org.universAAL.AALapplication.medication_manager.persistence.impl.Activator.*;

/**
 * @author George Fournadjiev
 */
public final class TimeDTO {

  private final int hour;
  private final int minutes;

  public TimeDTO(int hour, int minutes) {
    if (hour < 0 || hour > 23) {
      throw new MedicationManagerPersistenceException("The hour must be between 0 and 23");
    }

    if (minutes < 0 || minutes > 59) {
      throw new MedicationManagerPersistenceException("The minutes must between 0 and 59");
    }

    this.hour = hour;
    this.minutes = minutes;
  }

  public int getHour() {
    return hour;
  }

  public int getMinutes() {
    return minutes;
  }

  public String getTimeText() {
    return hour + ":" + minutes;
  }

  @Override
  public String toString() {
    return "TimeDTO{" +
        "hour=" + hour +
        ", minutes=" + minutes +
        '}';
  }

  public static TimeDTO createTimeDTO(String time) {

    validateParameter(time, "time");

    time = time.trim();

    String message = "The text time:" + time + " is not a valid time. " +
        "The time must be between 0:00 and 23:59 (in that format)";

    if (time.indexOf(':') == -1) {
      throw new MedicationManagerPersistenceException(message);
    }

    StringTokenizer st = new StringTokenizer(time, ":");

    if (st.countTokens() != 2) {
      throw new MedicationManagerPersistenceException(message);
    }

    int hour = getInt(st, message);

    if (hour < 0 || hour > 23) {
      throw new MedicationManagerPersistenceException(message);
    }

    int minutes = getInt(st, message);

    if (minutes < 0 || minutes > 59) {
      throw new MedicationManagerPersistenceException(message);
    }

    return new TimeDTO(hour, minutes);
  }

  private static int getInt(StringTokenizer st, String message) {
    String text = st.nextToken();

    try {
      return Integer.valueOf(text);
    } catch (NumberFormatException e) {
      throw new MedicationManagerPersistenceException(message);
    }

  }


}
