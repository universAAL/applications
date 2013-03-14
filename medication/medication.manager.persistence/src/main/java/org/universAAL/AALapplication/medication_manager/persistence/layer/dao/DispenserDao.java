package org.universAAL.AALapplication.medication_manager.persistence.layer.dao;

import org.universAAL.AALapplication.medication_manager.persistence.impl.Log;
import org.universAAL.AALapplication.medication_manager.persistence.impl.MedicationManagerPersistenceException;
import org.universAAL.AALapplication.medication_manager.persistence.impl.database.AbstractDao;
import org.universAAL.AALapplication.medication_manager.persistence.impl.database.Column;
import org.universAAL.AALapplication.medication_manager.persistence.impl.database.Database;
import org.universAAL.AALapplication.medication_manager.persistence.layer.entities.Dispenser;
import org.universAAL.AALapplication.medication_manager.persistence.layer.entities.Person;
import org.universAAL.AALapplication.medication_manager.persistence.layer.entities.Role;

import java.util.Map;

/**
 * @author George Fournadjiev
 */
public final class DispenserDao extends AbstractDao {

  private static final String PATIENT_FK_ID = "PATIENT_FK_ID";
  private static final String DISPENSER_URI = "DISPENSER_URI";
  private static final String ID = "ID";
  private PersonDao personDao;

  private static final String TABLE_NAME = "DISPENSER";

  public DispenserDao(Database database) {
    super(database, TABLE_NAME);

  }

  public void setPersonDao(PersonDao personDao) {
    this.personDao = personDao;
  }

  @Override
  @SuppressWarnings("unchecked")
  public Dispenser getById(int id) {
    Log.info("Looking for the dispenser with id=%s", getClass(), id);

    checkForSetDao(personDao, "personDao");

    Map<String, Column> columns = getTableColumnsValuesById(id);

    Column col = columns.get(PATIENT_FK_ID);
    int personId = (Integer) col.getValue();
    Person person = personDao.getById(personId);


    col = columns.get(DISPENSER_URI);
    String dispenserUri = (String) col.getValue();

    Dispenser dispenser = new Dispenser(id, person, dispenserUri);

    Log.info("Dispenser found: %s", getClass(), dispenser);

    return dispenser;
  }

  public Dispenser findByPerson(Person person) {
    Log.info("Looking for the dispenser with " + PATIENT_FK_ID + "=%s", getClass(), person.getId());

    Role role = person.getRole();
    if (role != Role.PATIENT) {
      throw new MedicationManagerPersistenceException("The person : " + person + " \n\t is not a patient but : " + role);
    }

    int personId = person.getId();
    Map<String, Column> columnMap = database.findDispenserByPerson(TABLE_NAME, PersonDao.TABLE_NAME, personId);

    if (columnMap.isEmpty()) {
      throw new MedicationManagerPersistenceException("There is no such record in the table : " + TABLE_NAME +
          " with personId=" + personId);
    }

    Column col = columnMap.get(ID);
    int id = (Integer) col.getValue();


    col = columnMap.get(DISPENSER_URI);
    String dispenserUri = (String) col.getValue();

    Dispenser dispenser = new Dispenser(id, person, dispenserUri);

    Log.info("Dispenser found: %s", getClass(), dispenser);

    return dispenser;

  }

  public Dispenser getByDispenserUri(String deviceUri) {
    String sql = "select * from MEDICATION_MANAGER.DISPENSER where DISPENSER_URI = '" + deviceUri + "'";

    Map<String, Column> dispenserRecordMap = executeQueryExpectedSingleRecord(TABLE_NAME, sql);

    return getDispenser(dispenserRecordMap);
  }

  private Dispenser getDispenser(Map<String, Column> dispenserRecordMap) {
    checkForSetDao(personDao, "personDao");

    Column colId = dispenserRecordMap.get(ID);
    int id = (Integer) colId.getValue();
    Column colUri = dispenserRecordMap.get(DISPENSER_URI);
    String dispenserUri = (String) colUri.getValue();
    Column colPatient = dispenserRecordMap.get(PATIENT_FK_ID);
    int personId = (Integer) colPatient.getValue();
    Person person = personDao.getById(personId);

    return new Dispenser(id, person, dispenserUri);
  }
}
