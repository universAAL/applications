package org.universAAL.LivingRoom.lights;

public class RainbowLightAnimation extends Thread {
	
	private ILightColor light;
	private boolean run;
	
	public RainbowLightAnimation(ILightColor light)
	{
		this.light = light;
		this.run = true;
	}
	
	public void run()
	{
		while(this.run)
		{
			for(int x = 1; x <= 300; x++)
			{
				try {
					((ILightColor) light).setColor((float)x / 300, 1);
					Thread.sleep(100);
				} catch (InterruptedException e) {
					
				}
			}
		}
	}
	
}
