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

/**
 * @author mllorente
 * 
 */
public enum Room {
	KITCHEN("KITCHEN", 0), BATHROOM("BATHROOM", 1), BEDROOM("BEDROOM", 2), LIVINGROOM(
			"LIVING_ROOM", 3), GARDEN("GARDEN", 4), HALL("HALL", 5);
	private final String roomString;
	private final int index;

	Room(String name, int index) {
		this.roomString = name;
		this.index = index;
	}

	public String getRoomString() {
		return roomString;
	}

	public String getRoomStringNoBlanks() {
		return roomString.replace(" ", "_");
	}

	public static Room getRoomByString(String source) {
		// System.out.println(source);
		if (source.equalsIgnoreCase("Kitchen")) {
			System.out.println("returning kitchen");
			return Room.KITCHEN;
		} else if (source.equalsIgnoreCase("Living room")) {
			return Room.LIVINGROOM;
		} else if (source.equalsIgnoreCase("Bathroom")) {
			return Room.BATHROOM;
		} else if (source.equalsIgnoreCase("Bedroom")) {
			return Room.BEDROOM;
		} else if (source.equalsIgnoreCase("Hall")) {
			return Room.HALL;
		} else if (source.equalsIgnoreCase("Garden")) {
			return Room.GARDEN;
		}
		return null;
	}
}
