package org.universAAL.AALapplication.contact_manager.persistence.impl.database;

import org.universAAL.AALapplication.contact_manager.persistence.impl.ContactManagerPersistenceException;
import org.universAAL.AALapplication.contact_manager.persistence.impl.Log;
import org.universAAL.AALapplication.contact_manager.persistence.impl.SqlScriptParser;
import org.universAAL.AALapplication.contact_manager.persistence.layer.EmailEnum;
import org.universAAL.AALapplication.contact_manager.persistence.layer.Mail;
import org.universAAL.AALapplication.contact_manager.persistence.layer.TelEnum;
import org.universAAL.AALapplication.contact_manager.persistence.layer.Telephone;
import org.universAAL.AALapplication.contact_manager.persistence.layer.VCard;

import java.sql.Clob;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.universAAL.AALapplication.contact_manager.persistence.impl.Activator.*;
import static org.universAAL.AALapplication.contact_manager.persistence.layer.Util.*;

/**
 * @author George Fournadjiev
 */
public final class DerbyDatabase implements Database {

  private final Connection connection;

  public static final String CONTACT_MANAGER = "Contact_Manager";
  private static final String VCARD = "VCARD";
  private static final String TYPES = "TYPES";

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

  public boolean saveVCard(VCard vCard) {
    //TODO
    return true;
  }

  public VCard getVCard(String userUri) {
    String sqlQuery = "select * from " + CONTACT_MANAGER + "." + VCARD +
        "\n\t where user_uri='" + userUri + '\'';

    sqlQuery = sqlQuery.toUpperCase();

    Statement statement = null;
    try {
      statement = connection.createStatement();

      System.out.println("sqlQuery = " + sqlQuery);

      ResultSet rs = statement.executeQuery(sqlQuery);

      if (rs.next()) {
        return createVCard(rs);
      }
    } catch (SQLException e) {
      throw new ContactManagerPersistenceException(e);
    } finally {
      closeStatement(statement);
    }

    throw new ContactManagerPersistenceException("No VCard records found with the userUri : " + userUri);

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


  private VCard createVCard(ResultSet rs) throws SQLException {

    String userUri = rs.getString("user_uri");
    String vcardVersion = rs.getString("vcard_version");
    Date lastRevision = rs.getDate("last_revision");
    String nickname = rs.getString("nickname");
    String displayName = rs.getString("display_name");
    Clob uciLabelClob = rs.getClob("uci_label");
    String uciLabel = uciLabelClob.getSubString(1, (int) uciLabelClob.length());
    Clob uciAditionalDataClob = rs.getClob("uci_additional_data");
    String uciAditionalData = uciAditionalDataClob.getSubString(1, (int) uciAditionalDataClob.length());
    String aboutMe = rs.getString("about_me");
    Date bday = rs.getDate("bday");
    String fn = rs.getString("fn");

    List<Telephone> telephones = getTelephones(userUri);
    List<Mail> mails = getMails(userUri);

    return new VCard(
        userUri,
        vcardVersion,
        lastRevision,
        nickname,
        displayName,
        uciLabel,
        uciAditionalData,
        aboutMe,
        bday,
        fn,
        telephones,
        mails
    );

  }

  private List<Telephone> getTelephones(String userUri) throws SQLException {
    String sqlQuery = "select * from " + CONTACT_MANAGER + '.' + TYPES +
        "\n\t where name='tel'" + " and " +
        "\n\t vcard_fk='" + userUri + '\'';

    sqlQuery = sqlQuery.toUpperCase();

    List<Telephone> telephones = new ArrayList<Telephone>();

    Statement statement = null;
    try {
      statement = connection.createStatement();

      System.out.println("sqlQuery = " + sqlQuery);

      ResultSet rs = statement.executeQuery(sqlQuery);
      while (rs.next()) {
        Telephone tel = createTelephone(rs);
        telephones.add(tel);
      }
    } catch (SQLException e) {
      throw new ContactManagerPersistenceException(e);
    } finally {
      closeStatement(statement);
    }

    return telephones;
  }

  private Telephone createTelephone(ResultSet rs) throws SQLException {
    String type = rs.getString("type");
    String value = rs.getString("value");

    TelEnum enumFromValue = TelEnum.getEnumFromValue(type);

    return new Telephone(value, enumFromValue);
  }

  private List<Mail> getMails(String userUri) throws SQLException {
    String sqlQuery = "select * from " + CONTACT_MANAGER + '.' + TYPES +
        "\n\t where name='email'" + " and " +
        "\n\t vcard_fk='" + userUri + '\'';

    sqlQuery = sqlQuery.toUpperCase();

    List<Mail> telephones = new ArrayList<Mail>();

    Statement statement = null;
    try {
      statement = connection.createStatement();

      System.out.println("sqlQuery = " + sqlQuery);

      ResultSet rs = statement.executeQuery(sqlQuery);
      while (rs.next()) {
        Mail mail = createMail(rs);
        telephones.add(mail);
      }
    } catch (SQLException e) {
      throw new ContactManagerPersistenceException(e);
    } finally {
      closeStatement(statement);
    }

    return telephones;
  }

  private Mail createMail(ResultSet rs) throws SQLException {
    String type = rs.getString("type");
    String value = rs.getString("value");

    EmailEnum anEnum = EmailEnum.getEnumFromValue(type);

    return new Mail(value, anEnum);
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
