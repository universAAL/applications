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
import java.util.Locale;

import javax.media.j3d.MediaContainer;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;

import org.universAAL.AALapplication.safety_home.service.uiclient.SafetyUIClient;
import org.universAAL.AALapplication.safety_home.service.uiclient.SharedResources;
import org.universAAL.AALapplication.safety_home.service.uiclient.UIProvider;
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
	   DOORBELL("door_bell.wav");
	   
	   // Nested class for specifying volume
	   public static enum Volume {
	      MUTE, LOW, MEDIUM, HIGH
	   }
	   
	   public static Volume volume = Volume.LOW;
	   
	   // Each sound effect has its own clip, loaded with its own sound file.
	   private Clip clip;
	   
	   // Constructor to construct each element of the enum with its own sound file.
	   SoundEffect(String soundFileName) {
	      try {
	    	  /* Open File and create audio input stream  */
	    	  //File soundFile = new File(soundFileName);
	    	  //AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
	    	  /* Open input stream and create audio input stream  */

	    	  //String curDir = System.getProperty("user.dir");
	    	  //System.out.println("*** "+curDir);
	    	  File confHome = new File(new BundleConfigHome("safety").getAbsolutePath());
	    	  //System.out.println("*** "+confHome.getAbsolutePath() + "  soundfilename="+soundFileName);
	    	  String filePath = confHome.getAbsolutePath() + File.separator + "sounds" + File.separator + soundFileName;
	    	  /*
	    	  URI filePath=null;
	    	  try {
	    		  filePath = ((java.net.URL)UIProvider.class.getResource("/sounds/"+soundFileName)).toURI();
	    		  System.out.println("*** "+filePath.toString());
	    	  } catch (URISyntaxException e) {
	    		  // TODO Auto-generated catch block
	    		  e.printStackTrace();
	    	  }
	    	  */
	    	  File soundFile = new 	File(filePath);
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

	private final static String window = "UIDoor#";
	static final String MY_UI_NAMESPACE = SharedResources.CLIENT_SAFETY_UI_NAMESPACE + window;
    static final String SUBMISSION_UNLOCK = MY_UI_NAMESPACE + "unlock";
    static final String SUBMISSION_LOCK = MY_UI_NAMESPACE + "lock";
    static final String SUBMISSION_OPEN = MY_UI_NAMESPACE + "open";
    static final String SUBMISSION_CLOSE = MY_UI_NAMESPACE + "close";
    static final String SUBMISSION_VISITOR = MY_UI_NAMESPACE + "visitor";
    static final String SUBMISSION_STATUS = MY_UI_NAMESPACE + "status";
	static final String SUBMISSION_GOBACK = MY_UI_NAMESPACE + "back";

    public static String deviceURI = "http://ontology.universaal.org/SafetyServer.owl#controlledDevice0";
	private Form mainDialog = null;
	private Form unlockDialog = null;
	private Form lockDialog = null;
	private Form openDialog = null;
	private Form closeDialog = null;
	private Form visitorDialog = null;
	private Form statusDialog = null;
	private Form errorDialog = null;
	private String active = ""; 
	private String status = ""; 
	private String visitorText = "";
	private String person = null;
	private static boolean success = false;
	
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
				if (SafetyClient.unlock(deviceURI)){
					this.active=this.status="unlock";
					startUnlockDialog();
/*
					new Thread() {
						public void run() {
							success = SafetyClient.unlock(deviceURI);
						}
					}.start();
*/
				}
				else
					startErrorDialog(1);
				
			} 
			else if (SUBMISSION_LOCK.equals(uir.getSubmissionID())) {
				if (SafetyClient.lock(deviceURI)){
			    	this.active=this.status="lock";
					startLockDialog();
				}
				else
					startErrorDialog(2);
			} 
			else if (SUBMISSION_OPEN.equals(uir.getSubmissionID())) {
				if (SafetyClient.open(deviceURI)){
					this.active=this.status="open";
					startOpenDialog();
				}
				else
					startErrorDialog(3);
			} 
			else if (SUBMISSION_CLOSE.equals(uir.getSubmissionID())) {
				if (SafetyClient.close(deviceURI)){
					this.active=this.status="close";
					startCloseDialog();
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
		}
		if (this.active.equals(""))
			startMainDialog();
		Utils.println(window + " Continues");
	}
	
	public void startMainDialog() {
		Utils.println(window + "startMainDialog");
		if (mainDialog == null)
			mainDialog = initMainDialog();

		UIRequest out = new UIRequest(SharedResources.testUser, mainDialog,
				LevelRating.middle, Locale.ENGLISH, PrivacyLevel.insensible);
		sendUIRequest(out);
	}

	public void startStatusDialog() {
		Utils.println(window + "startStatusDialog");
		statusDialog = statusMainDialog();

		UIRequest out = new UIRequest(SharedResources.testUser, statusDialog,
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
			new MediaObject(f.getIOControls(), new Label("Unlocked Door", null), "image/jpeg",
				((java.net.URL)UIProvider.class.getResource("/images/door_unlock.jpg")).toString());
		}
		else if (this.status.equals("lock")){
			new MediaObject(f.getIOControls(), new Label("Locked Door", null), "image/jpeg",
				((java.net.URL)UIProvider.class.getResource("/images/door_lock.jpg")).toString());
		}
		else if (this.status.equals("open")){
			new MediaObject(f.getIOControls(), new Label("Open Door", null), "image/jpeg",
				((java.net.URL)UIProvider.class.getResource("/images/door_open.jpg")).toString());
		}
		else if (this.status.equals("close")){
			new MediaObject(f.getIOControls(), new Label("Closed Door", null), "image/jpeg",
				((java.net.URL)UIProvider.class.getResource("/images/door_close.jpg")).toString());
		}

		f = submitButtons(f);
		
		return f;
	}
	
	public void startVisitorDialog() {
		Utils.println(window + "startVisitorDialog");
		visitorDialog = visitorMainDialog();

		UIRequest out = new UIRequest(SharedResources.testUser, visitorDialog,
				LevelRating.middle, Locale.ENGLISH, PrivacyLevel.insensible);
		sendUIRequest(out);
	}

	public void startUnlockDialog() {
		Utils.println(window + "startUnlockDialog");
		unlockDialog = unlockMainDialog();

		UIRequest out = new UIRequest(SharedResources.testUser, unlockDialog,
				LevelRating.middle, Locale.ENGLISH, PrivacyLevel.insensible);
		
		sendUIRequest(out);
	}

	public void startLockDialog() {
		Utils.println(window + "startLockDialog");
		lockDialog = lockMainDialog();

		UIRequest out = new UIRequest(SharedResources.testUser, lockDialog,
				LevelRating.middle, Locale.ENGLISH, PrivacyLevel.insensible);
		sendUIRequest(out);
	}

	public void startOpenDialog() {
		Utils.println(window + "startOpenDialog");
		openDialog = openMainDialog();

		UIRequest out = new UIRequest(SharedResources.testUser, openDialog,
				LevelRating.middle, Locale.ENGLISH, PrivacyLevel.insensible);
		sendUIRequest(out);
	}
	
	public void startCloseDialog() {
		Utils.println(window + "startCloseDialog");
		closeDialog = closeMainDialog();

		UIRequest out = new UIRequest(SharedResources.testUser, closeDialog,
				LevelRating.middle, Locale.ENGLISH, PrivacyLevel.insensible);
		sendUIRequest(out);
	}

	public void startErrorDialog(int action) {
		Utils.println(window + "startErrorDialog");
		errorDialog = errorMainDialog(action);

		UIRequest out = new UIRequest(SharedResources.testUser, errorDialog,
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
			new MediaObject(f.getIOControls(), new Label(this.visitorText, null), "image/jpeg",
			((java.net.URL)UIProvider.class.getResource("/images/"+person+".jpg")).toString());
			
			SoundEffect.DOORBELL.play();
		}


		f = submitButtons(f);
		
		return f;
	}

	private Form unlockMainDialog() {
		Utils.println(window + "createUnlockMainDialog");
		Form f = Form.newDialog("Unlock Front Door", new Resource());
		
		new MediaObject(f.getIOControls(), new Label("Unlocked Door", null), "image/jpeg",
				((java.net.URL)UIProvider.class.getResource("/images/door_unlock.jpg")).toString());
		SoundEffect.UNLOCK.play();
		
		f = submitButtons(f);
		
		return f;
	}

	private Form lockMainDialog() {
		Utils.println(window + "createLockMainDialog");
		Form f = Form.newDialog("Lock Front Door", new Resource());
		
		new MediaObject(f.getIOControls(), new Label("Locked Door", null), "image/jpeg",
				((java.net.URL)UIProvider.class.getResource("/images/door_lock.jpg")).toString());
		SoundEffect.LOCK.play();

		f = submitButtons(f);
		
		return f;
	}

	private Form openMainDialog() {
		Utils.println(window + "createOpenMainDialog");
		Form f = Form.newDialog("Open Front Door", new Resource());
		
		new MediaObject(f.getIOControls(), new Label("Open Door", null), "image/jpeg",
				((java.net.URL)UIProvider.class.getResource("/images/door_open.jpg")).toString());
		SoundEffect.OPEN.play();

		f = submitButtons(f);
		
		return f;
	}

	private Form closeMainDialog() {
		Utils.println(window + "createCloseMainDialog");
		Form f = Form.newDialog("Close Front Door", new Resource());
		
		MediaObject closeIcon = new MediaObject(f.getIOControls(), new Label("Close Door", null), "image/jpeg",
				((java.net.URL)UIProvider.class.getResource("/images/door_close.jpg")).toString());
		//closeIcon.setPreferredResolution(500, 500);
		SoundEffect.CLOSE.play();
		
		f = submitButtons(f);
		
		return f;
	}

	private Form errorMainDialog(int action) {
		Utils.println(window + "createErrorMainDialog");
		Form f = Form.newDialog("Error Front Door", new Resource());
		
		if (action==4){
			SimpleOutput errormsg = new SimpleOutput(f.getIOControls(), null, null, "The operation failed.\n\n" +
					"You have to open the door first.");
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
			UIRequest out = new UIRequest(SharedResources.testUser, visitorDialog,
					LevelRating.middle, Locale.ENGLISH, PrivacyLevel.insensible);
			sendUIRequest(out);
		}
	}

	private Form visitorMainDialog(int status) {
		Utils.println(window + "createVisitorAlertMainDialog");
		
		Form f = Form.newMessage("Visitor Alert Message", this.visitorText);
		new MediaObject(f.getIOControls(), new Label("", null), "image/jpeg",
				((java.net.URL)UIProvider.class.getResource("/images/"+person+".jpg")).toString());

		return f;
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
					SafetyClient.unlock(deviceURI);
		    		Thread.sleep(10*1000);
		    		// Open the door
		    		SafetyClient.open(deviceURI);
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
