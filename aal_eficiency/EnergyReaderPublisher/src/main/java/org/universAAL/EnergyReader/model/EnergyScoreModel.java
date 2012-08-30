package org.universAAL.EnergyReader.model;

public class EnergyScoreModel {

	private int todayScore; 
	private int percentage; 
	private ChallengeModel challenge;
	
	
	public int getTodayScore() {
		return todayScore;
	}
	public void setTodayScore(int todayScore) {
		this.todayScore = todayScore;
	}
	public int getPercentage() {
		return percentage;
	}
	public void setPercentage(int percentage) {
		this.percentage = percentage;
	}
	public ChallengeModel getChallenge() {
		return challenge;
	}
	public void setChallenge(ChallengeModel challenge) {
		this.challenge = challenge;
	} 
	
	
	
}
