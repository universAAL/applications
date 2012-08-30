package org.universAAL.drools.engine;

import org.osgi.framework.BundleContext;
import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.container.osgi.uAALBundleContainer;
import org.universAAL.middleware.context.ContextEvent;
import org.universAAL.middleware.context.ContextEventPattern;
import org.universAAL.middleware.context.ContextSubscriber;

/**
 * Suscriber component for listening ALL the context event in the context bus.
 * It automatically inserts the contextEvents in the rules engine.
 * 
 * @author mllorente TSB Technologies for Health and Well-being
 */
public class Suscriber extends ContextSubscriber {

	ModuleContext mctx;
	BundleContext bctx;
	RulesEngine drools;
	
	public Suscriber(BundleContext ctx, RulesEngine r) {
		// I think this is the simplest way to subscribe to all ContextEvents
		super(uAALBundleContainer.THE_CONTAINER
				.registerModule(new Object[] { ctx }),
				new ContextEventPattern[] { new ContextEventPattern() });
		mctx = uAALBundleContainer.THE_CONTAINER
				.registerModule(new Object[] { ctx });
		bctx = ctx;
		drools = r;
	}

	@Override
	public void communicationChannelBroken() {
		// TODO Auto-generated method stub
	}

	@Override
	public void handleContextEvent(ContextEvent event) {
		// System.out.println(event.getRDFPredicate().substring(event.getRDFPredicate().indexOf("#")));
	
		if (event.getRDFPredicate().substring(
				event.getRDFPredicate().indexOf("#") + 1).equals(
				new String("producesConsequences"))) {
			//do nothing
			//drools.insertContextEvent(event.getRDFObject());
		}else{
		
//			LogUtils.logDebug(mctx, getClass(), "handleContextEvent",
//					new String[] { "Handling consequence" }, null);
//			ConsequenceProperty[] cprops = ((Consequence) event.getRDFObject())
//					.getProperties();
//			for (ConsequenceProperty consequenceProperty : cprops) {
//				LogUtils.logDebug(mctx, getClass(), "handleContextEvent",
//						new String[] { "Consequence property information: " },
//						null);
//				LogUtils
//						.logDebug(mctx, getClass(), "handleContextEvent",
//								new String[] { "key->"
//										+ consequenceProperty.getKey()
//										+ "\nvalue->"
//										+ consequenceProperty.getValue() },
//								null);
//			}
//		}

		// System.out.println("Oh god. I have a fantastic Context Event!!!!");

//		LogUtils.logInfo(mctx, getClass(), "handleContextEvent",
//				new String[] { "Received context event:\n"
//						+ "    Subject     = " + event.getSubjectURI() + "\n"
//						+ "    Subject type= " + event.getSubjectTypeURI()
//						+ "\n" + "    Predicate   = " + event.getRDFPredicate()
//						+ "\n" + "    Object      = " + event.getRDFObject() },
//				null);
		// Sensor s = (Sensor) event.getRDFSubject();
		// System.out.println("The sensor is located in: "
		// + s.getLocation().getProperty(Location.PROP_HAS_NAME));
//		LogUtils
//				.logInfo(
//						mctx,
//						getClass(),
//						"handleContextEvent",
//						new String[] { "Inserting the event in the working memory..." },
//						null);
		
		drools.insertContextEvent(event);
		}

	}

}
