package org.universAAL.AALapplication.medication_manager.servlet.ui.base.export.parser.script;

/**
 * @author George Fournadjiev
 */
public final class Script {

  private static final char NEW_LINE = '\n';
  private final String[] singleJavaScriptObjects;
  private final String functionCallText;
  private final String[] rowsJavaScriptObjects;

  public static final String SCRIPT_START = "<script type=\"text/javascript\">\n";
  public static final String SCRIPT_END = "\n</script>";
  private static final String EMPTY = "";
  private static final String NEW_LINE_WITH_TAB = "\n\t";

  public Script(String[] singleJavaScriptObjects, String functionCallText, String[] rowsJavaScriptObjects) {

    this.singleJavaScriptObjects = singleJavaScriptObjects == null ? new String[]{} : singleJavaScriptObjects;
    this.functionCallText = functionCallText;
    this.rowsJavaScriptObjects = rowsJavaScriptObjects == null ? new String[]{} : rowsJavaScriptObjects;

  }

  public Script(String functionCallText, String[] rowsJavaScriptObjects) {
    this(null, functionCallText, rowsJavaScriptObjects);
  }

  public Script(String[] singleJavaScriptObjects) {
    this(singleJavaScriptObjects, null, null);
  }

  public String getScriptText() {

    if (missingInfo()) {
      return EMPTY;
    }

    if (missingRowsJavaScriptObject()) {
      return singleJavaScriptObjectsScriptText();
    }

    return getFullScriptText();
  }

  private String getFullScriptText() {

    StringBuffer sb = new StringBuffer();
    sb.append(SCRIPT_START);
    sb.append(NEW_LINE_WITH_TAB);

    for (String singleObject : singleJavaScriptObjects) {
      sb.append(singleObject);
      sb.append(NEW_LINE_WITH_TAB);
    }

    sb.append(NEW_LINE_WITH_TAB);

    for (String objectText : rowsJavaScriptObjects) {
      sb.append(functionCallText);
      sb.append('(');
      sb.append(objectText);
      sb.append(");");
      sb.append(NEW_LINE_WITH_TAB);
    }

    sb.append(NEW_LINE);

    sb.append(SCRIPT_END);

    return sb.toString();
  }

  private String singleJavaScriptObjectsScriptText() {
    StringBuffer sb = new StringBuffer();
    sb.append(SCRIPT_START);
    sb.append(NEW_LINE_WITH_TAB);

    for (String singleObject : singleJavaScriptObjects) {
      sb.append(singleObject);
      sb.append(NEW_LINE_WITH_TAB);
    }

    sb.append(NEW_LINE);

    sb.append(SCRIPT_END);

    return sb.toString();
  }

  private boolean missingInfo() {

    return singleJavaScriptObjects.length == 0 && missingRowsJavaScriptObject();
  }

  private boolean missingRowsJavaScriptObject() {
    return rowsJavaScriptObjects.length == 0;
  }
}
