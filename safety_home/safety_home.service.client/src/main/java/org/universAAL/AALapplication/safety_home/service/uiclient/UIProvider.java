/*****************************************************************************************
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *****************************************************************************************/

package org.universAAL.AALapplication.safety_home.service.uiclient;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Locale;
import java.util.Vector;


import org.universAAL.AALapplication.safety_home.service.uiclient.db.DerbyInterface;
import org.universAAL.AALapplication.safety_home.service.uiclient.dialogs.door.FrontDoorControl;
import org.universAAL.AALapplication.safety_home.service.uiclient.dialogs.environmental.EnvironmentalControl;
import org.universAAL.AALapplication.safety_home.service.uiclient.utils.Utils;
import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.container.utils.StringUtils;
import org.universAAL.middleware.owl.supply.LevelRating;
import org.universAAL.middleware.rdf.PropertyPath;
import org.universAAL.middleware.rdf.Resource;
import org.universAAL.middleware.ui.UICaller;
import org.universAAL.middleware.ui.UIRequest;
import org.universAAL.middleware.ui.UIResponse;
import org.universAAL.middleware.ui.owl.PrivacyLevel;
import org.universAAL.middleware.ui.rdf.Form;
import org.universAAL.middleware.ui.rdf.Group;
import org.universAAL.middleware.ui.rdf.Label;
import org.universAAL.middleware.ui.rdf.MediaObject;
import org.universAAL.middleware.ui.rdf.Repeat;
import org.universAAL.middleware.ui.rdf.Select1;
import org.universAAL.middleware.ui.rdf.SimpleOutput;
import org.universAAL.middleware.ui.rdf.Submit;
import org.universAAL.ontology.Safety.Notification;
import org.universAAL.ontology.Safety.SafetyOntology;
import org.universAAL.ontology.phThing.Device;
import org.universAAL.ontology.profile.User;

/**
 * @author dimokas
 * 
 */
public class UIProvider extends UICaller {

	private final static String window = "UIProvider#";
	static final String MY_UI_NAMESPACE = SharedResources.CLIENT_SAFETY_UI_NAMESPACE + window;
    static final String SUBMIT_DOOR = MY_UI_NAMESPACE + "door";
    static final String SUBMIT_ENVIRONMENTAL = MY_UI_NAMESPACE + "environmental";
    static final String SUBMIT_NOTIFICATIONS = MY_UI_NAMESPACE + "notifications";
    static final String SUBMIT_HELP = MY_UI_NAMESPACE + "help";
    static final String SUBMIT_EXIT = MY_UI_NAMESPACE + "exit";
	static final String SUBMISSION_GOBACK = MY_UI_NAMESPACE + "goback";
	static final String SUBMISSION_DELETEALL = MY_UI_NAMESPACE + "deleteall";
	public static final String NOTIFICATIONS = MY_UI_NAMESPACE + "NotificatioDetails";
	static final PropertyPath PROP_PATH_NOTIFICATIONS = new PropertyPath(
			null, false, new String[] { NOTIFICATIONS });

    public static Form mainDialog = null;
    public static Form notificationDialog = null;
	public static String dialogID;
	private Device[] devices = null;

    public static final String PROP_MESSAGE = SafetyOntology.NAMESPACE + "message";
    public static final String PROP_DATE = SafetyOntology.NAMESPACE + "date";
    public static final String PROP_TIME = SafetyOntology.NAMESPACE + "time";

	
	protected UIProvider(ModuleContext context) {
		super(context);
		devices = SafetyClient.getControlledDevices();
		for (int i = 0; i < devices.length; i++) {
			String label = devices[i].getResourceLabel();
			if (label == null) {
				String st = devices[i].getURI();
				String type = StringUtils.deriveLabel((st == null) ? devices[i]
						.getClassURI() : st);
				label = devices[i].getOrConstructLabel(type);
				devices[i].setResourceLabel(label);
			}
		}
	}

    public void communicationChannelBroken() { }

	private Form initMainDialog() {
		Form f = Form.newDialog("Safety and Security",	new Resource());
		//SimpleOutput welcome = new SimpleOutput(f.getIOControls(), null, null, "Welcome to the Safety and Security service.");
		new SimpleOutput(f.getIOControls(), null, null,"Safety and Secutiry in my Space");

		new Submit(f.getSubmits(), new Label("Front Door Control", null), SUBMIT_DOOR);
		new Submit(f.getSubmits(), new Label("Environmental Control", null), SUBMIT_ENVIRONMENTAL);
		if (SharedResources.currentUser == SharedResources.caregiver){
			new Submit(f.getSubmits(), new Label("Notifications", null), SUBMIT_NOTIFICATIONS);
		}
		new Submit(f.getSubmits(), new Label("Help", null), SUBMIT_HELP);
		new Submit(f.getSubmits(), new Label("Exit", null), SUBMIT_EXIT);

		return f;
	}

    String getDeviceURI(int index) {
		if (index < devices.length)
		    return devices[index].getURI();
		return null;
    }

	public int getbsIDFromURI(String deviceURI) {
		System.out.println(">> DEVICE URI: "+deviceURI);
		for (int i = 0; i < devices.length; i++) {
			if (devices[i].getURI().equals(deviceURI)) {
				return i;
			}
		}
		return 0;
	}
    
	public void handleUIResponse(UIResponse uir) {
		User user = (User) uir.getUser();
		if (user.getURI().equals(SharedResources.testUser.getURI())){
			Utils.println("Assisted Person is using the service");
			SharedResources.currentUser = SharedResources.testUser;
		}
		if (user.getURI().equals(SharedResources.caregiver.getURI())){
			Utils.println("Caregiver is using the service");
			SharedResources.currentUser = SharedResources.caregiver;
		}


		if (uir != null) {
		    if (SUBMIT_EXIT.equals(uir.getSubmissionID()))
				return; // Cancel Dialog, go back to main dialog

		    if (SUBMIT_DOOR.equals(uir.getSubmissionID())) {
		    	this.showDoorMainDialog();
			} 
		    if (SUBMIT_ENVIRONMENTAL.equals(uir.getSubmissionID())) {
		    	this.showEnvironmentalMainDialog();
			} 
		    if (SUBMIT_NOTIFICATIONS.equals(uir.getSubmissionID())) {
		    	this.startNotificationDialog();
			} 
		    if (SUBMIT_HELP.equals(uir.getSubmissionID())) {
		    	this.startMainDialog();
			} 
			if (SUBMISSION_GOBACK.equals(uir.getSubmissionID())) {
				Utils.println(window+"  go back to previous screen");
				this.startMainDialog();
			}
		    if (SUBMISSION_DELETEALL.equals(uir.getSubmissionID())) {
				DerbyInterface di = new DerbyInterface(); 
				try{
					di.init();
					di.modifyAllNotificationState();
				}
				catch(Exception e){ e.printStackTrace(); }
		    	this.startNotificationDialog();
			} 
		}
		//SharedResources.uIProvider.startMainDialog();
	}

    public void startMainDialog() {
    	int deviceID=0;
		if (mainDialog == null)
		    mainDialog = initMainDialog();
		UIRequest out = new UIRequest(SharedResources.currentUser, mainDialog,
				LevelRating.middle, Locale.ENGLISH, PrivacyLevel.insensible);
		sendUIRequest(out);
    }

	private void showDoorMainDialog() {
		Utils.println(window + " starts Front Door Dialog");
		//SharedResources.fdc.startMainDialog();
		SafetyClient.fdc.startMainDialog();
	}

	private void showEnvironmentalMainDialog() {
		Utils.println(window + " starts Front Door Dialog");
		//SharedResources.ec.startMainDialog();
		SafetyClient.ec.startMainDialog();
	}

	public void startNotificationDialog() {
		Utils.println(window + "startMainDialog");
		notificationDialog = notificationMainDialog();
		
		if (notificationDialog!=null){
			UIRequest out = new UIRequest(SharedResources.currentUser, notificationDialog,
					LevelRating.middle, Locale.ENGLISH, PrivacyLevel.insensible);
			sendUIRequest(out);
		}
	}

	private Form notificationMainDialog() {
		Utils.println(window + "createNotificationsMainDialog");
		Form f = Form.newDialog("Notifications", new Resource());
		
		DerbyInterface di = new DerbyInterface(); 
		//String output = "Notifications: \n\n";
		String output = "";
		try{
			di.init();
			Hashtable notifications = di.getUnReadNotifications();
			Enumeration en = notifications.keys();
			ArrayList l = new ArrayList();
			while (en.hasMoreElements()){
				int notificationID = ((Integer)en.nextElement()).intValue();
				Vector msg = (Vector)notifications.get(new Integer(notificationID));
				if (msg.size()>0){
					System.out.println(msg.get(0));
					Notification n = new Notification(Notification.MY_URI, (String)msg.get(0), (String)msg.get(1), (String)msg.get(2));
					l.add(n);
				}
			}

			Repeat r = new Repeat(f.getIOControls(),new Label("Notifications", (String) null),
					PROP_PATH_NOTIFICATIONS,null, l);
			Group g = new Group(r, null, null, null, null);

			new SimpleOutput(g, new Label("Message", (String) null),
				    new PropertyPath(null, false,
					    new String[] { Notification.PROP_MESSAGE }), null);
			new SimpleOutput(g, new Label("Date", (String) null),
				    new PropertyPath(null, false,
					    new String[] { Notification.PROP_DATE }), null);
			new SimpleOutput(g, new Label("Time", (String) null),
				    new PropertyPath(null, false,
					    new String[] { Notification.PROP_TIME }), null);

		}
		catch(Exception e){ e.printStackTrace(); }
		
		//SimpleOutput foutput = new SimpleOutput(f.getIOControls(), null, null, output);		
		new Submit(f.getSubmits(), new Label("Delete All", null), SUBMISSION_DELETEALL);
		new Submit(f.getSubmits(), new Label("Go back", null), SUBMISSION_GOBACK);
		
		return f;
	}

	
	@Override
	public void dialogAborted(String dialogID) {
		Utils.println(window + " dialogAborted: " + dialogID);
	}

}
