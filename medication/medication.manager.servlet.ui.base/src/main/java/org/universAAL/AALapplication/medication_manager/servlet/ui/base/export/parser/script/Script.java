/*******************************************************************************
 * Copyright 2012 , http://www.prosyst.com - ProSyst Software GmbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/

package org.universAAL.AALapplication.medication_manager.servlet.ui.base.export.parser.script;

/**
 * @author George Fournadjiev
 */
public final class Script {

  private final String[] singleJavaScriptObjects;
  private final String functionCallText;
  private final String[] rowsJavaScriptObjects;

  public static final String SCRIPT_START = "<script type=\"text/javascript\">\n";
  public static final String SCRIPT_END = "\n</script>";
  public static final String EMPTY = "";
  public static final String NEW_LINE_WITH_TAB = "\n\t";
  public static final char NEW_LINE = '\n';

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
