package org.universAAL.AALapplication.medication_manager.servlet.ui.configuration.impl.servlets.forms;

import org.universAAL.AALapplication.medication_manager.configuration.PropertyInfo;
import org.universAAL.AALapplication.medication_manager.persistence.layer.PersistentService;
import org.universAAL.AALapplication.medication_manager.persistence.layer.dao.MedicationPropertiesDao;
import org.universAAL.AALapplication.medication_manager.servlet.ui.base.export.helpers.Session;
import org.universAAL.AALapplication.medication_manager.servlet.ui.base.export.parser.script.JavaScriptObjectCreator;
import org.universAAL.AALapplication.medication_manager.servlet.ui.base.export.parser.script.Pair;
import org.universAAL.AALapplication.medication_manager.servlet.ui.base.export.parser.script.forms.ScriptForm;
import org.universAAL.AALapplication.medication_manager.servlet.ui.configuration.impl.MedicationManagerServletUIConfigurationException;

import java.util.Set;

import static org.universAAL.AALapplication.medication_manager.servlet.ui.base.export.parser.script.Script.*;

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

    int count = 0;
    for (PropertyInfo info : propertyInfos) {
      count++;
      sb.append('\n');
      addProperty(info, sb);
      if (count < propertyInfos.size()) {
        sb.append(',');
      }
      sb.append('\n');
    }

  }

  private void addProperty(PropertyInfo info, StringBuffer sb) {

    if (info.getId() <= 0) {
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
    sb.append(info.getId());
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

  /*
  parameters = {
          "id1":{
              "name":'medication.reminder.timeout',
              "value":100,
              "type":"number",
              "format":"seconds",
              "description":"timeout for user response"
          },
          "id2":{
              "name":'medication.intake.interval',
              "value":1,
              "type":"number",
              "format":"minutes",
              "description":"timestamp to closest intake in minutes tolerance (+/-)"
          },
          "id3":{
              "name":'debug.write.file',
              "value":false,
              "type":"boolean",
              "format":"on/off",
              "description":""
          },
          "id4":{
              "name":'debug.text.prefix',
              "value":"Medication Manager Service:",
              "type":"string",
              "format":"text",
              "description":""
          }

      };
   */
}
