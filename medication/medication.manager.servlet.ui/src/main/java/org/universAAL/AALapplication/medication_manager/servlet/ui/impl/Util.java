package org.universAAL.AALapplication.medication_manager.servlet.ui.impl;

import org.universAAL.AALapplication.medication_manager.servlet.ui.impl.parser.script.Pair;

import javax.servlet.http.HttpServlet;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;

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
  public static final String LOGIN_ERROR = "LOGIN_ERROR";
  public static final String LOGIN_FILE_NAME = "login.html";
  public static final String EMPTY = "";
  public static final String CANCEL = "cancel";
  public static final String TRUE = "true";
  public static final String PATIENT = "PATIENT";
  public static final String NEW_PRESCRIPTION = "new_prescription";
  public static final Pair<String> EMPTY_PAIR = new Pair<String>(null, null);

  private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy-mm-dd");
  public static final String PRESCRIPTION_VIEW = "prescription_view";

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

  public static void validateDate(String textDate) {
    try {
      SIMPLE_DATE_FORMAT.parse(textDate);
    } catch (ParseException e) {
      throw new MedicationManagerServletUIException("The provided text date:" + textDate + " is not a valid date");
    }
  }
}
