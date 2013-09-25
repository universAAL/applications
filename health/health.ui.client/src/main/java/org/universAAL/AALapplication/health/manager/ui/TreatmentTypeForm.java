/**
 * 
 */
package org.universAAL.AALapplication.health.manager.ui;

import java.util.Set;

import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.owl.OntologyManagement;
import org.universAAL.middleware.rdf.PropertyPath;
import org.universAAL.middleware.rdf.Resource;
import org.universAAL.middleware.ui.UIResponse;
import org.universAAL.middleware.ui.rdf.ChoiceItem;
import org.universAAL.middleware.ui.rdf.Form;
import org.universAAL.middleware.ui.rdf.Label;
import org.universAAL.middleware.ui.rdf.Select1;
import org.universAAL.middleware.ui.rdf.Submit;
import org.universAAL.ontology.health.owl.HealthProfileOntology;
import org.universAAL.ontology.health.owl.Treatment;
import org.universAAL.ontology.healthmeasurement.owl.HealthMeasurement;
import org.universAAL.ontology.profile.User;

/**
 * @author amedrano
 *
 */
public class TreatmentTypeForm extends AbstractHealthForm {
	
	private static final String CANCEL_LABEL = "Cancel";
	private static final String SELECTED_TREATMENT = HealthProfileOntology.NAMESPACE + "uiTreatmentSelected";
	private static final String OK_ICON = null;
	private static final String OK_LABEL = null;
	private static final String CANCELL_ICON = null;

	/**
	 * @param context
	 * @param inputUser
	 */
	public TreatmentTypeForm(ModuleContext context, User inputUser) {
		super(context, inputUser);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see org.universAAL.middleware.ui.UICaller#handleUIResponse(org.universAAL.middleware.ui.UIResponse)
	 */
	@Override
	public void handleUIResponse(UIResponse arg0) {
		Resource res = (Resource) arg0.getUserInput(new String[]{SELECTED_TREATMENT});
		if (arg0.getSubmissionID().startsWith(OK_LABEL)){
			//TODO service call
//			if (res)
		}
	}
	
	public void show(){
		Form f = Form.newDialog("New Treatment: Which Type?", new Resource());
		
		Set<String> typesOfTreatment = OntologyManagement
				.getInstance().getNamedSubClasses(Treatment.MY_URI, true, false);
		
		Select1 s = new Select1(f.getIOControls(),
				new Label("Select Measurement Type", null),
				new PropertyPath(SELECTED_TREATMENT, false),
				null, null);
		
		for (String type : typesOfTreatment) {
			Treatment t = (Treatment) OntologyManagement
					.getInstance().getResource(type, Resource.generateAnonURI());
			String name = t.getOrConstructLabel(t.getClassURI());
			s.addChoiceItem(new ChoiceItem(name, null, t));
		}
		
		new Submit(f.getSubmits(), 
				new Label(OK_LABEL, OK_ICON),
				OK_LABEL);
		
		Submit su = new Submit(f.getSubmits(),
				new Label(CANCEL_LABEL, CANCELL_ICON),
				CANCEL_LABEL );
		
		su.addMandatoryInput(s);
	}

}
