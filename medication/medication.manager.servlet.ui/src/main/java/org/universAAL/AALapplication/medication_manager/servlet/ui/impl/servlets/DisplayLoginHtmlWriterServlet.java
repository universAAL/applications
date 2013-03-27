package org.universAAL.AALapplication.medication_manager.servlet.ui.impl.servlets;

import org.universAAL.AALapplication.medication_manager.persistence.layer.PersistentService;
import org.universAAL.AALapplication.medication_manager.servlet.ui.impl.parser.script.forms.DisplayLoginScriptForm;
import org.universAAL.AALapplication.medication_manager.servlet.ui.impl.parser.script.forms.ScriptForm;
import org.universAAL.AALapplication.medication_manager.servlet.ui.impl.servlets.helpers.Session;
import org.universAAL.AALapplication.medication_manager.servlet.ui.impl.servlets.helpers.SessionTracking;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.universAAL.AALapplication.medication_manager.servlet.ui.impl.Activator.*;
import static org.universAAL.AALapplication.medication_manager.servlet.ui.impl.Util.*;

/**
 * @author George Fournadjiev
 */
public final class DisplayLoginHtmlWriterServlet extends BaseHtmlWriterServlet {

  private final Object lock = new Object();

  public DisplayLoginHtmlWriterServlet(SessionTracking sessionTracking) {
    super(LOGIN_FILE_NAME, sessionTracking);
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

    synchronized (lock) {
      Session session = getSession(req);
      String loggingError = (String) session.getAttribute(LOGIN_ERROR);
      boolean errorLogging = false;
      if (loggingError != null) {
        errorLogging = true;
      }
      session.removeAttribute(LOGIN_ERROR);
      handleResponse(resp, errorLogging);
    }
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    doGet(req, resp);
  }


  private void handleResponse(HttpServletResponse resp, boolean hasScript) throws IOException {
    try {
      PersistentService persistentService = getPersistentService();
      ScriptForm scriptForm = new DisplayLoginScriptForm(persistentService, hasScript);
      sendResponse(resp, scriptForm);
    } catch (Exception e) {
      sendErrorResponse(resp, e);
    }
  }


}
