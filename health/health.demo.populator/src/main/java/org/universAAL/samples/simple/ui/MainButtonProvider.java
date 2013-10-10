/*******************************************************************************
 * Copyright 2013 Universidad Polit�cnica de Madrid
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
package org.universAAL.samples.simple.ui;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.LinkedList;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.rdf.Resource;
import org.universAAL.middleware.service.CallStatus;
import org.universAAL.middleware.service.DefaultServiceCaller;
import org.universAAL.middleware.service.ServiceCall;
import org.universAAL.middleware.service.ServiceCallee;
import org.universAAL.middleware.service.ServiceRequest;
import org.universAAL.middleware.service.ServiceResponse;
import org.universAAL.middleware.service.owl.InitialServiceDialog;
import org.universAAL.middleware.service.owls.profile.ServiceProfile;
import org.universAAL.ontology.health.owl.HealthProfile;
import org.universAAL.ontology.health.owl.MeasurementRequirements;
import org.universAAL.ontology.health.owl.PerformedSession;
import org.universAAL.ontology.health.owl.StatusType;
import org.universAAL.ontology.health.owl.Treatment;
import org.universAAL.ontology.health.owl.services.ProfileManagementService;

public class MainButtonProvider extends ServiceCallee {

	private static final String NAMESPACE = "http://ontologies.universAAL.com/MyService.owl#";
	private static final String MY_URI = NAMESPACE + "MainButtonService";
	private static final String START_UI = NAMESPACE + "startUI";

	 private static final String REQ_OUTPUT_PROFILE = "http://ontologies.universaal.org/PerformedSessionManager#profile";
	
	private ModuleContext ctxt;
	
	public MainButtonProvider(ModuleContext context,
			ServiceProfile[] realizedServices) {
		super(context, realizedServices);
		this.ctxt = context;
	}
	
	public MainButtonProvider(ModuleContext context){
		this(context,getProfiles());
	}

	private static ServiceProfile[] getProfiles() {
		ServiceProfile initDP = InitialServiceDialog
				.createInitialDialogProfile(
						MY_URI,
						"http://depot.universAAL.com",
						"simple UI",
						START_UI);
		return new ServiceProfile[] {initDP};
	}

	@Override
	public void communicationChannelBroken() {
		// TODO Auto-generated method stub

	}

	@Override
	public ServiceResponse handleCall(ServiceCall call) {
		Object inputUser = call.getProperty(ServiceRequest.PROP_uAAL_INVOLVED_HUMAN_USER);
		
		//Get the current profile if any
		ServiceRequest req = new ServiceRequest(new ProfileManagementService(null), null);
		req.addValueFilter(new String[] {ProfileManagementService.PROP_ASSISTED_USER}, inputUser);
		req.addRequiredOutput(REQ_OUTPUT_PROFILE, new String[] {ProfileManagementService.PROP_ASSISTED_USER_PROFILE});
		
		ServiceResponse sr = new DefaultServiceCaller(ctxt).call(req);
		HealthProfile profile = null;
		if (sr.getCallStatus() == CallStatus.succeeded) {
			profile =  (HealthProfile) sr.getOutput(REQ_OUTPUT_PROFILE, false).get(0);
		}
		
		if(profile == null)
			profile = new HealthProfile();
		
		//Add treatments
		MeasurementRequirements BPreqs = new MeasurementRequirements();
		BPreqs.setMaxValueAllowed(500);
		BPreqs.setMinValueAllowed(30);

		Treatment fakeBPTreatment = new Treatment() {
		};
		fakeBPTreatment.setCompleteness(30);
		fakeBPTreatment.setCompletenessUnit(100);
		fakeBPTreatment.setDescription("Blood pressure monitoring");
		fakeBPTreatment.setName("BP measuremnt");
		fakeBPTreatment.setMeasurementRequirements(BPreqs);
		fakeBPTreatment.setStatus(StatusType.actived);
		
		//Add old sessions:
		LinkedList<PerformedSession> sessions = new LinkedList<PerformedSession>();
		try{
		PerformedSession sess = getFakePerformedSession(48 * 60 * 60 * 1000);
		sess.setAssociatedTreatment( fakeBPTreatment);
		sessions.add(sess);
		sess = getFakePerformedSession(24 * 60 * 60 * 1000);
		sess.setAssociatedTreatment( fakeBPTreatment);
		sessions.add(sess);
		sess = getFakePerformedSession(2 * 60 * 60 * 1000);
		sess.setAssociatedTreatment(fakeBPTreatment);
		sessions.add(sess);
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		profile.addTreatment(fakeBPTreatment);
		
		req = new ServiceRequest(new ProfileManagementService(null), null);
		req.addChangeEffect(new String[] {ProfileManagementService.PROP_ASSISTED_USER_PROFILE}, profile);
		
		new DefaultServiceCaller(ctxt).call(req);
		
		new DemoPopulatorUI(this.ctxt).showDialog((Resource) inputUser);
		return new ServiceResponse(CallStatus.succeeded);
	}
	
	
	public static PerformedSession getFakePerformedSession(long millisago) throws DatatypeConfigurationException{
		PerformedSession se1 = new PerformedSession();

		GregorianCalendar gcal = new GregorianCalendar();
		gcal.setTimeInMillis(Calendar.getInstance().getTimeInMillis() - millisago);
		XMLGregorianCalendar twoHoursAgo = DatatypeFactory.newInstance().newXMLGregorianCalendar(gcal);
		gcal.setTimeInMillis(Calendar.getInstance().getTimeInMillis() - millisago + 10 * 60 * 1000);
		XMLGregorianCalendar oneHourAgo = DatatypeFactory.newInstance().newXMLGregorianCalendar(gcal);
		se1.setSessionStartTime(twoHoursAgo);
		se1.setSessionEndTime(oneHourAgo);

		return se1;
	}

}
