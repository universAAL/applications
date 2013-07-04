package org.universAAL.AALapplication.medication_manager.servlet.ui.configuration.impl.servlets;

import org.universAAL.AALapplication.medication_manager.servlet.ui.base.export.helpers.Session;
import org.universAAL.AALapplication.medication_manager.servlet.ui.base.export.helpers.SessionTracking;
import org.universAAL.AALapplication.medication_manager.servlet.ui.base.export.parser.script.forms.DisplayErrorScriptForm;
import org.universAAL.AALapplication.medication_manager.servlet.ui.base.export.parser.script.forms.ScriptForm;
import org.universAAL.AALapplication.medication_manager.servlet.ui.configuration.impl.Log;
import org.universAAL.AALapplication.medication_manager.servlet.ui.configuration.impl.MedicationManagerServletUIConfigurationException;
import org.universAAL.AALapplication.medication_manager.servlet.ui.configuration.impl.Util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.PrintWriter;
import java.io.StringReader;

/**
 * @author George Fournadjiev
 */
public final class DisplayErrorPageWriterServlet extends BaseHtmlWriterServlet {

  private final Object lock = new Object();

  public DisplayErrorPageWriterServlet(SessionTracking sessionTracking) {
    super(Util.ERROR_FILE_NAME, sessionTracking);
  }


  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

    synchronized (lock) {
      try {
        Session session = getSession(req, resp, getClass());

        String message = (String) session.getAttribute(ERROR);
        if (message == null) {
          message = "Internal error";
        }

        message = escapeNewLinesAndSingleQuotes(message);

        handleResponse(resp, message);
      } catch (Exception e) {
        Log.error(e.fillInStackTrace(), "Unexpected Error occurred", getClass());
        sendPlainErrorResponse(resp, e);
      }
    }
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    doGet(req, resp);
  }


  private void handleResponse(HttpServletResponse resp, String message) throws IOException {
    try {
      ScriptForm scriptForm = new DisplayErrorScriptForm(message);
      String scriptText = scriptForm.createScriptText();
      String htmlOutput = htmlParser.addScriptElement(scriptText);
      PrintWriter writer = resp.getWriter();
      writer.println(htmlOutput);
    } catch (Exception e) {
      Log.error(e.fillInStackTrace(), "Unexpected Error occurred", getClass());
      sendPlainErrorResponse(resp, e);
    }
  }

  private void sendPlainErrorResponse(HttpServletResponse resp, Exception e) throws IOException {
    StringBuffer sb = new StringBuffer();

    sb.append("Internal error - an exception occurred: ");
    sb.append('\n');
    sb.append("class: ");
    sb.append(e.getClass().getName());
    sb.append('\n');
    sb.append("message: ");
    String message = e.getMessage();
    if (message == null || message.trim().isEmpty()) {
      sb.append("Missing message");
    } else {
      sb.append(message);
    }

    resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, sb.toString());
  }

  private String escapeNewLinesAndSingleQuotes(String description) {
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
        throw new MedicationManagerServletUIConfigurationException(e);
      }

    }


}
