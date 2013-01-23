package org.universAAL.AALapplication.medication_manager.persistence.layer.dao;

import org.universAAL.AALapplication.medication_manager.persistence.layer.entities.Person;
import org.universAAL.AALapplication.medication_manager.persistence.layer.entities.Role;
import org.universAAL.AALapplication.medication_manager.persistence.impl.database.Database;

import static org.universAAL.AALapplication.medication_manager.configuration.Util.*;

/**
 * @author George Fournadjiev
 */
public abstract class AbstractDao {

  private final Database database;

  protected AbstractDao(Database database) {
    validateParameter(database, "database");

    this.database = database;
  }

  public <T> T get(Class<T> type, int id) {

    System.out.println("aClass.getName() = " + type.getName());

    Person person = new Person(1, "test", "uri", Role.CAREGIVER);
    return (T) person;

  }

}
