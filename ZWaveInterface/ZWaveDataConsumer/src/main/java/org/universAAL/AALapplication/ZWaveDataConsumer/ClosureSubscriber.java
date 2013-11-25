package org.universAAL.AALapplication.ZWaveDataConsumer;

import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.context.ContextEvent;
import org.universAAL.middleware.context.ContextEventPattern;
import org.universAAL.middleware.context.ContextSubscriber;
import org.universAAL.middleware.owl.MergedRestriction;
import org.universAAL.ontology.activityhub.ContactClosureSensor;


public class ClosureSubscriber extends ContextSubscriber {

	protected ClosureSubscriber(ModuleContext context,
			ContextEventPattern[] initialSubscriptions) {
		super(context, initialSubscriptions);
	}
	private static ContextEventPattern[] getContextSubscriptionParams() {
		ContextEventPattern cep = new ContextEventPattern();
		cep.addRestriction(MergedRestriction.getFixedValueRestriction(
			ContextEvent.PROP_RDF_PREDICATE, ContactClosureSensor.PROP_MEASURED_VALUE));
		return new ContextEventPattern[] { cep };
	}

	protected ClosureSubscriber(ModuleContext context) {
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
		// TODO Auto-generated method stub
		System.out.println("CLOSURE  \n  ----------------\n" + " sub="
				+ event.getSubjectURI() + "\n pred=" + event.getRDFPredicate()
				+ "\n obj=" + event.getRDFObject() + "\n tst="
				+ event.getTimestamp());
	}

}
