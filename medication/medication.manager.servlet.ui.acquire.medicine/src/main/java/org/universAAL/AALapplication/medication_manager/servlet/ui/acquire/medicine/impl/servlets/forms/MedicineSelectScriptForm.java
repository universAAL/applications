package org.universAAL.AALapplication.medication_manager.servlet.ui.acquire.medicine.impl.servlets.forms;

import org.universAAL.AALapplication.medication_manager.persistence.layer.PersistentService;
import org.universAAL.AALapplication.medication_manager.persistence.layer.dao.TreatmentDao;
import org.universAAL.AALapplication.medication_manager.persistence.layer.entities.Medicine;
import org.universAAL.AALapplication.medication_manager.persistence.layer.entities.Person;
import org.universAAL.AALapplication.medication_manager.persistence.layer.entities.Treatment;
import org.universAAL.AALapplication.medication_manager.servlet.ui.base.export.parser.script.Pair;
import org.universAAL.AALapplication.medication_manager.servlet.ui.base.export.parser.script.forms.ScriptForm;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author George Fournadjiev
 */
public final class MedicineSelectScriptForm extends ScriptForm {

  private final List<Person> patients;
  private final PersistentService persistentService;

  private static final String MEDICINES_SELECT_FUNCTION_CALL_TEXT = "medicines.push";

  public MedicineSelectScriptForm(List<Person> patients, PersistentService persistentService) {
    super(MEDICINES_SELECT_FUNCTION_CALL_TEXT);

    this.patients = patients;
    this.persistentService = persistentService;
  }

  @Override
  public void setSingleJavascriptObjects() {
    // nothing to do here
  }

  @Override
  public void process() {

    TreatmentDao treatmentDao = persistentService.getTreatmentDao();
    Set<Treatment> treatments = treatmentDao.findTreatments(patients);
    Set<Medicine> medicines = getMedicines(treatments);
    for (Medicine med : medicines) {
      String medId = String.valueOf(med.getId());
      Pair<String> id = new Pair<String>(ID, medId);
      Pair<String> name = new Pair<String>(NAME, med.getMedicineName());
      addRow(id, name);
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
