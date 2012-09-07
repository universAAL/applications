package org.universAAL.AALapplication.health.manager.ui2;

import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.owl.supply.LevelRating;
import org.universAAL.middleware.rdf.Resource;
import org.universAAL.middleware.ui.UICaller;
import org.universAAL.middleware.ui.UIResponse;
import org.universAAL.middleware.ui.owl.PrivacyLevel;
import org.universAAL.middleware.ui.rdf.Form;
import org.universAAL.middleware.ui.rdf.Label;
import org.universAAL.middleware.ui.rdf.SubdialogTrigger;
import org.universAAL.middleware.ui.rdf.Submit;
import org.universaal.ontology.health.owl.HealthProfile;

public class MainMenu extends UICaller{

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
	
	protected MainMenu(ModuleContext context) {
		super(context);
	}

	@Override
	public void communicationChannelBroken() {
		// nothing
	}

	@Override
	public void dialogAborted(String dialogID) {
		// nothing
		
	}

	@Override
	public void handleUIResponse(UIResponse input) {
		// TODO Auto-generated method stub
		
	}
	
	public void show(Resource inputUser) {
		//get HealthProfile
		HealthProfile hp;
		hp = null;
		// Create Dialog
		Form f = Form.newDialog("Health Manager AAL Service", hp);
		new SubdialogTrigger(f.getSubmits(), 
				new Label(MESSAGE_LABEL, MESSAGE_ICON),
				MESSAGE_LABEL);
		new SubdialogTrigger(f.getSubmits(), 
				new Label(TREATMENT_LABEL, TREATMENT_ICON),
				TREATMENT_LABEL);
		new SubdialogTrigger(f.getSubmits(), 
				new Label(PREFERENCES_LABEL, PREFERENCES_ICON),
				PREFERENCES_LABEL);
		// add home submit
		new Submit(f.getSubmits(), new Label(HOME_LABEL, HOME_ICON), HOME_LABEL );
		// TODO Welcome Pane in IOControls
	}

}
