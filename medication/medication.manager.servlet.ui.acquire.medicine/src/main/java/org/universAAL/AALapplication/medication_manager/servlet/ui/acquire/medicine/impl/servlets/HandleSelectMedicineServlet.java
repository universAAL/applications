package org.universAAL.AALapplication.medication_manager.servlet.ui.acquire.medicine.impl.servlets;

import org.universAAL.AALapplication.medication_manager.persistence.layer.PersistentService;
import org.universAAL.AALapplication.medication_manager.persistence.layer.dao.ComplexDao;
import org.universAAL.AALapplication.medication_manager.persistence.layer.dao.MedicineDao;
import org.universAAL.AALapplication.medication_manager.persistence.layer.entities.Medicine;
import org.universAAL.AALapplication.medication_manager.persistence.layer.entities.Person;
import org.universAAL.AALapplication.medication_manager.persistence.layer.entities.UnitClass;
import org.universAAL.AALapplication.medication_manager.servlet.ui.acquire.medicine.impl.Activator;
import org.universAAL.AALapplication.medication_manager.servlet.ui.acquire.medicine.impl.Log;
import org.universAAL.AALapplication.medication_manager.servlet.ui.acquire.medicine.impl.MedicationManagerAcquireMedicineException;
import org.universAAL.AALapplication.medication_manager.servlet.ui.base.export.helpers.Session;
import org.universAAL.AALapplication.medication_manager.servlet.ui.base.export.helpers.SessionTracking;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.universAAL.AALapplication.medication_manager.servlet.ui.acquire.medicine.impl.Util.*;
import static org.universAAL.AALapplication.medication_manager.servlet.ui.base.export.helpers.ServletUtil.*;


/**
 * @author George Fournadjiev
 */
public final class HandleSelectMedicineServlet extends BaseServlet {


  public static final String PROCESS_MEDICINE = "process_medicine";
  public static final String MEDICINE = "medicine";
  public static final String QUANTITY = "quantity";
  private final Object lock = new Object();
  private SelectUserHtmlWriterServlet selectUserHtmlWriterServlet;
  private DisplayLoginHtmlWriterServlet displayLogin;
  private DisplaySuccessfulPageWriterServlet successfulPageWriterServlet;

  public HandleSelectMedicineServlet(SessionTracking sessionTracking) {
    super(sessionTracking);
  }

  public void setSelectUserHtmlWriterServlet(SelectUserHtmlWriterServlet selectUserHtmlWriterServlet) {
    this.selectUserHtmlWriterServlet = selectUserHtmlWriterServlet;
  }

  public void setDisplayLogin(DisplayLoginHtmlWriterServlet displayLogin) {
    this.displayLogin = displayLogin;
  }

  public void setSuccessfulPageWriterServlet(DisplaySuccessfulPageWriterServlet successfulPageWriterServlet) {
    this.successfulPageWriterServlet = successfulPageWriterServlet;
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    doGet(req, resp);
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    synchronized (lock) {
      try {
        isServletSet(selectUserHtmlWriterServlet, "selectUserHtmlWriterServlet");
        isServletSet(displayLogin, "displayLogin");
        isServletSet(successfulPageWriterServlet, "successfulPageWriterServlet");

        Session session = getSession(req, resp, getClass());
        debugSessions(session.getId(), "the servlet doGet/doPost method", getClass());
        Person caregiver = (Person) session.getAttribute(LOGGED_CAREGIVER);

        String back = req.getParameter(BACK);

        if (back != null && back.equalsIgnoreCase(TRUE)) {
          debugSessions(session.getId(), "back button (invalidate) the servlet doGet/doPost method", getClass());
          selectUserHtmlWriterServlet.doGet(req, resp);
          return;
        }

        if (caregiver != null) {
          debugSessions(session.getId(), "End of the servlet doGet/doPost method (caregiver is not null", getClass());
          String message = saveInMedicineInventory(req, session);
          session.setAttribute(MSG, message);
          successfulPageWriterServlet.doGet(req, resp);
          return;
        } else {
          debugSessions(session.getId(), "End of the servlet doGet/doPost method (caregiver is null)", getClass());
          invalidateSession(req, resp);
          displayLogin.doGet(req, resp);
        }
      } catch (Exception e) {
        Log.error(e.fillInStackTrace(), "Unexpected Error occurred", getClass());
        sendErrorResponse(req, resp, e);
      }

    }


  }

  private String saveInMedicineInventory(HttpServletRequest req, Session session) {

    String formName = req.getParameter(PROCESS_MEDICINE);
    if (formName == null || formName.trim().isEmpty()) {
      throw new MedicationManagerAcquireMedicineException("Missing expected form name parameter : " + PROCESS_MEDICINE);
    }

    Person patient = (Person) session.getAttribute(PATIENT);

    if (patient == null) {
      throw new MedicationManagerAcquireMedicineException("Missing expected patient session attribute : " + PATIENT);
    }

    String medId = req.getParameter(MEDICINE);

    if (medId == null) {
      throw new MedicationManagerAcquireMedicineException("Missing expected medicine parameter : " + MEDICINE);
    }

    String quantityText = req.getParameter(QUANTITY);

    if (quantityText == null) {
      throw new MedicationManagerAcquireMedicineException("Missing expected medicine parameter : " + QUANTITY);
    }

    int quantity = getIntFromString(quantityText, "quantityText");

    PersistentService persistentService = Activator.getPersistentService();
    MedicineDao medicineDao = persistentService.getMedicineDao();
    int id = getIntFromString(medId, "medId");
    Medicine medicine = medicineDao.getById(id);

    ComplexDao complexDao = persistentService.getComplexDao();

    complexDao.saveAcquiredMedicine(patient, medicine, quantity);

    StringBuffer sb = new StringBuffer();

    sb.append(quantity);
    sb.append(' ');
    String units = getUnitsString(medicine);
    sb.append(units);
    sb.append(' ');
    sb.append("has been added to the medicine inventory ");
    sb.append("(medicine : ");
    sb.append(medicine.getMedicineName());
    sb.append(", patient : ");
    sb.append(patient.getName());
    sb.append(").");

    return sb.toString();
  }

  private String getUnitsString(Medicine medicine) {
    UnitClass unitClass = medicine.getUnitClass();
    if (unitClass == UnitClass.PILLS) {
      return "pills";
    }
    return unitClass.getType().toLowerCase();
  }

}
