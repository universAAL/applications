package org.universAAL.AALapplication.medication_manager.persistence.layer.dao;

import org.universAAL.AALapplication.medication_manager.persistence.impl.database.AbstractDao;
import org.universAAL.AALapplication.medication_manager.persistence.impl.database.Database;
import org.universAAL.AALapplication.medication_manager.persistence.layer.entities.Prescription;

/**
 * @author George Fournadjiev
 */
public final class PrescriptionDao extends AbstractDao {


  private static final String TABLE_NAME = "PRESCRIPTION";

  public PrescriptionDao(Database database) {
    super(database, TABLE_NAME);
  }

  @Override
  @SuppressWarnings("unchecked")
  public Prescription getById(int id) {
    throw new UnsupportedOperationException("Not implemented yet");
  }

}
