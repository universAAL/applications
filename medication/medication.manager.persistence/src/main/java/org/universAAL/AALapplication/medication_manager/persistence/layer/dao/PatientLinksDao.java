package org.universAAL.AALapplication.medication_manager.persistence.layer.dao;

import org.universAAL.AALapplication.medication_manager.persistence.impl.Log;
import org.universAAL.AALapplication.medication_manager.persistence.impl.MedicationManagerPersistenceException;
import org.universAAL.AALapplication.medication_manager.persistence.impl.database.AbstractDao;
import org.universAAL.AALapplication.medication_manager.persistence.impl.database.Column;
import org.universAAL.AALapplication.medication_manager.persistence.impl.database.Database;
import org.universAAL.AALapplication.medication_manager.persistence.layer.entities.PatientLinks;
import org.universAAL.AALapplication.medication_manager.persistence.layer.entities.Person;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.universAAL.AALapplication.medication_manager.persistence.impl.Activator.*;


/**
 * @author George Fournadjiev
 */
public final class PatientLinksDao extends AbstractDao {

  private PersonDao personDao;

  static final String TABLE_NAME = "PATIENT_LINKS";
  public static final String DOCTOR_FK_ID = "DOCTOR_FK_ID";
  public static final String PATIENT_FK_ID = "PATIENT_FK_ID";
  public static final String CAREGIVER_FK_ID = "CAREGIVER_FK_ID";

  public PatientLinksDao(Database database) {
    super(database, TABLE_NAME);
  }

  public void setPersonDao(PersonDao personDao) {
    this.personDao = personDao;
  }

  @Override
  @SuppressWarnings("unchecked")
  public PatientLinks getById(int id) {
    Log.info("Looking for the person with id=%s", getClass(), id);
    Map<String, Column> columns = getTableColumnsValuesById(id);

    return getPatientLinks(columns);

  }

  private PatientLinks getPatientLinks(Map<String, Column> columns) {
    Column col = columns.get(ID);
    int id = (Integer) col.getValue();

    checkForSetDao(personDao, "personDao");

    col = columns.get(DOCTOR_FK_ID);
    int doctorId = (Integer) col.getValue();
    Person doctor = personDao.getById(doctorId);

    col = columns.get(PATIENT_FK_ID);
    int patientId = (Integer) col.getValue();
    Person patient = personDao.getById(patientId);

    col = columns.get(CAREGIVER_FK_ID);
    int caregiverId = (Integer) col.getValue();
    Person caregiver = personDao.getById(caregiverId);

    PatientLinks patientLinks = new PatientLinks(id, doctor, patient, caregiver);

    Log.info("PatientLinks found: %s", getClass(), patientLinks);

    return patientLinks;
  }

  public List<Person> findDoctorPatients(Person doctor) {
    Log.info("Looking for the doctor =%s patients", getClass(), doctor);

    String sql = "select * from MEDICATION_MANAGER.PATIENT_LINKS where DOCTOR_FK_ID = ?";

    PreparedStatement ps = null;

    try {
      ps = getPreparedStatement(sql);
      ps.setInt(1, doctor.getId());
      return getPatients(sql, ps);
    } catch (SQLException e) {
      throw new MedicationManagerPersistenceException(e);
    } finally {
      closeStatement(ps);
    }


  }

  public Person findPatientCaregiver(Person patient) {
    Log.info("Looking for the patient =%s caregiver", getClass(), patient);

    String sql = "select * from MEDICATION_MANAGER.PATIENT_LINKS where PATIENT_FK_ID = ?";

    PreparedStatement ps = null;

    try {
      ps = getPreparedStatement(sql);
      int patientId = patient.getId();
      ps.setInt(1, patientId);
      Map<String, Column> caregiverColumnMap = executeQueryExpectedSingleRecord(TABLE_NAME, ps);
      PatientLinks patientLinks = getPatientLinks(caregiverColumnMap);
      Person caregiver = patientLinks.getCaregiver();
      if (caregiver == null) {
        throw new MedicationManagerPersistenceException("Missing caregiver for a patient with id = " + patientId);
      }
      return caregiver;
    } catch (SQLException e) {
      throw new MedicationManagerPersistenceException(e);
    } finally {
      closeStatement(ps);
    }


  }

  private List<Person> getPatients(String sql, PreparedStatement ps) {

    try {
      List<Map<String, Column>> doctorPatientsRecordMap = executeQueryExpectedMultipleRecord(TABLE_NAME, sql, ps);

      List<Person> patients = new ArrayList<Person>();
      for (Map<String, Column> columnMap : doctorPatientsRecordMap) {
        PatientLinks patientLinks = getPatientLinks(columnMap);
        patients.add(patientLinks.getPatient());
      }

      return patients;
    } catch (MedicationManagerPersistenceException e) {
      if (MedicationManagerPersistenceException.MISSING_RECORD == e.getCode()) {
        return new ArrayList<Person>();
      }
      throw e;
    }
  }
}
