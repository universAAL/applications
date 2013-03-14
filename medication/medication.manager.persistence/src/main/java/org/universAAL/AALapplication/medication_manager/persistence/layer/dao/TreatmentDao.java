package org.universAAL.AALapplication.medication_manager.persistence.layer.dao;

import org.universAAL.AALapplication.medication_manager.persistence.impl.Log;
import org.universAAL.AALapplication.medication_manager.persistence.impl.MedicationManagerPersistenceException;
import org.universAAL.AALapplication.medication_manager.persistence.impl.database.AbstractDao;
import org.universAAL.AALapplication.medication_manager.persistence.impl.database.Column;
import org.universAAL.AALapplication.medication_manager.persistence.impl.database.Database;
import org.universAAL.AALapplication.medication_manager.persistence.layer.entities.Medicine;
import org.universAAL.AALapplication.medication_manager.persistence.layer.entities.Person;
import org.universAAL.AALapplication.medication_manager.persistence.layer.entities.Treatment;
import org.universAAL.AALapplication.medication_manager.persistence.layer.entities.TreatmentStatus;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.universAAL.AALapplication.medication_manager.persistence.impl.Activator.*;
import static org.universAAL.AALapplication.medication_manager.persistence.layer.entities.TreatmentStatus.*;

/**
 * @author George Fournadjiev
 */
public final class TreatmentDao extends AbstractDao {

  private PersonDao personDao;
  private MedicineDao medicineDao;

  private static final String TABLE_NAME = "TREATMENT";
  private static final String NAME = "NAME";
  private static final String PATIENT_FK_ID = "PATIENT_FK_ID";
  private static final String MEDICINE_FK_ID = "MEDICINE_FK_ID";
  private static final String PHYSICIAN_FK_ID = "PHYSICIAN_FK_ID";
  private static final String STATUS = "STATUS";

  public TreatmentDao(Database database) {
    super(database, TABLE_NAME);
  }

  @Override
  @SuppressWarnings("unchecked")
  public Treatment getById(int id) {
    throw new UnsupportedOperationException("Not implemented yet");
  }

  public void setPersonDao(PersonDao personDao) {
    this.personDao = personDao;
  }

  public void setMedicineDao(MedicineDao medicineDao) {
    this.medicineDao = medicineDao;
  }

  public List<Treatment> getByPersonAndActive(int personId) {
    String sql = "select * from MEDICATION_MANAGER.TREATMENT where PATIENT_FK_ID = ? and STATUS = ?";

    System.out.println("sql = " + sql);

    PreparedStatement statement = null;
    try {
      statement = getPreparedStatement(sql, statement);
      return getTreatment(personId, sql, statement);
    } catch (SQLException e) {
      throw new MedicationManagerPersistenceException(e);
    } finally {
      closeStatement(statement);
    }
  }

  private Treatment getTreatment(Map<String, Column> columns) {

    checkForSetDao(personDao, "personDao");
    checkForSetDao(medicineDao, "medicineDao");

    Column col = columns.get(ID);
    int treatmentId = (Integer) col.getValue();

    col = columns.get(NAME);
    String name = (String) col.getValue();

    col = columns.get(PATIENT_FK_ID);
    int patientId = (Integer) col.getValue();
    Person patient = personDao.getById(patientId);

    col = columns.get(MEDICINE_FK_ID);
    int medicineId = (Integer) col.getValue();

    Medicine medicine = medicineDao.getById(medicineId);

    col = columns.get(PHYSICIAN_FK_ID);
    int physicianId = (Integer) col.getValue();
    Person physician = personDao.getById(physicianId);

    col = columns.get(STATUS);
    String statusValue = (String) col.getValue();
    TreatmentStatus treatmentStatus = TreatmentStatus.getEnumValueFor(statusValue);

    Treatment treatment = new Treatment(treatmentId, name, patient, medicine, physician, treatmentStatus);

    Log.info("Treatment found: %s", getClass(), treatment);

    return treatment;
  }

  private List<Treatment> getTreatment(int personId, String sql, PreparedStatement statement) throws SQLException {
    statement.setInt(1, personId);
    statement.setString(2, ACTIVE.getValue());
    List<Map<String, Column>> results = executeQueryExpectedMultipleRecord(TABLE_NAME, sql, statement);
    return createTreatments(results);
  }

  private PreparedStatement getPreparedStatement(String sql, PreparedStatement statement) throws SQLException {
    Connection connection = database.getConnection();
    statement = connection.prepareStatement(sql);

    return statement;
  }

  private List<Treatment> createTreatments(List<Map<String, Column>> results) {

    List<Treatment> treatments = new ArrayList<Treatment>();

    for (Map<String, Column> columns : results) {
      Treatment treatment = getTreatment(columns);
      treatments.add(treatment);
    }

    return treatments;
  }
}
