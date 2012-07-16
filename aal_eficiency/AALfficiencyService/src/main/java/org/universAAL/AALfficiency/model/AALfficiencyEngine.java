package org.universAAL.AALfficiency.model;

import java.util.ArrayList;

import org.universAAL.AALfficiency.ProvidedAALfficiencyService;
import org.universAAL.middleware.service.CallStatus;
import org.universAAL.middleware.service.ServiceResponse;
import org.universAAL.middleware.service.owls.process.ProcessOutput;
import org.universAAL.ontology.aalfficiency.*;

public class AALfficiencyEngine {
	
	private AALfficiencyAdvicesModel advices = new AALfficiencyAdvicesModel(new AdviceModel[]{
			new AdviceModel("Electricity","Remember to turn off the lifghts when leaving a room"),
			new AdviceModel("Electricity","Don't let turn on devices you're not using"),
			new AdviceModel("Activity","Walking 30 minuts a day will help you keep healthy")});
	
	private AALfficiencyScoreModel score=new AALfficiencyScoreModel(157,24,95,13,62,9);
	
	private AALfficiencyChallengesModel challenges = new AALfficiencyChallengesModel(new ChallengeModel[]{
			new ChallengeModel("Electricity","15%","Reduce a 15% consumption on ligthing"),
			new ChallengeModel("Electricity","20%","Reduce a 20% consumption on big consumptions"),
			new ChallengeModel("Activity","1000/500","Walk 1000 steps a day and burn 500 Kcal"),
			}); 
	
	static final String SCORE_URI = ProvidedAALfficiencyService.NAMESPACE
		    + "scores";
	static final String CHALLENGES_URI = ProvidedAALfficiencyService.NAMESPACE
		    + "challenges";
	static final String CHALLENGE_URI = ProvidedAALfficiencyService.NAMESPACE
		    + "challenge";
	static final String ADVICES_URI = ProvidedAALfficiencyService.NAMESPACE
		    + "advices";
	static final String ADVICE_URI = ProvidedAALfficiencyService.NAMESPACE
		    + "advice";
	    	
	public AALfficiencyEngine() {
	}
	
	public ServiceResponse getScore(){
		ServiceResponse sr = new ServiceResponse(CallStatus.succeeded);
		
		//create the AALfficiency Score
		
		System.out.print("Returning Score");
		
		AalfficiencyScore sc = new  AalfficiencyScore(SCORE_URI);
		sc.setTodayScore(this.score.getTodayScore());
		sc.setTotalScore(this.score.getTotalScore());
		sc.setTodayElectricityScore(this.score.getTodayElectricScore());
		sc.setTodayActivityScore(this.score.getTodayActivityScore());
		sc.setTotalElectricityScore(this.score.getTotalElectricScore());
		sc.setTotalActivityScore(this.score.getTodayActivityScore());
				
		// create and add a ProcessOutput-Event that binds the output URI to the
		// created list of lamps
		sr.addOutput(new ProcessOutput(
			ProvidedAALfficiencyService.OUTPUT_SCORE, sc));
		System.out.print("AALfficiency Service returning Score");
		return sr;
		
	}

	public ServiceResponse getChallenges(){
		ServiceResponse sr = new ServiceResponse(CallStatus.succeeded);
		
		//create the AALfficiencyChallenges
		ChallengeModel[] chs = this.challenges.getChallenges();
		ArrayList challenges = new ArrayList(chs.length);
		for (int i=0;i<chs.length;i++){
			Challenge c = new Challenge(
			CHALLENGE_URI+i, chs[i].getType(), chs[i].getGoal(), chs[i].getDescription());
			challenges.add(c);
		}
				
		// create and add a ProcessOutput-Event that binds the output URI to the
		// created list of lamps
		sr.addOutput(new ProcessOutput(
			ProvidedAALfficiencyService.OUTPUT_CHALLENGES, challenges));
		System.out.print("AALfficiency Service returning Challenges");
		return sr;
	}
	
	
	public ServiceResponse getAdvices(){
		ServiceResponse sr = new ServiceResponse(CallStatus.succeeded);
		
		//create the AALfficiencyChallenges
		AdviceModel[] chs = this.advices.getAdvices();
		ArrayList advices = new ArrayList(chs.length);
		for (int i=0;i<chs.length;i++){
			Advice a = new Advice(ADVICE_URI+i, chs[i].getType(), chs[i].getText());
			advices.add(a);
		}
				
		// create and add a ProcessOutput-Event that binds the output URI to the
		// created list of lamps
		sr.addOutput(new ProcessOutput(
			ProvidedAALfficiencyService.OUTPUT_ADVICES, advices));
		System.out.print("AALfficiency Service returning Advices");
		return sr;
	}
	
}
