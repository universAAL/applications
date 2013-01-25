package org.universAAL.AALapplication.medication_manager.persistence.impl.database;

import org.universAAL.AALapplication.medication_manager.configuration.SqlScriptParser;
import org.universAAL.AALapplication.medication_manager.persistence.impl.Log;
import org.universAAL.AALapplication.medication_manager.persistence.impl.MedicationManagerPersistenceException;
import org.universAAL.AALapplication.medication_manager.persistence.layer.SqlUtility;

import java.io.BufferedReader;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import static java.sql.Types.*;
import static org.universAAL.AALapplication.medication_manager.configuration.Util.*;
import static org.universAAL.AALapplication.medication_manager.persistence.impl.Activator.*;

/**
 * @author George Fournadjiev
 */
public final class DerbyDatabase implements Database {

  private final Connection connection;
  private final DerbySqlUtility derbySqlUtility;

  public static final String MEDICATION_MANAGER = "Medication_Manager";

  public DerbyDatabase(Connection connection) {
    validateParameter(connection, "connection");

    this.connection = connection;
    this.derbySqlUtility = new DerbySqlUtility(connection);
  }

  public SqlUtility getSqlUtility() {
    return derbySqlUtility;
  }

  public void initDatabase() throws Exception {
    Class.forName("org.apache.derby.jdbc.EmbeddedDriver").newInstance();
    if (checkIfTablesNotExists()) {
      createTablesAndPopulateThemInOneTransaction();
      createSequence();
    }
  }

  public int getNextIdFromIdGenerator() {
    try {
      int sequenceValue = getNextSequenceValue();
      System.out.println("getNextIdFromIdGenerator() = " + sequenceValue);
      return sequenceValue;
    } catch (SQLException e) {
      throw new MedicationManagerPersistenceException("unable to get next id from sequence generator", e);
    }

  }

  public Map<String, Column> getById(String tableName, int id) {

    Map<String, Column> columns = new LinkedHashMap<String, Column>();

    Statement statement = null;
    try {
      statement = connection.createStatement();
      String sqlQuery = "select * from " + MEDICATION_MANAGER + '.' + tableName +
          "\n\t where id=" + id;

      System.out.println("sqlQuery = " + sqlQuery);

      ResultSet rs = statement.executeQuery(sqlQuery);

      Set<String> columnsNames = derbySqlUtility.getDBColumns(tableName);
      ResultSetMetaData metaData = rs.getMetaData();
      while (rs.next()) {
        fillColumnsData(columns, columnsNames, rs, metaData);
      }

    } catch (SQLException e) {
      throw new MedicationManagerPersistenceException(e);
    } finally {
      closeStatement(statement);
    }

    return columns;
  }

  private void fillColumnsData(Map<String, Column> columns, Set<String> columnsNames,
                               ResultSet rs, ResultSetMetaData metaData) throws SQLException {

    for (String name : columnsNames) {
      int columnIndex = rs.findColumn(name);
      int sqlType = metaData.getColumnType(columnIndex);
      Column col = createColumn(name, sqlType, rs);
      columns.put(col.getName(), col);
    }
  }

  private Column createColumn(String name, int sqlType, ResultSet rs) throws SQLException {
    switch (sqlType) {
      case INTEGER:
        int integer = rs.getInt(name);
        return new Column(name, integer);
      case TIMESTAMP: Timestamp date = rs.getTimestamp(name);
        return new Column(name, date);
      case VARCHAR: String string = rs.getString(name);
        return new Column(name, string);
      default:
        throw new MedicationManagerPersistenceException("Unsupported sql type : " + sqlType);
    }
  }

  private void createTablesAndPopulateThemInOneTransaction() {
    boolean autoCommitValue = true;
    Statement statement = null;
    try {
      autoCommitValue = connection.getAutoCommit();
      connection.setAutoCommit(false);
      statement = connection.createStatement();

      createTables(statement);
      insertDataIntoTables(statement);

      connection.commit();

    } catch (SQLException e) {
      throw new MedicationManagerPersistenceException(e);
    } finally {
      handleFinally(connection, statement, autoCommitValue);
    }

  }

  public static void handleFinally(Connection connection, Statement statement, boolean autoCommitValue) {
    try {
      closeStatement(statement);
      connection.rollback();
      connection.setAutoCommit(autoCommitValue);
    } catch (SQLException e) {
      throw new MedicationManagerPersistenceException(e);
    }
  }

  public int getNextSequenceValue() throws SQLException {
    PreparedStatement statement = null;
    try {
      statement = connection.prepareStatement(
          "VALUES NEXT VALUE FOR " + MEDICATION_MANAGER + ".ID_GEN");
      ResultSet rs = statement.executeQuery();
      if (rs.next()) {
        int id = rs.getInt(1);
        return id;
      }
    } finally {
      closeStatement(statement);
    }

    throw new MedicationManagerPersistenceException("Unexpected missing sequence value");
  }

  private void createSequence() {
    Log.info("The sequence does not exists, so creating the sequence", getClass());
    PreparedStatement ps = null;
    try {
      ps = connection.prepareStatement(
          "CREATE SEQUENCE " + MEDICATION_MANAGER + ".ID_GEN AS BIGINT START WITH 1000");
      ps.execute();
    } catch (SQLException e) {
      //nothing to do here in case the sequence exist and this method is triggered by other SQLException than sequence does not exists
    } finally {
      closeStatement(ps);
    }
  }

  private void createTables(Statement statement) throws SQLException {
    executeSqlStatementsFromSqlFile("CreateTables.sql", statement);
  }

  private void insertDataIntoTables(Statement statement) throws SQLException {
    executeSqlStatementsFromSqlFile("DataLoad.sql", statement);
  }

  private void executeSqlStatementsFromSqlFile(String sqlFileName, Statement statement) throws SQLException {
    SqlScriptParser sqlScriptParser = new SqlScriptParser();
    sqlScriptParser.parseSqlFile(sqlFileName);
    executeStatements(statement, sqlScriptParser);
  }

  public static void executeSqlStatementsFromSqlFile(Statement statement,
                                                     BufferedReader bufferedReader) throws SQLException {

    SqlScriptParser sqlScriptParser = new SqlScriptParser();
    sqlScriptParser.parseSqlFile(bufferedReader);
    executeStatements(statement, sqlScriptParser);
  }

  private static void executeStatements(Statement statement, SqlScriptParser sqlScriptParser) throws SQLException {
    Map<Integer, String> sqlTablesMap = sqlScriptParser.getSqlStatementMap();

    int counter = 0;
    while (sqlTablesMap.size() > counter) {
      String sqlStatement = sqlTablesMap.get(counter + 1);
      sqlStatement = sqlStatement.toUpperCase();
      statement.executeUpdate(sqlStatement);
      counter++;
    }

  }

  private boolean checkIfTablesNotExists() throws SQLException {
    Set<String> tableNames = getDBTables(connection);
    System.out.println("tableNames = " + tableNames);
    for (String name : tableNames) {
      System.out.println("name = " + name);
    }

    return tableNames.isEmpty();
  }

  public static Set<String> getDBTables(Connection connection) throws SQLException {
    Set<String> tablesNames = new LinkedHashSet<String>();
    DatabaseMetaData metaData = connection.getMetaData();
    ResultSet rs = metaData.getTables(null, null, null, new String[]{"TABLE"});
    while (rs.next()) {
      tablesNames.add(rs.getString("TABLE_NAME"));
    }
    return tablesNames;
  }

}
