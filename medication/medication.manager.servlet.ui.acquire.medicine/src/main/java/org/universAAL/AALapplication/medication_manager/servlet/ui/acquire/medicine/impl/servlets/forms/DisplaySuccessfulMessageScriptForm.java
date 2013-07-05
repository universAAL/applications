package org.universAAL.AALapplication.medication_manager.servlet.ui.acquire.medicine.impl.servlets.forms;

import org.universAAL.AALapplication.medication_manager.servlet.ui.base.export.parser.script.forms.ScriptForm;

/**
 * @author George Fournadjiev
 */
public final class DisplaySuccessfulMessageScriptForm extends ScriptForm {

  private final String message;


  public DisplaySuccessfulMessageScriptForm(String message) {

    this.message = message;
    setSingleJavascriptObjects();
  }

  @Override
  public void setSingleJavascriptObjects() {
    singleJavascriptObjects = new String[]{"var msg = \"" + message + "\";"};
  }

  @Override
  public void process() {
    //nothing we can do here
  }
}
