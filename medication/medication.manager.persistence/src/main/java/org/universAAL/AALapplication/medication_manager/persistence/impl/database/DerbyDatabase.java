package org.universAAL.AALapplication.medication_manager.persistence.impl.database;

import org.universAAL.AALapplication.medication_manager.configuration.SqlScriptParser;
import org.universAAL.AALapplication.medication_manager.persistence.SqlUtility;
import org.universAAL.AALapplication.medication_manager.persistence.impl.Log;
import org.universAAL.AALapplication.medication_manager.persistence.impl.MedicationManagerPersistenceException;

import java.io.BufferedReader;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import static org.universAAL.AALapplication.medication_manager.persistence.impl.Activator.*;

/**
 * @author George Fournadjiev
 */
public final class DerbyDatabase implements Database {

  private final Connection connection;

  public static final String MEDICATION_MANAGER = "Medication_Manager";

  public DerbyDatabase(Connection connection) {

    this.connection = connection;
  }

  public void initDatabase() throws Exception {
    Class.forName("org.apache.derby.jdbc.EmbeddedDriver").newInstance();
    handleSequence();
    if (checkIfTablesNotExists()) {
      createTablesAndPopulateThemInOneTransaction();
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

  public SqlUtility getSqlUtility() {
    return new SqlUtilityImpl(connection);
  }

  private void handleSequence() throws SQLException {
    try {
      int sequenceValue = getNextSequenceValue();
      System.out.println("sequenceValue = " + sequenceValue);
    } catch (SQLException e) {
      createSequence();
    }
  }

  private int getNextSequenceValue() throws SQLException {
    PreparedStatement statement = null;
    try {
      statement = connection.prepareStatement(
          "VALUES NEXT VALUE FOR " + MEDICATION_MANAGER + ".ID_GEN");
      ResultSet rs = statement.executeQuery();
      while (rs.next()) {
        int id = rs.getInt(1);
        return id;
      }
    } finally {
      closeStatement(statement);
    }

    throw new MedicationManagerPersistenceException("Unexpected missing sequence value");
  }

  private void createSequence() throws SQLException {
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
