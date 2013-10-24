package org.universAAL.AALapplication.health.manager.ui.measurements;

import org.universAAL.AALapplication.health.manager.ui.AbstractHealthForm;
import org.universAAL.AALapplication.health.manager.ui.MainMenu;
import org.universAAL.AALapplication.health.manager.ui.MeasurementTypeForm;
import org.universAAL.middleware.owl.supply.LevelRating;
import org.universAAL.middleware.rdf.PropertyPath;
import org.universAAL.middleware.service.DefaultServiceCaller;
import org.universAAL.middleware.service.ServiceCaller;
import org.universAAL.middleware.service.ServiceRequest;
import org.universAAL.middleware.ui.UIResponse;
import org.universAAL.middleware.ui.owl.PrivacyLevel;
import org.universAAL.middleware.ui.rdf.Form;
import org.universAAL.middleware.ui.rdf.InputField;
import org.universAAL.middleware.ui.rdf.Label;
import org.universAAL.middleware.ui.rdf.Submit;
import org.universAAL.ontology.health.owl.services.PerformedSessionManagementService;
import org.universAAL.ontology.healthmeasurement.owl.BloodPressure;
import org.universAAL.ontology.measurement.Measurement;

public class BloodPreasureMeasurement extends AbstractHealthForm{


	//TODO: internationalization!
		
		static final LevelRating PRIORITY = LevelRating.low;
		static final PrivacyLevel PRIVACY = PrivacyLevel.insensible;
		private static final String DONE_ICON = null;
		private static final String DONE_LABEL = "Done";
		private static final String CANCEL_LABEL = null;
		private static final String CANCEL_ICON = "Cancel";
		private static final String HELP_ICON = null;
		private static final String HELP_LABEL = "How to";
		private BloodPressure measurement;
		
	
	public BloodPreasureMeasurement(AbstractHealthForm ahf, BloodPressure hm) {
		super(ahf);
		this.measurement = hm;
	}

	

	public void handleUIResponse(UIResponse input) {
		measurement = (BloodPressure) input.getSubmittedData();
		if (input.getSubmissionID().startsWith(DONE_LABEL)){
			// service call add Performed Session.
			ServiceCaller sc = new DefaultServiceCaller(owner);
			ServiceRequest sr = new ServiceRequest(new PerformedSessionManagementService(null), inputUser);
			sr.addAddEffect(new String[]{PerformedSessionManagementService.PROP_MANAGES_SESSION}, measurement);
			sr.addValueFilter(new String[]{PerformedSessionManagementService.PROP_ASSISTED_USER}, targetUser);
			sc.call(sr);
			new MainMenu(this).show();
		}
		if (input.getSubmissionID().startsWith(CANCEL_LABEL)){
			//Back
			new MeasurementTypeForm(this).show();
		}
		if (input.getSubmissionID().startsWith(HELP_LABEL)){
			//show help
			new HowToBloodPreasure(this).show();
		}
	}
	
	public void show() {
		// Create Dialog
		Form f = Form.newDialog("Blood Preasure", measurement);
		
		InputField s = new InputField(
				f.getIOControls(), new Label("Systolic preassure (mmHg)", null),
				new PropertyPath(null, false, new String[]{BloodPressure.PROP_SYSTOLIC, Measurement.PROP_VALUE}),
				null, Integer.valueOf(100));
		s.setHelpString("Systolic Preasure, Marked as SYS, it is the maximum preassure reading.");
		s.setHintString("100");
		
		InputField d = new InputField(
				f.getIOControls(), new Label("Diastolic preassure (mmHg)", null),
				new PropertyPath(null, false, new String[]{BloodPressure.PROP_DIASTOLIC, Measurement.PROP_VALUE}),null, Integer.valueOf(70));
		d.setHelpString("Diastolic Preasure, Marked as DIA, it is the minimum preassure reading.");
		d.setHintString("70");
		
		new Submit(f.getSubmits(), 
				new Label(DONE_LABEL, DONE_ICON),
				DONE_LABEL);
		new Submit(f.getSubmits(), 
						new Label(HELP_LABEL, HELP_ICON),
						HELP_LABEL);
		new Submit(f.getSubmits(), 
				new Label(CANCEL_LABEL, CANCEL_ICON),
				CANCEL_LABEL);
		sendForm(f);
	}
	
//	public void ShowIncorrectMessage(){
//		Form f = Form.newMessage("Blood Preasure", null);
//		new SimpleOutput(f.getIOControls(), null, null, "Your Measure is not correct");
//		sendForm(f);
//	}
	
}


	


