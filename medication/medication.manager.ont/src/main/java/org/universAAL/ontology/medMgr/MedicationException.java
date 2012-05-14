package org.universAAL.ontology.medMgr;

/**
 * @author George Fournadjiev
 */
public final class MedicationException extends RuntimeException {


  private static final long serialVersionUID = -6536506555816585093L;

  public MedicationException(String message) {
    super(message);
  }

  public MedicationException(String message, Throwable cause) {
    super(message, cause);
  }

  public MedicationException(Throwable cause) {
    super(cause);
  }
}
