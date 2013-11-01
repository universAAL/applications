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
package org.universAAL.AALapplication.biomedicalsensors.server;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.context.ContextEvent;
import org.universAAL.middleware.context.ContextEventPattern;
import org.universAAL.middleware.context.ContextSubscriber;
import org.universAAL.middleware.owl.MergedRestriction;
import org.universAAL.middleware.service.DefaultServiceCaller;
import org.universAAL.middleware.service.ServiceCaller;
import org.universAAL.ontology.biomedicalsensors.CompositeBiomedicalSensor;
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

	Map<String, Long> map = new HashMap<String, Long>();
	String ruleExpTime;
	long lruleExpTime;

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

		System.out
				.println("I got the event " + event.getRDFObject().toString());
		System.out.println("I got the event subject "
				+ event.getRDFSubject().toString());
		System.out.println("I got the event Predicate "
				+ event.getRDFPredicate().toString());

		Consequence csq = (Consequence) event.getRDFObject();

		ConsequenceProperty[] consequenceArray = csq.getProperties();

		boolean pAlert = false;

		for (ConsequenceProperty consequenceProperty : consequenceArray) {

			if ((consequenceProperty.getKey() == "Info_Type")) {
				consequenceProperty.getValue();
			}

			if ((consequenceProperty.getKey() == "State")) {
				consequenceProperty.getValue();
			}

			if ((consequenceProperty.getKey() == "Title")) {
				BiomedicalSensorsCallee.ruleTitle = consequenceProperty
						.getValue();
			}

			if ((consequenceProperty.getKey() == "Description")) {
				BiomedicalSensorsCallee.ruleDesc = consequenceProperty
						.getValue();
			}

			if ((consequenceProperty.getKey() == "Intensity")) {
				BiomedicalSensorsCallee.ruleIntensity = consequenceProperty
						.getValue();
			}

			if (consequenceProperty.getKey() == "expirationTime") {
				ruleExpTime = consequenceProperty.getValue();
			}

			if (consequenceProperty.getKey() == "rule_ID") {

				BiomedicalSensorsCallee.ruleID = consequenceProperty.getValue();
			}
		}
		Date today = Calendar.getInstance().getTime();
		long nowtime = Math.abs(today.getTime() / 1000);

		if (ruleExpTime != null) {
			lruleExpTime = Long.parseLong(ruleExpTime.trim());
			ruleExpTime = null;
		}
		if (map.get(BiomedicalSensorsCallee.ruleID) == null) {
			map.put(BiomedicalSensorsCallee.ruleID, nowtime);
			pAlert = true;
		} else {
			if (nowtime - ((Long) map.get(BiomedicalSensorsCallee.ruleID)) > lruleExpTime) {
				pAlert = true;
				map.put(BiomedicalSensorsCallee.ruleID, nowtime);
			} else {
				pAlert = false;
			}
		}

		System.out
				.println("///////////////////////////////////////////////////////////");
		System.out.println("Hashmap: " + map);
		System.out.println("Rule Expiration Time(long): " + lruleExpTime);
		System.out.println("Nowtime: " + nowtime + " ALERT should print "
				+ pAlert);
		System.out.println("Time difference: "
				+ (nowtime - ((Long) map.get(BiomedicalSensorsCallee.ruleID))));
		System.out.println(BiomedicalSensorsCallee.ruleID + ":ruleTitle:"
				+ BiomedicalSensorsCallee.ruleTitle);
		System.out
				.println("Intensity:" + BiomedicalSensorsCallee.ruleIntensity);
		System.out
				.println("///////////////////////////////////////////////////////////");

		BiomedicalSensorsCallee.ruleFired = pAlert;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ContextSubscriber#communicationChannelBroken()
	 */
	public void communicationChannelBroken() {

	}

}
