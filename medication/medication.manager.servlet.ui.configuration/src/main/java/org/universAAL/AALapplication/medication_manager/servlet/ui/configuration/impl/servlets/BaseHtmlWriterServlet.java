package org.universAAL.AALapplication.medication_manager.servlet.ui.configuration.impl.servlets;

import org.universAAL.AALapplication.medication_manager.servlet.ui.base.export.helpers.SessionTracking;
import org.universAAL.AALapplication.medication_manager.servlet.ui.base.export.parser.HtmlParser;
import org.universAAL.AALapplication.medication_manager.servlet.ui.base.export.parser.script.forms.ScriptForm;
import org.universAAL.AALapplication.medication_manager.servlet.ui.configuration.impl.Log;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import static org.universAAL.AALapplication.medication_manager.servlet.ui.base.export.helpers.ServletUtil.*;


/**
 * @author George Fournadjiev
 */
public abstract class BaseHtmlWriterServlet extends BaseServlet {

  protected final HtmlParser htmlParser;

  protected BaseHtmlWriterServlet(String htmlFileName, SessionTracking sessionTracking) {
    super(sessionTracking);

    String htmlText = getHtml(htmlFileName, getClass().getClassLoader());
    htmlParser = new HtmlParser(htmlText);
  }

  public void sendResponse(HttpServletRequest req, HttpServletResponse resp,
                           ScriptForm scriptForm) throws IOException {

    try {

      String scriptText = scriptForm.createScriptText();

      sendSuccessfulResponse(resp, scriptText);

    } catch (Exception e) {
      Log.error(e.fillInStackTrace(), "Unexpected Error occurred", getClass());
      sendErrorResponse(req,  resp, e);

    }

  }

  private void sendSuccessfulResponse(HttpServletResponse resp, String script) throws IOException {
    String htmlOutput = htmlParser.addScriptElement(script);

    PrintWriter writer = resp.getWriter();

    writer.println(htmlOutput);
  }


}
