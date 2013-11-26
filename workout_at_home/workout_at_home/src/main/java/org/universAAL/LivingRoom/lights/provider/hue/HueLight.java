package org.universAAL.LivingRoom.lights.provider.hue;

import org.universAAL.LivingRoom.lights.ILight;
import org.universAAL.LivingRoom.lights.ILightColor;
import org.universAAL.LivingRoom.lights.ILightDim;
import org.universAAL.LivingRoom.lights.IStrobe;

public class HueLight implements ILight, IStrobe, ILightColor, ILightDim {
	
	private String key;
	private HueLightsProvider provider;
	
	public HueLight(String key, HueLightsProvider provider) {
		this.key = key;
		this.provider = provider;
	}

	@Override
	public boolean On() {
		System.out.println("開燈喔!!!!");
		HueLightJson request = new HueLightJson();
		request.on = true;
		this.provider.AddLightChange(this.key, request);
		
		return true;
	}
	
	@Override
	public void Off() {
		System.out.println("關燈喔!!!!");
		HueLightJson request = new HueLightJson();
		request.on = false;
		this.provider.AddLightChange(this.key, request);
	}
	
	@Override
	public void StrobeOn() {
		HueLightJson request = new HueLightJson();
		request.alert = HueLightAlerts.select;
		this.provider.AddLightChange(this.key, request);
	}

	@Override
	public void StrobeOff() {
		HueLightJson request = new HueLightJson();
		request.alert = HueLightAlerts.none;
		this.provider.AddLightChange(this.key, request);
	}
	
	@Override
	public void setColor(float h, float s) {
		HueLightJson request = new HueLightJson();
		
		request.hue = (int) (h * 360 * 182);
		request.sat = (int) (s * 254);
		request.transitiontime = 0;
		//request.hue = 33333;
		//request.sat = 214;
		
		this.provider.AddLightChange(this.key, request);
	}
	@Override
	public void setColorHr(int h, int s) {
		System.out.println("******************h:"+h+"s:"+s);
		HueLightJson request = new HueLightJson();
		request.transitiontime = 0;
		request.hue = h;
		request.sat = s;
		
		this.provider.AddLightChange(this.key, request);
	}
	
	@Override
	public void Dim(float value) {
		HueLightJson request = new HueLightJson();
		request.bri = (int) (value * 254);
		
		this.provider.AddLightChange(this.key, request);
	}	
	
	public String getkey()
	{
		return this.key; 
	}
}
