package org.universAAL.AALapplication.FollowMeExampleGUI;

import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.context.ContextEvent;
import org.universAAL.middleware.context.ContextEventPattern;
import org.universAAL.middleware.context.ContextSubscriber;
import org.universAAL.middleware.owl.MergedRestriction;
import org.universAAL.middleware.util.Constants;
import org.universAAL.ontology.location.Location;
import org.universAAL.ontology.profile.User;

public class LocationSubscriber extends ContextSubscriber {
	private String USER_NAMESPACE = Constants.uAAL_MIDDLEWARE_LOCAL_ID_PREFIX;
	private String LOCATION_NAMESPACE = Constants.uAAL_MIDDLEWARE_LOCAL_ID_PREFIX;
	
	private ModuleContext context;
	
	protected LocationSubscriber(ModuleContext context,
			ContextEventPattern[] initialSubscriptions) {
		super(context, initialSubscriptions);
		this.context=context;
	}
	private static ContextEventPattern[] getContextSubscriptionParams() {
		ContextEventPattern contextEventPattern = new ContextEventPattern();
	    contextEventPattern.addRestriction(MergedRestriction
	        .getFixedValueRestriction(ContextEvent.PROP_RDF_PREDICATE,
	            User.PROP_PHYSICAL_LOCATION));
	    return new ContextEventPattern[] { contextEventPattern }; 
	}

	protected LocationSubscriber(ModuleContext context) {
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
		System.out.print("LOCATION RECEIVED \n");
		
		User u = (User) event.getRDFSubject();
		String location = u.getProperty(User.PROP_PHYSICAL_LOCATION).toString();
		String loc[] = location.split("#");
		if (loc[1].compareTo("Garage")==0){
			System.out.print(loc[1]+"\n");
			new PopUp(loc[1]);
		}
	}

}
