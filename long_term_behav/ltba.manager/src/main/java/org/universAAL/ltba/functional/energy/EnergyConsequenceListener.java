/*
	Copyright 2008-2014 TSB, http://www.tsbtecnologias.es
	TSB - Tecnolog�as para la Salud y el Bienestar
	
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
package org.universAAL.ltba.functional.energy;

import java.util.Calendar;
import java.util.Date;

import org.osgi.framework.BundleContext;
import org.universAAL.ltba.activity.ActivityLogger;
import org.universAAL.ltba.activity.representation.GraphicReporter;
import org.universAAL.ltba.functional.energy.controller.WatchingTVController;
import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.container.osgi.uAALBundleContainer;
import org.universAAL.middleware.container.utils.LogUtils;
import org.universAAL.middleware.context.ContextEvent;
import org.universAAL.middleware.context.ContextEventPattern;
import org.universAAL.middleware.context.ContextSubscriber;
import org.universAAL.middleware.owl.MergedRestriction;
import org.universAAL.middleware.rdf.Resource;
import org.universAAL.ontology.drools.Consequence;
import org.universAAL.ontology.drools.ConsequenceProperty;
import org.universAAL.ontology.drools.DroolsReasoning;

/**
 * @author mllorente
 * 
 */
public class EnergyConsequenceListener extends ContextSubscriber {

	private boolean status = true;
	private ActivityLogger activityLogger;
	private static EnergyConsequenceListener INSTANCE;
	public Calendar debuggingCalendar = Calendar.getInstance();
	private boolean debugTime = false;
	private BundleContext bc;
	private ModuleContext mc;
	private long onTimpestamp = -1;

	/**
	 * Subscription to the events
	 * 
	 * @return The context event pattern for the subscription process
	 */
	private static ContextEventPattern[] getContextSuscriptionsParam() {
		ContextEventPattern cep = new ContextEventPattern();
		cep.addRestriction(MergedRestriction.getAllValuesRestriction(
				ContextEvent.PROP_RDF_SUBJECT, DroolsReasoning.MY_URI));

		// cep.addRestriction(MergedRestriction.getAllValuesRestriction(
		// ContextEvent.PROP_RDF_SUBJECT,"http://www.tsbtecnologias.es/DroolsReasoning.owl#ReasoningConsequence"));

		// TODO I want to do the next but error thrown
		// cep.addRestriction(MergedRestriction.getAllValuesRestriction(
		// ContextEvent.PROP_RDF_PREDICATE,
		// DroolsReasoning.PROP_PRODUCES_CONSEQUENCES));
		return new ContextEventPattern[] { cep };
	}

	public EnergyConsequenceListener(ModuleContext context) {
		super(context, getContextSuscriptionsParam());
		System.out.println("Creating EnergyConsequenceListener");
		mc = context;
		activityLogger = new ActivityLogger(context);
		INSTANCE = this;
	}

	public ActivityLogger getActivityLogger() {
		return activityLogger;
	}

	/**
	 * Gets an instance of the consequence listener for using it in universAAL.
	 * It allows the interaction with the rules engine: insertion an retraction
	 * of facts, rules and evens; extraction of information related to the
	 * runtime session for monitoring proposals and consequences publication.
	 * 
	 * @param context
	 *            The bundle context.
	 * @return Instance of the context listener.
	 */
	public static EnergyConsequenceListener getInstance(BundleContext context) {
		if (INSTANCE == null)
			return new EnergyConsequenceListener(
					uAALBundleContainer.THE_CONTAINER
							.registerModule(new Object[] { context }));
		else
			return INSTANCE;
	}

	public static EnergyConsequenceListener getInstance(ModuleContext context) {
		if (INSTANCE == null)
			return new EnergyConsequenceListener(context);
		else
			return INSTANCE;
	}

	/**
	 * Only use this method if the ConsequenceListener has been created.
	 * Otherwise it will return null and the working couldn't be the expected.
	 * 
	 * @return
	 */
	public static EnergyConsequenceListener getInstance() {
		return INSTANCE;
	}

	@Override
	public void communicationChannelBroken() {
	}

	@Override
	public void handleContextEvent(ContextEvent event) {

		Consequence csq = (Consequence) event.getRDFObject();
		System.out.println("Consequencea listened: ");
		ConsequenceProperty[] consequenceArray = csq.getProperties();

		String device = null;
		String status = null;
		String activity = null;
		for (ConsequenceProperty consequenceProperty : consequenceArray) {

			if (consequenceProperty.getKey() == "Device")
				device = consequenceProperty.getValue();
			else if (consequenceProperty.getKey() == "Status") {
				status = consequenceProperty.getValue();
			} else if (consequenceProperty.getKey() == "ActivityType") {
				activity = consequenceProperty.getValue();
			}
			System.out.println(consequenceProperty.getKey() + "--"
					+ consequenceProperty.getValue());
		}

		if (activity == "WatchingTV") {
			LogUtils.logDebug(mc, getClass(), "handleContextEvent",
					"Watching TV detected in consequence listener: " + device
							+ " - " + status + " - " + activity);
			if (status == "ON") {
				onTimpestamp = System.currentTimeMillis();
			} else if (status == "OFF") {
				if (onTimpestamp > -1) {
					WatchingTVController.getInstance().addTime(
							System.currentTimeMillis() - onTimpestamp);
				}
			}
		}

		if (device == "Pantalla Serdula") {
			// nothing
		} else if (device == "Miguel Angel") {
			// watching tv
		} else if (device == "VeraLite") {
			// washing dishes
		} else if (device == "Enchufe Armario") {
			// laundry
		}

		// System.out.println("<<<<<<EXECUTING NOMHAD PROTOCOL>>>>>>>>");
		// int meas = 0;
		// if (status.equalsIgnoreCase("null")) {
		// meas = 0;
		// } else if (status.equalsIgnoreCase("low")) {
		// meas = 1;
		// } else if (status.equalsIgnoreCase("medium")) {
		// meas = 3;
		// } else if (status.equalsIgnoreCase("high")) {
		// meas = 5;
		// }
		// if (!debugTime) {
		// NomhadGateway.getInstance().putMeasurement("A100", "123456",
		// "HEALTH_INDICATORS_GROUP_WEEK",
		// "AAL_HEALTH_INDEX_WEEK", Integer.toString(meas));
		// activityLogger.putEntry(Calendar.getInstance()
		// .getTimeInMillis(), Room.getRoomByString(device),
		// ActivityIntensity.getIntensityByString(status));
		//
		// } else {
		// NomhadGateway.getInstance().putMeasurement("A100", "123456",
		// "HEALTH_INDICATORS_GROUP_WEEK",
		// "AAL_HEALTH_INDEX_WEEK", Integer.toString(meas),
		// debuggingCalendar.getTimeInMillis());
		// activityLogger.putEntry(debuggingCalendar.getTimeInMillis(),
		// Room.getRoomByString(device), ActivityIntensity
		// .getIntensityByString(status));
		// }
	}

	public void setStatus(boolean status) {
		System.out.println("SETTING THE STATUS OF AVAILABILITY TO " + status);
		this.status = status;
	}

	public boolean getStatus() {
		return status;
	}

	// TODO dont use ConsequenceListener for showing reports!!!!!!!
	public void printDayReport(Resource inputUser) {
		GraphicReporter
				.showDayReport(new Date(), activityLogger, mc, inputUser);
		// activityLogger.printReport();
	}

	// TODO dont use ConsequenceListener for showing reports!!!!!!!
	public void printMonthReport(Resource inputUser) {
		GraphicReporter.showMonthReport(activityLogger, mc, inputUser);
		// activityLogger.printReport();
	}

	// TODO dont use ConsequenceListener for showing reports!!!!!!!
	public void printWeekReport(Resource inputUser) {
		GraphicReporter.showWeekReport(activityLogger, mc, inputUser);
		// activityLogger.printReport();
	}

	public void startDebugTime() {
		debugTime = true;
	}

	public void stopDebugTime() {
		debugTime = false;
	}

	public void setTimeForDebug(long time) {
		debuggingCalendar.setTimeInMillis(time);
	}

}
