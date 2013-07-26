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

import java.util.LinkedList;
import java.util.List;

/**
 * @author George Fournadjiev
 */
public final class JavaScriptObjectCreator {

  private final List<Pair> objectPairs = new LinkedList<Pair>();

  public void addPair(Pair pair) {
    objectPairs.add(pair);
  }

  public String createJavascriptObject() {
    if (objectPairs.isEmpty()) {
      return null;
    }

    StringBuffer sb = new StringBuffer();
    sb.append('{');
    int i = 0;
    int size = objectPairs.size();
    for (Pair pair : objectPairs) {
      sb.append(pair.getPairText());
      i++;
      if (i < size) {
        sb.append(", ");
      }
    }

    sb.append('}');

    return sb.toString();
  }

}
