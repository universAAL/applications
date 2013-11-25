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
  protected final String functionCallText;
  protected final List<String> pairs = new LinkedList<String>();

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
