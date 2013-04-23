package org.universAAL.AALapplication.medication_manager.persistence.impl.database;

import org.universAAL.AALapplication.medication_manager.persistence.impl.Log;
import org.universAAL.AALapplication.medication_manager.persistence.impl.MedicationManagerPersistenceException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
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
          tableName + " with id=" + id, MedicationManagerPersistenceException.MISSING_RECORD);
    }

    return columnMap;

  }

  protected Map<String, Column> executeQueryExpectedSingleRecord(String tableName, String sql) {

    return database.executeQueryExpectedSingleRecord(tableName, sql);

  }

  protected Map<String, Column> executeQueryExpectedSingleRecord(String tableName, PreparedStatement ps) {

    return database.executeQueryExpectedSingleRecord(tableName, ps);

  }

  protected List<Map<String, Column>> executeQueryExpectedMultipleRecord(String tableName,
                                                                         String sql, PreparedStatement statement) {

    List<Map<String, Column>> results = database.executeQueryExpectedMultipleRecord(tableName, statement);

    if (results == null || results.isEmpty()) {
      throw new MedicationManagerPersistenceException("Missing the record in the table:" +
          tableName + " for the following sql:\n" + sql, MedicationManagerPersistenceException.MISSING_RECORD);
    }

    return results;

  }

  protected List<Map<String, Column>> executeQueryMultipleRecordsPossible(String tableName,
                                                                          String sql, PreparedStatement statement) {

    List<Map<String, Column>> results = database.executeQueryExpectedMultipleRecord(tableName, statement);

    if (results == null) {
      results = new ArrayList<Map<String, Column>>();
    }

    return results;

  }

  public PreparedStatement getPreparedStatement(String sql) throws SQLException {
    Connection connection = database.getConnection();

    return connection.prepareStatement(sql);
  }

  public void rollback(Connection connection, Exception exc) {

    if (connection == null) {
      return;
    }

    Log.info("Exception occurred: %s. Rollback changes", getClass(), exc.getMessage());

    try {
      connection.rollback();
    } catch (SQLException e) {
      throw new MedicationManagerPersistenceException(e);
    }
  }

  public void setAutoCommitToTrue(Connection connection) {
    try {
      connection.setAutoCommit(true);
    } catch (SQLException e) {
      //nothing we can do here
    }
  }


}
