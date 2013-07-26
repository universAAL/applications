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

import org.universAAL.AALapplication.medication_manager.configuration.PropertyInfo;
import org.universAAL.AALapplication.medication_manager.persistence.layer.PersistentService;
import org.universAAL.AALapplication.medication_manager.persistence.layer.dao.MedicationPropertiesDao;
import org.universAAL.AALapplication.medication_manager.servlet.ui.base.export.helpers.Session;
import org.universAAL.AALapplication.medication_manager.servlet.ui.base.export.parser.script.JavaScriptObjectCreator;
import org.universAAL.AALapplication.medication_manager.servlet.ui.base.export.parser.script.Pair;
import org.universAAL.AALapplication.medication_manager.servlet.ui.base.export.parser.script.forms.ScriptForm;
import org.universAAL.AALapplication.medication_manager.servlet.ui.configuration.impl.MedicationManagerServletUIConfigurationException;

import java.util.HashSet;
import java.util.Set;

import static org.universAAL.AALapplication.medication_manager.servlet.ui.base.export.parser.script.Script.*;
import static org.universAAL.AALapplication.medication_manager.servlet.ui.configuration.impl.Util.*;

/**
 * @author George Fournadjiev
 */
public final class ParametersForm extends ScriptForm {

  private final PersistentService persistentService;
  private final Session session;

  public ParametersForm(PersistentService persistentService, Session session) {
    super();

    this.persistentService = persistentService;
    this.session = session;

  }

  @Override
  public String createScriptText() {
    StringBuffer sb = new StringBuffer();

    sb.append('\n');

    sb.append(SCRIPT_START);

    sb.append('\n');

    sb.append("\n\t parameters = {");

    createParametersValue(sb);

    sb.append("\n\t };");

    sb.append(SCRIPT_END);


    return sb.toString();
  }

  private void createParametersValue(StringBuffer sb) {

    MedicationPropertiesDao medicationPropertiesDao = persistentService.getMedicationPropertiesDao();

    Set<PropertyInfo> propertyInfos = medicationPropertiesDao.getAllProperties();

    Set<Integer> ids = new HashSet<Integer>();

    int count = 0;
    for (PropertyInfo info : propertyInfos) {
      count++;
      sb.append("\n\t\t");
      addProperty(info, sb);
      if (count < propertyInfos.size()) {
        sb.append(',');
      }
      sb.append('\n');
      ids.add(info.getId());
    }

    session.setAttribute(IDS, ids);

  }

  private void addProperty(PropertyInfo info, StringBuffer sb) {

    int id = info.getId();
    if (id <= 0) {
      throw new MedicationManagerServletUIConfigurationException("Unexpected id of the PropertyInfo object : " + info);
    }

    JavaScriptObjectCreator creator = new JavaScriptObjectCreator();

    Pair<String> pair = new Pair<String>("name", info.getName());

    creator.addPair(pair);

    pair = new Pair<String>("value", info.getValue(), true);

    creator.addPair(pair);

    pair = new Pair<String>("type", info.getType().getValue());

    creator.addPair(pair);

    pair = new Pair<String>("format", info.getFormat().getValue());

    creator.addPair(pair);

    pair = new Pair<String>("description", info.getDescription());

    creator.addPair(pair);

    sb.append('\"');
    sb.append(id);
    sb.append("\":");
    sb.append(creator.createJavascriptObject());

  }

  @Override
  public void process() {
    throw new UnsupportedOperationException("Not implemented");
  }

  @Override
  public void setSingleJavascriptObjects() {
    throw new UnsupportedOperationException("Not implemented");
  }

}
