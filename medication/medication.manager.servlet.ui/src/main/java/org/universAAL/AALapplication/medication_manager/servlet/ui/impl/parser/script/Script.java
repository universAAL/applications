package org.universAAL.AALapplication.medication_manager.servlet.ui.impl.parser.script;

/**
 * @author George Fournadjiev
 */
public final class Script {

  private final String functionCallText;
  private final String[] javascriptObjects;

  private static final String SCRIPT_START = "<script type=\"text/javascript\">\n";
  private static final String SCRIPT_END = "\n</script>>";

  public Script(String functionCallText, String[] javascriptObjects) {
    this.functionCallText = functionCallText;
    this.javascriptObjects = javascriptObjects;
  }

  public String getScriptText() {
    StringBuffer sb = new StringBuffer();
    sb.append(SCRIPT_START);
    sb.append("\n\t");
    for (String objectText : javascriptObjects) {
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
