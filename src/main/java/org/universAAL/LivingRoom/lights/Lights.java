package org.universAAL.LivingRoom.lights;

import java.util.List;

import org.universAAL.LivingRoom.lights.provider.hue.HueLight;

public class Lights {

	private ILightsProvider provider;

	public Lights(ILightsProvider provider) {
		this.provider = provider;
		this.provider.Initialize();
	}

	public List<ILight> getLights() {
		return this.provider.getLights();
	}

	public void AllOn() {
		for (ILight light : this.getLights()) {
			light.On();
			System.out.println("一顆明燈，走向全世界");
		}
	}

	public void AllOff() {
		for (ILight light : this.getLights()) {
			light.Off();
			System.out.println("一顆滅燈，淨化心靈");
		}
	}

	public void AllOnWith(int h, int s) {
		System.out.println("*************allonwith hues:" + h);
		for (int x = 100; x <= s; x = x + 100) {
			for (ILight light : this.getLights()) {
				try {
					((HueLight) light).setColorHr(h, x);
					if (h == 65280 || h == 25500|| h==12751)
						((HueLight) light).StrobeOn();
					Thread.sleep(100);
				} catch (InterruptedException e) {

				}
			}
		}
	}

	public void OneOn(int h, int s, String lamp) {
		for (ILight light : this.getLights()) {
			if (((HueLight) light).getkey().equalsIgnoreCase(lamp)) {
				System.out.println("丹麥3" + ((HueLight) light).getkey() + "丹麥3");
				light.On();
				// ((HueLight) light).Dim(0.001f);
				// ((HueLight) light).setColorHr(h,s);
				// ((HueLight) light).StrobeOn();

				for (int x = 1; x <= 255; x = x + 200) {
					try {
						((HueLight) light).setColorHr(h, x);
						if (h == 65280)
							((IStrobe) light).StrobeOn();
						// ((HueLight) light).StrobeOn();
						Thread.sleep(100);
					} catch (InterruptedException e) {

					}
				}
				// break;
			}
			if (((HueLight) light).getkey().equalsIgnoreCase(lamp)) {
				System.out.println("丹麥2" + ((HueLight) light).getkey() + "丹麥2");
				light.On();
				((HueLight) light).setColorHr(h, s);
			}
			if (((HueLight) light).getkey().equalsIgnoreCase(lamp)) {
				System.out.println("丹麥1" + ((HueLight) light).getkey() + "丹麥1");
				light.On();
				((HueLight) light).setColorHr(h, s);
			}
		}
	}

	public void OneOff() {
		for (ILight light : this.getLights()) {
			light.Off();
			System.out.println("一顆滅燈，淨化心靈");
		}
	}

}
