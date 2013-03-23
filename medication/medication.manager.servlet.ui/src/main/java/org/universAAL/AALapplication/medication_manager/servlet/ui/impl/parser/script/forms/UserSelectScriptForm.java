package org.universAAL.AALapplication.medication_manager.servlet.ui.impl.parser.script.forms;

import org.universAAL.AALapplication.medication_manager.persistence.layer.PersistentService;
import org.universAAL.AALapplication.medication_manager.servlet.ui.impl.parser.script.Pair;

/**
 * @author George Fournadjiev
 */
public final class UserSelectScriptForm extends ScriptForm {

  private final PersistentService persistentService;

  private static final String USER_SELECT_FUNCTION_CALL_TEXT = "users.push";

  public UserSelectScriptForm(PersistentService persistentService) {
    super(USER_SELECT_FUNCTION_CALL_TEXT);

    this.persistentService = persistentService;
  }

  @Override
  public void setSingleJavascriptObjects() {
    // nothing to do here
  }

  @Override
  public void process() {
    Pair<String> id = new Pair<String>(ID, "1");
    Pair<String> name = new Pair<String>(NAME, "Peni Peneva");
    addRow(id, name);

    Pair<String> id1 = new Pair<String>(ID, "2");
    Pair<String> name1 = new Pair<String>(NAME, "Ken Kenov");
    addRow(id1, name1);
  }
}
