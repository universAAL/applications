package org.universAAL.AALapplication.hwo.engine;

//Class that represents a POI, with its generic name and its GPS location.

public class POI { //As this is an internal class, we won't be adding it to the ontology.
	private String Name;
	private String Location;
	
	protected POI(String name, String loc) {
		Name = name;
		Location = loc;
	}
	
	public String getName(){
		return Name;
	}
	
	public String getCoordinate(){
		return Location;
	}
}

