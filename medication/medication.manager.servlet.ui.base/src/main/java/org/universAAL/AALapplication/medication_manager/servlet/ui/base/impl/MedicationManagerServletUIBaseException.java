package org.universAAL.AALapplication.medication_manager.servlet.ui.base.impl;

/**
 * @author George Fournadjiev
 */
public final class MedicationManagerServletUIBaseException extends RuntimeException {

  public MedicationManagerServletUIBaseException() {
  }

  public MedicationManagerServletUIBaseException(String message) {
    super(message);
  }

  public MedicationManagerServletUIBaseException(String message, Throwable cause) {
    super(message, cause);
  }

  public MedicationManagerServletUIBaseException(Throwable cause) {
    super(cause);
  }
}
