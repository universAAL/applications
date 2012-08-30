package org.universAAL.AALfficiency.model;

import java.util.ArrayList;

import org.universAAL.AALfficiency.ProvidedAALfficiencyService;
import org.universAAL.middleware.service.CallStatus;
import org.universAAL.middleware.service.ServiceResponse;
import org.universAAL.middleware.service.owls.process.ProcessOutput;
import org.universAAL.ontology.aalfficiency.scores.*;

public class AALfficiencyEngine {
	static final String ACTIVITY_DATA_URI = ProvidedAALfficiencyService.NAMESPACE
		    + "activitydata";
	static final String ELECTRICITY_DATA_URI = ProvidedAALfficiencyService.NAMESPACE
		    + "electricitydata";
	static final String CHALLENGE_URI = ProvidedAALfficiencyService.NAMESPACE
		    + "challenge";
	static final String ADVICES_URI = ProvidedAALfficiencyService.NAMESPACE
		    + "advices";
	static final String ADVICE_URI = ProvidedAALfficiencyService.NAMESPACE
		    + "advice";
	
	private AALfficiencyAdvicesModel advices = new AALfficiencyAdvicesModel(new AdviceModel[]{
			new AdviceModel(ADVICES_URI+"0","Electricity","Remember to turn off the lights when leaving a room"),
			new AdviceModel(ADVICES_URI+"1","Electricity","Don't let turn on devices you're not using"),
			new AdviceModel(ADVICES_URI+"2","Activity","Walking 30 minuts a day will help you keep healthy")});
	
	private ActivityScoreModel activity = new ActivityScoreModel(10,60,200,150,new ChallengeModel
			(CHALLENGE_URI+"0","Activity","1000","Walk 1000 steps a day and burn 500 Kcal", "true"),"false");
	
	private ElectricityScoreModel electricity = new ElectricityScoreModel(14,104,12,new ChallengeModel
			(CHALLENGE_URI+"1","Electricity","20","Reduce a 20% consumption on big consumptions", "true"),"false");
	
	
	public AALfficiencyEngine() {
	}
	
	/*public ServiceResponse getActivityData(){
		ServiceResponse sr = new ServiceResponse(CallStatus.succeeded);
		
		//create the AALfficiency Score
		
		System.out.print("Returning Activity Data");
		
		ActivityScore activity = new ActivityScore(ACTIVITY_DATA_URI);
		activity.setActivityTodayScore(this.activity.getTodayScore());
		activity.setActivityTotalScore(this.activity.getTotalScore());
		activity.(this.activity.getChallenge().getURI(),this.activity.getChallenge().getType(),
				String.valueOf(this.activity.getChallenge().getGoal()), this.activity.getChallenge().getDescription());
		activity.setKcal(this.activity.getKcal());
		activity.setSteps(this.activity.getSteps());
		activity.setSentByPublisher(this.activity.getSentByPublisher());
				
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
		
		ElectricityData electricity = new ElectricityData(ELECTRICITY_DATA_URI);
		electricity.setTodayScore(this.electricity.getTodayScore());
		electricity.setTotalScore(this.electricity.getTotalScore());
		electricity.setChallenge(this.electricity.getChallenge().getURI(),this.electricity.getChallenge().getType(),
				String.valueOf(this.electricity.getChallenge().getGoal()), this.electricity.getChallenge().getDescription());
		electricity.setSaving(this.electricity.getSaving());
		electricity.setSentByPublisher(this.electricity.getSentByPublisher());
				
		// create and add a ProcessOutput-Event that binds the output URI to the
		// created list of lamps
		sr.addOutput(new ProcessOutput(
			ProvidedAALfficiencyService.OUTPUT_ACTIVITY_DATA, activity));
		System.out.print("AALfficiency Service returning Activity Data");
		return sr;
		
	}*/
	
	public ServiceResponse getAdvices(){
		ServiceResponse sr = new ServiceResponse(CallStatus.succeeded);
		
		System.out.print("Returning Advices");
		//create the AALfficiencyChallenges
		AdviceModel[] adv = this.advices.getAdvices();
		ArrayList advices = new ArrayList(adv.length);
		for (int i=0;i<adv.length;i++){
			Advice a = new Advice(adv[i].getURI(), adv[i].getType(), adv[i].getText());
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
				a.setAdviceText(chs[i].getText());
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
		
		if (this.activity.getChallenge().getURI().compareTo(uri)==0){
			c.setChallengeDescription(this.activity.getChallenge().getDescription());
			c.setChallengeGoal(String.valueOf(this.activity.getChallenge().getGoal()));
			//c.(this.activity.getChallenge().getType());
			sr.addOutput(new ProcessOutput(
						ProvidedAALfficiencyService.OUTPUT_CHALLENGE, c));
		}
		
		else if (this.electricity.getChallenge().getURI().compareTo(uri)==0){
			c.setChallengeDescription(this.electricity.getChallenge().getDescription());
			c.setChallengeGoal(String.valueOf(this.electricity.getChallenge().getGoal()));
			//c.setChallengeType(this.electricity.getChallenge().getType());
			sr.addOutput(new ProcessOutput(
					ProvidedAALfficiencyService.OUTPUT_CHALLENGE, c));
		}
		
				
		// create and add a ProcessOutput-Event that binds the output URI to the
		// created list of lamps
		System.out.print("AALfficiency Service returning Challenge Info");
		return sr;
	}
}
