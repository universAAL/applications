package org.universAAL.AALapplication.medication_manager.persistence.impl.database;

import org.universAAL.AALapplication.medication_manager.persistence.impl.MedicationManagerPersistenceException;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Map;

import static org.universAAL.AALapplication.medication_manager.persistence.impl.Activator.*;

/**
 * @author George Fournadjiev
 */
public abstract class AbstractDao {

  protected final Database database;
  private final String tableName;

  protected static final String ID = "ID";

  protected AbstractDao(Database database, String tableName) {
    validateParameter(database, "database");
    validateParameter(tableName, "tableName");

    this.database = database;
    this.tableName = tableName.toUpperCase();
  }

  public void checkForSetDao(AbstractDao dao, String daoName) {
    if (dao == null) {
      throw new MedicationManagerPersistenceException(daoName + " is not set");
    }
  }

  public abstract <T> T getById(int id);

  protected Map<String, Column> getTableColumnsValuesById(int id) {

    Map<String, Column> columnMap = database.getById(tableName, id);

    if (columnMap.isEmpty()) {
      throw new MedicationManagerPersistenceException("There is no such record in the table : " +
          tableName + " with id=" + id);
    }

    return columnMap;

  }

  protected Map<String, Column> executeQueryExpectedSingleRecord(String tableName, String sql) {

    Map<String, Column> record = database.executeQueryExpectedSingleRecord(tableName, sql);

    if (record == null || record.isEmpty()) {
      throw new MedicationManagerPersistenceException("Missing the record in the table:" +
          tableName + " for the following sql:\n" + sql);
    }

    return record;

  }

  protected List<Map<String, Column>> executeQueryExpectedMultipleRecord(String tableName,
                                                                         String sql, PreparedStatement statement) {

    List<Map<String, Column>> results = database.executeQueryExpectedMultipleRecord(tableName, statement);

    if (results == null || results.isEmpty()) {
      throw new MedicationManagerPersistenceException("Missing the record in the table:" +
          tableName + " for the following sql:\n" + sql);
    }

    return results;

  }


}
