package org.universAAL.AALapplication.medication_manager.servlet.ui.base.export.parser.script.forms;

import org.universAAL.AALapplication.medication_manager.persistence.layer.PersistentService;

/**
 * @author George Fournadjiev
 */
public final class DisplayLoginScriptForm extends ScriptForm {

  private final PersistentService persistentService;

  private static final String USER_SELECT_FUNCTION_CALL_TEXT = "users.push";

  public DisplayLoginScriptForm(PersistentService persistentService, boolean hasScript) {

    this.persistentService = persistentService;
    if (hasScript) {
      setSingleJavascriptObjects();
    }
  }

  @Override
  public void setSingleJavascriptObjects() {
    singleJavascriptObjects = new String[]{"isError = true;"};
  }

  @Override
  public void process() {
    //nothing we can do here
  }
}
