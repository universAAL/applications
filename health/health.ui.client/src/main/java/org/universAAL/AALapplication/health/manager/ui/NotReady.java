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
		new MainMenu(this).show();
	}
	
	public void show(){
		LogUtils.logDebug(owner, getClass(), "show", "SHOWING: NOT READY");
		
		Form f = Form.newDialog("Functionality not available", new Resource());
		
		new SimpleOutput(f.getIOControls(), null, null,
				"Sorry this functionality is not yet ready. Press \"Back\" to got to Main menu");
		new Submit(f.getSubmits(), new Label("Back", null), GO_HOME);
		
		sendForm(f);
	}

}
