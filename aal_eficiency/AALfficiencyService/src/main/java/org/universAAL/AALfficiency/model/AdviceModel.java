package org.universAAL.AALfficiency.model;


public class AdviceModel {

	private String type;
	private String text;
	
	public AdviceModel(){};
	
	public AdviceModel(final String type, final String text){
		this.type = type;
		this.text = text;
		
	};
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	
	
}
