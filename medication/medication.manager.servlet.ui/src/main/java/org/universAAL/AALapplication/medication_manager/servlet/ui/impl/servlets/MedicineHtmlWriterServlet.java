package org.universAAL.AALapplication.medication_manager.servlet.ui.impl.servlets;

import org.universAAL.AALapplication.medication_manager.persistence.layer.PersistentService;
import org.universAAL.AALapplication.medication_manager.persistence.layer.entities.Person;
import org.universAAL.AALapplication.medication_manager.servlet.ui.impl.MedicationManagerServletUIException;
import org.universAAL.AALapplication.medication_manager.servlet.ui.impl.parser.script.forms.NewMedicineScriptForm;
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

  public static final String PRESCRIPTION_ID = "prescriptionId";
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
        newPrescriptionHtmlWriterServlet.doGet(req, resp);
        return;
      }

      setDateAndNotes(req, newPrescriptionView);
      MedicineView medicineView = getMedicineView(newPrescriptionView, req);

      handleResponse(resp, medicineView, newPrescriptionView.getPrescriptionId());
    }
  }

  private MedicineView getMedicineView(NewPrescriptionView newPrescriptionView, HttpServletRequest req) {

    String medicineId = req.getParameter("id");

    MedicineView medicineView;
    if (medicineId != null) {
      medicineView = newPrescriptionView.getMedicineView(medicineId);
      medicineView.setNew(false);
    } else {
      medicineView = new MedicineView(generateId());
      medicineView.setNew(true);
      newPrescriptionView.addMedicineView(medicineView);
    }

    String param = req.getParameter(PRESCRIPTION_ID);
    int prescriptionId = getIntFromString(param, "param");

    int id = newPrescriptionView.getPrescriptionId();
    if (prescriptionId != id) {
      throw new MedicationManagerServletUIException("PrescriptionId parameter: " +
          prescriptionId + " is different from the NewPrescriptionView kept in the session: " + id);
    }

    return medicineView;
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    doGet(req, resp);
  }


  private void handleResponse(HttpServletResponse resp, MedicineView medicineView, int prescriptionId) throws IOException {
    try {
      PersistentService persistentService = getPersistentService();
      ScriptForm scriptForm = new NewMedicineScriptForm(persistentService, medicineView, prescriptionId);
      sendResponse(resp, scriptForm);
    } catch (Exception e) {
      sendErrorResponse(resp, e);
    }
  }


}
