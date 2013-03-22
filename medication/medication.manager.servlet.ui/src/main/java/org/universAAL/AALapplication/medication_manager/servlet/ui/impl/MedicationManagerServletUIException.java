package org.universAAL.AALapplication.medication_manager.servlet.ui.impl;

/**
 * @author George Fournadjiev
 */
public final class MedicationManagerServletUIException extends RuntimeException {

  public MedicationManagerServletUIException() {
  }

  public MedicationManagerServletUIException(String message) {
    super(message);
  }

  public MedicationManagerServletUIException(String message, Throwable cause) {
    super(message, cause);
  }

  public MedicationManagerServletUIException(Throwable cause) {
    super(cause);
  }
}
