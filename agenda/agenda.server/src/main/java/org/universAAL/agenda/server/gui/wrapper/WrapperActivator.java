/**
 * 
 */
package org.universAAL.agenda.server.gui.wrapper;

import org.universAAL.middleware.container.ModuleContext;


/**
 * @author kagnantis
 * @author eandgrg
 * 
 */
public class WrapperActivator {
    private static MyUICaller myUICaller = null;
    private static WrapperActivator theInstance = null;

    private WrapperActivator(ModuleContext mcontext) {
	myUICaller = new MyUICaller(mcontext);
	
    }

    public static WrapperActivator initiateInstance(ModuleContext mcontext) {
	if (theInstance == null)
	    theInstance = new WrapperActivator(mcontext);

	return theInstance;
    }

    public static MyUICaller getMyUICaller() {
	return myUICaller;
    }



}
