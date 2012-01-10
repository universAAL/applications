/**
 * 
 */
package org.universAAL.agenda.server.gui.wrapper;

import org.osgi.framework.BundleContext;

/**
 * @author KAgnantis
 *
 */
public class WrapperActivator {
	private static SimpleInputSubscriber inputSubscriber = null;
	private static SimpleOutputPublisher outputPublisher = null;
	private static WrapperActivator theInstance = null;
	private WrapperActivator(BundleContext context) {
		inputSubscriber = new SimpleInputSubscriber(context);
		outputPublisher = new SimpleOutputPublisher(context);
	}
	
	public static WrapperActivator initiateInstance(BundleContext context) {
		if (theInstance == null) 
			theInstance = new WrapperActivator(context);

		return theInstance;
	}

	public static SimpleInputSubscriber getInputSubscriber() { 
		return inputSubscriber;
	}
	
	public static SimpleOutputPublisher getOutputPublisher() {
		return outputPublisher;
	}

}
