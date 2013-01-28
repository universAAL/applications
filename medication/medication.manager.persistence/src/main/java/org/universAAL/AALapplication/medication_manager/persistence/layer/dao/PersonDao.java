package org.universAAL.AALapplication.medication_manager.persistence.layer.dao;

import org.universAAL.AALapplication.medication_manager.persistence.impl.Log;
import org.universAAL.AALapplication.medication_manager.persistence.impl.database.AbstractDao;
import org.universAAL.AALapplication.medication_manager.persistence.impl.database.Column;
import org.universAAL.AALapplication.medication_manager.persistence.impl.database.Database;
import org.universAAL.AALapplication.medication_manager.persistence.layer.entities.Person;
import org.universAAL.AALapplication.medication_manager.persistence.layer.entities.Role;

import java.util.Map;

/**
 * @author George Fournadjiev
 */
public final class PersonDao extends AbstractDao {


  static final String TABLE_NAME = "PERSON";

  public PersonDao(Database database) {
    super(database, TABLE_NAME);
  }

  @Override
  @SuppressWarnings("unchecked")
  public Person getById(int id) {
    Log.info("Looking for the person with id=%s", getClass(), id);
    Map<String, Column> columns = getTableColumnsValuesById(id);

    Column col = columns.get("NAME");
    String name = (String) col.getValue();

    col = columns.get("PERSON_URI");
    String personUri = (String) col.getValue();

    col = columns.get("ROLE");
    String roleString = (String) col.getValue();
    Role role = Role.getEnumValueFor(roleString);

    Person person = new Person(id, name, personUri, role);

    Log.info("Person found: %s", getClass(), person);

    return person;

  }

}
