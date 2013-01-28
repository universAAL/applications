package org.universAAL.AALapplication.medication_manager.persistence.layer.dao;

import org.universAAL.AALapplication.medication_manager.persistence.impl.database.AbstractDao;
import org.universAAL.AALapplication.medication_manager.persistence.impl.database.Database;
import org.universAAL.AALapplication.medication_manager.persistence.layer.entities.Treatment;

/**
 * @author George Fournadjiev
 */
public final class TreatmentDao extends AbstractDao {


  private static final String TABLE_NAME = "TREATMENT";

  public TreatmentDao(Database database) {
    super(database, TABLE_NAME);
  }

  @Override
  @SuppressWarnings("unchecked")
  public Treatment getById(int id) {
    throw new UnsupportedOperationException("Not implemented yet");
  }

}
