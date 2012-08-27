package org.universAAL.AALapplication.hwo; //Simple Class to manage basic Cg information

public class Caregiver {
	private String Name;
	private String Number;
	
	protected Caregiver(String name, String number){
		Name=name;
		Number = number;
	}
	
	public String getName(){
		return Name;
	}
	
	public String getNumber(){
		return Number;
	}
}
