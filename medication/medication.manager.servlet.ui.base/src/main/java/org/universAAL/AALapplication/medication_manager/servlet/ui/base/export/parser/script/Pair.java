package org.universAAL.AALapplication.medication_manager.servlet.ui.base.export.parser.script;

/**
 * @author George Fournadjiev
 */
public final class Pair<V> {

  private static final char SEMICOLON = ':';
  private final String key;
  private final V value;
  private final boolean isValueWithoutQuotes;

  private static final char QUOTE = '\'';

  public Pair(String key, V value, boolean isValueWithoutQuotes) {
    this.key = key != null ? QUOTE + key + QUOTE : null;
    this.value = value;
    this.isValueWithoutQuotes = isValueWithoutQuotes;
  }

  public Pair(String key, V value) {
   this(key, value, false);
  }

  @Override
  public String toString() {
    return "Pair{" +
        "key='" + key + '\'' +
        ", value=" + value +
        ", isValueWithoutQuotes=" + isValueWithoutQuotes +
        '}';
  }

  public String getPairText() {

    if (key == null) {
      return "";
    }

    String pairText = key + SEMICOLON;

    if (isValueWithoutQuotes) {
      pairText = pairText + value;
    } else {
      pairText = pairText + "\'" + value + "\'";
    }

    return pairText;
  }

  public boolean isNotEmpty() {
    return key != null;
  }
}