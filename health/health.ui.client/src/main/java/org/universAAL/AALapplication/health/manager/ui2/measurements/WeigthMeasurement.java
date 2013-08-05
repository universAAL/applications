package org.universAAL.AALapplication.health.manager.ui2.measurements;

import org.universAAL.AALapplication.health.manager.ui2.AbstractHealthForm;
import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.owl.supply.LevelRating;
import org.universAAL.middleware.ui.UIResponse;
import org.universAAL.middleware.ui.owl.PrivacyLevel;
import org.universAAL.middleware.ui.rdf.Form;
import org.universAAL.middleware.ui.rdf.InputField;
import org.universAAL.middleware.ui.rdf.Label;
import org.universAAL.middleware.ui.rdf.SimpleOutput;
import org.universAAL.middleware.ui.rdf.Submit;
import org.universAAL.ontology.profile.User;
import org.universaal.ontology.healthmeasurement.owl.PersonWeight;

public class WeigthMeasurement extends AbstractHealthForm{

	//TODO: internationalization!
		
		static final LevelRating PRIORITY = LevelRating.low;
		static final PrivacyLevel PRIVACY = PrivacyLevel.insensible;
		private static final String DONE_ICON = null;
		private static final String DONE_LABEL = null;
		private static final String CANCEL_LABEL = null;
		private static final String CANCEL_ICON = null;
		private PersonWeight measurement;
		
	
	public WeigthMeasurement(ModuleContext context, User inpuUser, PersonWeight hm) {
		super(context,inpuUser);
		this.measurement = hm;
	}

	@Override
	public void handleUIResponse(UIResponse input) {
		// TODO Auto-generated method stub
		
	}
	
	public void show() {
		// Create Dialog
		Form f = Form.newDialog("Weight", measurement);
		//TODO: add units from actual prefix and unit.
		InputField i = new InputField(f.getIOControls(), new Label("Weight ", null),null,null, inputUser);
		i.setHelpString("Insert Your Weight in Kg.");
		i.setHintString("64.5");
		new Submit(f.getSubmits(), 
				new Label(DONE_LABEL, DONE_ICON),
				DONE_LABEL);
		new Submit(f.getSubmits(), 
				new Label(CANCEL_LABEL, CANCEL_ICON),
				CANCEL_LABEL);

		sendForm(f);
	}
	
	public void ShowUncorrectMessage(){
		Form f = Form.newMessage("Weight", null);
		new SimpleOutput(f.getIOControls(), null, null, "Your Measure is not correct");
		
		
	}
	
}
