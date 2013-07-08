package org.universAAL.AALapplication.medication_manager.servlet.ui.review.impl;

/**
 * @author George Fournadjiev
 */
public final class MedicationManagerReviewException extends RuntimeException {

  public MedicationManagerReviewException() {
  }

  public MedicationManagerReviewException(String message) {
    super(message);
  }

  public MedicationManagerReviewException(String message, Throwable cause) {
    super(message, cause);
  }

  public MedicationManagerReviewException(Throwable cause) {
    super(cause);
  }
}
