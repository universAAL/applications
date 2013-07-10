package org.universAAL.AALapplication.medication_manager.servlet.ui.review.impl;

import org.universAAL.AALapplication.medication_manager.persistence.layer.IntakeInfo;
import org.universAAL.AALapplication.medication_manager.persistence.layer.PersistentService;
import org.universAAL.AALapplication.medication_manager.persistence.layer.Week;
import org.universAAL.AALapplication.medication_manager.persistence.layer.dao.IntakeDao;
import org.universAAL.AALapplication.medication_manager.persistence.layer.entities.Intake;
import org.universAAL.AALapplication.medication_manager.persistence.layer.entities.Person;
import org.universAAL.AALapplication.medication_manager.servlet.ui.base.export.parser.script.JavaScriptObjectCreator;
import org.universAAL.AALapplication.medication_manager.servlet.ui.base.export.parser.script.Pair;
import org.universAAL.AALapplication.medication_manager.servlet.ui.base.export.parser.script.forms.ScriptForm;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.universAAL.AALapplication.medication_manager.servlet.ui.base.export.parser.script.Script.*;
import static org.universAAL.AALapplication.medication_manager.servlet.ui.review.impl.Util.*;

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
    sb.append("<br> WEEK <br> begin = ");
    sb.append(selectedWeek.getBegin());
    sb.append("<br> now = ");
    sb.append(selectedWeek.getNow());
    sb.append("<br> end = ");
    sb.append(selectedWeek.getEnd());
    sb.append("\";");

    sb.append('\n');

    sb.append("\n\t intakes = {");

    createIntakes(sb);

    sb.append("\n\t };");

    sb.append(SCRIPT_END);


    return sb.toString();
  }

  private void createIntakes(StringBuffer sb) {

    Set<IntakeInfo> infos = getIntakeInfos();

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

  /*private Set<IntakeInfo> getIntakeInfos() {
    Set<IntakeInfo> infos = new HashSet<IntakeInfo>();

    IntakeInfo intakeInfo = new IntakeInfo("23.03.2013", "15:30", "Vitaton", IntakeInfo.COMING);
    infos.add(intakeInfo);
    intakeInfo = new IntakeInfo("23.03.2013", "10:00", "A-Z", IntakeInfo.MISSED);
    infos.add(intakeInfo);
    intakeInfo = new IntakeInfo("23.03.2013", "15:30", "Centrum", IntakeInfo.TAKEN);
    infos.add(intakeInfo);
    return infos;

  }*/

  private Set<IntakeInfo> getIntakeInfos() {

    IntakeDao intakeDao = persistentService.getIntakeDao();
    List<Intake> intakes = intakeDao.getIntakesByPatientInWeek(patient, selectedWeek);

    if (intakes.isEmpty()) {
      return new HashSet<IntakeInfo>();
    }

    return createIntakeInfos(intakes);

  }

  private Set<IntakeInfo> createIntakeInfos(List<Intake> intakes) {
    Set<IntakeInfo> intakeInfos = new HashSet<IntakeInfo>();

    int count = 1;
    for (Intake intake : intakes) {
      String medication = intake.getTreatment().getMedicine().getMedicineName();
      String date = getDateText(intake.getTimePlan());
      String time = getTimeText(intake.getTimePlan());
      String status = getStatus(count);
      IntakeInfo info = new IntakeInfo(date, time, medication, status);
      intakeInfos.add(info);
      count++;
      if (count == 4) {
        count = 1;
      }
    }

    return intakeInfos;
  }

  private String getStatus(int count) {
    if (count == 1) {
      return "coming";
    }

    if (count == 2) {
      return "missed";
    }

    if (count == 3) {
      return "taken";
    }

    throw new MedicationManagerReviewException("blaaaaa");
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
