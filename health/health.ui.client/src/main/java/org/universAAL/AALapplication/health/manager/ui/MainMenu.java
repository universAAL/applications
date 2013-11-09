package org.universAAL.AALapplication.health.manager.ui;

import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.container.utils.LogUtils;
import org.universAAL.middleware.ui.UIResponse;
import org.universAAL.middleware.ui.rdf.Form;
import org.universAAL.middleware.ui.rdf.Label;
import org.universAAL.middleware.ui.rdf.Submit;
import org.universAAL.ontology.health.owl.HealthProfile;
import org.universAAL.ontology.profile.AssistedPerson;
import org.universAAL.ontology.profile.User;

public class MainMenu extends AbstractHealthForm{

	/**
	 * @param measurementTypeForm
	 */
	public MainMenu(AbstractHealthForm parent) {
		super(parent);
	}

		/**
	 * @param ctxt
	 * @param inputUser
	 */
	public MainMenu(ModuleContext ctxt, User inputUser, AssistedPerson targetUser) {
		super(ctxt, inputUser,targetUser);
	}

		private static final String MESSAGE_CMD = "openMessages"; 
		private static final String MEASUREMENT_CMD = "takeMeasurement"; 
		private static final String TREATMENT_CMD = "manageTreatments"; 
		private static final String PREFERENCES_CMD = "managePrefferences"; 
		private static final String HOME_CMD = "goHome"; 
	

	@Override
	public void handleUIResponse(UIResponse input) {
	    close();
		String cmd = input.getSubmissionID();
		LogUtils.logDebug(owner, getClass(), "handleUIResponse", "handling: " + cmd); //$NON-NLS-1$ //$NON-NLS-2$
		if (cmd.equalsIgnoreCase(MESSAGE_CMD)){
			//TODO: message Managing
			new NotReady(this).show();
		}
		if (cmd.equalsIgnoreCase(MEASUREMENT_CMD)){
			new MeasurementTypeForm(this).show();
		}
		if (cmd.equalsIgnoreCase(TREATMENT_CMD)){
			new TreatmentForm(this).show();
		}
		if (cmd.equalsIgnoreCase(PREFERENCES_CMD)){
			//TODO: Preferences Managing
			new NotReady(this).show();
		}
	}
	
	public void show() {
		if (targetUser == null){
			new AssistedPersonSelector(this).show();
		} else {
			showTarget();
		}
	}
	
	private void showTarget() {
		//get HealthProfile
		HealthProfile hp = getHealthProfile();
		if (hp == null){
			//WARN
			LogUtils.logError(owner, getClass(), "show", "No Health Profile Found!!"); //$NON-NLS-1$ //$NON-NLS-2$
			return;
		}
		// Create Dialog
		Form f = Form.newDialog(getString("mainmenu.title"), hp); //$NON-NLS-1$
		new Submit(f.getSubmits(), 
				new Label(getString("mainmenu.messages"), 
					getString("mainmenu.messages.icon")),
				MESSAGE_CMD);
		new Submit(f.getSubmits(), 
				new Label(getString("mainmenu.measurement"),
					getString("mainmenu.measurement.icon")),
				MEASUREMENT_CMD);
		new Submit(f.getSubmits(), 
				new Label(getString("mainmenu.treatment"),
					getString("mainmenu.treatment.icon")),
				TREATMENT_CMD);
		new Submit(f.getSubmits(), 
				new Label(getString("mainmenu.preferences"),
					getString("mainmenu.preferences.icon")),
				PREFERENCES_CMD);
		// add home submit
		new Submit(f.getSubmits(), 
			new Label(getString("mainmenu.back"),
					getString("mainmenu.back.icon")),
				HOME_CMD );
		
		// TODO Welcome Pane in IOControls
		
		sendForm(f);
	}

}
