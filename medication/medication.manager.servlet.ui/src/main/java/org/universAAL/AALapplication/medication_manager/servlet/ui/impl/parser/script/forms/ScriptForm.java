package org.universAAL.AALapplication.medication_manager.servlet.ui.impl.parser.script.forms;

import org.universAAL.AALapplication.medication_manager.servlet.ui.impl.parser.script.JavaScriptObjectCreator;
import org.universAAL.AALapplication.medication_manager.servlet.ui.impl.parser.script.Pair;
import org.universAAL.AALapplication.medication_manager.servlet.ui.impl.parser.script.Script;

import java.util.LinkedList;
import java.util.List;

/**
 * @author George Fournadjiev
 */
public abstract class ScriptForm {

  private final String functionCallText;
  private final List<String> pairs = new LinkedList<String>();

  protected ScriptForm(String functionCallText) {
    this.functionCallText = functionCallText;
  }

  public abstract void process();

  public void addRow(Pair... pair) {
    JavaScriptObjectCreator creator = new JavaScriptObjectCreator();

    for (Pair p : pair) {
      creator.addPair(p);
    }

    String javascriptObject = creator.createJavascriptObject();

    pairs.add(javascriptObject);
  }

  public String createScriptText() {
    process();

    String[] objects = new String[pairs.size()];
    objects = pairs.toArray(objects);
    Script script = new Script(functionCallText, objects);

    return script.getScriptText();

  }

}
