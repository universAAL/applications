/*
	Copyright 2011-2012 TSB, http://www.tsbtecnologias.es
	TSB - Tecnologías para la Salud y el Bienestar
	
	See the NOTICE file distributed with this work for additional 
	information regarding copyright ownership
	
	Licensed under the Apache License, Version 2.0 (the "License");
	you may not use this file except in compliance with the License.
	You may obtain a copy of the License at
	
	  http://www.apache.org/licenses/LICENSE-2.0
	
	Unless required by applicable law or agreed to in writing, software
	distributed under the License is distributed on an "AS IS" BASIS,
	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
	See the License for the specific language governing permissions and
	limitations under the License.
 */
package org.universAAL.EnergyReader.model;

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
