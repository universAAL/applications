package org.universAAL.AALapplication.medication_manager.servlet.ui.impl.servlets;

import org.universAAL.AALapplication.medication_manager.persistence.layer.PersistentService;
import org.universAAL.AALapplication.medication_manager.persistence.layer.entities.Person;
import org.universAAL.AALapplication.medication_manager.servlet.ui.impl.parser.script.forms.NewPrescriptionScriptForm;
import org.universAAL.AALapplication.medication_manager.servlet.ui.impl.parser.script.forms.ScriptForm;
import org.universAAL.AALapplication.medication_manager.servlet.ui.impl.servlets.helpers.MedicineView;
import org.universAAL.AALapplication.medication_manager.servlet.ui.impl.servlets.helpers.NewPrescriptionView;
import org.universAAL.AALapplication.medication_manager.servlet.ui.impl.servlets.helpers.Session;
import org.universAAL.AALapplication.medication_manager.servlet.ui.impl.servlets.helpers.SessionTracking;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

import static org.universAAL.AALapplication.medication_manager.servlet.ui.impl.Activator.*;
import static org.universAAL.AALapplication.medication_manager.servlet.ui.impl.DatabaseSimulation.*;
import static org.universAAL.AALapplication.medication_manager.servlet.ui.impl.Util.*;

/**
 * @author George Fournadjiev
 */
public final class NewPrescriptionHtmlWriterServlet extends BaseHtmlWriterServlet {

  private final Object lock = new Object();
  private DisplayLoginHtmlWriterServlet displayLoginHtmlWriterServlet;
  private SelectUserHtmlWriterServlet selectUserHtmlWriterServlet;

  private static final String NEW_PRESCRIPTION_HTML_FILE_NAME = "new_prescription.html";

  public NewPrescriptionHtmlWriterServlet(SessionTracking sessionTracking) {
    super(NEW_PRESCRIPTION_HTML_FILE_NAME, sessionTracking);
  }

  public void setDisplayLoginHtmlWriterServlet(DisplayLoginHtmlWriterServlet displayLoginHtmlWriterServlet) {
    this.displayLoginHtmlWriterServlet = displayLoginHtmlWriterServlet;
  }

  public void setSelectUserHtmlWriterServlet(SelectUserHtmlWriterServlet selectUserHtmlWriterServlet) {
    this.selectUserHtmlWriterServlet = selectUserHtmlWriterServlet;
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    synchronized (lock) {
      isServletSet(displayLoginHtmlWriterServlet, "displayLoginHtmlWriterServlet");
      isServletSet(selectUserHtmlWriterServlet, "selectUserHtmlWriterServlet");

      Session session = getSession(req, resp, getClass());
      Person doctor = (Person) session.getAttribute(LOGGED_DOCTOR);

      if (doctor == null) {
        debugSessions(session.getId(), "if(doctor is null) the servlet doGet/doPost method", getClass());
        displayLoginHtmlWriterServlet.doGet(req, resp);
        return;
      }

      Person patient = (Person) session.getAttribute(PATIENT);

      if (patient == null) {
        debugSessions(session.getId(), "if(patient is null) the servlet doGet/doPost method", getClass());
        selectUserHtmlWriterServlet.doGet(req, resp);
        return;
      }

      NewPrescriptionView newPrescriptionView = getNewPrescriptionView(doctor, patient, session);

      debugSessions(session.getId(), "End of the servlet doGet/doPost method", getClass());

      handleResponse(resp, newPrescriptionView);
    }
  }

  private NewPrescriptionView getNewPrescriptionView(Person doctor, Person patient, Session session) {

    NewPrescriptionView newPrescriptionView = (NewPrescriptionView) session.getAttribute(PRESCRIPTION_VIEW);
    if (newPrescriptionView != null) {
      Person per = newPrescriptionView.getPatient();
      if (per.getId() != patient.getId()) {
        newPrescriptionView = null;
      }
    }

    if (newPrescriptionView == null) {
      newPrescriptionView = new NewPrescriptionView(generateId(), doctor, patient);
      session.setAttribute(PRESCRIPTION_VIEW, newPrescriptionView);
    }

    validateNewPrescription(newPrescriptionView);

    return newPrescriptionView;
  }

  private void validateNewPrescription(NewPrescriptionView newPrescriptionView) {
    Set<MedicineView> medViews = newPrescriptionView.getMedicineViewSet();

    for (MedicineView view : medViews) {
      int id = view.getMedicineId();
      if (notValidMedicineView(view)) {
        newPrescriptionView.removeMedicineView(String.valueOf(id));
      }
    }
  }

  private boolean notValidMedicineView(MedicineView view) {
    if (view.getName() == null) {
      return true;
    }

    if (view.getDays() == 0) {
      return true;
    }

    if (view.getDose() == 0) {
      return true;
    }

    if (view.getUnit() == null) {
      return true;
    }

    if (view.getMealRelationDTO() == null) {
      return true;
    }

    if (view.getIntakeDTOSet().isEmpty()) {
      return true;
    }

    return false;

  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    doGet(req, resp);
  }


  private void handleResponse(HttpServletResponse resp, NewPrescriptionView newPrescriptionView) throws IOException {
    try {
      PersistentService persistentService = getPersistentService();
      ScriptForm scriptForm = new NewPrescriptionScriptForm(persistentService, newPrescriptionView);
      sendResponse(resp, scriptForm);
    } catch (Exception e) {
      sendErrorResponse(resp, e);
    }
  }


}
