package org.universAAL.AALapplication.medication_manager.servlet.ui.impl.servlets;

import org.universAAL.AALapplication.medication_manager.persistence.layer.PersistentService;
import org.universAAL.AALapplication.medication_manager.servlet.ui.impl.parser.script.forms.NewPrescriptionScriptForm;
import org.universAAL.AALapplication.medication_manager.servlet.ui.impl.parser.script.forms.ScriptForm;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.universAAL.AALapplication.medication_manager.servlet.ui.impl.Activator.*;

/**
 * @author George Fournadjiev
 */
public final class NewPrescriptionServlet extends BaseServlet {

  private final Object lock = new Object();

  private static final String NEW_PRESCRIPTION_HTML_FILE_NAME = "prescription.html";

  public NewPrescriptionServlet() {
    super(NEW_PRESCRIPTION_HTML_FILE_NAME);
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    synchronized (lock) {
      handleResponse(resp);
    }
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    doGet(req, resp);
  }


  private void handleResponse(HttpServletResponse resp) throws IOException {
    try {
      PersistentService persistentService = getPersistentService();
      ScriptForm scriptForm = new NewPrescriptionScriptForm(persistentService);
      sendResponse(resp, scriptForm);
    } catch (Exception e) {
      sendErrorResponse(resp, e);
    }
  }


}
