package org.universAAL.AALapplication.medication_manager.configuration.impl;

import org.universAAL.AALapplication.medication_manager.configuration.MedicationManagerConfigurationException;
import org.universAAL.AALapplication.medication_manager.configuration.Pair;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Set;

import static java.util.Calendar.*;

/**
 * @author George Fournadjiev
 */
public final class CurrentDateInserter {

  public static final String CURRENT_DATE = "current_date";
  public static final String DATE_FORMAT_SEPARATOR = "-";

  public String insertCurrentDate(String sqlStatement) {

    if (!sqlStatement.contains(CURRENT_DATE)) {
      System.out.println("skipping");
      return sqlStatement;
    }

    Set<Pair<String, Integer>> pairsSet = findTextToReplace(sqlStatement);

    for (Pair<String, Integer> pair : pairsSet) {
      Integer daysAfterCurrentDate = pair.getSecond();
      String dateToInsert = createDate(daysAfterCurrentDate);

      String textToReplace = pair.getFirst();
      sqlStatement = sqlStatement.replace(textToReplace, dateToInsert);
      System.out.println("sqlStatement = " + sqlStatement);
    }

    return sqlStatement;
  }

  private String createDate(Integer daysAfterCurrentDate) {
    Calendar date = new GregorianCalendar();

    if (daysAfterCurrentDate > 0) {
      int dayNumber = date.get(DAY_OF_MONTH);
      date.set(DAY_OF_MONTH, dayNumber + daysAfterCurrentDate);
    }

    int year = date.get(YEAR);
    int monthNumber = date.get(MONTH) + 1;
    String month;
    if (monthNumber < 10) {
      month = "0" + String.valueOf(monthNumber);
    } else {
      month = String.valueOf(monthNumber);
    }

    int dayNumber = date.get(DAY_OF_MONTH);
    String day;
    if (dayNumber < 10) {
      day = "0" + String.valueOf(dayNumber);
    } else {
      day = String.valueOf(dayNumber);
    }

    return year + DATE_FORMAT_SEPARATOR + month + DATE_FORMAT_SEPARATOR + day;
  }

  private Set<Pair<String, Integer>> findTextToReplace(String sqlStatement) {
    Set<Pair<String, Integer>> pairSet = new HashSet<Pair<String, Integer>>();

    int beginIndex = sqlStatement.indexOf(CURRENT_DATE);
    int endIndex = beginIndex + CURRENT_DATE.length() + 1;
    Pair<String, Integer> pair = null;
    if (beginIndex > 0) {
      pair = getStringIntegerPair(sqlStatement, beginIndex, endIndex);
      pairSet.add(pair);
    }

    sqlStatement = sqlStatement.substring(endIndex + 1);
    beginIndex = sqlStatement.indexOf(CURRENT_DATE);
    endIndex = beginIndex + CURRENT_DATE.length() + 1;
    pair = null;
    if (beginIndex > 0) {
      pair = getStringIntegerPair(sqlStatement, beginIndex, endIndex);
      pairSet.add(pair);
    }


    if (pairSet.isEmpty()) {
      throw new MedicationManagerConfigurationException("Error while parsing (the pairSet is empty)");
    }

    return pairSet;
  }

  private Pair<String, Integer> getStringIntegerPair(String sqlStatement, int beginIndex, int endIndex) {

    String replaceText = sqlStatement.substring(beginIndex, endIndex);

    String lastChar = replaceText.substring(replaceText.length() - 1);

    int daysAfterCurrentDate = Integer.parseInt(lastChar);

    return new Pair<String, Integer>(replaceText, daysAfterCurrentDate);
  }

}
