package org.universAAL.LivingRoom.lights;

import java.util.Random;

public class RandomLightAnimation extends Thread {
	
	private ILightColor light;
	private boolean run;
	
	public RandomLightAnimation(ILightColor light)
	{
		this.light = light;
		this.run = true;
	}
	
	public void run()
	{
		Random generator = new Random( 19580427 );
		while(this.run)
		{
			try {
				((ILightColor) light).setColor(generator.nextFloat(), 1);
				Thread.sleep(200);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
}
