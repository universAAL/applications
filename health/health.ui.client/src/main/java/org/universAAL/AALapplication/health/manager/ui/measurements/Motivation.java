package org.universAAL.AALapplication.health.manager.ui.measurements;

import org.universAAL.AALapplication.health.manager.ui.AbstractHealthForm;
import org.universAAL.AALapplication.health.manager.ui.MainMenu;
import org.universAAL.middleware.container.utils.LogUtils;
import org.universAAL.middleware.ui.UIResponse;
import org.universAAL.middleware.ui.rdf.Form;
import org.universAAL.middleware.ui.rdf.Label;
import org.universAAL.middleware.ui.rdf.MediaObject;
import org.universAAL.middleware.ui.rdf.SimpleOutput;

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

	private static final String WELL_DONE = "well done";

	@Override
	public void handleUIResponse(UIResponse arg0) {
		new MainMenu(this).show();
	}
	
	public void show(){
		LogUtils.logDebug(owner, getClass(), "show", "SHOWING: WELL DONE");
		
		Form f = Form.newMessage("Motivation Message",  WELL_DONE);
		new SimpleOutput(f.getIOControls(), null, null,
				"Well done. Your pressure levels are ok");
		new MediaObject(f.getIOControls(), new Label("Image", (String) null),
				"image", "../Accept.png");		
		sendForm(f);
	}

}