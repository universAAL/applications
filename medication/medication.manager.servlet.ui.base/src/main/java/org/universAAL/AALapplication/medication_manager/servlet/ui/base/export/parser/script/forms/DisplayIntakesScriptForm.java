package org.universAAL.AALapplication.medication_manager.servlet.ui.base.export.parser.script.forms;

import org.universAAL.AALapplication.medication_manager.persistence.layer.PersistentService;

/**
 * @author George Fournadjiev
 */
public final class DisplayIntakesScriptForm extends ScriptForm {

  private final PersistentService persistentService;

  public DisplayIntakesScriptForm(PersistentService persistentService) {

    this.persistentService = persistentService;

  }

  @Override
  public void setSingleJavascriptObjects() {

  }

  @Override
  public void process() {
    //nothing we can do here
  }

  @Override
  public String createScriptText() {
    return "";
  }
}
