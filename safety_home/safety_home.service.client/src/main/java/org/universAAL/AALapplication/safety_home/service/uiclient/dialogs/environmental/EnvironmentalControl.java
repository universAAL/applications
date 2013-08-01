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

package org.universAAL.AALapplication.safety_home.service.uiclient.dialogs.environmental;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Locale;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import org.universAAL.AALapplication.safety_home.service.uiclient.SafetyClient;
import org.universAAL.AALapplication.safety_home.service.uiclient.SharedResources;
import org.universAAL.AALapplication.safety_home.service.uiclient.UIProvider;
import org.universAAL.AALapplication.safety_home.service.uiclient.utils.Utils;
import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.container.osgi.util.BundleConfigHome;
import org.universAAL.middleware.owl.supply.LevelRating;
import org.universAAL.middleware.rdf.Resource;
import org.universAAL.middleware.ui.UICaller;
import org.universAAL.middleware.ui.UIRequest;
import org.universAAL.middleware.ui.UIResponse;
import org.universAAL.middleware.ui.owl.PrivacyLevel;
import org.universAAL.middleware.ui.rdf.Form;
import org.universAAL.middleware.ui.rdf.Group;
import org.universAAL.middleware.ui.rdf.Label;
import org.universAAL.middleware.ui.rdf.MediaObject;
import org.universAAL.middleware.ui.rdf.SimpleOutput;
import org.universAAL.middleware.ui.rdf.Submit;

/**
 * @author dimokas
 * 
 */

enum SoundEffect {
	
	   SMOKE("smoke_detection.wav"),
	   MOTION("motion_detection.wav"),
	   WINDOW("window_open.wav");
	   
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
	    	  String curDir = System.getProperty("user.dir");
	    	  //System.out.println("*** "+curDir);
	    	  File confHome = new File(new BundleConfigHome("safety").getAbsolutePath());
	    	  //System.out.println("*** "+confHome.getAbsolutePath());
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
	    	  File soundFile = new File(filePath);
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


public class EnvironmentalControl extends UICaller {

	private final static String window = "UIEnvironmental#";
	static final String MY_UI_NAMESPACE = SharedResources.CLIENT_SAFETY_UI_NAMESPACE + window;
    static final String SUBMISSION_WINDOW = MY_UI_NAMESPACE + "window";
    static final String SUBMISSION_LIGHTS = MY_UI_NAMESPACE + "lights";
    static final String SUBMISSION_TEMPERATURE = MY_UI_NAMESPACE + "temperature";
    static final String SUBMISSION_HUMIDITY = MY_UI_NAMESPACE + "humidity";
    static final String SUBMISSION_MOTION = MY_UI_NAMESPACE + "motion";
    static final String SUBMISSION_LAMP = MY_UI_NAMESPACE + "lamp";
    static final String SUBMISSION_HEATING = MY_UI_NAMESPACE + "heating";
	static final String SUBMISSION_GOBACK = MY_UI_NAMESPACE + "back";
    static final String SUBMISSION_TURN_ON_LAMP = MY_UI_NAMESPACE + "turnonlamp";
    static final String SUBMISSION_TURN_OFF_LAMP = MY_UI_NAMESPACE + "turnofflamp";
    static final String SUBMISSION_TURN_ON_HEATING = MY_UI_NAMESPACE + "turnonheating";
    static final String SUBMISSION_TURN_OFF_HEATING = MY_UI_NAMESPACE + "turnoffheating";
    static String lampURI = "http://ontology.universaal.org/SafetyServer.owl#controlledLamp0";
    static String heatingURI = "http://ontology.universaal.org/SafetyServer.owl#controlledHeating0";

	private Form mainDialog = null;
	private Form windowDialog = null;
	private Form lightsDialog = null;
	private Form smokeDialog = null;
	private Form tempDialog = null;
	private Form humidityDialog = null;
	private Form motionDialog = null;
	private Form lampDialog = null;
	private Form heatingDialog = null;
	private String active = ""; 
	
	private int lightStatus = 0;
	private int windowStatus = 0;
	private float temperature = 0;
	private float humidity = 0;
	private double motion = 0;
	private boolean smoke = false;
	private String motionVal = "Motion sensor";
	private int motionWarnings = 0;
	private int lampStatus = 0;
	private int heatingStatus = 0;
	
	public EnvironmentalControl(ModuleContext context) {
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
			if (SUBMISSION_WINDOW.equals(uir.getSubmissionID())) {
				this.active="window";
				this.startWindowDialog();
			} 
			else if (SUBMISSION_TEMPERATURE.equals(uir.getSubmissionID())) {
		    	this.active="temperature";
		    	this.startTemperatureDialog();
			} 
			else if (SUBMISSION_LIGHTS.equals(uir.getSubmissionID())) {
				this.active="lights";
				this.startLightsDialog();
			} 
			else if (SUBMISSION_HUMIDITY.equals(uir.getSubmissionID())) {
				this.active="humidity";
				this.startHumidityDialog();
			} 
			else if (SUBMISSION_MOTION.equals(uir.getSubmissionID())) {
				this.active="motion";
				this.startMotionDialog();
			} 
			else if (SUBMISSION_LAMP.equals(uir.getSubmissionID())) {
				this.active="lamp";
				this.startLampDialog();
			} 
			else if (SUBMISSION_TURN_ON_LAMP.equals(uir.getSubmissionID())) {
				this.active="lamp";
			
				new Thread() {
					public void run() {
						// Turn on the Lamp
						boolean b = SafetyClient.turnOn(lampURI);
						if (b)
							lampStatus = 1;
					}
				}.start();

				this.lampStatus = 1;
				this.startLampDialog();
			} 
			else if (SUBMISSION_TURN_OFF_LAMP.equals(uir.getSubmissionID())) {
				this.active="lamp";
			
				new Thread() {
					public void run() {
						// Turn off the Lamp
						boolean b = SafetyClient.turnOff(lampURI);
						if (b)
							lampStatus = 0;

					}
				}.start();

				this.lampStatus = 0;
				this.startLampDialog();
			} 
			else if (SUBMISSION_HEATING.equals(uir.getSubmissionID())) {
				this.active="heating";
				this.startHeatingDialog();
			} 
			else if (SUBMISSION_TURN_ON_HEATING.equals(uir.getSubmissionID())) {
				this.active="heating";
			
				new Thread() {
					public void run() {
						// Turn on the Heating
						boolean b = SafetyClient.turnOnHeating(heatingURI);
						if (b)
							heatingStatus = 1;
					}
				}.start();

				this.heatingStatus = 1;
				this.startHeatingDialog();
			} 
			else if (SUBMISSION_TURN_OFF_HEATING.equals(uir.getSubmissionID())) {
				this.active="heating";
			
				new Thread() {
					public void run() {
						// Turn off the Heating
						boolean b = SafetyClient.turnOffHeating(heatingURI);
						if (b)
							heatingStatus = 0;

					}
				}.start();

				this.heatingStatus = 0;
				this.startHeatingDialog();
			} 
		}
		Utils.println(window + " Continues");
	    //startMainDialog();
	}
	
	public void startWindowDialog() {
		Utils.println(window + "startWindowDialog");
		windowDialog = windowMainDialog();

		UIRequest out = new UIRequest(SharedResources.testUser, windowDialog,
				LevelRating.middle, Locale.ENGLISH, PrivacyLevel.insensible);
		sendUIRequest(out);
	}

	private Form windowMainDialog() {
		Utils.println(window + "createWindowMainDialog");
		Form f = Form.newDialog("Window", new Resource());
		
		if (this.windowStatus==0){
			Group g1 = new Group(f.getIOControls(), new Label("Window",
				      (String) null), null, null, (Resource) null);
			new MediaObject(g1, new Label("Window is closed", null), "image/png",
				((java.net.URL)UIProvider.class.getResource("/images/closed_window.png")).toString());
		}
		if (this.windowStatus==100){
			Group g1 = new Group(f.getIOControls(), new Label("Window",
				      (String) null), null, null, (Resource) null);
			new MediaObject(g1, new Label("Window is open", null), "image/png",
				((java.net.URL)UIProvider.class.getResource("/images/opened_window.png")).toString());
		}
		
		f = submitButtons(f);
		
		return f;
	}

	public void startWindowDialog(int status) {
		Utils.println(window + "startWindowAlertDialog");
		this.windowStatus = status;
		windowDialog = windowMainDialog(this.windowStatus);

		if (windowDialog!=null){
			new Thread() {
				public void run() {
					// Window is open sound
					SoundEffect.WINDOW.play();
				}
			}.start();
			
			UIRequest out = new UIRequest(SharedResources.testUser, windowDialog,
					LevelRating.middle, Locale.ENGLISH, PrivacyLevel.insensible);
			sendUIRequest(out);
		}
	}

	private Form windowMainDialog(int status) {
		Utils.println(window + "createWindowAlertMainDialog");
		
		if (status==100){
			Form f = Form.newMessage("Window Alert Message", "Window is open");
			new MediaObject(f.getIOControls(), new Label("Window", null), "image/png",
					((java.net.URL)UIProvider.class.getResource("/images/opened_window.png")).toString());
			return f;
		}
		
		return null;
	}

	public void startLightsDialog() {
		Utils.println(window + "startLightsDialog");
		lightsDialog = lightsMainDialog();

		UIRequest out = new UIRequest(SharedResources.testUser, lightsDialog,
				LevelRating.middle, Locale.ENGLISH, PrivacyLevel.insensible);
		sendUIRequest(out);
	}

	public void startLightsDialog(int status) {
		Utils.println(window + "startLightsAlertDialog");
		this.lightStatus = status;
		lightsDialog = lightsMainDialog(this.lightStatus);

		if (lightsDialog!=null){
			UIRequest out = new UIRequest(SharedResources.testUser, lightsDialog,
					LevelRating.middle, Locale.ENGLISH, PrivacyLevel.insensible);
			sendUIRequest(out);
		}
	}

	private Form lightsMainDialog() {
		Utils.println(window + "createLightsMainDialog");
		Form f = Form.newDialog("Lights", new Resource());
		//Form f = Form.newSubdialog("Lights", mainDialog.getURI());
		
		if (this.lightStatus==0){
			Group g1 = new Group(f.getIOControls(), new Label("Lights",
				      (String) null), null, null, (Resource) null);
			new MediaObject(g1, new Label("Lights", null), "image/png",
				((java.net.URL)UIProvider.class.getResource("/images/light_off.png")).toString());
		}
		if (this.lightStatus==1000){
			Group g1 = new Group(f.getIOControls(), new Label("Lights",
				      (String) null), null, null, (Resource) null);
			new MediaObject(g1, new Label("Lights", null), "image/png",
				((java.net.URL)UIProvider.class.getResource("/images/light_on.png")).toString());
		}

		f = submitButtons(f);
		
		return f;
	}

	private Form lightsMainDialog(int status) {
		Utils.println(window + "createLightsAlertMainDialog");
		
		if (status==1000){
			Form f = Form.newMessage("Lights Alert Message", "Lights are on");
			new MediaObject(f.getIOControls(), new Label("", null), "image/png",
					((java.net.URL)UIProvider.class.getResource("/images/light_on.png")).toString());
			return f;
		}
		return null;
	}

	public void startTemperatureDialog() {
		Utils.println(window + "startTemperatureDialog");
		tempDialog = temperatureMainDialog();

		UIRequest out = new UIRequest(SharedResources.testUser, tempDialog,
				LevelRating.middle, Locale.ENGLISH, PrivacyLevel.insensible);
		sendUIRequest(out);
	}

	private Form temperatureMainDialog() {
		Utils.println(window + "createTemperatureMainDialog");
		Form f = Form.newDialog("Temperature", new Resource());
		
		if (this.temperature!=0){
			Group g1 = new Group(f.getIOControls(), new Label("Temperature",
				      (String) null), null, null, (Resource) null);
			new MediaObject(g1, new Label("Temperature is "+this.temperature, null), "image/png",
				((java.net.URL)UIProvider.class.getResource("/images/temperature.png")).toString());
		}
		else{
			Group g1 = new Group(f.getIOControls(), new Label("Temperature",
				      (String) null), null, null, (Resource) null);
			new MediaObject(g1, new Label("", null), "image/png",
				((java.net.URL)UIProvider.class.getResource("/images/temperature.png")).toString());
		}
		f = submitButtons(f);
		
		return f;
	}

	public void startHumidityDialog() {
		Utils.println(window + "startHumidityDialog");
		humidityDialog = humidityMainDialog();

		UIRequest out = new UIRequest(SharedResources.testUser, humidityDialog,
				LevelRating.middle, Locale.ENGLISH, PrivacyLevel.insensible);
		sendUIRequest(out);
	}
	
	private Form humidityMainDialog() {
		Utils.println(window + "createHumidityMainDialog");
		Form f = Form.newDialog("Humidity", new Resource());
		//Form f = Form.newSubdialog("Humidity", mainDialog.getURI());
		if (this.humidity!=0){
			Group g1 = new Group(f.getIOControls(), new Label("Humidity",
				      (String) null), null, null, (Resource) null);
			new MediaObject(g1, new Label("Humidity is "+this.humidity, null), "image/png",
				((java.net.URL)UIProvider.class.getResource("/images/humidity.png")).toString());
		}
		else{
			Group g1 = new Group(f.getIOControls(), new Label("Humidity",
				      (String) null), null, null, (Resource) null);
			new MediaObject(g1, new Label("Humidity", null), "image/png",
				((java.net.URL)UIProvider.class.getResource("/images/humidity.png")).toString());
		}
		f = submitButtons(f);
		
		return f;
	}
	
	public void startHeatingDialog() {
		Utils.println(window + "startHeatingDialog");
		heatingDialog = heatingMainDialog();
		
		UIRequest out = new UIRequest(SharedResources.testUser, heatingDialog,
				LevelRating.middle, Locale.ENGLISH, PrivacyLevel.insensible);
		sendUIRequest(out);
	}

	private Form heatingMainDialog() {
		Utils.println(window + "createHeatingMainDialog");
		Form f = Form.newDialog("Heating", new Resource());
		
		if (heatingStatus==0){
			Group g2 = new Group(f.getIOControls(), new Label("Heating",
				      (String) null), null, null, (Resource) null);
			Submit turnOff = new Submit(g2, new Label("", ((java.net.URL)UIProvider.class.getResource("/images/heating_off.png")).toString()), SUBMISSION_TURN_ON_HEATING);
		}
		else if (heatingStatus==1){
			Group g1 = new Group(f.getIOControls(), new Label("Heating",
				      (String) null), null, null, (Resource) null);
			Submit turnOn = new Submit(g1, new Label("", ((java.net.URL)UIProvider.class.getResource("/images/heating_on.png")).toString()), SUBMISSION_TURN_OFF_HEATING);
		}
		
		f = submitButtons(f);
		
		return f;
	}
	
	public void startLampDialog() {
		Utils.println(window + "startLampDialog");
		lampDialog = lampMainDialog();
		
		UIRequest out = new UIRequest(SharedResources.testUser, lampDialog,
				LevelRating.middle, Locale.ENGLISH, PrivacyLevel.insensible);
		sendUIRequest(out);
	}

	private Form lampMainDialog() {
		Utils.println(window + "createLampMainDialog");
		Form f = Form.newDialog("Lamp", new Resource());
		
		if (lampStatus==0){
			Group g2 = new Group(f.getIOControls(), new Label("Lamp",
				      (String) null), null, null, (Resource) null);
			Submit turnOff = new Submit(g2, new Label("", ((java.net.URL)UIProvider.class.getResource("/images/light_off.png")).toString()), SUBMISSION_TURN_ON_LAMP);
			//Submit turnOff = new Submit(g2, new Label("", ((java.net.URL)UIProvider.class.getResource("/images/light_off.png")).toString()), SUBMISSION_TURN_OFF_LAMP);
		}
		else if (lampStatus==1){
			Group g1 = new Group(f.getIOControls(), new Label("Lamp",
				      (String) null), null, null, (Resource) null);
			Submit turnOn = new Submit(g1, new Label("", ((java.net.URL)UIProvider.class.getResource("/images/light_on.png")).toString()), SUBMISSION_TURN_OFF_LAMP);
			//Submit turnOn = new Submit(g1, new Label("", ((java.net.URL)UIProvider.class.getResource("/images/light_on.png")).toString()), SUBMISSION_TURN_ON_LAMP);
		}
		
		f = submitButtons(f);
		
		return f;
	}
	
	public void startMotionDialog() {
		Utils.println(window + "startMotionDialog");
		motionDialog = motionMainDialog();
		
		UIRequest out = new UIRequest(SharedResources.testUser, motionDialog,
				LevelRating.middle, Locale.ENGLISH, PrivacyLevel.insensible);
		sendUIRequest(out);
	}

	private Form motionMainDialog() {
		Utils.println(window + "createMotionMainDialog");
		Form f = Form.newDialog("Motion", new Resource());

		Group g1 = new Group(f.getIOControls(), new Label("Motion Detection",
			      (String) null), null, null, (Resource) null);
		new MediaObject(g1, new Label(this.motionVal, null), "image/png",
			((java.net.URL)UIProvider.class.getResource("/images/motion.png")).toString());

		f = submitButtons(f);
		
		return f;
	}

	public void startMotionDialog(int warning) {
		Utils.println(window + "startMotionAlertDialog");
		motionDialog = motionMainDialog(warning);

		if (motionDialog!=null){
			UIRequest out = new UIRequest(SharedResources.testUser, motionDialog,
				LevelRating.middle, Locale.ENGLISH, PrivacyLevel.insensible);
			sendUIRequest(out);
		}
	}

	private Form motionMainDialog(int warning) {
		Utils.println(window + "createMotionAlertMainDialog");
		
		if (this.motionWarnings==1){
			new Thread() {
				public void run() {
					// Motion detection sound
					SoundEffect.MOTION.play();
				}
			}.start();

			Form f = Form.newMessage("Motion Alert Message", "Motion detected.");
			new MediaObject(f.getIOControls(), new Label(this.motionVal, null), "image/png",
					((java.net.URL)UIProvider.class.getResource("/images/motion.png")).toString());

			return f;
		}
		return null;
	}
	
	public void startSmokeDialog(boolean status) {
		Utils.println(window + "startSmokeAlertDialog");
		this.smoke = status;
		smokeDialog = smokeMainDialog(this.smoke);

		if (smokeDialog!=null){
			new Thread() {
				public void run() {
					// Smoke detection sound
					SoundEffect.SMOKE.play();
				}
			}.start();
			
			UIRequest out = new UIRequest(SharedResources.testUser, smokeDialog,
					LevelRating.middle, Locale.ENGLISH, PrivacyLevel.insensible);
			sendUIRequest(out);
		}
	}

	private Form smokeMainDialog(boolean status) {
		Utils.println(window + "createSmokeAlertMainDialog");
		
		if (status){
			Form f = Form.newMessage("Smoke Alert Message", "Smoke Detection");
			new MediaObject(f.getIOControls(), new Label("", null), "image/png",
					((java.net.URL)UIProvider.class.getResource("/images/smoke.png")).toString());
			return f;
		}
		return null;
	}

	private Form submitButtons(Form f){
		new Submit(f.getSubmits(), new Label("Window", null),SUBMISSION_WINDOW);
		new Submit(f.getSubmits(), new Label("Lights", null), SUBMISSION_LIGHTS);
		new Submit(f.getSubmits(), new Label("Temperature", null), SUBMISSION_TEMPERATURE);
		new Submit(f.getSubmits(), new Label("Humidity", null), SUBMISSION_HUMIDITY);
		new Submit(f.getSubmits(), new Label("Motion", null), SUBMISSION_MOTION);
		new Submit(f.getSubmits(), new Label("Lamp Control", null), SUBMISSION_LAMP);
		new Submit(f.getSubmits(), new Label("Heating Control", null), SUBMISSION_HEATING);
		new Submit(f.getSubmits(), new Label("Go back", null), SUBMISSION_GOBACK);
		
		return f;
	}

	public void startMainDialog() {
		Utils.println(window + "startMainDialog");
		if (mainDialog == null){
			mainDialog = initMainDialog();
		}
		
		UIRequest out = new UIRequest(SharedResources.testUser, mainDialog,
				LevelRating.middle, Locale.ENGLISH, PrivacyLevel.insensible);
		sendUIRequest(out);
	}

	private Form initMainDialog() {
		Utils.println(window + "createMenusMainDialog");
		Form f = Form.newDialog("Environmental Control", new Resource());

		SimpleOutput welcome = new SimpleOutput(f.getIOControls(), null, null, "Welcome to the Environmental Control.\n\n");

		f = submitButtons(f);
		
		return f;
	}

	public int getLightStatus() {
		return lightStatus;
	}
	public void setLightStatus(int lightStatus) {
		this.lightStatus = lightStatus;
	}

	public float getHumidity() {
		return humidity;
	}
	public void setHumidity(float humidity) {
		this.humidity = humidity;
	}

	public double getMotion() {
		return motion;
	}
	public void setMotion(double motion) {
		this.motion = motion;
		long secs = (long)motion/1000;
		long hours = secs / 3600;
		long tmp = secs % 3600;
		long min = tmp / 60;
		long sec = tmp % 60;
		
		if ((motion>0 && motion<120000.0) && (this.motionWarnings==0)){
			//SoundEffect.MOTION.play();
			this.motionWarnings = 1;
		}
		else if (motion > 120000)
			this.motionWarnings = 0;

		if (motion > 0)
			this.motionVal = "Motion detected before \n" + hours + "hours " + min + "min. " + sec + "sec.";
		else
			this.motionVal = "Motion has not been detected\n";
	}
	
	public int getMotionWarnings() {
		return motionWarnings;
	}

	public float getTemperature() {
		return temperature;
	}
	public void setTemperature(float temperature) {
		this.temperature = temperature;
	}

	public int getWindowStatus() {
		return windowStatus;
	}
	public void setWindowStatus(int windowStatus) {
		this.windowStatus = windowStatus;
	}

	public void communicationChannelBroken() {
		Utils.println(window + " communicationChannelBroken");
	}

	@Override
	public void dialogAborted(String dialogID) {
		Utils.println(window + " dialogAborted: " + dialogID);
	}

}
