package org.universAAL.AALapplication.medication_manager.servlet.ui.configuration.impl.servlets.forms;

import org.universAAL.AALapplication.medication_manager.persistence.layer.PersistentService;
import org.universAAL.AALapplication.medication_manager.servlet.ui.base.export.helpers.Session;
import org.universAAL.AALapplication.medication_manager.servlet.ui.base.export.parser.script.forms.ScriptForm;

/**
 * @author George Fournadjiev
 */
public final class ParametersForm extends ScriptForm {

  private final PersistentService persistentService;
  private final Session session;

  public ParametersForm(PersistentService persistentService, Session session) {
    super();


    this.persistentService = persistentService;
    this.session = session;

  }

  @Override
  public String createScriptText() {
    return "";
  }

  @Override
  public void process() {
    throw new UnsupportedOperationException("Not implemented yet");
  }

  @Override
  public void setSingleJavascriptObjects() {
    throw new UnsupportedOperationException("Not implemented");
  }
}
