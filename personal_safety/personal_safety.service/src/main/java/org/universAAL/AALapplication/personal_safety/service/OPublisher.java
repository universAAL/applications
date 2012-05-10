/*
	Copyright 2008-2010 ITACA-TSB, http://www.tsb.upv.es
	Instituto Tecnologico de Aplicaciones de Comunicacion 
	Avanzadas - Grupo Tecnologias para la Salud y el 
	Bienestar (TSB)
	
	See the NOTICE file distributed with this work for additional 
	information regarding copyright ownership
	
	Licensed under the Apache License, Version 2.0 (the "License");
	you may not use this file except in compliance with the License.
	You may obtain a copy of the License at
	
	  http://www.apache.org/licenses/LICENSE-2.0
	
	Unless required by applicable law or agreed to in writing, software
	distributed under the License is distributed on an "AS IS" BASIS,
	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
	See the License for the specific language governing permissions and
	limitations under the License.
 */
package org.universAAL.AALapplication.personal_safety.service;

import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.ui.UICaller;
import org.universAAL.middleware.ui.UIRequest;
import org.universAAL.middleware.ui.UIResponse;
import org.universAAL.middleware.ui.owl.PrivacyLevel;
import org.universAAL.middleware.ui.rdf.Form;
import org.universAAL.middleware.owl.supply.LevelRating;
import org.universAAL.ontology.profile.User;

//import sun.audio.AudioPlayer;
//import sun.audio.AudioStream;

/**
 * @author <a href="mailto:alfiva@itaca.upv.es">Alvaro Fides Valero</a>
 *
 */
public class OPublisher extends UICaller{
	
	private final static Logger log=LoggerFactory.getLogger(OPublisher.class);
	protected static Timer responseWatch;
	
	protected OPublisher(ModuleContext context) {
		super(context);
	}

	public void communicationChannelBroken() {
		// TODO Auto-generated method stub
		
	}
	
	public void showButtonScreenForm(User user)
	{
		log.debug("Show button screen - Start delay timer");
		responseWatch=new Timer("Risk_ResponseTimer");
		responseWatch.schedule(new ResponseDelayTask(user), Long.parseLong(Main.getProperties().getProperty(Main.DELAY,"1"))*60000);
		Form f = Main.gui.getUserStateForm();
		UIRequest oe = new UIRequest(user,f,LevelRating.high,Locale.ENGLISH,PrivacyLevel.insensible);
		sendUIRequest(oe);
		playWarning();
	}
	
	public void showSMSForm(User user, boolean smsSuccess)
	{
		log.debug("Show SMS screen");
		Form f = Main.gui.getSMSForm(smsSuccess);
		UIRequest oe = new UIRequest(user,f,LevelRating.full,Locale.ENGLISH,PrivacyLevel.insensible);
		sendUIRequest(oe);
		playWarning();
	}
	
	public void showNoVCForm(User user)
	{
		log.debug("Show VC failed screen");
		Form f = Main.gui.getNoVCForm();
		UIRequest oe = new UIRequest(user,f,LevelRating.full,Locale.ENGLISH,PrivacyLevel.insensible);
		sendUIRequest(oe);
	}
	
	public void showBatteryForm(User user)
	{
		log.debug("Show Battery message");
		Form f = Main.gui.getBatteryForm();
		UIRequest oe = new UIRequest(user,f,LevelRating.middle,Locale.ENGLISH,PrivacyLevel.insensible);
		sendUIRequest(oe);
	}
	
	private static class ResponseDelayTask extends TimerTask{
		public static User user;
		public ResponseDelayTask(User usr){
			user=usr;
		}
		public void run() {
			log.debug("Did not cancel risk situation");
			if(Boolean.parseBoolean(Main.getProperties().getProperty(Main.SMSENABLE,"false"))){
				boolean sent=Main.rcaller.sendRiskSMSText();
				Main.routput.showSMSForm(user, sent);
			}
			if(Boolean.parseBoolean(Main.getProperties().getProperty(Main.VCENABLE,"false"))){
				boolean call=Main.rcaller.startVideoCall();
				if(!call)Main.routput.showNoVCForm(user);
			}
			OPublisher.responseWatch.cancel();
		}
	}
	
	private static void playWarning(){
/*
		Calendar now=Calendar.getInstance();
		if(8<now.get(Calendar.HOUR_OF_DAY) && now.get(Calendar.HOUR_OF_DAY)<23)
		new Thread(){
			public void run(){
				InputStream audio=null;
				try {
					audio=new FileInputStream("panic.wav");
				} catch (Exception e) {
					try {
						audio=new FileInputStream("warn.wav");
					} catch (Exception ex) {
						log.error("Audio file not found (panic.wav or warn.wav in runner folder)");
					}
				}
				try {
					//AudioStream as=new AudioStream((audio!=null)?audio:getClass().getClassLoader().getResourceAsStream("warn.wav"));
					//AudioPlayer.player.start(as);
					Thread.sleep(3000);
					//AudioPlayer.player.stop(as);
					//as.close();
					//as=null;
				} catch (Exception e) {
					e.printStackTrace();
				}				
			}
		}.start();	
		else
			log.warn("Too late to play a sound");
*/
		}

	public void dialogAborted(String dialogID) {
		// TODO Auto-generated method stub
		
	}

	public void handleUIResponse(UIResponse event) {
		User user=(User) event.getUser();
		log.info("Received an Input Event from user {}", user.getURI());
		String submit=event.getSubmissionID();

		try{
			if(submit.equals(RiskGUI.SUBMIT_HOME)){
				log.debug("Input received was go Home");
				//do nothing-> return to main menu
			}else if(submit.equals(RiskGUI.SUBMIT_CANCEL)){
				log.debug("Input received was Cancel (abort risk)");
				OPublisher.responseWatch.cancel();
			}
		}catch(Exception e){
			log.error("Error while processing the user input: {}",e);
		}
		
	}
}
