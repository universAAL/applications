package org.universAAL.AALapplication.medication_manager.event.issuer.impl;

/**
 * @author George Fournadjiev
 */
public final class MedicationManagerEventIssuerException extends RuntimeException {

  public MedicationManagerEventIssuerException() {
  }

  public MedicationManagerEventIssuerException(String message) {
    super(message);
  }

  public MedicationManagerEventIssuerException(String message, Throwable cause) {
    super(message, cause);
  }

  public MedicationManagerEventIssuerException(Throwable cause) {
    super(cause);
  }
}
