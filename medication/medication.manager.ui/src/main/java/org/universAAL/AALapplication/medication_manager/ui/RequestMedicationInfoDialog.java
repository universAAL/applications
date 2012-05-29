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
import org.universAAL.ontology.profile.User;

import java.util.Locale;

public class RequestMedicationInfoDialog extends UICaller {

  private final ModuleContext moduleContext;

  private static final String CLOSE_BUTTON = "closeButton";
  private static final String INFO_BUTTON = "infoButton";

  public RequestMedicationInfoDialog(ModuleContext context) {
    super(context);
    this.moduleContext = context;
  }

  @Override
  public void communicationChannelBroken() {
  }

  @Override
  public void dialogAborted(String dialogID) {
  }

  @Override
  public void handleUIResponse(UIResponse input) {
    if (CLOSE_BUTTON.equals(input.getSubmissionID())) {
      ReminderDialog reminderDialog = new ReminderDialog(moduleContext);
      reminderDialog.showDialog((User) input.getUser());
    } else if (INFO_BUTTON.equals(input.getSubmissionID())) {
      //TODO
    } else {
      System.out.println("unknown");
    }
  }

  public void showDialog(User inputUser) {
    Form f = Form.newDialog("Medication Manager UI", new Resource());
    handleForm(inputUser, f);

  }

  private void handleForm(User inputUser, Form f) {
    //start of the form model
    new SimpleOutput(f.getIOControls(), null, null, "Requested medication info!");
    //...
    new Submit(f.getSubmits(), new Label("close", null), CLOSE_BUTTON);
    new Submit(f.getSubmits(), new Label("info", null), INFO_BUTTON);
    //stop of form model
    UIRequest req = new UIRequest(inputUser, f, LevelRating.none, Locale.ENGLISH, PrivacyLevel.insensible);
    this.sendUIRequest(req);
  }

}
