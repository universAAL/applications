package org.universAAL.AALapplication.medication_manager.persistence.layer.dao;

import org.universAAL.AALapplication.medication_manager.persistence.impl.database.AbstractDao;
import org.universAAL.AALapplication.medication_manager.persistence.impl.database.Database;

/**
 * @author George Fournadjiev
 */
public final class MedicineInventoryDao extends AbstractDao {


  private static final String TABLE_NAME = "medicine_inventory";

  public MedicineInventoryDao(Database database) {
    super(database, TABLE_NAME);
  }


}
