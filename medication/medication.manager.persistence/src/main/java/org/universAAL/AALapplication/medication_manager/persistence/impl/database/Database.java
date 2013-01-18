package org.universAAL.AALapplication.medication_manager.persistence.impl.database;

import org.universAAL.AALapplication.medication_manager.persistence.SqlUtility;

/**
 * @author George Fournadjiev
 */
public interface Database {

  void initDatabase() throws Exception;

  //This method is used only for the purposes of console shell commands and must be removed in the production
  SqlUtility getSqlUtility();
}
