package org.universAAL.AALapplication.medication_manager.servlet.ui.impl.parser.script.forms;

import org.universAAL.AALapplication.medication_manager.persistence.layer.PersistentService;

/**
 * @author George Fournadjiev
 */
public final class DisplayErrorScriptForm extends ScriptForm {

  private final PersistentService persistentService;
  private final String message;

  private static final String USER_SELECT_FUNCTION_CALL_TEXT = "users.push";

  public DisplayErrorScriptForm(PersistentService persistentService, String message) {

    this.persistentService = persistentService;
    this.message = message;
    setSingleJavascriptObjects();
  }

  @Override
  public void setSingleJavascriptObjects() {
    singleJavascriptObjects = new String[]{"var errorMsg = \"" + message + "\";"};
  }

  @Override
  public void process() {
     //nothing we can do here
  }
}
