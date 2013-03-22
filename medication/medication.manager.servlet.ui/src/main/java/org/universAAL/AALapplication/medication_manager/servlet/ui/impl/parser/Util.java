package org.universAAL.AALapplication.medication_manager.servlet.ui.impl.parser;

import org.universAAL.AALapplication.medication_manager.servlet.ui.impl.MedicationManagerServletUIException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @author George Fournadjiev
 */
public final class Util {

  private Util() {
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

}
