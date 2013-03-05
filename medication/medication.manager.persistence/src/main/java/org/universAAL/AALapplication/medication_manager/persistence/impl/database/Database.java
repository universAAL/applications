package org.universAAL.AALapplication.medication_manager.persistence.impl.database;

import org.universAAL.AALapplication.medication_manager.persistence.layer.SqlUtility;

import java.util.Map;

/**
 * @author George Fournadjiev
 */
public interface Database {

  //This method is used only for the purposes of console shell commands and must be removed in the production
    SqlUtility getSqlUtility();

  void initDatabase() throws Exception;

  int getNextIdFromIdGenerator();

  Map<String, Column> getById(String tableName, int id);

  Map<String, Column> findDispenserByPerson(String tableName, String personTableName, int personId);

  Map<String,Column> executeQueryExpectedSingleRecord(String tableName, String sql);

}
