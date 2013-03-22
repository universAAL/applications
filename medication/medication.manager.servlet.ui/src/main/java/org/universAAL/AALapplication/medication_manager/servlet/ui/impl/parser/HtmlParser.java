package org.universAAL.AALapplication.medication_manager.servlet.ui.impl.parser;

import static org.universAAL.AALapplication.medication_manager.servlet.ui.impl.Activator.*;

/**
 * @author George Fournadjiev
 */
public final class HtmlParser {

  private final String htmlText;

  public HtmlParser(String htmlText) {

    validateParameter(htmlText, "htmlFile");

    this.htmlText = htmlText;
  }

  public String addScriptElement(String script) {
    return htmlText;
  }
}
