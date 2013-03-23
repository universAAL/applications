package org.universAAL.AALapplication.medication_manager.servlet.ui.impl.parser.script;

/**
 * @author George Fournadjiev
 */
public final class Script {

  private final String functionCallText;
  private final String[] javaScriptObjects;

  private static final String SCRIPT_START = "<script type=\"text/javascript\">\n";
  private static final String SCRIPT_END = "\n</script>>";
  private static final String EMPTY = "";

  public Script(String functionCallText, String[] javaScriptObjects) {
    this.functionCallText = functionCallText;
    this.javaScriptObjects = javaScriptObjects;
  }

  public String getScriptText() {

    if (javaScriptObjects == null || javaScriptObjects.length == 0) {
      return EMPTY;
    }

    StringBuffer sb = new StringBuffer();
    sb.append(SCRIPT_START);
    sb.append("\n\t");
    for (String objectText : javaScriptObjects) {
      sb.append(functionCallText);
      sb.append('(');
      sb.append(objectText);
      sb.append(");");
      sb.append("\n\t");
    }

    sb.append('\n');

    sb.append(SCRIPT_END);

    return sb.toString();
  }
}
