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

package org.universAAL.AALapplication.medication_manager.servlet.ui.configuration.impl.servlets.forms;

import org.universAAL.AALapplication.medication_manager.persistence.layer.entities.Dispenser;
import org.universAAL.AALapplication.medication_manager.servlet.ui.base.export.parser.script.JavaScriptObjectCreator;
import org.universAAL.AALapplication.medication_manager.servlet.ui.base.export.parser.script.Pair;

import java.util.List;

/**
 * @author George Fournadjiev
 */
public final class DispenserScriptArrayCreator {

  private final List<Dispenser> persons;


  public DispenserScriptArrayCreator(List<Dispenser> dispensers) {
    this.persons = dispensers;
  }

  public String createJavaScriptArrayText() {
    StringBuffer sb = new StringBuffer();

    sb.append("\n\t");
    sb.append("dispensers");
    sb.append(" = ");
    JavaScriptObjectCreator creator = new JavaScriptObjectCreator();

    for (Dispenser dispenser : persons) {
      String id = String.valueOf(dispenser.getId());
      String name = dispenser.getName();
      Pair<String> pair = new Pair<String>(id, name);
      creator.addPair(pair);
    }

    addNoneDispenser(sb, creator);

    String javascriptObject = creator.createJavascriptObject();
    sb.append(javascriptObject);

    sb.append('\n');


    return sb.toString();
  }

  private void addNoneDispenser(StringBuffer sb, JavaScriptObjectCreator creator) {
    String id = String.valueOf(-1);
    String name = "none";
    Pair<String> pair = new Pair<String>(id, name);
    creator.addPair(pair);
  }
}
