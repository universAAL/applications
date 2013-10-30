package org.universAAL.AALapplication.health.manager.ui.measurements;

import org.universAAL.AALapplication.health.manager.ui.AbstractHealthForm;
import org.universAAL.AALapplication.health.manager.ui.MainMenu;
import org.universAAL.AALapplication.health.manager.ui.MeasurementTypeForm;
import org.universAAL.middleware.rdf.PropertyPath;
import org.universAAL.middleware.service.DefaultServiceCaller;
import org.universAAL.middleware.service.ServiceCaller;
import org.universAAL.middleware.service.ServiceRequest;
import org.universAAL.middleware.ui.UIResponse;
import org.universAAL.middleware.ui.rdf.Form;
import org.universAAL.middleware.ui.rdf.InputField;
import org.universAAL.middleware.ui.rdf.Label;
import org.universAAL.middleware.ui.rdf.Submit;
import org.universAAL.ontology.health.owl.services.PerformedSessionManagementService;
import org.universAAL.ontology.healthmeasurement.owl.HeartRate;
import org.universAAL.ontology.measurement.Measurement;

public class HeartRateMeasurement extends AbstractHealthForm{

		private static final String DONE_CMD = "done"; 
		private static final String CANCEL_CMD = "cancel"; 
		private HeartRate measurement;
		
	public HeartRateMeasurement(AbstractHealthForm ahf, HeartRate hm) {
		super(ahf);
		this.measurement = hm;
	}
	
	@Override
	public void handleUIResponse(UIResponse input) {
		measurement = (HeartRate) input.getSubmittedData();
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
		Form f = Form.newDialog(getString("heartRateMeasurement.title"), measurement); 

		//TODO: add units from actual prefix and unit.
		InputField i=new InputField(f.getIOControls(), new Label(getString("heartRateMeasurement.hr") , null), 
				new PropertyPath(null, false, new String[]{Measurement.PROP_VALUE}),null, Integer.valueOf(90));
		i.setHelpString(getString("heartRateMeasurement.hr.help")); 
		i.setHintString(getString("heartRateMeasurement.hr.hint")); 
		new Submit(f.getSubmits(), 
				new Label(getString("heartRateMeasurement.done"), getString("heartRateMeasurement.done.icon")),  //$NON-NLS-2$
				DONE_CMD);
		new Submit(f.getSubmits(), 
				new Label(getString("heartRateMeasurement.cancel"), getString("heartRateMeasurement.cancel.icon")),  //$NON-NLS-2$
				CANCEL_CMD);
		
		sendForm(f);
	}
	
//	public void ShowIncorrectMessage(){
//		Form f = Form.newMessage("Heart Rate", null);
//		new SimpleOutput(f.getIOControls(), null, null, "Your Measure is not correct");
//		sendForm(f);
//		
//	}

}
