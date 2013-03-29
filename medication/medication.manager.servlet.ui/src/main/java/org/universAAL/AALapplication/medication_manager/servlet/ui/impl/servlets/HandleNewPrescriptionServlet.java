package org.universAAL.AALapplication.medication_manager.servlet.ui.impl.servlets;

import org.universAAL.AALapplication.medication_manager.persistence.layer.dto.MedicineDTO;
import org.universAAL.AALapplication.medication_manager.persistence.layer.dto.PrescriptionDTO;
import org.universAAL.AALapplication.medication_manager.persistence.layer.entities.Person;
import org.universAAL.AALapplication.medication_manager.servlet.ui.impl.Log;
import org.universAAL.AALapplication.medication_manager.servlet.ui.impl.MedicationManagerServletUIException;
import org.universAAL.AALapplication.medication_manager.servlet.ui.impl.servlets.helpers.MedicineView;
import org.universAAL.AALapplication.medication_manager.servlet.ui.impl.servlets.helpers.NewPrescriptionView;
import org.universAAL.AALapplication.medication_manager.servlet.ui.impl.servlets.helpers.Session;
import org.universAAL.AALapplication.medication_manager.servlet.ui.impl.servlets.helpers.SessionTracking;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static org.universAAL.AALapplication.medication_manager.servlet.ui.impl.DatabaseSimulation.*;
import static org.universAAL.AALapplication.medication_manager.servlet.ui.impl.Util.*;

/**
 * @author George Fournadjiev
 */
public final class HandleNewPrescriptionServlet extends BaseServlet {

  private final Object lock = new Object();
  private DisplayLoginHtmlWriterServlet displayLoginHtmlWriterServlet;
  private SelectUserHtmlWriterServlet selectUserHtmlWriterServlet;
  private NewPrescriptionHtmlWriterServlet newPrescriptionHtmlWriterServlet;
  private ListPrescriptionsHtmlWriterServlet listPrescriptionsHtmlWriterServlet;

  public HandleNewPrescriptionServlet(SessionTracking sessionTracking) {
    super(sessionTracking);
  }

  public void setDisplayLoginHtmlWriterServlet(DisplayLoginHtmlWriterServlet displayLoginHtmlWriterServlet) {
    this.displayLoginHtmlWriterServlet = displayLoginHtmlWriterServlet;
  }

  public void setSelectUserHtmlWriterServlet(SelectUserHtmlWriterServlet selectUserHtmlWriterServlet) {
    this.selectUserHtmlWriterServlet = selectUserHtmlWriterServlet;
  }

  public void setNewPrescriptionHtmlWriterServlet(NewPrescriptionHtmlWriterServlet newPrescriptionHtmlWriterServlet) {
    this.newPrescriptionHtmlWriterServlet = newPrescriptionHtmlWriterServlet;
  }

  public void setListPrescriptionsHtmlWriterServlet(ListPrescriptionsHtmlWriterServlet listPrescriptionsHtmlWriterServlet) {
    this.listPrescriptionsHtmlWriterServlet = listPrescriptionsHtmlWriterServlet;
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    doPost(req, resp);
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    synchronized (lock) {
      isServletSet(displayLoginHtmlWriterServlet, "displayLoginHtmlWriterServlet");
      isServletSet(selectUserHtmlWriterServlet, "selectUserHtmlWriterServlet");
      isServletSet(listPrescriptionsHtmlWriterServlet, "listPrescriptionsHtmlWriterServlet");
      isServletSet(newPrescriptionHtmlWriterServlet, "newPrescriptionHtmlWriterServlet");
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

      NewPrescriptionView newPrescriptionView = (NewPrescriptionView) session.getAttribute(PRESCRIPTION_VIEW);

      if (newPrescriptionView == null) {
        debugSessions(session.getId(), "if(newPrescriptionView is null) the servlet doGet/doPost method", getClass());
        newPrescriptionHtmlWriterServlet.doGet(req, resp);
        return;
      }

      PrescriptionDTO  prescriptionDTO = saveNewPrescription(newPrescriptionView, req, resp);

      if (prescriptionDTO != null) {
        session.removeAttribute(PRESCRIPTION_VIEW);
        addDescriptionDTO(patient, prescriptionDTO);
      }

      debugSessions(session.getId(), "End of the servlet doGet/doPost method", getClass());
      listPrescriptionsHtmlWriterServlet.doPost(req, resp);

    }
  }

  private PrescriptionDTO saveNewPrescription(NewPrescriptionView prescriptionView,
                                   HttpServletRequest req, HttpServletResponse resp) {

    Log.info("Creating PrescriptionDTO object from NewPrescriptionView object: %s", getClass(), prescriptionView);

    Date startDate = getDate(prescriptionView.getStartDate());
    PrescriptionDTO prescriptionDTO = new PrescriptionDTO(
        prescriptionView.getNotes(),
        startDate,
        getMedicineDTOs(prescriptionView.getMedicineViewSet(), startDate),
        prescriptionView.getPhysician(),
        prescriptionView.getPatient()
    );

    int prescriptionId = generateId();
    prescriptionDTO.setPrescriptionId(prescriptionId);

    return prescriptionDTO;
  }

  private Set<MedicineDTO> getMedicineDTOs(Set<MedicineView> medicineViewSet, Date startDate) {
    Set<MedicineDTO> medicineDTOSet = new HashSet<MedicineDTO>();

    if (medicineViewSet.isEmpty()) {
      throw new MedicationManagerServletUIException("The medicineViewSet cannot be empty");
    }

    for (MedicineView medicineView : medicineViewSet) {
      MedicineDTO medicineDTO = createMedicineDTO(medicineView, startDate);
      medicineDTOSet.add(medicineDTO);
    }

    return medicineDTOSet;
  }

  private MedicineDTO createMedicineDTO(MedicineView medicineView, Date startDate) {

    MedicineDTO medicineDTO = new MedicineDTO(
        medicineView.getName(),
        startDate,
        medicineView.getDays(),
        medicineView.getDescription(),
        medicineView.getSideeffects(),
        medicineView.getIncompliances(),
        medicineView.getMealRelationDTO(),
        medicineView.getIntakeDTOSet()
    );
    medicineDTO.setMedicineIdId(generateId());

    return medicineDTO;
  }
}
