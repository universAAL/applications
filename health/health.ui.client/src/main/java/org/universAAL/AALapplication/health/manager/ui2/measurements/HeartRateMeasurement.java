package org.universAAL.AALapplication.health.manager.ui2.measurements;

import org.universAAL.AALapplication.health.manager.ui2.AbstractHealthForm;
import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.owl.supply.LevelRating;
import org.universAAL.middleware.rdf.Resource;
import org.universAAL.middleware.ui.UIResponse;
import org.universAAL.middleware.ui.owl.PrivacyLevel;
import org.universAAL.middleware.ui.rdf.Form;
import org.universAAL.middleware.ui.rdf.InputField;
import org.universAAL.middleware.ui.rdf.Label;
import org.universAAL.middleware.ui.rdf.SimpleOutput;
import org.universAAL.middleware.ui.rdf.Submit;
import org.universAAL.ontology.profile.User;
import org.universaal.ontology.healthmeasurement.owl.HeartRate;

public class HeartRateMeasurement extends AbstractHealthForm{

	//TODO: internationalization!
		


		static final LevelRating PRIORITY = LevelRating.low;
		static final PrivacyLevel PRIVACY = PrivacyLevel.insensible;
		private static final String DONE_ICON = null;
		private static final String DONE_LABEL = null;
		private static final String CANCEL_LABEL = null;
		private static final String CANCEL_ICON = null;
		private HeartRate measurement;
		
	public HeartRateMeasurement(ModuleContext context, User inputUser, HeartRate hm) {
		super(context, inputUser);
		this.measurement = hm;
	}
	
	@Override
	public void handleUIResponse(UIResponse input) {
		// TODO Auto-generated method stub
		
	}
	
	public void show() {
		// Create Dialog
		Form f = Form.newDialog("Heart Rate", measurement);

		//TODO: add units from actual prefix and unit.
		InputField i=new InputField(f.getIOControls(), new Label("HeartRate" , null),null,null, inputUser);
		i.setHelpString("Insert Your heart rate in beats per minute.");
		i.setHintString("90");
		new Submit(f.getSubmits(), 
				new Label(DONE_LABEL, DONE_ICON),
				DONE_LABEL);
		new Submit(f.getSubmits(), 
				new Label(CANCEL_LABEL, CANCEL_ICON),
				CANCEL_LABEL);
		
		sendForm(f);
	}
	
	public void ShowUncorrectMessage(){
		Form f = Form.newMessage("Heart Rate", null);
		new SimpleOutput(f.getIOControls(), null, null, "Your Measure is not correct");
		
		
	}
	
	public void checkUserInput (Resource i){
		//TODO Check InputField is a correct number
	}

}
