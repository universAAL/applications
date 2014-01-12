package org.universAAL.AALapplication.health.manager.ui.measurements;

import org.universAAL.AALapplication.health.manager.ui.AbstractHealthForm;
import org.universAAL.middleware.container.utils.LogUtils;
import org.universAAL.middleware.rdf.Resource;
import org.universAAL.middleware.ui.UIResponse;
import org.universAAL.middleware.ui.rdf.Form;
import org.universAAL.middleware.ui.rdf.Label;
import org.universAAL.middleware.ui.rdf.MediaObject;

/**
 * @author amedrano
 *
 */
public final class HowToBloodPreasure extends AbstractHealthForm {

	/**
	 * @param ahf
	 */
	protected HowToBloodPreasure(AbstractHealthForm ahf) {
		super(ahf);
	}


	@Override
	public void handleUIResponse(UIResponse arg0) {
//	    nothing to handle
	}
	
	public void show(){
		LogUtils.logDebug(owner, getClass(), "show", "SHOWING: WELL DONE"); 
		
		Form f = Form.newMessage(getString("howToBloodPreasure.title"),  getString("howToBloodPreasure.text")); 

		new MediaObject(f.getIOControls(), new Label(null, (String) null),
				getString("howToBloodPreasure.content.type"), getString("howToBloodPreasure.content.url")); 
		sendForm(f);
		    close();
	}


	@Override
	public void dialogAborted(String dialogID, Resource data) {
		// TODO Auto-generated method stub
		
	}

}