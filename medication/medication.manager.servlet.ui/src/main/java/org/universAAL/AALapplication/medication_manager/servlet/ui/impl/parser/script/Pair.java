package org.universAAL.AALapplication.medication_manager.servlet.ui.impl.parser.script;

/**
 * @author George Fournadjiev
 */
public final class Pair<V> {

  private static final char SEMICOLON = ':';
  private final String key;
  private final V value;

  private static final char QUOTE = '\'';

  public Pair(String key, V value) {
    this.key = QUOTE + key + QUOTE;
    this.value = value;
  }

  @Override
  public String toString() {
    return "Pair{" +
        "key='" + key + '\'' +
        ", value=" + value +
        '}';
  }

  public String getPairText() {
    String pairText = key + SEMICOLON;

    if (value instanceof String) {
      pairText = pairText +  "\'" + value + "\'";
    } else {
      pairText = pairText + value;
    }

    return pairText;
  }
}
