package org.universAAL.AALfficiency.model;

public class ActivityScoreModel {

	private int todayScore;
	private int totalScore;
	private int steps;
	private int kcal; 
	private ChallengeModel challenge;
	private String sentByPublisher;
	
	public ActivityScoreModel(int todayScore, int totalScore, int steps, int kcal, ChallengeModel challenge, String sent){
		
		this.todayScore = todayScore; 
		this.totalScore = totalScore; 
		this.steps=steps; 
		this.kcal=kcal; 
		this.challenge=challenge; 
		this.sentByPublisher=sent;
		
	}
	
	
	public int getTodayScore() {
		return todayScore;
	}
	public void setTodayScore(int todayScore) {
		this.todayScore = todayScore;
	}
	public int getTotalScore() {
		return totalScore;
	}
	public void setTotalScore(int totalScore) {
		this.totalScore = totalScore;
	}
	public int getSteps() {
		return steps;
	}
	public void setSteps(int steps) {
		this.steps = steps;
	}
	public int getKcal() {
		return kcal;
	}
	public void setKcal(int kcal) {
		this.kcal = kcal;
	}
	public ChallengeModel getChallenge() {
		return challenge;
	}
	public void setChallenge(ChallengeModel challenge) {
		this.challenge = challenge;
	}
	public String getSentByPublisher() {
		return sentByPublisher;
	}
	public void setSentByPublisher(String sentByPublisher) {
		this.sentByPublisher = sentByPublisher;
	}
	
	
	
	
}
