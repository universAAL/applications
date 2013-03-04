package org.universAAL.AALapplication.contact_manager.persistence.impl.database;

import org.universAAL.AALapplication.contact_manager.persistence.impl.ContactManagerPersistenceException;
import org.universAAL.AALapplication.contact_manager.persistence.impl.Log;
import org.universAAL.AALapplication.contact_manager.persistence.impl.SqlScriptParser;
import org.universAAL.AALapplication.contact_manager.persistence.layer.EmailEnum;
import org.universAAL.AALapplication.contact_manager.persistence.layer.Mail;
import org.universAAL.AALapplication.contact_manager.persistence.layer.TelEnum;
import org.universAAL.AALapplication.contact_manager.persistence.layer.Telephone;
import org.universAAL.AALapplication.contact_manager.persistence.layer.Type;
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

  private static final String TEL = "tel";
  private static final String EMAIL = "email";
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

  public void setAutocommit(boolean autocommit) {
    try {
      connection.setAutoCommit(autocommit);
    } catch (SQLException e) {
      throw new ContactManagerPersistenceException(e);
    }
  }

  public PreparedStatement createEditStatementVCard(String userUri) throws SQLException {
    String sqlVCard = "UPDATE CONTACT_MANAGER.VCARD SET VCARD_VERSION = ?, LAST_REVISION = ?, NICKNAME = ?, " +
        "DISPLAY_NAME = ?, UCI_LABEL = ?, UCI_ADDITIONAL_DATA = ?, ABOUT_ME = ?, " +
        "BDAY = ?, FN = ? WHERE USER_URI = '" + userUri.toUpperCase() + "'";

    System.out.println("sqlVCard = " + sqlVCard);

    return connection.prepareStatement(sqlVCard);

  }

  public PreparedStatement createEditDeleteStatementTypes(String userUri) throws SQLException {
    String typesSql = "delete from CONTACT_MANAGER.TYPES where VCARD_FK = '" + userUri + "'";

    System.out.println("typesSql = " + typesSql);

    return connection.prepareStatement(typesSql);

  }

  public PreparedStatement createAddStatementVCard() throws SQLException {
    String sqlVCard = "INSERT INTO CONTACT_MANAGER.VCARD (USER_URI, VCARD_VERSION, LAST_REVISION, NICKNAME,\n" +
        "DISPLAY_NAME, UCI_LABEL, UCI_ADDITIONAL_DATA, ABOUT_ME, BDAY, FN)\n" +
        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    System.out.println("sqlVCard = " + sqlVCard);

    return connection.prepareStatement(sqlVCard);

  }

  public PreparedStatement createAddStatementTypes() throws SQLException {
    String typesSql = "INSERT INTO CONTACT_MANAGER.TYPES (ID, NAME, TYPE, VALUE, VCARD_FK)\n" +
        "VALUES (?, ?, ?, ?, ?)";

    System.out.println("typesSql = " + typesSql);

    return connection.prepareStatement(typesSql);

  }

  public PreparedStatement createDeleteStatementVCard(String userUri) throws SQLException {
    String sqlVCard = "delete from CONTACT_MANAGER.VCARD where USER_URI = '" + userUri + "'";

    System.out.println("sqlVCard = " + sqlVCard);

    return connection.prepareStatement(sqlVCard);

  }

  public PreparedStatement createDeleteStatementTypes(String userUri) throws SQLException {
    String typesSql = "delete from CONTACT_MANAGER.TYPES where VCARD_FK = '" + userUri + "'";

    System.out.println("typesSql = " + typesSql);

    return connection.prepareStatement(typesSql);

  }

  public Statement createGetStatementVCard() throws SQLException {
    return connection.createStatement();

  }

  public Statement createGetStatementTypes() throws SQLException {
    return connection.createStatement();

  }

  public void commit() throws SQLException {
    connection.commit();
  }

  public void rollback() {
    try {
      connection.rollback();
    } catch (SQLException e) {
      //nothing we can do here
      Log.error(e, "Rollback problem", getClass());
    }
  }

  public void removeVCard(String uri, PreparedStatement statementVCard,
                          PreparedStatement statementTypes) throws SQLException {


    statementTypes.execute();


    statementVCard.execute();

  }

  public void saveVCard(VCard vCard, PreparedStatement statementVCard,
                        PreparedStatement statementTypes) throws SQLException {

    statementVCard.setString(1, vCard.getUserUri().toUpperCase());
    statementVCard.setString(2, vCard.getVCardVersion());
    java.sql.Date lr = new java.sql.Date(vCard.getLastRevision().getTime());
    statementVCard.setDate(3, lr);
    statementVCard.setString(4, vCard.getNickname());
    statementVCard.setString(5, vCard.getDisplayName());
    statementVCard.setString(6, vCard.getUciLabel());
    statementVCard.setString(7, vCard.getUciAdditionalData());
    statementVCard.setString(8, vCard.getAboutMe());
    java.sql.Date db = new java.sql.Date(vCard.getBday().getTime());
    statementVCard.setDate(9, db);
    statementVCard.setString(10, vCard.getFn());

    statementVCard.execute();

    insertTypes(vCard, statementTypes);

  }

  private void insertTypes(VCard vCard, PreparedStatement statementTypes) throws SQLException {
    List<Type> types = createTypes(vCard.getTelephones(), vCard.getEmails());

    for (Type t : types) {
      int id = getNextIdFromIdGenerator();
      statementTypes.setInt(1, id);
      statementTypes.setString(2, t.getName());
      statementTypes.setString(3, t.getType());
      statementTypes.setString(4, t.getValue());
      statementTypes.setString(5, vCard.getUserUri().toUpperCase());

      statementTypes.execute();
    }
  }

  public void editVCard(String userUri, VCard vCard, PreparedStatement statementVCard,
                        PreparedStatement statementDeleteTypes, PreparedStatement statementTypes) throws SQLException {


    statementVCard.setString(1, vCard.getVCardVersion());
    java.sql.Date lr = new java.sql.Date(vCard.getLastRevision().getTime());
    statementVCard.setDate(2, lr);
    statementVCard.setString(3, vCard.getNickname());
    statementVCard.setString(4, vCard.getDisplayName());
    statementVCard.setString(5, vCard.getUciLabel());
    statementVCard.setString(6, vCard.getUciAdditionalData());
    statementVCard.setString(7, vCard.getAboutMe());
    java.sql.Date db = new java.sql.Date(vCard.getBday().getTime());
    statementVCard.setDate(8, db);
    statementVCard.setString(9, vCard.getFn());

    statementVCard.execute();

    int affectedRows = statementDeleteTypes.executeUpdate();

    if (affectedRows == 0) {
      throw new ContactManagerPersistenceException("No affected row by the update statement. " +
          "Maybe no such user with the following userUri:" + userUri);
    }

    insertTypes(vCard, statementTypes);
  }

  private List<Type> createTypes(List<Telephone> telephones, List<Mail> emails) {
    List<Type> types = new ArrayList<Type>();

    for (Telephone t : telephones) {
      Type type = new Type(t.getValue(), TEL, t.getType());
      types.add(type);
    }

    for (Mail m : emails) {
      Type type = new Type(m.getValue(), EMAIL, m.getType());
      types.add(type);
    }

    return types;
  }

  public VCard getVCard(String userUri, Statement statementVCard, Statement statementTypes) throws SQLException {
    String sqlQuery = "select * from " + CONTACT_MANAGER + "." + VCARD +
        "\n\t where user_uri='" + userUri + '\'';

    sqlQuery = sqlQuery.toUpperCase();

    ResultSet rs = statementVCard.executeQuery(sqlQuery);
    VCard vCard = null;
    if (rs.next()) {
      vCard = createVCard(rs, statementTypes);
    }

    return vCard;

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


  private VCard createVCard(ResultSet rs, Statement statementTypes) throws SQLException {

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

    List<Type> types = getTypes(userUri, statementTypes);
    List<Telephone> telephones = getTelephones(types);
    List<Mail> mails = getMails(types);

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

  private List<Type> getTypes(String userUri, Statement statementTypes) throws SQLException {
    String sqlQuery = "select * from " + CONTACT_MANAGER + '.' + TYPES +
        "\n\t where vcard_fk='" + userUri + '\'';

    sqlQuery = sqlQuery.toUpperCase();

    List<Type> types = new ArrayList<Type>();

    System.out.println("sqlQuery = " + sqlQuery);

    ResultSet rs = statementTypes.executeQuery(sqlQuery);
    while (rs.next()) {
      Type type = createType(rs);
      types.add(type);
    }


    return types;
  }

  private Type createType(ResultSet rs) throws SQLException {
    String name = rs.getString("name");
    String type = rs.getString("type");
    String value = rs.getString("value");

    return new Type(value, name, type);
  }

  private List<Telephone> getTelephones(List<Type> types) {

    List<Telephone> telephones = new ArrayList<Telephone>();

    for (Type type : types) {
      if (type.getName().equalsIgnoreCase(TEL)) {
        Telephone tel = createTelephone(type);
        telephones.add(tel);
      }
    }

    return telephones;
  }

  private Telephone createTelephone(Type typeObject) {
    String type = typeObject.getType();
    String value = typeObject.getValue();

    TelEnum enumFromValue = TelEnum.getEnumFromValue(type);

    return new Telephone(value, enumFromValue);
  }

  private List<Mail> getMails(List<Type> types) throws SQLException {
    List<Mail> telephones = new ArrayList<Mail>();

    for (Type type : types) {
      if (type.getName().equalsIgnoreCase(EMAIL)) {
        Mail tel = createMail(type);
        telephones.add(tel);
      }
    }

    return telephones;
  }

  private Mail createMail(Type typeObject) throws SQLException {
    String type = typeObject.getType();
    String value = typeObject.getValue();

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
