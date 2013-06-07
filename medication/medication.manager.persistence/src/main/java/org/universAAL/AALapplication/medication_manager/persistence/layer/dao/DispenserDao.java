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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.universAAL.AALapplication.medication_manager.persistence.impl.Activator.*;

/**
 * @author George Fournadjiev
 */
public final class DispenserDao extends AbstractDao {

  private static final String PATIENT_FK_ID = "PATIENT_FK_ID";
  private static final String NAME = "NAME";
  private static final String DISPENSER_URI = "DISPENSER_URI";
  private static final String ID = "ID";
  private static final String INSTRUCTIONS_FILE_NAME = "INSTRUCTIONS_FILE_NAME";
  private static final String DUE_INTAKE_ALERT = "DUE_INTAKE_ALERT";
  private static final String SUCCESSFUL_INTAKE_ALERT = "SUCCESSFUL_INTAKE_ALERT";
  private static final String MISSED_INTAKE_ALERT = "MISSED_INTAKE_ALERT";
  private static final String UPSIDE_DOWN_ALERT = "UPSIDE_DOWN_ALERT";
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
    Integer personId = (Integer) col.getValue();
    Person person;
    if (personId != null && personId > 0) {
      person = personDao.getById(personId);
    } else {
      person = null;
    }

    col = columns.get(NAME);
    String name = (String) col.getValue();

    col = columns.get(DISPENSER_URI);
    String dispenserUri = (String) col.getValue();

    col = columns.get(INSTRUCTIONS_FILE_NAME);
    String instructionsFileName = (String) col.getValue();

    col = columns.get(DUE_INTAKE_ALERT);
    boolean dueIntakeAlert = (Boolean) col.getValue();

    col = columns.get(SUCCESSFUL_INTAKE_ALERT);
    boolean successfulIntakeAlert = (Boolean) col.getValue();

    col = columns.get(MISSED_INTAKE_ALERT);
    boolean missedIntakeAlert = (Boolean) col.getValue();

    col = columns.get(UPSIDE_DOWN_ALERT);
    boolean upsideDownAlert = (Boolean) col.getValue();


    Dispenser dispenser = new Dispenser(id, person, name, dispenserUri, instructionsFileName, dueIntakeAlert,
        successfulIntakeAlert, missedIntakeAlert, upsideDownAlert);

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

    Dispenser dispenser = getDispenser(person, columnMap);

    Log.info("Dispenser found: %s", getClass(), dispenser);

    return dispenser;

  }

  public Dispenser getDispenserByPerson(Person person) {
    Log.info("Looking for the dispenser with " + PATIENT_FK_ID + "=%s", getClass(), person.getId());

    Role role = person.getRole();
    if (role != Role.PATIENT) {
      throw new MedicationManagerPersistenceException("The person : " + person + " \n\t is not a patient but : " + role);
    }

    int personId = person.getId();
    Map<String, Column> columnMap = database.findDispenserByPerson(TABLE_NAME, PersonDao.TABLE_NAME, personId);

    if (columnMap == null || columnMap.isEmpty()) {
      return null;
    }

    Dispenser dispenser = getDispenser(person, columnMap);

    Log.info("Dispenser found: %s", getClass(), dispenser);

    return dispenser;

  }

  public Dispenser getDispenserByPersonId(int id) {
    Log.info("Looking for the dispenser with " + PATIENT_FK_ID + "=%s", getClass(), id);

    Map<String, Column> columnMap = database.findDispenserByPerson(TABLE_NAME, PersonDao.TABLE_NAME, id);

    if (columnMap == null || columnMap.isEmpty()) {
      return null;
    }

    Dispenser dispenser = getDispenser(columnMap);

    Log.info("Dispenser found: %s", getClass(), dispenser);

    return dispenser;

  }

  private Dispenser getDispenser(Person person, Map<String, Column> columnMap) {
    Column col = columnMap.get(ID);
    int id = (Integer) col.getValue();

    col = columnMap.get(NAME);
    String name = (String) col.getValue();

    col = columnMap.get(DISPENSER_URI);
    String dispenserUri = (String) col.getValue();

    col = columnMap.get(INSTRUCTIONS_FILE_NAME);
    String instructionsFileName = (String) col.getValue();

    col = columnMap.get(DUE_INTAKE_ALERT);
    boolean dueIntakeAlert = (Boolean) col.getValue();

    col = columnMap.get(SUCCESSFUL_INTAKE_ALERT);
    boolean successfulIntakeAlert = (Boolean) col.getValue();

    col = columnMap.get(MISSED_INTAKE_ALERT);
    boolean missedIntakeAlert = (Boolean) col.getValue();

    col = columnMap.get(UPSIDE_DOWN_ALERT);
    boolean upsideDownAlert = (Boolean) col.getValue();

    return new Dispenser(id, person, name, dispenserUri, instructionsFileName, dueIntakeAlert,
        successfulIntakeAlert, missedIntakeAlert, upsideDownAlert);
  }

  public Dispenser getByDispenserUri(String deviceUri) {
    String sql = "select * from MEDICATION_MANAGER.DISPENSER where UPPER(DISPENSER_URI) = UPPER('" + deviceUri + "')";

    Map<String, Column> dispenserRecordMap = executeQueryExpectedSingleRecord(TABLE_NAME, sql);

    return getDispenser(dispenserRecordMap);
  }

  private Dispenser getDispenser(Map<String, Column> dispenserRecordMap) {
    checkForSetDao(personDao, "personDao");

    Column colId = dispenserRecordMap.get(ID);
    int id = (Integer) colId.getValue();
    Column colUri = dispenserRecordMap.get(DISPENSER_URI);
    String dispenserUri = (String) colUri.getValue();
    Column colName = dispenserRecordMap.get(NAME);
    String name = (String) colName.getValue();
    Column colPatient = dispenserRecordMap.get(PATIENT_FK_ID);
    Integer personId = (Integer) colPatient.getValue();
    Person person;
    if (personId != null && personId > 0) {
      person = personDao.getById(personId);
    } else {
      person = null;
    }
    Column instructionsFileNameColumn = dispenserRecordMap.get(INSTRUCTIONS_FILE_NAME);
    String instructionsFileName = (String) instructionsFileNameColumn.getValue();
    Column col = dispenserRecordMap.get(DUE_INTAKE_ALERT);
    boolean dueIntakeAlert = (Boolean) col.getValue();

    col = dispenserRecordMap.get(SUCCESSFUL_INTAKE_ALERT);
    boolean successfulIntakeAlert = (Boolean) col.getValue();

    col = dispenserRecordMap.get(MISSED_INTAKE_ALERT);
    boolean missedIntakeAlert = (Boolean) col.getValue();

    col = dispenserRecordMap.get(UPSIDE_DOWN_ALERT);
    boolean upsideDownAlert = (Boolean) col.getValue();

    return new Dispenser(id, person, name, dispenserUri, instructionsFileName, dueIntakeAlert,
        successfulIntakeAlert, missedIntakeAlert, upsideDownAlert);
  }

  public List<Dispenser> getAllDispensers() {
    String sql = "select * from MEDICATION_MANAGER.DISPENSER";

    List<Dispenser> dispensers = new ArrayList<Dispenser>();

    try {
      PreparedStatement ps = getPreparedStatement(sql);
      List<Map<String, Column>> result = executeQueryExpectedMultipleRecord(TABLE_NAME, sql, ps);

      if (result == null || result.isEmpty()) {
        return dispensers;
      }

      for (Map<String, Column> columnMap : result) {
        Dispenser disp = getDispenser(columnMap);
        dispensers.add(disp);
      }

      return dispensers;
    } catch (Exception e) {
      throw new MedicationManagerPersistenceException(e);
    }
  }

  public void updateDispenser(int dispenserId, int patientId) {

    String sql = "UPDATE MEDICATION_MANAGER.DISPENSER SET PATIENT_FK_ID = ? WHERE ID = ?";

    PreparedStatement ps = null;


    try {
      ps = getPreparedStatement(sql);
      ps.setInt(1, patientId);
      ps.setInt(2, dispenserId);
      ps.execute();
    } catch (SQLException e) {
      throw new MedicationManagerPersistenceException(e);
    } finally {
      closeStatement(ps);
    }
  }

  private boolean patientHasDispenser(int patientId) {

    String sql = "SELECT * FROM MEDICATION_MANAGER.DISPENSER WHERE PATIENT_FK_ID = ?";

    PreparedStatement ps = null;


    try {
      ps = getPreparedStatement(sql);
      ps.setInt(1, patientId);
      ResultSet rs = ps.executeQuery();
      return rs.next();
    } catch (SQLException e) {
      throw new MedicationManagerPersistenceException(e);
    } finally {
      closeStatement(ps);
    }

  }

  public void updateDispenserRemovePatientForeignKey(int patientId) {

    if (!patientHasDispenser(patientId)) {
      return;
    }

    String sql = "UPDATE MEDICATION_MANAGER.DISPENSER SET PATIENT_FK_ID = ? WHERE PATIENT_FK_ID = ?";

    PreparedStatement ps = null;


    try {
      ps = getPreparedStatement(sql);
      ps.setNull(1, Types.INTEGER);
      ps.setInt(2, patientId);
      ps.execute();
    } catch (SQLException e) {
      throw new MedicationManagerPersistenceException(e);
    } finally {
      closeStatement(ps);
    }
  }
}
