package org.universAAL.AALapplication.medication_manager.persistence.layer;

import java.io.BufferedReader;

/**
 * @author George Fournadjiev
 */
public interface SqlUtility {

  void printTablesData();

  void printTableData(String tableName);

  void executeSqlFile(BufferedReader bufferedReader);

  void executeSqlStatement(String sqlStatement);
}
