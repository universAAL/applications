package org.universAAL.EnergyReader.model;

public class MeasurementModel {
	
	public int measurement; 
	public String unit; 
	
	public MeasurementModel(){}

	public MeasurementModel(int measurement, String unit) {
		super();
		this.measurement = measurement;
		this.unit = unit;
	}

	public int getMeasurement() {
		return measurement;
	}

	public void setMeasurement(int measurement) {
		this.measurement = measurement;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}
	
	

}
