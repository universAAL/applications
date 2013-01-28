package org.universAAL.AALapplication.medication_manager.persistence.layer.dao;

import org.universAAL.AALapplication.medication_manager.persistence.impl.database.AbstractDao;
import org.universAAL.AALapplication.medication_manager.persistence.impl.database.Database;
import org.universAAL.AALapplication.medication_manager.persistence.layer.entities.InventoryLog;

/**
 * @author George Fournadjiev
 */
public final class InventoryLogDao extends AbstractDao {


  private static final String TABLE_NAME = "INVENTORY_LOG";

  public InventoryLogDao(Database database) {
    super(database, TABLE_NAME);
  }

  @Override
  @SuppressWarnings("unchecked")
  public InventoryLog getById(int id) {
    throw new UnsupportedOperationException("Not implemented yet");
  }


}
