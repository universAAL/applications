/*****************************************************************************************
 * Copyright 2012 CERTH, http://www.certh.gr - Center for Research and Technology Hellas
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

package org.universAAL.AALapplication.safety_home.service.uiclient.dialogs.door;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Locale;

//import javax.media.j3d.MediaContainer;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;

import org.universAAL.AALapplication.safety_home.service.uiclient.SafetyUIClient;
import org.universAAL.AALapplication.safety_home.service.uiclient.SharedResources;
import org.universAAL.AALapplication.safety_home.service.uiclient.UIProvider;
import org.universAAL.AALapplication.safety_home.service.uiclient.db.DerbyInterface;
import org.universAAL.AALapplication.safety_home.service.uiclient.utils.Utils;
import org.universAAL.AALapplication.safety_home.service.uiclient.SafetyClient;
import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.container.osgi.util.BundleConfigHome;
import org.universAAL.middleware.owl.supply.LevelRating;
import org.universAAL.middleware.rdf.PropertyPath;
import org.universAAL.middleware.rdf.Resource;
import org.universAAL.middleware.ui.UICaller;
import org.universAAL.middleware.ui.UIRequest;
import org.universAAL.middleware.ui.UIResponse;
import org.universAAL.middleware.ui.owl.PrivacyLevel;
import org.universAAL.middleware.ui.rdf.Form;
import org.universAAL.middleware.ui.rdf.Group;
import org.universAAL.middleware.ui.rdf.InputField;
import org.universAAL.middleware.ui.rdf.Label;
import org.universAAL.middleware.ui.rdf.MediaObject;
import org.universAAL.middleware.ui.rdf.SimpleOutput;
import org.universAAL.middleware.ui.rdf.Submit;
import org.universAAL.middleware.ui.rdf.TextArea;


/**
 * @author dimokas
 * 
 */
enum SoundEffect {
	
	   OPEN("door_open.wav"),
	   CLOSE("door_close.wav"),     
	   LOCK("door_lock.wav"),
	   UNLOCK("door_unlock.wav"),
	   DOORBELL("door_bell.wav"),
	   DOOROPEN("door_is_open.wav");
	   
	   // Nested class for specifying volume
	   public static enum Volume {
	      MUTE, LOW, MEDIUM, HIGH
	   }
	   
	   public static Volume volume = Volume.LOW;
	   public String SOUND_URL = "http://127.0.0.1:8080/resources/safety/sounds/";
	   // Each sound effect has its own clip, loaded with its own sound file.
	   private Clip clip;
	   
	   // Constructor to construct each element of the enum with its own sound file.
	   SoundEffect(String soundFileName) {
	      try {
	    	  /* Open File and create audio input stream  */
/*
	    	  //String curDir = System.getProperty("user.dir");
	    	  //System.out.println("*** "+curDir);
	    	  File confHome = new File(new BundleConfigHome("safety").getAbsolutePath());
	    	  //System.out.println("*** "+confHome.getAbsolutePath() + "  soundfilename="+soundFileName);
	    	  String filePath = confHome.getAbsolutePath() + File.separator + "sounds" + File.separator + soundFileName;
	    	  File soundFile = new 	File(filePath);
*/	    	  
	    	  URL soundFile = new URL(SOUND_URL+soundFileName);
	    	  AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);

	    	  // Get a clip resource.
	    	  clip = AudioSystem.getClip();
	    	  // Open audio clip and load samples from the audio input stream.
	    	  clip.open(audioIn);
	      } catch (UnsupportedAudioFileException e) {
	         e.printStackTrace();
	      } catch (IOException e) {
	         e.printStackTrace();
	      } catch (LineUnavailableException e) {
	         e.printStackTrace();
	      }
	   }
	   
	   // Play or Re-play the sound effect from the beginning, by rewinding.
	   public void play() {
	      if (volume != Volume.MUTE) {
	         if (clip.isRunning())
	            clip.stop();   // Stop the player if it is still running
	         clip.setFramePosition(0); // rewind to the beginning
	         clip.start();     // Start playing
	      }
	   }
	   
	   // Optional static method to pre-load all the sound files.
	   static void init() {
	      values(); // calls the constructor for all the elements
	   }
}


public class FrontDoorControl extends UICaller {

    public final static String IMG_URL = "http://127.0.0.1:8080/resources/safety/images/";
	private final static String window = "UIDoor#";

	static final String MY_UI_NAMESPACE = SharedResources.CLIENT_SAFETY_UI_NAMESPACE + window;
    static final String SUBMISSION_UNLOCK = MY_UI_NAMESPACE + "unlock";
    static final String SUBMISSION_LOCK = MY_UI_NAMESPACE + "lock";
    static final String SUBMISSION_OPEN = MY_UI_NAMESPACE + "open";
    static final String SUBMISSION_CLOSE = MY_UI_NAMESPACE + "close";
    static final String SUBMISSION_VISITOR = MY_UI_NAMESPACE + "visitor";
    static final String SUBMISSION_STATUS = MY_UI_NAMESPACE + "status";
	static final String SUBMISSION_GOBACK = MY_UI_NAMESPACE + "back";
    static final String SUBMISSION_OK_DOORBELL = MY_UI_NAMESPACE + "okdoorbell";
    static final String SUBMISSION_OK_DOOROPEN = MY_UI_NAMESPACE + "okdooropen";
    static final String SUBMISSION_OK_VISITOR = MY_UI_NAMESPACE + "okvisitor";

    public static String deviceURI = "http://ontology.universaal.org/SafetyServer.owl#controlledDevice0";
    public boolean unlockDoor = false;
    public boolean lockDoor = false;
    public boolean openDoor = false;
    public boolean closeDoor = false;
	private Form mainDialog = null;
	private Form unlockDialog = null;
	private Form lockDialog = null;
	private Form openDialog = null;
	private Form closeDialog = null;
	private Form visitorDialog = null;
	private Form statusDialog = null;
	private Form errorDialog = null;
	private Form doorBellDialog = null;
	private Form doorDialog = null;
	private String active = ""; 
	private String status = ""; 
	private String visitorText = "";
	private String person = null;
	private int doorStatus = 0;
	private static boolean success = false;

	private int doorbellNotificationID = 0;
	private int dooropenNotificationID = 0;
	
	public FrontDoorControl(ModuleContext context) {
		super(context);
	}

	public void handleUIResponse(UIResponse uir) {
		Utils.println(window + " Response ID: " + uir.getSubmissionID());
		if (uir != null) {
			if (SUBMISSION_GOBACK.equals(uir.getSubmissionID())) {
				Utils.println(window+"  go back to previous screen");
				SharedResources.uIProvider.startMainDialog();
				return; 
			}
			if (SUBMISSION_UNLOCK.equals(uir.getSubmissionID())) {
				this.active=this.status="unlock";
				startUnlockDialog();
			
				new Thread() {
					public void run() {
						// Unlock the door
						unlockDoor = SafetyClient.unlock(deviceURI);
					}
				}.start();
				lockDoor = false;
				unlockDoor = true;
			} 
			else if (SUBMISSION_LOCK.equals(uir.getSubmissionID())) {
				if ((closeDoor || unlockDoor)&&(!openDoor)){
			    	this.active=this.status="lock";
					startLockDialog();
					new Thread() {
						public void run() {
							// Lock the door
							lockDoor = SafetyClient.lock(deviceURI);
						}
					}.start();
					unlockDoor = false;
					lockDoor = true;
				}
				else
					startErrorDialog(2);				
			} 
			else if (SUBMISSION_OPEN.equals(uir.getSubmissionID())) {
				if (unlockDoor && !lockDoor){
					this.active=this.status="open";
					startOpenDialog();
					closeDoor = false;
					new Thread() {
						public void run() {
							// Open the door
							openDoor = SafetyClient.open(deviceURI);
						}
					}.start();
					openDoor = true;
					closeDoor = false;
				}
				else
					startErrorDialog(3);				
			} 
			else if (SUBMISSION_CLOSE.equals(uir.getSubmissionID())) {
				if (SafetyClient.close(deviceURI)){
					this.active=this.status="close";
					startCloseDialog();
					closeDoor = true;
					openDoor = false;
				}
				else
					startErrorDialog(4);
			} 
			else if (SUBMISSION_VISITOR.equals(uir.getSubmissionID())) {
				this.active="visitor";
				startVisitorDialog();
			} 
			else if (SUBMISSION_STATUS.equals(uir.getSubmissionID())) {
				this.active="status";
				startStatusDialog();
			} 
			else if (SUBMISSION_OK_DOORBELL.equals(uir.getSubmissionID())) {
				DerbyInterface di = new DerbyInterface();
				try{
					di.init();
					di.modifyNotificationState(this.doorbellNotificationID);
				}
				catch(Exception e){ e.printStackTrace(); }
				startMainDialog();
			} 
			else if (SUBMISSION_OK_DOOROPEN.equals(uir.getSubmissionID())) {
				DerbyInterface di = new DerbyInterface();
				try{
					di.init();
					di.modifyNotificationState(this.dooropenNotificationID);
				}
				catch(Exception e){ e.printStackTrace(); }
				startMainDialog();
			} 
			else if (SUBMISSION_OK_VISITOR.equals(uir.getSubmissionID())) {
				startMainDialog();
			} 
		}
		Utils.println(window + " Continues");
	}
	
	public void startMainDialog() {
		Utils.println(window + "startMainDialog");
		if (mainDialog == null)
			mainDialog = initMainDialog();

		UIRequest out = new UIRequest(SharedResources.currentUser, mainDialog,
				LevelRating.middle, Locale.ENGLISH, PrivacyLevel.insensible);
		sendUIRequest(out);
	}

	public void startStatusDialog() {
		Utils.println(window + "startStatusDialog");
		statusDialog = statusMainDialog();

		UIRequest out = new UIRequest(SharedResources.currentUser, statusDialog,
				LevelRating.middle, Locale.ENGLISH, PrivacyLevel.insensible);
		sendUIRequest(out);
	}

	private Form statusMainDialog() {
		Utils.println(window + "createStatusMainDialog");
		Form f = Form.newDialog("Status Front Door", new Resource());
		
		if (this.status.equals("")){
			SimpleOutput welcome = new SimpleOutput(f.getIOControls(), null, null, "Welcome to the Front Door Control application.\n\n");
		}
		else if (this.status.equals("unlock")){
			new MediaObject(f.getIOControls(), new Label("Unlocked Door", null), "image/png", IMG_URL+"door_unlock.png");					
				//((java.net.URL)UIProvider.class.getResource("/images/door_unlock.png")).toString());

		}
		else if (this.status.equals("lock")){
			new MediaObject(f.getIOControls(), new Label("Locked Door", null), "image/png",
				IMG_URL+"door_lock.png");					
				//((java.net.URL)UIProvider.class.getResource("/images/door_lock.png")).toString());
		}
		else if (this.status.equals("open")){
			new MediaObject(f.getIOControls(), new Label("Open Door", null), "image/png",
				IMG_URL+"door_open.png");					
				//((java.net.URL)UIProvider.class.getResource("/images/door_open.png")).toString());
		}
		else if (this.status.equals("close")){
			new MediaObject(f.getIOControls(), new Label("Closed Door", null), "image/png",
				IMG_URL+"door_close.png");					
				//((java.net.URL)UIProvider.class.getResource("/images/door_close.png")).toString());
		}

		f = submitButtons(f);
		
		return f;
	}
	
	public void startVisitorDialog() {
		Utils.println(window + "startVisitorDialog");
		visitorDialog = visitorMainDialog();

		UIRequest out = new UIRequest(SharedResources.currentUser, visitorDialog,
				LevelRating.middle, Locale.ENGLISH, PrivacyLevel.insensible);
		sendUIRequest(out);
	}

	public void startUnlockDialog() {
		Utils.println(window + "startUnlockDialog");
		unlockDialog = unlockMainDialog();

		UIRequest out = new UIRequest(SharedResources.currentUser, unlockDialog,
				LevelRating.middle, Locale.ENGLISH, PrivacyLevel.insensible);
		sendUIRequest(out);
	}

	public void startLockDialog() {
		Utils.println(window + "startLockDialog");
		lockDialog = lockMainDialog();

		UIRequest out = new UIRequest(SharedResources.currentUser, lockDialog,
				LevelRating.middle, Locale.ENGLISH, PrivacyLevel.insensible);
		sendUIRequest(out);
	}

	public void startOpenDialog() {
		Utils.println(window + "startOpenDialog");
		openDialog = openMainDialog();

		UIRequest out = new UIRequest(SharedResources.currentUser, openDialog,
				LevelRating.middle, Locale.ENGLISH, PrivacyLevel.insensible);
		sendUIRequest(out);
	}
	
	public void startCloseDialog() {
		Utils.println(window + "startCloseDialog");
		closeDialog = closeMainDialog();

		UIRequest out = new UIRequest(SharedResources.currentUser, closeDialog,
				LevelRating.middle, Locale.ENGLISH, PrivacyLevel.insensible);
		sendUIRequest(out);
	}

	public void startErrorDialog(int action) {
		Utils.println(window + "startErrorDialog");
		errorDialog = errorMainDialog(action);

		UIRequest out = new UIRequest(SharedResources.currentUser, errorDialog,
				LevelRating.middle, Locale.ENGLISH, PrivacyLevel.insensible);
		sendUIRequest(out);
	}

	private Form initMainDialog() {
		Utils.println(window + "createMenusMainDialog");
		Form f = Form.newDialog("Front Door Control", new Resource());
		SimpleOutput welcome = new SimpleOutput(f.getIOControls(), null, null, "Welcome to the Front Door Control application.\n\n" +
		"- Press the button \"Unlock Door\" to unlock the door.\n"+
		"- Press the button \"Lock Door\" to lock the door.\n"+
		"- Press the button \"Open Door\" to open the door.\n"+
		"- Press the button \"Close Door\" to close the door.\n"+
		"- Press the button \"Visitors\" to see the visitors.\n"+		
		"- Press the button \"Status\" to see the status of the door.\n");		
		
		f = submitButtons(f);

		return f;
	}

	private Form visitorMainDialog() {
		Utils.println(window + "createVisitorMainDialog");
		Form f = Form.newDialog("Visitor Front Door", new Resource());
		
		if (this.person==null){
			SimpleOutput welcome = new SimpleOutput(f.getIOControls(), null, null, "There are no visitors yet.\n\n");
		}
		else{
			new MediaObject(f.getIOControls(), new Label(this.visitorText, null), "image/png", IMG_URL+person+".png");					
			
			SoundEffect.DOORBELL.play();
		}


		f = submitButtons(f);
		
		return f;
	}

	private Form unlockMainDialog() {
		Utils.println(window + "createUnlockMainDialog");
		Form f = Form.newDialog("Unlock Front Door", new Resource());
		
		//Group g1 = new Group(f.getIOControls(), new Label("Door",
		//	      (String) null), null, null, (Resource) null);
		new MediaObject(f.getIOControls(), new Label("Unlocked Door", null), "image/png",
				IMG_URL+"door_unlock.png");					
				//((java.net.URL)UIProvider.class.getResource("/images/door_unlock.png")).toString());
		//new MediaObject(f.getIOControls(), new Label("", null), "audio/wav",
		//		((java.net.URL)UIProvider.class.getResource("/sounds/door_unlock.wav")).toString());
		
		new Thread() {
			public void run() {
				// Unlock the door sound
				SoundEffect.UNLOCK.play();
			}
		}.start();

		f = submitButtons(f);
		
		return f;
	}

	private Form lockMainDialog() {
		Utils.println(window + "createLockMainDialog");
		Form f = Form.newDialog("Lock Front Door", new Resource());
		
		//Group g1 = new Group(f.getIOControls(), new Label("Door",
		//	      (String) null), null, null, (Resource) null);
		new MediaObject(f.getIOControls(), new Label("Locked Door", null), "image/png",
				IMG_URL+"door_lock.png");					
				//((java.net.URL)UIProvider.class.getResource("/images/door_lock.png")).toString());
		//new MediaObject(f.getIOControls(), new Label("", null), "audio/wav",
		//		((java.net.URL)UIProvider.class.getResource("/sounds/door_lock.wav")).toString());
		
		new Thread() {
			public void run() {
				// Lock the door sound
				SoundEffect.LOCK.play();
			}
		}.start();		

		f = submitButtons(f);
		
		return f;
	}

	private Form openMainDialog() {
		Utils.println(window + "createOpenMainDialog");
		Form f = Form.newDialog("Open Front Door", new Resource());
		
		//Group g1 = new Group(f.getIOControls(), new Label("Door",
		//	      (String) null), null, null, (Resource) null);
		new MediaObject(f.getIOControls(), new Label("Open Door", null), "image/png",
				IMG_URL+"door_open.png");					
				//((java.net.URL)UIProvider.class.getResource("/images/door_open.png")).toString());
		//new MediaObject(f.getIOControls(), new Label("", null), "audio/wav",
		//		((java.net.URL)UIProvider.class.getResource("/sounds/door_open.wav")).toString());
		
		new Thread() {
			public void run() {
				// Open the door sound
				SoundEffect.OPEN.play();
			}
		}.start();		

		f = submitButtons(f);
		
		return f;
	}

	private Form closeMainDialog() {
		Utils.println(window + "createCloseMainDialog");
		Form f = Form.newDialog("Close Front Door", new Resource());
		
		//Group g1 = new Group(f.getIOControls(), new Label("Door",
		//	      (String) null), null, null, (Resource) null);
		MediaObject closeIcon = new MediaObject(f.getIOControls(), new Label("Close Door", null), "image/png",
				IMG_URL+"door_close.png");					
				//((java.net.URL)UIProvider.class.getResource("/images/door_close.png")).toString());
		//new MediaObject(f.getIOControls(), new Label("", null), "audio/wav",
		//		((java.net.URL)UIProvider.class.getResource("/sounds/door_close.wav")).toString());
		
		new Thread() {
			public void run() {
				// Close the door sound
				SoundEffect.CLOSE.play();
			}
		}.start();		
		
		f = submitButtons(f);
		
		return f;
	}

	public void startDoorBellDialog(boolean isEnabled) {
		Utils.println(window + "startDoorBellAlertDialog");
		doorBellDialog = doorBellMainDialog(isEnabled);

		if (doorBellDialog!=null){
			new Thread() {
				public void run() {
					// Door bell sound
					SoundEffect.DOORBELL.play();
				}
			}.start();
			
			UIRequest out = new UIRequest(SharedResources.currentUser, doorBellDialog,
					LevelRating.high, Locale.ENGLISH, PrivacyLevel.insensible);
			sendUIRequest(out);
		}
	}

	private Form doorBellMainDialog(boolean isEnabled) {
		Utils.println(window + "createDoorBellAlertMainDialog");
		
		if (isEnabled){
			Form f = Form.newSubdialog("Door Alert Message", null);
			new MediaObject(f.getIOControls(), new Label("Door Bell", null), "image/png", IMG_URL+"door_bell.png");					
			Submit ok = new Submit(f.getSubmits(), new Label("OK", null),SUBMISSION_OK_DOORBELL);
			if (SharedResources.currentUser == SharedResources.testUser){
				DerbyInterface di = new DerbyInterface(); 
				try{
					di.init();
					this.doorbellNotificationID = di.addNotification("Door Bell", 0, SafetyClient.DOORBELL_NOTIFICATION);
				}
				catch(Exception e){ e.printStackTrace(); }
			}

			return f;
		}
		
		return null;
	}

	
	private Form errorMainDialog(int action) {
		Utils.println(window + "createErrorMainDialog");
		Form f = Form.newDialog("Error Front Door", new Resource());
		
		if (action==4){
			SimpleOutput errormsg = new SimpleOutput(f.getIOControls(), null, null, "The operation failed.\n\n" +
					"You have to open the door first.");
		}
		else if (action==3){
			SimpleOutput errormsg = new SimpleOutput(f.getIOControls(), null, null, "The operation failed.\n\n" +
					"You have to unlock the door first.");
		}
		else if (action==2){
			SimpleOutput errormsg = new SimpleOutput(f.getIOControls(), null, null, "The operation failed.\n\n" +
					"You have to close the door first.");
		}
		else{
			SimpleOutput errormsg = new SimpleOutput(f.getIOControls(), null, null, "The operation failed.\n\n" +
					"Please try again later.");
		}
		f = submitButtons(f);
		
		return f;
	}

	public void startVisitorDialog(int status) {
		Utils.println(window + "startVisitorAlertDialog");
		visitorDialog = visitorMainDialog(status);

		if (visitorDialog!=null){
			UIRequest out = new UIRequest(SharedResources.currentUser, visitorDialog,
					LevelRating.high, Locale.ENGLISH, PrivacyLevel.insensible);
			sendUIRequest(out);
		}
	}

	private Form visitorMainDialog(int status) {
		Utils.println(window + "createVisitorAlertMainDialog");
		System.out.println("Visitor Alert Message");
/*
		Form f = Form.newMessage("Visitor Alert Message", this.visitorText);
		new MediaObject(f.getIOControls(), new Label("", null), "image/png", IMG_URL+person+".png");					
*/

		Form f = Form.newSubdialog("Visitor Alert Message", null);
		new MediaObject(f.getIOControls(), new Label("", null), "image/png", IMG_URL+person+".png");					
		Submit ok = new Submit(f.getSubmits(), new Label("OK", null),SUBMISSION_OK_VISITOR);

		return f;
	}
	
	public void startDoorDialog(int status) {
		Utils.println(window + "startDoorAlertDialog");
		this.doorStatus = status;
		doorDialog = doorMainDialog(this.doorStatus);

		if (doorDialog!=null){
			new Thread() {
				public void run() {
					// Door is open sound
					SoundEffect.DOOROPEN.play();
				}
			}.start();
			
			UIRequest out = new UIRequest(SharedResources.currentUser, doorDialog,
					LevelRating.high, Locale.ENGLISH, PrivacyLevel.insensible);
			sendUIRequest(out);
		}
	}

	private Form doorMainDialog(int status) {
		Utils.println(window + "createDoorAlertMainDialog");
		if (status==101){
			Form f = Form.newSubdialog("Door Open Alert Message", null);
			new MediaObject(f.getIOControls(), new Label("Door is open", null), "image/png",
					IMG_URL+"door_open.png");					
			Submit ok = new Submit(f.getSubmits(), new Label("OK", null),SUBMISSION_OK_DOOROPEN);
			if (SharedResources.currentUser == SharedResources.testUser){
				DerbyInterface di = new DerbyInterface(); 
				try{
					di.init();
					this.dooropenNotificationID = di.addNotification("Door is open", 0, SafetyClient.DOOROPEN_NOTIFICATION);
				}
				catch(Exception e){ e.printStackTrace(); }
			}

			return f;
		}
		else
			return null;
	}

	
	private Form submitButtons(Form f){
		Submit b1 = new Submit(f.getSubmits(), new Label("Unlock the Door", null),SUBMISSION_UNLOCK);
		Submit b2 = new Submit(f.getSubmits(), new Label("Lock the Door", null), SUBMISSION_LOCK);
		Submit b3 = new Submit(f.getSubmits(), new Label("Open the Door", null), SUBMISSION_OPEN);
		Submit b4 = new Submit(f.getSubmits(), new Label("Close the Door", null), SUBMISSION_CLOSE);
		Submit b5 = new Submit(f.getSubmits(), new Label("Visitors", null), SUBMISSION_VISITOR);
		Submit b6 = new Submit(f.getSubmits(), new Label("Status", null), SUBMISSION_STATUS);
		Submit b7 = new Submit(f.getSubmits(), new Label("Go back", null), SUBMISSION_GOBACK);
		
		return f;
	}
	
	public void setKnockingPerson(String person) {
		System.out.println("********************************************");
		Utils.println(window + " Person knocking the door: " +person);
		System.out.println("********************************************");
		  try{	
			if (person.indexOf("Unknown Person")!=-1){
				person = person.replaceAll(" ", "_");
				//jLabel5.setIcon(new ImageIcon((java.net.URL)SafetyUIClient.class.getResource("/images/"+person+".jpg")));
				//SoundEffect.DOORBELL.play();
				this.visitorText = person.replaceAll("_", " ") + " is knocking on the door.";
				this.person = person;
			}
			else{
				String newperson = person.replaceAll(" ", "_");
				newperson = newperson.substring(newperson.indexOf(":")+2,newperson.length());
				this.person = newperson;
				//SoundEffect.DOORBELL.play();
				this.visitorText = newperson.replaceAll("_", " ") + " is knocking on the door.";
				if (person.indexOf("Formal Caregiver")!=-1){
		    		// Unlock the door
					new Thread() {
						public void run() {
							SafetyClient.unlock(deviceURI);
				    		try {
								Thread.sleep(10*1000);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
				    		// Open the door
				    		SafetyClient.open(deviceURI);
						}
					}.start();
/*
					SafetyClient.unlock(deviceURI);
		    		Thread.sleep(10*1000);
		    		SafetyClient.open(deviceURI);
*/		    		
				}
			}
		  }
		  catch (Exception e) {e.printStackTrace();}
		}

	public void communicationChannelBroken() {
		System.out.println(window + " communicationChannelBroken");
	}

	@Override
	public void dialogAborted(String dialogID) {
		Utils.println(window + " dialogAborted: " + dialogID);
	}

}
