package org.universAAL.agenda.server.gui.wrapper;

import org.universAAL.middleware.container.ModuleContext;


/**
 * @author kagnantis
 * @author eandgrg
 * 
 */
public class WrapperActivator {
    private static UIProvider uIProvider = null;
    private static WrapperActivator theInstance = null;

    private WrapperActivator(ModuleContext mcontext) {
	uIProvider = new UIProvider(mcontext);
	
    }

    public static WrapperActivator initiateInstance(ModuleContext mcontext) {
	if (theInstance == null)
	    theInstance = new WrapperActivator(mcontext);

	return theInstance;
    }

    public static UIProvider getMyUICaller() {
	return uIProvider;
    }



}
