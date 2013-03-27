package org.universAAL.AALapplication.medication_manager.servlet.ui.impl.servlets;

import org.universAAL.AALapplication.medication_manager.persistence.layer.PersistentService;
import org.universAAL.AALapplication.medication_manager.persistence.layer.entities.Person;
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
      isServletSet(displayServlet, "displayServlet");
      isServletSet(selectUserHtmlWriterServlet, "selectUserHtmlWriterServlet");

      Session session = getSession(req);
      Person doctor = (Person) session.getAttribute(LOGGED_DOCTOR);

      if (doctor == null) {
        displayServlet.doGet(req, resp);
        return;
      }

      String cancel = req.getParameter(CANCEL);
      if (cancel != null && TRUE.equalsIgnoreCase(cancel)) {
        session.removeAttribute(NEW_PRESCRIPTION);
      }

      Person patient = (Person) session.getAttribute(PATIENT);
      if (patient == null) {
        patient = getPatient(req, session);
        session.setAttribute(PATIENT, patient);
      }

      handleResponse(resp, patient);
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
    try {
      id = Integer.valueOf(patientId);
    } catch (NumberFormatException e) {
      throw new MedicationManagerServletUIException("The user id is not a integer number : " + patientId);
    }

    if (id <= 0) {
      throw new MedicationManagerServletUIException("The user id must be positive integer: " + id);
    }

    session.setAttribute(USER, patientId);

    return getPatientById(id);

  }

  private void handleResponse(HttpServletResponse resp, Person patient) throws IOException {
    try {
      PersistentService persistentService = getPersistentService();
      ScriptForm scriptForm = new ListPrescriptionsScriptForm(persistentService, patient);
      sendResponse(resp, scriptForm);
    } catch (Exception e) {
      sendErrorResponse(resp, e);
    }
  }


}
