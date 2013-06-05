package org.universAAL.AALapplication.medication_manager.servlet.ui.configuration.impl.servlets.forms;

import org.universAAL.AALapplication.medication_manager.servlet.ui.base.export.parser.script.JavaScriptObjectCreator;
import org.universAAL.AALapplication.medication_manager.servlet.ui.base.export.parser.script.Pair;
import org.universAAL.AALapplication.medication_manager.user.management.UserInfo;

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
