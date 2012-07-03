package org.universAAL.AALfficiency.model;

public class ChallengeModel {
	private String type;
	private String goal;
	private String description;
	
	public ChallengeModel(){};
	
	public ChallengeModel(final String type, final String goal, final String description){
		this.type = type;
		this.goal=goal;
		this.description=description;
		
	};
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

	public String getGoal() {
		return goal;
	}

	public void setGoal(String goal) {
		this.goal = goal;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
}
