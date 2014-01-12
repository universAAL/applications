package org.universAAL.AALapplication.health.manager.ui.measurements;

import org.universAAL.AALapplication.health.manager.ui.AbstractHealthForm;
import org.universAAL.AALapplication.health.manager.ui.MainMenu;
import org.universAAL.AALapplication.health.manager.ui.MeasurementTypeForm;
import org.universAAL.middleware.rdf.PropertyPath;
import org.universAAL.middleware.rdf.Resource;
import org.universAAL.middleware.service.DefaultServiceCaller;
import org.universAAL.middleware.service.ServiceCaller;
import org.universAAL.middleware.service.ServiceRequest;
import org.universAAL.middleware.ui.UIResponse;
import org.universAAL.middleware.ui.rdf.Form;
import org.universAAL.middleware.ui.rdf.InputField;
import org.universAAL.middleware.ui.rdf.Label;
import org.universAAL.middleware.ui.rdf.Submit;
import org.universAAL.ontology.health.owl.PerformedSession;
import org.universAAL.ontology.health.owl.services.PerformedSessionManagementService;
import org.universAAL.ontology.healthmeasurement.owl.HeartRate;
import org.universAAL.ontology.measurement.Measurement;
import org.universAAL.ontology.unit.DividedUnit;
import org.universAAL.ontology.unit.Unit;
import org.universAAL.ontology.unit.system.TimeSystem;
import org.universAAL.ontology.unit.system.Util;

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
	    close();
		measurement = (HeartRate) input.getSubmittedData();
		if (measurement.getProperty(Measurement.PROP_HAS_UNIT) == null){
			Unit beatsPerMinute = new DividedUnit("BPM", Util.IND_UNIT_UNITY,
		    		TimeSystem.IND_UNIT_TS_MINUTE);
			measurement.setProperty(Measurement.PROP_HAS_UNIT, beatsPerMinute);
		}
		PerformedSession ps = BloodPreasureMeasurement.getPerformedSession(measurement);
		if (input.getSubmissionID().startsWith(DONE_CMD)){
			// service call add Performed Session.
			ServiceCaller sc = new DefaultServiceCaller(owner);
			ServiceRequest sr = new ServiceRequest(new PerformedSessionManagementService(null), inputUser);
			sr.addAddEffect(new String[]{PerformedSessionManagementService.PROP_MANAGES_SESSION}, ps);
			sr.addValueFilter(new String[]{PerformedSessionManagementService.PROP_ASSISTED_USER}, targetUser);
			sc.call(sr);
			new Motivation(this).show();
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

		String[] subs = new String[]{"bpm"}; //TODO: add units from actual prefix and unit.
		
		InputField i=new InputField(f.getIOControls(), 
			new Label(getLocaleHelper().getString("heartRateMeasurement.hr",subs) , 
				null), 
				new PropertyPath(null, false, new String[]{Measurement.PROP_VALUE}),null, Integer.valueOf(90));
		i.setHelpString(getString("heartRateMeasurement.hr.help")); 
		i.setHintString(getString("heartRateMeasurement.hr.hint")); 
		new Submit(f.getSubmits(), 
				new Label(getString("heartRateMeasurement.done"), getString("heartRateMeasurement.done.icon")), 
				DONE_CMD);
		new Submit(f.getSubmits(), 
				new Label(getString("heartRateMeasurement.cancel"), getString("heartRateMeasurement.cancel.icon")), 
				CANCEL_CMD);
		
		sendForm(f);
	}

	@Override
	public void dialogAborted(String dialogID, Resource data) {
		// TODO Auto-generated method stub
		
	}
	
//	public void ShowIncorrectMessage(){
//		Form f = Form.newMessage("Heart Rate", null);
//		new SimpleOutput(f.getIOControls(), null, null, "Your Measure is not correct");
//		sendForm(f);
//		
//	}

}
