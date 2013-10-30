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
import org.universAAL.ontology.healthmeasurement.owl.PersonWeight;
import org.universAAL.ontology.measurement.Measurement;

public class WeigthMeasurement extends AbstractHealthForm{

		private static final String DONE_CMD = "done"; 
		private static final String CANCEL_CMD = "Cancel"; 
		private PersonWeight measurement;
		
	
	public WeigthMeasurement(AbstractHealthForm ahf, PersonWeight hm) {
		super(ahf);
		this.measurement = hm;
	}

	@Override
	public void handleUIResponse(UIResponse input) {
		measurement = (PersonWeight) input.getSubmittedData();
		if (input.getSubmissionID().startsWith(DONE_CMD)){
			// service call add Performed Session.
			ServiceCaller sc = new DefaultServiceCaller(owner);
			ServiceRequest sr = new ServiceRequest(new PerformedSessionManagementService(null), inputUser);
			sr.addAddEffect(new String[]{PerformedSessionManagementService.PROP_MANAGES_SESSION}, measurement);
			sr.addValueFilter(new String[]{PerformedSessionManagementService.PROP_ASSISTED_USER}, targetUser);
			sc.call(sr);
			new MainMenu(this).show();
		}
		if (input.getSubmissionID().startsWith(CANCEL_CMD)){
			//Back
			new MeasurementTypeForm(this).show();
		}
	}
	
	public void show() {
		// Create Dialog
		Form f = Form.newDialog(getString("weigthMeasurement.title"), measurement); 
		
		String[] subs = new String[]{"Kg"}; //TODO: add units from actual prefix and unit.
		
		InputField i = new InputField(f.getIOControls(), 
			new Label(getLocaleHelper().getString("weigthMeasurement.input",subs), null), 
				new PropertyPath(null, false, new String[]{Measurement.PROP_VALUE}),null, Float.valueOf(64.5f));
		i.setHelpString(getString("weigthMeasurement.input.help")); 
		i.setHintString(getString("weigthMeasurement.input.hint")); 
		new Submit(f.getSubmits(), 
				new Label(getString("weigthMeasurement.done"), getString("weigthMeasurement.done.icon")),  
				DONE_CMD);
		new Submit(f.getSubmits(), 
				new Label(getString("weigthMeasurement.cancel"), getString("weigthMeasurement.cancel.icon")),  
				CANCEL_CMD);

		sendForm(f);
	}
	
//	public void ShowIncorrectMessage(){
//		Form f = Form.newMessage("Weight", null);
//		new SimpleOutput(f.getIOControls(), null, null, "Your Measure is not correct");
//		sendForm(f);		
//	}
	
}
