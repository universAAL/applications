package org.universAAL.AALapplication.medication_manager.servlet.ui.review.impl.servlets;

import org.universAAL.AALapplication.medication_manager.persistence.layer.PersistentService;
import org.universAAL.AALapplication.medication_manager.persistence.layer.dao.PatientLinksDao;
import org.universAAL.AALapplication.medication_manager.persistence.layer.entities.Person;
import org.universAAL.AALapplication.medication_manager.servlet.ui.review.impl.Log;
import org.universAAL.AALapplication.medication_manager.servlet.ui.review.impl.MedicationManagerReviewException;
import org.universAAL.AALapplication.medication_manager.servlet.ui.review.impl.servlets.forms.UserSelectScriptForm;
import org.universAAL.AALapplication.medication_manager.servlet.ui.base.export.helpers.Session;
import org.universAAL.AALapplication.medication_manager.servlet.ui.base.export.helpers.SessionTracking;
import org.universAAL.AALapplication.medication_manager.servlet.ui.base.export.parser.script.forms.ScriptForm;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static org.universAAL.AALapplication.medication_manager.servlet.ui.review.impl.Activator.*;
import static org.universAAL.AALapplication.medication_manager.servlet.ui.review.impl.Util.*;
import static org.universAAL.AALapplication.medication_manager.servlet.ui.base.export.helpers.ServletUtil.*;


/**
 * @author George Fournadjiev
 */
public final class SelectUserHtmlWriterServlet extends BaseHtmlWriterServlet {

  private final Object lock = new Object();
  private DisplayLoginHtmlWriterServlet displayServlet;

  private static final String USER_HTML_FILE_NAME = "user.html";

  public SelectUserHtmlWriterServlet(SessionTracking sessionTracking) {
    super(USER_HTML_FILE_NAME, sessionTracking);
  }

  public void setDisplayServlet(DisplayLoginHtmlWriterServlet displayServlet) {
    this.displayServlet = displayServlet;
  }



  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

    synchronized (lock) {
      try {
        isServletSet(displayServlet, "displayServlet");

        Session session = getSession(req, resp, getClass());
        Person caregiver = (Person) session.getAttribute(LOGGED_CAREGIVER);

        if (caregiver == null) {
          debugSessions(session.getId(), "if(caregiver is null) the servlet doGet/doPost method", getClass());
          displayServlet.doGet(req, resp);
          return;
        }

        String cancel = req.getParameter(CANCEL);
        if (cancel != null && TRUE.equalsIgnoreCase(cancel)) {
          debugSessions(session.getId(), "cancel the servlet doGet/doPost method", getClass());
          displayServlet.doGet(req, resp);
          return;
        }

        handleResponse(req, resp, caregiver, session);
      } catch (Exception e) {
        Log.error(e.fillInStackTrace(), "Unexpected Error occurred", getClass());
        sendErrorResponse(req, resp, e);
      }
    }
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    doGet(req, resp);
  }


  private void handleResponse(HttpServletRequest req, HttpServletResponse resp,
                              Person caregiver, Session session) throws IOException {

    PersistentService persistentService = getPersistentService();
    PatientLinksDao patientLinksDao = persistentService.getPatientLinksDao();
    List<Person> patients =patientLinksDao.findCaregiverPatients(caregiver);
    if (patients != null && !patients.isEmpty()) {
      ScriptForm scriptForm = new UserSelectScriptForm(patients);
      sendResponse(req, resp, scriptForm);
    } else {
      throw new MedicationManagerReviewException("Missing patients for the following caregiver: " + caregiver.getName());
    }

  }


}
