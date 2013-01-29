package org.universAAL.AALapplication.medication_manager.impl;

/**
 * @author George Fournadjiev
 */
public final class MedicationManagerException extends RuntimeException {

  public MedicationManagerException() {
  }

  public MedicationManagerException(String message) {
    super(message);
  }

  public MedicationManagerException(String message, Throwable cause) {
    super(message, cause);
  }

  public MedicationManagerException(Throwable cause) {
    super(cause);
  }
}
