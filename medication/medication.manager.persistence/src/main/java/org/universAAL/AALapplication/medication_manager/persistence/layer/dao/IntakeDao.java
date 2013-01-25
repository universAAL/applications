package org.universAAL.AALapplication.medication_manager.persistence.layer.dao;

import org.universAAL.AALapplication.medication_manager.persistence.impl.database.AbstractDao;
import org.universAAL.AALapplication.medication_manager.persistence.impl.database.Database;
import org.universAAL.AALapplication.medication_manager.persistence.layer.entities.Intake;

/**
 * @author George Fournadjiev
 */
public final class IntakeDao extends AbstractDao {


  private static final String TABLE_NAME = "intake";

  public IntakeDao(Database database) {
    super(database, TABLE_NAME);
  }

  @Override
  @SuppressWarnings("unchecked")
  public Intake getById(int id) {
    throw new UnsupportedOperationException("Not implemented yet");
  }


}
