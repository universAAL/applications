package org.universAAL.AALapplication.medication_manager.servlet.ui.impl.parser.script;

/**
 * @author George Fournadjiev
 */
public final class Pair<V> {

  private static final char SEMICOLON = ':';
  private final String key;
  private final V value;
  private final boolean isValueJavaScriptObject;

  private static final char QUOTE = '\'';

  public Pair(String key, V value, boolean isValueJavaScriptObject) {
    this.key = QUOTE + key + QUOTE;
    this.value = value;
    this.isValueJavaScriptObject = isValueJavaScriptObject;
  }

  public Pair(String key, V value) {
   this(key, value, false);
  }

  @Override
  public String toString() {
    return "Pair{" +
        "key='" + key + '\'' +
        ", value=" + value +
        ", isValueJavaScriptObject=" + isValueJavaScriptObject +
        '}';
  }

  public String getPairText() {
    String pairText = key + SEMICOLON;

    if (isValueJavaScriptObject) {
      pairText = pairText + value;
    } else if (value instanceof String) {
      pairText = pairText + "\'" + value + "\'";
    } else {
      pairText = pairText + value;
    }

    return pairText;
  }
}
