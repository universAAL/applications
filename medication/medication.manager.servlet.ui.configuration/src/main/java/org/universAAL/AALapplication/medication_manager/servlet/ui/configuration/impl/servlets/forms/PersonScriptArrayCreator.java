package org.universAAL.AALapplication.medication_manager.servlet.ui.configuration.impl.servlets.forms;

import org.universAAL.AALapplication.medication_manager.persistence.layer.entities.Person;
import org.universAAL.AALapplication.medication_manager.servlet.ui.base.export.parser.script.JavaScriptObjectCreator;
import org.universAAL.AALapplication.medication_manager.servlet.ui.base.export.parser.script.Pair;

import java.util.List;

/**
 * @author George Fournadjiev
 */
public final class PersonScriptArrayCreator {

  private final String type;
  private final List<Person> persons;

  public PersonScriptArrayCreator(String type, List<Person> persons) {
    this.type = type;
    this.persons = persons;
  }

  public String createJavaScriptArrayText() {
    StringBuffer sb = new StringBuffer();

    sb.append("\n\t");
    sb.append(type);
    sb.append(" = ");
    JavaScriptObjectCreator creator = new JavaScriptObjectCreator();

    for (Person person : persons) {
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
