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

import org.universAAL.AALapplication.medication_manager.servlet.ui.base.export.parser.script.JavaScriptObjectCreator;
import org.universAAL.AALapplication.medication_manager.servlet.ui.base.export.parser.script.Pair;
import org.universAAL.AALapplication.medication_manager.persistence.layer.UserInfo;

import java.util.List;

/**
 * @author George Fournadjiev
 */
public final class PersonScriptArrayCreator {

  private final String type;
  private final List<? extends  UserInfo> userInfos;

  public PersonScriptArrayCreator(String type, List<? extends UserInfo> userInfos) {
    this.type = type;
    this.userInfos = userInfos;
  }

  public String createJavaScriptArrayText() {
    StringBuffer sb = new StringBuffer();

    sb.append("\n\t");
    sb.append(type);
    sb.append(" = ");
    JavaScriptObjectCreator creator = new JavaScriptObjectCreator();

    for (UserInfo person : userInfos) {
      String id = String.valueOf(person.getId());
      String name = person.getName();
      Pair<String> pair = new Pair<String>(id, name);
       creator.addPair(pair);
    }

    String javascriptObject = creator.createJavascriptObject();
    sb.append(javascriptObject);

    sb.append('\n');


    return sb.toString();
  }
}
