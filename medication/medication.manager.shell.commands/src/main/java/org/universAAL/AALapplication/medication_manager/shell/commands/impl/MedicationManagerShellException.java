package org.universAAL.AALapplication.medication_manager.shell.commands.impl;

/**
 * @author George Fournadjiev
 */
public final class MedicationManagerShellException extends RuntimeException {


  public MedicationManagerShellException() {
  }

  public MedicationManagerShellException(String message) {
    super(message);
  }

  public MedicationManagerShellException(String message, Throwable cause) {
    super(message, cause);
  }

  public MedicationManagerShellException(Throwable cause) {
    super(cause);
  }
}
