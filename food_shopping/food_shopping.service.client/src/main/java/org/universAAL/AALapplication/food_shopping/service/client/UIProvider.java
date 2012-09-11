package org.universAAL.AALapplication.food_shopping.service.client;

import java.util.Locale;

import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.container.utils.StringUtils;
import org.universAAL.middleware.owl.MergedRestriction;
import org.universAAL.middleware.owl.supply.LevelRating;
import org.universAAL.middleware.rdf.PropertyPath;
import org.universAAL.middleware.rdf.Resource;
import org.universAAL.middleware.ui.UICaller;
import org.universAAL.middleware.ui.UIRequest;
import org.universAAL.middleware.ui.UIResponse;
import org.universAAL.middleware.ui.owl.PrivacyLevel;
import org.universAAL.middleware.ui.rdf.Form;
import org.universAAL.middleware.ui.rdf.InputField;
import org.universAAL.middleware.ui.rdf.Label;
import org.universAAL.middleware.ui.rdf.Select1;
import org.universAAL.middleware.ui.rdf.Submit;
import org.universAAL.ontology.Shopping.*;
import org.universAAL.ontology.location.Location;
import org.universAAL.ontology.phThing.Device;

public class UIProvider extends UICaller {

    static final String MY_UI_NAMESPACE = SharedResources.CLIENT_SHOPPING_UI_NAMESPACE + "UIProvider#";

    static final String SUBMISSION_ON = MY_UI_NAMESPACE + "on";
    static final String SUBMISSION_OFF = MY_UI_NAMESPACE + "off";
    static final String SUBMISSION_SCALE = MY_UI_NAMESPACE + "scale";
    static final String SUBMISSION_EXIT = MY_UI_NAMESPACE + "exit";
    // static final String USER_INPUT_SCALE_VALUE = MY_UI_NAMESPACE +
    // "givenScale";
    static final String USER_INPUT_SELECTED_LAMP = MY_UI_NAMESPACE + "selectedLamp";

    
    static final PropertyPath PROP_PATH_SCALE_VALUE = new PropertyPath(null,
	    false, new String[] { USER_INPUT_SELECTED_LAMP,
		    Refrigerator.PROP_DEVICE_STATUS });
    static final PropertyPath PROP_PATH_SELECTED_LAMP = new PropertyPath(null,
	    false, new String[] { USER_INPUT_SELECTED_LAMP });

    private Form mainDialog = null;
    // Save the devices for this dialog because in this example we assume that
    // this list does not change any more
    private Device[] devices = null;

    protected UIProvider(ModuleContext context) {
		super(context);
		System.out.println("1..............................................................................");
		// initialize the list of devices
		devices = FoodManagementClient.getControlledDevices();
	
		// make sure that all of them have a label appropriate for creating form
		// controls later
		for (int i = 0; i < devices.length; i++) {
		    String label = devices[i].getResourceLabel();
		    System.out.println("//// label = "+label);
		    if (label == null) {
			// in this case create something like the following: Light Bulb
			// "lamp1" in Room "loc1"
			Location l = devices[i].getLocation();
			System.out.println("//// loc = "+l);
			if (l != null)
			    label += " in " + l.getOrConstructLabel(null);
			if (l==null)
				label = "loc1";
			devices[i].setResourceLabel(label);
		    }
		}
    }

    public void communicationChannelBroken() {
	// TODO Auto-generated method stub
    }

    private Form initMainDialog() {
    	System.out.println(".3. .............................................................................");
	Form f = Form.newDialog("Sample Lighting UI: Lamp Controller",
		new Resource());

	// add the available lamps that can be selected for dimming or turning
	// on / off
	Select1 select = new Select1(f.getIOControls(),
		new Label("Lamps", null), PROP_PATH_SELECTED_LAMP,
		MergedRestriction.getAllValuesRestrictionWithCardinality(
			USER_INPUT_SELECTED_LAMP, Refrigerator.MY_URI, 1, 1),
		devices[0]);
	select.generateChoices(devices);

	// add a field for the percentage value to be used for dimming;
	// (not needed when only turning on / off)
	// no local restriction is needed due to model-based restrictions
	// derivable from the used path (in the following, 'root' stands for the
	// root of the form data):
	// 1. "root -> USER_INPUT_SELECTED_LAMP" is restricted to be a light
	// .. source (see the select control above)
	// 2. the path specified below is "root -> USER_INPUT_SELECTED_LAMP
	// .. -> LightSource.PROP_SOURCE_BRIGHTNESS", which refers to the model
	// .. of brightness **under** light source, which is in turn bound
	// .. to an integer between 0 and 100
	InputField in = new InputField(f.getIOControls(), new Label(
		"Scale Value", null), PROP_PATH_SCALE_VALUE, null, null);
	in.setAlertString("Expected: a number between 0 and 100!");

	// add the possible operations while specifying the mandatory input
	// needed form them
	new Submit(f.getSubmits(), new Label("On", null), SUBMISSION_ON)
		.addMandatoryInput(select);
	new Submit(f.getSubmits(), new Label("Off", null), SUBMISSION_OFF)
		.addMandatoryInput(select);
	Submit s = new Submit(f.getSubmits(), new Label("Scale", null),
		SUBMISSION_SCALE);
	s.addMandatoryInput(select);
	s.addMandatoryInput(in);

	// add an exit button for quitting the dialog
	new Submit(f.getSubmits(), new Label("Exit", null), SUBMISSION_EXIT);

	return f;
    }

    String getDeviceURI(int index) {
	if (index < devices.length)
	    return devices[index].getURI();
	return null;
    }

    public void handleUIResponse(UIResponse uir) {
    	System.out.println("2. EEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE");

    	if (uir != null) {
		    if (SUBMISSION_EXIT.equals(uir.getSubmissionID()))
			return; // Cancel Dialog, go back to main dialog
	
		    // check which lamp was selected
		    String lampURI = null;
		    Object o = uir.getUserInput(PROP_PATH_SELECTED_LAMP.getThePath());
		    if (o instanceof Refrigerator) {
			lampURI = ((Refrigerator) o).getURI();
		    }
	
		    if (lampURI != null) {
			// button
				if (SUBMISSION_ON.equals(uir.getSubmissionID())) {
				    FoodManagementClient.turnOn(lampURI);
				} 
				else if (SUBMISSION_OFF.equals(uir.getSubmissionID())) {
					FoodManagementClient.turnOff(lampURI);
				} 
				else if (SUBMISSION_SCALE.equals(uir.getSubmissionID())) {
				    // get scale value
				    o = uir.getUserInput(PROP_PATH_SCALE_VALUE.getThePath());
				    //if (o instanceof Integer) {
					//LightingConsumer.dimToValue(lampURI, (Integer) o);
				    //}
				}
		    }
		}
		SharedResources.uIProvider.startMainDialog();
    }

    void startMainDialog() {
	if (mainDialog == null)
	    mainDialog = initMainDialog();
	UIRequest out = new UIRequest(SharedResources.testUser, mainDialog,
		LevelRating.middle, Locale.ENGLISH, PrivacyLevel.insensible);
	sendUIRequest(out);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.universAAL.middleware.ui.UICaller#dialogAborted(java.lang.String)
     */
    @Override
    public void dialogAborted(String dialogID) {
	// TODO Auto-generated method stub

    }
}
