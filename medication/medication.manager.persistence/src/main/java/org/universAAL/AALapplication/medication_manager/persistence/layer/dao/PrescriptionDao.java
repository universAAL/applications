package org.universAAL.AALapplication.medication_manager.persistence.layer.dao;

import org.universAAL.AALapplication.medication_manager.persistence.impl.Log;
import org.universAAL.AALapplication.medication_manager.persistence.impl.MedicationManagerPersistenceException;
import org.universAAL.AALapplication.medication_manager.persistence.impl.database.AbstractDao;
import org.universAAL.AALapplication.medication_manager.persistence.impl.database.Column;
import org.universAAL.AALapplication.medication_manager.persistence.impl.database.Database;
import org.universAAL.AALapplication.medication_manager.persistence.layer.dto.MedicineDTO;
import org.universAAL.AALapplication.medication_manager.persistence.layer.dto.PrescriptionDTO;
import org.universAAL.AALapplication.medication_manager.persistence.layer.entities.Person;
import org.universAAL.AALapplication.medication_manager.persistence.layer.entities.Prescription;
import org.universAAL.AALapplication.medication_manager.persistence.layer.entities.PrescriptionStatus;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.universAAL.AALapplication.medication_manager.persistence.impl.Activator.*;
import static org.universAAL.AALapplication.medication_manager.persistence.layer.entities.PrescriptionStatus.*;

/**
 * @author George Fournadjiev
 */
public final class PrescriptionDao extends AbstractDao {

  private static final String PATIENT_FK_ID = "PATIENT_FK_ID";
  private static final String TIME_OF_CREATION = "TIME_OF_CREATION";
  private static final String PHYSICIAN_FK_ID = "PHYSICIAN_FK_ID";
  private static final String DESCRIPTION = "DESCRIPTION";
  private static final String STATUS = "STATUS";
  private PersonDao personDao;

  private static final String TABLE_NAME = "PRESCRIPTION";

  public PrescriptionDao(Database database) {
    super(database, TABLE_NAME);
  }

  @Override
  @SuppressWarnings("unchecked")
  public Prescription getById(int id) {
    Log.info("Looking for the Prescription with id=%s", getClass(), id);
    Map<String, Column> columns = getTableColumnsValuesById(id);

    return getPrescription(columns);
  }

  public void setPersonDao(PersonDao personDao) {
    this.personDao = personDao;
  }

  public List<Prescription> getByPerson(int personId) {
    String sql = "select * from MEDICATION_MANAGER.PRESCRIPTION where PATIENT_FK_ID = ? and STATUS = ?";

    System.out.println("sql = " + sql);

    PreparedStatement statement = null;
    try {
      statement = getPreparedStatement(sql);
      return getPrescriptions(personId, sql, statement);
    } catch (SQLException e) {
      throw new MedicationManagerPersistenceException(e);
    } finally {
      closeStatement(statement);
    }
  }

  private List<Prescription> getPrescriptions(int personId, String sql,
                                              PreparedStatement statement) throws SQLException {
    statement.setInt(1, personId);
    statement.setString(2, ACTIVE.getType());
    List<Map<String, Column>> results = executeQueryExpectedMultipleRecord(TABLE_NAME, sql, statement);
    return createPrescriptions(results);
  }

  private List<Prescription> createPrescriptions(List<Map<String, Column>> results) {

    List<Prescription> treatments = new ArrayList<Prescription>();

    for (Map<String, Column> columns : results) {
      Prescription prescription = getPrescription(columns);
      treatments.add(prescription);
    }

    return treatments;
  }

  private Prescription getPrescription(Map<String, Column> columns) {
    checkForSetDao(personDao, "personDao");

    Column col = columns.get(ID);
    int prescriptionId = (Integer) col.getValue();

    col = columns.get(TIME_OF_CREATION);
    Date timeOfCreation = (Date) col.getValue();

    col = columns.get(PATIENT_FK_ID);
    int personId = (Integer) col.getValue();
    Person patient = personDao.getById(personId);

    col = columns.get(PHYSICIAN_FK_ID);
    int physicianId = (Integer) col.getValue();
    Person physician = personDao.getById(physicianId);

    col = columns.get(DESCRIPTION);
    String description = (String) col.getValue();

    col = columns.get(STATUS);
    String statusValue = (String) col.getValue();
    PrescriptionStatus prescriptionStatus = PrescriptionStatus.getEnumValueFor(statusValue);

    Prescription prescription =
        new Prescription(prescriptionId, timeOfCreation, patient, physician, description, prescriptionStatus);

    Log.info("Prescription found: %s", getClass(), prescription);

    return prescription;
  }

  public void save(PrescriptionDTO prescriptionDTO) {

    Connection connection = null;
    try {
      connection = database.getConnection();
      connection.setAutoCommit(false);
      persistPrescriptionDto(prescriptionDTO, connection);
      connection.commit();
    } catch (Exception e) {
      throw new MedicationManagerPersistenceException(e);
    } finally {
      setConnectionAutoCommitToTrue(connection);
    }

  }

  private void persistPrescriptionDto(PrescriptionDTO prescriptionDTO, Connection connection) throws SQLException {

    String sql = "INSERT INTO MEDICATION_MANAGER.PRESCRIPTION " +
        "(ID, TIME_OF_CREATION, PATIENT_FK_ID, PHYSICIAN_FK_ID, DESCRIPTION, STATUS) " +
        "VALUES (?, ?, ?, ?, ?, ?)";

    PreparedStatement psPrescription = null;

    try {
      psPrescription = connection.prepareStatement(sql);
      insertRecordIntoPrescriptionTable(prescriptionDTO, psPrescription);
      insertMissingRecordsIntoMedicineTable(prescriptionDTO.getMedicineDTOSet(), connection);
    } finally {
      closeStatement(psPrescription);
    }

  }

  private void insertMissingRecordsIntoMedicineTable(Set<MedicineDTO> medicineDTOSet,
                                                     Connection connection) throws SQLException {

    Set<String> medicineNames = getMedicineNames(connection);

    for (MedicineDTO medicineDTO : medicineDTOSet) {
      String name = medicineDTO.getName();
      if (!medicineNames.contains(name)) {
        persistMedicineDTO(medicineDTO, connection);
      }
    }

  }

  private void persistMedicineDTO(MedicineDTO medicineDTO,
                                  Connection connection) throws SQLException {
    String sql = "INSERT INTO MEDICATION_MANAGER.MEDICINE " +
        "(ID, MEDICINE_NAME, MEDICINE_INFO, SIDE_EFFECTS, INCOMPLIANCES, MEAL_RELATION) " +
        "VALUES (?, ?, ?, ?, ?, ?)";

    PreparedStatement ps = null;

    try {
      ps = connection.prepareStatement(sql);
      insertRecordIntoMedicineTable(medicineDTO, ps);
    } finally {
      closeStatement(ps);
    }
  }

  private void insertRecordIntoMedicineTable(MedicineDTO medicineDTO, PreparedStatement ps) throws SQLException {
    int id = database.getNextIdFromIdGenerator();
    ps.setInt(1, id);
    ps.setString(2, medicineDTO.getName());
    ps.setString(3, medicineDTO.getDescription());
    ps.setString(4, medicineDTO.getSideeffects());
    ps.setString(5, medicineDTO.getIncompliances());
    ps.setString(6, medicineDTO.getMealRelationDTO().getValue());
    ps.execute();
  }

  private Set<String> getMedicineNames(Connection connection) throws SQLException {
    String sql = "SELECT MEDICINE_NAME FROM MEDICATION_MANAGER.MEDICINE";

    Statement statement = null;
    ResultSet rs = null;

    try {
      statement = connection.createStatement();
      rs = statement.executeQuery(sql);
      Set<String> medicineNames = new HashSet<String>();
      while (rs.next()) {
        medicineNames.add(rs.getString(1));
      }
      return medicineNames;
    } finally {
      if (rs != null) {
        rs.close();
      }
      closeStatement(statement);
    }
  }

  private void insertRecordIntoPrescriptionTable(PrescriptionDTO prescriptionDTO,
                                                 PreparedStatement ps) throws SQLException {

    int id = database.getNextIdFromIdGenerator();
    ps.setInt(1, id);
    Date now = new Date();
    long time = now.getTime();
    Timestamp timeOfCreation = new Timestamp(time);
    ps.setTimestamp(2, timeOfCreation);
    ps.setInt(3, prescriptionDTO.getPatient().getId());
    ps.setInt(4, prescriptionDTO.getPhysician().getId());
    ps.setString(5, prescriptionDTO.getDescription());
    ps.setString(6, PrescriptionStatus.ACTIVE.getType());
    ps.execute();
  }

  private void setConnectionAutoCommitToTrue(Connection connection) {
    try {
      if (connection != null) {
        connection.setAutoCommit(true);
      }
    } catch (SQLException e) {
      throw new MedicationManagerPersistenceException(e);
    }
  }

}
