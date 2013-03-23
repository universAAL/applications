package org.universAAL.AALapplication.medication_manager.servlet.ui.impl.parser.script.forms;

import org.universAAL.AALapplication.medication_manager.persistence.layer.PersistentService;
import org.universAAL.AALapplication.medication_manager.servlet.ui.impl.parser.script.JavaScriptObjectCreator;
import org.universAAL.AALapplication.medication_manager.servlet.ui.impl.parser.script.Pair;

/**
 * @author George Fournadjiev
 */
public final class ListPrescriptionsScriptForm extends ScriptForm {

  private final PersistentService persistentService;

  private static final String LIST_PRESCRIPTIONS_FUNCTION_CALL_TEXT = "userObj.prescriptions.push";
  private static final String DATE = "date";
  private static final String NOTES = "notes";
  private static final String MEDICINE = "medicine";
  private static final String NAME = "name";
  private static final String HOW = "how";

  public ListPrescriptionsScriptForm(PersistentService persistentService) {
    super(LIST_PRESCRIPTIONS_FUNCTION_CALL_TEXT);

    this.persistentService = persistentService;
    setSingleJavascriptObjects();
  }

  @Override
  public void setSingleJavascriptObjects() {
    singleJavascriptObjects = new String[]{"\n\t userObj.name = \'Penka Peneva\';"};
  }

  @Override
  public void process() {
    Pair<String> id = new Pair<String>(DATE, "2013-01-12");
    Pair<String> name = new Pair<String>(NOTES, "1. Sleeping problems");
    String medicineValue = createMedicineObject("Aspirin", "5 days 3 pills");
    Pair<String> medicine = new Pair<String>(MEDICINE, medicineValue, true);

    addRow(id, name, medicine);

    Pair<String> id1 = new Pair<String>(DATE, "2012-12-15");
    Pair<String> name1 = new Pair<String>(NOTES, "2. Sleeping problems");
    String medicineValue1 = createMedicineObject("Viteral", "5 days 1 pill");
    Pair<String> medicine1 = new Pair<String>(MEDICINE, medicineValue1, true);
    addRow(id1, name1, medicine1);
  }

  private String createMedicineObject(String name, String how) {

    JavaScriptObjectCreator creator = new JavaScriptObjectCreator();

    Pair<String> pairName = new Pair<String>(NAME, name);
    creator.addPair(pairName);

    Pair<String> pairHow = new Pair<String>(HOW, how);
    creator.addPair(pairHow);

    return creator.createJavascriptObject();

  }
}
