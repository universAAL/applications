/*
	Copyright 2008-2014 TSB, http://www.tsbtecnologias.es
	TSB - Tecnologías para la Salud y el Bienestar
	
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
package org.universAAL.ltba.manager;

import java.util.Calendar;
import java.util.Date;

import org.osgi.framework.BundleContext;
import org.universAAL.ltba.activity.ActivityLogger;
import org.universAAL.ltba.activity.representation.GraphicReporter;
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
public class ConsequenceListener extends ContextSubscriber {

	private boolean status = true;
	private ActivityLogger activityLogger;
	private static ConsequenceListener INSTANCE;
	public Calendar debuggingCalendar = Calendar.getInstance();
	private boolean debugTime = false;
	private BundleContext bc;
	private ModuleContext moduleContext;

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

	protected ConsequenceListener(ModuleContext context) {
		super(context, getContextSuscriptionsParam());
		moduleContext = context;
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
	public static ConsequenceListener getInstance(BundleContext context) {
		if (INSTANCE == null)
			return new ConsequenceListener(uAALBundleContainer.THE_CONTAINER
					.registerModule(new Object[] { context }));
		else
			return INSTANCE;
	}

	public static ConsequenceListener getInstance(ModuleContext context) {
		if (INSTANCE == null)
			return new ConsequenceListener(context);
		else
			return INSTANCE;
	}

	/**
	 * Only use this method if the ConsequenceListener has been created.
	 * Otherwise it will return null and the working couldn't be the expected.
	 * 
	 * @return
	 */
	public static ConsequenceListener getInstance() {
		return INSTANCE;
	}

	@Override
	public void communicationChannelBroken() {
	}

	@Override
	public void handleContextEvent(ContextEvent event) {

		if (getStatus()) {
			Consequence csq = (Consequence) event.getRDFObject();
			LogUtils.logInfo(moduleContext, getClass(), "handleContextEvent",
					new String[] { "Handling consequence" }, null);
			LogUtils.logDebug(moduleContext, getClass(), "handleContextEvent",
					new String[] { csq.getURI() }, null);
			ConsequenceProperty[] consequenceArray = csq.getProperties();

			String source = null;
			String intensity = null;

			for (ConsequenceProperty consequenceProperty : consequenceArray) {

				if (consequenceProperty.getKey() == "Source")
					source = consequenceProperty.getValue();
				else if (consequenceProperty.getKey() == "Intensity") {
					intensity = consequenceProperty.getValue();
				}
			}
		}

	}

	public void setStatus(boolean status) {
		LogUtils.logInfo(moduleContext, getClass(), "setStatus",
				new String[] { "SETTING THE STATUS OF AVAILABILITY TO "
						+ status }, null);
		this.status = status;
	}

	public boolean getStatus() {
		return status;
	}

	// TODO dont use ConsequenceListener for showing reports!!!!!!!
	public void printDayReport(Resource inputUser) {
		GraphicReporter.showDayReport(new Date(), activityLogger,
				moduleContext, inputUser);
		// activityLogger.printReport();
	}

	// TODO dont use ConsequenceListener for showing reports!!!!!!!
	public void printMonthReport(Resource inputUser) {
		GraphicReporter.showMonthReport(activityLogger, moduleContext,
				inputUser);
		// activityLogger.printReport();
	}

	// TODO dont use ConsequenceListener for showing reports!!!!!!!
	public void printWeekReport(Resource inputUser) {
		GraphicReporter
				.showWeekReport(activityLogger, moduleContext, inputUser);
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

	/**
	 * @return the moduleContext
	 */
	public ModuleContext getModuleContext() {
		return moduleContext;
	}

	/**
	 * @param moduleContext
	 *            the moduleContext to set
	 */
	public void setModuleContext(ModuleContext moduleContext) {
		this.moduleContext = moduleContext;
	}
}
