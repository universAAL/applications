/**
 * 
 */
package org.universAAL.agenda.server.gui.wrapper;

import org.universAAL.middleware.container.ModuleContext;

/**
 * @author KAgnantis
 * 
 */
public class WrapperActivator {
	private static SimpleInputSubscriber inputSubscriber = null;
	private static SimpleOutputPublisher outputPublisher = null;
	private static WrapperActivator theInstance = null;

	private WrapperActivator(ModuleContext mcontext) {
		inputSubscriber = new SimpleInputSubscriber(mcontext);
		outputPublisher = new SimpleOutputPublisher(mcontext);
	}

	public static WrapperActivator initiateInstance(ModuleContext mcontext) {
		if (theInstance == null)
			theInstance = new WrapperActivator(mcontext);

		return theInstance;
	}

	public static SimpleInputSubscriber getInputSubscriber() {
		return inputSubscriber;
	}

	public static SimpleOutputPublisher getOutputPublisher() {
		return outputPublisher;
	}

}
