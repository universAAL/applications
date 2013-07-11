package org.universAAL.AALapplication.medication_manager.servlet.ui.impl;


import org.universAAL.AALapplication.medication_manager.servlet.ui.base.export.parser.script.Pair;
import org.universAAL.AALapplication.medication_manager.servlet.ui.impl.servlets.NewPrescriptionView;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.StringReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * @author George Fournadjiev
 */
public final class Util {


  public static final String LOGGED_DOCTOR = "Doctor";
  public static final String LOGIN_HTML_SERVLET_ALIAS = "/login.html";
  public static final String LOGIN_SERVLET_ALIAS = "/login";
  public static final String SELECT_USER_SERVLET_ALIAS = "/select_user";
  public static final String LIST_PRESCRIPTIONS_SERVLET_ALIAS = "/display_prescriptions";
  public static final String NEW_PRESCRIPTION_SERVLET_ALIAS = "/display_new_prescription";
  public static final String HANDLE_NEW_PRESCRIPTION_SERVLET_ALIAS = "/handle_new_prescription";
  public static final String MEDICINE_SERVLET_ALIAS = "/display_new_medicine";
  public static final String HANDLE_MEDICINE_SERVLET_ALIAS = "/handle_new_medicine";
  public static final String ERROR_PAGE_SERVLET_ALIAS = "/error";
  public static final String LOGIN_ERROR = "LOGIN_ERROR";
  public static final String LOGIN_FILE_NAME = "login.html";
  public static final String ERROR_FILE_NAME = "error.html";
  public static final String EMPTY = "";
  public static final String CANCEL = "cancel";
  public static final String TRUE = "true";
  public static final String PATIENT = "PATIENT";
  public static final Pair<String> EMPTY_PAIR = new Pair<String>(null, null);
  public static final String ALERT = "alert";
  public static final String MISSED = "missed";
  public static final String NEW_DOSE = "new_dose";
  public static final String SHORTAGE = "shortage";

  private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
  public static final String PRESCRIPTION_VIEW = "prescription_view";
  public static final String DELETE_MEDICINE_VIEW_ID = "deleteID";
  public static final String DATE = "date";
  public static final String NOTES = "notes";
  public static final String ERROR = "ERROR";
  public static final String USER = "user";

  private Util() {
  }

  public static void isServletSet(HttpServlet servlet, String servletName) {
    if (servlet == null) {
      throw new MedicationManagerServletUIException("The " + servletName + " is not set");
    }
  }

  public static String getHtml(String resourcePath) {
    InputStream inputStream = Util.class.getClassLoader().getResourceAsStream(resourcePath);

    if (inputStream == null) {
      throw new MedicationManagerServletUIException("The resource: " + resourcePath + " cannot be found");
    }

    BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));

    return getHtmlText(br);
  }

  private static String getHtmlText(BufferedReader br) {
    StringBuffer sb = new StringBuffer();
    try {
      String line = br.readLine();
      while (line != null) {
        sb.append(line);
        sb.append('\n');
        line = br.readLine();
      }
    } catch (IOException e) {
      throw new MedicationManagerServletUIException(e);
    }

    return sb.toString();
  }

  public static void validateParameter(int parameter, String parameterName) {

    if (parameter <= 0) {
      throw new MedicationManagerServletUIException("The parameter : " +
          parameterName + " must be positive number");
    }

  }

  public static void validateParameter(Object parameter, String parameterName) {

    if (parameter == null) {
      throw new MedicationManagerServletUIException("The parameter : " + parameterName + " cannot be null");
    }

  }

  public static Date getDatePlusOneDayDelay(String textDate) {
    try {
      Date date = SIMPLE_DATE_FORMAT.parse(textDate);
      GregorianCalendar calendar = new GregorianCalendar();
      calendar.setTime(date);
      calendar.add(Calendar.DAY_OF_MONTH, 1);

      return calendar.getTime();
    } catch (ParseException e) {
      throw new MedicationManagerServletUIException("The provided text date:" + textDate + " is not a valid date");
    }
  }

  public static int getPositiveNumber(String var, String varName) {

    int value = getIntFromString(var, varName);

    if (value <= 0) {
      throw new MedicationManagerServletUIException("The number is not positive : " + value);
    }

    return value;

  }


  public static int getIntFromString(String var, String varName) {
    if (var == null || var.trim().isEmpty()) {
      throw new MedicationManagerServletUIException("the parameter var cannot be null or empty");
    }
    int value;
    try {
      value = Integer.valueOf(var);
    } catch (NumberFormatException e) {
      throw new MedicationManagerServletUIException("The " + varName + " is not a integer number : " + var);
    }

    return value;
  }

  public static void setDateAndNotes(HttpServletRequest req, NewPrescriptionView newPrescriptionView) {
    String date = req.getParameter(DATE);
    if (date != null && !date.trim().isEmpty()) {
      newPrescriptionView.setStartDate(date);
    }

    String notes = req.getParameter(NOTES);
    if (notes == null) {
      newPrescriptionView.setNotes(EMPTY);
    } else {
      notes = escapeNewLinesAndSingleQuotes(notes);
      newPrescriptionView.setNotes(notes);
    }
  }

  public static void validateRequiredStringField(String value, String propertyName) {
    if (value == null || value.trim().isEmpty()) {
      throw new MedicationManagerServletUIException("The " + propertyName + " String property cannot be set with null or empty");
    }
  }

  public static String escapeNewLinesAndSingleQuotes(String description) {
    StringBuffer sb = new StringBuffer();
    StringReader reader = new StringReader(description);
    LineNumberReader lineNumberReader = new LineNumberReader(reader);

    try {
      String line = lineNumberReader.readLine();
      while (line != null) {
        line = line.replace("'", "\\'");
        sb.append(line);
        line = lineNumberReader.readLine();
        if (line != null) {
          sb.append("\\n");
        }
      }

      return sb.toString();
    } catch (IOException e) {
      throw new MedicationManagerServletUIException(e);
    }

  }

}
