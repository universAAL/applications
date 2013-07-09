package org.universAAL.AALapplication.medication_manager.persistence.layer;

import org.universAAL.AALapplication.medication_manager.persistence.impl.MedicationManagerPersistenceException;

import static org.universAAL.AALapplication.medication_manager.persistence.impl.Activator.*;

/**
 * @author George Fournadjiev
 */
public final class IntakeInfo {

  private final String date;
  private final String time;
  private final String medication;
  private final String status;

  public static final String COMING = "coming";
  public static final String MISSED = "missed";
  public static final String TAKEN = "taken";

  public IntakeInfo(String date, String time, String medication, String status) {

    validateParameters(date, time, medication, status);

    this.date = date;
    this.time = time;
    this.medication = medication;
    this.status = status;
  }

  private void validateParameters(String date, String time, String medication, String status) {
    validateParameter(date, "date");
    validateParameter(time, "time");
    validateParameter(medication, "medication");
    validateParameter(status, "status");

    boolean statusOK = status.equals(COMING) || status.equals(MISSED) || status.equals(TAKEN);
    if (!statusOK) {
      throw new MedicationManagerPersistenceException("The status is incorrect : " + status +
          ". It can be one of the following: " + COMING + ", " + MISSED + ", " + TAKEN);
    }

  }

  public String getDate() {
    return date;
  }

  public String getTime() {
    return time;
  }

  public String getMedication() {
    return medication;
  }

  public String getStatus() {
    return status;
  }

  @Override
  public String toString() {
    return "IntakeInfo{" +
        "date='" + date + '\'' +
        ", time='" + time + '\'' +
        ", medication='" + medication + '\'' +
        ", status='" + status + '\'' +
        '}';
  }
}
