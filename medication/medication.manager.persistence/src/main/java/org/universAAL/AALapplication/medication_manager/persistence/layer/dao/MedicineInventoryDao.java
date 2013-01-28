package org.universAAL.AALapplication.medication_manager.persistence.layer.dao;

import org.universAAL.AALapplication.medication_manager.persistence.impl.database.AbstractDao;
import org.universAAL.AALapplication.medication_manager.persistence.impl.database.Database;
import org.universAAL.AALapplication.medication_manager.persistence.layer.entities.MedicineInventory;

/**
 * @author George Fournadjiev
 */
public final class MedicineInventoryDao extends AbstractDao {


  private static final String TABLE_NAME = "MEDICINE_INVENTORY";

  public MedicineInventoryDao(Database database) {
    super(database, TABLE_NAME);
  }

  @Override
  @SuppressWarnings("unchecked")
  public MedicineInventory getById(int id) {
    throw new UnsupportedOperationException("Not implemented yet");
  }

}
