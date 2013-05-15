package org.universAAL.AALapplication.medication_manager.configuration;

import org.universAAL.AALapplication.medication_manager.configuration.impl.CurrentDateInserter;
import org.universAAL.AALapplication.medication_manager.configuration.impl.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author George Fournadjiev
 */
public final class SqlScriptParser {

  private final Map<Integer, String> sqlStatementMap;
  private final String statementDelimiter;
  private int keyIndex;
  private CurrentDateInserter currentDateInserter;

  public SqlScriptParser() {
    sqlStatementMap = new LinkedHashMap<Integer, String>();
    statementDelimiter = ";";
    currentDateInserter = new CurrentDateInserter();
  }

  public void parseSqlFile(String sqlFileName) {
    keyIndex = 0;

    BufferedReader bufferedReader = getBufferedReader(sqlFileName);

    parse(bufferedReader);

  }

  public void parseSqlFile(BufferedReader bufferedReader) {
    keyIndex = 0;

    parse(bufferedReader);

  }

  private void parse(BufferedReader bufferedReader) {
    try {
      fillSqlStatements(bufferedReader);
    } catch (IOException e) {
      throw new MedicationManagerConfigurationException(e);
    }
  }

  private BufferedReader getBufferedReader(String sqlFileName) {
    InputStream inputStream = getClass().getClassLoader().getResourceAsStream(sqlFileName);
    InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
    return new BufferedReader(inputStreamReader);
  }

  private void fillSqlStatements(BufferedReader bufferedReader) throws IOException {
    StringBuilder sqlStatementBuilder = new StringBuilder();
    String line = bufferedReader.readLine();
    while (line != null) {
      line = line.trim();
      addSqlStatement(bufferedReader, sqlStatementBuilder, line);
      sqlStatementBuilder = new StringBuilder();
      line = bufferedReader.readLine();
    }
  }

  private void addSqlStatement(BufferedReader bufferedReader,
                               StringBuilder sqlStatementBuilder, String line) throws IOException {
    if (isComment(line, bufferedReader)) {
      return;
    }

    if (line.length() > 1) {
      String sqlStatement = getSqlStatement(line, bufferedReader, sqlStatementBuilder);
      sqlStatement = currentDateInserter.insertCurrentDate(sqlStatement);
      keyIndex++;
      sqlStatementMap.put(keyIndex, sqlStatement);
      Log.info("SQL statement added %s", getClass(), '\n' + sqlStatement + "\n\n\n");
    }
  }

  private boolean isComment(String line, BufferedReader bufferedReader) {
    boolean lineComment = isLineComment(line);
    boolean blockComment = isBlockComment(line, bufferedReader);

    return lineComment || blockComment;
  }

  private boolean isBlockComment(String line, BufferedReader bufferedReader) {
    if (!line.startsWith("/*")) {
      return false;
    }

    StringBuilder blockCommentBuilder = new StringBuilder();
    blockCommentBuilder.append('\n');
    blockCommentBuilder.append(line);
    blockCommentBuilder.append('\n');

    try {
      scrollToEndOfBlockComment(bufferedReader, blockCommentBuilder);
      Log.info("Block comment %s", getClass(), blockCommentBuilder.toString());
      return true;
    } catch (IOException e) {
      throw new MedicationManagerConfigurationException(e);
    }

  }

  private void scrollToEndOfBlockComment(BufferedReader bufferedReader,
                                         StringBuilder blockCommentBuilder) throws IOException {

    String line = bufferedReader.readLine();
    String message = "The block comment is not closed (scroll to end of the file)";
    if (line == null) {
      throw new MedicationManagerConfigurationException(message);
    }
    while (!line.endsWith("*/")) {
      blockCommentBuilder.append(line);
      blockCommentBuilder.append('\n');
      line = bufferedReader.readLine();
      if (line == null) {
        throw new MedicationManagerConfigurationException(message);
      }
    }

    blockCommentBuilder.append(line);
  }

  private boolean isLineComment(String line) {
    if (line.startsWith("//")) {
      Log.info("Line comment %s", getClass(), '\n' + line);
      return true;
    }

    return false;
  }

  private String getSqlStatement(String line, BufferedReader bufferedReader,
                                 StringBuilder sqlStatementBuilder) throws IOException {

    while (!line.endsWith(statementDelimiter)) {
      sqlStatementBuilder.append(line.trim());
      sqlStatementBuilder.append('\n');
      line = readNextLine(bufferedReader);
    }

    sqlStatementBuilder.append(line.substring(0, line.lastIndexOf(';')));

    return sqlStatementBuilder.toString();
  }

  private String readNextLine(BufferedReader bufferedReader) throws IOException {
    String line = bufferedReader.readLine();
    if (line == null) {
      throw new MedicationManagerConfigurationException("Sql statement is not closed with the delimiter : "
          + statementDelimiter);
    }
    return line.trim();
  }

  public Map<Integer, String> getSqlStatementMap() {
    if (sqlStatementMap.isEmpty()) {
      throw new MedicationManagerConfigurationException("The SQL statement map is empty");
    }
    return Collections.unmodifiableMap(sqlStatementMap);
  }
}
