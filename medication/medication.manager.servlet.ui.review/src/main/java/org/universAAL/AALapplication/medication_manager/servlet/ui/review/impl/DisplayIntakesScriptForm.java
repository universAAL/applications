package org.universAAL.AALapplication.medication_manager.servlet.ui.review.impl;

import org.universAAL.AALapplication.medication_manager.persistence.layer.IntakeInfo;
import org.universAAL.AALapplication.medication_manager.persistence.layer.PersistentService;
import org.universAAL.AALapplication.medication_manager.servlet.ui.base.export.parser.script.JavaScriptObjectCreator;
import org.universAAL.AALapplication.medication_manager.servlet.ui.base.export.parser.script.Pair;
import org.universAAL.AALapplication.medication_manager.servlet.ui.base.export.parser.script.forms.ScriptForm;

import java.util.HashSet;
import java.util.Set;

import static org.universAAL.AALapplication.medication_manager.servlet.ui.base.export.parser.script.Script.*;

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
    //nothing to do here
  }

  @Override
  public void process() {
    //nothing we can do here
  }

  @Override
  public String createScriptText() {
    StringBuffer sb = new StringBuffer();

    sb.append('\n');

    sb.append(SCRIPT_START);

    sb.append('\n');

    sb.append("\n\t");

    String username = "Saied"; //TODO to be removed

    sb.append("var user = \"");

    sb.append(username);

    sb.append("\";");

    sb.append('\n');

    sb.append("\n\t intakes = {");

    createIntakes(sb);

    sb.append("\n\t };");

    sb.append(SCRIPT_END);


    return sb.toString();
  }

  private void createIntakes(StringBuffer sb) {

    Set<IntakeInfo> infos = new HashSet<IntakeInfo>();

    IntakeInfo intakeInfo = new IntakeInfo("23.03.2013", "15:30", "Vitaton", IntakeInfo.COMING);
    infos.add(intakeInfo);
    intakeInfo = new IntakeInfo("23.03.2013", "10:00", "A-Z", IntakeInfo.MISSED);
    infos.add(intakeInfo);
    intakeInfo = new IntakeInfo("23.03.2013", "15:30", "Centrum", IntakeInfo.TAKEN);
    infos.add(intakeInfo);

    int count = 0;
    for (IntakeInfo info : infos) {
      count++;
      sb.append("\n\t\t");
      addIntake(info, sb, count);
      if (count < infos.size()) {
        sb.append(',');
      }
      sb.append('\n');
    }


  }

  private void addIntake(IntakeInfo info, StringBuffer sb, int id) {

    JavaScriptObjectCreator creator = new JavaScriptObjectCreator();

    Pair<String> pair = new Pair<String>("date", info.getDate());

    creator.addPair(pair);

    pair = new Pair<String>("time", info.getTime());

    creator.addPair(pair);

    pair = new Pair<String>("medication", info.getMedication());

    creator.addPair(pair);

    pair = new Pair<String>("status", info.getStatus());

    creator.addPair(pair);

    sb.append('\"');
    sb.append(id);
    sb.append("\":");
    sb.append(creator.createJavascriptObject());

  }
}
