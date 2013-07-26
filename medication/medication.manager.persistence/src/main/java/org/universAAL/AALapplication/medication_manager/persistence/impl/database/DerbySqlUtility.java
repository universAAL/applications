/*******************************************************************************
 * Copyright 2012 , http://www.prosyst.com - ProSyst Software GmbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/

package org.universAAL.AALapplication.medication_manager.persistence.impl.database;

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
import java.util.LinkedHashSet;
import java.util.Set;

import static org.universAAL.AALapplication.medication_manager.persistence.impl.Activator.*;
import static org.universAAL.AALapplication.medication_manager.persistence.impl.database.DerbyDatabase.*;

/**
 * @author George Fournadjiev
 */
public final class DerbySqlUtility implements SqlUtility {

  private final Connection connection;

  public DerbySqlUtility(Connection connection) {

    this.connection = connection;
  }

  public int generateId() {
    try {
      return getNextSequenceValue();
    } catch (SQLException e) {
      throw new MedicationManagerPersistenceException("unable to get next id from sequence generator", e);
    }

  }

  private int getNextSequenceValue() throws SQLException {
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

  public void printTablesData() {
    Log.info("Printing all tables", getClass());

    try {
      printTables();
    } catch (SQLException e) {
      throw new MedicationManagerPersistenceException(e);
    }

  }

  public void printTableData(String tableName) {
    Log.info("Printing the following table : %s", getClass(), tableName);

    try {
      printSpecificTable(tableName);
    } catch (SQLException e) {
      throw new MedicationManagerPersistenceException(e);
    }

  }

  public void executeSqlFile(BufferedReader bufferedReader) {
    Log.info("Processing the sql file", getClass());

    boolean autoCommit = true;
    Statement statement = null;
    try {
      autoCommit = connection.getAutoCommit();
      statement = connection.createStatement();
      executeSqlStatementsFromSqlFile(statement, bufferedReader);
    } catch (SQLException e) {
      throw new MedicationManagerPersistenceException(e);
    } finally {
      handleFinally(connection, statement, null, autoCommit);
    }
  }

  public void executeSqlStatement(String sqlStatement) {
    Log.info("Executing the following sql statement : %s", getClass(), sqlStatement);

    try {
      execute(sqlStatement);
    } catch (SQLException e) {
      throw new MedicationManagerPersistenceException(e);
    }

  }

  private void execute(String sqlStatement) throws SQLException {
    Statement statement =
        connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

    ResultSet rs = statement.executeQuery(sqlStatement);

    Set<String> columnNames = findColumnsNames(rs);

    System.out.println("\n\n&&&&&&&&&&&&&&&&&&&&& Printing select query result &&&&&&&&&&&&&&&&&&&&&\n\n");

    int row = 0;
    while (rs.next()) {
      row++;
      printTableRow(row, rs, columnNames);
    }

    if (row == 0) {
      System.out.println("The query didn't return result");
    }

    closeStatement(statement);

    System.out.println("\n\n&&&&&&&&&&&&&&&&&&&&& End of printing select query result &&&&&&&&&&&&&&&&&&&&&\n\n");
  }

  private Set<String> findColumnsNames(ResultSet rs) throws SQLException {
    Set<String> columnNames = new LinkedHashSet<String>();
    ResultSetMetaData resultSetMetaData = rs.getMetaData();
    int columnCount = resultSetMetaData.getColumnCount();
    for (int i = 0; i < columnCount; i++) {
      String cn = resultSetMetaData.getColumnName(i + 1);
      columnNames.add(cn);
    }

    return columnNames;
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

    ResultSet resultSet = statement.executeQuery("SELECT * FROM " + MEDICATION_MANAGER + '.' + tableName);
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

  public Set<String> getDBColumns(String tableName) throws SQLException {
    Set<String> columnNames = new LinkedHashSet<String>();
    DatabaseMetaData metaData = connection.getMetaData();
    ResultSet rs = metaData.getColumns(null, null, tableName, "%");
    while (rs.next()) {
      columnNames.add(rs.getString("COLUMN_NAME"));
    }
    return columnNames;
  }

  private void printSpecificTable(String tableName) throws SQLException {
    Set<String> tablesNamesSet = getDBTables(connection);

    boolean exists = false;

    for (String tb : tablesNamesSet) {
      if (tb.equalsIgnoreCase(tableName)) {
        exists = true;
        printTable(tb);
        break;
      }
    }

    if (!exists) {
      throw new MedicationManagerPersistenceException("The table does not exist : " + tableName);
    }
  }

  private void printTable(String tableName) throws SQLException {
    Statement statement =
        connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

    printTable(statement, tableName);

    closeStatement(statement);
  }

}
