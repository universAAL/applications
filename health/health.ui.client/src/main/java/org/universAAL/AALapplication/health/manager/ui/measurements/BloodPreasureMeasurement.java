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
import org.universAAL.ontology.healthmeasurement.owl.BloodPressure;
import org.universAAL.ontology.measurement.Measurement;

public class BloodPreasureMeasurement extends AbstractHealthForm {

    private static final String DONE_CMD = "done"; 
    private static final String CANCEL_CMD = "cancel"; 
    private static final String HELP_CMD = "help"; 
    private BloodPressure measurement;

    public BloodPreasureMeasurement(AbstractHealthForm ahf, BloodPressure hm) {
	super(ahf);
	this.measurement = hm;
    }

    public void handleUIResponse(UIResponse input) {
	measurement = (BloodPressure) input.getSubmittedData();
	if (input.getSubmissionID().startsWith(DONE_CMD)) {
	    // service call add Performed Session.
	    ServiceCaller sc = new DefaultServiceCaller(owner);
	    ServiceRequest sr = new ServiceRequest(
		    new PerformedSessionManagementService(null), inputUser);
	    sr.addAddEffect(
		    new String[] { PerformedSessionManagementService.PROP_MANAGES_SESSION },
		    measurement);
	    sr.addValueFilter(
		    new String[] { PerformedSessionManagementService.PROP_ASSISTED_USER },
		    targetUser);
	    sc.call(sr);
	    new Motivation(this).show();
	    new MainMenu(this).show();
	}
	if (input.getSubmissionID().startsWith(CANCEL_CMD)) {
	    // Back
	    new MeasurementTypeForm(this).show();
	}
	if (input.getSubmissionID().startsWith(HELP_CMD)) {
	    // show help
	    new HowToBloodPreasure(this).show();
	}
    }

    public void show() {
	// Create Dialog
	Form f = Form.newDialog(getString("bloodPressureMeasurement.title"), measurement); 

	String[] subs = new String[]{"mmHg"}; //TODO find the units.
	
	InputField s = new InputField(f.getIOControls(), new Label(
		getLocaleHelper().getString("bloodPressureMeasurement.systolic",subs), null),
		new PropertyPath(null, 
		false, new String[] { BloodPressure.PROP_SYSTOLIC,
			Measurement.PROP_VALUE }), null, Integer.valueOf(100));
	s.setHelpString(getString("bloodPressureMeasurement.systolic.help")); 
	s.setHintString(getString("bloodPressureMeasurement.systolic.hint")); 

	
	InputField d = new InputField(f.getIOControls(), new Label(
		getLocaleHelper().getString("bloodPressureMeasurement.diastolic",subs),
		null), new PropertyPath(null, 
		false, new String[] { BloodPressure.PROP_DIASTOLIC,
			Measurement.PROP_VALUE }), null, Integer.valueOf(70));
	d.setHelpString(getString("bloodPressureMeasurement.diastolic.help")); 
	d.setHintString(getString("bloodPressureMeasurement.diastolic.hint")); 

	new Submit(f.getSubmits(), 
		new Label(getString("bloodPressureMeasurement.done"), 
			getString("bloodPressureMeasurement.done.icon")),
			DONE_CMD); 
	new Submit(f.getSubmits(), new Label(getString("bloodPressureMeasurement.howto"),
			getString("bloodPressureMeasurement.howto.icon")),
			HELP_CMD); 
	new Submit(f.getSubmits(), new Label(getString("bloodPressureMeasurement.cancel"), 
			getString("bloodPressureMeasurement.cancel.icon")), 
			CANCEL_CMD); 
	sendForm(f);
    }

    // public void ShowIncorrectMessage(){
    // Form f = Form.newMessage("Blood Preasure", null);
    // new SimpleOutput(f.getIOControls(), null, null,
    // "Your Measure is not correct");
    // sendForm(f);
    // }

}
