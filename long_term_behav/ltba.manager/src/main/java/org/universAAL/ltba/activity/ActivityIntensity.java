/*
	Copyright 2008-2014 TSB, http://www.tsbtecnologias.es
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
package org.universAAL.ltba.activity;

import java.awt.Color;

/**
 * @author mllorente
 * 
 */
public enum ActivityIntensity {
	UNKNOWN("UNKNOWN", Color.DARK_GRAY, -1), NULL("NULL", Color.GRAY, 0), LOW(
			"LOW", Color.ORANGE, 3), MEDIUM("MEDIUM", Color.YELLOW, 7), HIGH(
			"HIGH", Color.GREEN, 12);
	private final String intensityString;
	private final Color intensityColor;
	private final int intensityValue;

	ActivityIntensity(String name, Color c, int value) {
		intensityString = name;
		intensityColor = c;
		intensityValue = value;
	}

	public String getIntensityString() {
		return intensityString;
	}

	public Color getIntensityColor() {
		return intensityColor;
	}

	public int getIntensityValue() {
		return intensityValue;
	}

	public static ActivityIntensity getIntensityByString(String intensity) {
		if (intensity.equalsIgnoreCase("HIGH")) {
			return ActivityIntensity.HIGH;
		} else if (intensity.equalsIgnoreCase("MEDIUM")) {
			return ActivityIntensity.MEDIUM;
		} else if (intensity.equalsIgnoreCase("LOW")) {
			return ActivityIntensity.LOW;
		} else if (intensity.equalsIgnoreCase("NULL")) {
			return ActivityIntensity.NULL;
		}
		return null;
	}

}
