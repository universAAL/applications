package org.universAAL.AALfficiency.model;

public class ElectricityScoreModel {

	private int totalScore;
	private int todayScore; 
	private int saving; 
	private ChallengeModel challenge; 
	private String sentByPublisher;
	
	public ElectricityScoreModel(int todayScore, int totalScore, int saving, ChallengeModel challenge, String sent){
		
		this.todayScore = todayScore; 
		this.totalScore = totalScore; 
		this.saving=saving;
		this.challenge=challenge; 
		this.sentByPublisher=sent;
		
	}
	
	
	public int getTotalScore() {
		return totalScore;
	}
	public void setTotalScore(int totalScore) {
		this.totalScore = totalScore;
	}
	public int getTodayScore() {
		return todayScore;
	}
	public void setTodayScore(int todayScore) {
		this.todayScore = todayScore;
	}
	public int getSaving() {
		return saving;
	}
	public void setSaving(int saving) {
		this.saving = saving;
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
