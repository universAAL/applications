package org.universAAL.LivingRoom.lights;

import java.util.List;

public interface ILightsProvider {
	
	List<ILight> getLights();
	void Initialize();
	
}
