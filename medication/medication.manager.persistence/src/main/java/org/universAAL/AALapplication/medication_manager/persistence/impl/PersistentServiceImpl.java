package org.universAAL.AALapplication.medication_manager.persistence.impl;

import org.universAAL.AALapplication.medication_manager.persistence.PersistentService;
import org.universAAL.AALapplication.medication_manager.persistence.SqlUtility;
import org.universAAL.AALapplication.medication_manager.persistence.impl.database.Database;
import org.universAAL.AALapplication.medication_manager.persistence.impl.database.SqlUtilityImpl;

/**
 * @author George Fournadjiev
 */
public final class PersistentServiceImpl implements PersistentService {

  private final Database database;
  private final SqlUtility sqlUtility;

  public PersistentServiceImpl(Database database) {

    this.database = database;
    this.sqlUtility = database.getSqlUtility();
  }

  public SqlUtility getSqlUtility() {
    return sqlUtility;
  }
}
