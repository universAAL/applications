/**
 * 
 */
package org.universAAL.ltba.manager;

import java.util.Calendar;
import java.util.Date;

import org.osgi.framework.BundleContext;
import org.universAAL.ltba.activity.ActivityIntensity;
import org.universAAL.ltba.activity.ActivityLogger;
import org.universAAL.ltba.activity.Room;
import org.universAAL.ltba.activity.representation.GraphicReporter;
import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.container.osgi.uAALBundleContainer;
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
	private ModuleContext mc;

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
		// System.out.println("HANDLING IN CONSEQUENCE-LISTENER");
		if (getStatus()) {
			Consequence csq = (Consequence) event.getRDFObject();
//			System.out.println("LOLEOLOELOLOE");
//			System.out.println("DEBUG TIME: " + debugTime);
//			System.out.println("THIS CALENDAR (CONSEQUENCE HANDLER): "
//					+ debuggingCalendar);
//			System.out.println("LOLEOLOELOLOE");

			System.out.println("Consequence listened: ");

			ConsequenceProperty[] consequenceArray = csq.getProperties();

			String source = null;
			String intensity = null;

			for (ConsequenceProperty consequenceProperty : consequenceArray) {

				if (consequenceProperty.getKey() == "Source")
					source = consequenceProperty.getValue();
				else if (consequenceProperty.getKey() == "Intensity") {
					intensity = consequenceProperty.getValue();
				}
				System.out.println(consequenceProperty.getKey() + "--"
						+ consequenceProperty.getValue());
			}
			if (!debugTime) {
				activityLogger.putEntry(Calendar.getInstance()
						.getTimeInMillis(), Room.getRoomByString(source),
						ActivityIntensity.getIntensityByString(intensity));
			} else {
				activityLogger.putEntry(debuggingCalendar.getTimeInMillis(),
						Room.getRoomByString(source), ActivityIntensity
								.getIntensityByString(intensity));
			}
		}
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
