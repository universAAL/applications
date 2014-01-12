/**
 * 
 */
package org.universAAL.AALapplication.health.manager.ui;

import org.universAAL.middleware.container.utils.LogUtils;
import org.universAAL.middleware.rdf.Resource;
import org.universAAL.middleware.ui.UIResponse;
import org.universAAL.middleware.ui.rdf.Form;
import org.universAAL.middleware.ui.rdf.Label;
import org.universAAL.middleware.ui.rdf.SimpleOutput;
import org.universAAL.middleware.ui.rdf.Submit;

/**
 * @author amedrano
 *
 */
public final class NotReady extends AbstractHealthForm {

	/**
	 * @param ahf
	 */
	protected NotReady(AbstractHealthForm ahf) {
		super(ahf);
	}

	private static final String GO_HOME = "goHome"; 

	@Override
	public void handleUIResponse(UIResponse arg0) {
	    close();
		new MainMenu(this).show();
	}
	
	public void show(){
		LogUtils.logDebug(owner, getClass(), "show", "SHOWING: NOT READY"); 
		
		Form f = Form.newDialog(getString("notReady.title"), new Resource()); 
		
		new SimpleOutput(f.getIOControls(), null, null,
				getString("notReady.text")); 
		
		new Submit(f.getSubmits(), 
			new Label(getString("back.toMainMenu"), 
				getString("back.toMainMenu.icon")),
				GO_HOME); 
		
		sendForm(f);
	}

	@Override
	public void dialogAborted(String dialogID, Resource data) {
		// TODO Auto-generated method stub
		
	}

}
