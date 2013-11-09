package org.universAAL.AALapplication.health.manager.ui.measurements;

import org.universAAL.AALapplication.health.manager.ui.AbstractHealthForm;
import org.universAAL.middleware.container.utils.LogUtils;
import org.universAAL.middleware.ui.UIResponse;
import org.universAAL.middleware.ui.rdf.Form;
import org.universAAL.middleware.ui.rdf.Label;
import org.universAAL.middleware.ui.rdf.MediaObject;

/**
 * @author amedrano
 *
 */
public final class Motivation extends AbstractHealthForm {

	/**
	 * @param ahf
	 */
	public Motivation(AbstractHealthForm ahf) {
		super(ahf);
	}

	@Override
	public void handleUIResponse(UIResponse arg0) {
	    // nothing to handle
	}
	
	public void show(){
		LogUtils.logDebug(owner, getClass(), "show", "SHOWING: WELL DONE"); 
		
		Form f = Form.newMessage(getString("motivationMessage.title"),  getString("motivationMessage.text"));

		new MediaObject(f.getIOControls(), new Label(null, (String) null),
				getString("motivationMessage.content.type"), getString("motivationMessage.content.url"));
		sendForm(f);
		close();
	}

}