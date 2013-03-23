package org.universAAL.AALapplication.medication_manager.servlet.ui.impl.parser.script.forms;

import org.universAAL.AALapplication.medication_manager.persistence.layer.PersistentService;
import org.universAAL.AALapplication.medication_manager.servlet.ui.impl.parser.script.Pair;

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

  private static final String NEW_PRESCRIPTION_FUNCTION_CALL_TEXT = "prescriptionObj.medicines.push";


  public NewPrescriptionScriptForm(PersistentService persistentService) {
    super(NEW_PRESCRIPTION_FUNCTION_CALL_TEXT);

    this.persistentService = persistentService;
    setSingleJavascriptObjects();
  }

  @Override
  public void setSingleJavascriptObjects() {

    //    Add date and notes if are post after save medicine

    this.singleJavascriptObjects = new String[]{
        "prescriptionObj.date = \'2013-01-12\';",
        "prescriptionObj.notes = \'Pain problems\';"
    };
  }

  @Override
  public void process() {

    Pair<String> id = new Pair<String>(ID, "1");
    Pair<String> name = new Pair<String>(NAME, "Benalgin");
    Pair<String> description = new Pair<String>(DESCRIPTION, "Benalgin Description");
    Pair<String> sideeffects = new Pair<String>(SIDE_EFFECTS, "Benalgin sideeffects");
    Pair<String> incompliances = new Pair<String>(INCOMPLIANCES, "Benalgin incompliances");
    Pair<Integer> days = new Pair<Integer>(DAYS, 5, true);
    Pair<String> hours = new Pair<String>(HOURS, "[8, 14, 21]", true);

    addRow(id, name, description, sideeffects, incompliances, days, hours);

    Pair<String> id1 = new Pair<String>(ID, "2");
    Pair<String> name1 = new Pair<String>(NAME, "Analgin");
    Pair<String> description1 = new Pair<String>(DESCRIPTION, "Analgin Description");
    Pair<String> sideeffects1 = new Pair<String>(SIDE_EFFECTS, "Analgin sideeffects");
    Pair<String> incompliances1 = new Pair<String>(INCOMPLIANCES, "Analgin incompliances");
    Pair<Integer> days1 = new Pair<Integer>(DAYS, 9, true);
    Pair<String> hours1 = new Pair<String>(HOURS, "[9, 13, 20]", true);

    addRow(id1, name1, description1, sideeffects1, incompliances1, days1, hours1);
  }


}
