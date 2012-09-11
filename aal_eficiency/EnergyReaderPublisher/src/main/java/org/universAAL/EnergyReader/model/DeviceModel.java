package org.univerAAL.AALapplication.dbExample.model;

public class DeviceModel {
	
	private String location; 
	private String name; 
	private String type;
	
	
	public DeviceModel(){};
	
	public DeviceModel(String location, String name, String type) {
		super();
		this.location = location;
		this.name = name;
		this.type = type;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	} 
	
	
	

}
