package org.universAAL.AALapplication.medication_manager.persistence.layer.dao;

import org.universAAL.AALapplication.medication_manager.persistence.impl.Log;
import org.universAAL.AALapplication.medication_manager.persistence.impl.MedicationManagerPersistenceException;
import org.universAAL.AALapplication.medication_manager.persistence.impl.database.AbstractDao;
import org.universAAL.AALapplication.medication_manager.persistence.impl.database.Column;
import org.universAAL.AALapplication.medication_manager.persistence.impl.database.Database;
import org.universAAL.AALapplication.medication_manager.persistence.layer.entities.Intake;
import org.universAAL.AALapplication.medication_manager.persistence.layer.entities.Medicine;
import org.universAAL.AALapplication.medication_manager.persistence.layer.entities.MedicineInventory;
import org.universAAL.AALapplication.medication_manager.persistence.layer.entities.Person;
import org.universAAL.AALapplication.medication_manager.persistence.layer.entities.Treatment;
import org.universAAL.AALapplication.medication_manager.persistence.layer.entities.UnitClass;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.universAAL.AALapplication.medication_manager.persistence.impl.Activator.*;
import static org.universAAL.AALapplication.medication_manager.persistence.impl.MedicationManagerPersistenceException.*;

/**
 * @author George Fournadjiev
 */
public final class MedicineInventoryDao extends AbstractDao {


  private PersonDao personDao;
  private MedicineDao medicineDao;

  private static final String TABLE_NAME = "MEDICINE_INVENTORY";
  private static final String PATIENT_FK_ID = "PATIENT_FK_ID";
  private static final String MEDICINE_FK_ID = "MEDICINE_FK_ID";
  private static final String UNIT_CLASS = "UNIT_CLASS";
  private static final String QUANTITY = "QUANTITY";
  private static final String WARNING_THRESHOLD = "WARNING_THRESHOLD";

  public MedicineInventoryDao(Database database) {
    super(database, TABLE_NAME);
  }

  public void setPersonDao(PersonDao personDao) {
    this.personDao = personDao;
  }

  public void setMedicineDao(MedicineDao medicineDao) {
    this.medicineDao = medicineDao;
  }

  @Override
  @SuppressWarnings("unchecked")
  public MedicineInventory getById(int id) {
    throw new UnsupportedOperationException("Not implemented yet");
  }

  public MedicineInventory getMedicineInventory(Map<String, Column> columns) {

    checkForSetDao(personDao, "personDao");
    checkForSetDao(medicineDao, "medicineDao");

    Column col = columns.get(ID);
    int medicineInventoriId = (Integer) col.getValue();

    col = columns.get(PATIENT_FK_ID);
    int patientId = (Integer) col.getValue();
    Person patient = personDao.getById(patientId);

    col = columns.get(MEDICINE_FK_ID);
    int medicineId = (Integer) col.getValue();
    Medicine medicine = medicineDao.getById(medicineId);

    col = columns.get(UNIT_CLASS);
    String unitClassString = (String) col.getValue();
    UnitClass unitClass = UnitClass.getEnumValueFor(unitClassString);

    col = columns.get(QUANTITY);
    int quantity = (Integer) col.getValue();

    col = columns.get(WARNING_THRESHOLD);
    int warningThreshold = (Integer) col.getValue();


    return new MedicineInventory(medicineInventoriId, patient, medicine, unitClass, quantity, warningThreshold);

  }

  public void decreaseInventory(Person patient, List<Intake> intakes) {

    Connection connection = database.getConnection();

    try {
      connection.setAutoCommit(false);

      for (Intake in : intakes) {
        handleIntake(patient, in);
      }

      connection.commit();

    } catch (Exception e) {
      rollback(connection, e);
      throw new MedicationManagerPersistenceException(e);
    } finally {
      setAutoCommitToTrue(connection);
    }


  }

  private void handleIntake(Person patient, Intake in) throws SQLException {
    Treatment treatment = in.getTreatment();
    Medicine medicine = treatment.getMedicine();
    int quantity = in.getQuantity();
    MedicineInventory medicineInventory = findInventory(patient, medicine, quantity);
    int quantityAfterDecrease = medicineInventory.getQuantity() - quantity;
    if (medicineInventory.getWarningThreshold() > quantityAfterDecrease) {
      throw new MedicationManagerPersistenceException("Not enough inventory", NOT_ENOUGH_INVENTORY);
    }

    decreaseInventory(medicineInventory, quantity);
  }

  private void decreaseInventory(MedicineInventory medicineInventory, int quantity) throws SQLException {
    Log.info("Decreasing medicineInventory :%s with quantity:%s", getClass(), medicineInventory, quantity);

    String sql = "update MEDICATION_MANAGER.MEDICINE_INVENTORY " +
        "set QUANTITY = ? where ID = ?";

    PreparedStatement ps = null;

    try {
      ps = getPreparedStatement(sql);
      ps.setInt(1, (medicineInventory.getQuantity() - quantity));
      ps.setInt(2, medicineInventory.getId());
      ps.execute();
    } finally {
      closeStatement(ps);
    }

  }

  public MedicineInventory findInventory(Person patient, Medicine medicine, int quantity) throws SQLException {
    Log.info("Checking for enough inventory for patient:%s and medicine:%s to decrease it with quantity:%s",
        getClass(), patient, medicine, quantity);

    String sql = "select * from MEDICATION_MANAGER.MEDICINE_INVENTORY where " +
        "PATIENT_FK_ID = ? and MEDICINE_FK_ID = ?";

    PreparedStatement ps = null;

    try {
      ps = getPreparedStatement(sql);
      ps.setInt(1, patient.getId());
      ps.setInt(2, medicine.getId());
      Map<String, Column> columnMap = executeQueryExpectedSingleRecord(TABLE_NAME, ps);
      return getMedicineInventory(columnMap);
    } finally {
      closeStatement(ps);
    }


  }

  public List<MedicineInventory> getAllMedicineInventoriesForPatient(Person patient) {
    String sql = "select * from MEDICATION_MANAGER.MEDICINE_INVENTORY where " +
        "PATIENT_FK_ID = ?";

    PreparedStatement ps = null;

    try {
      ps = getPreparedStatement(sql);
      ps.setInt(1, patient.getId());
      List<Map<String, Column>> records = executeQueryExpectedMultipleRecord(TABLE_NAME, sql, ps);
      return getMedicineInventories(records);
    } catch (SQLException e) {
      throw new MedicationManagerPersistenceException(e);
    } finally {
      closeStatement(ps);
    }
  }

  private List<MedicineInventory> getMedicineInventories(List<Map<String, Column>> records) {
    List<MedicineInventory> medicineInventories = new ArrayList<MedicineInventory>();

    for (Map<String, Column> columnMap : records) {
      MedicineInventory medicineInventory = getMedicineInventory(columnMap);
      medicineInventories.add(medicineInventory);
    }

    return medicineInventories;
  }
}
