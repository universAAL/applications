package org.universAAL.AALapplication.medication_manager.servlet.ui.impl.parser.script;

import java.util.LinkedList;
import java.util.List;

/**
 * @author George Fournadjiev
 */
public final class UserScriptFactory {

  private List<String> pairs = new LinkedList<String>();

  public UserScriptFactory() {
  }

  public void addRow(Pair id, Pair<String> name) {
    JavasrciptObjectCreator creator = new JavasrciptObjectCreator();

    creator.addPair(id);
    creator.addPair(name);

    String javascriptObject = creator.createJavascriptObject();

    pairs.add(javascriptObject);
  }

  public String createSelectUserScript() {


    String[] objects = new String[pairs.size()];
    objects = pairs.toArray(objects);
    Script script = new Script("users.push", objects);

    return script.getScriptText();

  }


}
