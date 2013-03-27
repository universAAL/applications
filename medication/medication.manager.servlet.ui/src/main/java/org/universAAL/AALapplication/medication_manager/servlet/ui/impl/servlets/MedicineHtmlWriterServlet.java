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

import static org.universAAL.AALapplication.medication_manager.servlet.ui.impl.Activator.*;
import static org.universAAL.AALapplication.medication_manager.servlet.ui.impl.DatabaseSimulation.*;
import static org.universAAL.AALapplication.medication_manager.servlet.ui.impl.Util.*;

/**
 * @author George Fournadjiev
 */
public final class MedicineHtmlWriterServlet extends BaseHtmlWriterServlet {

  private final Object lock = new Object();
  private DisplayLoginHtmlWriterServlet displayLoginHtmlWriterServlet;
  private NewPrescriptionHtmlWriterServlet newPrescriptionHtmlWriterServlet;
  private ListPrescriptionsHtmlWriterServlet listPrescriptionsHtmlWriterServlet;

  private static final String MEDICINE_HTML_FILE_NAME = "medicine.html";

  public MedicineHtmlWriterServlet(SessionTracking sessionTracking) {
    super(MEDICINE_HTML_FILE_NAME, sessionTracking);
  }

  public void setDisplayLoginHtmlWriterServlet(DisplayLoginHtmlWriterServlet displayLoginHtmlWriterServlet) {
    this.displayLoginHtmlWriterServlet = displayLoginHtmlWriterServlet;
  }

  public void setNewPrescriptionHtmlWriterServlet(NewPrescriptionHtmlWriterServlet newPrescriptionHtmlWriterServlet) {
    this.newPrescriptionHtmlWriterServlet = newPrescriptionHtmlWriterServlet;
  }

  public void setListPrescriptionsHtmlWriterServlet(ListPrescriptionsHtmlWriterServlet
                                                        listPrescriptionsHtmlWriterServlet) {

    this.listPrescriptionsHtmlWriterServlet = listPrescriptionsHtmlWriterServlet;
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    synchronized (lock) {
      isServletSet(displayLoginHtmlWriterServlet, "displayLoginHtmlWriterServlet");
      isServletSet(newPrescriptionHtmlWriterServlet, "newPrescriptionHtmlWriterServlet");
      isServletSet(listPrescriptionsHtmlWriterServlet, "listPrescriptionsHtmlWriterServlet");

      Session session = getSession(req);
      Person doctor = (Person) session.getAttribute(LOGGED_DOCTOR);

      if (doctor == null) {
        displayLoginHtmlWriterServlet.doGet(req, resp);
        return;
      }


      NewPrescriptionView newPrescriptionView = (NewPrescriptionView) session.getAttribute(PRESCRIPTION_VIEW);

      if (newPrescriptionView == null) {
        listPrescriptionsHtmlWriterServlet.doGet(req, resp);
        return;
      }

      MedicineView medicineView = new MedicineView(generateId());
      medicineView.setName("Auto set for medicine.id=" + medicineView.getMedicineId());

      newPrescriptionView.addMedicineView(medicineView);

      handleResponse(resp, newPrescriptionView);
    }
  }

  private NewPrescriptionView getNewPrescriptionView(Person doctor, Person patient, Session session) {

    NewPrescriptionView newPrescriptionView = (NewPrescriptionView) session.getAttribute(NEW_PRESCRIPTION);

    if (newPrescriptionView == null) {
      newPrescriptionView = new NewPrescriptionView(generateId(), doctor, patient);
      session.setAttribute(NEW_PRESCRIPTION, newPrescriptionView);
    }

    return newPrescriptionView;
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
