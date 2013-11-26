package org.universAAL.LivingRoom.lights.provider.hue;

public class HueLightsConfig {

	private String address;
	private String username;
	private String deviceType;
	
	public HueLightsConfig(String address, String username, String deviceType) {
		this.address = address;
		this.username = username;
		this.deviceType = deviceType;
	}
	
	/* Getters / setters */
	
	public String getAddress() {
		return this.address;
	}
	
	public String getUsername() {
		return this.username;
	}
	
	public String getDeviceType() {
		return this.deviceType;
	}
	
}
