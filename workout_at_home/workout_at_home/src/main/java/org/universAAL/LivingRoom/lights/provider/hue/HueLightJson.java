package org.universAAL.LivingRoom.lights.provider.hue;

public class HueLightJson {
	
	public Boolean on;
	
	public Integer hue;		// 65535
	public Integer sat;		// 0 - 254
	public Integer bri;		// 0 - 254
	
	public Integer transitiontime;
	//public String colormode;
	
	//public Integer ct;		// 154 - 500

	
	public HueLightAlerts alert;
}
