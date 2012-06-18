package org.universAAL.AALapplication.hwo;

// Class that represents a pair of coordinates.

public class Coordinates { //As this is an internal class, we won't be adding it to the ontology.
	private double Longitude;
	private double Latitude;
	
	protected Coordinates(double longit, double latit) {
		Longitude = longit;
		Latitude = latit;
	}
	
	public double getlongit(){
		return Longitude;
	}
	
	public double getlatit(){
		return Latitude;
	}
}


