/**
 * 
 */
package org.universAAL.AALapplication.health.manager.ui;

import java.util.Set;

import org.universAAL.middleware.owl.OntologyManagement;
import org.universAAL.middleware.rdf.PropertyPath;
import org.universAAL.middleware.rdf.Resource;
import org.universAAL.middleware.service.DefaultServiceCaller;
import org.universAAL.middleware.service.ServiceCaller;
import org.universAAL.middleware.service.ServiceRequest;
import org.universAAL.middleware.ui.UIResponse;
import org.universAAL.middleware.ui.rdf.ChoiceItem;
import org.universAAL.middleware.ui.rdf.Form;
import org.universAAL.middleware.ui.rdf.Label;
import org.universAAL.middleware.ui.rdf.Select1;
import org.universAAL.middleware.ui.rdf.Submit;
import org.universAAL.ontology.health.owl.HealthProfileOntology;
import org.universAAL.ontology.health.owl.Treatment;
import org.universAAL.ontology.health.owl.services.DisplayTreatmentService;

/**
 * @author amedrano
 *
 */
public class TreatmentTypeForm extends AbstractHealthForm {
	
	/**
	 * @param ahf
	 */
	protected TreatmentTypeForm(AbstractHealthForm ahf) {
		super(ahf);
	}

	private static final String SELECTED_TREATMENT = HealthProfileOntology.NAMESPACE + "uiTreatmentSelected"; 
	private static final String OK_CMD = "ok"; 
	private static final String CANCEL_CMD = "cancel"; 

	/* (non-Javadoc)
	 * @see org.universAAL.middleware.ui.UICaller#handleUIResponse(org.universAAL.middleware.ui.UIResponse)
	 */
	@Override
	public void handleUIResponse(UIResponse arg0) {
	    close();
		Resource res = (Resource) arg0.getUserInput(new String[]{SELECTED_TREATMENT});
		if (arg0.getSubmissionID().startsWith(OK_CMD)){
			// service call the most specific DisplayTreatmentService available.
			ServiceCaller sc = new DefaultServiceCaller(owner);
			ServiceRequest sr = new ServiceRequest(new DisplayTreatmentService(null), inputUser);
			sr.addAddEffect(new String[]{DisplayTreatmentService.PROP_TREATMENT}, res);
			sr.addValueFilter(new String[]{DisplayTreatmentService.PROP_AFFECTED_USER}, targetUser);
			sc.call(sr);
		}
		if (arg0.getSubmissionID().startsWith(CANCEL_CMD)){
			//Back
			new TreatmentForm(this).show();
		}
	}
	
	public void show(){
		Form f = Form.newDialog(getString("treatmentType.title"), new Resource()); 
		
		Set<String> typesOfTreatment = OntologyManagement
				.getInstance().getNamedSubClasses(Treatment.MY_URI, true, false);
		
		Select1 s = new Select1(f.getIOControls(),
				new Label(getString("treatmentType.select"), null), 
				new PropertyPath(null, false, new String[]{SELECTED_TREATMENT}),
				null, null);
		
		for (String type : typesOfTreatment) {
			Treatment t = (Treatment) OntologyManagement
					.getInstance().getResource(type, Resource.generateAnonURI());
			String name = t.getOrConstructLabel(null);
			s.addChoiceItem(new ChoiceItem(name, null, t));
		}
		
		new Submit(f.getSubmits(), 
				new Label(getString("treatmentType.ok"), getString("treatmentType.ok.icon")), 
				OK_CMD);
		
		Submit su = new Submit(f.getSubmits(),
				new Label(getString("treatmentType.back"), getString("treatmentType.back.icon")), 
				CANCEL_CMD );
		
		su.addMandatoryInput(s);
		
		sendForm(f);
	}

	@Override
	public void dialogAborted(String dialogID, Resource data) {
		// TODO Auto-generated method stub
		
	}

}
