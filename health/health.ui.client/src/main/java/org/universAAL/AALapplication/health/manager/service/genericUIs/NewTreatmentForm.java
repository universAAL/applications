/*******************************************************************************
 * Copyright 2013 Universidad Politécnica de Madrid
 * Copyright 2013 Fraunhofer-Gesellschaft - Institute for Computer Graphics Research
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

package org.universAAL.AALapplication.health.manager.service.genericUIs;

import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.universAAL.AALapplication.health.manager.ui.AbstractHealthForm;
import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.rdf.PropertyPath;
import org.universAAL.middleware.service.DefaultServiceCaller;
import org.universAAL.middleware.service.ServiceRequest;
import org.universAAL.middleware.ui.UIResponse;
import org.universAAL.middleware.ui.rdf.Form;
import org.universAAL.middleware.ui.rdf.Group;
import org.universAAL.middleware.ui.rdf.InputField;
import org.universAAL.middleware.ui.rdf.Label;
import org.universAAL.middleware.ui.rdf.Submit;
import org.universAAL.middleware.ui.rdf.TextArea;
import org.universAAL.ontology.health.owl.HealthProfile;
import org.universAAL.ontology.health.owl.Treatment;
import org.universAAL.ontology.health.owl.TreatmentPlanning;
import org.universAAL.ontology.health.owl.services.DisplayTreatmentService;
import org.universAAL.ontology.health.owl.services.ProfileManagementService;
import org.universAAL.ontology.profile.AssistedPerson;
import org.universAAL.ontology.profile.Caregiver;
import org.universAAL.ontology.profile.User;
import org.universAAL.ontology.recommendations.VerticalLayout;

public class NewTreatmentForm extends AbstractHealthForm{

	/**
	 * @param ahf
	 */
	protected NewTreatmentForm(AbstractHealthForm ahf) {
		super(ahf);
	}

	/**
	 * @param context
	 * @param inputUser
	 * @param targetUser
	 */
	public NewTreatmentForm(ModuleContext context, User inputUser,
			AssistedPerson targetUser) {
		super(context, inputUser, targetUser);
	}

	protected static final String NEW_CMD = "addNewTreatment"; 
	protected static final String BACK_CMD = "goBack"; 



	/** {@ inheritDoc}	 */
	@Override
	public void handleUIResponse(UIResponse uiResponse) {
	    close();
		String cmd = uiResponse.getSubmissionID();
		if (cmd.startsWith(NEW_CMD)){
			Treatment t = (Treatment) uiResponse.getSubmittedData();
			if (inputUser instanceof Caregiver){
				t.setCaregiver((Caregiver) inputUser);
			}
			// Call add Treatment
//			ServiceRequest sr = new ServiceRequest(new TreatmentManagementService(null), inputUser);
//			sr.addAddEffect(new String[]{TreatmentManagementService.PROP_MANAGES_TREATMENT}, t);
////			sr.addValueFilter(new String[]{TreatmentManagementService.PROP_ASSISTED_USER}, targetUser);
			
			// alternatively udpate the health profile
			HealthProfile hp = getHealthProfile();
			hp.addTreatment(t);
			ServiceRequest sr = new ServiceRequest(new ProfileManagementService(null), inputUser);
			sr.addChangeEffect(new String[]{ProfileManagementService.PROP_ASSISTED_USER_PROFILE}, hp);
			new DefaultServiceCaller(owner).call(sr);
		}
		//Call list treatment Service
		ServiceRequest sr = new ServiceRequest(new DisplayTreatmentService(null), inputUser);
		sr.addValueFilter(new String[]{DisplayTreatmentService.PROP_AFFECTED_USER}, targetUser);
		new DefaultServiceCaller(owner).call(sr);
		
	}
	
	public void show(Treatment t){
		Form f = getGenericTreatmentForm(t, this);
		new Submit(f.getSubmits(), new Label(getString("generic.newTreatmentadd"), null), NEW_CMD); 
		new Submit(f.getSubmits(), new Label(getString("generic.newTreatmentback"), null), BACK_CMD); 
		sendForm(f);
	}
	
	public static Form getGenericTreatmentForm(Treatment t, AbstractHealthForm ahf){
	    //TODO internationalized icons
		Form f = Form.newDialog(ahf.getString("generic.newTreatmenttitle"), t); 
		f.getIOControls().addAppearanceRecommendation(new VerticalLayout());
		
		InputField nf = new InputField(f.getIOControls(), 
				new Label(ahf.getString("generic.newTreatmentname"), null), 
				new PropertyPath(null, false, new String[]{Treatment.PROP_NAME}),
				null,
				""); 
		nf.setAlertString(ahf.getString("generic.newTreatmentname.alert")); 
		nf.setHelpString(ahf.getString("generic.newTreatmentname.help")); 
		nf.setHintString(ahf.getString("generic.newTreatmentbane.hint")); 
		
		TextArea df = new TextArea(f.getIOControls(), 
				new Label(ahf.getString("generic.newTreatmentdescription"), null), 
				new PropertyPath(null, false, new String[]{Treatment.PROP_DESCRIPTION}),
				null,
				""); 
		df.setAlertString(ahf.getString("generic.newTreatmentdescription.alert")); 
		df.setHelpString(ahf.getString("generic.newTreatmentdescription.help")); 
		df.setHintString(ahf.getString("generic.newTreatmentdescription.hint")); 
		
		//TODO conditional Treatment planning
		//TODO add complex planning subdialogtrigger + subdialog
		Group simplePlanning = new Group(f.getIOControls(), new Label(ahf.getString("generic.newTreatmentgroup"), null), null, null, null); 
		
		GregorianCalendar c = new GregorianCalendar();
		c.setTime(new Date());
		XMLGregorianCalendar date2 = null;
		try {
			date2 = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
		} catch (DatatypeConfigurationException e) {
			//LogUtils.logError(Owner, , "getGenericTreatmentForm", new String[]{"unable to create current time"}, e);
			e.printStackTrace();
		}
		
		if (t.getTreatmentPlanning() == null){
			t.setTreatmentPlanning(new TreatmentPlanning(date2, date2));
		}
		
		InputField start = new InputField(simplePlanning, new Label(ahf.getString("generic.newTreatmentstart"), null),  
				new PropertyPath(null, false,  new String[]{
			Treatment.PROP_HAS_TREATMENT_PLANNING,
			TreatmentPlanning.PROP_START_DATE
		}), null, date2);
		
		InputField end = new InputField(simplePlanning, new Label(ahf.getString("generic.newTreatmentend"), null),  
				new PropertyPath(null, false,  new String[]{
			Treatment.PROP_HAS_TREATMENT_PLANNING,
			TreatmentPlanning.PROP_END_DATE
		}), null, date2);
		
		//TODO add a selection for the disease this treatment is meant for.

		return f;
	}
	
}