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


package org.universAAL.AALapplication.medication_manager.ui;

import org.universAAL.AALapplication.medication_manager.persistence.layer.PersistentService;
import org.universAAL.AALapplication.medication_manager.persistence.layer.dao.IntakeDao;
import org.universAAL.AALapplication.medication_manager.persistence.layer.entities.Intake;
import org.universAAL.AALapplication.medication_manager.persistence.layer.entities.Medicine;
import org.universAAL.AALapplication.medication_manager.persistence.layer.entities.Treatment;
import org.universAAL.AALapplication.medication_manager.persistence.layer.entities.UnitClass;
import org.universAAL.AALapplication.medication_manager.ui.impl.Log;
import org.universAAL.AALapplication.medication_manager.ui.impl.MedicationManagerUIException;
import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.owl.supply.LevelRating;
import org.universAAL.middleware.rdf.Resource;
import org.universAAL.middleware.ui.UICaller;
import org.universAAL.middleware.ui.UIRequest;
import org.universAAL.middleware.ui.UIResponse;
import org.universAAL.middleware.ui.owl.PrivacyLevel;
import org.universAAL.middleware.ui.rdf.Form;
import org.universAAL.middleware.ui.rdf.Label;
import org.universAAL.middleware.ui.rdf.SimpleOutput;
import org.universAAL.middleware.ui.rdf.Submit;
import org.universAAL.ontology.medMgr.MedicinesInfo;
import org.universAAL.ontology.medMgr.Time;
import org.universAAL.ontology.profile.User;

import java.util.List;
import java.util.Locale;

import static org.universAAL.AALapplication.medication_manager.persistence.layer.entities.UnitClass.*;
import static org.universAAL.AALapplication.medication_manager.ui.impl.Activator.*;

public class RequestMedicationInfoDialog extends UICaller {

  private final ModuleContext moduleContext;
  private final Time time;
  private MedicinesInfo medicinesInfo;
  private User currentUser; //TODO to be removed (hack for saied user)

  private static final String CLOSE_BUTTON = "closeButton";
  private static final String INFO_BUTTON = "infoButton";

  public RequestMedicationInfoDialog(ModuleContext context, Time time) {
    super(context);

    validateParameter(context, "context");
    validateParameter(time, "time");

    this.moduleContext = context;
    this.time = time;
  }

  public RequestMedicationInfoDialog(ModuleContext context) {
    this(context, null);
  }

  @Override
  public void communicationChannelBroken() {
  }

  @Override
  public void dialogAborted(String dialogID) {
  }

  @Override
  public void handleUIResponse(UIResponse input) {
    try {
      //TODO to be removed (hack for saied user)
      User user = (User) input.getUser();
      if (user.getURI().equals(SAIED.getURI())) {
        user = currentUser;
      }
      if (CLOSE_BUTTON.equals(input.getSubmissionID())) {
        ReminderDialog reminderDialog = new ReminderDialog(moduleContext, time);
        reminderDialog.showDialog(user);
      } else if (INFO_BUTTON.equals(input.getSubmissionID())) {
        MedicationInfoDialog medicationInfoDialog = new MedicationInfoDialog(moduleContext, time, getMedicinesInfo());
        medicationInfoDialog.showDialog(user);
      } else {
        System.out.println("unknown");
      }
    } catch (Exception e) {
      Log.error(e, "Error while handling UI response", getClass());
    }
  }

  public void showDialog(User inputUser) {

    try {
      validateParameter(inputUser, "inputUser");

      //TODO to be removed (hack for saied user)
      currentUser = inputUser;

      Form f = Form.newDialog("Medication Manager", new Resource());
      //start of the form model

      createMedicineInfo(inputUser);


      new SimpleOutput(f.getIOControls(), null, null, medicinesInfo.getGeneralInfo());
      //...
      new Submit(f.getSubmits(), new Label("close", null), CLOSE_BUTTON);
      new Submit(f.getSubmits(), new Label("info", null), INFO_BUTTON);
      //stop of form model
      //TODO to remove SAIED user and to return inputUser variable
      UIRequest req = new UIRequest(SAIED, f, LevelRating.none, Locale.ENGLISH, PrivacyLevel.insensible);
      this.sendUIRequest(req);
    } catch (Exception e) {
      Log.error(e, "Error while trying to show dialog", getClass());
    }

  }

  private void createMedicineInfo(User inputUser) {
    PersistentService persistentService = getPersistentService();

    IntakeDao intakeDao = persistentService.getIntakeDao();

    List<Intake> intakes = intakeDao.getIntakesByUserAndTime(inputUser, time);

    this.medicinesInfo = createMedicineInfoFromIntakes(intakes);
  }

  public MedicinesInfo getMedicinesInfo() {
    if (medicinesInfo == null) {
      throw new MedicationManagerUIException("The MedicineInfo field is not set");
    }
    return medicinesInfo;
  }

  public MedicinesInfo createMedicineInfoFromIntakes(List<Intake> intakes) {
    String generalInfo = getGeneralInfo(intakes);
    String detailsInfo = getDetailsInfo(intakes);

    return new MedicinesInfo(generalInfo, detailsInfo, time);
  }

  private String getGeneralInfo(List<Intake> intakes) {
    StringBuilder sb = new StringBuilder();

    sb.append("   Intake info for          ");
    sb.append(time.getDailyTextFormat());
    sb.append("  o'clock   ");
    sb.append("\n");
    appendQuantityAndUnits(sb, intakes);

    return sb.toString();
  }

  private void appendQuantityAndUnits(StringBuilder sb, List<Intake> intakes) {
    int count = 0;
    for (Intake in : intakes) {
      sb.append('\n');
      count++;
      sb.append(count);
      sb.append(". ");
      Treatment treatment = in.getTreatment();
      sb.append(treatment.getMedicine().getMedicineName());
      sb.append("  -  ");
      sb.append(in.getQuantity());
      UnitClass unitClass = in.getUnitClass();
      if (unitClass == PILLS) {
        sb.append(" pills");
      } else if (unitClass == DROPS) {
        sb.append(" drops");
      } else {
        throw new MedicationManagerUIException("Unknown UnitClass enum value : " + unitClass);
      }
    }


    sb.append('\n');
  }

  private String getDetailsInfo(List<Intake> intakes) {
    StringBuilder sb = new StringBuilder();

    sb.append("    Medication info          ");
    sb.append("\n\n");

    int count = 0;
    for (Intake in : intakes) {
      sb.append('\n');
      count++;
      sb.append(count);
      sb.append(". ");
      Treatment treatment = in.getTreatment();
      Medicine medicine = treatment.getMedicine();
      sb.append(medicine.getMedicineName());
      sb.append('\n');
      sb.append(medicine.getMedicineInfo());
    }

    return sb.toString();
  }

}
