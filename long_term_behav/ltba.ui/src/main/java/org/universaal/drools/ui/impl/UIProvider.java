package org.universaal.drools.ui.impl;

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
import org.universAAL.middleware.ui.rdf.Submit;

public class UIProvider extends UICaller {

	static final String MY_UI_NAMESPACE = "http://www.tsbtecnolgias.es/LTBAUICaller.owl#";
	static final String SUBMISSION_ON = MY_UI_NAMESPACE + "submissionOn";
	static final String SUBMISSION_OFF = MY_UI_NAMESPACE + "submissionOff";
	static final String SUBMISSION_SHOW = MY_UI_NAMESPACE + "submissionDay";
	static final String SUBMISSION_WEEK = MY_UI_NAMESPACE + "submissionWeek";
	static final String SUBMISSION_MONTH = MY_UI_NAMESPACE + "submissionMonth";

	static final String USER_INPUT_SELECTED_RANGE = MY_UI_NAMESPACE
			+ "selectedRange";

	ModuleContext myModuleContext;

	protected UIProvider(ModuleContext context) {
		super(context);
		myModuleContext = context;
	}

	@Override
	public void communicationChannelBroken() {

	}

	@Override
	public void dialogAborted(String dialogID) {

	}

	public void showDialog(Resource inputUser) {
		Form f = Form.newDialog("LTBA UI", new Resource());
		// start of the form model
		// new SimpleOutput(f.getIOControls(), null, null,
		// "Long Term Behaviour Analyzer User Interface");
		// new SimpleOutput(f.getIOControls(), null, null, "NANONANO");
		// new SimpleOutput(f.getIOControls(), null, null, "TETETETE");

		// Submit s = new Submit(f.getIOControls(), new Label("Activate LTBA",
		// null), "ltbaButton");
		new Submit(f.getIOControls(), new Label("ltbaOn", null), SUBMISSION_ON);
		new Submit(f.getIOControls(), new Label("ltbaOff", null),
				SUBMISSION_OFF);
		new Submit(f.getIOControls(), new Label("ltbaDay", null),
				SUBMISSION_SHOW);
		new Submit(f.getIOControls(), new Label("ltbaWeek", null),
				SUBMISSION_WEEK);
		new Submit(f.getIOControls(), new Label("ltbaMonth", null),
				SUBMISSION_MONTH);

		new Submit(f.getSubmits(), new Label("Done", null), "doneForm");
		UIRequest req = new UIRequest(inputUser, f, LevelRating.none,
				Locale.ENGLISH, PrivacyLevel.insensible);
		this.sendUIRequest(req);
	}

	public void handleUIResponse(UIResponse uir) {
		if (uir != null) {
			if (SUBMISSION_ON.equals(uir.getSubmissionID())) {
				new LTBACaller(myModuleContext);
				LTBACaller.switchOn();
			} else if (SUBMISSION_OFF.equals(uir.getSubmissionID())) {
				new LTBACaller(myModuleContext);
				LTBACaller.switchOff();
			} else if (SUBMISSION_SHOW.equals(uir.getSubmissionID())) {
				myModuleContext.logDebug("To DroolsCaller in SUBMISSION_SHOW",
						null);
				new LTBACaller(myModuleContext);
				LTBACaller.printReport();
			} else if (SUBMISSION_WEEK.equals(uir.getSubmissionID())) {
				myModuleContext.logDebug("To DroolsCaller in SUBMISSION_WEEK",
						null);
				new LTBACaller(myModuleContext);
				LTBACaller.printWeek();
			} else if (SUBMISSION_MONTH.equals(uir.getSubmissionID())) {
				myModuleContext.logDebug("To DroolsCaller in SUBMISSION_MONTH",
						null);
				new LTBACaller(myModuleContext);
				LTBACaller.printMonth();
			}

			;
		}
	}

	void startMainDialog() {
	}

}
