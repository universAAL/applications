package org.universAAL.AALapplication.medication_manager.servlet.ui.configuration.impl.servlets.forms;

import org.universAAL.AALapplication.medication_manager.persistence.layer.NotificationsInfo;
import org.universAAL.AALapplication.medication_manager.persistence.layer.PersistentService;
import org.universAAL.AALapplication.medication_manager.persistence.layer.dao.ComplexDao;
import org.universAAL.AALapplication.medication_manager.servlet.ui.base.export.helpers.Session;
import org.universAAL.AALapplication.medication_manager.servlet.ui.base.export.parser.script.JavaScriptObjectCreator;
import org.universAAL.AALapplication.medication_manager.servlet.ui.base.export.parser.script.Pair;
import org.universAAL.AALapplication.medication_manager.servlet.ui.base.export.parser.script.forms.ScriptForm;

import java.util.HashSet;
import java.util.Set;

import static org.universAAL.AALapplication.medication_manager.servlet.ui.base.export.parser.script.Script.*;
import static org.universAAL.AALapplication.medication_manager.servlet.ui.configuration.impl.Util.*;

/**
 * @author George Fournadjiev
 */
public final class NotificationForm extends ScriptForm {

  private final PersistentService persistentService;
  private final Session session;

  public NotificationForm(PersistentService persistentService, Session session) {
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

    sb.append("\n\t var rules = {");

    createNotifications(sb);

    sb.append("\n\t };");

    sb.append(SCRIPT_END);


    return sb.toString();

  }

  private void createNotifications(StringBuffer sb) {

    ComplexDao complexDao = persistentService.getComplexDao();
    Set<NotificationsInfo> infos = complexDao.getAllNotificationsInfo();

    Set<String> complexIds = new HashSet<String>();

    int count = 0;
    for (NotificationsInfo info : infos) {
      count++;
      sb.append("\n\t\t");
      addRule(info, sb);
      if (count < infos.size()) {
        sb.append(',');
      }
      sb.append('\n');
      complexIds.add(info.getComplexId());
    }

    session.setAttribute(COMPLEX_IDS, complexIds);

  }

  private void addRule(NotificationsInfo info, StringBuffer sb) {

    String id = info.getComplexId();


    JavaScriptObjectCreator creator = new JavaScriptObjectCreator();

    Pair<String> pair = new Pair<String>("patient", info.getPatient());

    creator.addPair(pair);

    pair = new Pair<String>("medicine", info.getMedicine());

    creator.addPair(pair);

    Pair<Boolean> booleanPair = new Pair<Boolean>("missed", info.isMissed(), true);

    creator.addPair(booleanPair);

    Pair<Integer> integerPair = new Pair<Integer>("threshold", info.getThreshold());

    creator.addPair(integerPair);

    booleanPair = new Pair<Boolean>("shortage", info.isShortage(), true);

    creator.addPair(booleanPair);

    booleanPair = new Pair<Boolean>("dose", info.isDose(), true);

    creator.addPair(booleanPair);

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
