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

package org.universAAL.AALapplication.medication_manager.servlet.ui.acquire.medicine.impl.servlets.forms;

import org.universAAL.AALapplication.medication_manager.persistence.layer.entities.Person;
import org.universAAL.AALapplication.medication_manager.servlet.ui.base.export.parser.script.Pair;
import org.universAAL.AALapplication.medication_manager.servlet.ui.base.export.parser.script.forms.ScriptForm;

import java.util.List;

/**
 * @author George Fournadjiev
 */
public final class UserSelectScriptForm extends ScriptForm {

  private final List<Person> patients;

  private static final String USER_SELECT_FUNCTION_CALL_TEXT = "users.push";

  public UserSelectScriptForm(List<Person> patients) {
    super(USER_SELECT_FUNCTION_CALL_TEXT);

    this.patients = patients;
  }

  @Override
  public void setSingleJavascriptObjects() {
    // nothing to do here
  }

  @Override
  public void process() {

    for (Person p : patients) {
      String personId = String.valueOf(p.getId());
      Pair<String> id = new Pair<String>(ID, personId);
      Pair<String> name = new Pair<String>(NAME, p.getName());
      addRow(id, name);
    }

  }

}
