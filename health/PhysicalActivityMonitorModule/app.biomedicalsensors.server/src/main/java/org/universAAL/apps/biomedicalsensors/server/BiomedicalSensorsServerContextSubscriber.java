/*
	Copyright 2012 CERTH, http://www.certh.gr
	
	See the NOTICE file distributed with this work for additional 
	information regarding copyright ownership
	
	Licensed under the Apache License, Version 2.0 (the "License");
	you may not use this file except in compliance with the License.
	You may obtain a copy of the License at
	
	  http://www.apache.org/licenses/LICENSE-2.0
	
	Unless required by applicable law or agreed to in writing, software
	distributed under the License is distributed on an "AS IS" BASIS,
	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
	See the License for the specific language governing permissions and
	limitations under the License.
 */
package org.universAAL.apps.biomedicalsensors.server;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.container.utils.LogUtils;
import org.universAAL.middleware.context.ContextEvent;
import org.universAAL.middleware.context.ContextEventPattern;
import org.universAAL.middleware.context.ContextSubscriber;
import org.universAAL.middleware.owl.Intersection;
import org.universAAL.middleware.owl.MergedRestriction;
import org.universAAL.middleware.owl.TypeURI;
import org.universAAL.middleware.owl.supply.LevelRating;
import org.universAAL.middleware.service.CallStatus;
import org.universAAL.middleware.service.DefaultServiceCaller;
import org.universAAL.middleware.service.ServiceCaller;
import org.universAAL.middleware.service.ServiceRequest;
import org.universAAL.middleware.service.ServiceResponse;
import org.universAAL.middleware.ui.rdf.Form;
import org.universAAL.ontology.biomedicalsensors.AlertService;
import org.universAAL.ontology.biomedicalsensors.BiomedicalSensorService;
import org.universAAL.ontology.biomedicalsensors.CompositeBiomedicalSensor;
import org.universAAL.ontology.biomedicalsensors.MeasuredEntity;
import org.universAAL.ontology.biomedicalsensors.Zephyr;
import org.universAAL.ontology.drools.Consequence;
import org.universAAL.ontology.drools.ConsequenceProperty;
import org.universAAL.ontology.drools.DroolsReasoning;

/**
 * @author billyk,joemoul
 * 
 */
public class BiomedicalSensorsServerContextSubscriber extends ContextSubscriber {

	private static ServiceCaller caller;

	private static final String BIOMEDICALSENSORS_CONSUMER_NAMESPACE = "http://ontology.universaal.org/BiomedicalSensorsCaller.owl#";

	private static final String OUTPUT_LIST_OF_SENSORS = BIOMEDICALSENSORS_CONSUMER_NAMESPACE
			+ "controlledBiomedicalSensors";
	private static final String OUTPUT_SENSOR_TYPE = CompositeBiomedicalSensor.PROP_SENSOR_TYPE;
	private static final String OUTPUT_SENSOR_BTURL = CompositeBiomedicalSensor.PROP_DISCOVERED_BT_SERVICE;
	private static final String OUTPUT_SENSOR_MEASUREMENTS = CompositeBiomedicalSensor.PROP_LAST_MEASUREMENTS;

	private static ContextEventPattern[] getContextSubscriptionParams() {

		ContextEventPattern cep = new ContextEventPattern();

		cep.addRestriction(MergedRestriction.getAllValuesRestriction(
				ContextEvent.PROP_RDF_SUBJECT, DroolsReasoning.MY_URI));

		return new ContextEventPattern[] { cep };
	}

	BiomedicalSensorsServerContextSubscriber(ModuleContext context) {
		// the constructor register us to the bus
		super(context, getContextSubscriptionParams());

		caller = new DefaultServiceCaller(context);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ContextSubscriber#handleContextEvent(ContextEvent)
	 */
	public void handleContextEvent(ContextEvent event) {

	/*	System.out.println("I got the event" + event.getRDFObject().toString());
		System.out.println("I got the event subject"
				+ event.getRDFSubject().toString());
		System.out.println("I got the event Predicate"
				+ event.getRDFPredicate().toString());*/
		// DroolsReasoning c=(DroolsReasoning)event.getRDFSubject();
		Consequence csq = (Consequence) event.getRDFObject();
/*		System.out.println("***********************Consequence listened: ");
*/
		ConsequenceProperty[] consequenceArray = csq.getProperties();
		String source = null;
		String intensity = null;
		boolean pAlert = false;
		boolean alertState = false;
		for (ConsequenceProperty consequenceProperty : consequenceArray) {

			if ((consequenceProperty.getKey() == "Alert")
					&& (consequenceProperty.getValue() == "PostureAlert"))
				pAlert = true;

			if ((consequenceProperty.getKey() == "State")
					&& (consequenceProperty.getValue() == "true"))
				alertState = true;

			if ((consequenceProperty.getKey() == "State")
					&& (consequenceProperty.getValue() == "false")) {
				alertState = false;
				pAlert = false;
			}

			System.out.println(consequenceProperty.getKey() + "--"
					+ consequenceProperty.getValue());
			if ((pAlert) && (alertState)) {
				BiomedicalSensorsCallee.postureRuleFired = true;
			} else {
				BiomedicalSensorsCallee.postureRuleFired = false;
			}
		}

		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ContextSubscriber#communicationChannelBroken()
	 */
	public void communicationChannelBroken() {

	}

}
