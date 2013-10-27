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

import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.datatype.XMLGregorianCalendar;

import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.context.ContextEvent;
import org.universAAL.middleware.context.ContextEventPattern;
import org.universAAL.middleware.context.ContextSubscriber;
import org.universAAL.middleware.service.DefaultServiceCaller;
import org.universAAL.middleware.service.ServiceRequest;
import org.universAAL.ontology.health.owl.PerformedMeasurementSession;
import org.universAAL.ontology.health.owl.services.PerformedSessionManagementService;
import org.universAAL.ontology.healthmeasurement.owl.HealthMeasurement;
import org.universAAL.ontology.profile.User;

/**
 * @author amedrano
 *
 */
public class EventSubscriber extends ContextSubscriber {

    /**
     * @param connectingModule
     * @param initialSubscriptions
     */
    public EventSubscriber(ModuleContext connectingModule,
	    ContextEventPattern[] initialSubscriptions) {
	super(connectingModule, initialSubscriptions);
    }

    /** {@ inheritDoc}	 */
    @Override
    public void communicationChannelBroken() {

    }

    /** {@ inheritDoc}	 */
    @Override
    public void handleContextEvent(ContextEvent event) {
	
	// TODO find the info
	
	HealthMeasurement hm = null;
	
	//TODO find the user
	User u = null;
	
	PerformedMeasurementSession ps = new PerformedMeasurementSession();
	GregorianCalendar c = new GregorianCalendar();
	c.setTime(new Date(event.getTimestamp()));
	XMLGregorianCalendar date2 = null;
	ps.setSessionEndTime(date2);
	ps.setHealthMeasurement(hm);
	
	//CallService
	ServiceRequest sr = new ServiceRequest(new PerformedSessionManagementService(), null);
	sr.addAddEffect(new String[]{PerformedSessionManagementService.PROP_MANAGES_SESSION}, ps);
	sr.addValueFilter(new String[]{PerformedSessionManagementService.PROP_ASSISTED_USER}, u);
	new DefaultServiceCaller(owner).call(sr);
    }

}
