package org.universAAL.AALapplication.medication_manager.persistence.layer.dao;

import org.universAAL.AALapplication.medication_manager.persistence.impl.Log;
import org.universAAL.AALapplication.medication_manager.persistence.impl.MedicationManagerPersistenceException;
import org.universAAL.AALapplication.medication_manager.persistence.impl.database.AbstractDao;
import org.universAAL.AALapplication.medication_manager.persistence.impl.database.Column;
import org.universAAL.AALapplication.medication_manager.persistence.impl.database.Database;
import org.universAAL.AALapplication.medication_manager.persistence.layer.entities.Dispenser;
import org.universAAL.AALapplication.medication_manager.persistence.layer.entities.Person;
import org.universAAL.AALapplication.medication_manager.persistence.layer.entities.Role;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;

import static org.universAAL.AALapplication.medication_manager.persistence.impl.Activator.*;


/**
 * @author George Fournadjiev
 */
public final class PersonDao extends AbstractDao {

  private DispenserDao dispenserDao;

  public static final String NAME = "NAME";
  public static final String PERSON_URI = "PERSON_URI";
  public static final String ROLE = "ROLE";
  public static final String CAREGIVER_SMS = "CAREGIVER_SMS";

  static final String TABLE_NAME = "PERSON";

  public PersonDao(Database database) {
    super(database, TABLE_NAME);
  }

  public void setDispenserDao(DispenserDao dispenserDao) {
    this.dispenserDao = dispenserDao;
  }

  @Override
  @SuppressWarnings("unchecked")
  public Person getById(int id) {
    Log.info("Looking for the person with id=%s", getClass(), id);
    Map<String, Column> columns = getTableColumnsValuesById(id);

    return getPerson(columns);

  }

  private Person getPerson(Map<String, Column> columns) {
    Column col = columns.get(ID);
    int personId = (Integer) col.getValue();

    col = columns.get(NAME);
    String name = (String) col.getValue();

    col = columns.get(PERSON_URI);
    String personUri = (String) col.getValue();

    col = columns.get(ROLE);
    String roleString = (String) col.getValue();
    Role role = Role.getEnumValueFor(roleString);

    col = columns.get(CAREGIVER_SMS);
    String caregiverSms = (String) col.getValue();

    Person person = new Person(personId, name, personUri, role, caregiverSms);

    Log.info("Person found: %s", getClass(), person);

    return person;
  }

  public Person findPersonByPersonUri(String personUri) {
    Log.info("Looking for the person with personUri=%s", getClass(), personUri);

    String sql = "select * from MEDICATION_MANAGER.PERSON where UPPER(PERSON_URI) = UPPER('" + personUri + "')";

    Map<String, Column> personRecordMap = executeQueryExpectedSingleRecord(TABLE_NAME, sql);

    if (personRecordMap == null || personRecordMap.isEmpty()) {
      throw new MedicationManagerPersistenceException("Missing the record in the table:" +
          TABLE_NAME + " for the following sql:\n" + sql);
    }

    return getPerson(personRecordMap);
  }

  public Person findPersonByDeviceUri(String deviceUri) {
    Log.info("Looking for the person with deviceUri=%s", getClass(), deviceUri);

    checkForSetDao(dispenserDao, "dispenserDao");

    Dispenser dispenser = dispenserDao.getByDispenserUri(deviceUri);

    if (dispenser == null) {
      throw new MedicationManagerPersistenceException("There is no record for a " +
          "dispenser with the following uri:" + deviceUri);
    }

    return dispenser.getPatient();
  }

  public Person findDoctor(String username, String password) {
    Log.info("Looking for the doctor with username=%s", getClass(), username);

    String sql = "select * from MEDICATION_MANAGER.PERSON where UPPER(USERNAME) = ? AND " +
        "PASSWORD = ? AND UPPER(ROLE) = ?";

    PreparedStatement ps = null;

    try {
      ps = getPreparedStatement(sql);
      ps.setString(1, username.trim().toUpperCase());
      ps.setString(2, password);
      ps.setString(3, Role.PHYSICIAN.getValue());
      Map<String, Column> personRecordMap = executeQueryExpectedSingleRecord(TABLE_NAME, ps);
      if (personRecordMap == null || personRecordMap.isEmpty()) {
        return null;
      }
      return getPerson(personRecordMap);
    } catch (SQLException e) {
      throw new MedicationManagerPersistenceException(e);
    } finally {
      closeStatement(ps);
    }


  }
}
