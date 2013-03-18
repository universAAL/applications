package org.universAAL.AALapplication.medication_manager.persistence.layer.dao;

import org.universAAL.AALapplication.medication_manager.persistence.impl.Log;
import org.universAAL.AALapplication.medication_manager.persistence.impl.MedicationManagerPersistenceException;
import org.universAAL.AALapplication.medication_manager.persistence.impl.database.AbstractDao;
import org.universAAL.AALapplication.medication_manager.persistence.impl.database.Column;
import org.universAAL.AALapplication.medication_manager.persistence.impl.database.Database;
import org.universAAL.AALapplication.medication_manager.persistence.layer.entities.Medicine;
import org.universAAL.AALapplication.medication_manager.persistence.layer.entities.Prescription;
import org.universAAL.AALapplication.medication_manager.persistence.layer.entities.Treatment;
import org.universAAL.AALapplication.medication_manager.persistence.layer.entities.TreatmentStatus;

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

  private PrescriptionDao prescriptionDao;
  private MedicineDao medicineDao;

  private static final String TABLE_NAME = "TREATMENT";
  private static final String PRESCRIPTION_FK_ID = "PRESCRIPTION_FK_ID";
  private static final String MEDICINE_FK_ID = "MEDICINE_FK_ID";
  private static final String STATUS = "STATUS";

  public TreatmentDao(Database database) {
    super(database, TABLE_NAME);
  }

  @Override
  @SuppressWarnings("unchecked")
  public Treatment getById(int id) {
    Log.info("Looking for the treatment with id=%s", getClass(), id);
    Map<String, Column> columns = getTableColumnsValuesById(id);

    return getTreatment(columns);
  }

  public void setPrescriptionDao(PrescriptionDao prescriptionDao) {
    this.prescriptionDao = prescriptionDao;
  }

  public void setMedicineDao(MedicineDao medicineDao) {
    this.medicineDao = medicineDao;
  }

  public List<Treatment> getByPrescriptionAndActive(int prescriptionId) {
    String sql = "select * from MEDICATION_MANAGER.TREATMENT where PRESCRIPTION_FK_ID = ? and STATUS = ?";

    System.out.println("sql = " + sql);

    PreparedStatement statement = null;
    try {
      statement = getPreparedStatement(sql);
      return getTreatments(prescriptionId, sql, statement);
    } catch (SQLException e) {
      throw new MedicationManagerPersistenceException(e);
    } finally {
      closeStatement(statement);
    }
  }

  private Treatment getTreatment(Map<String, Column> columns) {

    checkForSetDao(prescriptionDao, "prescriptionDao");
    checkForSetDao(medicineDao, "medicineDao");

    Column col = columns.get(ID);
    int treatmentId = (Integer) col.getValue();

    col = columns.get(PRESCRIPTION_FK_ID);
    int prescriptionId = (Integer) col.getValue();
    Prescription prescription = prescriptionDao.getById(prescriptionId);

    col = columns.get(MEDICINE_FK_ID);
    int medicineId = (Integer) col.getValue();

    Medicine medicine = medicineDao.getById(medicineId);

    col = columns.get(STATUS);
    String statusValue = (String) col.getValue();
    TreatmentStatus treatmentStatus = TreatmentStatus.getEnumValueFor(statusValue);

    Treatment treatment = new Treatment(treatmentId, prescription, medicine, treatmentStatus);

    Log.info("Treatment found: %s", getClass(), treatment);

    return treatment;
  }

  private List<Treatment> getTreatments(int prescriptionId, String sql,
                                        PreparedStatement statement) throws SQLException {
    statement.setInt(1, prescriptionId);
    statement.setString(2, ACTIVE.getValue());
    List<Map<String, Column>> results = executeQueryExpectedMultipleRecord(TABLE_NAME, sql, statement);
    return createTreatments(results);
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
