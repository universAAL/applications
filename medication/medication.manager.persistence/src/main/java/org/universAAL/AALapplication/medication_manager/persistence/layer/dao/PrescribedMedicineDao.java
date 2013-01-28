package org.universAAL.AALapplication.medication_manager.persistence.layer.dao;

import org.universAAL.AALapplication.medication_manager.persistence.impl.database.AbstractDao;
import org.universAAL.AALapplication.medication_manager.persistence.impl.database.Database;
import org.universAAL.AALapplication.medication_manager.persistence.layer.entities.PrescribedMedicine;

/**
 * @author George Fournadjiev
 */
public final class PrescribedMedicineDao extends AbstractDao {


  private static final String TABLE_NAME = "PRESCRIBED_MEDICINE";

  public PrescribedMedicineDao(Database database) {
    super(database, TABLE_NAME);
  }

  @Override
  @SuppressWarnings("unchecked")
  public PrescribedMedicine getById(int id) {
    throw new UnsupportedOperationException("Not implemented yet");
  }


}
