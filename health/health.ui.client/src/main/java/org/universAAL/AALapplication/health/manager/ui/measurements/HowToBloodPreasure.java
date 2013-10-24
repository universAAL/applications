package org.universAAL.AALapplication.health.manager.ui.measurements;

import org.universAAL.AALapplication.health.manager.ui.AbstractHealthForm;
import org.universAAL.AALapplication.health.manager.ui.MainMenu;
import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.container.utils.LogUtils;

import org.universAAL.middleware.ui.UIResponse;
import org.universAAL.middleware.ui.rdf.Form;
import org.universAAL.middleware.ui.rdf.Label;
import org.universAAL.middleware.ui.rdf.MediaObject;
import org.universAAL.middleware.ui.rdf.SimpleOutput;

import org.universAAL.ontology.profile.User;

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

	private static final String GO_HOME = "goHome";


	@Override
	public void handleUIResponse(UIResponse arg0) {
		new MainMenu(this).show();
	}
	
	public void show(){
		LogUtils.logDebug(owner, getClass(), "show", "SHOWING: WELL DONE");
		
		Form f = Form.newMessage("Motivation Message",  GO_HOME);
		new SimpleOutput(f.getIOControls(), null, null,
				"Make sure you are relaxed and comfortable and make sure your blood presure is correctly.");
		new MediaObject(f.getIOControls(), new Label("Image", (String) null),
				"image", "../Monitor presion.jpg");

		
		sendForm(f);
	}

}