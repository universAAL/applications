package org.universAAL.AALapplication.health.manager.ui.measurements;

import org.universAAL.AALapplication.health.manager.ui.AbstractHealthForm;
import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.owl.supply.LevelRating;
import org.universAAL.middleware.rdf.PropertyPath;
import org.universAAL.middleware.rdf.Resource;
import org.universAAL.middleware.ui.UIResponse;
import org.universAAL.middleware.ui.owl.PrivacyLevel;
import org.universAAL.middleware.ui.rdf.Form;
import org.universAAL.middleware.ui.rdf.InputField;
import org.universAAL.middleware.ui.rdf.Label;
import org.universAAL.middleware.ui.rdf.SimpleOutput;
import org.universAAL.middleware.ui.rdf.Submit;
import org.universAAL.ontology.healthmeasurement.owl.BloodPressure;
import org.universAAL.ontology.measurement.Measurement;
import org.universAAL.ontology.profile.User;

public class BloodPreasureMeasurement extends AbstractHealthForm{


	//TODO: internationalization!
		
		static final LevelRating PRIORITY = LevelRating.low;
		static final PrivacyLevel PRIVACY = PrivacyLevel.insensible;
		private static final String DONE_ICON = null;
		private static final String DONE_LABEL = "Done";
		private static final String CANCEL_LABEL = null;
		private static final String CANCEL_ICON = "Cancel";
		private BloodPressure measurement;
		
	
	public BloodPreasureMeasurement(AbstractHealthForm ahf, BloodPressure hm) {
		super(ahf);
		this.measurement = hm;
	}

	

	public void handleUIResponse(UIResponse input) {
		// TODO Auto-generated method stub
		
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
				new Label(CANCEL_LABEL, CANCEL_ICON),
				CANCEL_LABEL);
		
		sendForm(f);
	}
	
	public void ShowIncorrectMessage(){
		Form f = Form.newMessage("Blood Preasure", null);
		new SimpleOutput(f.getIOControls(), null, null, "Your Measure is not correct");
		sendForm(f);
	}

	
	public boolean checkUserInput (Resource i){
		//TODO Check InputField is a correct number
		while (i.isWellFormed()){
			return true;
			}
		ShowIncorrectMessage();
		return false;
		}
	
}


	


