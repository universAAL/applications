package org.universAAL.AALapplication.health.manager.ui;

import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.container.utils.LogUtils;
import org.universAAL.middleware.owl.supply.LevelRating;
import org.universAAL.middleware.ui.UIResponse;
import org.universAAL.middleware.ui.owl.PrivacyLevel;
import org.universAAL.middleware.ui.rdf.Form;
import org.universAAL.middleware.ui.rdf.Label;
import org.universAAL.middleware.ui.rdf.Submit;
import org.universAAL.ontology.profile.User;
import org.universaal.ontology.health.owl.HealthProfile;

public class MainMenu extends AbstractHealthForm{

	public MainMenu(ModuleContext context, User inputUser) {
		super(context, inputUser);
	}

		//TODO: internationalization!
		private static final String MESSAGE_LABEL = "Messages";
		private static final String MESSAGE_ICON = null;
		private static final String TREATMENT_LABEL = "Treatment";
		private static final String TREATMENT_ICON = null;
		private static final String PREFERENCES_LABEL = "Preferences";
		private static final String PREFERENCES_ICON = null;
		static final LevelRating PRIORITY = LevelRating.low;
		static final PrivacyLevel PRIVACY = PrivacyLevel.insensible;
		private static final String HOME_LABEL = "Go Back";
		private static final String HOME_ICON = null;
		private static final String MESSAGE_CMD = "openMessages";
		private static final String MEASUREMENT_LABEL = "Take a Treatment Now";
		private static final String MEASUREMENT_ICON = null;
		private static final String MEASUREMENT_CMD = "takeMeasurement";
		private static final String TREATMENT_CMD = "manageTreatments";
		private static final String PREFERENCES_CMD = "managePrefferences";
	

	@Override
	public void handleUIResponse(UIResponse input) {
		String cmd = input.getSubmissionID();
		if (cmd.equalsIgnoreCase(MESSAGE_CMD)){
			//TODO: message Managing
			new NotReady(context,inputUser).show();
		}
		if (cmd.equalsIgnoreCase(MEASUREMENT_CMD)){
			new MeasurementForm(context, inputUser).show();
		}
		if (cmd.equalsIgnoreCase(TREATMENT_CMD)){
			new TreatmentForm(context, inputUser).show();
		}
		if (cmd.equalsIgnoreCase(PREFERENCES_CMD)){
			//TODO: Prefferences Managing
			new NotReady(context, inputUser).show();
		}
	}
	
	public void show() {
		//get HealthProfile
		HealthProfile hp = getHealthProfile();
		if (hp == null){
			//WARN
			LogUtils.logError(context, getClass(), "show", "No Health Profile Found!!");
			return;
		}
		// Create Dialog
		Form f = Form.newDialog("Health Manager AAL Service", hp);
		new Submit(f.getSubmits(), 
				new Label(MESSAGE_LABEL, MESSAGE_ICON),
				MESSAGE_CMD);
		new Submit(f.getSubmits(), 
				new Label(MEASUREMENT_LABEL, MEASUREMENT_ICON),
				MEASUREMENT_CMD);
		new Submit(f.getSubmits(), 
				new Label(TREATMENT_LABEL, TREATMENT_ICON),
				TREATMENT_CMD);
		new Submit(f.getSubmits(), 
				new Label(PREFERENCES_LABEL, PREFERENCES_ICON),
				PREFERENCES_CMD);
		// add home submit
		new Submit(f.getSubmits(), new Label(HOME_LABEL, HOME_ICON), HOME_LABEL );
		
		// TODO Welcome Pane in IOControls
		
		sendForm(f);
	}

}
