package org.universAAL.AALapplication.contact_manager.persistence.layer;

import org.universAAL.AALapplication.contact_manager.persistence.impl.ContactManagerPersistenceException;

/**
 * @author George Fournadjiev
 */
public final class Util {

  private Util() {
    // to prevent initialization, because this is util class
  }

  public static void validateParameter(int parameter, String parameterName) {

    if (parameter <= 0) {
      throw new ContactManagerPersistenceException("The parameter : " +
          parameterName + " must be positive number");
    }

  }

  public static void validateParameter(Object parameter, String parameterName) {

    if (parameter == null) {
      throw new ContactManagerPersistenceException("The parameter : " + parameterName + " cannot be null");
    }

  }

}
