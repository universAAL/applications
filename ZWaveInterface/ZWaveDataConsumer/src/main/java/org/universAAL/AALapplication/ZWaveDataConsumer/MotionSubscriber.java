package org.universAAL.AALapplication.ZWaveDataConsumer;


/* More on how to use this class at: 
 * http://forge.universaal.org/wiki/support:Developer_Handbook_7 */
import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.context.ContextEvent;
import org.universAAL.middleware.context.ContextEventPattern;
import org.universAAL.middleware.context.ContextSubscriber;
import org.universAAL.middleware.owl.MergedRestriction;
import org.universAAL.middleware.util.Constants;
import org.universAAL.ontology.activityhub.ActivityHubSensor;
import org.universAAL.ontology.activityhub.MotionSensor;
import org.universAAL.ontology.location.Location;

public class MotionSubscriber extends ContextSubscriber {
	private String USER_NAMESPACE = Constants.uAAL_MIDDLEWARE_LOCAL_ID_PREFIX;
	private String LOCATION_NAMESPACE = Constants.uAAL_MIDDLEWARE_LOCAL_ID_PREFIX;
	
	private ModuleContext context;
	
	protected MotionSubscriber(ModuleContext context,
			ContextEventPattern[] initialSubscriptions) {
		super(context, initialSubscriptions);
		this.context=context;
	}
	private static ContextEventPattern[] getContextSubscriptionParams() {
		ContextEventPattern cep = new ContextEventPattern();
		cep.addRestriction(MergedRestriction.getFixedValueRestriction(
			ContextEvent.PROP_RDF_PREDICATE, MotionSensor.PROP_MEASURED_VALUE));
		return new ContextEventPattern[] { cep };
	}

	protected MotionSubscriber(ModuleContext context) {
		super(context, getContextSubscriptionParams());
		this.context=context;// TODO Auto-generated constructor stub
	}

	private static ContextEventPattern[] getPermanentSubscriptions() {
		// TODO Auto-generated method stub
		return null;
	}

	public void communicationChannelBroken() {
		// TODO Auto-generated method stub

	}

	public void handleContextEvent(ContextEvent event) {
		ActivityHubSensor cc = (ActivityHubSensor) event.getRDFSubject();
		String location = cc.getLocation().getProperty(Location.PROP_HAS_NAME).toString();
		System.out.print(location);
		System.out.println("MOTION  \n  ----------------\n" + " sub="
				+ event.getSubjectURI() + "\n pred=" + event.getRDFPredicate()
				+ "\n obj=" + event.getRDFObject() + "\n tst="
				+ event.getTimestamp());
		System.out.print("PUBLISHING LOCATION\n");
		LocationContextPublisher lp = new LocationContextPublisher(context);
		lp.publishLocation(TypeOfUser.ASSISTED_PERSON, USER_NAMESPACE
				+ "saied", LOCATION_NAMESPACE
				+ location);
	}

}
