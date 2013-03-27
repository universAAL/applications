package org.universAAL.AALapplication.medication_manager.servlet.ui.impl.parser.script.forms;

import org.universAAL.AALapplication.medication_manager.persistence.layer.PersistentService;
import org.universAAL.AALapplication.medication_manager.persistence.layer.dto.IntakeDTO;
import org.universAAL.AALapplication.medication_manager.servlet.ui.impl.servlets.helpers.MedicineView;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author George Fournadjiev
 */
public final class NewMedicineScriptForm extends ScriptForm {

  public static final String PREFIX = "medicineObj.";
  public static final String SUFFIX = "\';";
  public static final String ID = "id";
  public static final String PRESCRIPTION_ID = "prescriptionId";
  private final PersistentService persistentService;
  private final MedicineView medicineView;
  private final int prescriptionId;

  private static final String NEW_PRESCRIPTION_FUNCTION_CALL_TEXT = "prescriptionObj.medicines.push";


  public NewMedicineScriptForm(PersistentService persistentService, MedicineView medicineView, int prescriptionId) {
    super(NEW_PRESCRIPTION_FUNCTION_CALL_TEXT);

    this.persistentService = persistentService;
    this.medicineView = medicineView;
    this.prescriptionId = prescriptionId;
    setSingleJavascriptObjects();
  }

  @Override
  public void setSingleJavascriptObjects() {

    List<String> singleObjects = new ArrayList<String>();

    String medId = createSingleObjectText(ID, medicineView.getMedicineId());
    singleObjects.add(medId);

    String prescrId = createSingleObjectText(PRESCRIPTION_ID, prescriptionId);
    singleObjects.add(prescrId);

    String[] objects = new String[singleObjects.size()];
    this.singleJavascriptObjects = singleObjects.toArray(objects);
  }

  private String createSingleObjectText(String field, Object value) {

    return PREFIX + field + " = \'"  + value + SUFFIX;
  }

  /*
  medicineObj.id='med1';
  	medicineObj.prescriptionId='p1';

  		isNew = false;//add ths if edit medicine
  	medicineObj.name='Medicine name 1 that is too long';
  	medicineObj.description='We recommend against using this property; please try to use feature detection instead (see jQuery.support). jQuery.browser may be moved to a plugin in a future release of jQuery.';
  	medicineObj.side_effects='sleeping problems';
  	medicineObj.incompliances='alcohol';
  	medicineObj.days=7;
  	medicineObj.dose=1;
  	medicineObj.unit='drops';
  	medicineObj.meal_relation='before';
    medicineObj.hours=[1,13, 22, 24];
   */

  @Override
  public void process() {

   //nothing we can do here
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


}
