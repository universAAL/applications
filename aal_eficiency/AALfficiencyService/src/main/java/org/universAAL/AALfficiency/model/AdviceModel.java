package org.universAAL.AALfficiency.model;


public class AdviceModel {

	private String URI;
	private String type;
	private String text;
	
	public AdviceModel(){};
	
	public AdviceModel(final String uri,final String type, final String text){
		this.URI=uri;
		this.type = type;
		this.text = text;
		
	};
	
	
	public String getURI() {
		return URI;
	}

	public void setURI(String uRI) {
		URI = uRI;
	}

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
