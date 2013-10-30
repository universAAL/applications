package org.universAAL.ltba.functional.pir;

import java.util.Calendar;

import org.osgi.framework.BundleContext;
import org.universAAL.ltba.activity.Room;
import org.universAAL.ltba.functional.pir.controller.ActivityIndexController;
import org.universAAL.ltba.functional.pir.controller.AwakeningController;
import org.universAAL.ltba.functional.pir.controller.CurrentActivityController;
import org.universAAL.ltba.functional.pir.controller.GoingToBedController;
import org.universAAL.ltba.functional.pir.controller.OutOfHomeController;
import org.universAAL.ltba.functional.pir.controller.PresenceInBathController;
import org.universAAL.ltba.functional.pir.controller.PresenceInKitchenController;
import org.universAAL.ltba.functional.pir.controller.ShoppingController;
import org.universAAL.ltba.functional.pir.controller.WakeUpController;
import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.container.osgi.uAALBundleContainer;
import org.universAAL.middleware.container.utils.LogUtils;
import org.universAAL.middleware.context.ContextEvent;
import org.universAAL.middleware.context.ContextEventPattern;
import org.universAAL.middleware.context.ContextSubscriber;
import org.universAAL.middleware.owl.MergedRestriction;
import org.universAAL.ontology.drools.Consequence;
import org.universAAL.ontology.drools.ConsequenceProperty;
import org.universAAL.ontology.drools.DroolsReasoning;

/**
 * @author mllorente
 * 
 */
public class PIRConsequenceListener extends ContextSubscriber {

	private boolean status = true;
	private static PIRConsequenceListener INSTANCE;
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
		return new ContextEventPattern[] { cep };
	}

	public PIRConsequenceListener(ModuleContext context) {
		super(context, getContextSuscriptionsParam());
		System.out.println("Creating EnergyConsequenceListener");
		mc = context;
		INSTANCE = this;
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
	public static PIRConsequenceListener getInstance(BundleContext context) {
		if (INSTANCE == null)
			return new PIRConsequenceListener(
					uAALBundleContainer.THE_CONTAINER
							.registerModule(new Object[] { context }));
		else
			return INSTANCE;
	}

	public static PIRConsequenceListener getInstance(ModuleContext context) {
		if (INSTANCE == null)
			return new PIRConsequenceListener(context);
		else
			return INSTANCE;
	}

	/**
	 * Only use this method if the ConsequenceListener has been created.
	 * Otherwise it will return null and the working couldn't be the expected.
	 * 
	 * @return
	 */
	public static PIRConsequenceListener getInstance() {
		return INSTANCE;
	}

	@Override
	public void communicationChannelBroken() {
	}

	@Override
	public void handleContextEvent(ContextEvent event) {
		LogUtils.logDebug(mc, getClass(), "handleContextEvent",
				"LTBA Manager is handling a context evetnt...");
		Consequence csq = (Consequence) event.getRDFObject();
		ConsequenceProperty[] consequenceArray = csq.getProperties();

		String device = null;
		String status = null;
		String activity = null;
		String phase = null;
		String index = null;
		String room = null;
		for (ConsequenceProperty consequenceProperty : consequenceArray) {

			if (consequenceProperty.getKey() == "Device") {
				device = consequenceProperty.getValue();
			} else if (consequenceProperty.getKey() == "Status") {

				status = consequenceProperty.getValue();
			} else if (consequenceProperty.getKey() == "ActivityType") {
				activity = consequenceProperty.getValue();
			} else if (consequenceProperty.getKey() == "Phase") {
				phase = consequenceProperty.getValue();
			} else if (consequenceProperty.getKey() == "Index") {
				index = consequenceProperty.getValue();
			} else if (consequenceProperty.getKey() == "Room") {
				room = consequenceProperty.getValue();
			}
		}

		float h = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
		float m = Calendar.getInstance().get(Calendar.MINUTE);
		float s = Calendar.getInstance().get(Calendar.SECOND);
		System.out.println(m / 60);
		float longTime = h + ((float) (m / 60)) + ((float) (s / 3600));

		if (activity == "WakingUp") {
			WakeUpController.getInstance().addWakingUpHour(longTime);
		} else if (activity == "GoingToBed") {
			GoingToBedController.getInstance().addGoingToBedHour(longTime);
		} else if (activity == "Awakening") {
			AwakeningController.getInstance().addAwakening();
		} else if (activity == "GoingOut") {
			OutOfHomeController.getInstance().outOfHomeStart(longTime);
		} else if (activity == "Current") {
			CurrentActivityController.getInstance().activityDetected(index,
					Room.getRoomByString(room));
		} else if (activity == "AverageDay") {
			ActivityIndexController.getInstance().addActivityIndex(index);
		} else if (activity == "Back") {
			OutOfHomeController.getInstance().outOfHomeStop(longTime);
		} else if (activity == "BackFromShopping") {
			ShoppingController.getInstance().addBackFromShopping();
		} else if (activity == "PresenceInKitchen") {
			if (phase == "Start") {
				PresenceInKitchenController.getInstance()
						.presenceInKitchenStart(longTime);
			} else if (phase == "Stop") {
				PresenceInKitchenController.getInstance()
						.presenceInKitchenStop(longTime);
			}
		} else if (activity == "PresenceInBath") {
			if (phase == "Start") {

				LogUtils.logDebug(mc, getClass(), "handleOcntextEvent",
						"StartTime->" + longTime);
				PresenceInBathController.getInstance().presenceInBathStart(
						longTime);
			} else if (phase == "Stop") {
				PresenceInBathController.getInstance().presenceInBathStop(
						longTime);
				LogUtils.logDebug(mc, getClass(), "handleOcntextEvent",
						"StopTime->" + longTime);
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
