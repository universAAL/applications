package org.univerAAL.AALapplication.dbExample.model;

public class ReadEnergyModel {
	
	private DeviceModel device;
	private MeasurementModel measure;
	
	public ReadEnergyModel(DeviceModel device, MeasurementModel measure) {
		super();
		this.device = device;
		this.measure = measure;
	}
	
	public ReadEnergyModel() {
		super();
		
	}
	
	public DeviceModel getDevice() {
		return device;
	}
	public void setDevice(DeviceModel device) {
		this.device = device;
	}
	public MeasurementModel getMeasure() {
		return measure;
	}
	public void setMeasure(MeasurementModel measure) {
		this.measure = measure;
	}
		
}
