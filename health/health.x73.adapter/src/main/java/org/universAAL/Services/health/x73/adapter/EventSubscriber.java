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

package org.universAAL.Services.health.x73.adapter;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.xml.datatype.XMLGregorianCalendar;

import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.context.ContextEvent;
import org.universAAL.middleware.context.ContextEventPattern;
import org.universAAL.middleware.context.ContextSubscriber;
import org.universAAL.middleware.owl.MergedRestriction;
import org.universAAL.middleware.rdf.Resource;
import org.universAAL.middleware.service.CallStatus;
import org.universAAL.middleware.service.DefaultServiceCaller;
import org.universAAL.middleware.service.ServiceRequest;
import org.universAAL.middleware.service.ServiceResponse;
import org.universAAL.middleware.service.owls.process.ProcessOutput;
import org.universAAL.ontology.health.owl.PerformedMeasurementSession;
import org.universAAL.ontology.health.owl.services.PerformedSessionManagementService;
import org.universAAL.ontology.healthmeasurement.owl.BloodPressure;
import org.universAAL.ontology.healthmeasurement.owl.HealthMeasurement;
import org.universAAL.ontology.healthmeasurement.owl.HeartRate;
import org.universAAL.ontology.healthmeasurement.owl.PersonWeight;
import org.universAAL.ontology.measurement.Measurement;
import org.universAAL.ontology.personalhealthdevice.BloodPressureMeasurement;
import org.universAAL.ontology.personalhealthdevice.BloodPressureMonitor;
import org.universAAL.ontology.personalhealthdevice.WeighingScale;
import org.universAAL.ontology.phThing.Device;
import org.universAAL.ontology.profile.AssistedPerson;
import org.universAAL.ontology.profile.User;
import org.universAAL.ontology.profile.service.ProfilingService;

/**
 * @author amedrano
 *
 */
public class EventSubscriber extends ContextSubscriber {

    private static final String USRS = "http://lst.tfo.upm.es/Heatlh.owl#userList";

    /**
     * @param connectingModule
     * @param initialSubscriptions
     */
    public EventSubscriber(ModuleContext connectingModule,
	    ContextEventPattern[] initialSubscriptions) {
	super(connectingModule, initialSubscriptions);
    }
    
	/** Define the context event pattern to be subscribed */	 
    public static ContextEventPattern[] getContextSubscriptionParams() {
    	// Blood pressure device context event
    	ContextEventPattern cep1 = new ContextEventPattern();
    	cep1.addRestriction(MergedRestriction.getAllValuesRestriction(ContextEvent.PROP_RDF_SUBJECT,BloodPressureMonitor.MY_URI));
    	// Weighing scale context event
    	ContextEventPattern cep2 = new ContextEventPattern();
    	cep2.addRestriction(MergedRestriction.getAllValuesRestriction(ContextEvent.PROP_RDF_SUBJECT,WeighingScale.MY_URI));
    	
    	return new ContextEventPattern[] {cep1,cep2 };
    }

    /** {@ inheritDoc}	 */
    @Override
    public void communicationChannelBroken() {

    }

    /** {@ inheritDoc}	 */
    @Override
    public void handleContextEvent(ContextEvent event) {
	
	List<HealthMeasurement> lhm = new ArrayList<HealthMeasurement>();
	
	// Blood pressure monitor event
	if (event.getRDFObject() instanceof BloodPressureMeasurement) {
		BloodPressureMeasurement bpme = (BloodPressureMeasurement) event.getRDFObject();
		String sysMeasuredValue = bpme.getMeasuredBPSys().getValue().toString();
		String diaMeasuredValue = bpme.getMeasuredBPDia().getValue().toString();
		String hrMeasuredValue = bpme.getMeasuredHeartRate().getValue().toString();
		
		BloodPressure bpm = new BloodPressure();
		Measurement m = new Measurement();
		//TODO set units?
		m.setValue(Double.valueOf(diaMeasuredValue));
		bpm.setSyst(m);
		m = (Measurement) m.copy(false);
		m.setValue(Double.valueOf(sysMeasuredValue));
		bpm.setDias(m);
		bpm.setMeasuredFrom((Device) event.getRDFSubject());
		lhm.add(bpm);
		
		HeartRate hr = new HeartRate();
		//TODO set units?
		hr.setMeasuredFrom((Device) event.getRDFSubject());
		hr.changeProperty(Measurement.PROP_VALUE, Double.valueOf(hrMeasuredValue));
		lhm.add(hr);
		
	// Weighing scale event	
	} else if (event.getRDFSubject() instanceof WeighingScale) {
		double temp = -1;
		WeighingScale ws = (WeighingScale) event.getRDFSubject();
		temp = Double.parseDouble(ws.getMeasuredWeight().getValue().toString());
		String weightMeasuredValue;
		if(temp >= 1000)
			weightMeasuredValue = ""+ (temp/1000);
		else
			weightMeasuredValue = ws.getMeasuredWeight().getValue().toString();
		
		PersonWeight pw = new PersonWeight();
		//TODO set units?
		pw.setMeasuredFrom((Device) event.getRDFSubject());
		pw.changeProperty(Measurement.PROP_VALUE, Double.valueOf(weightMeasuredValue));
		lhm.add(pw);
	}
	
	//TODO find the user
	User u = findUser();
	
	for (HealthMeasurement hm : lhm) {
	    PerformedMeasurementSession ps = new PerformedMeasurementSession();
	    GregorianCalendar c = new GregorianCalendar();
	    c.setTime(new Date(event.getTimestamp()));
	    XMLGregorianCalendar date2 = null;
	    ps.setSessionEndTime(date2);
	    ps.setHealthMeasurement(hm);
	    //CallService
	    ServiceRequest sr = new ServiceRequest(
		    new PerformedSessionManagementService(), null);
	    sr.addAddEffect(
		    new String[] { PerformedSessionManagementService.PROP_MANAGES_SESSION },
		    ps);
	    sr.addValueFilter(
		    new String[] { PerformedSessionManagementService.PROP_ASSISTED_USER },
		    u);
	    new DefaultServiceCaller(owner).call(sr);
	}
    }
    
    private User findUser(){
	ServiceRequest sr = new ServiceRequest(new ProfilingService(null), null);
	ProcessOutput output0 = new ProcessOutput(null);
	output0.setParameterType(User.MY_URI);
	sr.addSimpleOutputBinding(output0, new String[]{ProfilingService.PROP_CONTROLS});
	sr.addRequiredOutput(USRS, new String[]{ProfilingService.PROP_CONTROLS});
	ServiceResponse sre = new DefaultServiceCaller(owner).call(sr);
	if (sre.getCallStatus().equals(CallStatus.succeeded)){
	    List<Resource> usrs = sre.getOutput(USRS, true);
	    ArrayList<AssistedPerson> apUsrs = new ArrayList<AssistedPerson>();
	    for (Resource res : usrs) {
		if (res instanceof AssistedPerson){
		    apUsrs.add((AssistedPerson) res);
		}
	    }
	    if (apUsrs.size() == 1){
		return apUsrs.get(0);
	    } 
	}
	return null;
    }

}
