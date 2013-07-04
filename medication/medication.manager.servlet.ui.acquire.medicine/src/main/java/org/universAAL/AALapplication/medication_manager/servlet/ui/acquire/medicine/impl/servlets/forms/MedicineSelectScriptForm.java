package org.universAAL.AALapplication.medication_manager.servlet.ui.acquire.medicine.impl.servlets.forms;

import org.universAAL.AALapplication.medication_manager.persistence.layer.PersistentService;
import org.universAAL.AALapplication.medication_manager.persistence.layer.dao.TreatmentDao;
import org.universAAL.AALapplication.medication_manager.persistence.layer.entities.Medicine;
import org.universAAL.AALapplication.medication_manager.persistence.layer.entities.Person;
import org.universAAL.AALapplication.medication_manager.persistence.layer.entities.Treatment;
import org.universAAL.AALapplication.medication_manager.servlet.ui.acquire.medicine.impl.MedicationManagerAcquireMedicineException;
import org.universAAL.AALapplication.medication_manager.servlet.ui.base.export.parser.script.Pair;
import org.universAAL.AALapplication.medication_manager.servlet.ui.base.export.parser.script.forms.ScriptForm;

import java.util.HashSet;
import java.util.Set;

/**
 * @author George Fournadjiev
 */
public final class MedicineSelectScriptForm extends ScriptForm {

  public static final String UNIT = "UNIT";
  private final Person patient;
  private final PersistentService persistentService;

  private static final String MEDICINES_SELECT_FUNCTION_CALL_TEXT = "medicines.push";

  public MedicineSelectScriptForm(Person patient, PersistentService persistentService) {
    super(MEDICINES_SELECT_FUNCTION_CALL_TEXT);

    this.patient = patient;
    this.persistentService = persistentService;
  }

  @Override
  public void setSingleJavascriptObjects() {
    // nothing to do here
  }

  @Override
  public void process() {

    TreatmentDao treatmentDao = persistentService.getTreatmentDao();
    Set<Treatment> treatments = treatmentDao.findTreatments(patient);
    if (treatments.isEmpty()) {
      throw new MedicationManagerAcquireMedicineException("Missing active or pending " +
          "treatments for the patient: " + patient.getName());
    }
    Set<Medicine> medicines = getMedicines(treatments);
    for (Medicine med : medicines) {
      String medId = String.valueOf(med.getId());
      Pair<String> id = new Pair<String>(ID, medId);
      Pair<String> name = new Pair<String>(NAME, med.getMedicineName());
      Pair<String> unit = new Pair<String>(UNIT.toLowerCase(), med.getUnitClass().getType().toLowerCase());
      addRow(id, name, unit);
    }

  }

  private Set<Medicine> getMedicines(Set<Treatment> treatments) {
    Set<Medicine> medicines = new HashSet<Medicine>();

    for (Treatment treatment : treatments) {
      Medicine med = treatment.getMedicine();
      medicines.add(med);
    }

    return medicines;
  }

}
