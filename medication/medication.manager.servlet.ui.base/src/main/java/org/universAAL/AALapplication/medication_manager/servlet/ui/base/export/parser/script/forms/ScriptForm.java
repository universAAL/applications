package org.universAAL.AALapplication.medication_manager.servlet.ui.base.export.parser.script.forms;


import org.universAAL.AALapplication.medication_manager.servlet.ui.base.export.parser.script.JavaScriptObjectCreator;
import org.universAAL.AALapplication.medication_manager.servlet.ui.base.export.parser.script.Pair;
import org.universAAL.AALapplication.medication_manager.servlet.ui.base.export.parser.script.Script;

import java.util.LinkedList;
import java.util.List;

/**
 * @author George Fournadjiev
 */
public abstract class ScriptForm {

  protected String[] singleJavascriptObjects;
  private final String functionCallText;
  private final List<String> pairs = new LinkedList<String>();

  public static final String ID = "id";
  public static final String NAME = "name";

  protected ScriptForm(String functionCallText) {
    this.functionCallText = functionCallText;
    this.singleJavascriptObjects = new String[]{};
  }

  protected ScriptForm() {
    this(null);
  }

  public abstract void process();

  public abstract void setSingleJavascriptObjects();

  public void addRow(Pair... pair) {
    JavaScriptObjectCreator creator = new JavaScriptObjectCreator();

    for (Pair p : pair) {
      if (p.isNotEmpty()) {
        creator.addPair(p);
      }
    }

    String javascriptObject = creator.createJavascriptObject();

    pairs.add(javascriptObject);
  }

  public String createScriptText() {
    process();

    String[] rowsObjects = new String[pairs.size()];
    rowsObjects = pairs.toArray(rowsObjects);
    Script script;
    if (singleJavascriptObjects != null && singleJavascriptObjects.length != 0) {
      script = new Script(singleJavascriptObjects, functionCallText, rowsObjects);
    } else {
      script = new Script(functionCallText, rowsObjects);
    }

    return script.getScriptText();

  }

}