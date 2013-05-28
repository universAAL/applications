package org.universAAL.AALapplication.medication_manager.user.management.impl;

/**
 * @author George Fournadjiev
 */
public final class MedicationManagerUserManagementException extends RuntimeException {

  public MedicationManagerUserManagementException() {
  }

  public MedicationManagerUserManagementException(String message) {
    super(message);
  }

  public MedicationManagerUserManagementException(String message, Throwable cause) {
    super(message, cause);
  }

  public MedicationManagerUserManagementException(Throwable cause) {
    super(cause);
  }
}
