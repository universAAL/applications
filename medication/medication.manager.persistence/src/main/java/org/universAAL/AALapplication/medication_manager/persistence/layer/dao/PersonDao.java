package org.universAAL.AALapplication.medication_manager.persistence.layer.dao;

import org.universAAL.AALapplication.medication_manager.persistence.layer.entities.Person;
import org.universAAL.AALapplication.medication_manager.persistence.layer.entities.Role;
import org.universAAL.AALapplication.medication_manager.persistence.impl.database.Database;

/**
 * @author George Fournadjiev
 */
public final class PersonDao extends AbstractDao {


  public PersonDao(Database database) {
    super(database);
  }

  public Person get(int id) {
    return get(Person.class, id);
  }

  public Person getByName(String name) {

    return new Person(11, "1", "2", Role.PHYSICIAN);

  }
}
