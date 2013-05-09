package org.universAAL.AALapplication.medication_manager.servlet.ui.impl.servlets.forms;

import org.universAAL.AALapplication.medication_manager.persistence.layer.PersistentService;
import org.universAAL.AALapplication.medication_manager.persistence.layer.dto.IntakeDTO;
import org.universAAL.AALapplication.medication_manager.persistence.layer.dto.TimeDTO;
import org.universAAL.AALapplication.medication_manager.servlet.ui.base.export.parser.script.Pair;
import org.universAAL.AALapplication.medication_manager.servlet.ui.base.export.parser.script.forms.ScriptForm;
import org.universAAL.AALapplication.medication_manager.servlet.ui.impl.servlets.MedicineView;
import org.universAAL.AALapplication.medication_manager.servlet.ui.impl.servlets.NewPrescriptionView;

import java.util.ArrayList;
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
    String date = newPrescriptionView.getStartDate();
    if (date != null) {
      date = "prescriptionObj.date = \'" + date + "\';";
      singleObjects.add(date);
    }
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
      int hour = getHour(dto);
      sb.append(hour);
      count++;
      if (count < size) {
        sb.append(", ");
      }
    }

    sb.append("]");

    return sb.toString();
  }

  private int getHour(IntakeDTO dto) {
    TimeDTO time = dto.getTime();
    int hour = time.getHour();
    if (hour == 23 && time.getMinutes() == 59) {
      hour = 24;
    }
    return hour;
  }


}
