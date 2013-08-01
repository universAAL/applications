

/*******************************************************************************
 * Copyright 2011 Universidad Polit�cnica de Madrid
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package org.universAAL.AALapplication.health.manager.ui2;

import java.util.Locale;

import org.universAAL.AALapplication.health.manager.ui.InputListener;
import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.owl.supply.LevelRating;
import org.universAAL.middleware.rdf.PropertyPath;
import org.universAAL.middleware.rdf.Resource;
import org.universAAL.middleware.ui.UICaller;
import org.universAAL.middleware.ui.UIRequest;
import org.universAAL.middleware.ui.UIResponse;
import org.universAAL.middleware.ui.owl.PrivacyLevel;
import org.universAAL.middleware.ui.rdf.Form;
import org.universAAL.middleware.ui.rdf.Group;
import org.universAAL.middleware.ui.rdf.InputField;
import org.universAAL.middleware.ui.rdf.Label;
import org.universAAL.middleware.ui.rdf.Repeat;
import org.universAAL.middleware.ui.rdf.SimpleOutput;
import org.universAAL.middleware.ui.rdf.SubdialogTrigger;
import org.universAAL.middleware.ui.rdf.Submit;
import org.universaal.ontology.health.owl.HealthProfile;
import org.universaal.ontology.health.owl.Treatment;

/**
 * @author amedrano
 *
 */
public class TreatmentViewForm extends UICaller {

	private static final String DELETE_ICON = null;
	private static final String DELETE_LABEL = null;
	private static final String EDIT_LABEL = null;
	private static final String EDIT_ICON = null;
	private static final String BACK_LABEL = null;
	private static final String BACK_ICON = null;

	protected TreatmentViewForm(ModuleContext context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see org.universAAL.AALapplication.health.manager.ui.InputListener#getDialog()
	 */
	
	

	

	public void show(Resource inputUser, Treatment treat) {
		
		// Create Dialog
		Form f = Form.newDialog("Treatment Details", treat);
		
		Group details= new Group(f.getIOControls(), new Label("Details", null), null, null, null);

		InputField  i = new InputField(details, new Label("Name", null), new PropertyPath(null, false, 
				new String [] {Treatment.PROP_NAME}), null, "treatment Name");
		i.setHelpString("input a Treatment name");
		i.setHintString("take medicine");
		
		InputField d= new InputField(details, new Label("Description", null), new PropertyPath(null, false, 
				new String [] {Treatment.PROP_DESCRIPTION}), null, "treatment description");
		d.setHelpString("input a description");
		d.setHintString("Treatment description");
		
		Repeat r = new Repeat(f.getIOControls(), new Label("Treatments", null),
				new PropertyPath(null, false, new String[] {HealthProfile.PROP_HAS_TREATMENT}),
				null, null);
		
		Group sesions=new Group(r, new Label("Sesions", null), 
				null, null, null);
		new SimpleOutput(sesions, new Label("Name", null), 
				new PropertyPath(null, false, new String[] {Treatment.PROP_NAME}), null);
		
	
		new SimpleOutput(sesions, new Label("Performed", null), 
				new PropertyPath(null, false, new String[] 
						{Treatment.PROP_HAS_PERFORMED_SESSION}), null);
		
		Group nextSession = new Group(f.getIOControls(),new Label ( "Next Session",null),
				null,null, null);
		
		new SimpleOutput(nextSession, null, new PropertyPath(null, false, 
			new String [] {Treatment.PROP_HAS_TREATMENT_PLANNING}), null);
		new Submit(nextSession, new Label("Perform now", null), "Now");
		
		new SubdialogTrigger(f.getSubmits(), 
				new Label(DELETE_LABEL, DELETE_ICON),
				DELETE_LABEL);
		new SubdialogTrigger(f.getSubmits(), 
				new Label(EDIT_LABEL, EDIT_ICON),
			EDIT_LABEL);
		
		// add home submit
		new Submit(f.getSubmits(), new Label(BACK_LABEL, BACK_ICON), BACK_LABEL );
		
		
		sendUIRequest(new UIRequest(inputUser, f, LevelRating.low, Locale.ENGLISH, PrivacyLevel.insensible));
		
		
		
	}

	@Override
	public void communicationChannelBroken() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dialogAborted(String arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handleUIResponse(UIResponse arg0) {
		// TODO Auto-generated method stub
		
	}
}
