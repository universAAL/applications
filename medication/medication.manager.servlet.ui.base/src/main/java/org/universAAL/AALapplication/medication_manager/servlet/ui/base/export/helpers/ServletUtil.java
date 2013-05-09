package org.universAAL.AALapplication.medication_manager.servlet.ui.base.export.helpers;

import org.universAAL.AALapplication.medication_manager.servlet.ui.base.impl.MedicationManagerServletUIBaseException;

import javax.servlet.http.HttpServlet;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.StringReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author George Fournadjiev
 */
public final class ServletUtil {


  private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

  private ServletUtil() {
  }

  public static void isServletSet(HttpServlet servlet, String servletName) {
      if (servlet == null) {
        throw new MedicationManagerServletUIBaseException("The " + servletName + " is not set");
      }
    }

    public static String getHtml(String resourcePath, ClassLoader classLoader) {
      InputStream inputStream = classLoader.getResourceAsStream(resourcePath);

      if (inputStream == null) {
        throw new MedicationManagerServletUIBaseException("The resource: " + resourcePath + " cannot be found");
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
        throw new MedicationManagerServletUIBaseException(e);
      }

      return sb.toString();
    }

  public static void validateParameter(int parameter, String parameterName) {

    if (parameter <= 0) {
      throw new MedicationManagerServletUIBaseException("The parameter : " +
          parameterName + " must be positive number");
    }

  }

  public static void validateParameter(Object parameter, String parameterName) {

    if (parameter == null) {
      throw new MedicationManagerServletUIBaseException("The parameter : " + parameterName + " cannot be null");
    }

  }

  public static Date getDate(String textDate) {
    try {
      return SIMPLE_DATE_FORMAT.parse(textDate);
    } catch (ParseException e) {
      throw new MedicationManagerServletUIBaseException("The provided text date:" + textDate + " is not a valid date");
    }
  }

  public static int getPositiveNumber(String var, String varName) {

    int value = getIntFromString(var, varName);

    if (value <= 0) {
      throw new MedicationManagerServletUIBaseException("The number is not positive : " + value);
    }

    return value;

  }


  public static int getIntFromString(String var, String varName) {
    if (var == null || var.trim().isEmpty()) {
      throw new MedicationManagerServletUIBaseException("the parameter var cannot be null or empty");
    }
    int value;
    try {
      value = Integer.valueOf(var);
    } catch (NumberFormatException e) {
      throw new MedicationManagerServletUIBaseException("The " + varName + " is not a integer number : " + var);
    }

    return value;
  }

  public static void validateRequiredStringField(String value, String propertyName) {
    if (value == null || value.trim().isEmpty()) {
      throw new MedicationManagerServletUIBaseException("The " + propertyName + " String property cannot be set with null or empty");
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
      throw new MedicationManagerServletUIBaseException(e);
    }

  }
}
