package org.universAAL.AALapplication.medication_manager.persistence.layer.dao;

import org.universAAL.AALapplication.medication_manager.persistence.impl.database.AbstractDao;
import org.universAAL.AALapplication.medication_manager.persistence.impl.database.Column;
import org.universAAL.AALapplication.medication_manager.persistence.impl.database.Database;
import org.universAAL.AALapplication.medication_manager.persistence.layer.entities.Dispenser;
import org.universAAL.AALapplication.medication_manager.persistence.layer.entities.Person;

import java.util.Map;

import static org.universAAL.AALapplication.medication_manager.configuration.Util.*;

/**
 * @author George Fournadjiev
 */
public final class DispenserDao extends AbstractDao {

  private final PersonDao personDao;

  private static final String TABLE_NAME = "dispenser";

  public DispenserDao(Database database, PersonDao personDao) {
    super(database, TABLE_NAME);

    validateParameter(personDao, "personDao");

    this.personDao = personDao;
  }

  @Override
  @SuppressWarnings("unchecked")
  public Dispenser getById(int id) {
    Map<String, Column> columns = getTableColumnsValuesById(id);

    Column col = columns.get("PATIENT_FK_ID");
    int personId = (Integer) col.getValue();
    Person person = personDao.getById(personId);


    col = columns.get("DISPENSER_URI");
    String dispenserUri = (String) col.getValue();

    return new Dispenser(id, person, dispenserUri);
  }

  public Dispenser findByPerson(Person person) {

    return new Dispenser(1, person, "uri");
  }
}
