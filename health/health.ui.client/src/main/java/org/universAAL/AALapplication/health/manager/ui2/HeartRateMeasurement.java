package org.universAAL.AALapplication.health.manager.ui2;

import java.util.Locale;

import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.owl.supply.LevelRating;
import org.universAAL.middleware.rdf.Resource;
import org.universAAL.middleware.ui.UICaller;
import org.universAAL.middleware.ui.UIRequest;
import org.universAAL.middleware.ui.UIResponse;
import org.universAAL.middleware.ui.owl.PrivacyLevel;
import org.universAAL.middleware.ui.rdf.Form;
import org.universAAL.middleware.ui.rdf.InputField;
import org.universAAL.middleware.ui.rdf.Label;
import org.universAAL.middleware.ui.rdf.SimpleOutput;
import org.universAAL.middleware.ui.rdf.Submit;
import org.universaal.ontology.health.owl.HealthProfile;

public class HeartRateMeasurement extends UICaller{

	//TODO: internationalization!
		
		static final LevelRating PRIORITY = LevelRating.low;
		static final PrivacyLevel PRIVACY = PrivacyLevel.insensible;
		private static final String DONE_ICON = null;
		private static final String DONE_LABEL = null;
		private static final String CANCEL_LABEL = null;
		private static final String CANCEL_ICON = null;
		
	
	public HeartRateMeasurement(ModuleContext context) {
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
		Form f = Form.newDialog("Heart Rate", hp);
		
		InputField i=new InputField(f.getIOControls(), new Label("puls/min", null),null,null, inputUser);
		new Submit(f.getSubmits(), 
				new Label(DONE_LABEL, DONE_ICON),
				DONE_LABEL);
		new Submit(f.getSubmits(), 
				new Label(CANCEL_LABEL, CANCEL_ICON),
				CANCEL_LABEL);
		

		
		
		this.sendUIRequest(new UIRequest(inputUser, 
				f, LevelRating.low, Locale.ENGLISH, PrivacyLevel.insensible));
	}
	
	public void ShowUncorrectMessage(){
		Form f = Form.newMessage("Heart Rate", null);
		new SimpleOutput(f.getIOControls(), null, null, "Your Measure is not correct");
		
		
	}
	
	public void checkUserInput (Resource i){
		//TODO Check InputField is a correct number
	}

}
