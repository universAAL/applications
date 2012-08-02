/**
 * 
 */
package org.universAAL.ltba.activity;

/**
 * @author mllorente
 *
 */
public enum Room {
	KITCHEN("KITCHEN",0), BATHROOM("BATHROOM",1), BEDROOM("BEDROOM",2), LIVINGROOM(
			"LIVING ROOM",3), GARDEN("GARDEN",4), HALL("HALL",5);
	private final String roomString;
	private final int index;

	Room(String name, int index) {
		this.roomString = name;
		this.index = index;
	}

	public String getRoomString() {
		return roomString;
	}

	public static Room getRoomByString(String source) {
		//System.out.println(source);
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
