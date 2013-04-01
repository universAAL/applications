package org.universAAL.AALapplication.medication_manager.persistence.layer.dao;

import org.universAAL.AALapplication.medication_manager.persistence.impl.Log;
import org.universAAL.AALapplication.medication_manager.persistence.impl.MedicationManagerPersistenceException;
import org.universAAL.AALapplication.medication_manager.persistence.impl.database.AbstractDao;
import org.universAAL.AALapplication.medication_manager.persistence.impl.database.Column;
import org.universAAL.AALapplication.medication_manager.persistence.impl.database.Database;
import org.universAAL.AALapplication.medication_manager.persistence.layer.entities.DoctorPatient;
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
public final class DoctorPatientDao extends AbstractDao {

  private PersonDao personDao;

  static final String TABLE_NAME = "DOCTOR_PATIENT";

  public DoctorPatientDao(Database database) {
    super(database, TABLE_NAME);
  }

  public void setPersonDao(PersonDao personDao) {
    this.personDao = personDao;
  }

  @Override
  @SuppressWarnings("unchecked")
  public DoctorPatient getById(int id) {
    Log.info("Looking for the person with id=%s", getClass(), id);
    Map<String, Column> columns = getTableColumnsValuesById(id);

    return getDoctorPatient(columns);

  }

  private DoctorPatient getDoctorPatient(Map<String, Column> columns) {
    Column col = columns.get(ID);
    int personId = (Integer) col.getValue();

    checkForSetDao(personDao, "personDao");

    col = columns.get("DOCTOR_FK_ID");
    int doctorId = (Integer) col.getValue();

    col = columns.get("PATIENT_FK_ID");
    int patientId = (Integer) col.getValue();

    Person doctor = personDao.getById(doctorId);
    Person patient = personDao.getById(patientId);

    DoctorPatient doctorPatient = new DoctorPatient(database, TABLE_NAME, doctor, patient);

    Log.info("DoctorPatient found: %s", getClass(), doctorPatient);

    return doctorPatient;
  }

  public List<Person> findDoctorPatients(Person doctor) {
    Log.info("Looking for the doctor =%s patients", getClass(), doctor);

    String sql = "select * from MEDICATION_MANAGER.DOCTOR_PATIENT where DOCTOR_FK_ID = ?";

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

  private List<Person> getPatients(String sql, PreparedStatement ps) {

    try {
      List<Map<String, Column>> doctorPatientsRecordMap = executeQueryExpectedMultipleRecord(TABLE_NAME, sql, ps);

      List<Person> patients = new ArrayList<Person>();
      for (Map<String, Column> columnMap : doctorPatientsRecordMap) {
        DoctorPatient doctorPatient = getDoctorPatient(columnMap);
        patients.add(doctorPatient.getPatient());
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
