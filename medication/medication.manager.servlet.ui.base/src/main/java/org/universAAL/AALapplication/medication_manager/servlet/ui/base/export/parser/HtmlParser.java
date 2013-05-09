package org.universAAL.AALapplication.medication_manager.servlet.ui.base.export.parser;

import org.universAAL.AALapplication.medication_manager.servlet.ui.base.impl.MedicationManagerServletUIBaseException;

import java.util.LinkedList;
import java.util.List;

import static org.universAAL.AALapplication.medication_manager.servlet.ui.base.impl.Activator.*;

/**
 * @author George Fournadjiev
 */
public final class HtmlParser {

  private final String firstPart;
  private final String lastPart;

  public HtmlParser(String htmlText) {

    validateParameter(htmlText, "htmlFile");

    int index = htmlText.indexOf("<body>");

    if (index == -1) {
      throw new MedicationManagerServletUIBaseException("Missing <body> tag! Expected to be with the small letters");
    }

    int insertIndex = index + 6;
    firstPart = htmlText.substring(0, insertIndex);
    lastPart = htmlText.substring(insertIndex + 1);
  }

  public String addScriptElement(String script) {

    List<String> parts = new LinkedList<String>();

    parts.add(firstPart);
    parts.add(script);
    parts.add(lastPart);

    return getOutputHtml(parts);
  }

  public String getOutputHtml(List<String> parts) {
    StringBuffer sb = new StringBuffer();

    for (String p : parts) {
      sb.append('\n');
      sb.append(p);
      sb.append('\n');
    }

    return sb.toString();
  }
}
