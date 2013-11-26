package org.universAAL.LivingRoom.lights.provider.hue;

public class HueLightChange {
	
	private String key;
	private HueLightJson state;
	
	public HueLightChange(String key, HueLightJson state)
	{
		this.key = key;
		this.state = state;
	}
	
	public String getKey()
	{
		return this.key;
	}
	
	public HueLightJson getState()
	{
		return this.state;
	}
	
}
