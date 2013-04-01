package org.universAAL.AALapplication.medication_manager.servlet.ui.impl.servlets;

import org.universAAL.AALapplication.medication_manager.persistence.layer.PersistentService;
import org.universAAL.AALapplication.medication_manager.persistence.layer.entities.Person;
import org.universAAL.AALapplication.medication_manager.servlet.ui.impl.Log;
import org.universAAL.AALapplication.medication_manager.servlet.ui.impl.MedicationManagerServletUIException;
import org.universAAL.AALapplication.medication_manager.servlet.ui.impl.parser.script.forms.ListPrescriptionsScriptForm;
import org.universAAL.AALapplication.medication_manager.servlet.ui.impl.parser.script.forms.ScriptForm;
import org.universAAL.AALapplication.medication_manager.servlet.ui.impl.servlets.helpers.Session;
import org.universAAL.AALapplication.medication_manager.servlet.ui.impl.servlets.helpers.SessionTracking;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.universAAL.AALapplication.medication_manager.servlet.ui.impl.Activator.*;
import static org.universAAL.AALapplication.medication_manager.servlet.ui.impl.DatabaseSimulation.*;
import static org.universAAL.AALapplication.medication_manager.servlet.ui.impl.Util.*;

/**
 * @author George Fournadjiev
 */
public final class ListPrescriptionsHtmlWriterServlet extends BaseHtmlWriterServlet {

  private static final String USER = "user";
  private final Object lock = new Object();
  private DisplayLoginHtmlWriterServlet displayServlet;
  private SelectUserHtmlWriterServlet selectUserHtmlWriterServlet;

  private static final String LIST_PRESCRIPTION_HTML_FILE_NAME = "prescriptions.html";

  public ListPrescriptionsHtmlWriterServlet(SessionTracking sessionTracking) {
    super(LIST_PRESCRIPTION_HTML_FILE_NAME, sessionTracking);
  }

  public void setDisplayServlet(DisplayLoginHtmlWriterServlet displayServlet) {
    this.displayServlet = displayServlet;
  }

  public void setSelectUserHtmlWriterServlet(SelectUserHtmlWriterServlet selectUserHtmlWriterServlet) {
    this.selectUserHtmlWriterServlet = selectUserHtmlWriterServlet;
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    doGet(req, resp);
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    synchronized (lock) {
      try {
        isServletSet(displayServlet, "displayServlet");
        isServletSet(selectUserHtmlWriterServlet, "selectUserHtmlWriterServlet");

        Session session = getSession(req, resp, getClass());
        Person doctor = (Person) session.getAttribute(LOGGED_DOCTOR);

        if (doctor == null) {
          debugSessions(session.getId(), "if(doctor is null) the servlet doGet/doPost method", getClass());
          displayServlet.doGet(req, resp);
          return;
        }

        String cancel = req.getParameter(CANCEL);
        if (cancel != null && TRUE.equalsIgnoreCase(cancel)) {
          debugSessions(session.getId(), "cancel (removing PRESCRIPTION_VIEW   " +
              "the servlet doGet/doPost method", getClass());
          session.removeAttribute(PRESCRIPTION_VIEW);
        }

        Person patient = getPatient(req, session);
        debugSessions(session.getId(), "Patient found and set the attribute the servlet doGet/doPost method", getClass());
        session.setAttribute(PATIENT, patient);
        session.removeAttribute(PRESCRIPTION_VIEW);

        debugSessions(session.getId(), "End of the servlet doGet/doPost method", getClass());
        handleResponse(req, resp, patient);
      } catch (Exception e) {
        Log.error(e.fillInStackTrace(), "Unexpected Error occurred", getClass());
        sendErrorResponse(req, resp, e);
      }
    }
  }

  private Person getPatient(HttpServletRequest req, Session session) {
    String patientId = req.getParameter(USER);

    if (patientId == null) {
      patientId = (String) session.getAttribute(USER);
    }

    if (patientId == null) {
      throw new MedicationManagerServletUIException("Missing selected user");
    }

    int id;
    id = getIntFromString(patientId, "patientId");

    if (id <= 0) {
      throw new MedicationManagerServletUIException("The user id must be positive integer: " + id);
    }

    session.setAttribute(USER, patientId);

    return getPatientById(id);

  }

  private void handleResponse(HttpServletRequest req, HttpServletResponse resp, Person patient) throws IOException {
    try {
      PersistentService persistentService = getPersistentService();
      ScriptForm scriptForm = new ListPrescriptionsScriptForm(persistentService, patient);
      sendResponse(req, resp, scriptForm);
    } catch (Exception e) {
      sendErrorResponse(req, resp, e);
    }
  }


}
