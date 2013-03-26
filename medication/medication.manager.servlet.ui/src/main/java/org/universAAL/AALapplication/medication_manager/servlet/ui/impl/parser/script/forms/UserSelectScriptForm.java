package org.universAAL.AALapplication.medication_manager.servlet.ui.impl.parser.script.forms;

import org.universAAL.AALapplication.medication_manager.persistence.layer.PersistentService;
import org.universAAL.AALapplication.medication_manager.persistence.layer.entities.Person;
import org.universAAL.AALapplication.medication_manager.servlet.ui.impl.DatabaseSimulation;
import org.universAAL.AALapplication.medication_manager.servlet.ui.impl.parser.script.Pair;

/**
 * @author George Fournadjiev
 */
public final class UserSelectScriptForm extends ScriptForm {

  private final PersistentService persistentService;
  private final Person doctor;

  private static final String USER_SELECT_FUNCTION_CALL_TEXT = "users.push";

  public UserSelectScriptForm(PersistentService persistentService, Person doctor) {
    super(USER_SELECT_FUNCTION_CALL_TEXT);

    this.persistentService = persistentService;
    this.doctor = doctor;
  }

  @Override
  public void setSingleJavascriptObjects() {
    // nothing to do here
  }

  @Override
  public void process() {

    Person[] patients = findPatients();

    for (Person p : patients) {
      String personId = String.valueOf(p.getId());
      Pair<String> id = new Pair<String>(ID, personId);
      Pair<String> name = new Pair<String>(NAME, p.getName());
      addRow(id, name);
    }

  }

  private Person[] findPatients() {
    //TODO real database related code here

    return DatabaseSimulation.PATIENTS;
  }
}
