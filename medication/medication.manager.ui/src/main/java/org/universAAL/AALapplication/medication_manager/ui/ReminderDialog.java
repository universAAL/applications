package org.universAAL.AALapplication.medication_manager.ui;

import java.util.Locale;

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

public class ReminderDialog extends UICaller {

	public ReminderDialog(ModuleContext context) {
		super(context);
	}

	@Override
	public void communicationChannelBroken() {	}

	@Override
	public void dialogAborted(String dialogID) {	}

	@Override
	public void handleUIResponse(UIResponse input) {
		// TODO Auto-generated method stub
	}
	
	public void showDialog(Resource inputUser){
		Form f = Form.newDialog("Medication Manager UI", new Resource());
		//start of the form model
		new SimpleOutput(f.getIOControls(), null, null, "UniversAAL!");
		//...
		new Submit(f.getSubmits(), new Label("Done", null), "doneForm");
		//stop of form model
		UIRequest req = new UIRequest(inputUser, f, LevelRating.none, Locale.ENGLISH, PrivacyLevel.insensible);
		this.sendUIRequest(req);
	}

}
