/*
	Copyright 2011-2012 TSB, http://www.tsbtecnologias.es
	TSB - Tecnologías para la Salud y el Bienestar
	
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
package org.universAAL.AALfficiency.model;

import java.util.ArrayList;

import org.universAAL.AALfficiency.ProvidedAALfficiencyService;
import org.universAAL.AALfficiency.utils.Setup;
import org.universAAL.middleware.service.CallStatus;
import org.universAAL.middleware.service.ServiceResponse;
import org.universAAL.middleware.service.owls.process.ProcessOutput;
import org.universAAL.ontology.aalfficiency.scores.*;

public class AALfficiencyEngine {
	static final String ACTIVITY_DATA_URI = ProvidedAALfficiencyService.NAMESPACE
		    + "activitydata";
	static final String ELECTRICITY_DATA_URI = ProvidedAALfficiencyService.NAMESPACE
		    + "electricitydata";
	static final String ELECTRICITY_CHALLENGE_URI = ProvidedAALfficiencyService.NAMESPACE
		    + "electricitychallenge";
	static final String ACTIVITY_CHALLENGE_URI = ProvidedAALfficiencyService.NAMESPACE
		    + "activitychallenge";
	static final String ADVICES_URI = ProvidedAALfficiencyService.NAMESPACE
		    + "advices";
	static final String ADVICE_URI = ProvidedAALfficiencyService.NAMESPACE
		    + "advice";
	
	private Setup properties = new Setup();
	
	private AALfficiencyAdvicesModel advices = new AALfficiencyAdvicesModel(new AdviceModel[]{
			new AdviceModel(ADVICES_URI+"0","Electricity","Remember to turn off the lights when leaving a room"),
			new AdviceModel(ADVICES_URI+"1","Electricity","Don't let turn on devices you're not using"),
			new AdviceModel(ADVICES_URI+"2","Activity","Walking 30 minuts a day will help you keep healthy")});
	
	
	
	public AALfficiencyEngine() {
	}
	
	public ServiceResponse getActivityData(){
		ServiceResponse sr = new ServiceResponse(CallStatus.succeeded);
		
		//create the AALfficiency Score
		
		System.out.print("Returning Activity Data");
		
		ChallengeModel  challenge = properties.getActivityChallenge(); 
		
		Challenge c = new Challenge(ACTIVITY_CHALLENGE_URI+"Activity");
		c.setDescription(challenge.getDescription());
		c.setGoal(challenge.getGoal());
		
		ActivityScore activity = new ActivityScore(ACTIVITY_DATA_URI);
		activity.setTodayActivityScore(properties.getTodayActivityScore());
		activity.setTotalActivityScore(properties.getTotalActivityScore());
		activity.setChallenge(c);
		activity.setKcal(properties.getActivityKcal());
		activity.setSteps(properties.getActivitySteps());
				
		// create and add a ProcessOutput-Event that binds the output URI to the
		// created list of lamps
		sr.addOutput(new ProcessOutput(
			ProvidedAALfficiencyService.OUTPUT_ACTIVITY_DATA, activity));
		System.out.print("AALfficiency Service returning Activity Data");
		return sr;
		
	}

	public ServiceResponse getElectricityData(){
		ServiceResponse sr = new ServiceResponse(CallStatus.succeeded);
		
		//create the AALfficiency Score
		
		System.out.print("Returning Electricity Data");
		
		ChallengeModel  challenge = properties.getElectricityChallenge();
		
		Challenge c = new Challenge(ELECTRICITY_CHALLENGE_URI+"Electricity");
		c.setDescription(challenge.getDescription());
		c.setGoal(challenge.getGoal());
		
		ElectricityScore electricity = new ElectricityScore(ELECTRICITY_DATA_URI);
		electricity.setTodayElectricityScore(properties.getTodayElectricScore());
		electricity.setTotalElectricityScore(properties.getTotalElectricScore());
		electricity.setChallenge(c);
		electricity.setSaving(properties.getElectricitySaving());
		
		
		sr.addOutput(new ProcessOutput(
			ProvidedAALfficiencyService.OUTPUT_ELECTRICITY_DATA, electricity));
		System.out.print("AALfficiency Service returning Electricity Data");
		return sr;
		
	}
	
	public ServiceResponse getAdvices(){
		ServiceResponse sr = new ServiceResponse(CallStatus.succeeded);
		
		System.out.print("Returning Advices");
		//create the AALfficiencyChallenges
		AdviceModel[] adv = this.advices.getAdvices();
		ArrayList advices = new ArrayList(adv.length);
		for (int i=0;i<adv.length;i++){
			Advice a = new Advice(adv[i].getURI());
			a.setAdviceType(adv[i].getType());
			a.setText(adv[i].getText());
			advices.add(a);
		}
				
		// create and add a ProcessOutput-Event that binds the output URI to the
		// created list of lamps
		sr.addOutput(new ProcessOutput(
			ProvidedAALfficiencyService.OUTPUT_ADVICES, advices));
		System.out.print("AALfficiency Service returning Advices");
		return sr;
	}
	
	public ServiceResponse getAdviceInfo(String adviceURI){
		ServiceResponse sr = new ServiceResponse(CallStatus.succeeded);
		
		System.out.print("Returning Advice Info");
		//create the AALfficiencyChallenges
		AdviceModel[] chs = this.advices.getAdvices();
		Advice a = new Advice();
		ArrayList advices = new ArrayList(chs.length);
		for (int i=0;i<chs.length;i++){
			if (chs[i].getURI().compareTo(adviceURI)==0){
				a.setText(chs[i].getText());
				a.setAdviceType(chs[i].getType());
				sr.addOutput(new ProcessOutput(
						ProvidedAALfficiencyService.OUTPUT_ADVICE_INFO, a));
				break;
			}
		}
				
		// create and add a ProcessOutput-Event that binds the output URI to the
		// created list of lamps
		System.out.print("AALfficiency Service returning Advice Info");
		return sr;
	}
	
	public ServiceResponse getChallengeInfo(String uri){
		ServiceResponse sr = new ServiceResponse(CallStatus.succeeded);
		
		System.out.print("Returning Challenge Info");
		//create the AALfficiencyChallenges
		Challenge c = new Challenge();
		ChallengeModel challenge = new ChallengeModel();
		
		if (uri.contains("Activity")){
			challenge = properties.getActivityChallenge(); 
			c = new Challenge(ACTIVITY_CHALLENGE_URI+"Activity");
			c.setDescription(challenge.getDescription());
			c.setGoal(challenge.getGoal());
			sr.addOutput(new ProcessOutput(
						ProvidedAALfficiencyService.OUTPUT_ACTIVITY_CHALLENGE, c));
		}
		
		else if (uri.contains("Electricity")){
			challenge = properties.getElectricityChallenge();
			c = new Challenge(ELECTRICITY_CHALLENGE_URI+"Electricity");
			c.setDescription(challenge.getDescription());
			c.setGoal(challenge.getGoal());
			//c.setChallengeType(this.electricity.getChallenge().getType());
			sr.addOutput(new ProcessOutput(
					ProvidedAALfficiencyService.OUTPUT_ELECTRICITY_CHALLENGE, c));
		}
		
				
		// create and add a ProcessOutput-Event that binds the output URI to the
		// created list of lamps
		System.out.print("AALfficiency Service returning Challenge Info");
		return sr;
	}
}
