package org.universAAL.AALfficiency.model;

public class ChallengeModel {
	private String type;
	private String goal;
	private String description;
	private String active;
	private String URI;
	
	public ChallengeModel(){};
	
	public ChallengeModel(final String uri, final String type, final String goal, final String description,final String active){
		this.type = type;
		this.goal=goal;
		this.description=description;
		this.active=active;
		this.URI=uri;
		
	};
	
	
	
	public String getURI() {
		return URI;
	}

	public void setURI(String uRI) {
		URI = uRI;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

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
