package org.universAAL.AALapplication.medication_manager.persistence.impl.database;

import org.universAAL.AALapplication.medication_manager.persistence.impl.MedicationManagerPersistenceException;

import java.util.Map;

import static org.universAAL.AALapplication.medication_manager.configuration.Util.*;

/**
 * @author George Fournadjiev
 */
public abstract class AbstractDao {

  protected final Database database;
  private final String tableName;

  protected AbstractDao(Database database, String tableName) {
    validateParameter(database, "database");
    validateParameter(tableName, "tableName");

    this.database = database;
    this.tableName = tableName.toUpperCase();
  }

  public abstract <T> T getById(int id);

  public Map<String, Column> getTableColumnsValuesById(int id) {

    Map<String, Column> columnMap = database.getById(tableName, id);

    if (columnMap.isEmpty()) {
      throw new MedicationManagerPersistenceException("There is no such record in the table : " +
          tableName + " with id=" + id);
    }

    return columnMap;

  }

}
