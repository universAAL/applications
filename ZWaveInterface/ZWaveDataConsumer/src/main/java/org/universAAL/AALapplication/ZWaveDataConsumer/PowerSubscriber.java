package org.universAAL.AALapplication.ZWaveDataConsumer;

import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.context.ContextEvent;
import org.universAAL.middleware.context.ContextEventPattern;
import org.universAAL.middleware.context.ContextSubscriber;
import org.universAAL.middleware.owl.MergedRestriction;
import org.universAAL.ontology.device.DimmerSensor;

public class PowerSubscriber extends ContextSubscriber {

	protected PowerSubscriber(ModuleContext context,
			ContextEventPattern[] initialSubscriptions) {
		super(context, initialSubscriptions);
	}
	private static ContextEventPattern[] getContextSubscriptionParams() {
		ContextEventPattern cep = new ContextEventPattern();
		cep.addRestriction(MergedRestriction.getFixedValueRestriction(
			ContextEvent.PROP_RDF_PREDICATE, DimmerSensor.PROP_HAS_VALUE));
		return new ContextEventPattern[] { cep };
	}

	protected PowerSubscriber(ModuleContext context) {
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
		System.out.println("POWER  \n  ----------------\n" + " sub="
				+ event.getSubjectURI() + "\n pred=" + event.getRDFPredicate()
				+ "\n obj=" + event.getRDFObject() + "\n tst="
				+ event.getTimestamp());
	}

}