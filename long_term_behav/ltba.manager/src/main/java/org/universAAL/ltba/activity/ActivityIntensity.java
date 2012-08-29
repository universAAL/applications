/**
 * 
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
