package org.universAAL.AALapplication.contact_manager.impl;

/**
 * @author George Fournadjiev
 */
public final class ContactManagerException extends RuntimeException {

  public ContactManagerException() {
  }

  public ContactManagerException(String message) {
    super(message);
  }

  public ContactManagerException(String message, Throwable cause) {
    super(message, cause);
  }

  public ContactManagerException(Throwable cause) {
    super(cause);
  }
}
