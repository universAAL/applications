package org.universAAL.AALapplication.ZWaveDataConsumer;


/* More on how to use this class at: 
 * http://forge.universaal.org/wiki/support:Developer_Handbook_7 */
import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.context.ContextEvent;
import org.universAAL.middleware.context.ContextEventPattern;
import org.universAAL.middleware.context.ContextSubscriber;
import org.universAAL.middleware.owl.MergedRestriction;
import org.universAAL.ontology.activityhub.MotionSensor;

public class MotionSubscriber extends ContextSubscriber {

	protected MotionSubscriber(ModuleContext context,
			ContextEventPattern[] initialSubscriptions) {
		super(context, initialSubscriptions);
	}
	private static ContextEventPattern[] getContextSubscriptionParams() {
		ContextEventPattern cep = new ContextEventPattern();
		cep.addRestriction(MergedRestriction.getFixedValueRestriction(
			ContextEvent.PROP_RDF_PREDICATE, MotionSensor.PROP_MEASURED_VALUE));
		return new ContextEventPattern[] { cep };
	}

	protected MotionSubscriber(ModuleContext context) {
		super(context, getContextSubscriptionParams());
		// TODO Auto-generated constructor stub
	}

	private static ContextEventPattern[] getPermanentSubscriptions() {
		// TODO Auto-generated method stub
		return null;
	}

	public void communicationChannelBroken() {
		// TODO Auto-generated method stub

	}

	public void handleContextEvent(ContextEvent event) {
		 
		System.out.println("MOTION  \n  ----------------\n" + " sub="
				+ event.getSubjectURI() + "\n pred=" + event.getRDFPredicate()
				+ "\n obj=" + event.getRDFObject() + "\n tst="
				+ event.getTimestamp());
	}

}
