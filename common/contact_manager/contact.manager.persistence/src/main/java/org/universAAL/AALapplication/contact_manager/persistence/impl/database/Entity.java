package org.universAAL.AALapplication.contact_manager.persistence.impl.database;

import org.universAAL.AALapplication.contact_manager.persistence.impl.ContactManagerPersistenceException;

/**
 * @author George Fournadjiev
 */
public abstract class Entity {

  private final int id;

  protected Entity(int id) {
    validateId(id, "id");

    this.id = id;
  }

  protected Entity() {
    this(0);
  }

  private void validateId(int parameter, String parameterName) {

    if (parameter < 0) {
      throw new ContactManagerPersistenceException("The parameter : " +
          parameterName + " cannot be negative number");
    }

  }

  public int getId() {
    return id;
  }
}
