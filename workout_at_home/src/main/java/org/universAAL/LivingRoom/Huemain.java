package org.universAAL.LivingRoom;
import org.universAAL.LivingRoom.lights.Lights;
import org.universAAL.LivingRoom.lights.provider.hue.HueLightsConfig;
import org.universAAL.LivingRoom.lights.provider.hue.HueLightsProvider;
import org.universAAL.LivingRoom.lights.provider.hue.HueRegisterStates;

public class Huemain {

	/**
	 * @throws InterruptedException 
	 * @param args
	 * @throws  
	 */
	public static void huecontrol(int hues, int sat, String number) {
		System.out.println("Start");
		
		HueLightsConfig hueConfig = new HueLightsConfig("10.0.1.2", "javaRemote", "javaRemote");
		System.out.println("Start1");
		HueLightsProvider lightsProvider = new HueLightsProvider(hueConfig);
		System.out.println("Start2");
		try {
			System.out.println("Start3");
			// Register if needed
			if(!lightsProvider.IsRegistered())
			{
				HueRegisterStates state;
				while((state = lightsProvider.Register()) == HueRegisterStates.Unregistered)
				{
					System.out.println("Start4");
					System.out.println("Please press the link button.");
					Thread.sleep(5000);
				}
				
				if(state == HueRegisterStates.Registered)
					System.out.println("We are succesfully registered!");
				else
					throw new Exception("Unable to register.");
			}
			
			Lights lights = new Lights(lightsProvider);
			System.out.println("*************huncontrol hues:"+hues);
			lights.AllOn();
			Thread.sleep(100);
			lights.AllOnWith(hues, sat);
			//lights.AllOn();
			//Thread.sleep(1000);
			//lights.OneOn(hues, sat, number);
			/*
			Thread.sleep(1000);
			lights.AllOff();
			System.out.println("Start2");
			Thread.sleep(1000);
			lights.OneOn(hue,sat,"1");
			Thread.sleep(1000);
			lights.OneOn(hue,sat,"2");
			Thread.sleep(1000);
			lights.OneOn(hue,sat,"3");
			Thread.sleep(1000);
			lights.AllOn();*/
			//Thread.sleep(1000);
			//lights.AllOff();
			//Thread.sleep(1000);
			
			
			/*  RainBow Mode
			for(ILight light : lights.getLights())
			{
				light.On();
				if(light instanceof ILightDim)
				{
					((ILightDim) light).Dim(0.001f);
				}
				if(light instanceof ILightColor)
				{
					RainbowLightAnimation animation = new RainbowLightAnimation((ILightColor) light);
					animation.start();
					Thread.sleep(2000);
				}
			}
			*/
			
			/*
			for(ILight light : lights.getLights())
			{
				light.On();
				//Thread.currentThread().sleep(5000);
				//light.Off();
				Thread.currentThread().sleep(5000);
				if(light instanceof IStrobe)
				{
					((IStrobe) light).StrobeOn();
					Thread.currentThread().sleep(5000);
					((IStrobe) light).StrobeOff();
					Thread.currentThread().sleep(5000);
				}
				if(light instanceof ILightColor)
				{
					for(int x = 1; x <= 30; x++)
					{
						((ILightColor) light).setColor((float)x / 30, (float)0.5);
						Thread.currentThread().sleep(1000);
					}
				}
				//light.Off();
			}*/
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
		System.out.println("Stop");
	}

}
