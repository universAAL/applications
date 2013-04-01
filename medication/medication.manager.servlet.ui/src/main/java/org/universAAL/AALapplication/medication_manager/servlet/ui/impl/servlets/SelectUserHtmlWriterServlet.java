package org.universAAL.AALapplication.medication_manager.servlet.ui.impl.servlets;

import org.universAAL.AALapplication.medication_manager.persistence.layer.PersistentService;
import org.universAAL.AALapplication.medication_manager.persistence.layer.dao.DoctorPatientDao;
import org.universAAL.AALapplication.medication_manager.persistence.layer.entities.Person;
import org.universAAL.AALapplication.medication_manager.servlet.ui.impl.Log;
import org.universAAL.AALapplication.medication_manager.servlet.ui.impl.MedicationManagerServletUIException;
import org.universAAL.AALapplication.medication_manager.servlet.ui.impl.parser.script.forms.ScriptForm;
import org.universAAL.AALapplication.medication_manager.servlet.ui.impl.parser.script.forms.UserSelectScriptForm;
import org.universAAL.AALapplication.medication_manager.servlet.ui.impl.servlets.helpers.Session;
import org.universAAL.AALapplication.medication_manager.servlet.ui.impl.servlets.helpers.SessionTracking;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static org.universAAL.AALapplication.medication_manager.servlet.ui.impl.Activator.*;
import static org.universAAL.AALapplication.medication_manager.servlet.ui.impl.Util.*;

/**
 * @author George Fournadjiev
 */
public final class SelectUserHtmlWriterServlet extends BaseHtmlWriterServlet {

  private final Object lock = new Object();
  private DisplayLoginHtmlWriterServlet displayServlet;
  private ListPrescriptionsHtmlWriterServlet listPrescriptionsHtmlWriterServlet;

  private static final String USER_HTML_FILE_NAME = "user.html";

  public SelectUserHtmlWriterServlet(SessionTracking sessionTracking) {
    super(USER_HTML_FILE_NAME, sessionTracking);
  }

  public void setDisplayServlet(DisplayLoginHtmlWriterServlet displayServlet) {
    this.displayServlet = displayServlet;
  }

  public void setListPrescriptionsHtmlWriterServlet(ListPrescriptionsHtmlWriterServlet
                                                        listPrescriptionsHtmlWriterServlet) {

    this.listPrescriptionsHtmlWriterServlet = listPrescriptionsHtmlWriterServlet;
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

    synchronized (lock) {
      try {
        isServletSet(displayServlet, "displayServlet");
        isServletSet(listPrescriptionsHtmlWriterServlet, "listPrescriptionsHtmlWriterServlet");

        Session session = getSession(req, resp, getClass());
        Person doctor = (Person) session.getAttribute(LOGGED_DOCTOR);

        if (doctor == null) {
          debugSessions(session.getId(), "if(Doctor is null) the servlet doGet/doPost method", getClass());
          displayServlet.doGet(req, resp);
          return;
        }

        handleResponse(req, resp, doctor);
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


  private void handleResponse(HttpServletRequest req, HttpServletResponse resp, Person doctor) throws IOException {
    try {
      PersistentService persistentService = getPersistentService();
      DoctorPatientDao doctorPatientDao = persistentService.getDoctorPatientDao();
      List<Person> patients = doctorPatientDao.findDoctorPatients(doctor);
      if (patients != null && patients.size() > 1) {
        ScriptForm scriptForm = new UserSelectScriptForm(patients);
        sendResponse(req, resp, scriptForm);
      } else if (patients != null && patients.size() == 1) {
        listPrescriptionsHtmlWriterServlet.doGet(req, resp);
      } else {
        throw new MedicationManagerServletUIException("Missing patients for the doctor : " + doctor);
      }
    } catch (Exception e) {
      sendErrorResponse(req, resp, e);
    }
  }


}
