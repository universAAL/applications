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

public class AALfficiencyScoreModel {
	
	private int totalScore;
	private int todayScore;
	private int totalElectricScore;
	private int todayElectricScore;
	private int totalActivityScore;
	private int todayActivityScore;
	
	public AALfficiencyScoreModel(){};
	
	public AALfficiencyScoreModel(int totalScore,int todayScore, int totalElectricScore,int todayElectricScore,
	int totalActivityScore,int todayActivityScore){
		this.totalScore=totalScore;
		this.todayScore=todayScore;
		this.totalElectricScore=totalElectricScore;
		this.todayElectricScore=todayElectricScore;
		this.totalActivityScore=totalActivityScore;
		this.todayActivityScore=todayActivityScore;
		
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

	public int getTotalElectricScore() {
		return totalElectricScore;
	}

	public void setTotalElectricScore(int totalElectricScore) {
		this.totalElectricScore = totalElectricScore;
	}

	public int getTodayElectricScore() {
		return todayElectricScore;
	}

	public void setTodayElectricScore(int todayElectricScore) {
		this.todayElectricScore = todayElectricScore;
	}

	public int getTotalActivityScore() {
		return totalActivityScore;
	}

	public void setTotalActivityScore(int totalActivityScore) {
		this.totalActivityScore = totalActivityScore;
	}

	public int getTodayActivityScore() {
		return todayActivityScore;
	}

	public void setTodayActivityScore(int todayActivityScore) {
		this.todayActivityScore = todayActivityScore;
	};

	
	
}
