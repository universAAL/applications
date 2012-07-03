package org.universAAL.AALfficiency.model;

public class AALfficiencyChallengesModel {
private ChallengeModel[] challenges;
	
	public AALfficiencyChallengesModel(){};
	
	
	public AALfficiencyChallengesModel(ChallengeModel[] challenges){
		this.challenges=challenges;
	}


	public ChallengeModel[] getChallenges() {
		return challenges;
	}


	public void setChallenges(ChallengeModel[] Challenges) {
		this.challenges = Challenges;
	};
		
}
