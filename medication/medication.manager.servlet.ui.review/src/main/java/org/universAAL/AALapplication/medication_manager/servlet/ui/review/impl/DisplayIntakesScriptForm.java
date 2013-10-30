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

package org.universAAL.AALapplication.medication_manager.servlet.ui.review.impl;

import org.universAAL.AALapplication.medication_manager.persistence.layer.IntakeInfo;
import org.universAAL.AALapplication.medication_manager.persistence.layer.PersistentService;
import org.universAAL.AALapplication.medication_manager.persistence.layer.Week;
import org.universAAL.AALapplication.medication_manager.persistence.layer.dao.ComplexDao;
import org.universAAL.AALapplication.medication_manager.persistence.layer.entities.Person;
import org.universAAL.AALapplication.medication_manager.servlet.ui.base.export.parser.script.JavaScriptObjectCreator;
import org.universAAL.AALapplication.medication_manager.servlet.ui.base.export.parser.script.Pair;
import org.universAAL.AALapplication.medication_manager.servlet.ui.base.export.parser.script.forms.ScriptForm;

import java.util.Set;

import static org.universAAL.AALapplication.medication_manager.servlet.ui.base.export.parser.script.Script.*;

/**
 * @author George Fournadjiev
 */
public final class DisplayIntakesScriptForm extends ScriptForm {

  private final PersistentService persistentService;
  private final Person patient;
  private final Week selectedWeek;

  public DisplayIntakesScriptForm(PersistentService persistentService,
                                  Person patient, Week selectedWeek) {

    this.persistentService = persistentService;
    this.patient = patient;
    this.selectedWeek = selectedWeek;
  }

  @Override
  public void setSingleJavascriptObjects() {
    //nothing to do here
  }

  @Override
  public void process() {
    //nothing we can do here
  }

  @Override
  public String createScriptText() {
    StringBuffer sb = new StringBuffer();

    sb.append('\n');

    sb.append(SCRIPT_START);

    sb.append('\n');

    sb.append("\n\t");

    sb.append("var user = \"");

    sb.append(patient.getName());
    sb.append("<br> WEEK <br> ");
    sb.append(Week.getFormattedTextWeek(selectedWeek));
    sb.append("\";");

    sb.append('\n');

    sb.append("\n\t intakes = {");

    createIntakes(sb);

    sb.append("\n\t };");

    sb.append(SCRIPT_END);


    return sb.toString();
  }

  private void createIntakes(StringBuffer sb) {

    ComplexDao complexDao = persistentService.getComplexDao();

    Set<IntakeInfo> infos = complexDao.getIntakeInfos(patient, selectedWeek);

    int count = 0;
    for (IntakeInfo info : infos) {
      count++;
      sb.append("\n\t\t");
      addIntake(info, sb, count);
      if (count < infos.size()) {
        sb.append(',');
      }
      sb.append('\n');
    }


  }

  private void addIntake(IntakeInfo info, StringBuffer sb, int id) {

    JavaScriptObjectCreator creator = new JavaScriptObjectCreator();

    Pair<String> pair = new Pair<String>("date", info.getDate());

    creator.addPair(pair);

    pair = new Pair<String>("time", info.getTime());

    creator.addPair(pair);

    pair = new Pair<String>("medication", info.getMedication());

    creator.addPair(pair);

    pair = new Pair<String>("status", info.getStatus());

    creator.addPair(pair);

    sb.append('\"');
    sb.append(id);
    sb.append("\":");
    sb.append(creator.createJavascriptObject());

  }


}
