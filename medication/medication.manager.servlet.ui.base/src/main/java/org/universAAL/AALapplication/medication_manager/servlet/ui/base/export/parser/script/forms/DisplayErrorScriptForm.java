package org.universAAL.AALapplication.medication_manager.servlet.ui.base.export.parser.script.forms;

/**
 * @author George Fournadjiev
 */
public final class DisplayErrorScriptForm extends ScriptForm {

  private final String message;

  private static final String USER_SELECT_FUNCTION_CALL_TEXT = "users.push";

  public DisplayErrorScriptForm(String message) {

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
