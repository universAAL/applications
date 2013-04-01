package org.universAAL.AALapplication.medication_manager.servlet.ui.impl.parser.script.forms;

import org.universAAL.AALapplication.medication_manager.persistence.layer.entities.Person;
import org.universAAL.AALapplication.medication_manager.servlet.ui.impl.parser.script.Pair;

import java.util.List;

/**
 * @author George Fournadjiev
 */
public final class UserSelectScriptForm extends ScriptForm {

  private final List<Person> patients;

  private static final String USER_SELECT_FUNCTION_CALL_TEXT = "users.push";

  public UserSelectScriptForm(List<Person> patients) {
    super(USER_SELECT_FUNCTION_CALL_TEXT);

    this.patients = patients;
  }

  @Override
  public void setSingleJavascriptObjects() {
    // nothing to do here
  }

  @Override
  public void process() {

    for (Person p : patients) {
      String personId = String.valueOf(p.getId());
      Pair<String> id = new Pair<String>(ID, personId);
      Pair<String> name = new Pair<String>(NAME, p.getName());
      addRow(id, name);
    }

  }

}
