package org.universAAL.AALapplication.medication_manager.servlet.ui.impl.parser.script;

import java.util.LinkedList;
import java.util.List;

/**
 * @author George Fournadjiev
 */
public final class JavaScriptObjectCreator {

  private final List<Pair> objectPairs = new LinkedList<Pair>();

  public void addPair(Pair pair) {
    objectPairs.add(pair);
  }

  public String createJavascriptObject() {
    if (objectPairs.isEmpty()) {
      return null;
    }

    StringBuffer sb = new StringBuffer();
    sb.append('{');
    int i = 0;
    int size = objectPairs.size();
    for (Pair pair : objectPairs) {
      sb.append(pair.getPairText());
      i++;
      if (i < size) {
        sb.append(", ");
      }
    }

    sb.append('}');

    return sb.toString();
  }

}
