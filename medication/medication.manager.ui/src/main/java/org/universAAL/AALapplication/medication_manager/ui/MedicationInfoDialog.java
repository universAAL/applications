package org.universAAL.AALapplication.medication_manager.ui;

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
import org.universAAL.ontology.medMgr.MedicationException;
import org.universAAL.ontology.medMgr.MedicinesInfo;
import org.universAAL.ontology.medMgr.MyIntakeInfosDatabase;
import org.universAAL.ontology.profile.User;

import java.util.Locale;

public class MedicationInfoDialog extends UICaller {

  private ModuleContext moduleContext;

  private static final String CLOSE_BUTTON = "closeButton";
  private static final String INFO_BUTTON = "reminderButton";

  public MedicationInfoDialog(ModuleContext context) {
    super(context);
    moduleContext = context;
  }

  @Override
  public void communicationChannelBroken() {
  }

  @Override
  public void dialogAborted(String dialogID) {
  }

  @Override
  public void handleUIResponse(UIResponse input) {
    //nothing to do here

  }

  public void showDialog(User inputUser) {
    Form f = Form.newDialog("Medication Manager UI", new Resource());


    //start of the form model

    MedicinesInfo intakeInfoForUser = MyIntakeInfosDatabase.getIntakeInfoForUser(inputUser);
    if (intakeInfoForUser == null) {
      throw new MedicationException("No information for the user: " + inputUser);
    }
    String medicationInfoMessage = getTitle() + intakeInfoForUser.getDetailsInfo();

    new SimpleOutput(f.getIOControls(), null, null, medicationInfoMessage);
    //...
    new Submit(f.getSubmits(), new Label("Close", null), CLOSE_BUTTON);
    //stop of form model
    UIRequest req = new UIRequest(inputUser, f, LevelRating.none, Locale.ENGLISH, PrivacyLevel.insensible);
    this.sendUIRequest(req);
  }

  private String getTitle() {

    StringBuilder sb = new StringBuilder();

    sb.append("         Intake info           ");
    sb.append("\n\n");


    return sb.toString();

  }

}
