package org.universAAL.AALapplication.medication_manager.servlet.ui.impl.parser.script.forms;

import org.universAAL.AALapplication.medication_manager.persistence.layer.PersistentService;
import org.universAAL.AALapplication.medication_manager.persistence.layer.dto.IntakeDTO;
import org.universAAL.AALapplication.medication_manager.persistence.layer.dto.TimeDTO;
import org.universAAL.AALapplication.medication_manager.servlet.ui.impl.parser.script.Pair;
import org.universAAL.AALapplication.medication_manager.servlet.ui.impl.servlets.helpers.MedicineView;
import org.universAAL.AALapplication.medication_manager.servlet.ui.impl.servlets.helpers.NewPrescriptionView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.universAAL.AALapplication.medication_manager.servlet.ui.impl.Util.*;

/**
 * @author George Fournadjiev
 */
public final class NewPrescriptionScriptForm extends ScriptForm {

  private static final String DESCRIPTION = "description";
  private static final String SIDE_EFFECTS = "side_effects";
  private static final String INCOMPLIANCES = "incompliances";
  private static final String DAYS = "days";
  private static final String HOURS = "hours";
  private final PersistentService persistentService;
  private final NewPrescriptionView newPrescriptionView;

  private static final String NEW_PRESCRIPTION_FUNCTION_CALL_TEXT = "prescriptionObj.medicines.push";


  public NewPrescriptionScriptForm(PersistentService persistentService, NewPrescriptionView newPrescriptionView) {
    super(NEW_PRESCRIPTION_FUNCTION_CALL_TEXT);

    this.persistentService = persistentService;
    this.newPrescriptionView = newPrescriptionView;
    setSingleJavascriptObjects();
  }

  @Override
  public void setSingleJavascriptObjects() {

    List<String> singleObjects = new ArrayList<String>();

    String prescriptionId = "prescriptionObj.id = \'" + newPrescriptionView.getPrescriptionId() + "\';";
    singleObjects.add(prescriptionId);
//    newPrescriptionView.setStartDate("2013-03-27");
    String date = newPrescriptionView.getStartDate();
    if (date != null) {
      date = "prescriptionObj.date = \'" + date + "\';";
      singleObjects.add(date);
    }
//    newPrescriptionView.setNotes("notes zswsws");
    String notes = newPrescriptionView.getNotes();
    if (notes != null) {
      notes = "prescriptionObj.notes = \'" + notes + "\';";
      singleObjects.add(notes);
    }

    String[] res = new String[singleObjects.size()];
    res = singleObjects.toArray(res);

    this.singleJavascriptObjects = res;
  }

  @Override
  public void process() {

    Set<MedicineView> medicineViewSet = newPrescriptionView.getMedicineViewSet();

//    fillMedicineViewSetWithTempData(medicineViewSet);

    if (medicineViewSet.isEmpty()) {
      return;
    }

    for (MedicineView medicineView : medicineViewSet) {

      int medicineId = medicineView.getMedicineId();
      Pair<String> id = new Pair<String>(ID, String.valueOf(medicineId));
      Pair<String> name = new Pair<String>(NAME, medicineView.getName());
      String descr = medicineView.getDescription();
      Pair<String> description = getStringPair(DESCRIPTION, descr);
      String se = medicineView.getSideeffects();
      Pair<String> sideeffects = getStringPair(SIDE_EFFECTS, se);
      String inc = medicineView.getIncompliances();
      Pair<String> incompliances = getStringPair(INCOMPLIANCES, inc);
      Pair<Integer> days = new Pair<Integer>(DAYS, medicineView.getDays(), true);
      Set<IntakeDTO> intakeDTOSet = medicineView.getIntakeDTOSet();
      String hoursText = createHoursText(intakeDTOSet);
      Pair<String> hours = new Pair<String>(HOURS, hoursText, true);

      addRow(id, name, description, sideeffects, incompliances, days, hours);
    }

  }

  private Pair<String> getStringPair(String key, String value) {
    Pair<String> stringPair;
    if (value != null) {
      stringPair = new Pair<String>(key, value);
    } else {
      stringPair = EMPTY_PAIR;
    }
    return stringPair;
  }

  private String createHoursText(Set<IntakeDTO> intakeDTOs) {
    StringBuffer sb = new StringBuffer();
    sb.append('[');

    int count = 0;
    int size = intakeDTOs.size();
    for (IntakeDTO dto : intakeDTOs) {
      int hour = dto.getTime().getHour();
      sb.append(hour);
      count++;
      if (count < size) {
        sb.append(", ");
      }
    }

    sb.append("]");

    return sb.toString();
  }

  private void fillMedicineViewSetWithTempData(Set<MedicineView> medicineViewSet) {

    MedicineView medicineView1 = new MedicineView(1);
    medicineView1.setName("Benalgin");
    medicineView1.setDescription("Benalgin Description");
    medicineView1.setSideeffects("Benalgin sideeffects");

    medicineView1.setDays(8);

    Set<IntakeDTO> intakeDTOs1 = new HashSet<IntakeDTO>();

    IntakeDTO in11 = new IntakeDTO(TimeDTO.createTimeDTO("8:00"), IntakeDTO.Unit.PILL, 2);
    IntakeDTO in12 = new IntakeDTO(TimeDTO.createTimeDTO("16:00"), IntakeDTO.Unit.PILL, 1);

    intakeDTOs1.add(in11);
    intakeDTOs1.add(in12);

    medicineView1.setIntakeDTOSet(intakeDTOs1);

    medicineViewSet.add(medicineView1);

    MedicineView medicineView2 = new MedicineView(2);
    medicineView2.setName("Analgin");
    medicineView2.setDescription("Analgin Description");
    medicineView2.setSideeffects("Analgin sideeffects");

    medicineView2.setDays(10);

    Set<IntakeDTO> intakeDTOs2 = new HashSet<IntakeDTO>();

    IntakeDTO in21 = new IntakeDTO(TimeDTO.createTimeDTO("9:00"), IntakeDTO.Unit.PILL, 1);
    IntakeDTO in22 = new IntakeDTO(TimeDTO.createTimeDTO("13:00"), IntakeDTO.Unit.PILL, 2);
    IntakeDTO in23 = new IntakeDTO(TimeDTO.createTimeDTO("21:00"), IntakeDTO.Unit.PILL, 3);

    intakeDTOs2.add(in21);
    intakeDTOs2.add(in22);
    intakeDTOs2.add(in23);

    medicineView2.setIntakeDTOSet(intakeDTOs2);

    medicineViewSet.add(medicineView2);

  }


}
