package org.universAAL.AALapplication.medication_manager.servlet.ui.configuration.impl.servlets;

import org.universAAL.AALapplication.medication_manager.persistence.layer.PersistentService;
import org.universAAL.AALapplication.medication_manager.servlet.ui.base.export.helpers.Session;
import org.universAAL.AALapplication.medication_manager.servlet.ui.base.export.helpers.SessionTracking;
import org.universAAL.AALapplication.medication_manager.servlet.ui.base.export.parser.script.forms.DisplayLoginScriptForm;
import org.universAAL.AALapplication.medication_manager.servlet.ui.base.export.parser.script.forms.ScriptForm;
import org.universAAL.AALapplication.medication_manager.servlet.ui.configuration.impl.Log;
import org.universAAL.AALapplication.medication_manager.servlet.ui.configuration.impl.Util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.universAAL.AALapplication.medication_manager.servlet.ui.configuration.impl.Activator.*;

/**
 * @author George Fournadjiev
 */
public final class DisplayLoginHtmlWriterServlet extends BaseHtmlWriterServlet {

  private final Object lock = new Object();

  public DisplayLoginHtmlWriterServlet(SessionTracking sessionTracking) {
    super(Util.LOGIN_FILE_NAME, sessionTracking);
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

    synchronized (lock) {
      try {
        Session session = getSession(req, resp, getClass());
        String loggingError = (String) session.getAttribute(Util.LOGIN_ERROR);
        boolean errorLogging = false;
        if (loggingError != null) {
          errorLogging = true;
        }
        session.removeAttribute(Util.LOGIN_ERROR);

        debugSessions(session.getId(), "End of the servlet doGet/doPost method", getClass());

        handleResponse(req, resp, errorLogging);

      } catch (Exception e) {
        Log.error(e.fillInStackTrace(), "Unexpected Error occurred", getClass());
        sendErrorResponse(req, resp, e);
      }
    }
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    doGet(req, resp);
  }


  private void handleResponse(HttpServletRequest req, HttpServletResponse resp, boolean hasScript) throws IOException {
    try {
      PersistentService persistentService = getPersistentService();
      ScriptForm scriptForm = new DisplayLoginScriptForm(persistentService, hasScript);
      sendResponse(req, resp, scriptForm);
    } catch (Exception e) {
      sendErrorResponse(req, resp, e);
    }
  }


}
