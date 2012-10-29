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
