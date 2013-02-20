package org.universAAL.AALapplication.contact_manager.persistence.impl.database;

import org.universAAL.AALapplication.contact_manager.persistence.impl.ContactManagerPersistenceException;
import org.universAAL.AALapplication.contact_manager.persistence.impl.Log;
import org.universAAL.AALapplication.contact_manager.persistence.impl.SqlScriptParser;

import java.sql.Clob;
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
import static org.universAAL.AALapplication.contact_manager.persistence.impl.Activator.*;
import static org.universAAL.AALapplication.contact_manager.persistence.layer.Util.*;

/**
 * @author George Fournadjiev
 */
public final class DerbyDatabase implements Database {

  private final Connection connection;

  public static final String CONTACT_MANAGER = "Contact_Manager";

  public DerbyDatabase(Connection connection) {
    validateParameter(connection, "connection");

    this.connection = connection;
  }

  public void initDatabase() throws Exception {
    Class.forName("org.apache.derby.jdbc.EmbeddedDriver").newInstance();
    if (checkIfTablesNotExists()) {
      createTablesAndPopulateThemInOneTransaction();
      createSequence();
    }
  }

  public void printData() {
    try {
      printTables();
    } catch (SQLException e) {
      throw new ContactManagerPersistenceException(e);
    }
  }

  public int getNextIdFromIdGenerator() {
    try {
      int sequenceValue = getNextSequenceValue();
      System.out.println("getNextIdFromIdGenerator() = " + sequenceValue);
      return sequenceValue;
    } catch (SQLException e) {
      throw new ContactManagerPersistenceException("unable to get next id from sequence generator", e);
    }

  }

  public Map<String, Column> getById(String tableName, int id) {
    String message = "There are more than one record with the following id : " + id;
    String sqlQuery = "select * from " + CONTACT_MANAGER + '.' + tableName +
        "\n\t where id=" + id;
    return getStringColumnMapSingleRecord(sqlQuery, tableName, message);
  }

  private Map<String, Column> getStringColumnMapSingleRecord(String sqlQuery, String tableName, String message) {

    Map<String, Column> columns = new LinkedHashMap<String, Column>();

    Statement statement = null;
    try {
      statement = connection.createStatement();

      System.out.println("sqlQuery = " + sqlQuery);

      ResultSet rs = statement.executeQuery(sqlQuery);

      Set<String> columnsNames = getDBColumns(tableName);
      ResultSetMetaData metaData = rs.getMetaData();
      int count = 0;
      while (rs.next()) {
        count++;
        if (count > 1) {
          throw new ContactManagerPersistenceException(message);
        }
        fillColumnsData(columns, columnsNames, rs, metaData);
      }

    } catch (SQLException e) {
      throw new ContactManagerPersistenceException(e);
    } finally {
      closeStatement(statement);
    }

    return columns;
  }

  private Set<String> getDBColumns(String tableName) throws SQLException {
    Set<String> columnNames = new LinkedHashSet<String>();
    DatabaseMetaData metaData = connection.getMetaData();
    ResultSet rs = metaData.getColumns(null, null, tableName, "%");
    while (rs.next()) {
      columnNames.add(rs.getString("COLUMN_NAME"));
    }
    return columnNames;
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
      case TIMESTAMP:
        Timestamp date = rs.getTimestamp(name);
        return new Column(name, date);
      case VARCHAR:
        String string = rs.getString(name);
        return new Column(name, string);
      case CLOB:
        Clob clob = rs.getClob(name);
        String text = clob.getSubString(1, (int) clob.length());
        return new Column(name, text);
      default:
        throw new ContactManagerPersistenceException("Unsupported sql type : " + sqlType);
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
      throw new ContactManagerPersistenceException(e);
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
      throw new ContactManagerPersistenceException(e);
    }
  }

  public int getNextSequenceValue() throws SQLException {
    PreparedStatement statement = null;
    try {
      statement = connection.prepareStatement(
          "VALUES NEXT VALUE FOR " + CONTACT_MANAGER + ".ID_GEN");
      ResultSet rs = statement.executeQuery();
      if (rs.next()) {
        int id = rs.getInt(1);
        return id;
      }
    } finally {
      closeStatement(statement);
    }

    throw new ContactManagerPersistenceException("Unexpected missing sequence value");
  }

  private void createSequence() {
    Log.info("The sequence does not exists, so creating the sequence", getClass());
    PreparedStatement ps = null;
    try {
      ps = connection.prepareStatement(
          "CREATE SEQUENCE " + CONTACT_MANAGER + ".ID_GEN AS BIGINT START WITH 1000");
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

  public void printTablesData() {
    Log.info("Printing all tables", getClass());

    try {
      printTables();
    } catch (SQLException e) {
      throw new ContactManagerPersistenceException(e);
    }

  }

  private void printTables() throws SQLException {
    Set<String> tablesNamesSet = getDBTables(connection);

    if (tablesNamesSet.isEmpty()) {
      Log.info("There aren't tables created in the database", getClass());
      return;
    }

    System.out.println("\n\n&&&&&&&&&&&&&&&&&&&&& Printing all tables &&&&&&&&&&&&&&&&&&&&&\n\n");

    Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

    for (String tableName : tablesNamesSet) {
      printTable(statement, tableName);
    }

    System.out.println("\n\n******************** End of printing all tables ***************************\n\n");

    closeStatement(statement);
  }

  private void printTable(Statement statement, String tableName) throws SQLException {
    System.out.println("\n***************** Printing table : " + tableName + " *****************");
    Set<String> columnNamesSet = getDBColumns(tableName);

    ResultSet resultSet = statement.executeQuery("select * from " + CONTACT_MANAGER + '.' + tableName);
    int row = 0;
    while (resultSet.next()) {
      row++;
      printTableRow(row, resultSet, columnNamesSet);
    }

    if (row == 0) {
      System.out.println("The table is empty");
    }

    System.out.println("\n***************** End of printing table : " + tableName + " *****************");
  }

  private void printTableRow(int row, ResultSet resultSet, Set<String> columnNamesSet) throws SQLException {
    System.out.println("---------------- row No : " + row + " ----------------");
    for (String columnName : columnNamesSet) {
      String value = resultSet.getString(columnName);
      System.out.println(columnName + " : " + value);
    }
  }


}
