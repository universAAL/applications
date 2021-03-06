/*******************************************************************************
 * Copyright 2012 , http://www.prosyst.com - ProSyst Software GmbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/

package org.universAAL.AALapplication.medication_manager.persistence.layer.dao;

import org.universAAL.AALapplication.medication_manager.configuration.Pair;
import org.universAAL.AALapplication.medication_manager.persistence.impl.Log;
import org.universAAL.AALapplication.medication_manager.persistence.impl.MedicationManagerPersistenceException;
import org.universAAL.AALapplication.medication_manager.persistence.impl.database.AbstractDao;
import org.universAAL.AALapplication.medication_manager.persistence.impl.database.Column;
import org.universAAL.AALapplication.medication_manager.persistence.impl.database.Database;
import org.universAAL.AALapplication.medication_manager.persistence.layer.MedicineInventoryShortageCaregiverNotifier;
import org.universAAL.AALapplication.medication_manager.persistence.layer.entities.Intake;
import org.universAAL.AALapplication.medication_manager.persistence.layer.entities.InventoryLog;
import org.universAAL.AALapplication.medication_manager.persistence.layer.entities.Medicine;
import org.universAAL.AALapplication.medication_manager.persistence.layer.entities.MedicineInventory;
import org.universAAL.AALapplication.medication_manager.persistence.layer.entities.Person;
import org.universAAL.AALapplication.medication_manager.persistence.layer.entities.Reference;
import org.universAAL.AALapplication.medication_manager.persistence.layer.entities.Treatment;
import org.universAAL.AALapplication.medication_manager.persistence.layer.entities.UnitClass;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
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
  private PatientLinksDao patientLinksDao;
  private TreatmentDao treatmentDao;
  private InventoryLogDao inventoryLogDao;
  private final MedicineInventoryShortageCaregiverNotifier notifier;

  private static final String TABLE_NAME = "MEDICINE_INVENTORY";
  private static final String PATIENT_FK_ID = "PATIENT_FK_ID";
  private static final String MEDICINE_FK_ID = "MEDICINE_FK_ID";
  private static final String UNIT_CLASS = "UNIT_CLASS";
  private static final String QUANTITY = "QUANTITY";
  private static final String WARNING_THRESHOLD = "WARNING_THRESHOLD";
  public static final int DEFAULT_WARNING_THRESHOLD = 1;

  public MedicineInventoryDao(Database database) {
    super(database, TABLE_NAME);

    notifier = new MedicineInventoryShortageCaregiverNotifier();
  }

  public void setPersonDao(PersonDao personDao) {
    this.personDao = personDao;
  }

  public void setMedicineDao(MedicineDao medicineDao) {
    this.medicineDao = medicineDao;
  }

  public void setPatientLinksDao(PatientLinksDao patientLinksDao) {
    this.patientLinksDao = patientLinksDao;
  }

  public void setTreatmentDao(TreatmentDao treatmentDao) {
    this.treatmentDao = treatmentDao;
  }

  public void setInventoryLogDao(InventoryLogDao inventoryLogDao) {
    this.inventoryLogDao = inventoryLogDao;
  }

  @Override
  @SuppressWarnings("unchecked")
  public MedicineInventory getById(int id) {
    throw new UnsupportedOperationException("Not implemented yet");
  }

  public MedicineInventory getMedicineInventory(Map<String, Column> columns) {

    checkForSetDao(personDao, "personDao");
    checkForSetDao(medicineDao, "medicineDao");
    checkForSetDao(patientLinksDao, "patientLinksDao");
    checkForSetDao(treatmentDao, "treatmentDao");

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

    MedicineInventory medicineInventory =
        new MedicineInventory(medicineInventoriId, patient, medicine, unitClass, quantity, warningThreshold);

    Log.info("MedicineInventory found with id : %s", getClass(), medicineInventory.getId());

    return medicineInventory;

  }

  public void decreaseInventory(Person patient, List<Intake> intakes) {

    checkForSetDao(inventoryLogDao, "inventoryLogDao");

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
    int medicineInventoryQuantity = medicineInventory.getQuantity();
    int quantityAfterDecrease = medicineInventoryQuantity - quantity;
    if (medicineInventory.getWarningThreshold() > quantityAfterDecrease) {
      notifyCaregiver(patient, medicine, medicineInventory);
      throw new MedicationManagerPersistenceException("Not enough inventory", NOT_ENOUGH_INVENTORY);
    }

    decreaseInventory(medicineInventory, quantity);
  }

  private void notifyCaregiver(Person patient, Medicine medicine, MedicineInventory medicineInventory) {

    Treatment treatment = treatmentDao.findTreatment(patient.getId(), medicine.getId());

    if (treatment.isShortageAlert()) {
      notifier.notifyCaregiverForMedicineShortage(patient, medicine, medicineInventory, patientLinksDao);
    }
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
      writeToInventoryLog(medicineInventory, quantity, Reference.INTAKE);
    } finally {
      closeStatement(ps);
    }

  }

  private void writeToInventoryLog(MedicineInventory medicineInventory, int quantity, Reference reference) {
    InventoryLog inventoryLog = new InventoryLog(
        database.getNextIdFromIdGenerator(),
        new Date(),
        medicineInventory.getPatient(),
        medicineInventory.getMedicine(),
        quantity,
        medicineInventory.getUnitClass(),
        reference
    );

    inventoryLogDao.save(inventoryLog);

  }

  private MedicineInventory findInventory(Person patient, Medicine medicine, int quantity) throws SQLException {
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
      if (columnMap == null || columnMap.isEmpty()) {
        throw new MedicationManagerPersistenceException("Missing the record in the table:" +
            TABLE_NAME + " for the following sql:\n" + sql, MedicationManagerPersistenceException.MISSING_RECORD);
      }
      return getMedicineInventory(columnMap);
    } finally {
      closeStatement(ps);
    }


  }

  public boolean hasMedicineInventory(int patientId, int medicineId) {
    String sql = "select * from MEDICATION_MANAGER.MEDICINE_INVENTORY where " +
        "PATIENT_FK_ID = ? and MEDICINE_FK_ID = ?";

    PreparedStatement ps = null;

    try {
      ps = getPreparedStatement(sql);
      ps.setInt(1, patientId);
      ps.setInt(2, medicineId);
      Map<String, Column> records = executeQueryExpectedSingleRecord(TABLE_NAME, ps);
      if (records == null || records.isEmpty()) {
        return false;
      }
      MedicineInventory medicineInventory = getMedicineInventory(records);
      return medicineInventory.getQuantity() > 0;
    } catch (SQLException e) {
      throw new MedicationManagerPersistenceException(e);
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
      List<Map<String, Column>> records = executeQueryMultipleRecordsPossible(TABLE_NAME, sql, ps);
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

  public void updateMedicineInventoryTableWithNewThreshold(int medicineInventoryId,
                                                           int patientId, int threshold) throws SQLException {

    Log.info("Setting new threshold for a medicineInventory with id: %s and new threshold: %s",
        getClass(), medicineInventoryId, threshold);

    String sql = "update MEDICATION_MANAGER.MEDICINE_INVENTORY " +
        "set WARNING_THRESHOLD = ? where ID = ?";

    PreparedStatement ps = null;

    try {
      ps = getPreparedStatement(sql);
      ps.setInt(1, threshold);
      ps.setInt(2, medicineInventoryId);
      ps.execute();
    } finally {
      closeStatement(ps);
    }
  }

  public Pair<Boolean, MedicineInventory> hasMedicineInventoryRecord(int patientId, int medicineId) {
    String sql = "select * from MEDICATION_MANAGER.MEDICINE_INVENTORY where " +
        "PATIENT_FK_ID = ? and MEDICINE_FK_ID = ?";

    PreparedStatement ps = null;

    try {
      ps = getPreparedStatement(sql);
      ps.setInt(1, patientId);
      ps.setInt(2, medicineId);
      Map<String, Column> records = executeQueryExpectedSingleRecord(TABLE_NAME, ps);
      if (records == null || records.isEmpty()) {
        return new Pair<Boolean, MedicineInventory>(false, null);
      }
      MedicineInventory medicineInventory = getMedicineInventory(records);
      return new Pair<Boolean, MedicineInventory>(true, medicineInventory);
    } catch (SQLException e) {
      throw new MedicationManagerPersistenceException(e);
    } finally {
      closeStatement(ps);
    }
  }

  public void saveMedicineInventory(Person patient, Medicine medicine, int quantity) {
    try {
      Pair<Boolean, MedicineInventory> inventoryPair = hasMedicineInventoryRecord(patient.getId(), medicine.getId());
      MedicineInventory medicineInventory;
      if (inventoryPair.getFirst()) {
        medicineInventory = updateQuantity(inventoryPair.getSecond(), quantity);
      } else {
        medicineInventory = insertNewRecord(patient, medicine, quantity);
      }

      writeToInventoryLog(medicineInventory, quantity, Reference.REFILL);

    } catch (Exception e) {
      throw new MedicationManagerPersistenceException(e);
    }
  }

  private MedicineInventory insertNewRecord(Person patient, Medicine medicine, int quantity) {

    MedicineInventory inventory = new MedicineInventory(
        database.getNextIdFromIdGenerator(),
        patient,
        medicine,
        medicine.getUnitClass(),
        quantity,
        DEFAULT_WARNING_THRESHOLD
    );

    String sql = "insert into medication_manager.MEDICINE_INVENTORY " +
        "(id, patient_fk_id, medicine_fk_id, unit_class, quantity, warning_threshold)" +
        "values (?, ?, ?, ?, ?, ?)";

    PreparedStatement ps = null;

    try {
      ps = getPreparedStatement(sql);
      ps.setInt(1, inventory.getId());
      ps.setInt(2, inventory.getPatient().getId());
      ps.setInt(3, inventory.getMedicine().getId());
      ps.setString(4, inventory.getUnitClass().getType());
      ps.setInt(5, inventory.getQuantity());
      ps.setInt(6, inventory.getWarningThreshold());

      ps.execute();

      return inventory;

    } catch (SQLException e) {
      throw new MedicationManagerPersistenceException(e);
    } finally {
      closeStatement(ps);
    }
  }

  private MedicineInventory updateQuantity(MedicineInventory medicineInventory, int quantity) throws SQLException {
    Log.info("Increasing quantity for a medicineInventory with id: %s with: %s quantity",
        getClass(), medicineInventory.getId(), quantity);

    int quant = medicineInventory.getQuantity() + quantity;

    MedicineInventory inventory = new MedicineInventory(
        medicineInventory.getId(),
        medicineInventory.getPatient(),
        medicineInventory.getMedicine(),
        medicineInventory.getUnitClass(),
        quant,
        medicineInventory.getWarningThreshold()
    );

    String sql = "update MEDICATION_MANAGER.MEDICINE_INVENTORY " +
        "set quantity = ? where ID = ?";

    PreparedStatement ps = null;

    try {
      ps = getPreparedStatement(sql);
      ps.setInt(1, inventory.getQuantity());
      ps.setInt(2, medicineInventory.getId());
      ps.execute();
      return inventory;
    } finally {
      closeStatement(ps);
    }
  }
}
