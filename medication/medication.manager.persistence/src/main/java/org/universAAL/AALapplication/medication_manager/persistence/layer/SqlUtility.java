package org.universAAL.AALapplication.medication_manager.persistence.layer;

import java.io.BufferedReader;
import java.sql.SQLException;
import java.util.Set;

/**
 * @author George Fournadjiev
 */
public interface SqlUtility {

  void printTablesData();

  void printTableData(String tableName);

  void executeSqlFile(BufferedReader bufferedReader);

  void executeSqlStatement(String sqlStatement);

  int generateId();

  public Set<String> getDBColumns(String tableName) throws SQLException;
}
